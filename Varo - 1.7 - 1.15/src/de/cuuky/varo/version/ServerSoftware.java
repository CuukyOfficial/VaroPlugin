package de.cuuky.varo.version;

import java.util.ArrayList;
import java.util.Arrays;

public enum ServerSoftware {

	BUKKIT("Bukkit", false),
	SPIGOT("Spigot", false),
	PAPER("Paper", false),
	TACO("TacoSpigot", false),
	MAGMA("Magma", true),
	THERMOS("Thermos", true),
	URANIUM("Uranium", true),
	UNKNOWN("Unknown", false);

	private static ArrayList<Character> abc;

	static {
		abc = new ArrayList<Character>(Arrays.asList(new Character[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' }));
	}

	private String versionname;
	private boolean modsupport;

	private ServerSoftware(String versionname, boolean modsupport) {
		this.versionname = versionname;
		this.modsupport = modsupport;
	}

	public String getName() {
		return this.versionname;
	}

	public boolean hasModSupport() {
		return this.modsupport;
	}

	public static ServerSoftware getServerSoftware(String version) {
		version = version.toLowerCase();

		ServerSoftware found = UNKNOWN;

		for(ServerSoftware software : values()) {
			String softwareName = software.getName().toLowerCase();

			if(!version.contains(softwareName) || found != null && softwareName.length() < found.getName().length())
				continue;

			found = software;
		}

		if(found != UNKNOWN) {
			int location = version.indexOf(found.getName().toLowerCase());

			if(location - 1 > -1)
				if(abc.contains(version.charAt(location - 1)))
					found = UNKNOWN;

			if(location + found.getName().length() + 1 < version.length())
				if(abc.contains(version.charAt(location + found.getName().length() + 1)))
					found = UNKNOWN;
		}

		return found;
	}
}