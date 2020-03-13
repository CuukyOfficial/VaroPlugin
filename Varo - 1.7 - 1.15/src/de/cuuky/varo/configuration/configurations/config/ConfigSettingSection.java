package de.cuuky.varo.configuration.configurations.config;

import java.util.ArrayList;

import org.bukkit.Material;

import de.cuuky.varo.configuration.configurations.SectionConfiguration;
import de.cuuky.varo.configuration.configurations.SectionEntry;
import de.cuuky.varo.version.types.Materials;

public enum ConfigSettingSection implements SectionConfiguration {

	ACTIVITY("Activity", Material.FURNACE, "Hier kannst du Einstellungen zur Aktivitaet eines Spielers vornehmen."),
	AUTOSETUP("Autosetup", Materials.CLOCK.parseMaterial(), "Hier kannst das Autosetup einstellen!"),
	BACKPACKS("Backpacks", Material.CHEST, "Alle Einstellungen zur Rucksaecken"),
	BORDER("Border", Materials.DISPENSER.parseMaterial(), "Hier kannst du Einstellungen zur Border vornehmen."),
	CHAT("Chat", Materials.WRITABLE_BOOK.parseMaterial(), "Hier kannst du Einstellungen zum Chat vornehmen"),
	COMBATLOG("Combatlog", Material.DIAMOND_SWORD, "Hier kannst du einstellen, was passiert,\nwenn ein Spieler sich waehrend des Kampfes ausloggt."),
	DEATH("Death", Materials.SKELETON_SKULL.parseMaterial(), "Hier kannst du Einstellungen zum Tod eines Spielers vornehmen."),
	DISCONNECT("Disconnect", Material.COAL, "Hier kannst du einstellen, was passiert,\nwenn ein Spieler zu frueh disconnected."),
	DISCORD("Discord", Material.DISPENSER, "Hier kannst du Einstellungen zum DiscordBot vornehmen."),
	FINALE("Finale", Materials.END_PORTAL_FRAME.parseMaterial(), "Hier kannst du Einstellungen zum Finale des Projektes vornehmen."),
	GUI("Gui", Materials.COMPASS.parseMaterial(), "Hier kannst du Einstellungen zur Gui vornehmen."),
	JOIN_SYSTEMS("JoinSystems", Materials.RED_BED.parseMaterial(), "Hier kannst du einstellen, wann und wie oft Spieler joinen duerfen."),
	MAIN("Main", Material.LEVER, "Hier kannst du alle Haupteinstellungen vornehmen."),
	OFFLINEVILLAGER("OfflineVillager", Materials.EMERALD.parseMaterial(), "Einstellungen zu den OfflineVillagern"),
	OTHER("Other", Material.REDSTONE, "Hier findest du alle restlichen Einstellungen."),
	PROTECTIONS("Protections", Material.DIAMOND_CHESTPLATE, "Hier kannst du alle Einstellungen zu Schutzzeiten vornehmen."),
	REPORT("Report", Materials.REDSTONE_TORCH.parseMaterial(), "Hier kannst du Einstellungen zum Report-System vornehmen."),
	SERVER_LIST("Serverlist", Materials.SIGN.parseMaterial(), "Hier kannst du die Anzeige des Servers in der Serverliste konfigurieren."),
	START("Start", Material.ACTIVATOR_RAIL, "Hier kannst du Einstellungen zum Start deines Plugins vornehmen."),
	STRIKE("Strike", Materials.PAPER.parseMaterial(), "Hier kannst du Einstellungen zu den Strikes vornehmen."),
	TEAMS("Teams", Material.BOOK, "Hier kannst du Einstellungen zu Teams vornehmen."),
	TELEGRAM("Telegram", Materials.MAP.parseMaterial(), "Alle Einstellungen zum Telegram-Bot."),
	WORLD("World", Material.GRASS, "Hier kannst du Einstellungen zur Welt vornehmen."),
	YOUTUBE("YouTube", Material.MAP, "Hier kannst du Einstellungen zu den Videos deines Projektes vornehmen.");

	private String name, description;
	private Material material;

	private ConfigSettingSection(String name, Material material, String description) {
		this.name = name;
		this.material = material;
		this.description = description;
	}

	@Override
	public String getDescription() {
		return description;
	}

	public Material getMaterial() {
		return material;
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public ArrayList<SectionEntry> getEntries() {
		ArrayList<SectionEntry> temp = new ArrayList<>();
		for(ConfigSetting entry : ConfigSetting.values()) {
			if(!entry.getSection().equals(this))
				continue;

			temp.add(entry);
		}

		return temp;
	}
}