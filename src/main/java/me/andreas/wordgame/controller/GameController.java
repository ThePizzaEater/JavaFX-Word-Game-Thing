package me.andreas.wordgame.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import me.andreas.wordgame.Main;
import me.andreas.wordgame.util.GameType;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class GameController implements Initializable{

	@FXML public TextField input;

	@FXML public Text secondsLeft;
	@FXML public Label word;
	@FXML public Label someText;

	private Main main;

	private Logger logger = Logger.getLogger(getClass().getName());

	public void updateSomeText(GameType gameType){
		switch (gameType){
			case NORMAL:
				someText.setText("Word:");
				break;
			case UNSCRAMBLE:
				someText.setText("Unscramble:");
				break;
			default:
				logger.info("Unsupported GameType: " + gameType);
				break;
		}
	}

	public void checkInputWord(){
		String input = this.input.getText();
		main.getGame().checkAnswer(input);
	}

	public void resetInput(){
		// Check if method is being called on ui thread, if its not, queue the task on the ui thread.
		// Gets called from another thread if the round time expires.
		if(Platform.isFxApplicationThread()){
			clearInput();
		}else{
			Platform.runLater(this::clearInput);
		}
	}

	private void clearInput(){
		input.clear();
		if(!input.isFocused()){
			input.requestFocus();
		}
	}

	public void setWord(String word){
		// same as resetInput
		if(Platform.isFxApplicationThread()){
			this.word.setText(word);
		}else{
			Platform.runLater(() -> this.word.setText(word));
		}
	}

	public void setSecondsLeft(String secondsLeft1){
		// same as resetInput
		if(Platform.isFxApplicationThread()){
			secondsLeft.setText(secondsLeft1);
		}else{
			Platform.runLater(() -> secondsLeft.setText(secondsLeft1));
		}
	}

	public void end(){
		main.getGame().end();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		main = Main.getInstance();
	}
}
