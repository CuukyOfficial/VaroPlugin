package de.cuuky.varo.report;

import de.cuuky.varo.version.types.Materials;
import org.bukkit.Material;

import de.cuuky.varo.serialize.identifier.VaroSerializeField;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;

public enum ReportReason implements VaroSerializeable {

	@VaroSerializeField(enumValue = "HACKING")
	HACKING("Hacking", Materials.TNT.parseMaterial(), "Benutze dies, falls jemand hackt oder exploited."),
	@VaroSerializeField(enumValue = "TEAMING")
	TEAMING("Teaming", Materials.DIRT.parseMaterial(), "Benutze dies, falls Personen aus unterschiedlichen Teams teamen."),
	@VaroSerializeField(enumValue = "CHAT")
	CHAT("Chat", Materials.OAK_WOOD.parseMaterial(), "Benutze dies, falls jemand sich gegen die Chatregeln verhält."),
	@VaroSerializeField(enumValue = "XRAY")
	XRAY("Xray", Materials.STONE.parseMaterial(), "Benutze dies, falls jemand Xray benutzt."),
	@VaroSerializeField(enumValue = "TROLLING")
	TROLLING("Trolling", Materials.BLACK_WOOL.parseMaterial(), "Benutze dies, falls jemand trollt.");


	private String name;
	private Material material;
	private String description;

	ReportReason(String s, Material mat, String desc) {
		this.name = s;
		this.material = mat;
		this.description = desc;
	}

	public String getName() {
		return name;
	}

	public Material getMaterial() {
		return material;
	}

	public String getDescription() {
		return description;
	}

	public static ReportReason getByName(String name) {
		for(ReportReason reasons : values()) {
			if(reasons.getName().equals(name)) {
				return reasons;
			}
		}
		return null;
	}

	@Override
	public void onDeserializeEnd() {}

	@Override
	public void onSerializeStart() {}
}