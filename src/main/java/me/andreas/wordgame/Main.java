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
import me.andreas.wordgame.util.Settings;
import me.andreas.wordgame.util.GameType;

import java.io.IOException;
import java.util.logging.Logger;

public class Main extends Application {

	private static Main instance;

	private Stage primaryStage;
	private Stage settingsStage;

	private Scene startScene;
	private Scene gameScene;
	private Scene settingsScene;

	private GameController gameController;
	private Controller controller;

	private NormalGame game;
	private Settings settings;

	private Logger logger = Logger.getLogger(getClass().getName());

	@Override
    public void start(Stage primaryStage) throws Exception{
    	try {
			instance = this;
			this.primaryStage = primaryStage;
			settings = new Settings();
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
				game = new UnscrambleGame(gameController);
				game.start();
				break;
			case NORMAL:
				showGameScene();
				game = new NormalGame(gameController);
				game.start();
				break;
			default:
				logger.info("Tried to start unsupported GameType: " + gameType);
				break;
		}
	}

	private void showGameScene() {
		if (gameController == null || gameScene == null){
			try {
				FXMLLoader loader = loadFxml("game.fxml");
				Parent root = loader.load();
				gameScene = new Scene(root, 1080, 720);
				gameController = loader.getController();

			}catch (IOException e) {
				logger.info("Failed to load game scene.");
				e.printStackTrace();
			}
		}
		primaryStage.setScene(gameScene);
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
		controller.setStats(stats);
	}

	private void showStartupScene(){
		if(startScene == null || controller == null){
			try{
				FXMLLoader loader = loadFxml("main.fxml");
				Parent root = loader.load();
				startScene = new Scene(root, 1080, 720);
				controller = loader.getController();
			} catch (IOException e) {
				logger.info("Failed to load startup scene.");
				e.printStackTrace();
			}
		}
		primaryStage.setScene(startScene);
	}

	public void showOptionsWindow(){
		if(settingsStage == null || settingsScene == null){
			try{
				FXMLLoader loader = loadFxml("options.fxml");
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

	private FXMLLoader loadFxml(String fileName){
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("fxml/" + fileName));
		loader.setClassLoader(Main.class.getClassLoader());
		return loader;
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
