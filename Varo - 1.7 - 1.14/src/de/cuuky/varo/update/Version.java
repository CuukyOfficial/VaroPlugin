package de.cuuky.varo.update;

public class Version {

	/*
	 * 
	 */

	private String version;

	public enum VersionResult {
		GREATER, EQUAL, LOWER;
	}

	public final String get() {
		return this.version;
	}

	public Version(String version) {
		if (version == null)
			throw new IllegalArgumentException("Version can not be null");

		if (!version.matches("[0-9]+(\\.[0-9]+)*"))
			throw new IllegalArgumentException("Invalid version format");

		this.version = version;
	}

	public VersionResult compareTo(Version that) {
		if (that == null)
			return VersionResult.GREATER;

		String[] thisParts = this.get().split("\\.");
		String[] thatParts = that.get().split("\\.");
		int length = Math.max(thisParts.length, thatParts.length);
		for (int i = 0; i < length; i++) {
			int thisPart = i < thisParts.length ? Integer.parseInt(thisParts[i]) : 0;
			int thatPart = i < thatParts.length ? Integer.parseInt(thatParts[i]) : 0;
			if (thisPart < thatPart)
				return VersionResult.LOWER;
			if (thisPart > thatPart)
				return VersionResult.GREATER;
		}
		return VersionResult.EQUAL;
	}

	@Override
	public boolean equals(Object that) {
		if (this == that)
			return true;

		if (that == null)
			return false;

		if (this.getClass() != that.getClass())
			return false;

		return this.compareTo((Version) that) == VersionResult.EQUAL;
	}
}
