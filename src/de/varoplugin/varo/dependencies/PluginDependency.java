package de.varoplugin.varo.dependencies;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import de.varoplugin.varo.VaroPlugin;

class PluginDependency extends Dependency {

	PluginDependency(String name, String link, String hash) {
		super(name, link, hash);
	}

	PluginDependency(String name, String link, String hash, LoadPolicy loadPolicy) {
		super(name, link, hash, loadPolicy);
	}

	@Override
	protected void init(VaroPlugin varo) {
		try {
			Plugin plugin = Bukkit.getPluginManager().loadPlugin(this.getFile());
			Bukkit.getPluginManager().enablePlugin(plugin);
		} catch (Exception e) {
			varo.getLogger().log(Level.SEVERE, "Failed to load plugin!", e);
		}
	}
}
