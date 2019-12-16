package de.cuuky.varo.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.config.config.ConfigSection;
import de.cuuky.varo.config.messages.ConfigMessages;
import de.cuuky.varo.utils.JavaUtils;

public class ConfigHandler {

	private static ConfigHandler instance;

	private File configFile;
	private boolean configExisted;
	private YamlConfiguration configCfg;

	private File messagesFile;
	private boolean messagesExisted;
	private YamlConfiguration messagesCfg;

	private ConfigHandler() {
		reload();
	}

	public static ConfigHandler getInstance() {
		if (instance == null) {
			instance = new ConfigHandler();
		}
		return instance;
	}

	public void reload() {
		loadConfigurations();

		load();

		testConfig();
	}

	/**
	 * Initalize the configurations
	 */
	private void loadConfigurations() {
		this.configFile = new File("plugins/Varo/config.yml");
		this.configExisted = configFile.exists();
		this.configCfg = YamlConfiguration.loadConfiguration(configFile);

		this.messagesFile = new File("plugins/Varo/messages.yml");
		this.messagesExisted = messagesFile.exists();
		this.messagesCfg = YamlConfiguration.loadConfiguration(messagesFile);
	}

	/**
	 * @return Every description of every ConfigEntry combined
	 */
	private String getConfigHeader() {
		String header = "Config Einstellungen \r\n" + "WARNUNG: DIE RICHTIGE CONFIG BEFINDET SICH UNTEN, NICHT DIE '#' VOR DEN EINTRÄGEN WEGNEHMEN!\n Hier ist die Beschreibung der Config:\n\n";
		String desc = "";
		for (ConfigSection section : ConfigSection.values()) {
			desc = desc + "\n----------- " + section.getName() + " -----------";

			for (ConfigEntry entry : section.getEntries()) {
				String description = JavaUtils.getArgsToString(entry.getDescription(), "\n  ");
				desc = desc + "\r\n" + " " + entry.getPath() + ":\n  " + description + "\n  Default-Value: " + entry.getDefaultValue() + "\r\n";
			}
		}
		return header + desc + "-------------------------";
	}

	private void save(File file, YamlConfiguration cfg) {
		try {
			cfg.save(file);
		} catch (IOException e) {
			System.out.println(Main.getConsolePrefix() + "Failed saving file " + file.getName());
		}
	}

	/**
	 * Method to test whether the entries in the Main Config are correct
	 */
	public void testConfig() {
		boolean shutdown = false;
		for (ConfigEntry entry : ConfigEntry.values()) {

			Class<?> dataTypeWanted = entry.getDefaultValue().getClass();
			Class<?> dataTypeInConfig = configCfg.get(entry.getFullPath()).getClass();

			if (dataTypeInConfig.equals(MemorySection.class)) {
				continue;
			}

			if (dataTypeInConfig.equals(Long.class) && dataTypeWanted.equals(Integer.class)) {
				continue;
			}

			if (!dataTypeWanted.equals(dataTypeInConfig)) {
				System.err.println(Main.getConsolePrefix() + "CONFIGFEHLER! Der Eintrag " + entry.getName() + " muss vom Datentyp \"" + dataTypeWanted.getSimpleName() + "\" sein, ist aber vom Datentyp \"" + dataTypeInConfig.getSimpleName() + "\".");
				shutdown = true;
			}
		}

		if (shutdown) {
			System.out.println(Main.getConsolePrefix() + "Das Plugin wird heruntergefahren, da Fehler in der Config existieren.");
			Bukkit.getServer().shutdown();
		}

		if (ConfigEntry.BACKPACK_SIZE.getValueAsInt() > 54) {
			System.err.println(Main.getConsolePrefix() + "CONFIGFEHLER! Die Größe des Backpacks darf nicht mehr als 54 betragen.");
			shutdown = true;
		}

		if (ConfigEntry.SESSIONS_PER_DAY.getValueAsInt() <= 0 && ConfigEntry.JOIN_AFTER_HOURS.getValueAsInt() <= 0 && ConfigEntry.PLAY_TIME.getValueAsInt() > 0) {
			System.err.println(Main.getConsolePrefix() + "CONFIGFEHLER! Wenn die Spielzeit nicht unendlich ist, muss ein JoinSystem aktiviert sein.");
			shutdown = true;
		}

		if (ConfigEntry.SESSIONS_PER_DAY.getValueAsInt() > 0 && ConfigEntry.JOIN_AFTER_HOURS.getValueAsInt() > 0) {
			System.err.println(Main.getConsolePrefix() + "CONFIGFEHLER! Es dürfen nicht beide JoinSysteme gleichzeitig aktiviert sein.");
			shutdown = true;
		}

		if (shutdown) {
			System.out.println(Main.getConsolePrefix() + "Das Plugin wird heruntergefahren, da Fehler in der Config existieren.");
			Bukkit.getServer().shutdown();
		}
	}

	/**
	 * Loads the ConfigType with the configs in the arguments
	 */
	private void loadConfig(YamlConfiguration cfg, File file, boolean configEntry, boolean existed) {
		boolean save = false;
		if (configEntry)
			for (ConfigEntry entry : ConfigEntry.values()) {
				if (cfg.get(entry.getFullPath()) == null)
					save = true;

				cfg.addDefault(entry.getFullPath(), entry.getDefaultValue());
			}
		else
			for (ConfigMessages message : ConfigMessages.values()) {
				if (cfg.get(message.getPath()) == null)
					save = true;

				cfg.addDefault(message.getPath(), message.getDefaultValue());
			}

		cfg.options().copyDefaults(true);

		if (configEntry)
			cfg.options().header(getConfigHeader());
		else
			cfg.options().header("Die Liste aller Placeholder steht auf der Seite");

		if (!existed) {
			save(file, cfg);
			return;
		}

		for (String key : cfg.getKeys(true)) {
			if (cfg.get(key) instanceof MemorySection)
				continue;

			try {
				if (!configEntry)
					ConfigMessages.getEntryByPath(key).setValue(String.valueOf(cfg.get(key)));
				else
					ConfigEntry.getEntryByPath(key).setValue(cfg.get(key), false);
			} catch (NullPointerException e) {
				cfg.set(key, null);
				save = true;
			}
		}

		if (save)
			save(file, cfg);
	}

	/**
	 * Main method to load the configs
	 */
	public void load() {
		loadConfig(configCfg, configFile, true, configExisted);
		loadConfig(messagesCfg, messagesFile, false, messagesExisted);
	}

	public void saveConfig() {
		try {
			configCfg.save(configFile);
		} catch (IOException e) {
			System.out.println(Main.getConsolePrefix() + "Failed saving file " + configFile.getName());
		}
	}

	public YamlConfiguration getConfigCfg() {
		return configCfg;
	}
}