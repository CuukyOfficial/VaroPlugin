package de.cuuky.varo.config.config;

import java.util.ArrayList;

import org.bukkit.Material;

import de.cuuky.varo.version.types.Materials;

public enum ConfigSection {

	MAIN("Main", Material.LEVER, "Hier kannst du alle Haupteinstellungen vornehmen."),

	OFFLINEVILLAGER("OfflineVillager", Materials.EMERALD.parseMaterial(), "Einstellungen zu den OfflineVillagern"),

	BACKPACKS("Backpacks", Material.CHEST, "Alle Einstellungen zur Rucksäcken"),

	PROTECTIONS("Protections", Material.DIAMOND_CHESTPLATE, "Hier kannst du alle Einstellungen zu Schutzzeiten vornehmen."),

	SERVER_LIST("Serverlist", Materials.SIGN.parseMaterial(), "Hier kannst du die Anzeige des Servers in der Serverliste konfigurieren."),

	TEAMS("Teams", Material.BOOK, "Hier kannst du Einstellungen zu Teams vornehmen."),

	JOIN_SYSTEMS("JoinSystems", Materials.RED_BED.parseMaterial(), "Hier kannst du einstellen, wann und wie oft Spieler joinen dürfen."),

	DEATH("Death", Materials.SKELETON_SKULL.parseMaterial(), "Hier kannst du Einstellungen zum Tod eines Spielers vornehmen."),
	
	FINALE("Finale", Materials.END_PORTAL_FRAME.parseMaterial(), "Hier kannst du Einstellungen zum Finale des Projektes vornehmen."),

	STRIKE("Strike", Materials.PAPER.parseMaterial(), "Hier kannst du Einstellungen zu den Strikes vornehmen."),

	WORLD("World", Material.GRASS, "Hier kannst du Einstellungen zur Welt vornehmen."),

	START("Start", Material.ACTIVATOR_RAIL, "Hier kannst du Einstellungen zum Start deines Plugins vornehmen."),

	DISCONNECT("Disconnect", Material.COAL, "Hier kannst du einstellen, was passiert,\nwenn ein Spieler zu früh disconnected."),

	COMBATLOG("Combatlog", Material.DIAMOND_SWORD, "Hier kannst du einstellen, was passiert,\nwenn ein Spieler sich während des Kampfes ausloggt."),

	BORDER("Border", Materials.DISPENSER.parseMaterial(), "Hier kannst du Einstellungen zur Border vornehmen."),

	CHAT("Chat", Materials.WRITABLE_BOOK.parseMaterial(), "Hier kannst du Einstellungen zum Chat vornehmen"),

	ACTIVITY("Activity", Material.FURNACE, "Hier kannst du Einstellungen zur Aktivität eines Spielers vornehmen."),

	REPORT("Report", Materials.REDSTONE_TORCH.parseMaterial(), "Hier kannst du Einstellungen zum Report-System vornehmen."),

	YOUTUBE("YouTube", Material.MAP, "Hier kannst du Einstellungen zu den Videos deines Projektes vornehmen."),

	DISCORD("Discord", Material.DISPENSER, "Hier kannst du Einstellungen zum DiscordBot vornehmen."),

	TELEGRAM("Telegram", Materials.MAP.parseMaterial(), "Alle Einstellungen zum Telegram-Bot."),

	AUTOSETUP("Autosetup", Materials.CLOCK.parseMaterial(), "Hier kannst das Autosetup einstellen!"),
	
	GUI("Gui", Materials.WHITE_BANNER.parseMaterial(), "Hier kannst du Einstellungen zur Gui vornehmen."),

	OTHER("Other", Material.REDSTONE, "Hier findest du alle restlichen Einstellungen.");

	private String name;
	private Material material;
	private String description;

	private ConfigSection(String name, Material material, String description) {
		this.name = name;
		this.material = material;
		this.description = description;
	}

	public String getPath() {
		return name + ".";
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

	public ArrayList<ConfigEntry> getEntries() {
		ArrayList<ConfigEntry> temp = new ArrayList<>();
		for(ConfigEntry entry : ConfigEntry.values()) {
			if(!entry.getSection().equals(this))
				continue;

			temp.add(entry);
		}

		return temp;
	}

	public static ConfigSection getSection(String name) {
		for(ConfigSection section : ConfigSection.values()) {
			if(!section.getName().equalsIgnoreCase(name))
				continue;

			return section;
		}

		return null;
	}
}
