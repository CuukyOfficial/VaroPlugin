package de.cuuky.varo.data.plugin;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;

import de.cuuky.varo.Main;
import de.cuuky.varo.spigot.updater.VaroUpdateResultSet.UpdateResult;
import de.cuuky.varo.spigot.updater.VaroUpdater;

public class PluginUpdateAgent {

	private HashMap<DownloadPlugin, VaroUpdater> updater;
	private int scheduler;
	private PluginLoader loader;

	public PluginUpdateAgent(PluginLoader loader) {
		this.updater = new HashMap<>();
		this.loader = loader;

		for (DownloadPlugin plugin : DownloadPlugin.values())
			if (plugin.getPlugin() != null)
				this.updater.put(plugin, new VaroUpdater(plugin.getId(), plugin.getPlugin().getDescription().getVersion(), null));

		startAgent();
	}

	private void startAgent() {
		scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				ArrayList<DownloadPlugin> needUpdate = new ArrayList<>();
				for (DownloadPlugin plugin : updater.keySet()) {
					VaroUpdater pluginUpdater = updater.get(plugin);
					if (pluginUpdater.getLastResult() == null)
						return;

					if (pluginUpdater.getLastResult().getUpdateResult() == UpdateResult.UPDATE_AVAILABLE)
						needUpdate.add(plugin);
				}

				if (!needUpdate.isEmpty())
					updatePlugins(needUpdate);
				else {
					System.out.println(Main.getConsolePrefix() + "Updater: All libraries are up to date!");
					Bukkit.getScheduler().cancelTask(scheduler);
				}
			}
		}, 20, 20);
	}

	private void updatePlugins(ArrayList<DownloadPlugin> needUpdate) {
		Main.getDataManager().setDoSave(false);

		for (DownloadPlugin plugin : needUpdate) {
			Bukkit.getPluginManager().disablePlugin(plugin.getPlugin());

			System.out.println(Main.getConsolePrefix() + "Downloading update of library " + plugin.getName() + "...");
			this.loader.downloadAdditionalPlugin(plugin.getId(), plugin.getPath(), false);
		}

		System.out.println(Main.getConsolePrefix() + "Shutting down server...");
		Bukkit.getServer().shutdown();
	}
}