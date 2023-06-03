package de.cuuky.varo.api.game;

import de.cuuky.varo.api.VaroEvent;
import de.cuuky.varo.game.VaroGame;

public class VaroStartEvent extends VaroEvent {

	private final VaroGame game;

	public VaroStartEvent(VaroGame game) {
		this.game = game;
	}

	public VaroGame getGame() {
		return this.game;
	}
}
