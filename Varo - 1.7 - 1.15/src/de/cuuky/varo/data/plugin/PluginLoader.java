package de.cuuky.varo.data.plugin;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.UnknownDependencyException;

import de.cuuky.varo.Main;
import de.cuuky.varo.bot.discord.VaroDiscordBot;
import de.cuuky.varo.bot.telegram.VaroTelegramBot;
import de.cuuky.varo.configuration.config.ConfigEntry;
import de.cuuky.varo.listener.PermissionSendListener;
import de.cuuky.varo.spigot.FileDownloader;

public class PluginLoader {
	
	private static final int LABYMOD_ID = 52423, DISCORDBOT_ID = 66778, TELEGRAM_ID = 66823;
	
	public PluginLoader() {
		loadPlugins();
	}
	
	private void loadPlugins() {
		boolean discordNewDownloadFailed = false;
		if(ConfigEntry.DISCORDBOT_ENABLED.getValueAsBoolean()) {
			try {
				VaroDiscordBot.getClassName();
			} catch(NoClassDefFoundError | BootstrapMethodError ef) {
				System.out.println(Main.getConsolePrefix() + "Das Discordbot-Plugin wird automatisch heruntergeladen...");
				discordNewDownloadFailed = !loadAdditionalPlugin(DISCORDBOT_ID, "Discordbot.jar");
			}
		}

		boolean telegramNewDownloadFailed = false;
		if(ConfigEntry.TELEGRAM_ENABLED.getValueAsBoolean()) {
			try {
				VaroTelegramBot.getClassName();
			} catch(NoClassDefFoundError | BootstrapMethodError e) {
				System.out.println(Main.getConsolePrefix() + "Das Telegrambot-Plugin wird automatisch heruntergeladen...");
				telegramNewDownloadFailed = !loadAdditionalPlugin(TELEGRAM_ID, "Telegrambot.jar");
			}
		}

		boolean labymodNewDownloadFailed = false;
		if(ConfigEntry.DISABLE_LABYMOD_FUNCTIONS.getValueAsBoolean() || ConfigEntry.KICK_LABYMOD_PLAYER.getValueAsBoolean() || ConfigEntry.ONLY_LABYMOD_PLAYER.getValueAsBoolean()) {
			try {
				PermissionSendListener.getClassName();

				Bukkit.getPluginManager().registerEvents(new PermissionSendListener(), Main.getInstance());
			} catch(NoClassDefFoundError e) {
				System.out.println(Main.getConsolePrefix() + "Das Labymod-Plugin wird automatisch heruntergeladen...");
				labymodNewDownloadFailed = !loadAdditionalPlugin(LABYMOD_ID, "Labymod.jar");
			}
		}

		if(discordNewDownloadFailed || telegramNewDownloadFailed || labymodNewDownloadFailed) {
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