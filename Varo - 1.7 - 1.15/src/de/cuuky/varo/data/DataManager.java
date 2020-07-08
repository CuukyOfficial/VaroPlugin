package de.cuuky.varo.data;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import de.cuuky.cfw.utils.ServerPropertiesReader;
import de.cuuky.varo.Main;
import de.cuuky.varo.alert.AlertHandler;
import de.cuuky.varo.ban.VaroPlayerBan;
import de.cuuky.varo.bot.discord.register.BotRegister;
import de.cuuky.varo.broadcast.Broadcaster;
import de.cuuky.varo.clientadapter.VaroBoardProvider;
import de.cuuky.varo.configuration.ConfigHandler;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.placeholder.MessagePlaceholderLoader;
import de.cuuky.varo.data.plugin.PluginLoader;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.VaroPlayerHandler;
import de.cuuky.varo.entity.team.VaroTeamHandler;
import de.cuuky.varo.game.VaroGameHandler;
import de.cuuky.varo.list.VaroList;
import de.cuuky.varo.list.VaroListManager;
import de.cuuky.varo.logger.VaroLoggerManager;
import de.cuuky.varo.mysql.MySQLClient;
import de.cuuky.varo.preset.DefaultPresetLoader;
import de.cuuky.varo.report.ReportHandler;
import de.cuuky.varo.serialize.VaroSerializeHandler;
import de.cuuky.varo.spawns.SpawnHandler;
import de.cuuky.varo.threads.daily.DailyTimer;
import de.cuuky.varo.utils.OutSideTimeChecker;
import de.cuuky.varo.utils.VaroUtils;

public class DataManager {
	
	private static final int SAVE_DELAY = 12000; 

	private Main ownerInstance;
	
	private ConfigHandler configHandler;
	private VaroGameHandler varoGameHandler;
	private VaroPlayerHandler varoPlayerHandler;
	private VaroTeamHandler varoTeamHandler;
	private SpawnHandler spawnHandler;
	private ReportHandler reportHandler;
	private AlertHandler alertHandler;
	private OutSideTimeChecker outsideTimeChecker;
	private MySQLClient mysqlClient;
	private VaroListManager listManager;
	private VaroLoggerManager varoLoggerManager;
	private Broadcaster broadcaster;
	private DailyTimer dailyTimer;
	private ServerPropertiesReader propertiesReader;
	private PluginLoader pluginLoader;

	private boolean doSave;

	public DataManager(Main ownerInstance) {
		this.ownerInstance = ownerInstance;
		
		Main.setDataManager(this);

		load();
		startAutoSave();

		doSave = true;
	}

	private void load() {
		VaroPlayerBan.loadBans();

		new DefaultPresetLoader();
		this.varoLoggerManager = new VaroLoggerManager();
		this.configHandler = new ConfigHandler();
		new MessagePlaceholderLoader();
		this.propertiesReader = new ServerPropertiesReader();
		this.varoGameHandler = new VaroGameHandler();
		this.varoPlayerHandler = new VaroPlayerHandler();
		this.varoTeamHandler = new VaroTeamHandler();
		this.spawnHandler = new SpawnHandler();
		this.reportHandler = new ReportHandler();
		this.alertHandler = new AlertHandler();
		this.outsideTimeChecker = new OutSideTimeChecker();
		this.mysqlClient = new MySQLClient();
		this.listManager = new VaroListManager();
		this.broadcaster = new Broadcaster();
		this.dailyTimer = new DailyTimer();

		Bukkit.getServer().setSpawnRadius(ConfigSetting.SPAWN_PROTECTION_RADIUS.getValueAsInt());
		VaroUtils.setWorldToTime();

		VaroPlayer.getOnlinePlayer().forEach(vp -> vp.update());

		this.pluginLoader = new PluginLoader();
	}

	private void startAutoSave() {
		Bukkit.getScheduler().scheduleAsyncRepeatingTask(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				reloadConfig();
				save();

				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

					@Override
					public void run() {
						reloadPlayerClients();
					}
				}, 1);
			}
		}, SAVE_DELAY, SAVE_DELAY);
	}

	public void reloadConfig() {
		VaroPlayerBan.loadBans();
		VaroList.reloadLists();
		Main.getCuukyFrameWork().getPlaceholderManager().clear();
		configHandler.reload();
		Main.getLanguageManager().loadLanguages();
		VaroBoardProvider.update();
	}

	public void reloadPlayerClients() {
		for (VaroPlayer vp : VaroPlayer.getOnlinePlayer()) 
			vp.update();
	}

	public void save() {
		if (!doSave)
			return;

		VaroSerializeHandler.saveAll();
		VaroList.saveLists();

		try {
			BotRegister.saveAll();
		} catch (NoClassDefFoundError e) {}
	}

	public PluginLoader getPluginLoader() {
		return this.pluginLoader;
	}

	public void setDoSave(boolean doSave) {
		this.doSave = doSave;
	}

	public ServerPropertiesReader getPropertiesReader() {
		return this.propertiesReader;
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
}