package de.cuuky.varo.configuration.configurations.language.languages;

import de.cuuky.cfw.configuration.language.Language;
import de.cuuky.cfw.configuration.language.languages.DefaultLanguage;
import de.cuuky.cfw.player.CustomPlayer;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;

public enum ConfigMessages implements DefaultLanguage {

	ALERT_AUTOSTART_AT("alerts.BOTS_ALERT.autostartAt", "%projectname% wird am %date% starten!"),
	ALERT_BORDER_CHANGED("alerts.BOTS_ALERT.borderChanged", "Die Border wurde auf %size% gesetzt!"),
	ALERT_BORDER_DECREASED_DEATH("alerts.BOTS_ALERT.borderDecrease.death", "Die Border wurde um %size% aufgrund eines Todes verringert!"),
	ALERT_BORDER_DECREASED_TIME_DAYS("alerts.BOTS_ALERT.borderDecrease.days", "Die Border wurde um %size% verkleinert. Nächste Verkleinerung in %days% Tagen!"),
	ALERT_BORDER_DECREASED_TIME_MINUTE("alerts.BOTS_ALERT.borderDecrease.minutes", "Die Border wurde um %size% verringert! Nächste Verkleinerung in %minutes% Minuten!"),
	ALERT_COMBAT_LOG("alerts.BOTS_ALERT.combatlog", "%player% hat sich im Kampf ausgeloggt!"),
	ALERT_COMBAT_LOG_STRIKE("alerts.BOTS_ALERT.combatlogStrike", "%player% hat sich im Kampf ausgeloggt und hat somit einen Strike erhalten!"),
	ALERT_DISCONNECT_TOO_OFTEN("alerts.BOTS_ALERT.disconnectTooOften", "%player% hat das Spiel zu oft verlassen, weswegen seine Session entfernt wurde!"),
	ALERT_DEATH_ELIMINATED_OTHER("alerts.BOTS_ALERT.death.eliminated.other", "%player% ist gestorben. Grund: %reason%"),
	ALERT_DEATH_ELIMINATED_PLAYER("alerts.BOTS_ALERT.death.eliminated.player", "%player% wurde von %killer% getötet!"),
    ALERT_DEATH_LIFE_OTHER("alerts.BOTS_ALERT.death.teamLifeLost.other", "%player% ist gestorben und hat nun noch %teamLifes% Teamleben! Grund: %reason%"),
    ALERT_DEATH_LIFE_PLAYER("alerts.BOTS_ALERT.death.teamLifeLost.player", "%player% wurde von %killer% getötet und hat nun noch %teamLifes% Teamleben! Grund: %reason%"),
    ALERT_FIRST_STRIKE("alerts.BOTS_ALERT.firstStrike", "%player% hat nun einen Strike. Der Strike wurde von %striker% gegeben. Begründung: %reason%\nAufgrund dessen sind hier die derzeiten Koordinaten: %pos%!"),
	ALERT_FIRST_STRIKE_NEVER_ONLINE("alerts.BOTS_ALERT.firstStrikeNeverOnline", "%player% hat nun einen Strike. Der Strike wurde von %striker% gegeben. Begründung: %reason%\nDer Spieler war noch nicht online und wird an den Spawn-Koordinaten spawnen: %pos%!"),
	ALERT_GAME_STARTED("alerts.BOTS_ALERT.gameStarted", "%projectname% wurde gestartet!"),
	ALERT_GENERAL_STRIKE("alerts.BOTS_ALERT.generalStrike", "%player% hat nun den %strikeNumber%ten Strike! Der Strike wurde von %striker% gegeben. Begründung: %reason%"),
	ALERT_JOIN_FINALE("alerts.BOTS_ALERT.finale", "%player% hat den Server zum Finale betreten."),
	ALERT_KICKED_PLAYER("alerts.BOTS_ALERT.kickedPlayer", "%player% wurde gekickt!"),
	ALERT_SESSIONS_ENDED("alerts.BOTS_ALERT.sessionsEnded", "%player%'s Session wurde beendet, da der Spieltag vorrüber ist!"),
	ALERT_NEW_SESSIONS("alerts.BOTS_ALERT.newSessions", "Es wurden %newSessions% neue Folgen an die Spieler gegeben!"),
	ALERT_NEW_SESSIONS_FOR_ALL("alerts.BOTS_ALERT.newSessionsForAll", "Alle haben %newSessions% neue Folgen bekommen!"),
	ALERT_NO_BLOODLUST("alerts.BOTS_ALERT.noBloodlust", "%player% hat nun %days% Tage nicht gekämpft, was das Limit überschritten hat!"),
	ALERT_NO_BLOODLUST_STRIKE("alerts.BOTS_ALERT.noBloodlustStrike", "%player% hat nun %days% Tage nicht gekämpft, weswegen %player% jetzt gestriket wurde!"),
	ALERT_NOT_JOIN("alerts.BOTS_ALERT.notJoin", "%player% war nun %days% Tage nicht online, was das Limit überschritten hat!"),
	ALERT_NOT_JOIN_STRIKE("alerts.BOTS_ALERT.notJoinStrike", "%player% war nun %days% Tage nicht online, weswegen %player% jetzt gestriket wurde!"),
	ALERT_PLAYER_DC_TO_EARLY("alerts.BOTS_ALERT.playerQuitToEarly", "%player% hat das Spiel vorzeitig verlassen! %player% hat noch %seconds% Sekunden Spielzeit über!"),
	ALERT_PLAYER_JOIN_MASSREC("alerts.BOTS_ALERT.playerJoinMassrec", "%player% hat den Server in der Massenaufnahme betreten und spielt nun die %episodesPlayedPlus1%te Folge"),
	ALERT_PLAYER_JOIN_NORMAL("alerts.BOTS_ALERT.playerJoinNormal", "%player% hat das Spiel betreten!"),
	ALERT_PLAYER_JOINED("alerts.BOTS_ALERT.playerJoined", "%player% hat den Server betreten und spielt nun die %episodesPlayedPlus1%te Folge!"),
	ALERT_PLAYER_QUIT("alerts.BOTS_ALERT.playerQuit", "%player% hat das Spiel verlassen!"),
	ALERT_PLAYER_RECONNECT("alerts.BOTS_ALERT.playerReconnect", "%player% hatte das Spiel vorzeitig verlassen und ist rejoint! %player% hat noch %seconds% Sekunden verbleibend!"),
	ALERT_SECOND_STRIKE("alerts.BOTS_ALERT.secondStrike", "%player% hat nun zwei Strikes. Der Strike wurde von %striker% gegeben. Begründung: %reason%\nAufgrund dessen wurde das Inventar geleert!"),
	ALERT_SWITCHED_NAME("alerts.BOTS_ALERT.switchedName", "%player% hat den Namen gewechselt und ist nun unter %newName% bekannt!"),
	ALERT_TELEPORTED_TO_MIDDLE("alerts.BOTS_ALERT.teleportedToMiddle", "%player% wurde zur Mitte teleportiert!"),
	ALERT_THRID_STRIKE("alerts.BOTS_ALERT.thirdStrike", "%player% hat nun drei Strikes. Der Strike wurde von %striker% gegeben. Begründung: %reason%\nDamit ist %player% aus %projectname% ausgeschieden!"),
	ALERT_WINNER("alerts.BOTS_ALERT.win.player", "%player% hat %projectname% gewonnen! Gratulation!"),
	ALERT_WINNER_TEAM("alerts.BOTS_ALERT.win.team", "%winnerPlayers% haben %projectname% gewonnen! Gratulation!"),

