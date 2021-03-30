package de.cuuky.varo.api.event.events.player;

import de.cuuky.varo.api.event.VaroAPIEvent;
import de.cuuky.varo.api.objects.player.VaroAPIPlayer;
import de.cuuky.varo.entity.player.VaroPlayer;

public class VaroPlayerEvent extends VaroAPIEvent {

	private VaroAPIPlayer player;

	public VaroPlayerEvent(VaroPlayer player, boolean cancelAble) {
		super(cancelAble);

		this.player = new VaroAPIPlayer(player);
	}

	public VaroAPIPlayer getPlayer() {
		return player;
	}
}
