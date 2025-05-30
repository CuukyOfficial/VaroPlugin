package de.varoplugin.varo.player.stats.stat;

import de.varoplugin.varo.serialize.identifier.VaroSerializeField;
import de.varoplugin.varo.serialize.identifier.VaroSerializeable;

public enum PlayerState implements VaroSerializeable {

	@VaroSerializeField(enumValue = "ALIVE")
	ALIVE("ALIVE"),
	@VaroSerializeField(enumValue = "DEAD")
	DEAD("DEAD"),
	@VaroSerializeField(enumValue = "SPECTATOR")
	SPECTATOR("SPECTATOR");

	private String name;

	PlayerState(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public void onDeserializeEnd() {}

	@Override
	public void onSerializeStart() {}

	@Override
	public String toString() {
		return name;
	}
}