package me.andreas.wordgame.game;

import me.andreas.wordgame.controller.GameController;
import me.andreas.wordgame.util.GameType;
import me.andreas.wordgame.util.words.ScrambledWord;


public class UnscrambleGame extends NormalGame{

	public UnscrambleGame(GameController gameController) {
		super(gameController);
		gameType = GameType.UNSCRAMBLE;
	}

	@Override
	protected void setNewWord() {
		word = new ScrambledWord(words.poll());
	}

}
