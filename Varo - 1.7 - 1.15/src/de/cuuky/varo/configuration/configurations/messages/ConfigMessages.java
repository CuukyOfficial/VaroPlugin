package de.cuuky.varo.configuration.configurations.messages;

import java.util.ArrayList;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.SectionConfiguration;
import de.cuuky.varo.configuration.configurations.SectionEntry;
import de.cuuky.varo.configuration.placeholder.placeholder.GeneralMessagePlaceholder;
import de.cuuky.varo.configuration.placeholder.placeholder.PlayerMessagePlaceholder;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.team.VaroTeam;

public enum ConfigMessages implements SectionEntry {

	// ALERTS
	
	ALERT_AUTOSTART_AT(ConfigMessageSection.ALERTS, "BOTS_ALERT.autostartAt", "%projectname% wird am %date% starten!"),
	ALERT_BORDER_CHANGED(ConfigMessageSection.ALERTS, "BOTS_ALERT.borderChanged", "Die Border wurde auf %size% gesetzt!"),
	ALERT_BORDER_DECREASED_DEATH(ConfigMessageSection.ALERTS, "BOTS_ALERT.borderDecrease.death", "Die Border wurde um %size% aufgrund eines Todes verringert!"),
	ALERT_BORDER_DECREASED_TIME_DAYS(ConfigMessageSection.ALERTS, "BOTS_ALERT.borderDecrease.days", "Die Border wurde um %size% verkleinert. Naechste Verkleinerung in %days% Tagen!"),
	ALERT_BORDER_DECREASED_TIME_MINUTE(ConfigMessageSection.ALERTS, "BOTS_ALERT.borderDecrease.minutes", "Die Border wurde um %size% verringert! Naechste Verkleinerung in %minutes% Minuten!"),
	ALERT_COMBAT_LOG(ConfigMessageSection.ALERTS, "BOTS_ALERT.combatlog", "%player% hat sich im Kampf ausgeloggt!"),
	ALERT_COMBAT_LOG_STRIKE(ConfigMessageSection.ALERTS, "BOTS_ALERT.combatlogStrike", "%player% hat sich im Kampf ausgeloggt und hat somit einen Strike erhalten!"),
	ALERT_DISCONNECT_TOO_OFTEN(ConfigMessageSection.ALERTS, "BOTS_ALERT.disconnectTooOften", "%player% hat das Spiel zu oft verlassen, weswegen seine Session entfernt wurde!"),
	ALERT_DISCORD_DEATH(ConfigMessageSection.ALERTS, "BOTS_ALERT.death", "%player% ist gestorben! Grund: %reason%"),
	ALERT_DISCORD_KILL(ConfigMessageSection.ALERTS, "BOTS_ALERT.kill", "%player% wurde von %killer% getoetet!"),
	ALERT_FIRST_STRIKE(ConfigMessageSection.ALERTS, "BOTS_ALERT.firstStrike", "%player% hat nun einen Strike. Der Strike wurde von %striker% gegeben. Begruendung: %reason%\nAufgrund dessen sind hier die derzeiten Koordinaten: %pos%!"),
	ALERT_FIRST_STRIKE_NEVER_ONLINE(ConfigMessageSection.ALERTS, "BOTS_ALERT.firstStrikeNeverOnline", "%player% hat nun einen Strike. Der Strike wurde von %striker% gegeben. Begruendung: %reason%\nDer Spieler war noch nicht online und wird an den Spawn-Koordinaten spawnen: %pos%!"),
	ALERT_GAME_STARTED(ConfigMessageSection.ALERTS, "BOTS_ALERT.gameStarted", "%projectname% wurde gestartet!"),
	ALERT_GENERAL_STRIKE(ConfigMessageSection.ALERTS, "BOTS_ALERT.generalStrike", "%player% hat nun den %strikeNumber%ten Strike! Der Strike wurde von %striker% gegeben. Begruendung: %reason%"),
	ALERT_JOIN_FINALE(ConfigMessageSection.ALERTS, "BOTS_ALERT.finale", "%player% &7hat den Server zum Finale betreten."),
	ALERT_KICKED_PLAYER(ConfigMessageSection.ALERTS, "BOTS_ALERT.kickedPlayer", "%player% wurde gekickt!"),
	ALERT_NEW_SESSIONS(ConfigMessageSection.ALERTS, "BOTS_ALERT.newSessions", "Es wurden %newSessions% neue Folgen an die Spieler gegeben!"),
	ALERT_NEW_SESSIONS_FOR_ALL(ConfigMessageSection.ALERTS, "BOTS_ALERT.newSessionsForAll", "Alle haben %newSessions% neue Folgen bekommen!"),
	ALERT_NO_BLOODLUST(ConfigMessageSection.ALERTS, "BOTS_ALERT.noBloodlust", "%player% hat nun %days% Tage nicht gekaempft, was das Limit ueberschritten hat!"),
	ALERT_NO_BLOODLUST_STRIKE(ConfigMessageSection.ALERTS, "BOTS_ALERT.noBloodlustStrike", "%player% hat nun %days% Tage nicht gekaempft, weswegen %player% jetzt gestriket wurde!"),
	ALERT_NOT_JOIN(ConfigMessageSection.ALERTS, "BOTS_ALERT.notJoin", "%player% war nun %days% Tage nicht online, was das Limit ueberschritten hat!"),
	ALERT_NOT_JOIN_STRIKE(ConfigMessageSection.ALERTS, "BOTS_ALERT.notJoinStrike", "%player% war nun %days% Tage nicht online, weswegen %player% jetzt gestriket wurde!"),
	ALERT_PLAYER_DC_TO_EARLY(ConfigMessageSection.ALERTS, "BOTS_ALERT.playerQuitToEarly", "%player% hat das Spiel vorzeitig verlassen! %player% hat noch %seconds% Sekunden Spielzeit ueber!"),
	ALERT_PLAYER_JOIN_MASSREC(ConfigMessageSection.ALERTS, "BOTS_ALERT.playerJoinMassrec", "%player% hat den Server in der Massenaufnahme betreten und spielt nun die %episodesPlayedPlus1%te Folge"),
	ALERT_PLAYER_JOIN_NORMAL(ConfigMessageSection.ALERTS, "BOTS_ALERT.playerJoinNormal", "%player% hat das Spiel betreten!"),
	ALERT_PLAYER_JOINED(ConfigMessageSection.ALERTS, "BOTS_ALERT.playerJoined", "%player% hat den Server betreten und spielt nun die %episodesPlayedPlus1%te Folge!"),
	ALERT_PLAYER_QUIT(ConfigMessageSection.ALERTS, "BOTS_ALERT.playerQuit", "%player% hat das Spiel verlassen!"),
	ALERT_PLAYER_RECONNECT(ConfigMessageSection.ALERTS, "BOTS_ALERT.playerReconnect", "%player% hatte das Spiel vorzeitig verlassen und ist rejoint! %player% hat noch %seconds% Sekunden verbleibend!"),
	ALERT_SECOND_STRIKE(ConfigMessageSection.ALERTS, "BOTS_ALERT.secondStrike", "%player% hat nun zwei Strikes. Der Strike wurde von %striker% gegeben. Begruendung: %reason%\nAufgrund dessen wurde das Inventar geleert!"),
	ALERT_SWITCHED_NAME(ConfigMessageSection.ALERTS, "BOTS_ALERT.switchedName", "%player% hat den Namen gewechselt und ist nun unter %newName% bekannt!"),
	ALERT_TELEPORTED_TO_MIDDLE(ConfigMessageSection.ALERTS, "BOTS_ALERT.teleportedToMiddle", "%player% wurde zur Mitte teleportiert!"),
	ALERT_THRID_STRIKE(ConfigMessageSection.ALERTS, "BOTS_ALERT.thirdStrike", "%player% hat nun drei Strikes. Der Strike wurde von %striker% gegeben. Begruendung: %reason%\nDamit ist %player% aus %projectname% ausgeschieden!"),
	ALERT_WINNER(ConfigMessageSection.ALERTS, "BOTS_ALERT.win.player", "%player% hat %projectname% gewonnen! Gratulation!"),
	ALERT_WINNER_TEAM(ConfigMessageSection.ALERTS, "BOTS_ALERT.win.team", "%winnerPlayers% haben %projectname% gewonnen! Gratulation!"),
	
