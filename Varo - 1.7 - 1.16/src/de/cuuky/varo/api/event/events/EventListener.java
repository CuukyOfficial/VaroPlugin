package de.cuuky.varo.api.event.events;

import de.cuuky.varo.api.VaroAPI;
import de.cuuky.varo.api.event.events.player.strike.PlayerStrikeReceiveEvent;
import de.cuuky.varo.api.event.register.VaroEventMethod;
import de.cuuky.varo.api.event.register.VaroListener;

public class EventListener implements VaroListener {

	static {
		VaroAPI.getEventManager().registerEvent(new EventListener());
	}

	@VaroEventMethod
	public void onPlayerStrike(PlayerStrikeReceiveEvent event) {
//		System.out.println(event.getStrike().getReason());
	}
}
