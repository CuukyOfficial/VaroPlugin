package de.cuuky.varo.entity.player;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

import de.cuuky.cfw.configuration.YamlConfigurationUtil;

public abstract class BoardList {

	private String path;

	protected File file;
	protected YamlConfiguration configuration;

	public BoardList(String path) {
		this.path = path;
	}

	public void update() {
		this.file = new File(path);
		this.configuration = YamlConfigurationUtil.loadConfiguration(this.file);

		this.configuration.options().copyDefaults(true);

		updateList();

		if (!file.exists())
			YamlConfigurationUtil.save(this.configuration, this.file);
	}

	protected abstract void updateList();
}