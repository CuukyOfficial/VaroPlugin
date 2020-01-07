package de.cuuky.varo.entity.player.event;

import java.util.ArrayList;
import java.util.List;

import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.event.events.DeathEvent;
import de.cuuky.varo.entity.player.event.events.JoinEvent;
import de.cuuky.varo.entity.player.event.events.KickEvent;
import de.cuuky.varo.entity.player.event.events.KillEvent;
import de.cuuky.varo.entity.player.event.events.QuitEvent;

public class BukkitEvent {

	private static List<BukkitEvent> events;

	static {
		events = new ArrayList<BukkitEvent>();

		new DeathEvent();
		new KickEvent();
		new JoinEvent();
		new QuitEvent();
		new KillEvent();
	}

	protected BukkitEventType eventType;

	public BukkitEvent(VaroPlayer player, BukkitEventType eventType) {
		for(BukkitEvent event : events)
			if(event.getEventType().equals(eventType))
				event.onExec(player);
	}

	protected BukkitEvent(BukkitEventType eventType) {
		this.eventType = eventType;

		events.add(this);
	}

	public BukkitEventType getEventType() {
		return eventType;
	}

	public void onExec(VaroPlayer player) {}
}
