package de.cuuky.varo.game;

import de.cuuky.varo.Main;
import de.cuuky.varo.game.start.AutoStart;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.serialize.VaroSerializeObject;
import de.cuuky.varo.world.border.BorderDecreaseDayTimer;

public class GameHandler extends VaroSerializeObject {

	private static GameHandler instance;

	static {
		registerEnum(GameState.class);
		registerClass(AutoStart.class);
		registerClass(BorderDecreaseDayTimer.class);
	}

	public static void initialise() {
		if (instance == null) {
			instance = new GameHandler();
		}
	}

	private GameHandler() {
		super(Game.class, "/stats/game.yml");

		load();
	}

	@Override
	public void onSave() {
		clearOld();

		save("current", Game.getInstance(), getConfiguration());

		saveFile();
	}
}