	BOTS_DISCORD_NOT_REGISTERED_DISCORD("bots.notRegisteredDiscord", "&cDu bist noch nicht mit dem Discord authentifiziert!\n&7Um dich zu authentifizieren, schreibe in den #verify -Channel &c'varo verify <Deine ID>' &7auf dem Discord!\nLink zum Discordserver: &c%discordLink%\n&7Deine ID lautet: &c%code%"),
	BOTS_DISCORD_NO_SERVER_USER("bots.noServerUser", "&cDein Account ist nicht auf dem Discord!%nextLine%&7Joine dem Discord und versuche es erneut."),

	BORDER_MINIMUM_REACHED("border.minimumReached", "&cDie Border hat ihr Minimum erreicht!"),
	BORDER_DECREASE_DAYS("border.decreaseDays", "&7Die Border wird jetzt um &c%size% &7Blöcke mit &c%speed% &7Blöcken/s verkleinert. &7Nächste Verkleinerung in &c%days% &7Tagen!"),
	BORDER_DECREASE_DEATH("border.decreaseDeath", "&7Die Border wird jetzt um &c%size% &7Blöcke mit &c%speed% &7Blöcken/s aufgrund eines Todes verkleinert."),
	BORDER_MINUTE_TIME_UPDATE("border.minuteTimeUpdate", "&7Die Border wird in &c%minutes%&7:&c%seconds% &7verkleinert!"),
	BORDER_DECREASE_MINUTES("border.decreaseMinutes", "&7Die Border wird jetzt um &c%size% &7Blöcke mit &c%speed% &7Blöcken/s verkleinert. &7Nächste Verkleinerung in &c%days% &7Minuten!"),
	BORDER_DISTANCE("border.distanceToBorder", "&7Distanz zur Border: %colorcode%%size% &7Blöcke"),
	BORDER_COMMAND_SET_BORDER("border.borderSet", "&7Die Border wurde auf %colorcode%%size% &7gesetzt!"),

	CHAT_PLAYER_WITH_TEAM("chat.format.withTeam", "%colorcode%%team% &8| &7%player% &8» &f%message%"),
	CHAT_PLAYER_WITH_TEAM_RANK("chat.format.withTeamAndRank", "&7%rank% &8| %colorcode%%team% &8| &7%player% &8» &f%message%"),
	CHAT_PLAYER_WITHOUT_TEAM("chat.format.withoutTeam", "&7%player% &8» &f%message%"),
	CHAT_PLAYER_WITHOUT_TEAM_RANK("chat.format.withoutTeamWithRank", "&7%rank% &8| &7%player% &8» &f%message%"),

