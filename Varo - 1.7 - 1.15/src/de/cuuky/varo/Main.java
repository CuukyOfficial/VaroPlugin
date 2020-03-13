package de.cuuky.varo;

import java.io.File;

import javax.swing.JOptionPane;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import de.cuuky.varo.bot.BotLauncher;
import de.cuuky.varo.bstats.MetricsLoader;
import de.cuuky.varo.configuration.ConfigFailureDetector;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.data.BukkitRegisterer;
import de.cuuky.varo.data.DataManager;
import de.cuuky.varo.game.VaroGame;
import de.cuuky.varo.logger.logger.ConsoleLogger;
import de.cuuky.varo.recovery.recoveries.VaroBugreport;
import de.cuuky.varo.spigot.updater.VaroUpdater;
import de.cuuky.varo.threads.SmartLagDetector;
import de.cuuky.varo.utils.JavaUtils;
import de.cuuky.varo.version.VersionUtils;

public class Main extends JavaPlugin {

	/*
	 * Plugin by Cuuky @ 2019-2020 - All rights reserved! Contributors: Korne127
	 */

	private static final String CONSOLE_PREFIX = "[Varo] ";

	private static Main instance;

	private static BotLauncher botLauncher;
	private static DataManager dataManager;
	private static VaroUpdater varoUpdater;
	private static VaroGame varoGame;

	private boolean failed;

	@Override
	public void onLoad() {
		failed = false;
		instance = this;

		new ConsoleLogger("consolelogs");
		super.onLoad();
	}

	@Override
	public void onEnable() {
		long timestamp = System.currentTimeMillis();
		
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
		System.out.println("#                               by Cuuky                                   #");
		System.out.println("#                                                                          #");
		System.out.println("#                             Contributors:                                #");
		System.out.println("#                               Korne127                                   #");
		System.out.println("#                                                                          #");
		System.out.println("############################################################################");

		System.out.println(CONSOLE_PREFIX);
		System.out.println(CONSOLE_PREFIX + "Enabling " + getPluginName() + "...");
		System.out.println(CONSOLE_PREFIX + "Running on " + Bukkit.getVersion());
		System.out.println(CONSOLE_PREFIX + "Other plugins enabled: " + (Bukkit.getPluginManager().getPlugins().length - 1));
		
		if(Bukkit.getVersion().contains("Bukkit")) {
			System.out.println(CONSOLE_PREFIX + "It seems like you're using Bukkit. Bukkit has a worse performance and is lacking some features.");
			System.out.println(CONSOLE_PREFIX + "Please use Spigot or Paper instead (https://getbukkit.org/download/spigot).");
		}

		try {
			if(new ConfigFailureDetector().hasFailed()) {
				failed = true;
				Bukkit.getPluginManager().disablePlugin(Main.getInstance());
				return;
			}
			
			dataManager = new DataManager();
			varoUpdater = new VaroUpdater();
			botLauncher = new BotLauncher();
			new MetricsLoader(this);
			new SmartLagDetector(this);
			
			BukkitRegisterer.registerEvents();
			BukkitRegisterer.registerCommands();
		} catch(Exception e) {
			e.printStackTrace();
			failed = true;
			Bukkit.getPluginManager().disablePlugin(Main.this);
		}

		if(failed)
			return;

		System.out.println(CONSOLE_PREFIX + "Enabled! (" + (System.currentTimeMillis() - timestamp) + "ms)");
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
		else {
			VaroBugreport report = new VaroBugreport();
			System.out.println(CONSOLE_PREFIX + "Saved Crashreport to " + report.getZipFile().getName());
		}
		Bukkit.getScheduler().cancelTasks(this);

		System.out.println(CONSOLE_PREFIX + "Disabled!");
		System.out.println(CONSOLE_PREFIX + " ");
		System.out.println(CONSOLE_PREFIX + "--------------------------------");
		super.onDisable();
	}
	
	public void setFailed(boolean failed) {
		this.failed = failed;
	}
	
	public boolean isFailed() {
		return this.failed;
	}

	public File getThisFile() {
		return getFile();
	}

	public static void broadcastMessage(String message) {
		Bukkit.broadcastMessage(getPrefix() + message);
	}

	public static String getColorCode() {
		return ConfigSetting.PROJECTNAME_COLORCODE.getValueAsString();
	}

	public static String getConsolePrefix() {
		return CONSOLE_PREFIX;
	}

	public static void setVaroGame(VaroGame varoGame) {
		Main.varoGame = varoGame;
	}

	public static VaroGame getVaroGame() {
		return varoGame;
	}

	public static VaroUpdater getVaroUpdater() {
		return varoUpdater;
	}

	public static void setDataManager(DataManager dataManager) {
		Main.dataManager = dataManager;
	}

	public static DataManager getDataManager() {
		return dataManager;
	}
	
	public static BotLauncher getBotLauncher() {
		return botLauncher;
	}

	public static String getPluginName() {
		return instance.getDescription().getName() + " v" + instance.getDescription().getVersion() + " by " + instance.getDescription().getAuthors().get(0) + ", Contributors: " + getContributors();
	}
	
	public static String getContributors() {
		return JavaUtils.getArgsToString(JavaUtils.removeString(JavaUtils.arrayToCollection(instance.getDescription().getAuthors()), 0), ",");
	}

	public static String getPrefix() {
		return ConfigSetting.PREFIX.getValueAsString();
	}

	public static String getProjectName() {
		return getColorCode() + ConfigSetting.PROJECT_NAME.getValueAsString();
	}

	public static boolean isBootedUp() {
		return dataManager != null;
	}

	public static void main(String[] args) {
		JOptionPane.showMessageDialog(null, "No don't do it");
	}

	public static Main getInstance() {
		return instance;
	}
}