package de.cuuky.varo.api.objects.game;

import de.cuuky.varo.game.state.GameState;

public enum VaroAPIGameState {

	END(GameState.END),
	LOBBY(GameState.LOBBY),
	RUNNING(GameState.STARTED);

	private GameState origin;

	private VaroAPIGameState(GameState origin) {
		this.origin = origin;
	}

	public GameState getOrigin() {
		return origin;
	}

	public static VaroAPIGameState getState(GameState state) {
		for(VaroAPIGameState apistate : values())
			if(apistate.getOrigin() == state)
				return apistate;

		return null;
	}

}
