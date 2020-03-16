package de.cuuky.varo.data.plugin;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.UnknownDependencyException;

import de.cuuky.varo.Main;
import de.cuuky.varo.spigot.FileDownloader;

public class PluginLoader {

	public PluginLoader() {
		loadPlugins();
	}

	private void loadPlugins() {
		System.out.println(Main.getConsolePrefix() + "Checking for additional plugins to load...");
		boolean failed = false;
		for(DownloadPlugin dp : DownloadPlugin.values()) {
			if(!dp.shallLoad())
				continue;
			
			try {
				Class.forName(dp.getRequiredClassName());
			} catch(ClassNotFoundException e) {
				System.out.println(Main.getConsolePrefix() + "Das " + dp.getName() + "-Plugin wird automatisch heruntergeladen...");
				if(!loadAdditionalPlugin(dp.getId(), dp.getName() + ".jar")) {
					failed = true;
					continue;
				}
			}
			
			dp.checkedAndLoaded();
		}

		if(failed) {
			System.out.println(Main.getConsolePrefix() + "Beim Herunterladen / Initialisieren der Plugins ist ein Fehler aufgetreten.");
			System.out.println(Main.getConsolePrefix() + "Der Server wird nun heruntergefahren. Bitte danach fahre den Server wieder hoch.");
			Bukkit.getServer().shutdown();
		}
	}

	public boolean loadAdditionalPlugin(int resourceId, String dataName) {
		try {
			FileDownloader fd = new FileDownloader("http://api.spiget.org/v2/resources/" + resourceId + "/download", "plugins/" + dataName);

			System.out.println(Main.getConsolePrefix() + "Downloade plugin " + dataName + "...");

			fd.startDownload();

			System.out.println(Main.getConsolePrefix() + "Donwload von " + dataName + " erfolgreich abgeschlossen!");

			System.out.println(Main.getConsolePrefix() + dataName + " wird nun geladen...");
			Bukkit.getPluginManager().enablePlugin(Bukkit.getPluginManager().loadPlugin(new File("plugins/" + dataName)));
			System.out.println(Main.getConsolePrefix() + dataName + " wurde erfolgreich geladen!");
			return true;
		} catch(IOException | UnknownDependencyException | InvalidPluginException | InvalidDescriptionException e) {
			System.out.println(Main.getConsolePrefix() + "Es gab einen kritischen Fehler beim Download eines Plugins.");
			System.out.println(Main.getConsolePrefix() + "---------- Stack Trace ----------");
			e.printStackTrace();
			System.out.println(Main.getConsolePrefix() + "---------- Stack Trace ----------");
			return false;
		}
	}
}