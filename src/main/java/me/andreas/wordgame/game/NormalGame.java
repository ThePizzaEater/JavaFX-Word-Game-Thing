package me.andreas.wordgame.game;

import javafx.animation.AnimationTimer;
import me.andreas.wordgame.Main;
import me.andreas.wordgame.controller.GameController;
import me.andreas.wordgame.util.Settings;
import me.andreas.wordgame.util.GameType;
import me.andreas.wordgame.util.words.ListFromFile;
import me.andreas.wordgame.util.words.NormalWord;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class NormalGame {

	protected NormalWord word;

	private static List<String> allWords;
	static Queue<String> words;

	private Main main;

	private int round;
	private long roundStart;

	private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	private ScheduledFuture<?> task;
	private AnimationTimer countdown;

	private GameController controller;

	private Stats stats;

	private DecimalFormat decimalFormat;
	GameType gameType;

	private Logger logger = Logger.getLogger(getClass().getName());

	public NormalGame(GameController gameController){
		main = Main.getInstance();

		if(allWords == null) allWords = new ListFromFile().getWordsFromFile();
		if(words == null) words = new LinkedList<>();

		controller = gameController;
		decimalFormat = Settings.getDecimalFormat();
		gameType = GameType.NORMAL;
	}

	public void start(){
		stats = new Stats(gameType);
		round = 1;
		controller.updateSomeText(gameType);

		startRound();
	}

	private void startRound(){
		if (round <= Settings.getWordsPerRound()) {
			round++;

			if(words == null || words.peek() == null){
				Collections.shuffle(allWords);
				words.addAll(allWords);
			}
			setNewWord();
			cancelRunningTasks();

			task = executor.schedule(this::endRound, Settings.getWordDuration(), TimeUnit.SECONDS);
			if(countdown == null) {
				countdown = new AnimationTimer() {
					@Override
					public void handle(long now) {
						updateTimeLeftText();
					}
				};
			}else countdown.start();

			roundStart = System.currentTimeMillis();
			controller.resetInput();
			controller.setSecondsLeft(Integer.toString(Settings.getWordDuration()));

			countdown.start();
			displayWord();
		}else{
			exit();
		}
	}

	protected void setNewWord(){
		word = new NormalWord(words.poll());
	}

	private void displayWord(){
		controller.setWord(word.getDisplayWord());
		if(Settings.shouldCheat()){
			logger.info(word.getOriginalWord());
		}
	}

	public void checkAnswer(String input){
		if(word.isAnswer(input)) {
			cancelRunningTasks();
			stats.completedWord(word.getOriginalWord(), (System.currentTimeMillis() - roundStart));
			startRound();
		}else{
			if(Settings.isAlwaysNext()){
				cancelRunningTasks();
				stats.failedWord(word.getOriginalWord());
				startRound();
			}else controller.resetInput();
		}
	}

	private void updateTimeLeftText(){
		controller.setSecondsLeft(decimalFormat.format((double) task.getDelay(TimeUnit.MILLISECONDS) / 1000));
	}

	private void endRound(){
		stats.failedWord(word.getOriginalWord());
		startRound();
	}

	private void cancelRunningTasks(){
		if(task != null && !task.isDone()) task.cancel(true);
		if(countdown != null) countdown.stop();

		task = null;
	}

	public void end(){
		stats.failedWord(word.getOriginalWord());
		exit();
	}

	private void exit(){
		cancelRunningTasks();
		main.showStartupScene(stats);
	}
}
