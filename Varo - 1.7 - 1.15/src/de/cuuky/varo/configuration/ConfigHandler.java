package de.cuuky.varo.configuration;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.config.ConfigEntry;
import de.cuuky.varo.configuration.config.ConfigSection;
import de.cuuky.varo.configuration.messages.ConfigMessages;
import de.cuuky.varo.utils.JavaUtils;

public class ConfigHandler {

	private static ConfigHandler instance;

	private YamlConfiguration configCfg;
	private boolean configExisted;
	private File configFile;

	private YamlConfiguration messagesCfg;
	private boolean messagesExisted;
	private File messagesFile;

	private ConfigHandler() {
		reload();
	}

	/**
	 * @return Every description of every ConfigEntry combined
	 */
	private String getConfigHeader() {
		String header = "Config Einstellungen \r\n" + "WARNUNG: DIE RICHTIGE CONFIG BEFINDET SICH UNTEN, NICHT DIE '#' VOR DEN EINTRÄGEN WEGNEHMEN!\n Hier ist die Beschreibung der Config:\n\n";
		String desc = "";
		for(ConfigSection section : ConfigSection.values()) {
			desc = desc + "\n----------- " + section.getName() + " -----------";

			for(ConfigEntry entry : section.getEntries()) {
				String description = JavaUtils.getArgsToString(entry.getDescription(), "\n  ");
				desc = desc + "\r\n" + " " + entry.getPath() + ":\n  " + description + "\n  Default-Value: " + entry.getDefaultValue() + "\r\n";
			}
		}
		return header + desc + "-------------------------";
	}

	/**
	 * Loads the ConfigType with the configs in the arguments
	 */
	private void loadConfig(YamlConfiguration cfg, File file, boolean configEntry, boolean existed) {
		boolean save = false;
		if(configEntry)
			for(ConfigEntry entry : ConfigEntry.values()) {
				if(cfg.get(entry.getFullPath()) == null)
					save = true;

				cfg.addDefault(entry.getFullPath(), entry.getDefaultValue());
			}
		else
			for(ConfigMessages message : ConfigMessages.values()) {
				if(cfg.get(message.getPath()) == null)
					save = true;

				cfg.addDefault(message.getPath(), message.getDefaultValue());
			}

		cfg.options().copyDefaults(true);

		if(configEntry)
			cfg.options().header(getConfigHeader());
		else
			cfg.options().header("Die Liste aller Placeholder steht auf der Seite");

		if(!existed) {
			save(file, cfg);
			return;
		}

		for(String key : cfg.getKeys(true)) {
			if(cfg.get(key) instanceof MemorySection)
				continue;

			try {
				if(!configEntry)
					ConfigMessages.getEntryByPath(key).setValue(String.valueOf(cfg.get(key)));
				else
					ConfigEntry.getEntryByPath(key).setValue(cfg.get(key), false);
			} catch(NullPointerException e) {
				cfg.set(key, null);
				save = true;
			}
		}

		if(save)
			save(file, cfg);
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

	private void save(File file, YamlConfiguration cfg) {
		try {
			cfg.save(file);
		} catch(IOException e) {
			System.out.println(Main.getConsolePrefix() + "Failed saving file " + file.getName());
		}
	}

	public YamlConfiguration getConfigCfg() {
		return configCfg;
	}

	/**
	 * Main method to load the configs
	 */
	public void load() {
		loadConfig(configCfg, configFile, true, configExisted);
		loadConfig(messagesCfg, messagesFile, false, messagesExisted);
	}

	public void reload() {
		loadConfigurations();

		load();

		testConfig();
	}

	public void saveConfig() {
		try {
			configCfg.save(configFile);
		} catch(IOException e) {
			System.out.println(Main.getConsolePrefix() + "Failed saving file " + configFile.getName());
		}
	}

	/**
	 * Method to test whether the entries in the Main Config are correct
	 */
	public void testConfig() {
		boolean shutdown = false;
		if(ConfigEntry.BACKPACK_PLAYER_SIZE.getValueAsInt() > 54 || ConfigEntry.BACKPACK_TEAM_SIZE.getValueAsInt() > 54) {
			System.err.println(Main.getConsolePrefix() + "CONFIGFEHLER! Die Größe des Rucksackes darf nicht mehr als 54 betragen.");
			shutdown = true;
		}

		if(ConfigEntry.SESSIONS_PER_DAY.getValueAsInt() <= 0 && ConfigEntry.JOIN_AFTER_HOURS.getValueAsInt() <= 0 && ConfigEntry.PLAY_TIME.getValueAsInt() > 0) {
			System.err.println(Main.getConsolePrefix() + "CONFIGFEHLER! Wenn die Spielzeit nicht unendlich ist, muss ein JoinSystem aktiviert sein.");
			shutdown = true;
		}

		if(ConfigEntry.SESSIONS_PER_DAY.getValueAsInt() > 0 && ConfigEntry.JOIN_AFTER_HOURS.getValueAsInt() > 0) {
			System.err.println(Main.getConsolePrefix() + "CONFIGFEHLER! Es dürfen nicht beide JoinSysteme gleichzeitig aktiviert sein.");
			shutdown = true;
		}

		if(shutdown) {
			System.out.println(Main.getConsolePrefix() + "Das Plugin wird heruntergefahren, da Fehler in der Config existieren.");
			Bukkit.getServer().shutdown();
		}
	}

	public static ConfigHandler getInstance() {
		if(instance == null) {
			instance = new ConfigHandler();
		}
		return instance;
	}
}