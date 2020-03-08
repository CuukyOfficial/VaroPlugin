package de.cuuky.varo.utils.varo;

import org.bukkit.Location;

public class LocationFormat {
	
	private Location location;
	public LocationFormat(Location location) {
		this.location = location;
	}

	public String format(String format) {
		return format.replace("x", String.valueOf(location.getBlockX())).replace("y", String.valueOf(location.getBlockY())).replace("z", String.valueOf(location.getBlockZ())).replace("world", location.getWorld().getName());
	}
}