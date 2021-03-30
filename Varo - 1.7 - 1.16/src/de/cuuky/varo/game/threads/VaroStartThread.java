package de.cuuky.varo.game.threads;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import de.cuuky.cfw.version.VersionUtils;
import de.cuuky.cfw.version.types.Sounds;
import de.cuuky.varo.Main;
import de.cuuky.varo.api.VaroAPI;
import de.cuuky.varo.api.event.events.game.VaroStartEvent;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.game.VaroGame;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.logger.logger.EventLogger.LogType;

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
		if (VersionUtils.getOnlinePlayer().size() != 0)
			((Player) VersionUtils.getOnlinePlayer().toArray()[0]).getWorld().setTime(1000);

		if (startcountdown != 0)
			Main.getLanguageManager().broadcastMessage(ConfigMessages.GAME_START_COUNTDOWN).replace("%countdown%", startcountdown == 1 ? "einer" : String.valueOf(startcountdown));

		if (startcountdown == ConfigSetting.STARTCOUNTDOWN.getValueAsInt() || startcountdown == 1) {
			for (VaroPlayer pl1 : VaroPlayer.getOnlinePlayer()) {
				if (pl1.getStats().isSpectator())
					continue;

				Player pl = pl1.getPlayer();
				pl.setGameMode(GameMode.ADVENTURE);
				pl1.cleanUpPlayer();
			}
		}

		if (startcountdown == 5 || startcountdown == 4 || startcountdown == 3 || startcountdown == 2 || startcountdown == 1) {
			for (VaroPlayer vp : VaroPlayer.getOnlinePlayer()) {
				if (vp.getStats().isSpectator())
					continue;

				Player pl = vp.getPlayer();
				pl.playSound(pl.getLocation(), Sounds.NOTE_BASS_DRUM.bukkitSound(), 1, 1);

				String[] title = ConfigMessages.GAME_VARO_START_TITLE.getValue(vp).replace("%countdown%", String.valueOf(startcountdown)).split("\n");
				if (title.length != 0)
					vp.getNetworkManager().sendTitle(title[0], title.length == 2 ? title[1] : "");
			}
		}

		if (startcountdown == 0) {
			if (VaroAPI.getEventManager().executeEvent(new VaroStartEvent(game))) {
				startcountdown = ConfigSetting.STARTCOUNTDOWN.getValueAsInt();
				Bukkit.getScheduler().cancelTask(game.getStartScheduler());
				return;
			}

			Main.getVaroGame().setGamestate(GameState.STARTED);
			this.startcountdown = ConfigSetting.STARTCOUNTDOWN.getValueAsInt();

			Main.getVaroGame().getVaroWorldHandler().getMainWorld().getWorld().strikeLightningEffect(Main.getVaroGame().getVaroWorldHandler().getMainWorld().getWorld().getSpawnLocation());
			Main.getLanguageManager().broadcastMessage(ConfigMessages.GAME_VARO_START);
			Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, ConfigMessages.ALERT_GAME_STARTED.getValue());
			Bukkit.getScheduler().cancelTask(game.getStartScheduler());

			this.game.doStartStuff();

			game.setStartThread(null);
			return;
		}

		startcountdown--;
	}
}