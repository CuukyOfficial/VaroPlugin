package de.cuuky.varo.configuration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import com.google.common.io.Files;

import de.cuuky.cfw.utils.JavaUtils;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.SectionConfiguration;
import de.cuuky.varo.configuration.configurations.SectionEntry;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.config.ConfigSettingSection;

public class ConfigHandler {

	private static final String VARO_DIR = "plugins/Varo/", CONFIG_PATH = VARO_DIR + "config";

	private HashMap<String, YamlConfiguration> configurations;
	private HashMap<String, File> files;
	private boolean legacyFound;

	public ConfigHandler() {
		this.configurations = new HashMap<>();
		this.files = new HashMap<>();
		this.legacyFound = false;

		loadConfigurations();
	}

	private void loadConfigurations() {
		loadConfiguration(ConfigSettingSection.values(), CONFIG_PATH);
		// loadConfiguration(ConfigMessageSection.values(), MESSAGE_PATH);

		if (legacyFound)
			moveLegacyFiles();

		testConfig();
	}

	private void loadConfiguration(SectionConfiguration[] sections, String filepath) {
		checkLegacyConfiguration(sections, filepath);

		for (SectionConfiguration section : sections) {
			if (configurations.containsKey(section.getFolder() + "/" + section.getName())) {
				System.out.println(Main.getConsolePrefix() + "PAUL NEIN");
				Bukkit.getServer().shutdown();
			}

			File file = new File(filepath, section.getName().toLowerCase() + ".yml");
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

			boolean save = false;
			config.options().copyDefaults(true);

			String header = getConfigHeader(section);
			if (!header.equals(config.options().header())) {
				config.options().header(header);
				save = true;
			}

			for (SectionEntry entry : section.getEntries()) {
				if (!config.contains(entry.getPath()))
					save = true;

				config.addDefault(entry.getPath(), entry.getDefaultValue());
				entry.setValue(config.get(entry.getPath()));
			}

			for (String path : config.getKeys(true)) {
				if (section.getEntry(path) != null || config.isConfigurationSection(path))
					continue;

				System.out.println(Main.getConsolePrefix() + "Removed " + path + " in " + section.getFolder() + "/" + file.getName() + " because it was removed from the plugin");
				config.set(path, null);
				save = true;
			}

			if (save)
				saveFile(config, file);

			files.put(section.getFolder() + "/" + section.getName(), file);
			configurations.put(section.getFolder() + "/" + section.getName(), config);
		}
	}

	private void moveLegacyFiles() {
		String legacyDirName = VARO_DIR + "legacy/";
		File legacyDir = new File(legacyDirName);
		if (!legacyDir.isDirectory())
			legacyDir.mkdirs();

		for (File file : new File(VARO_DIR).listFiles()) {
			if (!file.isFile())
				continue;

			try {
				Files.copy(file, new File(legacyDirName + "legacy_" + file.getName()));

				file.delete();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void checkLegacyConfiguration(SectionConfiguration[] sections, String filepath) {
		File file = new File(filepath + ".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

		if (!file.exists())
			return;

		System.out.println(Main.getConsolePrefix() + "Found legacy configuration! Loading " + file.getName() + "...");
		for (SectionConfiguration section : sections) {
			File sectionFile = new File(filepath, section.getName().toLowerCase() + ".yml");
			YamlConfiguration sectionConfig = YamlConfiguration.loadConfiguration(sectionFile);

			for (SectionEntry entry : section.getEntries()) {
				if (!config.contains(entry.getFullPath()))
					continue;

				entry.setValue(config.get(entry.getFullPath()));
				sectionConfig.set(entry.getPath(), entry.getValue());
			}

			try {
				sectionConfig.save(sectionFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		this.legacyFound = true;
	}

	private void saveFile(YamlConfiguration config, File file) {
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveValue(SectionEntry entry) {
		YamlConfiguration config = configurations.get(entry.getSection().getFolder() + "/" + entry.getSection().getName());
		config.set(entry.getPath(), entry.getValue());

		saveFile(config, files.get(entry.getSection().getFolder() + "/" + entry.getSection().getName()));
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
		String header = "WARNUNG: DIE RICHTIGE CONFIG BEFINDET SICH UNTEN, NICHT DIE '#' VOR DEN EINTRAEGEN WEGNEHMEN!\n Hier ist die Beschreibung der Config:";
		String desc = "\n----------- " + section.getName() + " -----------" + "\nBeschreibung: " + section.getDescription() + "\n";

		for (SectionEntry entry : section.getEntries()) {
			if (entry.getDescription() == null)
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
		if (ConfigSetting.BACKPACK_PLAYER_SIZE.getValueAsInt() > 54 || ConfigSetting.BACKPACK_TEAM_SIZE.getValueAsInt() > 54) {
			System.err.println(Main.getConsolePrefix() + "CONFIGFEHLER! Die Groesse des Rucksackes darf nicht mehr als 54 betragen.");
			shutdown = true;
		}

		if (shutdown) {
			System.out.println(Main.getConsolePrefix() + "Das Plugin wird heruntergefahren, da Fehler in der Config existieren.");
			Bukkit.getServer().shutdown();
		}
	}

	public static String getConfigPath() {
		return CONFIG_PATH;
	}
}