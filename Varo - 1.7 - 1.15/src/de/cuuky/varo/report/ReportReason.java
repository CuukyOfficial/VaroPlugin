package de.cuuky.varo.report;

import org.bukkit.Material;

import de.cuuky.varo.serialize.identifier.VaroSerializeField;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;
import de.cuuky.varo.version.types.Materials;

public enum ReportReason implements VaroSerializeable {

	@VaroSerializeField(enumValue = "CHAT")
	CHAT("Chat", Materials.OAK_WOOD.parseMaterial(), "Benutze dies, falls jemand sich gegen die Chatregeln verhält."),
	@VaroSerializeField(enumValue = "HACKING")
	HACKING("Hacking", Materials.TNT.parseMaterial(), "Benutze dies, falls jemand hackt oder exploited."),
	@VaroSerializeField(enumValue = "TEAMING")
	TEAMING("Teaming", Materials.DIRT.parseMaterial(), "Benutze dies, falls Personen aus unterschiedlichen Teams teamen."),
	@VaroSerializeField(enumValue = "TROLLING")
	TROLLING("Trolling", Materials.BLACK_WOOL.parseMaterial(), "Benutze dies, falls jemand trollt."),
	@VaroSerializeField(enumValue = "XRAY")
	XRAY("Xray", Materials.STONE.parseMaterial(), "Benutze dies, falls jemand Xray benutzt.");

	private String description;
	private Material material;
	private String name;

	ReportReason(String s, Material mat, String desc) {
		this.name = s;
		this.material = mat;
		this.description = desc;
	}

	public String getDescription() {
		return description;
	}

	public Material getMaterial() {
		return material;
	}

	public String getName() {
		return name;
	}

	@Override
	public void onDeserializeEnd() {}

	@Override
	public void onSerializeStart() {}

	public static ReportReason getByName(String name) {
		for(ReportReason reasons : values()) {
			if(reasons.getName().equals(name)) {
				return reasons;
			}
		}
		return null;
	}
}