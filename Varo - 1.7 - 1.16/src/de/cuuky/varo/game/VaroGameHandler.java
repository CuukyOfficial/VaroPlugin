package de.cuuky.varo.game;

import de.cuuky.varo.Main;
import de.cuuky.varo.game.start.AutoStart;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.game.world.border.decrease.BorderDecreaseDayTimer;
import de.cuuky.varo.serialize.VaroSerializeObject;

public class VaroGameHandler extends VaroSerializeObject {

	static {
		registerEnum(GameState.class);
		registerClass(AutoStart.class);
		registerClass(BorderDecreaseDayTimer.class);
	}

	public VaroGameHandler() {
		super(VaroGame.class, "/stats/game.yml");

		load();

		if (Main.getVaroGame() == null)
			new VaroGame().init(); // When nothing got deserialized -> new game
	}

	@Override
	public void onSave() {
		clearOld();

		save("current", Main.getVaroGame(), getConfiguration());

		saveFile();
	}
}