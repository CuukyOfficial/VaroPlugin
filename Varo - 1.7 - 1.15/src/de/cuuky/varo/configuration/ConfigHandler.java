package de.cuuky.varo.configuration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.SectionConfiguration;
import de.cuuky.varo.configuration.configurations.SectionEntry;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.config.ConfigSettingSection;
import de.cuuky.varo.configuration.configurations.messages.ConfigMessageSection;
import de.cuuky.varo.utils.JavaUtils;

public class ConfigHandler {

	private static final String CONFIG_PATH = "plugins/Varo/config", MESSAGE_PATH = "plugins/Varo/messages";

	private HashMap<String, YamlConfiguration> configurations;
	private HashMap<String, File> files;

	public ConfigHandler() {
		configurations = new HashMap<>();
		files = new HashMap<>();
		
		loadConfigurations();
	}
	
	private void loadConfigurations() {
		loadConfiguration(ConfigSettingSection.values(), CONFIG_PATH);
		loadConfiguration(ConfigMessageSection.values(), MESSAGE_PATH);
		
		testConfig();
	}
	
	private void loadConfiguration(SectionConfiguration[] sections, String filepath) {
		for(SectionConfiguration section : sections) {
			File file = new File(filepath, section.getName().toLowerCase() + ".yml");
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

			boolean save = false;
			config.options().copyDefaults(true);
			
			String header = getConfigHeader(section);
			if(!header.equals(config.options().header())) {
				config.options().header(getConfigHeader(section));
				save = true;
			}

			ArrayList<String> entryNames = new ArrayList<>();
			for(SectionEntry entry : section.getEntries()) {
				if(!config.contains(entry.getPath()))
					save = true;

				config.addDefault(entry.getPath(), entry.getDefaultValue());
				entry.setValue(config.get(entry.getPath()));
				entryNames.add(entry.getPath());
			}

			for(String path : config.getKeys(true)) {		
				if(entryNames.contains(path) || config.isConfigurationSection(path))
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
	}

	private void saveFile(YamlConfiguration config, File file) {
		try {
			config.save(file);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void saveValue(ConfigSetting entry) {
		YamlConfiguration config = configurations.get(entry.getSection().getName());
		config.set(entry.getPath(), entry.getValue());

		saveFile(config, files.get(entry.getSection().getName()));
	}

	public void reload() {
		configurations.clear();
		files.clear();

		loadConfigurations();
	}

	/**
	 * @return Every description of every ConfigEntry combined
	 */
	private String getConfigHeader(SectionConfiguration section) {
		String header = "WARNUNG: DIE RICHTIGE CONFIG BEFINDET SICH UNTEN, NICHT DIE '#' VOR DEN EINTRÄGEN WEGNEHMEN!\n Hier ist die Beschreibung der Config:";
		String desc = "\n----------- " + section.getName() + " -----------" + "\nBeschreibung: " + section.getDescription() + "\n";

		for(SectionEntry entry : section.getEntries()) {
			if(entry.getDescription() == null)
				break;
			
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
		if(ConfigSetting.BACKPACK_PLAYER_SIZE.getValueAsInt() > 54 || ConfigSetting.BACKPACK_TEAM_SIZE.getValueAsInt() > 54) {
			System.err.println(Main.getConsolePrefix() + "CONFIGFEHLER! Die Groesse des Rucksackes darf nicht mehr als 54 betragen.");
			shutdown = true;
		}

		if(ConfigSetting.SESSIONS_PER_DAY.getValueAsInt() <= 0 && ConfigSetting.JOIN_AFTER_HOURS.getValueAsInt() <= 0 && ConfigSetting.PLAY_TIME.getValueAsInt() > 0) {
			System.err.println(Main.getConsolePrefix() + "CONFIGFEHLER! Wenn die Spielzeit nicht unendlich ist, muss ein JoinSystem aktiviert sein.");
			shutdown = true;
		}

		if(ConfigSetting.SESSIONS_PER_DAY.getValueAsInt() > 0 && ConfigSetting.JOIN_AFTER_HOURS.getValueAsInt() > 0) {
			System.err.println(Main.getConsolePrefix() + "CONFIGFEHLER! Es duerfen nicht beide JoinSysteme gleichzeitig aktiviert sein.");
			shutdown = true;
		}

		if(shutdown) {
			System.out.println(Main.getConsolePrefix() + "Das Plugin wird heruntergefahren, da Fehler in der Config existieren.");
			Bukkit.getServer().shutdown();
		}
	}
}