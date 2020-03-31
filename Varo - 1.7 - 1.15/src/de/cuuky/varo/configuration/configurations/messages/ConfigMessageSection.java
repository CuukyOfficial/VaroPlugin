package de.cuuky.varo.configuration.configurations.messages;

import java.util.ArrayList;

import de.cuuky.varo.configuration.ConfigHandler;
import de.cuuky.varo.configuration.configurations.SectionConfiguration;
import de.cuuky.varo.configuration.configurations.SectionEntry;

public enum ConfigMessageSection implements SectionConfiguration {

	ALERTS("alerts", "Nachrichten zu den Meldungen auf Discord, im Chat etc."), 
	AUTOSTART("autostart", "Nachrichten zum AutoStart"), 
	BOTS("bots", "Nachrichten, die die Bots ausgeben"), 
	BORDER("border", "Nachrichten zu der Worldborder"), 
	CHAT("chat", "Nachrichten zum Chat"),
	CHEST("chest", "Nachrichten zum Sichern von Kisten & Oefen"),
	COMBAT("combat", "Nachrichten zum Kampf"),
//	COMMANDS("commands", "Nachrichten zu den Befehlen"), 
	DEATH("death", "Nachrichten zum Tod eines Spielers"),
	GAME("game", "Nachrichten zum Spielverlauf"),
	KICK("kick", "Nachrichten zum Kick eines Spielers"),
	MOTD("motd", "Nachrichten zur Motd des Servers"), 
	LABYMOD("labymod", "Nachrichten zu den LabyMod-Einstellungen"), 
	OTHER("other", "Andere Nachrichten"), 
	SPECTATOR("spectator", "Nachrichten zu den Zuschauern"), 
	SPAWNS("spawns", "Nachrichten fuer die Spawns"), 
	PROTECTION("protection", "Nachrichten fuer die Schutzzeiten"), 
	SORT("sort", "Nachrichten zur Sortierung der Spieler"), 
	NAMETAG("nametag", "Nachrichten zu den Nametags der Spieler"), 
	TABLIST("tablist", "Nachrichten zur Modifizierung der Tablist"), 
	TEAMREQUEST("teamrequest", "Nachrichten zu den Teameinladungen"), 
	NOPERMISSION("nopermission", "Nachrichten fuer unzureichende Berechtigungen"), 
	QUITMESSAGE("quitmessage", "Nachrichten fuer das Verlassen des Servers"), 
	SPAWN("spawn", "Nachrichten zum Worldspawn"), 
	VARO_COMMANDS("varocommands", "Nachrichten von Befehlen (/varo)"), 
	JOINMESSAGE("joinmessage", "Nachrichten zum Betreteten des Servers");
	
	private String name, description;
	private ConfigMessageSection(String path, String description) {
		this.name = path;
		this.description = description;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public String getDescription() {
		return this.description;
	}
	
	@Override
	public String getFolder() {
		return ConfigHandler.getMessagePath();
	}
	
	@Override
	public ArrayList<SectionEntry> getEntries() {
		ArrayList<SectionEntry> temp = new ArrayList<>();
		for(ConfigMessages message : ConfigMessages.values()) {
			if(!message.getSection().equals(this))
				continue;

			temp.add(message);
		}

		return temp;
	}
	
	@Override
	public SectionEntry getEntry(String name) {
		for(SectionEntry entry : getEntries())
			if(entry.getPath().equals(name))
				return entry;
		
		return null;
	}
}