	CHAT_TEAMCHAT_FORMAT("chat.teamchatFormat", "&7[%team%&7] %from% &8» &f%message%"),
	CHAT_MUTED("chat.muted", "&7Du wurdest gemutet!"),
	CHAT_WHEN_START("chat.chatOnStart", "&7Du kannst erst ab dem Start wieder schreiben!"),
	CHAT_SPECTATOR("chat.spectatorChat", "&7Der Chat ist für Spectator deaktiviert!"),

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

	GAME_START_COUNTDOWN("game.start.startCountdown", "%projectname% &7startet in %colorcode%%countdown% &7Sekunde(n)!"),
	GAME_VARO_START("game.start.varoStart", "%projectname% &7wurde gestartet! &5Viel Erfolg!"),
	GAME_VARO_START_TITLE("game.start.startTitle", "%colorcode%%countdown%"),
	GAME_VARO_START_SUBTITLE("game.start.startSubtitle", "&7Viel Glück!"),
	GAME_WIN("game.win.single", "&5%player% &7hat %projectname% &7gewonnen! &5Gratulation!"),
	GAME_WIN_TEAM("game.win.team", "&5%winnerPlayers% &7haben %projectname% &7gewonnen! &5Gratulation!"),

	JOIN_MESSAGE("joinmessage.join", "%prefix%&a%player% &7hat den Server betreten!"),
	JOIN_PLAYERS_REQUIRED("joinmessage.requiredplayers", "%prefix%Es werden noch %required% Spieler zum Start benötigt!"),
	JOIN_FINALE("joinmessage.finale", "%prefix%&a%player% &7hat den Server zum Finale betreten."),
	JOIN_FINALE_PLAYER("joinmessage.finaleplayer", "%prefix%Das Finale beginnt bald. Bis zum Finalestart wurden alle gefreezed."),
	JOIN_MASS_RECORDING("joinmessage.massrecording", "%prefix%&a%player% &7hat den Server in der Massenaufnahme betreten und ist in %colorcode%%protectionTime% &7Sekunden angreifbar!"),
	JOIN_NO_MOVE_IN_PROTECTION("joinmessage.notMoveinProtection", "&7Du kannst dich nicht bewegen, solange du noch in der %colorcode%Schutzzeit &7bist!"),
	JOIN_PROTECTION_OVER("joinmessage.joinProtectionOver", "%prefix%&a%player% &7ist nun angreifbar!"),
	JOIN_PROTECTION_TIME("joinmessage.joinProtectionTime", "%prefix%&a%player% &7hat den Server betreten und ist in %colorcode%%protectionTime% &7Sekunden angreifbar!"),
	JOIN_SPECTATOR("joinmessage.spectator", "&a%player% &7hat den Server als Spectator betreten!"),
	JOIN_WITH_REMAINING_TIME("joinmessage.joinWithRemainingTime", "%prefix%&a%player% &7hatte den Server zu früh verlassen und hat jetzt noch %colorcode%%seconds% &7Sekunden übrig! Verbleibende &cDisconnects&7: &c%remainingDisconnects%"),

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

	NOPERMISSION_NO_PERMISSION("nopermission.noPermission", "%colorcode%Dazu bist du nicht berechtigt!"),
	NOPERMISSION_NOT_ALLOWED_CRAFT("nopermission.notAllowedCraft", "&7Das darfst du nicht craften, benutzen oder brauen!"),
	NOPERMISSION_NO_LOWER_FLIGHT("nopermission.noLowerFlight", "&7Niedriger darfst du nicht fliegen!"),

	PLACEHOLDER_NO_TOP_PLAYER("placeholder.noTopPlayer", "-"),
	PLACEHOLDER_NO_TOP_TEAM("placeholder.noTopTeam", "-"),

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

	TEAM_NAME_INVALID("team.name.invalid", "%prefix%&cUngültiger Name!"),
    TEAM_NAME_DUPLICATE("team.name.duplicate", "%prefix%&cDieser Name ist bereits vergeben!"),
    TEAM_NAME_TOO_LONG("team.name.tooLong", "%prefix%&cDer Name darf nicht mehr als %colorcode%%maxLength% &cZeichen enthalten!"),
    TEAM_RENAME("team.rename", "%prefix%%colorcode%&lBitte gib einen neuen Teamnamen ein"),
    TEAM_RENAMED("team.renamed", "%prefix%&7Das Team %colorcode%%teamId% &7hat seinen namen zu %colorcode%%newName% &7geändert"),
	
