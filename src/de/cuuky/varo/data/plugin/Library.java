package de.cuuky.varo.data.plugin;

import de.cuuky.varo.configuration.configurations.config.ConfigSetting;

public abstract class Library {

	private final String name;
	private final String className;
	private final ConfigSetting[] configSettings;

	public Library(String name, String className, ConfigSetting... configSettings) {
		this.name = name;
		this.className = className;
		this.configSettings = configSettings;
	}

	public boolean shouldLoad() {
		try {
			Class.forName(this.className);
			// already loaded
			return false;
		} catch (ClassNotFoundException e) {
			for (ConfigSetting setting : this.configSettings)
				if (setting.getValueAsBoolean())
					return true;
			// not required
			return false;
		}
	}

	public abstract void load();

	public String getName() {
		return this.name;
	}
}
