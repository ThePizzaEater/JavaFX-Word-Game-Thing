package me.andreas.wordgame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import me.andreas.wordgame.controller.Controller;
import me.andreas.wordgame.controller.GameController;
import me.andreas.wordgame.game.NormalGame;
import me.andreas.wordgame.game.Stats;
import me.andreas.wordgame.game.UnscrambleGame;
import me.andreas.wordgame.ui.UI;
import me.andreas.wordgame.util.Settings;
import me.andreas.wordgame.util.GameType;

import java.io.IOException;
import java.util.logging.Logger;

public class Main extends Application {

	private static Main instance;

	private Stage primaryStage;
	private Stage settingsStage;

	private Scene settingsScene;

	private UI<GameController> gameUI;
	private UI<Controller> startUI;

	private NormalGame game;
	private Settings settings;

	private Logger logger = Logger.getLogger(getClass().getName());

	@Override
    public void start(Stage primaryStage){
    	try {
			instance = this;
			this.primaryStage = primaryStage;
			settings = new Settings();

			startUI = new UI<>("main.fxml");
			gameUI = new UI<>("game.fxml");

			showStartupScene();
			primaryStage.setTitle("Word Game Thing");
			primaryStage.show();
		}catch (Exception e){
    		logger.info(e.getMessage());
    		e.printStackTrace();
		}
    }

    public void startGame(GameType gameType){
		switch (gameType){
			case UNSCRAMBLE:
				showGameScene();
				game = new UnscrambleGame(gameUI.getController());
				game.start();
				break;
			case NORMAL:
				showGameScene();
				game = new NormalGame(gameUI.getController());
				game.start();
				break;
			default:
				logger.info("Tried to start unsupported GameType: " + gameType);
				break;
		}
	}

	private void showStartupScene(){
		startUI.show(primaryStage);
	}

	private void showGameScene() {
		gameUI.show(primaryStage);
	}

	public void showStartupScene(Stats stats){
		// Check if method is being called on ui thread, if its not, queue the task on the ui thread.
		// Gets called from another thread if the last rounds time expires.
    	if(Platform.isFxApplicationThread()){
    		showStartup(stats);
		}else{
    		Platform.runLater(() -> showStartup(stats));
		}
	}

	private void showStartup(Stats stats){
		showStartupScene();
		startUI.getController().setStats(stats);
	}

	public void showOptionsWindow(){
		if(settingsStage == null || settingsScene == null){
			try{
				FXMLLoader loader = UI.loadFxml("options.fxml");
				Parent root = loader.load();

				settingsScene = new Scene(root, 400, 520);

				settingsStage = new Stage();
				settingsStage.setTitle("Options");
				settingsStage.setScene(settingsScene);
				settingsStage.setResizable(false);
				settingsStage.initModality(Modality.APPLICATION_MODAL);
				settingsStage.initOwner(primaryStage);
			} catch (IOException e) {
				logger.info("Failed to load options window.");
				e.printStackTrace();
			}
		}
		settingsStage.show();
	}

	public Settings getSettings(){
		return settings;
	}

	public NormalGame getGame(){
		return game;
	}

	public static Main getInstance() {
		return instance;
	}

	@Override
	public void stop() throws Exception {
		super.stop();
		System.exit(0);
	}

	public static void main(String[] args) {
        launch(args);
    }
}