	TEAMREQUEST_ENTER_TEAMNAME("teamrequest.enterTeamName", "%prefix%%colorcode%&lGib jetzt den Teamnamen für dich und %invited% ein:"),
	TEAMREQUEST_PLAYER_NOT_ONLINE("teamrequest.playerNotOnline", "%colorcode%%invitor% ist nicht mehr online!"),
	TEAMREQUEST_REVOKED("teamrequest.invationRevoked", "Einladung erfolgreich zurückgezogen!"),
	TEAMREQUEST_TEAM_FULL("teamrequest.teamIsFull", "%invited% konnte dem Team nicht beitreten - es ist bereits voll."),
	TEAMREQUEST_TEAM_REQUEST_RECIEVED("teamrequest.teamRequestRecieved", "%colorcode%%invitor% &7hat dich in ein Team eingeladen (/varo tr)!"),
	TEAMREQUEST_INVITED_TEAM("teamrequest.invitedInTeam", "&7Du hast %colorcode%%invited% &7in das Team %colorcode%%team% &7eingeladen!"),
	TEAMREQUEST_NO_TEAMNAME("teamrequest.noteamname", "&7Du hast noch &7keinen &7Teamnamen!"),
	TEAMREQUEST_LOBBYITEM_INVITE_NAME("teamRequest.items.invite.name", "&6Spieler Einladen"),
	TEAMREQUEST_LOBBYITEM_INVITE_LORE("teamRequest.items.invite.lore", "&7Schlage einen Spieler um ihn\n&7in dein Team einzuladen"),
	TEAMREQUEST_LOBBYITEM_LEAVE_NAME("teamRequest.items.leave.name", "&cTeam Verlassen"),
	TEAMREQUEST_LOBBYITEM_LEAVE_LORE("teamRequest.items.leave.lore", ""),
	TEAMREQUEST_LOBBYITEM_RENAME_NAME("teamRequest.items.rename.name", "&cTeam Umbenennen"),
	TEAMREQUEST_LOBBYITEM_RENAME_LORE("teamRequest.items.rename.lore", ""),

	VARO_COMMANDS_HELP_HEADER("varoCommands.help.header", "&7-------- %colorcode%%category% &7-------"),
	VARO_COMMANDS_HELP_FOOTER("varoCommands.help.footer", "&7------------------------"),

	VARO_COMMANDS_ERROR_USER_NOT_FOUND("varoCommands.error.usernotfound", "&7Es konnte kein User für diesen Spieler gefunden werden!"),

	VARO_COMMANDS_ERROR_UNKNOWN_PLAYER("varoCommands.error.unknownplayer", "&7Der Spieler %colorcode%%player% &7hat den Server noch nie betreten!"),
	VARO_COMMANDS_ERROR_NO_CONSOLE("varoCommands.error.noconsole", "Du musst ein Spieler sein!"),
	VARO_COMMANDS_ERROR_NOT_STARTED("varoCommands.error.notstarted", "Das Spiel wurde noch nicht gestartet!"),
	VARO_COMMANDS_ERROR_USAGE("varoCommands.error.usage", "&cFehler! &7Nutze %colorcode%/varo %command% &7für Hilfe."),
	VARO_COMMANDS_ERROR_NO_NUMBER("varoCommands.error.nonumber", "%colorcode%%text% &7ist keine Zahl!"),
	VARO_COMMANDS_ERROR_WRONGVERSION("varoCommands.error.wrongVersion", "&7Dieses Feature ist vor der Version %colorcode%%version% &7nicht verfügbar!"),
	VARO_COMMANDS_ERROR("varoCommands.error.error", "&7Es ist ein Fehler aufgetreten!"),

	VARO_COMMANDS_BUGREPORT_OUTDATED_VERSION("varoCommands.bugreport.outdatedversion", "Du kannst keine Bugreports von einer alten Plugin-Version machen!"),
	VARO_COMMANDS_BUGREPORT_CURRENT_VERSION("varoCommands.bugreport.currentversion", "Derzeitige Version: &c%version%"),
	VARO_COMMANDS_BUGREPORT_NEWEST_VERSION("varoCommands.bugreport.newestversion", "Neueste Version: &a%version%"),
	VARO_COMMANDS_BUGREPORT_UPDATE("varoCommands.bugreport.update", "&7Nutze %colorcode%/varo update &7zum updaten."),
	VARO_COMMANDS_BUGREPORT_COLLECTING_DATA("varoCommands.bugreport.collectingdata", "Daten werden gesammelt..."),
	VARO_COMMANDS_BUGREPORT_CREATED("varoCommands.bugreport.created", "Bugreport wurde unter &c%filename% &7gespeichert! Bitte lade die Datei auf unserem Discord hoch: " + Main.DISCORD_INVITE),

	VARO_COMMANDS_ABORT_COUNTDOWN_NOT_ACTIVE("varoCommands.abort.notactive", "Der Startcountdown ist nicht aktiv!"),
	VARO_COMMANDS_ABORT_COUNTDOWN_STOPPED("varoCommands.abort.stopped", "Startcountdown erfolgreich gestoppt!"),

	VARO_COMMANDS_ACTIONBAR_DEACTIVATED("varoCommands.actionbar.deactivated", "&7Die Actionbar ist deaktiviert!"),
	VARO_COMMANDS_ACTIONBAR_ENABLED("varoCommands.actionbar.enabled", "Du siehst nun die Actionbar!"),
	VARO_COMMANDS_ACTIONBAR_DISABLED("varoCommands.actionbar.disabled", "Du siehst nun nicht mehr die Actionbar!"),

	VARO_COMMANDS_AUTOSETUP_NOT_SETUP_YET("varoCommands.autosetup.notsetupyet", "Das AutoSetup wurde noch nicht in der Config eingerichtet!"),
	VARO_COMMANDS_AUTOSETUP_FINISHED("varoCommands.autosetup.finished", "Der AutoSetup ist fertig."),
	VARO_COMMANDS_AUTOSETUP_HELP("varoCommands.autosetup.help", "%colorcode%/varo autosetup run &7startet den Autosetup"),
	VARO_COMMANDS_AUTOSETUP_ATTENTION("varoCommands.autosetup.attention", "&cVorsicht: &7Dieser Befehl setzt neue Spawns, Lobby, Portal, Border und &loptional&7 einen Autostart."),

