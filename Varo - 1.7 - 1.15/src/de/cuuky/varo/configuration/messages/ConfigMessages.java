package de.cuuky.varo.configuration.messages;

import java.util.ArrayList;

import de.cuuky.varo.configuration.placeholder.placeholder.GeneralMessagePlaceholder;
import de.cuuky.varo.configuration.placeholder.placeholder.PlayerMessagePlaceholder;
import de.cuuky.varo.entity.player.VaroPlayer;

public enum ConfigMessages {

	ALERT_AUTOSTART_AT("Alert.autostartAt", "%projectname% wird am %date% starten!"),
	ALERT_BORDER_CHANGED("Alert.borderChanged", "Die Border wurde auf %size% gesetzt!"),
	ALERT_BORDER_DECREASED_DEATH("Alert.borderDecrease.death", "Die Border wurde um %size% aufgrund eines Todes verringert!"),

	ALERT_BORDER_DECREASED_TIME_DAYS("Alert.borderDecrease.days", "Die Border wurde um %size% verkleinert. Nächste Verkleinerung in %days% Tagen!"),
	ALERT_BORDER_DECREASED_TIME_MINUTE("Alert.borderDecrease.minutes", "Die Border wurde um %size% verringert! Nächste Verkleinerung in %minutes% Minuten!"),
	ALERT_COMBAT_LOG("Alert.combatlog", "%player% hat sich im Kampf ausgeloggt!"),

	ALERT_COMBAT_LOG_STRIKE("Alert.combatlogStrike", "%player% hat sich im Kampf ausgeloggt und hat somit einen Strike erhalten!"),
	ALERT_DISCONNECT_TOO_OFTEN("Alert.disconnectTooOften", "%player% hat das Spiel zu oft verlassen, weswegen seine Session entfernt wurde!"),
	ALERT_DISCORD_DEATH("Alert.death", "%player% ist gestorben! Grund: %reason%"),

	ALERT_DISCORD_KILL("Alert.kill", "%player% wurde von %killer% getötet!"),
	ALERT_FIRST_STRIKE("Alert.firstStrike", "%player% hat nun einen Strike. Der Strike wurde von %striker% gegeben. Begründung: %strikeBegründung%\nAufgrund dessen sind hier die derzeiten Koordinaten: %pos%!"),
	ALERT_FIRST_STRIKE_NEVER_ONLINE("Alert.firstStrikeNeverOnline", "%player% hat nun einen Strike. Der Strike wurde von %striker% gegeben. Begründung: %strikeBegründung%\nDer Spieler war noch nicht online und wird an den Spawn-Koordinaten spawnen: %pos%!"),
	// ALERTS
	ALERT_GAME_STARTED("Alert.gameStarted", "%projectname% wurde gestartet!"),

	ALERT_GENERAL_STRIKE("Alert.generalStrike", "%player% hat nun den %strikeNumber%ten Strike! Der Strike wurde von %striker% gegeben. Begründung: %strikeBegründung%"),
	ALERT_JOIN_FINALE("Alert.finale", "%player% &7hat den Server zum Finale betreten."),
	ALERT_KICKED_PLAYER("Alert.kickedPlayer", "%player% wurde gekickt!"),
	ALERT_NEW_SESSIONS("Alert.newSessions", "Es wurden %newSessions% neue Folgen an die Spieler gegeben!"),
	ALERT_NEW_SESSIONS_FOR_ALL("Alert.newSessionsForAll", "Alle haben %newSessions% neue Folgen bekommen!"),

	ALERT_NO_BLOODLUST("Alert.noBloodlust", "%player% hat nun %days% Tage nicht gekämpft, was das Limit überschritten hat!"),
	ALERT_NO_BLOODLUST_STRIKE("Alert.noBloodlustStrike", "%player% hat nun %days% Tage nicht gekämpft, weswegen %player% jetzt gestriket wurde!"),
	ALERT_NOT_JOIN("Alert.notJoin", "%player% war nun %days% Tage nicht online, was das Limit überschritten hat!"),
	ALERT_NOT_JOIN_STRIKE("Alert.notJoinStrike", "%player% war nun %days% Tage nicht online, weswegen %player% jetzt gestriket wurde!"),
	ALERT_PLAYER_DC_TO_EARLY("Alert.playerQuitToEarly", "%player% hat das Spiel vorzeitig verlassen! %player% hat noch %seconds% Sekunden Spielzeit über!"),
	ALERT_PLAYER_JOIN_MASSREC("Alert.playerJoinMassrec", "%player% hat den Server in der Massenaufnahme betreten und spielt nun die %episodesPlayedPlus1%te Folge"),
	ALERT_PLAYER_JOIN_NORMAL("Alert.playerJoinNormal", "%player% hat das Spiel betreten!"),
	ALERT_PLAYER_JOINED("Alert.playerJoined", "%player% hat den Server betreten und spielt nun die %episodesPlayedPlus1%te Folge!"),

