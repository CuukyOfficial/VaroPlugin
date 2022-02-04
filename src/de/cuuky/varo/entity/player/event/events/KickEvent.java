package de.cuuky.varo.entity.player.event.events;

import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.event.BukkitEvent;
import de.cuuky.varo.entity.player.event.BukkitEventType;

public class KickEvent extends BukkitEvent {

	public KickEvent() {
		super(BukkitEventType.KICKED);
	}

	@Override
	public void onExec(VaroPlayer player) {
		if (!player.isMassRecordingKick()) {
			player.getStats().addSessionPlayed();
			player.getStats().removeReamainingSession();
		} else {
			player.setMassRecordingKick(false);
		}

		player.getStats().removeCountdown();
	}
}