	VARO_COMMANDS_AUTOSTART_ALREADY_STARTED("varoCommands.autostart.alreadystarted", "%projectname% &7wurde bereits gestartet!"),
	VARO_COMMANDS_AUTOSTART_ALREADY_SETUP("varoCommands.autostart.alreadysetup", "&7Entferne erst den AutoStart, bevor du einen neuen setzt!"),
	VARO_COMMANDS_AUTOSTART_HELP_SET("varoCommands.autostart.helpset", "%colorcode%/autostart &7set <Hour> <Minute> <Day> <Month> <Year>"),
	VARO_COMMANDS_AUTOSTART_NO_NUMBER("varoCommands.autostart.nonumber", "Eines der Argumente ist &ckeine &7Zahl!"),
	VARO_COMMANDS_AUTOSTART_DATE_IN_THE_PAST("varoCommands.autostart.dateinthepast", "&7Das %colorcode%Datum &7darf nicht in der Vergangenheit sein!"),
	VARO_COMMANDS_AUTOSTART_NOT_SETUP_YET("varoCommands.autostart.notsetupyet", "&7Es wurde noch kein %colorcode%Autostart &7festegelegt!"),
	VARO_COMMANDS_AUTOSTART_REMOVED("varoCommands.autostart.removed", "%colorcode%AutoStart &7erfolgreich entfernt!"),
	VARO_COMMANDS_AUTOSTART_DELAY_HELP("varoCommands.autostart.delayhelp", "%colorcode%/autostart delay &7<Delay in Minutes>"),
	VARO_COMMANDS_AUTOSTART_DELAY_TO_SMALL("varoCommands.autostart.delaytosmall", "Der Delay darf nicht kleiner als 1 sein!"),
	VARO_COMMANDS_AUTOSTART_START_DELAYED("varoCommands.autostart.startdelayed", "&7Der Start wurde um %colorcode%%delay% &7Minuten verzögert!"),
	VARO_COMMANDS_AUTOSTART_INFO_NOT_ACTIVE("varoCommands.autostart.notactive", "AutoStart nicht aktiv"),
	VARO_COMMANDS_AUTOSTART_INFO_ACTIVE("varoCommands.autostart.active", "AutoStart &aaktiv&7:"),
	VARO_COMMANDS_AUTOSTART_INFO_DATE("varoCommands.autostart.info.date", "%colorcode%Datum: &7%date%"),
	VARO_COMMANDS_AUTOSTART_INFO_AUTOSORT("varoCommands.autostart.info.autosort", "%colorcode%AutoSort: &7%active%"),
	VARO_COMMANDS_AUTOSTART_INFO_RANDOM_TEAM_SIZE("varoCommands.autostart.info.randomteamsize", "%colorcode%AutoRandomteamgrösse: &7%teamsize%"),
	VARO_COMMANDS_BACKPACK_PLAYER_DOESNT_EXIST("varoCommands.backpack.playerdoesntexist", "Der Spieler %colorcode%%player% &7existiert nicht."),
	VARO_COMMANDS_BACKPACK_TEAM_DOESNT_EXIST("varoCommands.backpack.teamdoesntexist", "Das Team %colorcode%%team% &7existiert nicht."),
	VARO_COMMANDS_BACKPACK_CANT_SHOW_BACKPACK("varoCommands.backpack.cantshowbackpack", "Der Rucksack kann dir daher nicht angezeigt werden."),
	VARO_COMMANDS_BACKPACK_NO_TEAM("varoCommands.backpack.noteam", "Du befindest dich in keinem Team und hast deshalb keinen Teamrucksack."),
	VARO_COMMANDS_BACKPACK_CHOOSE_TYPE("varoCommands.backpack.choosetype", "Bitte wähle aus, welchen Rucksack du öffnen willst %colorcode%(Player/Team)&7."),
	VARO_COMMANDS_BACKPACK_NOT_ENABLED("varoCommands.backpack.notenabled", "Die Rucksacke sind nicht aktiviert."),
	VARO_COMMANDS_CONFIG_RELOADED("varoCommands.config.reloaded", "&7Alle %colorcode%Listen&7, %colorcode%Nachrichten &7und die %colorcode%Config &7wurden erfolgreich neu geladen."),
	VARO_COMMANDS_CONFIG_HELP_SET("varoCommands.config.helpset", "%colorcode%/varo config &7set <key> <value>"),
	VARO_COMMANDS_CONFIG_NO_INGAME_SET("varoCommands.config.noimgameset", "%colorcode%Du kannst diese Einstellung nur in der Config Datei ändern!"),
	VARO_COMMANDS_CONFIG_ERROR_SET("varoCommands.config.errorset", "%colorcode%Es ist ein fehler aufgetreten: %error%"),
	VARO_COMMANDS_CONFIG_HELP_SEARCH("varoCommands.config.helpsearch", "%colorcode%/varo config &7search <key>"),
	VARO_COMMANDS_CONFIG_ENTRY_SET("varoCommands.config.entryset", "&7Der Eintrag '%colorcode%%entry%&7' wurde erfolgreich auf '%colorcode%%value%&7' gesetzt."),
	VARO_COMMANDS_CONFIG_ENTRY_NOT_FOUND("varoCommands.config.entrynotfound", "&7Der Eintrag '%colorcode%%entry%&7' wurde nicht gefunden."),
	VARO_COMMANDS_CONFIG_RESET("varoCommands.config.reset", "&7Alle Einträge wurden erfolgreich zurückgesetzt."),
	VARO_COMMANDS_CONFIG_SEARCH_LIST_TITLE("varoCommands.config.searchlisttitle", "&lFolgende Einstellungen wurden gefunden:"),
	VARO_COMMANDS_CONFIG_SEARCH_LIST_FORMAT("varoCommands.config.searchlistformat", "%colorcode%%entry% &8- &7%description%"),
	VARO_COMMANDS_EXPORT_SUCCESSFULL("varoCommands.export.players", "&7Alle Spieler wurden erfolgreich in '%colorcode%%file%&7' exportiert."),
	VARO_COMMANDS_DISCORD_PLEASE_RELOAD("varoCommands.discord.pleasereload", "&7Der DiscordBot wurde beim Start nicht aufgesetzt, bitte reloade!"),
	VARO_COMMANDS_DISCORD_VERIFY_DISABLED("varoCommands.discord.verifydisabled", "&7Das Verifzierungs-System wurde in der Config deaktiviert!"),
	VARO_COMMANDS_DISCORD_BOT_DISABLED("varoCommands.discord.botdisabled", "&7Der DiscordBot wurde nicht aktiviert!"),
	VARO_COMMANDS_DISCORD_USER_NOT_FOUND("varoCommands.discord.usernotfound", "&7User für diesen Spieler nicht gefunden!"),
	VARO_COMMANDS_INTRO_ALREADY_STARTED("varoCommands.intro.alreadystarted", "&7Das Intro wurde bereits gestartet!"),
	VARO_COMMANDS_INTRO_GAME_ALREADY_STARTED("varoCommands.intro.gamealreadystarted", "&7Das Spiel wurde bereits gestartet!"),
	VARO_COMMANDS_INTRO_STARTED("varoCommands.intro.started", "&7Und los geht's!"),
	VARO_COMMANDS_PRESET_NOT_FOUND("varoCommands.preset.notfound", "Das Preset %colorcode%%preset% &7wurde nicht gefunden."),
	VARO_COMMANDS_PRESET_PATH_TRAVERSAL("varoCommands.preset.pathtraversal", "Presets dürfen sich nur im &3presets&7-Order befinden."),
	VARO_COMMANDS_PRESET_LOADED("varoCommands.preset.loaded", "Das Preset %colorcode%%preset% &7wurde &aerfolgreich &7geladen."),
	VARO_COMMANDS_PRESET_SAVED("varoCommands.preset.saved", "Die aktuellen Einstellungen wurden &aerfolgreich &7als Preset %colorcode%%preset% &7gespeichert."),
	VARO_COMMANDS_PRESET_LIST("varoCommands.preset.list", "&lListe aller Presets:"),
	VARO_COMMANDS_PRESET_HELP_LOAD("varoCommands.preset.helploaded", "%colorcode%/varo preset &7load <PresetPath>"),
	VARO_COMMANDS_PRESET_HELP_SAVE("varoCommands.preset.helpsave", "%colorcode%/varo preset &7save <PresetPath>"),
	VARO_COMMANDS_RANDOMTEAM_HELP("varoCommands.randomteam.help", "%colorcode%/varo randomTeam <Teamgrösse>"),
	VARO_COMMANDS_RANDOMTEAM_TEAMSIZE_TOO_SMALL("varoCommands.randomteam.teamsizetoosmall", "&7Die Teamgrösse muss mindestens 1 betragen."),
	VARO_COMMANDS_RANDOMTEAM_SORTED("varoCommands.randomteam.sorted", "&7Alle Spieler ohne Team sind nun in %colorcode%%teamsize%er &7Teams!"),
	VARO_COMMANDS_RANDOMTEAM_NO_PARTNER("varoCommands.randomteam.nopartner", "&7Für dich konnten nicht genug Teampartner gefunden werden."),

