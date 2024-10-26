package de.cuuky.varo.report;

import org.bukkit.Material;

import com.cryptomorin.xseries.XMaterial;

import de.cuuky.varo.serialize.identifier.VaroSerializeField;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;

public enum ReportReason implements VaroSerializeable {

	@VaroSerializeField(enumValue = "CHAT")
	CHAT("Chat", XMaterial.OAK_WOOD.parseMaterial(), "Benutze dies, falls jemand sich gegen die Chatregeln verhaelt."),

	@VaroSerializeField(enumValue = "HACKING")
	HACKING("Hacking", XMaterial.TNT.parseMaterial(), "Benutze dies, falls jemand hackt oder exploited."),

	@VaroSerializeField(enumValue = "TEAMING")
	TEAMING("Teaming", XMaterial.DIRT.parseMaterial(), "Benutze dies, falls Personen aus unterschiedlichen Teams teamen."),

	@VaroSerializeField(enumValue = "TROLLING")
	TROLLING("Trolling", XMaterial.BLACK_WOOL.parseMaterial(), "Benutze dies, falls jemand trollt."),

	@VaroSerializeField(enumValue = "XRAY")
	XRAY("Xray", XMaterial.STONE.parseMaterial(), "Benutze dies, falls jemand Xray benutzt.");

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
		for (ReportReason reasons : values()) {
			if (reasons.getName().equals(name)) {
				return reasons;
			}
		}
		return null;
	}
}