	ALERT_PLAYER_QUIT("Alert.playerQuit", "%player% hat das Spiel verlassen!"),
	ALERT_PLAYER_RECONNECT("Alert.playerReconnect", "%player% hatte das Spiel vorzeitig verlassen und ist rejoint! %player% hat noch %seconds% Sekunden verbleibend!"),
	ALERT_SECOND_STRIKE("Alert.secondStrike", "%player% hat nun zwei Strikes. Der Strike wurde von %striker% gegeben. Begründung: %strikeBegründung%\nAufgrund dessen wurde das Inventar geleert!"),
	ALERT_SWITCHED_NAME("Alert.switchedName", "%player% hat den Namen gewechselt und ist nun unter %newName% bekannt!"),
	ALERT_TELEPORTED_TO_MIDDLE("Alert.teleportedToMiddle", "%player% wurde zur Mitte teleportiert!"),
	ALERT_THRID_STRIKE("Alert.thirdStrike", "%player% hat nun drei Strikes. Der Strike wurde von %striker% gegeben. Begründung: %strikeBegründung%\nDamit ist %player% aus %projectname% ausgeschieden!"),
	ALERT_WINNER("Alert.win.player", "%player% hat %projectname% gewonnen! Gratulation!"),
	ALERT_WINNER_TEAM("Alert.win.team", "%winnerPlayers% haben %projectname% gewonnen! Gratulation!"),

	BORDER_DECREASE_DAYS("Border.decreaseDays", "&7Die Border wird jetzt um &c%size% &7Blöcke mit &c%speed% &7Blöcken/s verkleinert. &7Nächste Verkleinerung in &c%days% &7Tagen!"),
	BORDER_DECREASE_DEATH("Border.decreaseDeath", "&7Die Border wird jetzt um &c%size% &7Blöcke mit &c%speed% &7Blöcken/s aufgrund eines Todes verkleinert."),
	BORDER_DECREASE_MINUTES("Border.decreaseMinutes", "&7Die Border wird jetzt um &c%size% &7Blöcke mit &c%speed% &7Blöcken/s verkleinert. &7Nächste Verkleinerung in &c%days% &7Minuten!"),
	// BORDER
	BORDER_MINIMUM_REACHED("Border.minimumReached", "&cDie Border hat ihr Minimum erreicht!"),
	// CHAT
	CHAT_FORMAT("Chat.format", "§7%player% §8» §f"),
	CHAT_MUTED("Chat.muted", "&7Du wurdest gemutet!"),
	CHAT_WHEN_START("Chat.chatOnStart", "&7Du kannst erst ab dem Start wieder schreiben!"),
	// COMBAT
	COMBAT_FRIENDLY_FIRE("Combat.friendlyfire", "§7Dieser Spieler ist in deinem Team!"),
	COMBAT_IN_FIGHT("Combat.inFight", "&7Du bist nun im Kampf, logge dich &4NICHT &7aus!"),

	COMBAT_LOGGED_OUT("Combat.loggedOut", "&c%player% &7hat den Server während eines Kampfes verlassen!"),
	COMBAT_NOT_IN_FIGHT("Combat.notInFight", "§7Du bist nun nicht mehr im &cKampf&7!"),
	COMMAND_CONFIG_RELOAD("Commands.configReload", "&7Die %colorcode%Config &7wurde neu geladen"),
	COMMAND_INVITED_TEAM("Commands.invitedInTeam", "&7Du hast %colorcode%%invited% &7in das Team %colorcode%%team% &7eingeladen!"),
	COMMAND_KICKED("Commands.kick", "%colorcode%%player% &7wurde gekickt!"),

