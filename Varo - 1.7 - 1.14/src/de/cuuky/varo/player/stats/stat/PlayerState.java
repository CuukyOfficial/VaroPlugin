package de.cuuky.varo.player.stats.stat;

import de.cuuky.varo.serialize.identifier.VaroSerializeField;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;

public enum PlayerState implements VaroSerializeable {

	@VaroSerializeField(enumValue = "DEAD")
	DEAD("DEAD"), @VaroSerializeField(enumValue = "ALIVE")
	ALIVE("ALIVE"), @VaroSerializeField(enumValue = "SPECTATOR")
	SPECTATOR("SPECTATOR");

	private String name;

	private PlayerState(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public void onDeserializeEnd() {
	}

	@Override
	public void onSerializeStart() {
	}

	@Override
	public String toString() {
		return name;
	}

	public static PlayerState getByName(String name) {
		for (PlayerState state : values())
			if (state.getName().equalsIgnoreCase(name))
				return state;

		return null;
	}
}
