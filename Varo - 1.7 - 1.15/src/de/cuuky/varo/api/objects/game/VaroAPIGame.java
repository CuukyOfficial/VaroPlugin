package de.cuuky.varo.api.objects.game;

import org.bukkit.Location;

import de.cuuky.varo.game.Game;

public class VaroAPIGame {

	private Game game;

	public VaroAPIGame(Game game) {
		this.game = game;
	}

	public VaroAPIGameState getState() {
		return VaroAPIGameState.getState(game.getGameState());
	}

	public boolean isStarting() {
		return game.isStarting();
	}

	public void setLobbyLocation(Location lobby) {
		game.setLobby(lobby);
	}
}
