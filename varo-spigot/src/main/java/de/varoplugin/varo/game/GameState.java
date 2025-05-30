package de.varoplugin.varo.game;

import de.varoplugin.varo.serialize.identifier.VaroSerializeField;
import de.varoplugin.varo.serialize.identifier.VaroSerializeable;

public enum GameState implements VaroSerializeable {

	@VaroSerializeField(enumValue = "LOBBY")
	LOBBY,

	@VaroSerializeField(enumValue = "STARTED") // TODO Rename this in major version upgrade
	STARTED,
	
	@VaroSerializeField(enumValue = "FINALE")
    FINALE,

	@VaroSerializeField(enumValue = "END")
	END;

	@Override
	public void onDeserializeEnd() {}

	@Override
	public void onSerializeStart() {}

	@Override
	public String toString() {
		return this.name();
	}
}