	// BOTS
	
	BOTS_DISCORD_NOT_REGISTERED_DISCORD(ConfigMessageSection.BOTS, "notRegisteredDiscord", "&cDu bist noch nicht mit dem Discord authentifiziert!\n&7Um dich zu authentifizieren, schreibe in den #verify -Channel &c'varo verify <Deine ID>' &7auf dem Discord!\nLink zum Discordserver: &c%discordLink%\n&7Deine ID lautet: &c%code%"),
	BOTS_DISCORD_NO_SERVER_USER(ConfigMessageSection.BOTS, "noServerUser", "&cDein Account ist nicht auf dem Discord!%nextLine%&7Joine dem Discord und versuche es erneut."),
	
	// BORDER
	
	BORDER_MINIMUM_REACHED(ConfigMessageSection.BORDER, "minimumReached", "&cDie Border hat ihr Minimum erreicht!"),
	BORDER_DECREASE_DAYS(ConfigMessageSection.BORDER, "decreaseDays", "&7Die Border wird jetzt um &c%size% &7Bloecke mit &c%speed% &7Bloecken/s verkleinert. &7Naechste Verkleinerung in &c%days% &7Tagen!"),
	BORDER_DECREASE_DEATH(ConfigMessageSection.BORDER, "decreaseDeath", "&7Die Border wird jetzt um &c%size% &7Bloecke mit &c%speed% &7Bloecken/s aufgrund eines Todes verkleinert."),
	BORDER_MINUTE_TIME_UPDATE(ConfigMessageSection.BORDER, "minuteTimeUpdate", "&7Die Border wird in &c%minutes%&7:&c%seconds% &7verkleinert!"),
	BORDER_DECREASE_MINUTES(ConfigMessageSection.BORDER, "decreaseMinutes", "&7Die Border wird jetzt um &c%size% &7Bloecke mit &c%speed% &7Bloecken/s verkleinert. &7Naechste Verkleinerung in &c%days% &7Minuten!"),	
	BORDER_DISTANCE(ConfigMessageSection.BORDER, "distanceToBorder", "&7Distanz zur Border: %colorcode%%size% &7Bloecke"),
	BORDER_COMMAND_SET_BORDER(ConfigMessageSection.BORDER, "borderSet", "&7Die Border wurde auf %colorcode%%size% &7gesetzt!"),
	
