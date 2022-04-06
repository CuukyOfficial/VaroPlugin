package de.cuuky.varo.configuration.configurations.config;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

import de.cuuky.cfw.version.BukkitVersion;
import de.cuuky.cfw.version.VersionUtils;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.configuration.ConfigHandler;
import de.cuuky.varo.configuration.configurations.SectionConfiguration;
import de.cuuky.varo.configuration.configurations.SectionEntry;

public enum ConfigSettingSection implements SectionConfiguration {

	ACTIVITY("Activity", Materials.FURNACE.parseMaterial(), "Hier kannst du Einstellungen zur Aktivitaet eines Spielers vornehmen."),
	AUTOSETUP("Autosetup", Materials.CLOCK.parseMaterial(), "Hier kannst das Autosetup einstellen!"),
	BACKPACKS("Backpacks", Materials.ENDER_CHEST.parseMaterial(), "Alle Einstellungen zur Rucksaecken"),
	BORDER("Border", VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_7) ? Materials.BARRIER.parseMaterial() : Materials.OAK_FENCE.parseMaterial(), "Hier kannst du Einstellungen zur Border vornehmen."),
	CHAT("Chat", Materials.WRITABLE_BOOK.parseMaterial(), "Hier kannst du Einstellungen zum Chat vornehmen"),
	COMBATLOG("Combatlog", Materials.DIAMOND_SWORD.parseMaterial(), "Hier kannst du einstellen, was passiert,\nwenn ein Spieler sich waehrend des Kampfes ausloggt."),
	COMMANDS("Commands", Materials.COMMAND_BLOCK.parseMaterial(), "Hier kannst Commands aktivieren oder deaktivieren (Neustart erforderlich)"),
	DEATH("Death", Materials.SKELETON_SKULL.parseMaterial(), "Hier kannst du Einstellungen zum Tod eines Spielers vornehmen."),
	DISCONNECT("Disconnect", Materials.COAL.parseMaterial(), "Hier kannst du einstellen, was passiert,\nwenn ein Spieler zu frueh disconnected."),
	DISCORD("Discord", Materials.DISPENSER.parseMaterial(), "Hier kannst du Einstellungen zum DiscordBot vornehmen."),
	FINALE("Finale", Materials.END_PORTAL_FRAME.parseMaterial(), "Hier kannst du Einstellungen zum Finale des Projektes vornehmen."),
	INTRO("Intro", Materials.FIREWORK_ROCKET.parseMaterial(), "Hier kannst du Einstellungen zum Intro vornehmen."),
	JOIN_SYSTEMS("JoinSystems", Materials.RED_BED.parseMaterial(), "Hier kannst du einstellen, wann und wie oft Spieler joinen duerfen."),
	MAIN("Main", Materials.LEVER.parseMaterial(), "Hier kannst du alle Haupteinstellungen vornehmen."),
	OFFLINEVILLAGER("OfflineVillager", Materials.VILLAGER_SPAWN_EGG.parseMaterial(), "Einstellungen zu den OfflineVillagern"),
	OTHER("Other", Materials.REDSTONE.parseMaterial(), "Hier findest du alle restlichen Einstellungen."),
	PROTECTIONS("Protections", Materials.DIAMOND_CHESTPLATE.parseMaterial(), "Hier kannst du alle Einstellungen zu Schutzzeiten vornehmen."),
	REPORT("Report", Materials.REDSTONE_TORCH.parseMaterial(), "Hier kannst du Einstellungen zum Report-System vornehmen."),
	SERVER_LIST("Serverlist", Materials.SIGN.parseMaterial(), "Hier kannst du die Anzeige des Servers in der Serverliste konfigurieren."),
	START("Start", Materials.ACTIVATOR_RAIL.parseMaterial(), "Hier kannst du Einstellungen zum Start deines Plugins vornehmen."),
	STRIKE("Strike", Materials.PAPER.parseMaterial(), "Hier kannst du Einstellungen zu den Strikes vornehmen."),
	TABLIST("TablistSettings", Material.PAINTING, "Hier kannst du Einstellungen zur Tablist vornehmen."),
	TEAMS("Teams", Materials.DIAMOND_HELMET.parseMaterial(), "Hier kannst du Einstellungen zu Teams vornehmen."),
	TELEGRAM("Telegram", Materials.DROPPER.parseMaterial(), "Alle Einstellungen zum Telegram-Bot."),
	WORLD("World", Materials.GRASS.parseMaterial(), "Hier kannst du Einstellungen zur Welt vornehmen."),
	YOUTUBE("YouTube", Materials.MAP.parseMaterial(), "Hier kannst du Einstellungen zu den Videos deines Projektes vornehmen.");
	

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