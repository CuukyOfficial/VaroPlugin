package de.cuuky.varo.event;

public enum VaroEventType {

	EXPOSED("§cExposed"),
	MASS_RECORDING("§aMassRecording"),
	MOON_GRAVITY("§2MoonGravity"),
	POISON_RAIN("§4Poisened Rain"),
	POISON_WATER("§bPoisoned Water");

	private String name;

	private VaroEventType(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}