	// CHAT
	
	CHAT_FORMAT(ConfigMessageSection.CHAT, "format", "§7%player% §8» §f"),
	CHAT_TEAMCHAT_FORMAT(ConfigMessageSection.CHAT, "teamchatFormat", "&7[%team%&7] %player% &8» &f%message%"),
	CHAT_MUTED(ConfigMessageSection.CHAT, "muted", "&7Du wurdest gemutet!"),
	CHAT_WHEN_START(ConfigMessageSection.CHAT, "chatOnStart", "&7Du kannst erst ab dem Start wieder schreiben!"),
	
	// COMBAT
	
	COMBAT_FRIENDLY_FIRE(ConfigMessageSection.COMBAT, "friendlyfire", "§7Dieser Spieler ist in deinem Team!"),
	COMBAT_IN_FIGHT(ConfigMessageSection.COMBAT, "inFight", "&7Du bist nun im Kampf, logge dich &4NICHT &7aus!"),
	COMBAT_LOGGED_OUT(ConfigMessageSection.COMBAT, "loggedOut", "&c%player% &7hat den Server waehrend eines Kampfes verlassen!"),
	COMBAT_NOT_IN_FIGHT(ConfigMessageSection.COMBAT, "notInFight", "§7Du bist nun nicht mehr im &cKampf&7!"),
	
	// SPAWN
	
	SPAWN_WORLD(ConfigMessageSection.SPAWN, "spawn", "%colorcode%Koordinaten&7 vom Spawn: %colorcode%%x%&7, %colorcode%%y%&7, %colorcode%%z%"),
	SPAWN_NETHER(ConfigMessageSection.SPAWN, "spawnNether", "%colorcode%Koordinaten&7 vom Portal zur Oberwelt: %colorcode%%x%&7, %colorcode%%y%&7, %colorcode%%z%"),
	SPAWN_DISTANCE(ConfigMessageSection.SPAWN, "spawnDistance", "&7Du bist %colorcode%%distance% &7Bloecke vom Spawn entfernt!"),
	SPAWN_DISTANCE_NETHER(ConfigMessageSection.SPAWN, "spawnDistanceNether", "&7Du bist %colorcode%%distance% &7Bloecke vom Portal zur Oberwelt entfernt!"),
	
