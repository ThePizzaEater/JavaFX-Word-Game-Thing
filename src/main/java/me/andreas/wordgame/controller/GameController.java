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

public class GameController implements Initializable{

	@FXML public TextField input;

	@FXML public Text secondsLeft;
	@FXML public Label word;
	@FXML public Label someText;

	private Main main;

	public void updateSomeText(GameType gameType){
		switch (gameType){
			case NORMAL:
				someText.setText("Word:");
				break;
			case UNSCRAMBLE:
				someText.setText("Unscramble:");
				break;
		}
	}

	public void checkInputWord(){
		String input = this.input.getText();
		main.getGame().checkAnswer(input);
	}

	public void resetInput(){
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
		if(Platform.isFxApplicationThread()){
			this.word.setText(word);
		}else{
			Platform.runLater(() -> this.word.setText(word));
		}
	}

	public void setSecondsLeft(String secondsLeft1){
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
