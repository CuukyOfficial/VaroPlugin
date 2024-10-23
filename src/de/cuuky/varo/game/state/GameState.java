package de.cuuky.varo.game.state;

import de.cuuky.varo.serialize.identifier.VaroSerializeField;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;

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