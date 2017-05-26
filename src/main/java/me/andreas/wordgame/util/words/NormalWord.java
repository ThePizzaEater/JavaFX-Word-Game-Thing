package me.andreas.wordgame.util.words;

public class NormalWord {

	private String originalWord;

	public NormalWord(String word) {
		originalWord = word;
	}

	public boolean isAnswer(String input){
		return input.equalsIgnoreCase(originalWord);
	}

	public String getOriginalWord() {
		return originalWord;
	}

	public String getDisplayWord(){
		return originalWord;
	}

}
