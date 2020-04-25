package de.cuuky.varo;

import java.io.File;

import javax.swing.JOptionPane;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import de.cuuky.cfw.CuukyFrameWork;
import de.cuuky.cfw.utils.JavaUtils;
import de.cuuky.cfw.version.ServerSoftware;
import de.cuuky.cfw.version.VersionUtils;
import de.cuuky.varo.bot.BotLauncher;
import de.cuuky.varo.bstats.MetricsLoader;
import de.cuuky.varo.clientadapter.VaroBoardProvider;
import de.cuuky.varo.configuration.ConfigFailureDetector;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.VaroLanguageManager;
import de.cuuky.varo.data.BukkitRegisterer;
import de.cuuky.varo.data.DataManager;
import de.cuuky.varo.game.VaroGame;
import de.cuuky.varo.logger.logger.ConsoleLogger;
import de.cuuky.varo.recovery.recoveries.VaroBugreport;
import de.cuuky.varo.spigot.updater.VaroUpdater;
import de.cuuky.varo.threads.SmartLagDetector;

public class Main extends JavaPlugin {

	/*
	 * Plugin by Cuuky @ 2019-2020
	 */

	private static final String CONSOLE_PREFIX = "[Varo] ";
	private static final int RESCOURCE_ID = 71075;

	private static Main instance;

	private static BotLauncher botLauncher;
	private static VaroBoardProvider varoBoard;
	private static CuukyFrameWork cuukyFrameWork;
	private static DataManager dataManager;
	private static VaroUpdater varoUpdater;
	private static VaroLanguageManager languageManager;
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
		System.out.println("############################################################################");

		System.out.println(CONSOLE_PREFIX);
		System.out.println(CONSOLE_PREFIX + "Enabling " + getPluginName() + "...");
		System.out.println(CONSOLE_PREFIX + "Your server: ");
		System.out.println(CONSOLE_PREFIX + "	Running on " + VersionUtils.getServerSoftware().getName() + " (" + Bukkit.getVersion() + ")");
		System.out.println(CONSOLE_PREFIX + "	Software-Name (Base): " + Bukkit.getName() + " (1." + VersionUtils.getVersion().getIdentifier() + ")");
		System.out.println(CONSOLE_PREFIX + "	Other plugins enabled: " + (Bukkit.getPluginManager().getPlugins().length - 1));

		if (VersionUtils.getServerSoftware() != ServerSoftware.UNKNOWN)
			System.out.println(CONSOLE_PREFIX + "	Forge-Support: " + VersionUtils.getServerSoftware().hasModSupport());

		if (VersionUtils.getServerSoftware() == ServerSoftware.BUKKIT) {
			System.out.println(CONSOLE_PREFIX + "	It seems like you're using Bukkit. Bukkit has a worse performance and is lacking some features.");
			System.out.println(CONSOLE_PREFIX + "	Please use Spigot or Paper instead (https://getbukkit.org/download/spigot).");
		}

		try {
			if (new ConfigFailureDetector().hasFailed()) {
				failed = true;
				Bukkit.getPluginManager().disablePlugin(Main.getInstance());
				return;
			}

			long dataStamp = System.currentTimeMillis();

			cuukyFrameWork = new CuukyFrameWork(instance);
			cuukyFrameWork.getClientAdapterManager().setUpdateHandler(varoBoard = new VaroBoardProvider());

			dataManager = new DataManager();
			System.out.println(CONSOLE_PREFIX + "Loaded all data (" + (System.currentTimeMillis() - dataStamp) + "ms)");

			varoUpdater = new VaroUpdater(RESCOURCE_ID, getDescription().getVersion(), new Runnable() {

				@Override
				public void run() {
					varoUpdater.printResults();
				}
			});
			botLauncher = new BotLauncher();

			new MetricsLoader(this);
			new SmartLagDetector(this);

			BukkitRegisterer.registerEvents();
			BukkitRegisterer.registerCommands();
		} catch (Throwable e) {
			e.printStackTrace();
			failed = true;
			Bukkit.getPluginManager().disablePlugin(Main.this);
		}

		if (failed)
			return;

		System.out.println(CONSOLE_PREFIX + "Enabled! (" + (System.currentTimeMillis() - timestamp) + "ms)");
		System.out.println(CONSOLE_PREFIX + " ");
		System.out.println(CONSOLE_PREFIX + "--------------------------------");
		super.onEnable();
	}

	@Override
	public void onDisable() {
		long timestamp = System.currentTimeMillis();

		System.out.println(CONSOLE_PREFIX + "--------------------------------");
		System.out.println(CONSOLE_PREFIX + " ");
		System.out.println(CONSOLE_PREFIX + "Disabling " + this.getDescription().getName() + "...");

		if (dataManager != null && !failed) {
			System.out.println(CONSOLE_PREFIX + "Saving data...");
			dataManager.save();
		}

		if (botLauncher != null && (botLauncher.getDiscordbot() != null || botLauncher.getTelegrambot() != null)) {
			System.out.println(CONSOLE_PREFIX + "Disconnecting bots...");
			botLauncher.disconnect();
		}

		if (!failed)
			VersionUtils.getOnlinePlayer().forEach(pl -> pl.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard()));
		else {
			VaroBugreport report = new VaroBugreport();
			System.out.println(CONSOLE_PREFIX + "Saved Crashreport to " + report.getZipFile().getName());
		}
		Bukkit.getScheduler().cancelTasks(this);

		System.out.println(CONSOLE_PREFIX + "Disabled! (" + (System.currentTimeMillis() - timestamp) + "ms)");
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

	public static VaroBoardProvider getVaroBoard() {
		return varoBoard;
	}

	public static CuukyFrameWork getCuukyFrameWork() {
		return cuukyFrameWork;
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

	public static void setLanguageManager(VaroLanguageManager languageManager) {
		Main.languageManager = languageManager;
	}

	public static VaroLanguageManager getLanguageManager() {
		return languageManager;
	}

	public static BotLauncher getBotLauncher() {
		return botLauncher;
	}

	public static String getPluginName() {
		return instance.getDescription().getName() + " v" + instance.getDescription().getVersion() + " by " + instance.getDescription().getAuthors().get(0) + " - Contributors: " + getContributors();
	}

	public static String getContributors() {
		return JavaUtils.getArgsToString(JavaUtils.removeString(JavaUtils.arrayToCollection(instance.getDescription().getAuthors()), 0), ", ");
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

	public static int getRescourceId() {
		return RESCOURCE_ID;
	}
}