	// DEATH
	
	DEATH_DEAD(ConfigMessageSection.DEATH, "killMessage", "&c%player% &7ist gestorben. &7Grund: &c%reason%"),
	DEATH_KILLED_BY(ConfigMessageSection.DEATH, "killed", "%colorcode%%player% &7wurde von &c%killer% &7getoetet!"),
	DEATH_LIFE_LOST(ConfigMessageSection.DEATH, "teamLifeLost", "%player% hat nun noch %colorcode%%teamLifes% &7Teamleben!"),
	DEATH_RESPAWN_PROTECTION(ConfigMessageSection.DEATH, "respawnProtection", "&c%player% hat nun ein Leben weniger und ist fuer %seconds% unverwundbar!"),
	DEATH_RESPAWN_PROTECTION_OVER(ConfigMessageSection.DEATH, "respawnProtectionOver", "&c%player% ist nun wieder verwundbar!"),
	DEATH_KILL_LIFE_ADD(ConfigMessageSection.DEATH, "killLifeAdd", "Dein Team hat aufgrund eines Kills ein Teamleben erhalten!"),
	
	// GAME
	
	GAME_START_COUNTDOWN(ConfigMessageSection.GAME, "start.startCountdown", "%projectname% &7startet in %colorcode%%countdown% &7Sekunde(n)!"),
	GAME_VARO_START(ConfigMessageSection.GAME, "start.varoStart", "%projectname% &7wurde gestartet! &5Viel Erfolg!"),
	GAME_VARO_START_TITLE(ConfigMessageSection.GAME, "start.startTitle", "%colorcode%%countdown%\n&7Viel Glueck!"),
	GAME_WIN(ConfigMessageSection.GAME, "win.single", "&5%player% &7hat %projectname% &7gewonnen! &5Gratulation!"),
	GAME_WIN_TEAM(ConfigMessageSection.GAME, "win.team", "&5%winnerPlayers% &7haben %projectname% &7gewonnen! &5Gratulation!"),
	
	// JOIN
	
	JOIN_MESSAGE(ConfigMessageSection.JOINMESSAGE, "join", "%prefix%&a%player% &7hat den Server betreten!"),
	JOIN_FINALE(ConfigMessageSection.JOINMESSAGE, "finale", "%prefix%&a%player% &7hat den Server zum Finale betreten."),
	JOIN_MASS_RECORDING(ConfigMessageSection.JOINMESSAGE, "massrecording", "%prefix%&a%player% &7hat den Server in der Massenaufnahme betreten und ist in %colorcode%%protectionTime% &7Sekunden angreifbar!"),
	JOIN_NO_MOVE_IN_PROTECTION(ConfigMessageSection.JOINMESSAGE, "notMoveinProtection", "&7Du kannst dich nicht bewegen, solange du noch in der %colorcode%Schutzzeit &7bist!"),
	JOIN_PROTECTION_OVER(ConfigMessageSection.JOINMESSAGE, "joinProtectionOver", "%prefix%&a%player% &7ist nun angreifbar!"),
	JOIN_PROTECTION_TIME(ConfigMessageSection.JOINMESSAGE, "joinProtectionTime", "%prefix%&a%player% &7hat den Server betreten und ist in %colorcode%%protectionTime% &7Sekunden angreifbar!"),
	JOIN_SPECTATOR(ConfigMessageSection.JOINMESSAGE, "spectator", "&a%player% &7hat den Server als Spectator betreten!"),
	JOIN_WITH_REMAINING_TIME(ConfigMessageSection.JOINMESSAGE, "joinWithRemainingTime", "%prefix%&a%player% &7hatte den Server zu frueh verlassen und hat jetzt noch %colorcode%%seconds% &7Sekunden uebrig! Verbleibende &cDisconnects&7: &c%remainingDisconnects%"),

	
	// QUIT
	