	COMMAND_NO_TEAMNAME("Commands.noteamname", "&7Du hast noch &7keinen &7Teamnamen!"),
	COMMAND_PING("Commands.ping", "&7Dein %colorcode%Ping &7beträgt: %colorcode%%ping%&7ms"),
	// COMMANDS
	COMMAND_SET_BORDER("Commands.borderSet", "&7Die Border wurde auf %colorcode%%zahl% &7gesetzt!"),
	COMMAND_SPAWN("Commands.spawn", "%colorcode%Koordinaten&7 vom Spawn: %colorcode%%x%&7, %colorcode%%y%&7, %colorcode%%z%"),
	COMMAND_SPAWN_DISTANCE("Commands.spawnDistance", "&7Du bist %colorcode%%distance% &7Blöcke vom Spawn entfernt!"),
	COMMAND_SPAWN_DISTANCE_NETHER("Commands.spawnDistanceNether", "&7Du bist %colorcode%%distance% &7Blöcke vom Portal zur Oberwelt entfernt!"),
	COMMAND_SPAWN_NETHER("Commands.spawnNether", "%colorcode%Koordinaten&7 vom Portal zur Oberwelt: %colorcode%%x%&7, %colorcode%%y%&7, %colorcode%%z%"),
	COMMAND_TEAM_REQUEST_RECIEVED("Commands.teamRequestRecieved", "%colorcode%%invitor% &7hat dich in ein Team eingeladen (/varo tr)!"),

	DEATH_DEAD("Death.killMessage", "&c%player% &7ist gestorben. &7Grund: &c%reason%"),
	DEATH_KICK_DEAD("Death.kickedKilled", "&cDu bist gestorben! %nextLine% &7Damit bist du aus %projectname% &7ausgeschieden"),
	DEATH_KICK_KILLED("Death.killedKick", "&7Du wurdest von &c%killer% &7getötet! %nextLine% &7Damit bist du aus %projectname% &7ausgeschieden!"),
	// DEATH
	DEATH_KILLED_BY("Death.killed", "%colorcode%%player% &7wurde von &c%killer% &7getötet!"),
	DEATH_LIFE_LOST("Death.teamLifeLost", "%player% hat nun noch %colorcode%%teamLifes% &7Teamleben!"),
	DEATH_RESPAWN_PROTECTION("Death.respawnProtection", "&c%player% hat nun ein Leben weniger und ist für %seconds% unverwundbar!"),
	DEATH_RESPAWN_PROTECTION_OVER("Death.respawnProtectionOver", "&c%player% ist nun wieder verwundbar!"),

	DISCORD_NO_SERVER_USER("Discord.noServerUser", "&cDein Account ist nicht auf dem Discord!%nextLine%&7Joine dem Discord und versuche es erneut."),
	// DISCORD
	DISCORD_NOT_REGISTERED_DISCORD("Discord.notRegisteredDiscord", "&cDu bist noch nicht mit dem Discord authentifiziert!\n&7Um dich zu authentifizieren, schreibe in den #verify -Channel &c'varo verify <Deine ID>' &7auf dem Discord!\nLink zum Discordserver: &c%discordLink%\n&7Deine ID lautet: &c%code%"),
	DISTANCE_BORDER("General.distanceToBorder", "&7Distanz zur Border: %colorcode%%size% &7Blöcke"),

	// GAME
	GAME_START_COUNTDOWN("Game.start.startCountdown", "%projectname% &7startet in %colorcode%%countdown% &7Sekunde(n)!"),
	GAME_VARO_START("Game.start.varoStart", "%projectname% &7wurde gestartet! &5Viel Erfolg!"),
	GAME_VARO_START_TITLE("Game.start.startTitle", "%colorcode%%countdown%\n&7Viel Glück!"),
	GAME_WIN("Game.win.single", "&5%player% &7hat %projectname% &7gewonnen! &5Gratulation!"),
	GAME_WIN_TEAM("Game.win.team", "&5%winnerPlayers% &7haben %projectname% &7gewonnen! &5Gratulation!"),
	
	// JOIN
	JOIN("Join.join", "%prefix%&a%player% &7hat den Server betreten!"),

	JOIN_FINALE("Join.finale", "%prefix%&a%player% &7hat den Server zum Finale betreten."),
	JOIN_KICK_BANNED("Join.kick.banned", "&4Du bist vom Server gebannt!\n&7Melde dich bei einem Admin, falls dies ein Fehler sein sollte.\n&7Grund: &c%reason%"),
	JOIN_KICK_NO_PREPRODUCES_LEFT("Join.kick.noPreproduceLeft", "&cDu hast bereits vorproduziert! %nextLine%&7Versuche es morgen erneut."),
	JOIN_KICK_NO_SESSIONS_LEFT("Join.kick.noSessionLeft", "&cDu hast keine Sessions mehr übrig! %nextLine%&7Warte bis morgen, damit du wieder spielen kannst!"),
	JOIN_KICK_NO_TIME_LEFT("Join.kick.noTimeLeft", "&cDu darfst nur alle &4%timeHours% &cStunden regulär spielen! %nextLine%&7Du kannst erst in &c%stunden%&7:&c%minuten%&7:&c%sekunden% &7wieder joinen!"),
	JOIN_KICK_NOT_STARTED("Server.modt.notStarted", "&cDer Server wurde noch nicht eröffnet! %nextLine%&7Gedulde dich noch ein wenig!"),

