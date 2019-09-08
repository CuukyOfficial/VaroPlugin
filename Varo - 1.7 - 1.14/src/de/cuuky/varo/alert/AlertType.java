package de.cuuky.varo.alert;

import de.cuuky.varo.serialize.identifier.VaroSerializeField;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;

public enum AlertType implements VaroSerializeable {

	@VaroSerializeField(enumValue = "STRIKE")
	STRIKE("STRIKE"),
	@VaroSerializeField(enumValue = "NO_YOUTUBE_UPLOAD")
	NO_YOUTUBE_UPLOAD("NO_YOUTUBE_UPLOAD"),
	@VaroSerializeField(enumValue = "COMBATLOG")
	COMBATLOG("COMBATLOG"),
	@VaroSerializeField(enumValue = "BLOODLUST")
	BLOODLUST("BLOODLUST"),
	@VaroSerializeField(enumValue = "NAME_SWITCH")
	NAME_SWITCH("NAME_SWITCH"),
	@VaroSerializeField(enumValue = "INVENTORY_CLEAR")
	INVENTORY_CLEAR("INVENTORY_CLEAR"),
	@VaroSerializeField(enumValue = "UPDATE_AVAILABLE")
	UPDATE_AVAILABLE("UPDATE_AVAILABLE"),
	@VaroSerializeField(enumValue = "NO_JOIN")
	NO_JOIN("NO_JOIN"),
	@VaroSerializeField(enumValue = "DISCONNECT")
	DISCONNECT("DISCONNECT");

	private String name;

	private AlertType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public void onDeserializeEnd() {}

	@Override
	public void onSerializeStart() {}
}
