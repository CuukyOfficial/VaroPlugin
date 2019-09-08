package de.cuuky.varo.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.scanner.ScannerException;

public class ConfigFailureDetector {

	private static ArrayList<String> scan;

	static {
		scan = new ArrayList<>();
		scan.add("stats");
	}

	private boolean failure;

	public ConfigFailureDetector() {
		failure = false;
		File newFile = new File("plugins/Varo");
		if(newFile.listFiles() == null)
			newFile.mkdir();

		scanDirectory(newFile);

		System.out.println("[Varo] Configurations scanned for mistakes successfully!");
	}

	private void scanDirectory(File newFile) {
		for(File file : newFile.listFiles()) {
			if(file.isDirectory()) {
				if(!scan.contains(file.getName()))
					continue;

				scanDirectory(file);
				continue;
			}

			if(!file.getName().endsWith(".yml"))
				continue;

			try {
				new YamlConfiguration().load(file);
			} catch(ScannerException e) {} catch(FileNotFoundException e) {} catch(IOException e) {} catch(InvalidConfigurationException e) {
				if(e.getMessage().contains("deserialize"))
					continue;

				failure = true;
				System.err.println("[Varo] Config failure detected!");
				System.err.println("[Varo] File: " + file.getName());
				System.err.println("[Varo] Usually the first information of the message gives you the location of the mistake. Just read the error and check the files.");
				System.err.println("[Varo] Message: \n" + e.getMessage());
				return;
			}
		}
	}

	public boolean hasFailed() {
		return failure;
	}
}
