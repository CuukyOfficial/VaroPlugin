package de.varoplugin.varo.game;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.cryptomorin.xseries.XSound;

import de.varoplugin.cfw.version.VersionUtils;
import de.varoplugin.varo.Main;
import de.varoplugin.varo.api.game.VaroStartEvent;
import de.varoplugin.varo.config.language.Messages;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;
import de.varoplugin.varo.logger.logger.EventLogger.LogType;
import de.varoplugin.varo.player.VaroPlayer;
import de.varoplugin.varo.utils.EventUtils;
import io.github.almightysatan.slams.Placeholder;

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
		    Messages.GAME_START_COUNTDOWN.broadcast(Placeholder.constant("start-countdown", String.valueOf(this.startcountdown)));

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

				String title = Messages.GAME_START_TITLE.value(vp ,Placeholder.constant("start-countdown", String.valueOf(this.startcountdown)));
				String subtitle = Messages.GAME_START_SUBTITLE.value(vp, Placeholder.constant("start-countdown", String.valueOf(this.startcountdown)));
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
			Messages.GAME_START_VARO.broadcast();
			Messages.LOG_GAME_STARTED.log(LogType.LOG);
			cancel();

			this.game.start();

			this.game.setStartThread(null);
			return;
		}

		startcountdown--;
	}
}