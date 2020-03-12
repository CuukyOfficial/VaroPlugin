package de.cuuky.varo.configuration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.config.ConfigEntry;
import de.cuuky.varo.configuration.config.ConfigSection;
import de.cuuky.varo.utils.JavaUtils;

public class ConfigHandler {

	private static final String CONFIG_PATH = "plugins/Varo/config", MESSAGE_PATH = CONFIG_PATH + "/messages";

	private HashMap<String, YamlConfiguration> configurations;
	private HashMap<String, File> files;

	public ConfigHandler() {
		configurations = new HashMap<>();
		files = new HashMap<>();
		MESSAGE_PATH.charAt(0);

		loadConfigurations();
	}

	private void loadConfigurations() {
		for(ConfigSection section : ConfigSection.values()) {
			File file = new File(CONFIG_PATH, section.getName().toLowerCase() + ".yml");
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

			boolean save = false;
			config.options().copyDefaults(true);
			
			String header = getConfigHeader(section);
			if(!header.equals(config.options().header())) {
				config.options().header(getConfigHeader(section));
				save = true;
			}

			for(ConfigEntry entry : section.getEntries()) {
				if(!config.contains(entry.getPath()))
					save = true;

				config.addDefault(entry.getPath(), entry.getDefaultValue());

				entry.setValue(config.get(entry.getPath()), false);
			}

			for(String path : config.getKeys(true)) {
				if(ConfigEntry.getEntryByPath(path) != null || config.isConfigurationSection(path))
					continue;

				System.out.println(Main.getConsolePrefix() + "Removed " + path + " because it was removed from the plugin");
				config.set(path, null);
				save = true;
			}

			if(save)
				saveFile(config, file);

			files.put(section.getName(), file);
			configurations.put(section.getName(), config);
		}

		testConfig();
	}

	private void saveFile(YamlConfiguration config, File file) {
		try {
			config.save(file);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void saveValue(ConfigEntry entry) {
		YamlConfiguration config = configurations.get(entry.getSection().getPath());
		config.set(entry.getPath(), entry.getValue());

		saveFile(config, files.get(entry.getSection().getPath()));
	}

	public void reload() {
		configurations.clear();
		files.clear();

		loadConfigurations();
	}

	/**
	 * @return Every description of every ConfigEntry combined
	 */
	private String getConfigHeader(ConfigSection section) {
		String header = "WARNUNG: DIE RICHTIGE CONFIG BEFINDET SICH UNTEN, NICHT DIE '#' VOR DEN EINTRÄGEN WEGNEHMEN!\n Hier ist die Beschreibung der Config:";
		String desc = "\n----------- " + section.getName() + " -----------" + "\nBeschreibung: " + section.getDescription() + "\n";

		for(ConfigEntry entry : section.getEntries()) {
			String description = JavaUtils.getArgsToString(entry.getDescription(), "\n  ");
			desc = desc + "\r\n" + " " + entry.getPath() + ":\n  " + description + "\n  Default-Value: " + entry.getDefaultValue() + "\r\n";
		}

		return header + desc + "-------------------------\n";
	}

	/**
	 * Method to test whether the entries in the Main Config are correct
	 */
	public void testConfig() {
		boolean shutdown = false;
		if(ConfigEntry.BACKPACK_PLAYER_SIZE.getValueAsInt() > 54 || ConfigEntry.BACKPACK_TEAM_SIZE.getValueAsInt() > 54) {
			System.err.println(Main.getConsolePrefix() + "CONFIGFEHLER! Die Groesse des Rucksackes darf nicht mehr als 54 betragen.");
			shutdown = true;
		}

		if(ConfigEntry.SESSIONS_PER_DAY.getValueAsInt() <= 0 && ConfigEntry.JOIN_AFTER_HOURS.getValueAsInt() <= 0 && ConfigEntry.PLAY_TIME.getValueAsInt() > 0) {
			System.err.println(Main.getConsolePrefix() + "CONFIGFEHLER! Wenn die Spielzeit nicht unendlich ist, muss ein JoinSystem aktiviert sein.");
			shutdown = true;
		}

		if(ConfigEntry.SESSIONS_PER_DAY.getValueAsInt() > 0 && ConfigEntry.JOIN_AFTER_HOURS.getValueAsInt() > 0) {
			System.err.println(Main.getConsolePrefix() + "CONFIGFEHLER! Es duerfen nicht beide JoinSysteme gleichzeitig aktiviert sein.");
			shutdown = true;
		}

		if(shutdown) {
			System.out.println(Main.getConsolePrefix() + "Das Plugin wird heruntergefahren, da Fehler in der Config existieren.");
			Bukkit.getServer().shutdown();
		}
	}
}