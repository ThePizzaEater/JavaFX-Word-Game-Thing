package me.andreas.wordgame.util;

import java.io.*;
import java.text.DecimalFormat;
import java.util.Properties;

public class Settings {

	private static int MAX_SCRAMBLE_ATTEMPTS = 50;
	private static int WORDS_PER_ROUND = 6;
	private static int WORD_DURATION = 20;

	private static boolean ALWAYS_NEXT = false;
	private static boolean CHEAT = false;

	private static DecimalFormat decimalFormat;

	private Properties properties;
	private File file;

	public Settings() {
		properties = new Properties();
		try{
			file = FileUtils.loadFile("config.properties");
			properties.load(new FileInputStream(file));

			String maxAttempts = properties.getProperty("maxScrambleAttempts");
			MAX_SCRAMBLE_ATTEMPTS = Integer.valueOf(maxAttempts);

			String rounds = properties.getProperty("scrambleRounds");
			WORDS_PER_ROUND = Integer.valueOf(rounds);

			String roundDuration = properties.getProperty("roundDuration");
			WORD_DURATION = Integer.valueOf(roundDuration);

			String alwaysNext = properties.getProperty("alwaysNext");
			ALWAYS_NEXT = Boolean.valueOf(alwaysNext);

			String cheat = properties.getProperty("cheat");
			CHEAT = Boolean.valueOf(cheat);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void updateSettings(int maxScrambleAttempts, int scrambleRounds, int roundDuration, boolean alwaysNext, boolean cheat){
		boolean shouldSave = false;
		if(maxScrambleAttempts != MAX_SCRAMBLE_ATTEMPTS){
			properties.setProperty("maxScrambleAttempts", Integer.toString(maxScrambleAttempts));
			MAX_SCRAMBLE_ATTEMPTS = maxScrambleAttempts;
			shouldSave = true;
		}
		if(scrambleRounds != WORDS_PER_ROUND){
			properties.setProperty("scrambleRounds", Integer.toString(scrambleRounds));
			WORDS_PER_ROUND = scrambleRounds;
			shouldSave = true;
		}
		if (roundDuration != WORD_DURATION) {
			properties.setProperty("roundDuration", Integer.toString(roundDuration));
			WORD_DURATION = roundDuration;
			shouldSave = true;
		}
		if(alwaysNext != ALWAYS_NEXT){
			properties.setProperty("alwaysNext", Boolean.toString(alwaysNext));
			ALWAYS_NEXT = alwaysNext;
			shouldSave = true;
		}
		if(cheat != CHEAT){
			properties.setProperty("cheat", Boolean.toString(cheat));
			CHEAT = cheat;
			shouldSave = true;
		}
		if(shouldSave){
			try(FileOutputStream out = new FileOutputStream(file)){
				properties.store(out, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static DecimalFormat getDecimalFormat() {
		if(decimalFormat == null){
			decimalFormat = new DecimalFormat("#0.00");
		}
		return decimalFormat;
	}

	public static int getMaxScrambleAttempts() {
		return MAX_SCRAMBLE_ATTEMPTS;
	}

	public static int getWordsPerRound() {
		return WORDS_PER_ROUND;
	}

	public static int getWordDuration() {
		return WORD_DURATION;
	}

	public static boolean isAlwaysNext() {
		return ALWAYS_NEXT;
	}

	public static boolean shouldCheat() {
		return CHEAT;
	}
}
