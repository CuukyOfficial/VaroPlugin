package de.cuuky.varo.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import de.cuuky.varo.spigot.FileDownloader;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;

import de.cuuky.varo.Main;
import de.cuuky.varo.alert.AlertHandler;
import de.cuuky.varo.bot.discord.VaroDiscordBot;
import de.cuuky.varo.bot.discord.register.BotRegister;
import de.cuuky.varo.bot.telegram.VaroTelegramBot;
import de.cuuky.varo.broadcast.Broadcaster;
import de.cuuky.varo.config.ConfigFailureDetector;
import de.cuuky.varo.config.ConfigHandler;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.entity.player.PlayerHandler;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.team.TeamHandler;
import de.cuuky.varo.game.GameHandler;
import de.cuuky.varo.list.ListHandler;
import de.cuuky.varo.list.VaroList;
import de.cuuky.varo.listener.PermissionSendListener;
import de.cuuky.varo.mysql.MySQL;
import de.cuuky.varo.report.ReportHandler;
import de.cuuky.varo.scoreboard.ScoreboardHandler;
import de.cuuky.varo.serialize.VaroSerializeHandler;
import de.cuuky.varo.spawns.SpawnHandler;
import de.cuuky.varo.threads.OutSideTimeChecker;
import de.cuuky.varo.world.WorldHandler;
import net.labymod.serverapi.LabyModAPI;

public class DataManager {

	private static DataManager instance;

	private static int LABYMOD_ID = 52423, DISCORDBOT_ID = 66778, TELEGRAM_ID = 66823;

	private boolean doSave;

	public static DataManager getInstance() {
		if (instance == null) {
			instance = new DataManager();
		}
		return instance;
	}

	private DataManager() {
		load();
		loadPlugins();

		startAutoSave();
		doSave = true;
	}

	private void load() {
		ConfigFailureDetector.detectConfig();

		copyDefaultPresets();
		ConfigHandler.getInstance(); //Initialisierung

		GameHandler.initialise(); //Initialisierung GameHandler
		PlayerHandler.initialise(); //Initialisierung PlayerHandler
		TeamHandler.initialise(); //Initialisierung TeamHandler
		SpawnHandler.initialise(); //Initialisierung SpawnHandler
		WorldHandler.getInstance(); //Initialisierung WorldHandler
		ScoreboardHandler.getInstance(); //Initialisierung ScoreboardHandler
		ReportHandler.initialise(); //Initialisierung ReportHandler
		AlertHandler.initialise(); //Initialisierung AlertHandler
		OutSideTimeChecker.getInstance(); //Initialisierung TimeChecker
		MySQL.getInstance(); //Initialisierung MySQL
		ListHandler.getInstance(); //Initialisierung ListHandler
		Broadcaster.getInstance(); //Initialisierung Broadcaster

		VaroPlayer.getOnlinePlayer().forEach(vp -> vp.update());
	}

