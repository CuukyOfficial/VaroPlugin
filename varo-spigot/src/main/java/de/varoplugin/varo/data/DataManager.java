package de.varoplugin.varo.data;

import de.varoplugin.cfw.player.hud.NameTagGroup;
import de.varoplugin.cfw.version.ServerVersion;
import de.varoplugin.cfw.version.VersionUtils;
import de.varoplugin.varo.Main;
import de.varoplugin.varo.alert.AlertHandler;
import de.varoplugin.varo.bot.BotLauncher;
import de.varoplugin.varo.bot.discord.BotRegister;
import de.varoplugin.varo.broadcast.Broadcaster;
import de.varoplugin.varo.command.custom.CustomCommandManager;
import de.varoplugin.varo.config.language.Messages;
import de.varoplugin.varo.configuration.ConfigHandler;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;
import de.varoplugin.varo.enchantment.EnchantmentManager;
import de.varoplugin.varo.game.VaroGameHandler;
import de.varoplugin.varo.list.VaroList;
import de.varoplugin.varo.list.VaroListManager;
import de.varoplugin.varo.logger.VaroLoggerManager;
import de.varoplugin.varo.mysql.MySQLClient;
import de.varoplugin.varo.player.VaroPlayer;
import de.varoplugin.varo.player.VaroPlayerHandler;
import de.varoplugin.varo.report.ReportHandler;
import de.varoplugin.varo.serialize.VaroSerializeHandler;
import de.varoplugin.varo.spawns.SpawnHandler;
import de.varoplugin.varo.tasks.DailyTasks;
import de.varoplugin.varo.team.VaroTeamHandler;
import de.varoplugin.varo.utils.OutSideTimeChecker;
import de.varoplugin.varo.utils.VaroUtils;
import io.github.almightysatan.slams.InvalidTypeException;
import io.github.almightysatan.slams.MissingTranslationException;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

public class DataManager {

	private static final int SAVE_DELAY = 12000;

	private final Main instance;

	private VaroLoggerManager varoLoggerManager;
	private ConfigHandler configHandler;
	private NameTagGroup defaultNameTagGroup;
	private NameTagGroup spectatorNameTagGroup;
	private VaroGameHandler varoGameHandler;
	private VaroPlayerHandler varoPlayerHandler;
	private VaroTeamHandler varoTeamHandler;
	private SpawnHandler spawnHandler;
	private ReportHandler reportHandler;
	private AlertHandler alertHandler;
	private OutSideTimeChecker outsideTimeChecker;
	private MySQLClient mysqlClient;
	private VaroListManager listManager;
	private Broadcaster broadcaster;
	private DailyTasks dailyTimer;
	private CustomCommandManager customCommandManager;
	private EnchantmentManager enchantmentManager;
    private BotLauncher botLauncher;
	private final SortedSet<VaroBackup> backups = new TreeSet<>();
	private final Collection<Runnable> shutdownTasks = new LinkedList<>();

	private boolean doSave;

	public DataManager(Main instance) {
		this.instance = instance;

		Main.setDataManager(this);
	}

	public void preLoad() {
		this.configHandler = new ConfigHandler();
		this.defaultNameTagGroup = new NameTagGroup();
		this.spectatorNameTagGroup = new NameTagGroup();
		this.varoLoggerManager = new VaroLoggerManager();
	}

	public void load() throws MissingTranslationException, InvalidTypeException, IOException {
		this.varoPlayerHandler = new VaroPlayerHandler();
		this.varoTeamHandler = new VaroTeamHandler();
		this.varoGameHandler = new VaroGameHandler();
		this.spawnHandler = new SpawnHandler();
		this.reportHandler = new ReportHandler();
		this.alertHandler = new AlertHandler();
		this.outsideTimeChecker = new OutSideTimeChecker();
		this.mysqlClient = new MySQLClient();
		this.listManager = new VaroListManager();
		this.broadcaster = new Broadcaster();
		this.customCommandManager = new CustomCommandManager();
		this.enchantmentManager = new EnchantmentManager();

		this.loadBackups();

        Messages.load();

        this.botLauncher = new BotLauncher(); // initialize bot before running daily tasks

        this.varoPlayerHandler.initPlayers();

        this.dailyTimer = new DailyTasks();

        if (VersionUtils.getVersion().isLowerThan(ServerVersion.ONE_12)) {
            // 1.12+ uses a game rule which is set in VaroWorld
            VersionUtils.getVersionAdapter().setServerProperty("announce-player-achievements", !ConfigSetting.BLOCK_ADVANCEMENTS.getValueAsBoolean());
        }

		Bukkit.getServer().setSpawnRadius(ConfigSetting.SPAWN_PROTECTION_RADIUS.getValueAsInt());
		VaroUtils.updateWorldTime();

		VaroPlayer.getOnlinePlayer().forEach(VaroPlayer::update);

		this.startAutoSave();

		this.doSave = true;
	}
	
