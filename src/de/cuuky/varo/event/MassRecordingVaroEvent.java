package de.cuuky.varo.event;

import de.cuuky.varo.app.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.event.BukkitEventType;
import de.cuuky.varo.logger.logger.EventLogger.LogType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;

// TODO: Fu*king recode
public class MassRecordingVaroEvent extends VaroEvent {

	private final ArrayList<Integer[]> countdowns;
	private BukkitTask scheduler;
	private int timer;
	private boolean timerEnd;

	public MassRecordingVaroEvent() {
		super(null);

		this.timerEnd = false;
		this.countdowns = new ArrayList<>();
	}

	public int getCountdown(VaroPlayer vp) {
		for (Integer[] Countdown : countdowns) {
			if (vp.getId() == Countdown[0]) {
				return Countdown[1];
			}
		}
		return 0;
	}

	public int getTimer() {
		return timer;
	}

	@Override
	public void onDisable() {
		scheduler.cancel();

		for (Integer[] Speicher : countdowns) {
			VaroPlayer vp = VaroPlayer.getPlayer(Speicher[0]);
			vp.getStats().setCountdown(Speicher[1]);
			if (Speicher[1] == ConfigSetting.PLAY_TIME.getValueAsInt() * 60) {
				if (vp.isOnline()) {
					vp.setMassRecordingKick(true);

					Main.getLanguageManager().broadcastMessage(ConfigMessages.QUIT_KICK_BROADCAST, vp);
					vp.onEvent(BukkitEventType.KICKED);
					vp.getPlayer().kickPlayer(ConfigMessages.KICK_MASS_REC_SESSION_OVER.getValue(vp, vp));
				}
			}
		}

		if (!timerEnd) {
			for (VaroPlayer vp : VaroPlayer.getOnlinePlayer()) {
				vp.getVersionAdapter().sendTitle("Ende", "Die Massenaufnahme wurde beendet.");

				Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, "Die Massenaufnahme wurde vorzeitig beendet.");
			}
		} else {
			for (VaroPlayer vp : VaroPlayer.getOnlinePlayer()) {
				vp.getVersionAdapter().sendTitle("Ende", "Die Massenaufnahme ist zu Ende.");

				Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, "Die Massenaufnahme ist zu Ende.");
			}
		}

		countdowns.clear();
		timerEnd = false;
	}

	@Override
	public void onEnable() {
		countdowns.clear();
		timerEnd = false;

		for (VaroPlayer vp : VaroPlayer.getVaroPlayers()) {
			Integer[] save = { vp.getId(), vp.getStats().getCountdown() };
			countdowns.add(save);
			vp.getStats().setCountdown(vp.getStats().getCountdown() + 60 * ConfigSetting.MASS_RECORDING_TIME.getValueAsInt());

			vp.setalreadyHadMassProtectionTime(false);
		}

		for (VaroPlayer vp : VaroPlayer.getOnlinePlayer()) {
			vp.setalreadyHadMassProtectionTime(true);
		}

		for (VaroPlayer vp : VaroPlayer.getOnlineAndAlivePlayer()) {
			vp.getStats().addSessionPlayed();
			Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.JOIN_LEAVE, vp.getName() + " ist auf dem Server und nimmt an der Massenaufnahme teil.");
		}

		timer = ConfigSetting.MASS_RECORDING_TIME.getValueAsInt() * 60;

		Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, ConfigSetting.MASS_RECORDING_TIME.getValueAsInt() == 1 ? "DIE MASSENAUFNAHME WURDE GESTARTET UND DAUERT EINE MINUTE!" : "DIE MASSENAUFNAHME WURDE GESTARTET UND DAUERT " + ConfigSetting.MASS_RECORDING_TIME.getValueAsInt() + " MINUTEN!");
		for (VaroPlayer vp : VaroPlayer.getOnlinePlayer())
			vp.getVersionAdapter().sendTitle("Massenaufnahme", ConfigSetting.MASS_RECORDING_TIME.getValueAsInt() == 1 ? "Alle koennen fuer eine Minute joinen." : "Alle koennen fuer" + ConfigSetting.MASS_RECORDING_TIME.getValueAsInt() + " Minuten joinen.");

		scheduler = new BukkitRunnable() {
			@Override
			public void run() {
				if (timer < 1) {
					timerEnd = true;
					disable();
				}

				timer -= 1;
			}
		}.runTaskTimer(Main.getInstance(), 0L, 20L);
	}
}