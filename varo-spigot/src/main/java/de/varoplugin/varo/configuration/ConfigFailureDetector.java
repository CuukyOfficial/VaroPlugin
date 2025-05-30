package de.varoplugin.varo.configuration;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import org.yaml.snakeyaml.scanner.ScannerException;

import de.varoplugin.cfw.configuration.YamlConfigurationUtil;
import de.varoplugin.varo.Main;

public final class ConfigFailureDetector {

	private static List<String> ignoreScan = Arrays.asList("logs", "presets", "legacy");

	private boolean failed;

	public ConfigFailureDetector() {
		detectConfig();
	}

	private void detectConfig() {
		File newFile = new File("plugins/Varo");
		if (newFile.listFiles() == null)
			newFile.mkdir();

		if (scanDirectory(newFile)) {
			Main.getInstance().getLogger().log(Level.SEVERE, "Configurations scanned for mistakes - mistakes have been found");
			Main.getInstance().getLogger().log(Level.SEVERE, "The plugin will shut down.");

			this.failed = true;
		} else {
			Main.getInstance().getLogger().log(Level.INFO, "Configurations scanned for mistakes successfully!");
		}
	}

	private boolean scanDirectory(File newFile) {
		for (File file : newFile.listFiles()) {
			if (file.isDirectory()) {
				if (ignoreScan.contains(file.getName()))
					continue;

				if (scanDirectory(file)) return true;
                continue;
			}

			if (!file.getName().endsWith(".yml")) continue;

			try {
				YamlConfigurationUtil.loadConfiguration(file);
			} catch (NullPointerException e) { // wtf?
				Main.getInstance().getLogger().log(Level.INFO, "Odd config found, ignoring it");
			} catch (ScannerException e) {
				if (e.getMessage().contains("deserialize"))
					continue;

				Main.getInstance().getLogger().log(Level.SEVERE, "Config failure detected!");
				Main.getInstance().getLogger().log(Level.SEVERE, "File: " + file.getName());
				Main.getInstance().getLogger().log(Level.SEVERE, "Usually the first information of the message gives you the location of the mistake. Just read the error and check the files.");
				Main.getInstance().getLogger().log(Level.SEVERE, "Message: \n" + e.getMessage());
				return true;
			}
		}

		return false;
	}

	public boolean hasFailed() {
		return this.failed;
	}
}