	private void loadPlugins() {
		boolean discordNewDownload = false;
		if(ConfigEntry.DISCORDBOT_ENABLED.getValueAsBoolean()) {
			VaroDiscordBot discordbot;
			try {
				discordbot = VaroDiscordBot.getInstance();
				discordbot.connect();
			} catch(NoClassDefFoundError | BootstrapMethodError ef) {
				System.out.println(Main.getConsolePrefix() + "Das Discordbot-Plugin wird automatisch heruntergeladen...");
				discordNewDownload = loadAdditionalPlugin(DISCORDBOT_ID, "Discordbot.jar");
			}
		}

		boolean telegramNewDownload = false;
		if(ConfigEntry.TELEGRAM_ENABLED.getValueAsBoolean()) {
			VaroTelegramBot telegrambot;
			try {
				telegrambot = VaroTelegramBot.getInstance();
				telegrambot.connect();
			} catch(NoClassDefFoundError | BootstrapMethodError e) {
				telegrambot = null;
				System.out.println(Main.getConsolePrefix() + "Das Telegrambot-Plugin wird automatisch heruntergeladen...");
				telegramNewDownload = loadAdditionalPlugin(TELEGRAM_ID, "Telegrambot.jar");
			}
		}

		boolean labymodNewDownload = false;
		if(ConfigEntry.DISABLE_LABYMOD_FUNCTIONS.getValueAsBoolean() || ConfigEntry.KICK_LABYMOD_PLAYER.getValueAsBoolean() || ConfigEntry.ONLY_LABYMOD_PLAYER.getValueAsBoolean()) {
			try {
				LabyModAPI.class.getName();
				Bukkit.getPluginManager().registerEvents(new PermissionSendListener(), Main.getInstance());
			} catch(NoClassDefFoundError e) {
				System.out.println(Main.getConsolePrefix() + "Das Labymod-Plugin wird automatisch heruntergeladen...");
				labymodNewDownload = loadAdditionalPlugin(LABYMOD_ID, "Labymod.jar");
			}
		}

		if(discordNewDownload || telegramNewDownload || labymodNewDownload) {
			System.out.println(Main.getConsolePrefix() + "Der Server wird heruntergefahren, damit das Heruntergeladene angewandt werden kann.");
			System.out.println(Main.getConsolePrefix() + "Bitte fahre den Server wieder hoch.");
			Bukkit.getServer().shutdown();
		}

	}

	public boolean loadAdditionalPlugin(int resourceId, String dataName) {
		try {
			FileDownloader fd = new FileDownloader("http://api.spiget.org/v2/resources/" + resourceId + "/download", "plugins/" + dataName);

			System.out.println(Main.getConsolePrefix() + "Downloade plugin " + dataName + "...");

			fd.startDownload();

			System.out.println(Main.getConsolePrefix() + "Donwload von " + dataName + " erfolgreich abgeschlossen!");
			return true;
		} catch(IOException e) {
			System.out.println(Main.getConsolePrefix() + "Es gab einen kritischen Fehler beim Download eines Plugins.");
			System.out.println(Main.getConsolePrefix() + "---------- Stack Trace ----------");
			e.printStackTrace();
			System.out.println(Main.getConsolePrefix() + "---------- Stack Trace ----------");
			return false;
		}

		// True: Plugin wurde neu heruntergeladen -> Neustart
		// False: Plugin konnte nicht heruntergeladen werden -> Kein Neustart
	}

	public void save() {
		if(!doSave)
			return;

		VaroSerializeHandler.saveAll();
		VaroList.saveLists();

		try {
			BotRegister.saveAll();
		} catch(NoClassDefFoundError e) {}
	}

	public void reloadConfig() {
		VaroList.reloadLists();
		ConfigHandler.getInstance().reload();
		ScoreboardHandler.getInstance().loadScores();

		for(VaroPlayer vp : VaroPlayer.getOnlinePlayer()) {
			vp.getPlayer().getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
			ScoreboardHandler.getInstance().sendScoreBoard(vp);
			vp.getNametag().giveAll();
		}
	}

	@SuppressWarnings("deprecation")
	private void startAutoSave() {
		Bukkit.getScheduler().scheduleAsyncRepeatingTask(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				save();
			}
		}, 12000, 12000);
	}

	private void copyDefaultPresets() {
		try {
			ZipInputStream zip = new ZipInputStream(new FileInputStream(Main.getInstance().getThisFile()));
			while(true) {
				ZipEntry e = zip.getNextEntry();
				if(e == null)
					break;

				String name = e.getName();
				e.isDirectory();
				if(name.startsWith("presets")) {
					File file = new File("plugins/Varo/" + name);
					if(e.isDirectory()) {
						file.mkdir();
						continue;
					}

					if(!file.exists()) {
						new File(file.getParent()).mkdirs();
						file.createNewFile();
					} else
						continue;

					FileOutputStream out = new FileOutputStream(file);

					byte[] byteBuff = new byte[1024];
					int bytesRead = 0;
					while((bytesRead = zip.read(byteBuff)) != -1) {
						out.write(byteBuff, 0, bytesRead);
					}

					out.flush();
					out.close();
				}
			}

			zip.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void setDoSave(boolean doSave) {
		this.doSave = doSave;
	}
}