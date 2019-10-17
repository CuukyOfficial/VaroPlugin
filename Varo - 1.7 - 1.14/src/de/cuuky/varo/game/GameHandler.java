package de.cuuky.varo.game;

import de.cuuky.varo.Main;
import de.cuuky.varo.game.start.AutoStart;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.serialize.VaroSerializeHandler;
import de.cuuky.varo.world.border.BorderDecreaseDayTimer;

public class GameHandler extends VaroSerializeHandler {

	static {
		VaroSerializeHandler.registerEnum(GameState.class);
		VaroSerializeHandler.registerClass(AutoStart.class);
		VaroSerializeHandler.registerClass(BorderDecreaseDayTimer.class);
	}

	public GameHandler() {
		super(Game.class, "/stats/game.yml");

		load();
		if(Main.getGame() == null)
			Main.setGame(new Game(true));
	}

	@Override
	public void onSave() {
		clearOld();

		save("current", Main.getGame(), getConfiguration());

		saveFile();
	}
}
