package me.andreas.wordgame.util.words;

import me.andreas.wordgame.util.Settings;

public class WordStats {

	private String word;
	private long time;

	public WordStats(String word, long time) {
		this.word = word;
		this.time = time;
	}

	public String getWord() {
		return word;
	}

	public long getTime() {
		return time;
	}

	public String getDisplayTime(){
		return Settings.getDecimalFormat().format((double) time / 1000);
	}
}
