package de.cuuky.varo.game.state;

import de.cuuky.varo.serialize.identifier.VaroSerializeField;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;

public enum GameState implements VaroSerializeable {

	@VaroSerializeField(enumValue = "LOBBY")
	LOBBY("LOBBY"),
	@VaroSerializeField(enumValue = "STARTED")
	STARTED("STARTED"),
	@VaroSerializeField(enumValue = "END")
	END("END");

	private String name;

	GameState(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public void onDeserializeEnd() {}

	@Override
	public void onSerializeStart() {}
}
