package de.cuuky.varo.configuration.configurations.language.languages;

import de.cuuky.cfw.configuration.language.Language;
import de.cuuky.cfw.configuration.language.languages.DefaultLanguage;
import de.cuuky.cfw.player.CustomPlayer;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;

public enum ConfigMessages implements DefaultLanguage {

	BOTS_DISCORD_NOT_REGISTERED_DISCORD("bots.notRegisteredDiscord", "&cDu bist noch nicht mit Discord verifiziert!\n&7Nutze &c/verify %code% &7um dich auf unserem Discord zu verifizieren!\nLink zum Discord: &c%discordLink%"),
	BOTS_DISCORD_NO_SERVER_USER("bots.noServerUser", "&cDein Account ist nicht auf dem Discord!%nextLine%&7Joine dem Discord und versuche es erneut."),

	COMBAT_FRIENDLY_FIRE("combat.friendlyfire", "&7Dieser Spieler ist in deinem Team!"),
	COMBAT_IN_FIGHT("combat.inFight", "&7Du bist nun im Kampf, logge dich &4NICHT &7aus!"),
	COMBAT_LOGGED_OUT("combat.loggedOut", "&c%player% &7hat den Server während eines Kampfes verlassen!"),
	COMBAT_NOT_IN_FIGHT("combat.notInFight", "&7Du bist nun nicht mehr im &cKampf&7!"),

	SPAWN_WORLD("spawn.spawn", "%colorcode%Koordinaten&7 vom Spawn: %colorcode%%x%&7, %colorcode%%y%&7, %colorcode%%z%"),
	SPAWN_NETHER("spawn.spawnNether", "%colorcode%Koordinaten&7 vom Portal zur Oberwelt: %colorcode%%x%&7, %colorcode%%y%&7, %colorcode%%z%"),
	SPAWN_DISTANCE("spawn.spawnDistance", "&7Du bist %colorcode%%distance% &7Blöcke vom Spawn entfernt!"),
	SPAWN_DISTANCE_NETHER("spawn.spawnDistanceNether", "&7Du bist %colorcode%%distance% &7Blöcke vom Portal zur Oberwelt entfernt!"),

	DEATH_ELIMINATED_OTHER("death.eliminated.other", "%colorcode%%player% &7ist gestorben. Grund: &c%reason%"),
	DEATH_ELIMINATED_PLAYER("death.eliminated.player", "%colorcode%%player% &7wurde von %colorcode%%killer% &7getötet!"),
	DEATH_LIFE_OTHER("death.teamLifeLost.other", "%colorcode%%player% &7ist gestorben und hat nun noch %colorcode%%teamLifes% &7Teamleben! Grund: &c%reason%"),
	DEATH_LIFE_PLAYER("death.teamLifeLost.player", "%colorcode%%player% &7wurde von %colorcode%%killer% &7getötet und hat nun noch %colorcode%%teamLifes% &7Teamleben! Grund: &c%reason%"),
	DEATH_RESPAWN_PROTECTION("death.respawnProtection", "&c%player% hat nun ein Leben weniger und ist für %seconds% unverwundbar!"),
	DEATH_RESPAWN_PROTECTION_OVER("death.respawnProtectionOver", "&c%player% ist nun wieder verwundbar!"),
	DEATH_KILL_LIFE_ADD("death.killLifeAdd", "Dein Team hat aufgrund eines Kills ein Teamleben erhalten!"),
	DEATH_KILL_TIME_ADD("death.killTimeAdd", "Aufgrund deines Kills hast du zusätzlich %colorcode%%timeAdded% &7Sekunden Zeit erhalten!"),

	GAME_VARO_START("game.start.varoStart", "%projectname% &7wurde gestartet! &5Viel Erfolg!"),
	GAME_VARO_START_TITLE("game.start.startTitle", "%colorcode%%countdown%"),
	GAME_VARO_START_SUBTITLE("game.start.startSubtitle", "&7Viel Glück!"),
	GAME_WIN("game.win.single", "&5%player% &7hat %projectname% &7gewonnen! &5Gratulation!"),
	GAME_WIN_TEAM("game.win.team", "&5%winnerPlayers% &7haben %projectname% &7gewonnen! &5Gratulation!"),