	JOIN_KICK_NOT_USER_OF_PROJECT("Join.kick.notUserOfTheProject", "&7Du bist kein Teilnehmer dieses %projectname%&7's!"),
	JOIN_KICK_SERVER_FULL("Join.kick.serverFull", "&cDer Server ist voll!%nextLine%&7Sprich mit dem Owner, falls das das ein Irrtum sein sollte!"),
	JOIN_KICK_STRIKE_BAN("Join.kick.strikeBan", "&cDu wurdest aufgrund deines letzten Strikes für %hours% gebannt!\nVersuche es später erneut"),
	JOIN_MASS_RECORDING("Join.massrecording", "%prefix%&a%player% &7hat den Server in der Massenaufnahme betreten und ist in %colorcode%%protectionTime% &7Sekunden angreifbar!"),

	JOIN_NO_MOVE_IN_PROTECTION("Join.notMoveinProtection", "&7Du kannst dich nicht bewegen, solange du noch in der %colorcode%Schutzzeit &7bist!"),
	JOIN_PROTECTION_OVER("Join.joinProtectionOver", "%prefix%&a%player% &7ist nun angreifbar!"),
	JOIN_PROTECTION_TIME("Join.joinProtectionTime", "%prefix%&a%player% &7hat den Server betreten und ist in %colorcode%%protectionTime% &7Sekunden angreifbar!"),

	JOIN_SPECTATOR("Join.spectator", "&a%player% &7hat den Server als Spectator betreten!"),
	JOIN_WITH_REMAINING_TIME("Join.joinWithRemainingTime", "%prefix%&a%player% &7hatte den Server zu früh verlassen und hat jetzt noch %colorcode%%seconds% &7Sekunden übrig! Verbleibende &cDisconnects&7: &c%remainingDisconnects%"),
	KICK_BROADCAST("Kick.broadcast", "%colorcode%%player% &7wurde gekickt!"),
	// KICK
	KICK_DELAY_OVER("Kick.protectionOver", "%colorcode%%player% &7wurde aufgrund seines Todes jetzt gekickt!"),

	KICK_IN_SECONDS("Kick.kickInSeconds", "%colorcode%%player% &7wird in %colorcode%%countdown% &7Sekunde(n) gekickt!"),
	KICK_LABY_MOD("Kick.labyMod", "&cLabyMod isn't allowed on this server."),
	KICK_MESSAGE("Kick.kickMessage", "&cDeine Aufnahmezeit ist abgelaufen, %nextLine%&7deswegen wurdest du gekickt!"),
	KICK_MESSAGE_MASS_REC("Kick.kickMessageMassRec", "&cDie Massenaufnahme ist beendet, %nextLine%&7deswegen wurdest du gekickt!"),
	KICK_PLAYER_NEARBY("Kick.noKickPlayerNearby", "&cEs befindet sich ein Spieler &4%distance% &cBlöcke in deiner Nähe!%nextLine%&7Um gekickt zu werden, entferne dich von diesem Spieler!"),
	KICK_SERVER_CLOSE_SOON("Kick.serverCloseSoon", "&7Der Server schließt in &c%minutes% &7Minuten!"),
	KICK_TOO_MANY_STRIKES("Kick.tooManyStrikes", "&7Du hast zu viele Strikes bekommen und wurdest daher aus dem Projekt %projectname% &7entfernt."),
	KILL_LIFE_ADD("Death.killLifeAdd", "Dein Team hat aufgrund eines Kills ein Teamleben erhalten!"),
	LABYMOD_DISABLED("General.labyModDisabled", "&7Alle deine LabyMod Funktionen wurden deaktiviert!"),
	// Nametag
	NAMETAG_NORMAL("NameTag.normalNametagPrefix", "&7"),
	NAMETAG_SUFFIX("NameTag.nameTag.normalSuffix", " &c%kills%"),