	VARO_COMMANDS_CHECKCOMBAT_HELP("varoCommands.checkcombat.help", "%colorcode%/varo checkcombat"),
	VARO_COMMANDS_CHECKCOMBAT_INCOMBAT("varoCommands.checkcombat.incombat", "&7Du bist momentan im %colorcode%Combat&7!"),
	VARO_COMMANDS_CHECKCOMBAT_NOTINCOMBAT("varoCommands.checkcombat.notincombat", "&7Du bist momentan nicht im %colorcode%Combat&7!"),
	VARO_COMMANDS_EPISODES("varoCommands.episodes", "Du hast bereits %colorcode%%episodesPlayedPlus1% &7Folge(n) gespielt und kannst heute noch %colorcode%%sessions% &7weitere Folge(n) spielen"),
	VARO_COMMANDS_RESTART_IN_LOBBY("varoCommands.restart.inlobby", "&7Das Spiel befindet sich bereits in der Lobby-Phase!"),
	VARO_COMMANDS_RESTART_RESTARTED("varoCommands.restart.restarted", "&7Das Spiel wurde neugestartet."),
	VARO_COMMANDS_SCOREBOARD_DEACTIVATED("varoCommands.scoreboard.deactivated", "&7Das Scoreboard ist deaktiviert."),
	VARO_COMMANDS_SCOREBOARD_ENABLED("varoCommands.scoreboard.enabled", "&7Du siehst nun das Scoreboard."),
	VARO_COMMANDS_SCOREBOARD_DISABLED("varoCommands.scoreboard.disabled", "&7Du siehst nun das Scoreboard."),
	VARO_COMMANDS_SORT_HELP("varoCommands.sort.help", "%colorcode%/varo sort"),
	VARO_COMMANDS_SORT_SORTED_WELL("varoCommands.sort.sorted", "&7Alle Spieler wurden erfolgreich sortiert."),
	VARO_COMMANDS_SORT_NO_SPAWN_WITH_TEAM("varoCommands.sort.nospawnwithteam", "&7Es konnte nicht für jeden Spieler ein Loch bei den Teampartnern gefunden werden!"),
	VARO_COMMANDS_SORT_NO_SPAWN("varoCommands.sort.nospawn", "&7Es konnte nicht für jeden Spieler ein Loch gefunden werden!"),