	QUIT_MESSAGE(ConfigMessageSection.QUITMESSAGE, "quit","%prefix%&c%player%&7 hat den Server verlassen!"),
	QUIT_DISCONNECT_SESSION_END(ConfigMessageSection.QUITMESSAGE, "disconnectKilled", "&c%player% &7hat das Spiel verlassen und ist seit &c%banTime% &7Minute(n) nicht mehr online.%nextLine%&7Damit ist er aus %projectname% &7ausgeschieden!"),
	QUIT_SPECTATOR(ConfigMessageSection.QUITMESSAGE, "spectator", "&c%player% &7hat den Server als Spectator verlassen!"),
	QUIT_TOO_OFTEN(ConfigMessageSection.QUITMESSAGE, "quitTooOften", "&c%player% &7hat den Server zu oft verlassen und dadurch seine Sitzung verloren."),
	QUIT_WITH_REMAINING_TIME(ConfigMessageSection.QUITMESSAGE, "quitRemainingTime", "%prefix%&c%player% &7hat den Server vorzeitig verlassen!"),
	QUIT_KICK_BROADCAST(ConfigMessageSection.QUITMESSAGE, "broadcast", "%colorcode%%player% &7wurde gekickt!"),
	QUIT_KICK_DELAY_OVER(ConfigMessageSection.QUITMESSAGE, "protectionOver", "%colorcode%%player% &7wurde aufgrund seines Todes jetzt gekickt!"),
	QUIT_KICK_IN_SECONDS(ConfigMessageSection.QUITMESSAGE, "kickInSeconds", "%colorcode%%player% &7wird in %colorcode%%countdown% &7Sekunde(n) gekickt!"),
	QUIT_KICK_PLAYER_NEARBY(ConfigMessageSection.QUITMESSAGE, "noKickPlayerNearby", "&cEs befindet sich ein Spieler &4%distance% &cBloecke in deiner Naehe!%nextLine%&7Um gekickt zu werden, entferne dich von diesem Spieler!"),
	QUIT_KICK_SERVER_CLOSE_SOON(ConfigMessageSection.QUITMESSAGE, "serverCloseSoon", "&7Der Server schliesst in &c%minutes% &7Minuten!"),
	
	// KICK
	
	DEATH_KICK_DEAD(ConfigMessageSection.KICK, "kickedKilled", "&cDu bist gestorben! %nextLine% &7Damit bist du aus %projectname% &7ausgeschieden"),
	DEATH_KICK_KILLED(ConfigMessageSection.KICK, "killedKick", "&7Du wurdest von &c%killer% &7getoetet! %nextLine% &7Damit bist du aus %projectname% &7ausgeschieden!"),
	JOIN_KICK_NOT_USER_OF_PROJECT(ConfigMessageSection.KICK, "notUserOfTheProject", "&7Du bist kein Teilnehmer dieses %projectname%&7's!"),
	JOIN_KICK_SERVER_FULL(ConfigMessageSection.KICK, "serverFull", "&cDer Server ist voll!%nextLine%&7Sprich mit dem Owner, falls das das ein Irrtum sein sollte!"),
	JOIN_KICK_STRIKE_BAN(ConfigMessageSection.KICK, "strikeBan", "&cDu wurdest aufgrund deines letzten Strikes fuer %hours% gebannt!\nVersuche es spaeter erneut"),
	JOIN_KICK_BANNED(ConfigMessageSection.KICK, "banned", "&4Du bist vom Server gebannt!\n&7Melde dich bei einem Admin, falls dies ein Fehler sein sollte.\n&7Grund: &c%reason%"),
	JOIN_KICK_NO_PREPRODUCES_LEFT(ConfigMessageSection.KICK, "noPreproduceLeft", "&cDu hast bereits vorproduziert! %nextLine%&7Versuche es morgen erneut."),
	JOIN_KICK_NO_SESSIONS_LEFT(ConfigMessageSection.KICK, "noSessionLeft", "&cDu hast keine Sessions mehr uebrig! %nextLine%&7Warte bis morgen, damit du wieder spielen kannst!"),
	JOIN_KICK_NO_TIME_LEFT(ConfigMessageSection.KICK, "noTimeLeft", "&cDu darfst nur alle &4%timeHours% &cStunden regulaer spielen! %nextLine%&7Du kannst erst in &c%stunden%&7:&c%minuten%&7:&c%sekunden% &7wieder joinen!"),
	JOIN_KICK_NOT_STARTED(ConfigMessageSection.KICK, "notStarted", "&cDer Server wurde noch nicht eroeffnet! %nextLine%&7Gedulde dich noch ein wenig!"),
	KICK_SESSION_OVER(ConfigMessageSection.KICK, "kickMessage", "&cDeine Aufnahmezeit ist abgelaufen, %nextLine%&7deswegen wurdest du gekickt!"),
	KICK_MASS_REC_SESSION_OVER(ConfigMessageSection.KICK, "kickMessageMassRec", "&cDie Massenaufnahme ist beendet, %nextLine%&7deswegen wurdest du gekickt!"),
	KICK_TOO_MANY_STRIKES(ConfigMessageSection.KICK, "tooManyStrikes", "&7Du hast zu viele Strikes bekommen und wurdest daher aus dem Projekt %projectname% &7entfernt."),
	KICK_COMMAND(ConfigMessageSection.KICK, "kick", "%colorcode%%player% &7wurde gekickt!"),
	
