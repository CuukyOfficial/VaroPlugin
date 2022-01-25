package de.cuuky.varo.configuration.configurations.config;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

import de.cuuky.cfw.configuration.YamlConfigurationUtil;

public abstract class BoardConfig {

	protected File file;
	protected YamlConfiguration configuration;

	public BoardConfig(String path) {
		this.file = new File(path);

		if (file.exists())
			this.configuration = YamlConfigurationUtil.loadConfiguration(this.file);
		else
			this.configuration = new YamlConfiguration();

		load();
		
		this.configuration.options().copyDefaults(true);

		YamlConfigurationUtil.save(this.configuration, this.file);
	}

	protected abstract void load();
}