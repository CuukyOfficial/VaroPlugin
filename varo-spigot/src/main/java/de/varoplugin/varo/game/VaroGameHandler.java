package de.varoplugin.varo.game;

import de.varoplugin.varo.Main;
import de.varoplugin.varo.game.start.AutoStart;
import de.varoplugin.varo.game.world.border.decrease.BorderDecreaseDayTimer;
import de.varoplugin.varo.serialize.VaroSerializeObject;

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