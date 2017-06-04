package me.andreas.wordgame.game;

import me.andreas.wordgame.util.GameType;
import me.andreas.wordgame.util.words.WordStats;

import java.util.Iterator;
import java.util.TreeSet;

public class Stats {

	private int words;

	private TreeSet<WordStats> allWords;

	private GameType gameType;

	public Stats(GameType gameType) {
		this.gameType = gameType;
		words = 0;

		allWords = new TreeSet<>((o1, o2) -> {
			if(o1.getTime() == 0 || o1.getTime() == o2.getTime()) return 1;
			else if(o2.getTime() == 0) return -1;

			return ((Long)o1.getTime()).compareTo((Long)o2.getTime());
		});
	}

	public void completedWord(String word, long time){
		words++;
		allWords.add(new WordStats(word, time));
	}

	void failedWord(String word){
		allWords.add(new WordStats(word, 0));
	}

	public int getWords() {
		return words;
	}

	public int getMaxWords() {
		return allWords.size();
	}

	public WordStats getBestWord(){
		return allWords.first();
	}

	public WordStats getWorstWord(){
		Iterator<WordStats> wordIterator = allWords.descendingIterator();
		while(wordIterator.hasNext()){
			WordStats word = wordIterator.next();
			if(word.getTime() != 0 || !wordIterator.hasNext()){
				return word;
			}
		}
		return allWords.last();
	}

	public TreeSet<WordStats> getAllWords(){
		return allWords;
	}

	public GameType getGameType() {
		return gameType;
	}
}
