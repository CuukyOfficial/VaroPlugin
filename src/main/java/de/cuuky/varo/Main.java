package de.cuuky.varo;

import java.io.File;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.io.BaseEncoding;

import de.cuuky.cfw.CuukyFrameWork;
import de.cuuky.varo.bot.BotLauncher;
import de.cuuky.varo.bstats.MetricsLoader;
import de.cuuky.varo.configuration.ConfigFailureDetector;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.VaroLanguageManager;
import de.cuuky.varo.data.BukkitRegisterer;
import de.cuuky.varo.data.DataManager;
import de.cuuky.varo.data.Dependencies;
import de.cuuky.varo.game.VaroGame;
import de.cuuky.varo.gui.VaroInventoryManager;
import de.cuuky.varo.recovery.recoveries.BugReport;
import de.cuuky.varo.spigot.VaroUpdater;
import de.cuuky.varo.threads.SmartLagDetector;
import de.varoplugin.cfw.utils.PlayerProfileUtils;
import de.varoplugin.cfw.utils.PlayerProfileUtils.PlayerLookup;
import de.varoplugin.cfw.version.ServerSoftware;
import de.varoplugin.cfw.version.ServerVersion;
import de.varoplugin.cfw.version.VersionUtils;

public class Main extends JavaPlugin {

	/*
	 * Plugin by Cuuky @ 2019-2020
	 */

	private static final String CONSOLE_PREFIX = "[Varo] ";
	private static final int RESOURCE_ID = 71075;
	public static final String DISCORD_INVITE = "https://discord.varoplugin.de/";

	private static Main instance;

	private static BotLauncher botLauncher;
	private static CuukyFrameWork cuukyFrameWork;
	private static VaroInventoryManager inventoryManager;
	private static DataManager dataManager;
	private static VaroUpdater varoUpdater;
	private static VaroLanguageManager languageManager;
	private static VaroGame varoGame;
	private static boolean enabled;

	private boolean failed;

	@Override
	public void onLoad() {
		this.failed = false;
		instance = this;

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
		System.out.println(CONSOLE_PREFIX + "	Running on " + VersionUtils.getServerSoftware().getName() + " ("
				+ Bukkit.getVersion() + ")");
		System.out.println(CONSOLE_PREFIX + "	Software-Name (Base): " + Bukkit.getName() + " (1."
				+ VersionUtils.getVersion().getIdentifier() + ")");
		System.out.println(
				CONSOLE_PREFIX + "	Other plugins enabled: " + (Bukkit.getPluginManager().getPlugins().length - 1));
		System.out.println(CONSOLE_PREFIX + "Forge-Support: " + VersionUtils.hasForgeSupport());

		if (VersionUtils.getServerSoftware() == ServerSoftware.BUKKIT) {
			System.out.println(CONSOLE_PREFIX
					+ "	It seems like you're using Bukkit. Bukkit has a worse performance and is lacking some features.");
			System.out.println(
					CONSOLE_PREFIX + "	Please use Spigot or Paper instead (https://papermc.io/).");
		}
		System.out.println(CONSOLE_PREFIX);
		
		Dependencies.loadRequired(this);
		
		dataManager = new DataManager(this);
		dataManager.preLoad();
		
		System.out.println(CONSOLE_PREFIX);

		if (VersionUtils.getVersion().isHigherThan(ServerVersion.ONE_7))
		    System.out.println(CONSOLE_PREFIX + "SHA1SUM: " + this.calcChecksum());

		if (this.failed)
			return;

		try {
			if (new ConfigFailureDetector().hasFailed()) {
				this.fail();
				return;
			}

			long dataStamp = System.currentTimeMillis();
			cuukyFrameWork = new CuukyFrameWork(instance,
					languageManager = new VaroLanguageManager(Main.this));
			inventoryManager = new VaroInventoryManager(this);
			dataManager.load();
			System.out.println(CONSOLE_PREFIX + "Loaded all data (" + (System.currentTimeMillis() - dataStamp) + "ms)");

			varoUpdater = new VaroUpdater(RESOURCE_ID, this.getDescription().getVersion(),
					() -> varoUpdater.printResults());
			botLauncher = new BotLauncher();

			if (this.isFailed())
				return;

			new MetricsLoader(this);
			new SmartLagDetector(this);

			BukkitRegisterer.registerEvents();
			BukkitRegisterer.registerCommands();
		} catch (Throwable e) {
			e.printStackTrace();
			this.fail();
		}

		if (this.failed)
			return;

		enabled = true;
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

		if (dataManager != null && !this.failed) {
			System.out.println(CONSOLE_PREFIX + "Saving data...");
			dataManager.save();
		}

		if (botLauncher != null && (botLauncher.getDiscordbot() != null || botLauncher.getTelegrambot() != null)) {
			System.out.println(CONSOLE_PREFIX + "Disconnecting bots...");
			botLauncher.disconnect();
		}

		if (!this.failed) {
            cuukyFrameWork.disable();
            VersionUtils.getVersionAdapter().getOnlinePlayers()
                .forEach(pl -> pl.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard()));
        } else {
			File bugReport = BugReport.createBugReport();
			if (bugReport != null)
			    System.out.println(CONSOLE_PREFIX + "Saved crash-report to " + bugReport.getAbsolutePath());
			else
			    System.out.println(CONSOLE_PREFIX + "Unable to create crash-report");
		}
		Bukkit.getScheduler().cancelTasks(this);
		dataManager.getVaroLoggerManager().cleanUp();

