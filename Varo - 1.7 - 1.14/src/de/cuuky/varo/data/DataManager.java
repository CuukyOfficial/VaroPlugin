package de.cuuky.varo.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;

import de.cuuky.varo.Main;
import de.cuuky.varo.alert.AlertHandler;
import de.cuuky.varo.bot.discord.register.BotRegister;
import de.cuuky.varo.broadcast.Broadcaster;
import de.cuuky.varo.config.ConfigFailureDetector;
import de.cuuky.varo.config.ConfigHandler;
import de.cuuky.varo.game.GameHandler;
import de.cuuky.varo.list.ListHandler;
import de.cuuky.varo.list.VaroList;
import de.cuuky.varo.logger.LoggerMaster;
import de.cuuky.varo.mysql.MySQL;
import de.cuuky.varo.player.PlayerHandler;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.report.ReportHandler;
import de.cuuky.varo.scoreboard.ScoreboardHandler;
import de.cuuky.varo.serialize.VaroSerializeHandler;
import de.cuuky.varo.spawns.SpawnHandler;
import de.cuuky.varo.team.TeamHandler;
import de.cuuky.varo.threads.OutSideTimeChecker;
import de.cuuky.varo.world.WorldHandler;

public class DataManager {

	private WorldHandler worldHandler;
	private ConfigHandler configHandler;
	private ScoreboardHandler scoreboardHandler;
	private MySQL mysql;
	private OutSideTimeChecker timeChecker;
	private ListHandler listHandler;

	private boolean doSave;

	public DataManager() {
		load();
		startAutoSave();
		doSave = true;
	}

	private void load() {
		new ConfigFailureDetector();

		copyDefaultPresets();
		this.configHandler = new ConfigHandler();

		Main.setLogger(new LoggerMaster());
		new GameHandler();
		new PlayerHandler();
		new TeamHandler();
		new SpawnHandler();
		this.worldHandler = new WorldHandler();
		this.scoreboardHandler = new ScoreboardHandler();
		new ReportHandler();
		new AlertHandler();
		this.timeChecker = new OutSideTimeChecker();
		this.mysql = new MySQL();
		this.listHandler = new ListHandler();
		new Broadcaster();

		VaroPlayer.getOnlinePlayer().forEach(vp -> vp.update());
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
		Main.getDataManager().getConfigHandler().reload();
		Main.getDataManager().getScoreboardHandler().loadScores();

		for(VaroPlayer vp : VaroPlayer.getOnlinePlayer()) {
			vp.getPlayer().getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
			Main.getDataManager().getScoreboardHandler().sendScoreBoard(vp);
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

	public ListHandler getItemHandler() {
		return listHandler;
	}

	public OutSideTimeChecker getTimeChecker() {
		return timeChecker;
	}

	public ConfigHandler getConfigHandler() {
		return configHandler;
	}

	public WorldHandler getWorldHandler() {
		return worldHandler;
	}

	public ScoreboardHandler getScoreboardHandler() {
		return scoreboardHandler;
	}

	public MySQL getMysql() {
		return mysql;
	}
}