	NAMETAG_TEAM_PREFIX("NameTag.nametagWithTeam", "%colorcode%%team% &7"),
	// SAVEABLE
	NOT_TEAM_CHEST("Chest..notTeamChest", "&7Diese Kiste gehört %colorcode%%player%&7!"),
	NOT_TEAM_FURNACE("Chest.notTeamFurnace", "&7Dieser Ofen gehört %colorcode%%player%&7!"),

	OTHER_NO_PERMISSION("Other.noPermission", "%colorcode%Dazu bist du nicht berechtigt!"),
	OTHER_NOT_ALLOWED_CRAFT("Other.notAllowedCraft", "&7Das darfst du nicht craften, benutzen oder brauen!"),

	// OTHER
	OTHER_SORTED("Other.sorted", "&7Du wurdest in das Loch %colorcode%%zahl% &7teleportiert!"),
	// PROTECTION
	PROTECTION_NO_MOVE_START("Protection.noMoveStart", "§7Du kannst dich nicht bewegen, solange das Projekt noch nicht gestartet wurde."),
	PROTECTION_START("Protection.start", "&7Die &cSchutzzeit &7startet jetzt und wird &c%seconds% &7Sekunden anhalten!"),

	PROTECTION_TIME_OVER("Protection.protectionOver", "&7Die &cSchutzzeit &7ist nun vorrüber!"),
	PROTECTION_TIME_RUNNING("Protection.timeRunning", "&7Die %colorcode%Schutzzeit &7läuft noch!"),

	// QUIT
	QUIT("Quit.noTeamNoRank", "%prefix%&c%player%&7 hat den Server verlassen!"),
	QUIT_DISCONNECT_SESSION_END("Quit.disconnectKilled", "&c%player% &7hat das Spiel verlassen und ist seit &c%banTime% &7Minute(n) nicht mehr online.%nextLine%&7Damit ist er aus %projectname% &7ausgeschieden!"),
	QUIT_SPECTATOR("Quit.spectator", "&c%player% &7hat den Server als Spectator verlassen!"),

	QUIT_TOO_OFTEN("Quit.quitTooOften", "&c%player% &7hat den Server zu oft verlassen und dadurch seine Sitzung verloren."),
	QUIT_WITH_REMAINING_TIME("Quit.quitRemainingTime", "%prefix%&c%player% &7hat den Server vorzeitig verlassen!"),
	REMOVED_SAVEABLE("Chest.removedChest", "&7Du hast diese/n %saveable% %colorcode%erfolgreich &7entfernt!"),
	SAVEABLE_NEW_CHEST("Chest.newChestSaved", "&7Eine neue Kiste wurde gesichert!"),
	SERVER_MODT_CANT_JOIN_HOURS("Server.motd.cantJoinHours", "&cDu kannst nur zwischen &4%minHour% &cund &4%maxHour%&c Uhr joinen! %nextLine%&7Versuche es später erneut! &7%currHour%&7:&7%currMin%&7:&7%currSec%"),
	SERVER_MODT_NOT_OPENED("Server.motd.serverNotOpened", "&cDer Server wurde noch nicht für alle geöffnet! %nextLine%&7Versuche es später erneut!"),
	// Server
	SERVER_MODT_OPEN("Server.motd.serverOpen", "&aSei nun bei %projectname% &adabei! \n&7Viel Spass!"),
	SORT_NO_HOLE_FOUND("Sort.noHoleFound", "Es konnte für dich kein Loch gefunden werden!"),
	SORT_NO_HOLE_FOUND_TEAM("Sort.noHoleFoundTeam", "Es konnte für dich kein Loch bei deinen Teampartnern gefunden werden."),
	SORT_NUMBER_HOLE("Sort.numberHoleTeleport", "Du wurdest in das Loch %colorcode%%number% &7teleportiert!"),
	SORT_OWN_HOLE("Sort.ownHoleTeleport", "Du wurdest in dein Loch einsortiert!"),
	// SORT
	SORT_SPECTATOR_TELEPORT("Sort.spectatorTeleport", "Du wurdest, da du Spectator bist, zum Spawn teleportiert!"),
	TABLIST_FOOTER("Tablist.tablistFooter", "&7------------------------%nextLine%&7Registriert: %colorcode%%players%%nextLine%&7Lebend: %colorcode%%remaining%%nextLine%&7Online: %colorcode%%online%%nextLine%&7Plugin by %colorcode%Cuuky%nextLine%%nextLine%&c%currHour%&7:&c%currMin%&7:&c%currSec%%nextLine%&7------------------------%nextLine%"),
	// TABLIST
	TABLIST_HEADER("Tablist.tablistHeader", "%nextLine%&c%projectname%%nextLine%"),
	TABLIST_PLAYER_WITH_TEAM("Tablist.player.withTeam", "%colorcode%%team% &8| &7%player%  &c%kills%"),
	TABLIST_PLAYER_WITH_TEAM_RANK("Tablist.player.withTeamAndRank", "&7%rank% &8| %colorcode%%team% &8| &7%player%  &c%kills%"),
	TABLIST_PLAYER_WITHOUT_TEAM("Tablist.player.withoutTeam", "&7%player%  &c%kills%"),
	TABLIST_PLAYER_WITHOUT_TEAM_RANK("Tablist.player.withoutTeamWithRank", "&7%rank% &8| &7%player%  &c%kills%"),
	TEAMCHAT_FORMAT("General.teamchatFormat", "&7[%colorcode%%team%&7] %player% &8» &f%message%"),
	// TEAMREQUEST
	TEAMREQUEST_ENTER_TEAMNAME("Teamrequest.enterTeamName", "%colorcode%&lGib jetzt den Teamnamen für dich und %invited% ein:"),
	TEAMREQUEST_MAX_TEAMNAME_LENGTH("Teamrequest.maxTeamnameLength", "Dein Teamname darf maximal %colorcode%%maxLength% &7Zeichen enthalten!"),
	TEAMREQUEST_NO_COLORCODE("Teamrequest.noColorCode", "Dein Teamname darf keine Farbcodes enthalten!"),
	TEAMREQUEST_NO_HASHTAG("Teamrequest.noHashtag", "Dein Teamname darf kein '#' enthalten. (Wird automatisch hinzugefügt)"),
	TEAMREQUEST_PLAYER_NOT_ONLINE("Teamrequest.playerNotOnline", "%colorcode%%invitor% ist nicht mehr online!"),
	TEAMREQUEST_REVOKED("Teamrequest.invationRevoked", "Einladung erfolgreich zurückgezogen!"),
	TEAMREQUEST_TEAM_FULL("Teamrequest.teamIsFull", "%invited% konnte dem Team nicht beitreten - es ist bereits voll."),
	WORLD_NO_LOWER_FLIGHT("World.noLowerFlight", "&7Niedriger darfst du nicht fliegen!"),
	// WORLD
	WORLD_SPAWN_NUMBER("World.spawnNameTag.number", "&7Spawn &b%number%"),
	WORLD_SPAWN_PLAYER("World.spawnNameTag.player", "&7Spawn von %colorcode%%player%");