	QUIT_MESSAGE("quitmessage.quit", "%prefix%&c%player%&7 hat den Server verlassen!"),
	QUIT_DISCONNECT_SESSION_END("quitmessage.disconnectKilled", "&c%player% &7hat das Spiel verlassen und ist seit &c%banTime% &7Minute(n) nicht mehr online.%nextLine%&7Damit ist er aus %projectname% &7ausgeschieden!"),
	QUIT_SPECTATOR("quitmessage.spectator", "&c%player% &7hat den Server als Spectator verlassen!"),
	QUIT_TOO_OFTEN("quitmessage.quitTooOften", "&c%player% &7hat den Server zu oft verlassen und dadurch seine Sitzung verloren."),
	QUIT_WITH_REMAINING_TIME("quitmessage.quitRemainingTime", "%prefix%&c%player% &7hat den Server vorzeitig verlassen!"),
	QUIT_KICK_BROADCAST("quitmessage.broadcast", "%colorcode%%player% &7wurde gekickt!"),
	QUIT_KICK_DELAY_OVER("quitmessage.protectionOver", "%colorcode%%player% &7wurde aufgrund seines Todes jetzt gekickt!"),
	QUIT_KICK_IN_SECONDS("quitmessage.kickInSeconds", "%colorcode%%player% &7wird in %colorcode%%countdown% &7Sekunde(n) gekickt!"),
	QUIT_KICK_PLAYER_NEARBY("quitmessage.noKickPlayerNearby", "&cEs befindet sich ein Spieler &4%distance% &cBlöcke in deiner Nähe!%nextLine%&7Um gekickt zu werden, entferne dich von diesem Spieler!"),
	QUIT_KICK_SERVER_CLOSE_SOON("quitmessage.serverCloseSoon", "&7Der Server schliesst in &c%minutes% &7Minuten!"),

	DEATH_KICK_DEAD("kick.kickedKilled", "&cDu bist gestorben! %nextLine% &7Damit bist du aus %projectname% &7ausgeschieden"),
	DEATH_KICK_KILLED("kick.killedKick", "&7Du wurdest von &c%killer% &7getötet! %nextLine% &7Damit bist du aus %projectname% &7ausgeschieden!"),
	JOIN_KICK_NOT_USER_OF_PROJECT("kick.notUserOfTheProject", "&7Du bist kein Teilnehmer dieses %projectname%&7's!"),
	JOIN_KICK_SERVER_FULL("kick.serverFull", "&cDer Server ist voll!%nextLine%&7Sprich mit dem Owner, falls das das ein Irrtum sein sollte!"),
	JOIN_KICK_STRIKE_BAN("kick.strikeBan", "&cDu wurdest aufgrund deines letzten Strikes für %hours% gebannt!\nVersuche es später erneut"),
	JOIN_KICK_BANNED("kick.banned", "&4Du bist vom Server gebannt!\n&7Melde dich bei einem Admin, falls dies ein Fehler sein sollte.\n&7Grund: &c%reason%"),
	JOIN_KICK_NO_PREPRODUCES_LEFT("kick.noPreproduceLeft", "&cDu hast bereits vorproduziert! %nextLine%&7Versuche es morgen erneut."),
	JOIN_KICK_NO_SESSIONS_LEFT("kick.noSessionLeft", "&cDu hast keine Sessions mehr übrig! %nextLine%&7Warte bis morgen, damit du wieder spielen kannst!"),
	JOIN_KICK_NO_TIME_LEFT("kick.noTimeLeft", "&cDu darfst nur alle &4%timeHours% &cStunden regulär spielen! %nextLine%&7Du kannst erst in &c%stunden%&7:&c%minuten%&7:&c%sekunden% &7wieder joinen!"),
	JOIN_KICK_NOT_STARTED("kick.notStarted", "&cDer Server wurde noch nicht eröffnet! %nextLine%&7Gedulde dich noch ein wenig!"),
	KICK_SESSION_OVER("kick.kickMessage", "&cDeine Aufnahmezeit ist abgelaufen, %nextLine%&7deswegen wurdest du gekickt!"),
	KICK_MASS_REC_SESSION_OVER("kick.kickMessageMassRec", "&cDie Massenaufnahme ist beendet, %nextLine%&7deswegen wurdest du gekickt!"),
	KICK_TOO_MANY_STRIKES("kick.tooManyStrikes", "&7Du hast zu viele Strikes bekommen und wurdest daher aus dem Projekt %projectname% &7entfernt."),
	KICK_COMMAND("kick.kick", "%colorcode%%player% &7wurde gekick.kickt!"),