	VARO_COMMANDS_DISCORD_NOT_SETUP("varoCommands.discord.notsetup", "&7Der DiscordBot wurde beim Start nicht aufgesetzt!"),
	VARO_COMMANDS_DISCORD_STATUS("varoCommands.discord.status", "&7Deine Discord Verifizierung ist %status%&7."),
	VARO_COMMANDS_DISCORD_ACTIVE("varoCommands.discord.status.active", "&aaktiv"),
	VARO_COMMANDS_DISCORD_INACTIVE("varoCommands.discord.status.inactive", "&cinaktiv"),
	VARO_COMMANDS_DISCORD_NOT_VERIFIED("varoCommands.discord.notverified", "&7Du bist noch nicht verifiziert!"),
	VARO_COMMANDS_DISCORD_VERIFICATION_REMOVED("varoCommands.discord.verificationremoved", "&7Deine Verifizierung wurde entfernt."),
	VARO_COMMANDS_DISCORD_VERIFY_SYSTEM_DISABLED("varoCommands.discord.verifysystemdisabled", "&7Das Verifysystem ist deaktiviert."),
	VARO_COMMANDS_DISCORD_DISCORDBOT_DISABLED("varoCommands.discord.discordbotdisabled", "&7Der Discordbot ist deaktiviert."),
	VARO_COMMANDS_DISCORD_GETLINK("varoCommands.discord.getlink", "&7Der Discord Account von %colorcode%%player% heisst %colorcode%%user%&7 und die ID lautet %colorcode%%id%&7!"),
	VARO_COMMANDS_DISCORD_UNVERIFY("varoCommands.discord.unverify", "&7Der Discord Account wurde erfolgreich von %colorcode%%player% &7entkoppelt!"),
	VARO_COMMANDS_DISCORD_RELOADED("varoCommands.discord.reloaded", "&7Der Discordbot wurde &aerfolgreich &7neu geladen."),
	VARO_COMMANDS_DISCORD_SHUTDOWN("varoCommands.discord.shutdown", "&7Der Discordbot wurde &aerfolgreich &7heruntergefahren."),
	VARO_COMMANDS_DISCORD_BOT_OFFLINE("varoCommands.discord.botoffline", "&7Der Discordbot ist nicht online!"),
	VARO_COMMANDS_DISCORD_NO_EVENT_CHANNEL("varoCommands.discord.noeventchannel", "&7Dem Bot wurde kein Event-Channel gegeben."),
	VARO_COMMANDS_DISCORD_VERIFY_ENABLED("varoCommands.discord.verifyenabled", "&7Das Verifysystem wurde aktiviert."),
	VARO_COMMANDS_DISCORD_DISCORD_MESSAGE_TITLE("varoCommands.discord.discordmessagetitle", "MESSAGE"),
	VARO_COMMANDS_DISCORD_BYPASS_ACTIVE("varoCommands.discord.bypassactive", "&7%player% umgeht nun das Verifysystem."),
	VARO_COMMANDS_DISCORD_BYPASS_INACTIVE("varoCommands.discord.bypassinactive", "&7%player% umgeht nicht mehr das Verifysystem."),
	VARO_COMMANDS_DISCORD_VERIFY_ACCOUNT("varoCommands.discord.account", "&7Account: %colorcode%%account%"),
	VARO_COMMANDS_DISCORD_VERIFY_REMOVE_USAGE("varoCommands.discord.remove.usage", "&7Nutze %colorcode%/varo discord verify remove &7ein, um die Verifizierung zu entfernen."),

	VARO_COMMANDS_PLAYTIME("varoCommands.playtime", "&7Deine verbleibende Zeit: %formattedCountdown%&7."),