	private String defaultValue;
	private String path;
	private String value;

	private ConfigMessages(String path, String value) {
		this.path = path;
		this.value = value;
		this.defaultValue = value;
	}

	public Object getDefaultValue() {
		return defaultValue;
	}

	public String getPath() {
		return path;
	}

	public String getSection() {
		return this.path.split("\\.")[0];
	}

	public String getValue() {
		return getValue(value);
	}

	public String getValue(VaroPlayer vp) {
		return getValue(value, vp);
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static ArrayList<ConfigMessages> getBySection(String section) {
		ArrayList<ConfigMessages> list = new ArrayList<>();
		for(ConfigMessages entry : ConfigMessages.values()) {
			if(!entry.getSection().equals(section))
				continue;

			list.add(entry);
		}

		return list;
	}

	public static ConfigMessages getEntryByPath(String path) {
		for(ConfigMessages entry : ConfigMessages.values()) {
			if(!entry.getPath().equals(path) && !entry.getPath().contains(path))
				continue;

			return entry;
		}

		return null;
	}

	public static ArrayList<String> getSections() {
		ArrayList<String> list = new ArrayList<>();
		for(ConfigMessages entry : values())
			if(!list.contains(entry.getSection()))
				list.add(entry.getSection());

		return list;
	}

	public static String getValue(String value) {
		String replaced = value;
		for(GeneralMessagePlaceholder gmp : GeneralMessagePlaceholder.getGeneralPlaceholder()) 
			if(gmp.containsPlaceholder(value))
				replaced = gmp.replacePlaceholder(replaced);

		return replaced;
	}

	public static String getValue(String value, VaroPlayer vp) {
		String replaced = getValue(value);
		for(PlayerMessagePlaceholder pmp : PlayerMessagePlaceholder.getPlayerPlaceholder()) 
			if(pmp.containsPlaceholder(value))
				replaced = pmp.replacePlaceholder(replaced, vp);

		return replaced;
	}
}