package de.cuuky.varo.game;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.cryptomorin.xseries.XSound;

import de.cuuky.varo.Main;
import de.cuuky.varo.api.game.VaroStartEvent;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.logger.logger.EventLogger.LogType;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.utils.EventUtils;
import de.varoplugin.cfw.version.VersionUtils;

public class VaroStartThread extends BukkitRunnable {

	private final VaroGame game;
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
		VersionUtils.getVersionAdapter().getOnlinePlayers().stream().findFirst().ifPresent(player -> player.getWorld().setTime(1000));

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
				pl.playSound(pl.getLocation(), XSound.BLOCK_NOTE_BLOCK_BASEDRUM.get(), 1, 1);

				String countdownString = String.valueOf(startcountdown);
				String title = ConfigMessages.GAME_VARO_START_TITLE.getValue(vp).replace("%countdown%", countdownString);
				String subtitle = ConfigMessages.GAME_VARO_START_SUBTITLE.getValue(vp).replace("%countdown%", countdownString);
				if (!title.isEmpty() || !subtitle.isEmpty())
					vp.getVersionAdapter().sendTitle(title, subtitle);
			}
		}

		if (startcountdown == 0) {
			if (EventUtils.callEvent(new VaroStartEvent(game))) {
				startcountdown = ConfigSetting.STARTCOUNTDOWN.getValueAsInt();
				cancel();
				return;
			}

			this.startcountdown = ConfigSetting.STARTCOUNTDOWN.getValueAsInt();

			Main.getVaroGame().getVaroWorldHandler().getMainWorld().getWorld().strikeLightningEffect(Main.getVaroGame().getVaroWorldHandler().getMainWorld().getWorld().getSpawnLocation());
			Main.getLanguageManager().broadcastMessage(ConfigMessages.GAME_VARO_START);
			Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, ConfigMessages.ALERT_GAME_STARTED.getValue());
			cancel();

			this.game.start();

			this.game.setStartThread(null);
			return;
		}

		startcountdown--;
	}
}