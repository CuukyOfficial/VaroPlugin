package de.cuuky.varo.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.config.config.ConfigSection;
import de.cuuky.varo.config.messages.ConfigMessages;
import de.cuuky.varo.utils.Utils;

public class ConfigHandler {

	private File configFile;
	private boolean configExisted;
	private YamlConfiguration configCfg;

	private File messagesFile;
	private boolean messagesExisted;
	private YamlConfiguration messagesCfg;

	public ConfigHandler() {
		reload();
	}

	public void reload() {
		loadConfigurations();

		load();
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
	 * Main method to load the configs
	 */
	public void load() {
		loadConfig(configCfg, configFile, true, configExisted);
		loadConfig(messagesCfg, messagesFile, false, messagesExisted);
	}

	/**
	 * Loads the ConfigType with the configs in the arguments
	 */
	private void loadConfig(YamlConfiguration cfg, File file, boolean configEntry, boolean existed) {
		boolean save = false;
		if(configEntry)
			for(ConfigEntry entry : ConfigEntry.values()) {
				if(cfg.get(entry.getPath()) == null)
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

	public void saveConfig() {
		try {
			configCfg.save(configFile);
		} catch(IOException e) {
			System.out.println(Main.getConsolePrefix() + "Failed saving file " + configFile.getName());
		}
	}

	/**
	 * @return Every description of every ConfigEntry combined
	 */
	private String getConfigHeader() {
		String header = "Config Einstellungen \r\n" + "WARNUNG: DIE RICHTIGE CONFIG BEFINDET SICH UNTEN, NICHT DIE '#' VOR DEN EINTRAEGEN WEGNEHMEN!\n Hier ist die Beschreibung der Config:\n\n";
		String desc = "";
		for(ConfigSection section : ConfigSection.values()) {
			desc = desc + "\n----------- " + section.getName() + " -----------";

			for(ConfigEntry entry : section.getEntries()) {
				String description = Utils.getArgsToString(entry.getDescription(), "\n  ");
				desc = desc + "\r\n" + " " + entry.getPath() + ":\n  " + description + "\n  Default-Value: " + entry.getDefaultValue() + "\r\n";
			}
		}
		return header + desc + "-------------------------";
	}
}