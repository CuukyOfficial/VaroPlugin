package de.cuuky.varo.data.plugin;

import java.io.File;
import java.util.function.Supplier;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import de.cuuky.varo.app.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;

public class PluginLibrary extends Library {

	public PluginLibrary(String name, String className, String link, Supplier<Boolean> shouldLoad) {
		super(name, className, link, shouldLoad);
	}

	public PluginLibrary(String name, String className, String link, ConfigSetting... configSettings) {
		super(name, className, link, configSettings);
	}

	public PluginLibrary(String name, String className, String link) {
		super(name, className, link);
	}

	@Override
	protected void init(File jar) {
		try {
			Plugin plugin = Bukkit.getPluginManager().loadPlugin(jar);
			Bukkit.getPluginManager().enablePlugin(plugin);
		} catch (Exception e) {
			System.out.println(Main.getConsolePrefix() + "Failed to load plugin!");
			e.printStackTrace();
			Main.getInstance().fail();
			return;
		}
	}
}
