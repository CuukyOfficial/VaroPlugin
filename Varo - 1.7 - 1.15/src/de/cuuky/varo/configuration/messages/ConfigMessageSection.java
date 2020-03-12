package de.cuuky.varo.configuration.messages;

public enum ConfigMessageSection {

	ALERTS("alerts"), 
	AUTOSTART("autostart"), 
	BOTS("bots"), 
	BORDER("border"), 
	CHAT("chat"),
	CHEST("chest"),
	COMBAT("combat"),
	COMMANDS("commands"), 
	DEATH("death"),
	GAME("game"),
	KICK("kick"),
	MOTD("motd"), 
	LABYMOD("labymod"), 
	OTHER("other"), 
	SPECTATOR("spectator"), 
	SPAWNS("spawns"), 
	PROTECTION("protection"), 
	SORT("sort"), 
	NAMETAG("nametag"), 
	TABLIST("tablist"), 
	TEAMREQUEST("teamrequest"), 
	NOPERMISSION("nopermission"), 
	QUITMESSAGE("quitmessage"), 
	SPAWN("spawn"), 
	JOINMESSAGE("joinmessage");
	
	
	

	private String path;

	private ConfigMessageSection(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}
}
