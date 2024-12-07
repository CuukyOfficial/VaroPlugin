package de.cuuky.varo.configuration.configurations.config;

import java.util.ArrayList;
import java.util.List;

import com.cryptomorin.xseries.XMaterial;

import de.cuuky.varo.configuration.ConfigHandler;
import de.cuuky.varo.configuration.configurations.SectionConfiguration;
import de.cuuky.varo.configuration.configurations.SectionEntry;
import de.varoplugin.cfw.version.ServerVersion;
import de.varoplugin.cfw.version.VersionUtils;

public enum ConfigSettingSection implements SectionConfiguration {

	ACTIVITY("Activity", XMaterial.FURNACE, "Hier kannst du Einstellungen zur Aktivitaet eines Spielers vornehmen."),
	AUTOSETUP("Autosetup", XMaterial.CLOCK, "Hier kannst das Autosetup einstellen!"),
	BACKPACKS("Backpacks", XMaterial.ENDER_CHEST, "Alle Einstellungen zur Rucksaecken"),
	BORDER("Border", VersionUtils.getVersion().isHigherThan(ServerVersion.ONE_7) ? XMaterial.BARRIER : XMaterial.OAK_FENCE, "Hier kannst du Einstellungen zur Border vornehmen."),
	CHAT("Chat", XMaterial.WRITABLE_BOOK, "Hier kannst du Einstellungen zum Chat vornehmen"),
	COMBATLOG("Combatlog", XMaterial.DIAMOND_SWORD, "Hier kannst du einstellen, was passiert,\nwenn ein Spieler sich waehrend des Kampfes ausloggt."),
	COMMANDS("Commands", XMaterial.COMMAND_BLOCK, "Hier kannst Commands aktivieren oder deaktivieren (Neustart erforderlich)"),
	DEATH("Death", XMaterial.SKELETON_SKULL, "Hier kannst du Einstellungen zum Tod eines Spielers vornehmen."),
	DISCONNECT("Disconnect", XMaterial.COAL, "Hier kannst du einstellen, was passiert,\nwenn ein Spieler zu frueh disconnected."),
	DISCORD("Discord", XMaterial.DISPENSER, "Hier kannst du Einstellungen zum DiscordBot vornehmen."),
	FINALE("Finale", XMaterial.END_PORTAL_FRAME, "Hier kannst du Einstellungen zum Finale des Projektes vornehmen."),
	HUD("HUD", XMaterial.PAINTING, "Hier kannst du Einstellungen zur HUD vornehmen."),
	INTRO("Intro", XMaterial.FIREWORK_ROCKET, "Hier kannst du Einstellungen zum Intro vornehmen."),
	JOIN_SYSTEMS("JoinSystems", XMaterial.RED_BED, "Hier kannst du einstellen, wann und wie oft Spieler joinen duerfen."),
	MAIN("Main", XMaterial.LEVER, "Hier kannst du alle Haupteinstellungen vornehmen."),
	OFFLINEVILLAGER("OfflineVillager", XMaterial.VILLAGER_SPAWN_EGG, "Einstellungen zu den OfflineVillagern"),
	OTHER("Other", XMaterial.REDSTONE, "Hier findest du alle restlichen Einstellungen."),
	PROTECTIONS("Protections", XMaterial.DIAMOND_CHESTPLATE, "Hier kannst du alle Einstellungen zu Schutzzeiten vornehmen."),
	REPORT("Report", XMaterial.REDSTONE_TORCH, "Hier kannst du Einstellungen zum Report-System vornehmen."),
	SERVER_LIST("Serverlist", XMaterial.OAK_SIGN, "Hier kannst du die Anzeige des Servers in der Serverliste konfigurieren."),
	START("Start", XMaterial.ACTIVATOR_RAIL, "Hier kannst du Einstellungen zum Start deines Plugins vornehmen."),
	STRIKE("Strike", XMaterial.PAPER, "Hier kannst du Einstellungen zu den Strikes vornehmen."),
	TEAMS("Teams", XMaterial.DIAMOND_HELMET, "Hier kannst du Einstellungen zu Teams vornehmen."),
	TELEGRAM("Telegram", XMaterial.DROPPER, "Alle Einstellungen zum Telegram-Bot."),
	WORLD("World", XMaterial.GRASS_BLOCK, "Hier kannst du Einstellungen zur Welt vornehmen."),
	YOUTUBE("YouTube", XMaterial.MAP, "Hier kannst du Einstellungen zu den Videos deines Projektes vornehmen.");
	

	private String name, description;
	private XMaterial material;

	private ConfigSettingSection(String name, XMaterial material, String description) {
		this.name = name;
		this.material = material;
		this.description = description;
	}

	@Override
	public String getDescription() {
		return description;
	}

	public XMaterial getMaterial() {
		return material;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getFolder() {
		return ConfigHandler.getConfigPath();
	}

	@Override
	public List<ConfigSetting> getEntries() {
		List<ConfigSetting> temp = new ArrayList<>();
		for (ConfigSetting entry : ConfigSetting.values()) {
			if (!entry.getSection().equals(this))
				continue;

			temp.add(entry);
		}

		return temp;
	}

	@Override
	public SectionEntry getEntry(String name) {
		for (SectionEntry entry : getEntries())
			if (entry.getPath().equals(name))
				return entry;

		return null;
	}
}