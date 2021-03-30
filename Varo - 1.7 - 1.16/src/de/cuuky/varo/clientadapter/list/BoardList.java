package de.cuuky.varo.clientadapter.list;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

public abstract class BoardList {

	private String path;

	protected File file;
	protected YamlConfiguration configuration;

	public BoardList(String path) {
		this.path = path;
	}

	public void update() {
		this.file = new File(path);
		this.configuration = YamlConfiguration.loadConfiguration(this.file);

		this.configuration.options().copyDefaults(true);

		updateList();

		if (!file.exists())
			try {
				this.configuration.save(this.file);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	protected abstract void updateList();
}