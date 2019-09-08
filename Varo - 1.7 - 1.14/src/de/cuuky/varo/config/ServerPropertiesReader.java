package de.cuuky.varo.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ServerPropertiesReader {
	
	/*
	 * OLD CODE
	 */

	private Scanner scanner;

	public ServerPropertiesReader() {
		try {
			scanner = new Scanner(new File("server.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Object get(String key) {
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (!line.split("=")[0].equals(key))
				continue;

			return line.split("=")[1];
		}

		return null;
	}
}