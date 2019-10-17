package de.cuuky.varo.spawns.spawn;

import de.cuuky.varo.serialize.identifier.VaroSerializeField;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;

public enum SpawnType implements VaroSerializeable {

	@VaroSerializeField(enumValue = "NUMBERS")
	NUMBERS, @VaroSerializeField(enumValue = "PLAYER")
	PLAYER;

	@Override
	public void onDeserializeEnd() {
	}

	@Override
	public void onSerializeStart() {
	}

}
