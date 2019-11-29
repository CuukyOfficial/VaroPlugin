package de.cuuky.varo.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import de.cuuky.varo.Main;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.scanner.ScannerException;

public final class ConfigFailureDetector {

	private static ArrayList<String> scan;

	static {
		scan = new ArrayList<>();
		scan.add("stats");
	}

	public static void detectConfig() {
		File newFile = new File("plugins/Varo");
		if(newFile.listFiles() == null)
			newFile.mkdir();

		if (scanDirectory(newFile)) {
			System.out.println(Main.getPrefix() + "Configurations scanned for mistakes - mistakes have been found");
			//TODO Plugin Shutdown
		} else {
			System.out.println(Main.getPrefix() + "Configurations scanned for mistakes successfully!");
		}
	}

	private static boolean scanDirectory(File newFile) {
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

				System.err.println("[Varo] Config failure detected!");
				System.err.println("[Varo] File: " + file.getName());
				System.err.println("[Varo] Usually the first information of the message gives you the location of the mistake. Just read the error and check the files.");
				System.err.println("[Varo] Message: \n" + e.getMessage());
				return true;
			}
		}
		return false;
	}
}