	public void cleanUp() {
	    this.getVaroLoggerManager().cleanUp();
	    this.shutdownTasks.forEach(Runnable::run);
	}

	private void startAutoSave() {

		new BukkitRunnable() {
			@Override
			public void run() {
				DataManager.this.saveSync(); // TODO This can cause concurrency issues
			}
		}.runTaskTimerAsynchronously(Main.getInstance(), SAVE_DELAY, SAVE_DELAY);
	}

	public void reloadConfig() {
		VaroList.loadLists();
		this.customCommandManager.reload();
		this.configHandler.reload();
	}

	public synchronized void saveSync() {
		if (!this.doSave)
			return;

		VaroSerializeHandler.saveAll();
		VaroList.saveLists();
		this.customCommandManager.reloadSave();

		try {
			BotRegister.saveAll();
		} catch (NoClassDefFoundError e) {
		}
	}
	
	private void loadBackups() {
        File directory = new File(VaroBackup.BACKUP_DIRECTORY);
        if (!directory.exists())
            return;
        if (!directory.isDirectory())
            throw new IllegalStateException();

        synchronized (this.backups) {
            for (File file : directory.listFiles())
                if (file.isFile() && file.getName().endsWith(".zip"))
                    this.backups.add(new VaroBackup(file));
        }
    }

	public void createBackup(Consumer<VaroBackup> callback) {
	    this.saveSync();
	    Bukkit.getScheduler().runTaskAsynchronously(this.instance, () -> {
	        VaroBackup backup = VaroBackup.createBackup();
	        if (backup != null)
	            synchronized (this.backups) {
                    this.backups.add(backup);
                }
	        if (callback != null)
	            Bukkit.getScheduler().scheduleSyncDelayedTask(this.instance, () -> callback.accept(backup));
	    });
	}
	
	public void restoreBackup(VaroBackup backup) {
	    this.shutdownTasks.add(backup::restore);
	    this.setDoSave(false);
	    Bukkit.getServer().shutdown();
	}

	public void deleteBackup(VaroBackup backup) {
	    boolean removed;
	    synchronized (this.backups) {
	        removed = this.backups.remove(backup);
        }
	    if (removed)
	        Bukkit.getScheduler().runTaskAsynchronously(this.instance, backup::delete);
	}

	public void setDoSave(boolean doSave) {
		this.doSave = doSave;
	}

	public AlertHandler getAlertHandler() {
		return this.alertHandler;
	}

	public Broadcaster getBroadcaster() {
		return this.broadcaster;
	}

	public ConfigHandler getConfigHandler() {
		return this.configHandler;
	}

	public NameTagGroup getDefaultNameTagGroup() {
		return defaultNameTagGroup;
	}

	public NameTagGroup getSpectatorNameTagGroup() {
		return this.spectatorNameTagGroup;
	}

	public VaroListManager getListManager() {
		return this.listManager;
	}

	public VaroLoggerManager getVaroLoggerManager() {
		return this.varoLoggerManager;
	}

	public MySQLClient getMysqlClient() {
		return this.mysqlClient;
	}

	public OutSideTimeChecker getOutsideTimeChecker() {
		return this.outsideTimeChecker;
	}

	public ReportHandler getReportHandler() {
		return this.reportHandler;
	}

	public SpawnHandler getSpawnHandler() {
		return this.spawnHandler;
	}

	public VaroGameHandler getVaroGameHandler() {
		return this.varoGameHandler;
	}

	public VaroPlayerHandler getVaroPlayerHandler() {
		return this.varoPlayerHandler;
	}

	public VaroTeamHandler getVaroTeamHandler() {
		return this.varoTeamHandler;
	}

	public DailyTasks getDailyTimer() {
		return this.dailyTimer;
	}
	
	public EnchantmentManager getEnchantmentManager() {
        return this.enchantmentManager;
    }

    public BotLauncher getBotLauncher() {
        return this.botLauncher;
    }

    public List<VaroBackup> getBackups() {
	    synchronized (this.backups) {
	        return Collections.unmodifiableList(new ArrayList<>(this.backups));
        }
    }

	public CustomCommandManager getCustomCommandManager() { return customCommandManager; }
}