package de.cuuky.varo.data;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import de.cuuky.varo.Main;
import de.cuuky.varo.alert.AlertHandler;
import de.cuuky.varo.bot.discord.BotRegister;
import de.cuuky.varo.broadcast.Broadcaster;
import de.cuuky.varo.command.custom.CustomCommandManager;
import de.cuuky.varo.configuration.ConfigHandler;
import de.cuuky.varo.configuration.configurations.config.ActionbarConfig;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.config.ScoreboardConfig;
import de.cuuky.varo.configuration.configurations.config.TablistConfig;
import de.cuuky.varo.configuration.placeholder.MessagePlaceholderLoader;
import de.cuuky.varo.game.LobbyItem;
import de.cuuky.varo.game.VaroGameHandler;
import de.cuuky.varo.list.VaroList;
import de.cuuky.varo.list.VaroListManager;
import de.cuuky.varo.logger.VaroLoggerManager;
import de.cuuky.varo.mysql.MySQLClient;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.player.VaroPlayerHandler;
import de.cuuky.varo.preset.DefaultPresetLoader;
import de.cuuky.varo.report.ReportHandler;
import de.cuuky.varo.serialize.VaroSerializeHandler;
import de.cuuky.varo.spawns.SpawnHandler;
import de.cuuky.varo.team.VaroTeamHandler;
import de.cuuky.varo.threads.daily.DailyTimer;
import de.cuuky.varo.utils.OutSideTimeChecker;
import de.cuuky.varo.utils.VaroUtils;
import de.varoplugin.cfw.player.hud.NameTagGroup;
import de.varoplugin.cfw.version.ServerVersion;
import de.varoplugin.cfw.version.VersionUtils;

public class DataManager {

	private static final int SAVE_DELAY = 12000;

	private Main ownerInstance;

	private VaroLoggerManager varoLoggerManager;
	private ConfigHandler configHandler;
	private ScoreboardConfig scoreboardConfig;
	private TablistConfig tablistConfig;
	private ActionbarConfig actionbarConfig;
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
	private DailyTimer dailyTimer;
	private CustomCommandManager customCommandManager;

	private boolean doSave;

	public DataManager(Main ownerInstance) {
		this.ownerInstance = ownerInstance;

		Main.setDataManager(this);
	}

	public void preLoad() {
		this.configHandler = new ConfigHandler();
		Dependencies.loadOptional(this.ownerInstance);
		this.scoreboardConfig = new ScoreboardConfig();
		this.tablistConfig = new TablistConfig();
		this.actionbarConfig = new ActionbarConfig();
		this.defaultNameTagGroup = new NameTagGroup();
		this.spectatorNameTagGroup = new NameTagGroup();
		this.varoLoggerManager = new VaroLoggerManager();
		new DefaultPresetLoader();
	}

	public void load() {
		new MessagePlaceholderLoader();
		this.varoPlayerHandler = new VaroPlayerHandler();
		this.varoTeamHandler = new VaroTeamHandler();
		this.varoGameHandler = new VaroGameHandler();
		if (!Main.getVaroGame().hasStarted())
			VaroPlayer.getOnlinePlayer().forEach(LobbyItem::giveItems);
		this.spawnHandler = new SpawnHandler();
		this.reportHandler = new ReportHandler();
		this.alertHandler = new AlertHandler();
		this.outsideTimeChecker = new OutSideTimeChecker();
		this.mysqlClient = new MySQLClient();
		this.listManager = new VaroListManager();
		this.broadcaster = new Broadcaster();
		this.dailyTimer = new DailyTimer();
		this.customCommandManager = new CustomCommandManager();

		if (ConfigSetting.BLOCK_ADVANCEMENTS.getValueAsBoolean()
				&& !VersionUtils.getVersion().isHigherThan(ServerVersion.ONE_11))
			VersionUtils.getVersionAdapter().setServerProperty("announce-player-achievements", false);

		Bukkit.getServer().setSpawnRadius(ConfigSetting.SPAWN_PROTECTION_RADIUS.getValueAsInt());
		VaroUtils.updateWorldTime();

		VaroPlayer.getOnlinePlayer().forEach(vp -> vp.update());

		this.startAutoSave();

		this.doSave = true;
	}

	private void startAutoSave() {

		new BukkitRunnable() {
			@Override
			public void run() {
				DataManager.this.reloadConfig();
				DataManager.this.save();

				new BukkitRunnable() {
					@Override
					public void run() {
						DataManager.this.reloadPlayerClients();
					}
				}.runTask(Main.getInstance());
			}
		}.runTaskTimerAsynchronously(Main.getInstance(), SAVE_DELAY, SAVE_DELAY);
	}

	public void reloadConfig() {
		VaroList.loadLists();
		this.customCommandManager.reload();
		Main.getCuukyFrameWork().getPlaceholderManager().clear();
		this.configHandler.reload();
		Main.getLanguageManager().loadLanguages();
	}

	public void reloadPlayerClients() {
		for (VaroPlayer vp : VaroPlayer.getOnlinePlayer())
			vp.update();
	}

	public void save() {
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

	public ScoreboardConfig getScoreboardConfig() {
		return scoreboardConfig;
	}

	public TablistConfig getTablistConfig() {
		return tablistConfig;
	}

	public ActionbarConfig getActionbarConfig() {
		return this.actionbarConfig;
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

	public DailyTimer getDailyTimer() {
		return this.dailyTimer;
	}

	public JavaPlugin getOwnerInstance() {
		return this.ownerInstance;
	}

	public CustomCommandManager getCustomCommandManager() { return customCommandManager; }
}