	// LABYMOD
	
	LABYMOD_DISABLED(ConfigMessageSection.LABYMOD, "labyModDisabled", "&7Alle deine LabyMod Funktionen wurden deaktiviert!"),
	LABYMOD_KICK(ConfigMessageSection.LABYMOD, "labyMod", "&cLabyMod isn't allowed on this server."),
	
	// MOTD
	
	SERVER_MODT_CANT_JOIN_HOURS(ConfigMessageSection.MOTD, "cantJoinHours", "&cDu kannst nur zwischen &4%minHour% &cund &4%maxHour%&c Uhr joinen! %nextLine%&7Versuche es spaeter erneut! &7%currHour%&7:&7%currMin%&7:&7%currSec%"),
	SERVER_MODT_NOT_OPENED(ConfigMessageSection.MOTD, "serverNotOpened", "&cDer Server wurde noch nicht fuer alle geoeffnet! %nextLine%&7Versuche es spaeter erneut!"),
	SERVER_MODT_OPEN(ConfigMessageSection.MOTD, "serverOpen", "&aSei nun bei %projectname% &adabei! \n&7Viel Spass!"),
	
	// NAMETAG
	
	NAMETAG_NORMAL(ConfigMessageSection.NAMETAG, "normalNametagPrefix", "&7"),
	NAMETAG_SUFFIX(ConfigMessageSection.NAMETAG, "normalSuffix", "&c %kills%"),
	NAMETAG_TEAM_PREFIX(ConfigMessageSection.NAMETAG, "nametagWithTeam", "%colorcode%%team% &7"),
	
	// CHEST
	
	CHEST_NOT_TEAM_CHEST(ConfigMessageSection.CHEST, "notTeamChest", "&7Diese Kiste gehoert %colorcode%%player%&7!"),
	CHEST_NOT_TEAM_FURNACE(ConfigMessageSection.CHEST, "notTeamFurnace", "&7Dieser Ofen gehoert %colorcode%%player%&7!"),
	CHEST_REMOVED_SAVEABLE(ConfigMessageSection.CHEST, "removedChest", "&7Du hast diese/n %saveable% %colorcode%erfolgreich &7entfernt!"),
	CHEST_SAVED_SAVEABLE(ConfigMessageSection.CHEST, "newChestSaved", "&7Eine neue Kiste wurde gesichert!"),

	// NO PERMISSION
	
	NOPERMISSION_NO_PERMISSION(ConfigMessageSection.NOPERMISSION, "noPermission", "%colorcode%Dazu bist du nicht berechtigt!"),
	NOPERMISSION_NOT_ALLOWED_CRAFT(ConfigMessageSection.NOPERMISSION, "notAllowedCraft", "&7Das darfst du nicht craften, benutzen oder brauen!"),
	NOPERMISSION_NO_LOWER_FLIGHT(ConfigMessageSection.NOPERMISSION, "noLowerFlight", "&7Niedriger darfst du nicht fliegen!"),

	// PROTECTION
	
