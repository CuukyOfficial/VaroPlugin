package de.cuuky.varo.api.player;

import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.stat.PlayerState;

public class PlayerStateChangeEvent extends VaroPlayerEvent {

	private final PlayerState state;

	public PlayerStateChangeEvent(VaroPlayer player, PlayerState state) {
		super(player);

		this.state = state;
	}

	public PlayerState getState() {
		return this.state;
	}
}
