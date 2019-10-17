package de.cuuky.varo.utils;

import org.bukkit.Location;

public class LocationFormatter {

	private String format;

	public LocationFormatter(String format) {
		this.format = format;
	}

	public String format(Location location) {
		return format.replaceAll("x", String.valueOf(location.getBlockX())).replaceAll("y", String.valueOf(location.getBlockY())).replaceAll("z", String.valueOf(location.getBlockZ())).replaceAll("world", location.getWorld().getName());
	}
}
