package de.cuuky.varo.configuration.configurations.config;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

import de.cuuky.cfw.configuration.YamlConfigurationUtil;

public abstract class BoardConfig {

	protected File file;
	protected YamlConfiguration configuration;

	public BoardConfig(String path) {
		this.file = new File(path);

		boolean exists = file.exists();
		if (exists)
			this.configuration = YamlConfigurationUtil.loadConfiguration(this.file);

		if (!exists || shouldReset()) {
			this.configuration = new YamlConfiguration();
			this.configuration.options().copyDefaults(true);

			load();

			YamlConfigurationUtil.save(this.configuration, this.file);
		} else 
			load();
	}

	protected abstract boolean shouldReset();

	protected abstract void load();
}