package me.andreas.wordgame.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.andreas.wordgame.Main;

import java.io.IOException;
import java.util.logging.Logger;

public class UI<C> {

	private Logger logger = Logger.getLogger(getClass().getName());

	private Scene scene;
	private C controller;

	private String fxmlFileName;

	public UI(String fxmlFileName) {
		this.fxmlFileName = fxmlFileName;
	}

	public void show(Stage stage){
		if(scene == null || controller == null){
			try{
				FXMLLoader loader = loadFxml(fxmlFileName);
				Parent root = loader.load();
				scene = new Scene(root, 1080, 720);
				controller = loader.getController();
			} catch (IOException e) {
				logger.info("Failed to load startup scene.");
				e.printStackTrace();
			}
		}
		stage.setScene(scene);
	}

	public static FXMLLoader loadFxml(String fileName){
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("fxml/" + fileName));
		loader.setClassLoader(Main.class.getClassLoader());
		return loader;
	}

	public C getController() {
		return controller;
	}
}
