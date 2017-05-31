package me.andreas.wordgame.util.words;

import me.andreas.wordgame.util.FileUtils;
import me.andreas.wordgame.util.Settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class ListFromFile {

	private Logger logger = Logger.getLogger(getClass().getName());

	public List<String> getWordsFromFile(){
		File file = FileUtils.loadFile(Settings.getWordsFilePath());

		try(Scanner scanner = new Scanner(file)){

			List<String> words = new ArrayList<>();
			while(scanner.hasNextLine()){
				String next = scanner.nextLine();
				words.add(next);
			}

			return words;
		} catch (FileNotFoundException e) {
			logger.info("Unable to find the words file.");
			e.printStackTrace();
		}
		return null;
	}


}
