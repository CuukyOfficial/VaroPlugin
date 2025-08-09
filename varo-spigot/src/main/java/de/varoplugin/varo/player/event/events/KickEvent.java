package de.varoplugin.varo.player.event.events;

import de.varoplugin.varo.player.VaroPlayer;
import de.varoplugin.varo.player.VaroPlayerDisconnect;
import de.varoplugin.varo.player.event.BukkitEvent;
import de.varoplugin.varo.player.event.BukkitEventType;

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

		VaroPlayerDisconnect disconnect = VaroPlayerDisconnect.getDisconnect(player.getRealUUID());
		if (disconnect != null)
		    disconnect.remove();
	}
}