	PROTECTION_NO_MOVE_START(ConfigMessageSection.PROTECTION, "noMoveStart", "§7Du kannst dich nicht bewegen, solange das Projekt noch nicht gestartet wurde."),
	PROTECTION_START(ConfigMessageSection.PROTECTION, "start", "&7Die &cSchutzzeit &7startet jetzt und wird &c%seconds% &7Sekunden anhalten!"),
	PROTECTION_TIME_OVER(ConfigMessageSection.PROTECTION, "protectionOver", "&7Die &cSchutzzeit &7ist nun vorrueber!"),
	PROTECTION_TIME_UPDATE(ConfigMessageSection.PROTECTION, "protectionUpdate", "&7Die &cSchutzzeit &7ist in &c%minutes%&7:&c%seconds% &7vorrueber!"),
	PROTECTION_TIME_RUNNING(ConfigMessageSection.PROTECTION, "timeRunning", "&7Die %colorcode%Schutzzeit &7laeuft noch!"),
	
	// SORT
	
	SORT_NO_HOLE_FOUND(ConfigMessageSection.SORT, "noHoleFound", "Es konnte fuer dich kein Loch gefunden werden!"),
	SORT_NO_HOLE_FOUND_TEAM(ConfigMessageSection.SORT, "noHoleFoundTeam", "Es konnte fuer dich kein Loch bei deinen Teampartnern gefunden werden."),
	SORT_NUMBER_HOLE(ConfigMessageSection.SORT, "numberHoleTeleport", "Du wurdest in das Loch %colorcode%%number% &7teleportiert!"),
	SORT_OWN_HOLE(ConfigMessageSection.SORT, "ownHoleTeleport", "Du wurdest in dein Loch einsortiert!"),
	SORT_SPECTATOR_TELEPORT(ConfigMessageSection.SORT, "spectatorTeleport", "Du wurdest, da du Spectator bist, zum Spawn teleportiert!"),
	SORT_SORTED(ConfigMessageSection.SORT, "sorted", "&7Du wurdest in das Loch %colorcode%%zahl% &7teleportiert!"),
	
	// TABLIST
	
	TABLIST_PLAYER_WITH_TEAM(ConfigMessageSection.TABLIST, "player.withTeam", "%colorcode%%team% &8| &7%player%  &c%kills%"),
	TABLIST_PLAYER_WITH_TEAM_RANK(ConfigMessageSection.TABLIST, "player.withTeamAndRank", "&7%rank% &8| %colorcode%%team% &8| &7%player%  &c%kills%"),
	TABLIST_PLAYER_WITHOUT_TEAM(ConfigMessageSection.TABLIST, "player.withoutTeam", "&7%player%  &c%kills%"),
	TABLIST_PLAYER_WITHOUT_TEAM_RANK(ConfigMessageSection.TABLIST, "player.withoutTeamWithRank", "&7%rank% &8| &7%player%  &c%kills%"),	
	
	// TEAMREQUEST
	
	TEAMREQUEST_ENTER_TEAMNAME(ConfigMessageSection.TEAMREQUEST, "enterTeamName", "%colorcode%&lGib jetzt den Teamnamen fuer dich und %invited% ein:"),
	TEAMREQUEST_MAX_TEAMNAME_LENGTH(ConfigMessageSection.TEAMREQUEST, "maxTeamnameLength", "Dein Teamname darf maximal %colorcode%%maxLength% &7Zeichen enthalten!"),
	TEAMREQUEST_NO_COLORCODE(ConfigMessageSection.TEAMREQUEST, "noColorCode", "Dein Teamname darf keine Farbcodes enthalten!"),
	TEAMREQUEST_NO_HASHTAG(ConfigMessageSection.TEAMREQUEST, "noHashtag", "Dein Teamname darf kein '#' enthalten. (Wird automatisch hinzugefuegt)"),
	TEAMREQUEST_PLAYER_NOT_ONLINE(ConfigMessageSection.TEAMREQUEST, "playerNotOnline", "%colorcode%%invitor% ist nicht mehr online!"),
	TEAMREQUEST_REVOKED(ConfigMessageSection.TEAMREQUEST, "invationRevoked", "Einladung erfolgreich zurueckgezogen!"),
	TEAMREQUEST_TEAM_FULL(ConfigMessageSection.TEAMREQUEST, "teamIsFull", "%invited% konnte dem Team nicht beitreten - es ist bereits voll."),
	TEAMREQUEST_TEAM_REQUEST_RECIEVED(ConfigMessageSection.TEAMREQUEST, "teamRequestRecieved", "%colorcode%%invitor% &7hat dich in ein Team eingeladen (/varo tr)!"),
	TEAMREQUEST_INVITED_TEAM(ConfigMessageSection.TEAMREQUEST, "invitedInTeam", "&7Du hast %colorcode%%invited% &7in das Team %colorcode%%team% &7eingeladen!"),
	TEAMREQUEST_NO_TEAMNAME(ConfigMessageSection.TEAMREQUEST, "noteamname", "&7Du hast noch &7keinen &7Teamnamen!"),
	
