package de.cuuky.varo.report;

import org.bukkit.Material;

import de.cuuky.varo.serialize.identifier.VaroSerializeField;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;

public enum ReportReason implements VaroSerializeable {

	@VaroSerializeField(enumValue = "HACKING")
	HACKING("Hacking", Material.TNT, "Benutze dies falls du jemanden hacken siehst"),
	@VaroSerializeField(enumValue = "TROLLING")
	TROLLING("Trolling", Material.DIRT, "Benutze dies falls dich jemand, oder dein Teammate, trollt");

	private String name;
	private Material material;
	private String description;

	private ReportReason(String s, Material mat, String desc) {
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