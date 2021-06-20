package de.cuuky.varo.data.plugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.UnknownDependencyException;
import org.bukkit.plugin.java.JavaPlugin;

import de.cuuky.varo.Main;
import de.cuuky.varo.spigot.FileDownloader;

public class ExternalPluginLoader {

	private static Method getFileMethod;

	static {
		try {
			getFileMethod = JavaPlugin.class.getDeclaredMethod("getFile");
			getFileMethod.setAccessible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ExternalPluginLoader() {
		loadPlugins();
	}

	private void loadPlugins() {
		System.out.println(Main.getConsolePrefix() + "Checking for additional plugins to load...");
		boolean failed = false;
		for (DownloadPlugin dp : DownloadPlugin.values()) {
			if (!dp.shallLoad())
				continue;

			try {
				Class.forName(dp.getRequiredClassName());

				for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
					try {
						File file = (File) getFileMethod.invoke((JavaPlugin) plugin);
						if (file == null)
							continue;

						if (file.getName().equals(dp.getName() + ".jar")) {
							dp.setPlugin((JavaPlugin) plugin);
							dp.checkedAndLoaded();
						}
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			} catch (ClassNotFoundException | UnknownDependencyException e) {
				if (!loadPlugin(dp)) {
					failed = true;
					continue;
				}
			}
		}

		if (failed) {
			System.out.println(Main.getConsolePrefix() + "Beim Herunterladen / Initialisieren der Plugins ist ein Fehler aufgetreten.");
			System.out.println(Main.getConsolePrefix() + "Der Server wird nun heruntergefahren. Bitte danach fahre den Server wieder hoch.");
			Bukkit.getServer().shutdown();
		}

		System.out.println(Main.getConsolePrefix() + "Starting library update agent...");
		new PluginUpdateAgent(this);
	}

	private boolean loadPlugin(DownloadPlugin downloadPlugin) {
		File pluginFile = new File(downloadPlugin.getPath());
		if (!pluginFile.exists()) {
			System.out.println(Main.getConsolePrefix() + "Das " + downloadPlugin.getName() + "-Plugin wird automatisch heruntergeladen...");
			if (!downloadLibraryPlugin(downloadPlugin.getId(), downloadPlugin.getPath()))
				return false;
		}

		try {
			System.out.println(Main.getConsolePrefix() + downloadPlugin.getName() + " wird nun geladen...");

			Plugin plugin = Bukkit.getPluginManager().loadPlugin(new File(downloadPlugin.getPath()));
			Bukkit.getPluginManager().enablePlugin(plugin);

			System.out.println(Main.getConsolePrefix() + downloadPlugin.getName() + " wurde erfolgreich geladen!");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	private boolean downloadLibraryPlugin(int resourceId, String dataName) {
		return downloadAdditionalPlugin(resourceId, dataName, false);
	}

	public boolean downloadAdditionalPlugin(int resourceId, String dataName, boolean enablePlugin) {
		try {
			FileDownloader fd = new FileDownloader("http://api.spiget.org/v2/resources/" + resourceId + "/download", dataName);

			System.out.println(Main.getConsolePrefix() + "Downloade plugin " + dataName + "...");

			fd.startDownload();

			System.out.println(Main.getConsolePrefix() + "Donwload von " + dataName + " erfolgreich abgeschlossen!");

			if (enablePlugin) {
				System.out.println(Main.getConsolePrefix() + dataName + " wird nun geladen...");
				Bukkit.getPluginManager().enablePlugin(Bukkit.getPluginManager().loadPlugin(new File("plugins/" + dataName)));
				System.out.println(Main.getConsolePrefix() + dataName + " wurde erfolgreich geladen!");
			}
			return true;
		} catch (IOException | UnknownDependencyException | InvalidPluginException | InvalidDescriptionException e) {
			System.out.println(Main.getConsolePrefix() + "Es gab einen kritischen Fehler beim Download eines Plugins.");
			System.out.println(Main.getConsolePrefix() + "---------- Stack Trace ----------");
			e.printStackTrace();
			System.out.println(Main.getConsolePrefix() + "---------- Stack Trace ----------");
			return false;
		}
	}
}