package de.varoplugin.varo;

import java.io.File;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.logging.Level;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.io.BaseEncoding;

import de.varoplugin.cfw.utils.PlayerProfileUtils;
import de.varoplugin.cfw.utils.PlayerProfileUtils.PlayerLookup;
import de.varoplugin.cfw.version.ServerSoftware;
import de.varoplugin.cfw.version.ServerVersion;
import de.varoplugin.cfw.version.VersionUtils;
import de.varoplugin.varo.bot.BotLauncher;
import de.varoplugin.varo.config.VaroConfig;
import de.varoplugin.varo.configuration.ConfigFailureDetector;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;
import de.varoplugin.varo.data.BugReport;
import de.varoplugin.varo.data.BukkitRegisterer;
import de.varoplugin.varo.data.DataManager;
import de.varoplugin.varo.data.Dependencies;
import de.varoplugin.varo.game.VaroGame;
import de.varoplugin.varo.gui.VaroInventoryManager;
import de.varoplugin.varo.spigot.VaroUpdater;

public class Main extends JavaPlugin {

	/*
	 * Plugin by Cuuky @ 2019-2020
	 */

	private static final String CONSOLE_PREFIX = "[Varo] ";
	private static final int RESOURCE_ID = 71075;
	public static final String DISCORD_INVITE = "https://discord.varoplugin.de/";

	private static Main instance;
	private static boolean paper;

	private static BotLauncher botLauncher;
	private static VaroInventoryManager inventoryManager;
	private static DataManager dataManager;
	private static VaroUpdater varoUpdater;
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
	    try {
    		this.getLogger().log(Level.INFO, "############################################################################");
    		this.getLogger().log(Level.INFO, "#                                                                          #");
    		this.getLogger().log(Level.INFO, "#  #     #                         ######                                  #");
    		this.getLogger().log(Level.INFO, "#  #     #   ##   #####   ####     #     # #      #    #  ####  # #    #   #");
    		this.getLogger().log(Level.INFO, "#  #     #  #  #  #    # #    #    #     # #      #    # #    # # ##   #   #");
    		this.getLogger().log(Level.INFO, "#  #     # #    # #    # #    #    ######  #      #    # #      # # #  #   #");
    		this.getLogger().log(Level.INFO, "#   #   #  ###### #####  #    #    #       #      #    # #  ### # #  # #   #");
    		this.getLogger().log(Level.INFO, "#    # #   #    # #   #  #    #    #       #      #    # #    # # #   ##   #");
    		this.getLogger().log(Level.INFO, "#     #    #    # #    #  ####     #       ######  ####   ####  # #    #   #");
    		this.getLogger().log(Level.INFO, "#                                                                          #");
    		this.getLogger().log(Level.INFO, "#                               by Cuuky                                   #");
    		this.getLogger().log(Level.INFO, "#                                                                          #");
    		this.getLogger().log(Level.INFO, "############################################################################");
    
    		this.getLogger().log(Level.INFO, "");
    		this.getLogger().log(Level.INFO, "Enabling " + getPluginName() + "...");
    		
    		this.getLogger().log(Level.INFO, "Your server: ");
    		this.getLogger().log(Level.INFO, "	Running on " + VersionUtils.getServerSoftware().getName() + " ("
    				+ Bukkit.getVersion() + ")");
    		this.getLogger().log(Level.INFO, "	Software-Name (Base): " + Bukkit.getName() + " (1."
    				+ VersionUtils.getVersion().getIdentifier() + ")");
    		this.getLogger().log(Level.INFO, 
    				"	Other plugins enabled: " + (Bukkit.getPluginManager().getPlugins().length - 1));
    		this.getLogger().log(Level.INFO, "Forge-Support: " + VersionUtils.hasForgeSupport());

    		if (VersionUtils.getServerSoftware() == ServerSoftware.BUKKIT)
    			this.getLogger().log(Level.SEVERE,
    			        "	It seems like you're using Bukkit. Please use Spigot or Paper instead! (https://papermc.io/)");
    		this.getLogger().log(Level.INFO, "");
    		
    		if (!isPaper()) {
    		    Dependencies.loadRequired(this);
    		}

    		VaroConfig.load();

    		dataManager = new DataManager(this);
    		dataManager.preLoad();
    		
    		this.getLogger().log(Level.INFO, "");
    
    		if (VersionUtils.getVersion().isHigherThan(ServerVersion.ONE_7))
    		    this.getLogger().log(Level.INFO, "SHA1SUM: " + this.calcChecksum());
    		
			if (new ConfigFailureDetector().hasFailed()) {
				this.fail();
				return;
			}

			long dataStamp = System.currentTimeMillis();
			inventoryManager = new VaroInventoryManager(this);
			dataManager.load();
			this.getLogger().log(Level.INFO, "Loaded all data (" + (System.currentTimeMillis() - dataStamp) + "ms)");

			varoUpdater = new VaroUpdater(RESOURCE_ID, this.getDescription().getVersion(),
					() -> varoUpdater.printResults());
			botLauncher = new BotLauncher();

			if (this.isFailed())
				return;

			MetricsLoader.loadMetrics(this);

			BukkitRegisterer.registerEvents();
			BukkitRegisterer.registerCommands();
		} catch (Throwable e) {
			e.printStackTrace();
			this.fail();
		}

		if (this.failed)
			return;

		enabled = true;
		this.getLogger().log(Level.INFO, "Enabled! (" + (System.currentTimeMillis() - timestamp) + "ms)");
		this.getLogger().log(Level.INFO, " ");
		this.getLogger().log(Level.INFO, "--------------------------------");
		super.onEnable();
	}

	@Override
	public void onDisable() {
		long timestamp = System.currentTimeMillis();

		this.getLogger().log(Level.INFO, "--------------------------------");
		this.getLogger().log(Level.INFO, " ");
		this.getLogger().log(Level.INFO, "Disabling " + this.getDescription().getName() + "...");

		if (dataManager != null && !this.failed) {
			this.getLogger().log(Level.INFO, "Saving data...");
			dataManager.saveSync();
		}

		if (botLauncher != null && botLauncher.getDiscordbot() != null) {
			this.getLogger().log(Level.INFO, "Disconnecting bots...");
			botLauncher.disconnect();
		}

		if (!this.failed) {
            VersionUtils.getVersionAdapter().getOnlinePlayers()
                .forEach(pl -> pl.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard()));
            dataManager.cleanUp();
        } else {
			File bugReport = BugReport.createBugReport();
			if (bugReport != null)
			    this.getLogger().log(Level.INFO, "Saved crash-report to " + bugReport.getAbsolutePath());
			else
			    this.getLogger().log(Level.SEVERE, "Unable to create crash-report");
		}
		Bukkit.getScheduler().cancelTasks(this);

		this.getLogger().log(Level.INFO, "Disabled! (" + (System.currentTimeMillis() - timestamp) + "ms)");
		this.getLogger().log(Level.INFO, " ");
		this.getLogger().log(Level.INFO, "--------------------------------");
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
	
	/**
	 * Returns true if the plugin is running as a paper plugin
	 * 
	 * @return true if the plugin is running as a paper plugin
	 */
	public static boolean isPaper() {
        return paper;
    }
	
	public static void setPaper(boolean paper) {
        Main.paper = paper;
    }

	public static int getResourceId() {
		return RESOURCE_ID;
	}
}