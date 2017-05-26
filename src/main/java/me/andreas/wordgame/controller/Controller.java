package me.andreas.wordgame.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import me.andreas.wordgame.Main;
import me.andreas.wordgame.game.Stats;
import me.andreas.wordgame.util.GameType;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable{

	@FXML public Button unscramble;
	@FXML public TreeView<String> statsView;
	@FXML public ChoiceBox<String> choiceBox;

	private Main main;

	private TreeItem<String> unscrambleNode;
	private TreeItem<String> normalNode;


	public void startGame(){
		if (choiceBox.getSelectionModel().getSelectedItem().equalsIgnoreCase("Unscramble")) {
			main.startGame(GameType.UNSCRAMBLE);
		}else{
			main.startGame(GameType.NORMAL);
		}
	}

	public void setStats(Stats stats){
		TreeItem<String> rootNode = stats.getGameType() == GameType.UNSCRAMBLE ? unscrambleNode : normalNode;

		TreeItem<String> roundStats = new TreeItem<>("Round " + (rootNode.getChildren().size() + 1));

		roundStats.getChildren().add(new TreeItem<>("Words: " + stats.getWords() + " / " + stats.getMaxWords()));
		roundStats.getChildren().add(new TreeItem<>("Best Time: " + stats.getBestWord().getDisplayTime() + " (" + stats.getBestWord().getWord() + ")"));
		roundStats.getChildren().add(new TreeItem<>("Worst Time: " + stats.getWorstWord().getDisplayTime()  + " (" + stats.getWorstWord().getWord() + ")"));
		roundStats.getChildren().add(new TreeItem<>());

		stats.getAllWords().forEach(w -> roundStats.getChildren().add(new TreeItem<>(w.getWord() + ": " + w.getDisplayTime())));
		roundStats.setExpanded(true);
		rootNode.getChildren().forEach(t -> t.setExpanded(false));
		rootNode.getChildren().add(roundStats);
		rootNode.setExpanded(true);

		if(rootNode == unscrambleNode) normalNode.setExpanded(false);
		else unscrambleNode.setExpanded(false);
	}

	public void openSettings(){
		main.showOptionsWindow();
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		main = Main.getInstance();

		ArrayList<String> types = new ArrayList<>();
		types.add("Unscramble");
		types.add("Normal");
		choiceBox.setItems(FXCollections.observableArrayList(types));
		choiceBox.getSelectionModel().selectFirst();

		TreeItem<String> rootNode = new TreeItem<>();
		unscrambleNode = new TreeItem<>("Unscramble");
		normalNode = new TreeItem<>("Normal");
		rootNode.getChildren().addAll(unscrambleNode, normalNode);

		statsView.setRoot(rootNode);
		statsView.setShowRoot(false);
		rootNode.setExpanded(true);
	}
}