	SERVER_MODT_CANT_JOIN_HOURS("motd.cantJoinHours", "&cDu kannst nur zwischen &4%minHour% &cund &4%maxHour%&c Uhr joinen! %nextLine%&7Versuche es später erneut! &7%currHour%&7:&7%currMin%&7:&7%currSec%"),
	SERVER_MODT_NOT_OPENED("motd.serverNotOpened", "&cDer Server wurde noch nicht für alle geöffnet! %nextLine%&7Versuche es später erneut!"),
	SERVER_MODT_OPEN("motd.serverOpen", "&aSei nun bei %projectname% &adabei! \n&7Viel Spass!"),

	NAMETAG_PREFIX_NO_TEAM("nametag.prefix.noTeam", "&7"),
	NAMETAG_PREFIX_TEAM("nametag.prefix.team", "%colorcode%%team% &7"),
	NAMETAG_SUFFIX_NO_TEAM("nametag.suffix.noTeam", "&c %kills%"),
	NAMETAG_SUFFIX_TEAM("nametag.suffix.team", "&c %kills%"),

	CHEST_NOT_TEAM_CHEST("chest.notTeamChest", "&7Diese Kiste gehört %colorcode%%player%&7!"),
	CHEST_NOT_TEAM_FURNACE("chest.notTeamFurnace", "&7Dieser Ofen gehört %colorcode%%player%&7!"),
	CHEST_REMOVED_SAVEABLE("chest.removedChest", "&7Du hast diese/n %saveable% %colorcode%erfolgreich &7entfernt!"),
	CHEST_SAVED_CHEST("chest.newChestSaved", "&7Eine neue Kiste wurde gesichert!"),
	CHEST_SAVED_FURNACE("chest.newFurnaceSaved", "&7Ein neuer Ofen wurde gesichert!"),

	PLACEHOLDER_NO_TOP_PLAYER("placeholder.noTopPlayer", "-"), // TODO delete this
	PLACEHOLDER_NO_TOP_TEAM("placeholder.noTopTeam", "-"), // TODO delete this

	PROTECTION_NO_MOVE_START("protection.noMoveStart", "&7Du kannst dich nicht bewegen, solange das Projekt noch nicht gestartet wurde."),
	PROTECTION_START("protection.start", "&7Die &cSchutzzeit &7startet jetzt und wird &c%seconds% &7Sekunden anhalten!"),
	PROTECTION_TIME_OVER("protection.protectionOver", "&7Die &cSchutzzeit &7ist nun vorrüber!"),
	PROTECTION_TIME_UPDATE("protection.protectionUpdate", "&7Die &cSchutzzeit &7ist in &c%minutes%&7:&c%seconds% &7vorrüber!"),
	PROTECTION_TIME_RUNNING("protection.timeRunning", "&7Die %colorcode%Schutzzeit &7läuft noch!"),

	SORT_NO_HOLE_FOUND("sort.noHoleFound", "&7Es konnte für dich kein Loch gefunden werden!"),
	SORT_NO_HOLE_FOUND_TEAM("sort.noHoleFoundTeam", "Es konnte für dich kein Loch bei deinen Teampartnern gefunden werden."),
	SORT_NUMBER_HOLE("sort.numberHoleTeleport", "Du wurdest in das Loch %colorcode%%number% &7teleportiert!"),
	SORT_OWN_HOLE("sort.ownHoleTeleport", "Du wurdest in dein Loch einsortiert!"),
	SORT_SPECTATOR_TELEPORT("sort.spectatorTeleport", "Du wurdest, da du Spectator bist, zum Spawn teleportiert!"),
	SORT_SORTED("sort.sorted", "&7Du wurdest in das Loch %colorcode%%zahl% &7teleportiert!"),