	COMMANDS_XRAY_ERROR_NOT_AVAIALABLE("varoCommands.xray.errorNotAvailable", "&cEs gab einen Fehler mit dem Anti-Xray-System. Bitte überprüfe, deine Serverversion (%version%) auf Spigot basiert."),
	COMMANDS_XRAY_INSTALLING_PLUGIN("varoCommands.xray.installingPlugin", "Das Anti-Xray-Plugin wird installiert und der Server danach heruntergefahren."),
	COMMANDS_XRAY_INSTALLING_ERROR("varoCommands.xray.installingError", "Es gab einen kritischen Fehler beim Download des Plugins. Du kannst dir das externe Plugin hier manuell herunterladen: %colorcode%&nhttps://www.spigotmc.org/resources/22818/"),
	COMMANDS_XRAY_VERSION_NOT_AVAIALABLE("varoCommands.xray.versionNotAvailable", "&cAuf deiner Serverversion ist kein X-Ray verfügbar (%version%)."),
	COMMANDS_XRAY_STATUS("varoCommands.xray.status", "Anti-Xray ist momentan: %status%&7."),
	COMMANDS_XRAY_STATUS_ACTIVATED("varoCommands.xray.statusActivated", "&aaktiviert"),
	COMMANDS_XRAY_STATUS_DEACTIVATED("varoCommands.xray.statusDeactivated", "&cdeaktiviert"),
	COMMANDS_XRAY_ACTIVATED("varoCommands.xray.activated", "Das Anti-Xray wurde aktiviert."),
	COMMANDS_XRAY_DEACTIVATED("varoCommands.xray.dectivated", "Das Anti-Xray wurde deaktiviert."),
	COMMANDS_XRAY_ALREADY_ACTIVATED("varoCommands.xray.alreadyActivated", "Das Anti-Xray ist bereits aktiviert."),
	COMMANDS_XRAY_ALREADY_DEACTIVATED("varoCommands.xray.alreadyDactivated", "Das Anti-Xray ist bereits deaktiviert."),

	COMMANDS_BORDER_SIZE("varoCommands.border.size", "Die Border ist momentan %colorcode%%size% Blöcke &7gross."),
	COMMANDS_BORDER_DISTANCE("varoCommands.border.distance", "Du bist %colorcode%%distance% Blöcke &7von der Border entfernt."),
	COMMANDS_BORDER_USAGE("varoCommands.border.usage", "Du kannst die Grösse der Border mit %colorcode%/border <Durchmesser> [Sekunden] &7setzen. Der Mittelpunkt der Border wird zu deinem derzeiten Punkt gesetzt."),

	COMMANDS_BROADCAST_FORMAT("varoCommands.broadcast.format", "&8[&cBroadcast&8] &7%message%"),

	COMMANDS_CHATCLEAR_CLEAR("varoCommands.chatclear.cleared", "Der Chat wurde %colorcode%gecleart&7."),

	COMMANDS_COUNTDOWN_ABORT("varoCommands.countdown.abort", "Der Countdown wurde abgebrochen."),
	COMMANDS_COUNTDOWN_TOO_SMALL("varoCommands.countdown.tooSmall", "Der Countdown kann nicht negativ oder 0 sein!"),
	COMMANDS_COUNTDOWN_START("varoCommands.countdown.start", "Los!"),
	COMMANDS_COUNTDOWN_FORMAT("varoCommands.countdown.format", "%colorcode%%seconds%"),

	COMMANDS_TIME_DAY("varoCommands.time.day", "Es ist jetzt %colorcode%Tag&7."),
	COMMANDS_TIME_NIGHT("varoCommands.time.night", "Es ist jetzt %colorcode%Nacht&7."),

	COMMANDS_WEATHER_SUN("varoCommands.weather.sun", "Es ist jetzt %colorcode%sonnig&7."),
	COMMANDS_WEATHER_RAIN("varoCommands.weather.rain", "Es ist jetzt %colorcode%regnerisch&7."),
	COMMANDS_WEATHER_THUNDER("varoCommands.weather.thunder", "Es %colorcode%gewittert &7nun."),

	COMMANDS_SETWORLDSPAWN("varoCommands.setworldspawn.setworldspawn", "Weltspawn erfolgreich gesetzt."),

	COMMANDS_DENIED("varoCommands.blockedcommand", "Du darst diesen Befehl nicht benutzen!"),

	SPAWNS_SPAWN_NUMBER("spawns.spawnNameTag.number", "&7Spawn %colorcode%%number%"),
	SPAWNS_SPAWN_PLAYER("spawns.spawnNameTag.player", "&7Spawn von %colorcode%%player%"),

	MODS_BLOCKED_MODS_KICK("mods.blockedModsKick", "&7Bitte entferne folgende Mods: %colorcode%%mods%"),
	MODS_BLOCKED_MODLIST_SPLIT("mods.blockedModsListSplit", "&7, "),
	MODS_BLOCKED_MODS_BROADCAST("mods.blockedModsBroadcast", "&7Der Spieler %colorcode%%player% &7hat versucht mit folgenden blockierten Mods zu joinen: %colorcode%%mods%"),

	OTHER_CONFIG("other.configReload", "&7Die %colorcode%Config &7wurde neu geladen"),
	OTHER_PING("other.ping", "&7Dein %colorcode%Ping &7beträgt: %colorcode%%ping%&7ms"),

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
