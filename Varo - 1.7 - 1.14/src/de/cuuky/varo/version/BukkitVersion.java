package de.cuuky.varo.version;

public enum BukkitVersion {

	ONE_7(7), ONE_8(8), ONE_9(9), ONE_10__ONE_11(10, 11), ONE_12(12), ONE_13__ONE__14(13, 14);

	private int[] identifier;

	private BukkitVersion(int... identifier) {
		this.identifier = identifier;
	}

	public int[] getIdentifier() {
		return identifier;
	}

	public boolean isHigherThan(BukkitVersion ver) {
		return getIdentifier()[0] > ver.getIdentifier()[0];
	}

	public static BukkitVersion getVersion(String v) {
		int versionNumber = Integer.valueOf(v.split("1_")[1].split("_")[0]);
		for (BukkitVersion version : values())
			for (int s : version.getIdentifier())
				if (versionNumber == s)
					return version;

		if (versionNumber < values()[0].getIdentifier()[0])
			return values()[0];
		else if (versionNumber > values()[values().length - 1]
				.getIdentifier()[values()[values().length - 1].getIdentifier().length - 1])
			return values()[values().length - 1];

		return null;
	}
}