package de.cuuky.varo.utils.varo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class ServerPropertiesReader {
	
	private HashMap<String, String> configuration;
	
	public ServerPropertiesReader() {
		this.configuration = new HashMap<>();
		
		readProperties();
	}
	
	private void readProperties() {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("server.properties"));
			while(scanner.hasNextLine()) {
				String[] line = scanner.nextLine().split("=");
				if(line.length != 2)
					continue;
				
				configuration.put(line[0], line[1]);
			}

		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}
	}
	
	public HashMap<String, String> getConfiguration() {
		return this.configuration;
	}
}