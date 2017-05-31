package me.andreas.wordgame.util;

import me.andreas.wordgame.Main;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.logging.Logger;

public class FileUtils {

	private static Logger logger = Logger.getLogger(FileUtils.class.getName());

	public static File loadFile(String filePath){
		File file = new File(filePath);
		if(!file.exists()){
			try(InputStream in = Main.class.getResourceAsStream(filePath)){
				Files.copy(in, file.toPath());
			} catch (IOException e) {
				logger.info("Failed to save file: " + filePath);
				e.printStackTrace();
			}
		}
		return file;
	}

}
