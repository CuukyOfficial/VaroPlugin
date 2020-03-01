package de.cuuky.varo.version;

public enum BukkitVersion {

	ONE_10(10),
	ONE_11(11),
	ONE_12(12),
	ONE_13(13),
	ONE_14(14),
	ONE_15(15),
	ONE_7(7),
	ONE_8(8),
	ONE_9(9);

	private int identifier;

	BukkitVersion(int identifier) {
		this.identifier = identifier;
	}

	public boolean isHigherThan(BukkitVersion ver) {
		return identifier > ver.identifier;
	}

	public static BukkitVersion getVersion(String v) {
		int versionNumber = Integer.valueOf(v.split("1_")[1].split("_")[0]);
		for(BukkitVersion version : values())
			if(versionNumber == version.identifier)
				return version;

		if(versionNumber < values()[1].identifier)
			return values()[0];
		else if(versionNumber > values()[values().length - 2].identifier)
			return values()[values().length - 1];

		return null;
	}
}