package de.cuuky.varo.api.event.events.player;

import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.player.stats.stat.PlayerState;

public class PlayerStateChangeEvent extends VaroPlayerEvent {

	private PlayerState state;
	public PlayerStateChangeEvent(VaroPlayer player, PlayerState state) {
		super(player, true);
		
		this.state = state;
	}

	public PlayerState getState() {
		return state;
	}
}
