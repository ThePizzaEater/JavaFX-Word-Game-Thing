package me.andreas.wordgame.util.words;

import me.andreas.wordgame.util.WordScrambler;

public class ScrambledWord extends NormalWord{

	private static WordScrambler scrambler = new WordScrambler();

	private String scrambledWord;

	public ScrambledWord(String word) {
		super(word);
		scrambledWord = scrambler.scramble(word);
	}

	@Override
	public String getDisplayWord() {
		return scrambledWord;
	}
}
