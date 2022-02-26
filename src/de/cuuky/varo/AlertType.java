package de.cuuky.varo;

import de.cuuky.varo.serialize.identifier.VaroSerializeField;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;

import java.util.Arrays;

public enum AlertType implements VaroSerializeable {

	@VaroSerializeField(enumValue = "BLOODLUST")
	BLOODLUST("BLOODLUST"),

	@VaroSerializeField(enumValue = "COMBATLOG")
	COMBATLOG("COMBATLOG"),

	@VaroSerializeField(enumValue = "DISCONNECT")
	DISCONNECT("DISCONNECT"),

	@VaroSerializeField(enumValue = "INVENTORY_CLEAR")
	INVENTORY_CLEAR("INVENTORY_CLEAR"),

	@VaroSerializeField(enumValue = "NAME_SWITCH")
	NAME_SWITCH("NAME_SWITCH"),

	@VaroSerializeField(enumValue = "NO_JOIN")
	NO_JOIN("NO_JOIN"),

	@VaroSerializeField(enumValue = "NO_YOUTUBE_UPLOAD")
	NO_YOUTUBE_UPLOAD("NO_YOUTUBE_UPLOAD"),

	@VaroSerializeField(enumValue = "STRIKE")
	STRIKE("STRIKE"),

	@VaroSerializeField(enumValue = "UPDATE_AVAILABLE")
	UPDATE_AVAILABLE("UPDATE_AVAILABLE");

	private String name;

	AlertType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public void onDeserializeEnd() {}

	@Override
	public void onSerializeStart() {}

    public static AlertType getByName(String name) {
        return Arrays.stream(values()).filter(type -> type.name.equals(name)).findAny().orElse(null);
    }
}