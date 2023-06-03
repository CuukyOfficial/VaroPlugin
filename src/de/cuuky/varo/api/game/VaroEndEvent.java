package de.cuuky.varo.api.game;

import de.cuuky.varo.api.VaroEvent;
import de.cuuky.varo.game.VaroGame;
import de.cuuky.varo.game.end.WinnerCheck;

public class VaroEndEvent extends VaroEvent {

	private final VaroGame game;
	private final WinnerCheck check;

	public VaroEndEvent(VaroGame game, WinnerCheck check) {
		this.game = game;
		this.check = check;
	}

	public VaroGame getGame() {
		return this.game;
	}

	public WinnerCheck getCheck() {
		return this.check;
	}
}