		System.out.println(CONSOLE_PREFIX + "Disabled! (" + (System.currentTimeMillis() - timestamp) + "ms)");
		System.out.println(CONSOLE_PREFIX + " ");
		System.out.println(CONSOLE_PREFIX + "--------------------------------");
		super.onDisable();
	}

	private String calcChecksum() {
	    try {
    	    MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            try (InputStream inputStream = Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().toURL().openStream(); DigestInputStream digestInputStream = new DigestInputStream(inputStream, messageDigest)) {
                byte[] buffer = new byte[4096];
                while (digestInputStream.read(buffer) == buffer.length);
                return BaseEncoding.base16().lowerCase().encode(messageDigest.digest());
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return "Unknown";
	}

	public void fail() {
		this.failed = true;
		Bukkit.getPluginManager().disablePlugin(this);
	}

	public boolean isFailed() {
		return this.failed;
	}

	public File getThisFile() {
		return this.getFile();
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

	public static CuukyFrameWork getCuukyFrameWork() {
		return cuukyFrameWork;
	}
	
	public static VaroInventoryManager getInventoryManager() {
        return inventoryManager;
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
		return instance.getDescription().getName() + " v" + instance.getDescription().getVersion() + " by "
				+ instance.getDescription().getAuthors().get(0) + " - Contributors: " + getContributors();
	}

	public static String getContributors() {
		return instance.getDescription().getAuthors().stream().skip(1).collect(Collectors.joining(", "));
	}

	public static String getPrefix() {
		return ConfigSetting.PREFIX.getValueAsString();
	}

	public static String getProjectName() {
		return getColorCode() + ConfigSetting.PROJECT_NAME.getValueAsString();
	}
	
	public static void sendPluginInfo(Player player) {
        player.sendMessage(Main.getPrefix() + Main.getColorCode() + "§l" + Main.getPluginName());
        player.sendMessage(Main.getPrefix() + "§7Version: " + Main.getColorCode() + Main.getInstance().getDescription().getVersion());
        player.sendMessage(Main.getPrefix() + "§7Discord-Server: " + Main.getColorCode() + Main.DISCORD_INVITE);
        player.sendMessage(Main.getPrefix() + "§7This software is licensed under the GNU AGPL v3 license");
        player.sendMessage(Main.getPrefix() + "§7Source code: https://github.com/CuukyOfficial/VaroPlugin");
    }
	
	public static PlayerLookup lookupPlayer(String name) {
        return !ConfigSetting.CRACKED_SERVER.getValueAsBoolean() ? PlayerProfileUtils.getOrFetchByName(name) : PlayerProfileUtils.getCrackedByName(name);
    }

	public static boolean isBootedUp() {
		return enabled;
	}

	public static Main getInstance() {
		return instance;
	}

	public static int getResourceId() {
		return RESOURCE_ID;
	}
}