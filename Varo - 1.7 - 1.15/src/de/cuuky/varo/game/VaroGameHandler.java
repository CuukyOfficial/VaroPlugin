package de.cuuky.varo.game;

import de.cuuky.varo.game.start.AutoStart;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.serialize.VaroSerializeObject;
import de.cuuky.varo.world.border.decrease.BorderDecreaseDayTimer;

public class VaroGameHandler extends VaroSerializeObject {

	private static VaroGameHandler instance;

	static {
		registerEnum(GameState.class);
		registerClass(AutoStart.class);
		registerClass(BorderDecreaseDayTimer.class);
	}

	private VaroGameHandler() {
		super(VaroGame.class, "/stats/game.yml");

		load();

		if(VaroGame.getInstance() == null) {
			VaroGame.initialize(); // When nothing got deserialized -> new game
		}
	}

	@Override
	public void onSave() {
		clearOld();

		save("current", VaroGame.getInstance(), getConfiguration());

		saveFile();
	}

	public static void initialize() {
		if(instance == null) {
			instance = new VaroGameHandler();
		}
	}
}