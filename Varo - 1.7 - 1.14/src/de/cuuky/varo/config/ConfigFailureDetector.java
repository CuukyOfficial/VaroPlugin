package de.cuuky.varo.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import org.yaml.snakeyaml.scanner.ScannerException;

import de.cuuky.varo.Main;

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

		if(scanDirectory(newFile)) {
			System.out.println(Main.getConsolePrefix() + "Configurations scanned for mistakes - mistakes have been found");
			System.out.println(Main.getConsolePrefix() + "Plugin will get shut down.");
			Bukkit.getPluginManager().disablePlugin(Main.getInstance());
		} else {
			System.out.println(Main.getConsolePrefix() + "Configurations scanned for mistakes successfully!");
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

				System.err.println(Main.getConsolePrefix() + "Config failure detected!");
				System.err.println(Main.getConsolePrefix() + "File: " + file.getName());
				System.err.println(Main.getConsolePrefix() + "Usually the first information of the message gives you the location of the mistake. Just read the error and check the files.");
				System.err.println(Main.getConsolePrefix() + "Message: \n" + e.getMessage());
				return true;
			}

		}
		return false;
	}
}