	// SPAWNS
	
	SPAWNS_SPAWN_NUMBER(ConfigMessageSection.SPAWNS, "spawnNameTag.number", "&7Spawn %colorcode%%number%"),
	SPAWNS_SPAWN_PLAYER(ConfigMessageSection.SPAWNS, "spawnNameTag.player", "&7Spawn von %colorcode%%player%"),
	
	// OTHER
	
	OTHER_CONFIG(ConfigMessageSection.OTHER, "configReload", "&7Die %colorcode%Config &7wurde neu geladen"),
	OTHER_PING(ConfigMessageSection.OTHER, "ping", "&7Dein %colorcode%Ping &7betraegt: %colorcode%%ping%&7ms");

	private ConfigMessageSection section;
	private String defaultValue, path, value;

	private ConfigMessages(ConfigMessageSection section, String path, String value) {
		this.path = path;
		this.section = section;
		this.value = value;
		this.defaultValue = value;
	}

	@Override
	public Object getDefaultValue() {
		return defaultValue;
	}

	@Override
	public String getPath() {
		return path;
	}
	
	@Override
	public String[] getDescription() {
		return null;
	}
	
	@Override
	public SectionConfiguration getSection() {
		return this.section;
	}
	
	@Override
	public void setValue(Object value) {
		this.value = String.valueOf(value);
	}

	public String getValue() {
		return getValue(value);
	}

	public String getValue(VaroPlayer vp) {
		return getValue(value, vp);
	}
	
	private static ArrayList<Integer> getConvNumbers(String line, String key) {
		ArrayList<Integer> list = new ArrayList<>();

		boolean first = true;
		for(String split0 : line.split(key)) {
			if(first) {
				first = false;
				if(!line.startsWith(key))
					continue;
			}

			String[] split1 = split0.split("%", 2);

			if(split1.length == 2) {
				try {
					list.add(Integer.parseInt(split1[0]));
				} catch(NumberFormatException e) {
					continue;
				}
			}
		}

		return list;
	}

	public static String getValue(String value) {
		String replaced = value;
		
		for(int rank : getConvNumbers(replaced, "%topplayer-")) {
			VaroPlayer player = Main.getVaroGame().getTopScores().getPlayer(rank);
			replaced = replaced.replace("%topplayer-" + rank + "%", (player == null ? "-" : player.getName()));
		}

		for(int rank : getConvNumbers(replaced, "%topplayerkills-")) {
			VaroPlayer player = Main.getVaroGame().getTopScores().getPlayer(rank);
			replaced = replaced.replace("%topplayerkills-" + rank + "%", (player == null ? "0" : String.valueOf(player.getStats().getKills())));
		}

		for(int rank : getConvNumbers(replaced, "%topteam-")) {
			VaroTeam team = Main.getVaroGame().getTopScores().getTeam(rank);
			replaced = replaced.replace("%topteam-" + rank + "%", (team == null ? "-" : team.getName()));
		}

		for(int rank : getConvNumbers(replaced, "%topteamkills-")) {
			VaroTeam team = Main.getVaroGame().getTopScores().getTeam(rank);
			replaced = replaced.replace("%topteamkills-" + rank + "%", (team == null ? "0" : String.valueOf(team.getKills())));
		}
		
		for(GeneralMessagePlaceholder gmp : GeneralMessagePlaceholder.getGeneralPlaceholder()) {
			if(gmp.containsPlaceholder(value))
				replaced = gmp.replacePlaceholder(replaced);
		}

		return replaced;
	}

	public static String getValue(String value, VaroPlayer vp) {
		for(PlayerMessagePlaceholder pmp : PlayerMessagePlaceholder.getPlayerPlaceholder()) 
			if(pmp.containsPlaceholder(value))
				value = pmp.replacePlaceholder(value, vp);

		return getValue(value);
	}
}