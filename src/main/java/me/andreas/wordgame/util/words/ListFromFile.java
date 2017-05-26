package me.andreas.wordgame.util.words;

import me.andreas.wordgame.util.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ListFromFile {

	public List<String> getWordsFromFile(){
		File file = FileUtils.loadFile("words.txt");
		try {
			Scanner scanner = new Scanner(file);
			List<String> words = new ArrayList<>();
			while(scanner.hasNextLine()){
				String next = scanner.nextLine();
				words.add(next);
			}
			return words;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}


}
