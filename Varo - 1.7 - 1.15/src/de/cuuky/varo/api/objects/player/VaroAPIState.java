package de.cuuky.varo.api.objects.player;

import de.cuuky.varo.entity.player.stats.stat.PlayerState;

public enum VaroAPIState {

	ALIVE(PlayerState.ALIVE),
	DEAD(PlayerState.DEAD),
	SPECTATOR(PlayerState.SPECTATOR);

	private PlayerState origin;

	private VaroAPIState(PlayerState origin) {
		this.origin = origin;
	}

	public PlayerState getOrigin() {
		return origin;
	}

	public static VaroAPIState getState(PlayerState state) {
		for (VaroAPIState apistate : values())
			if (apistate.getOrigin() == state)
				return apistate;

		return null;
	}
}
