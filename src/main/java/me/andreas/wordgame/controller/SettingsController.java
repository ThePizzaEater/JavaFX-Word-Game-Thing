package me.andreas.wordgame.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import me.andreas.wordgame.Main;
import me.andreas.wordgame.util.Settings;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable{

	@FXML public TabPane tabPane;

	@FXML public TextField maxScrambleAttempts;
	@FXML public TextField scrambleRounds;
	@FXML public TextField roundDuration;

	@FXML public Button apply;
	@FXML public RadioButton alwaysNext;
	@FXML public RadioButton cheatButton;

	private Settings settings;

	public void applySettings(){
		try{
			int maxScrambleAttempts = Integer.valueOf(this.maxScrambleAttempts.getText());
			int scrambleRounds = Integer.valueOf(this.scrambleRounds.getText());
			int roundDuration = Integer.valueOf(this.roundDuration.getText());
			boolean alwaysNext = this.alwaysNext.isSelected();
			boolean cheat = this.cheatButton.isSelected();
			settings.updateSettings(maxScrambleAttempts, scrambleRounds, roundDuration, alwaysNext, cheat);
		}catch (NumberFormatException e){
			setCurrentValues();
		}
		Stage stage = (Stage) apply.getScene().getWindow();
		stage.close();
	}

	private void setCurrentValues(){
		maxScrambleAttempts.setText(Integer.toString(Settings.getMaxScrambleAttempts()));
		scrambleRounds.setText(Integer.toString(Settings.getWordsPerRound()));
		roundDuration.setText(Integer.toString(Settings.getWordDuration()));
		alwaysNext.setSelected(Settings.isAlwaysNext());
		cheatButton.setSelected(Settings.shouldCheat());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		VBox.setVgrow(tabPane, Priority.ALWAYS);

		makeTextFieldNumeric(maxScrambleAttempts, 3, Integer.MAX_VALUE);
		makeTextFieldNumeric(scrambleRounds, 1, 99);
		makeTextFieldNumeric(roundDuration, 1, Integer.MAX_VALUE);

		setCurrentValues();
		settings = Main.getInstance().getSettings();
	}

	private void makeTextFieldNumeric(TextField field, Integer min, Integer max){
		field.textProperty().addListener((observable, oldValue, newValue) -> {
			if(!newValue.matches("\\d*")){
				field.setText(newValue.replaceAll("[^\\d]",""));
			}
			if(field.getText().isEmpty()) return;

			Integer input = null;
			try{
				input = Integer.valueOf(field.getText());
			}catch (NumberFormatException ignored){}

			if(input == null || (max != null && input > max)) field.setText(max.toString());
			else if(min != null && input < min) field.setText(min.toString());
		});
	}

}
