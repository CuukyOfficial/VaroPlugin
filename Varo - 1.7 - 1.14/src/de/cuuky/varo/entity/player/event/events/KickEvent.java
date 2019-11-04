package de.cuuky.varo.player.event.events;

import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.player.event.BukkitEvent;
import de.cuuky.varo.player.event.BukkitEventType;

public class KickEvent extends BukkitEvent {

	public KickEvent() {
		super(BukkitEventType.KICKED);
	}

	@Override
	public void onExec(VaroPlayer player) {
		if(!player.isMassRecordingKick()) {
			player.getStats().addSessionPlayed();
			player.getStats().setBan();
		} else {
			player.setMassRecordingKick(false);
		}

		player.getStats().removeCountdown();
	}
}