	TABLIST_PLAYER_WITH_TEAM("tablist.player.withTeam", "%colorcode%%team% &8| &7%player%  &c%kills%"),
	TABLIST_PLAYER_WITH_TEAM_RANK("tablist.player.withTeamAndRank", "&7%rank% &8| %colorcode%%team% &8| &7%player%  &c%kills%"),
	TABLIST_PLAYER_WITHOUT_TEAM("tablist.player.withoutTeam", "&7%player%  &c%kills%"),
	TABLIST_PLAYER_WITHOUT_TEAM_RANK("tablist.player.withoutTeamWithRank", "&7%rank% &8| &7%player%  &c%kills%"),

	SPAWNS_SPAWN_NUMBER("spawns.spawnNameTag.number", "&7Spawn %colorcode%%number%"),
	SPAWNS_SPAWN_PLAYER("spawns.spawnNameTag.player", "&7Spawn von %colorcode%%player%"),

	MODS_BLOCKED_MODS_KICK("mods.blockedModsKick", "&7Bitte entferne folgende Mods: %colorcode%%mods%"),
	MODS_BLOCKED_MODLIST_SPLIT("mods.blockedModsListSplit", "&7, "),
	MODS_BLOCKED_MODS_BROADCAST("mods.blockedModsBroadcast", "&7Der Spieler %colorcode%%player% &7hat versucht mit folgenden blockierten Mods zu joinen: %colorcode%%mods%"),

	LOGGER_FILTER_INVALID_FILTER("blockLoggerFilter.invalidFilter", "&c%filterName% \"%content%\" ist nicht gültig!"),
	LOGGER_FILTER_SET_FILTER("blockLoggerFilter.setFilter", "&7%filterName% wurde auf %colorcode%%newContent% &7gesetzt (vorher: %oldContent%)."),
	LOGGER_FILTER_RESET_FILTER("blockLoggerFilter.resetFilter", "&7%filterName% wurde zurückgesetzt (vorher: %oldContent%)."),
	LOGGER_FILTER_PLAYER_FILTER_MESSAGE("blockLoggerFilter.playerFilterMessage", "&7Bitte gib einen Spielernamen ein:"),
	LOGGER_FILTER_MATERIAL_FILTER_MESSAGE("blockLoggerFilter.materialFilterMessage", "&7Bitte gib einen Materialnamen ein:");

	private String path, defaultMessage, message;

	ConfigMessages(String path, String message) {
		this.path = path;
		this.defaultMessage = message;
		this.message = message;
	}

	private String getMessage(Language lang) {
		String message;
		if (lang == null || lang == Main.getLanguageManager().getDefaultLanguage() || !ConfigSetting.MAIN_LANGUAGE_ALLOW_OTHER.getValueAsBoolean())
			message = Main.getLanguageManager().getDefaultLanguage().getMessage(this.path);
		else {
			String langMsg = lang.getMessage(this.path);
			message = langMsg == null ? Main.getLanguageManager().getDefaultLanguage().getMessage(this.path) : langMsg;
		}

		return Main.getLanguageManager().replaceMessage(message != null ? message : this.message);
	}

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public String getDefaultMessage() {
		return this.defaultMessage;
	}

	@Override
	public String getValue() {
		return getMessage(Main.getLanguageManager().getDefaultLanguage());
	}

	@Override
	public String getValue(CustomPlayer localeHolder) {
		return getMessage(localeHolder != null && localeHolder.getLocale() != null ? Main.getLanguageManager().getLanguages().get(localeHolder.getLocale()) : Main.getLanguageManager().getDefaultLanguage());
	}

	@Override
	public String getValue(CustomPlayer localeHolder, CustomPlayer replace) {
		return Main.getLanguageManager().replaceMessage(getValue(localeHolder), replace);
	}
}
