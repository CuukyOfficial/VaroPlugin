package de.varoplugin.varo.config;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Level;

import com.google.common.collect.Multimap;

import de.varoplugin.varo.Dependencies;
import de.varoplugin.varo.VaroPlugin;
import de.varoplugin.varo.api.config.Config;
import de.varoplugin.varo.api.config.ConfigCategory;
import de.varoplugin.varo.api.config.ConfigEntry;
import de.varoplugin.varo.api.config.ConfigException;

public class ClassLoaderConfig implements Config {

	private static Class<?> configClass;

	protected Config config;

	/**
	 * This constructor is just for testing
	 * 
	 * @param path The file path
	 */
	public ClassLoaderConfig(String path) {
		this.config = new ConfigImpl(path);
	}

	public ClassLoaderConfig(VaroPlugin plugin, File pluginFile, String path) {
		try {
			if (configClass == null) {
				// All classes except for ConfigImpl and SnakeYAML should still be loaded via the class loader of this class (Bukkit's ClassLoader)
				ClassLoader parentClassLoader = new ClassLoader(this.getClass().getClassLoader()) {

					@Override
					protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
						if (name.equals("de.varoplugin.varo.config.ConfigImpl") || name.startsWith("org.yaml.snakeyaml."))
							throw new ClassNotFoundException();
						return super.loadClass(name, resolve);
					}
				};

				// Create URLClassLoader and load the ConfigImpl class using the newly created URLClassLoader
				URLClassLoader classLoader = new URLClassLoader(new URL[] {Dependencies.SIMPLE_YAML.getUrl(), Dependencies.SNAKE_YAML.getUrl(), pluginFile.toURI().toURL()}, parentClassLoader);
				configClass = Class.forName("de.varoplugin.varo.config.ConfigImpl", false, classLoader);
			}

			// Create new ConfigImpl instance
			this.config = (Config) configClass.getConstructor(String.class).newInstance(path);
		} catch (Throwable t) {
			plugin.getLogger().log(Level.SEVERE, "Unable to load config", t);
			return;
		}
	}

	@Override
	public void load() throws ConfigException {
		this.config.load();
	}

	@Override
	public void dump() throws ConfigException {
		this.config.dump();
	}

	@Override
	public void delete() throws ConfigException {
		this.config.delete();
	}

	@Override
	public void addConfigEntry(ConfigEntry<?> configEntry) {
		this.config.addConfigEntry(configEntry);
	}

	@Override
	public Multimap<ConfigCategory, ConfigEntry<?>> getConfigEntries() {
		return this.config.getConfigEntries();
	}

	@Override
	public ConfigEntry<?> getEntry(ConfigCategory category, String path) {
		return this.config.getEntry(category, path);
	}
}
