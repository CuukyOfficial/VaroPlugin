package de.cuuky.varo;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import de.cuuky.varo.alert.Alert;
import de.cuuky.varo.alert.AlertType;
import de.cuuky.varo.bot.BotLauncher;
import de.cuuky.varo.bot.discord.VaroDiscordBot;
import de.cuuky.varo.bot.telegram.VaroTelegramBot;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.data.BukkitRegisterer;
import de.cuuky.varo.data.DataManager;
import de.cuuky.varo.game.Game;
import de.cuuky.varo.logger.LoggerMaster;
import de.cuuky.varo.logger.logger.ConsoleLogger;
import de.cuuky.varo.threads.DailyTimer;
import de.cuuky.varo.update.Updater;
import de.cuuky.varo.update.Updater.UpdateResult;
import de.cuuky.varo.version.VersionUtils;
import de.cuuky.varo.world.AutoSetup;

public class Main extends JavaPlugin {

	private static final String CONSOLE_PREFIX = "[Varo] ";
	private static Main instance;

	private static LoggerMaster logger;
	private static DataManager dataManager;
	private static VaroDiscordBot discordBot;
	private static VaroTelegramBot telegramBot;
	private static BotLauncher botLauncher;
	private static Updater updater;
	private static Game game;

	private String RESCOURCE_ID = "%%__RESOURCE__%%";
	private boolean failed = false;

	@Override
	public void onLoad() {
		new ConsoleLogger();
		super.onLoad();
	}

	@Override
	public void onEnable() {
		System.out.println("############################################################################");
		System.out.println("#                                                                          #");
		System.out.println("#  #     #                         ######                                  #");
		System.out.println("#  #     #   ##   #####   ####     #     # #      #    #  ####  # #    #   #");
		System.out.println("#  #     #  #  #  #    # #    #    #     # #      #    # #    # # ##   #   #");
		System.out.println("#  #     # #    # #    # #    #    ######  #      #    # #      # # #  #   #");
		System.out.println("#   #   #  ###### #####  #    #    #       #      #    # #  ### # #  # #   #");
		System.out.println("#    # #   #    # #   #  #    #    #       #      #    # #    # # #   ##   #");
		System.out.println("#     #    #    # #    #  ####     #       ######  ####   ####  # #    #   #");
		System.out.println("#                                                                          #");
		System.out.println("#                                 by Cuuky                                 #");
		System.out.println("#                                                                          #");
		System.out.println("############################################################################");

		System.out.println(CONSOLE_PREFIX);
		System.out.println(CONSOLE_PREFIX + "Enabling " + this.getDescription().getName() + " v" + this.getDescription().getVersion() + " by " + this.getDescription().getAuthors().get(0) + "...");
		System.out.println(CONSOLE_PREFIX + "Running on " + Bukkit.getVersion());
		System.out.println(CONSOLE_PREFIX + "Other plugins enabled: " + (Bukkit.getPluginManager().getPlugins().length - 1));

		instance = this;

		try {
			dataManager = new DataManager();

			try {
				updater = new Updater(Main.this, Integer.valueOf(RESCOURCE_ID));
				updater.postResults();
				if(updater.getResult() == UpdateResult.UPDATE_AVAILABLE)
					new Alert(AlertType.UPDATE_AVAILABLE, "§cEin neues Update des Plugins ist verfügbar!\n§7Im Regelfall kannst du dies ohne Probleme installieren, bitte\n§7informiere dichdennoch auf dem Discord.");
			} catch(NumberFormatException e) {}

			new DailyTimer();

			botLauncher = new BotLauncher();
			discordBot = botLauncher.getDiscordbot();
			telegramBot = botLauncher.getTelegrambot();
			new BukkitRegisterer();
		} catch(Exception e) {
			e.printStackTrace();
			failed = true;
			Bukkit.getPluginManager().disablePlugin(Main.this);
		}

		if(failed)
			return;

		if(ConfigEntry.AUTOSETUP_ENABLED.getValueAsBoolean() && game.willSetupNext())
			new AutoSetup();

		System.out.println(CONSOLE_PREFIX + "Enabled!");
		System.out.println(CONSOLE_PREFIX + " ");
		System.out.println(CONSOLE_PREFIX + "--------------------------------");
		super.onEnable();
	}

	@Override
	public void onDisable() {
		System.out.println(CONSOLE_PREFIX + "--------------------------------");
		System.out.println(CONSOLE_PREFIX + " ");
		System.out.println(CONSOLE_PREFIX + "Disabling " + this.getDescription().getName() + "...");

		if(dataManager != null && !failed) {
			System.out.println(CONSOLE_PREFIX + "Saving files...");
			dataManager.save();
		}

		if(botLauncher != null) {
			System.out.println(CONSOLE_PREFIX + "Disconnecting bots...");
			botLauncher.disconnect();
		}

		if(!failed)
			VersionUtils.getOnlinePlayer().forEach(pl -> pl.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard()));
		Bukkit.getScheduler().cancelTasks(this);

		System.out.println(CONSOLE_PREFIX + "Disabled!");
		System.out.println(CONSOLE_PREFIX + " ");
		System.out.println(CONSOLE_PREFIX + "--------------------------------");
		super.onDisable();
	}

	public File getThisFile() {
		return getFile();
	}

	public static DataManager getDataManager() {
		return dataManager;
	}

	public static VaroTelegramBot getTelegramBot() {
		return telegramBot;
	}

	public static void setTelegramBot(VaroTelegramBot telegramBot) {
		Main.telegramBot = telegramBot;
	}

	public static VaroDiscordBot getDiscordBot() {
		return discordBot;
	}

	public static LoggerMaster getLoggerMaster() {
		return logger;
	}

	public static void setLogger(LoggerMaster logger) {
		Main.logger = logger;
	}

	public static Game getGame() {
		return game;
	}

	public static void setGame(Game game) {
		Main.game = game;
	}

	public static String getConsolePrefix() {
		return CONSOLE_PREFIX;
	}

	public static Main getInstance() {
		return instance;
	}

	public static String getPrefix() {
		return ConfigEntry.PREFIX.getValueAsString();
	}

	public static String getColorCode() {
		return ConfigEntry.PROJECTNAME_COLORCODE.getValueAsString();
	}

	public static String getProjectName() {
		return getColorCode() + ConfigEntry.PROJECT_NAME.getValueAsString();
	}

	public static void broadcastMessage(String message) {
		Bukkit.broadcastMessage(getPrefix() + message);
	}

	public static boolean isBootedUp() {
		return dataManager != null;
	}
}