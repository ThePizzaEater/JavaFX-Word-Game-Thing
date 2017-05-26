package me.andreas.wordgame.util;

import java.util.Random;

public class WordScrambler {

	private Random random;

	public WordScrambler() {
		random = new Random();
	}

	public String scramble(String word){
		String result = word;
		for(int x = 1; x <= Settings.getMaxScrambleAttempts(); x++){
			result = scrambleWord(word);
			if(!result.equalsIgnoreCase(word)){
				break;
			}
		}
		return result;
	}

	private String scrambleWord(String word){
		char letters[] = word.toCharArray();
		for(int x = 0; x < letters.length-1; x++){
			int j = random.nextInt(letters.length - 1);
			char temp = letters[x];
			letters[x] = letters[j];
			letters[j] = temp;
		}
		return new String(letters);
	}

}
