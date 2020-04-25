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
	ALERT_BORDER_DECREASED_TIME_DAYS("alerts.BOTS_ALERT.borderDecrease.days", "Die Border wurde um %size% verkleinert. Naechste Verkleinerung in %days% Tagen!"),
	ALERT_BORDER_DECREASED_TIME_MINUTE("alerts.BOTS_ALERT.borderDecrease.minutes", "Die Border wurde um %size% verringert! Naechste Verkleinerung in %minutes% Minuten!"),
	ALERT_COMBAT_LOG("alerts.BOTS_ALERT.combatlog", "%player% hat sich im Kampf ausgeloggt!"),
	ALERT_COMBAT_LOG_STRIKE("alerts.BOTS_ALERT.combatlogStrike", "%player% hat sich im Kampf ausgeloggt und hat somit einen Strike erhalten!"),
	ALERT_DISCONNECT_TOO_OFTEN("alerts.BOTS_ALERT.disconnectTooOften", "%player% hat das Spiel zu oft verlassen, weswegen seine Session entfernt wurde!"),
	ALERT_DISCORD_DEATH("alerts.BOTS_ALERT.death", "%player% ist gestorben! Grund: %reason%"),
	ALERT_DISCORD_KILL("alerts.BOTS_ALERT.kill", "%player% wurde von %killer% getoetet!"),
	ALERT_FIRST_STRIKE("alerts.BOTS_ALERT.firstStrike", "%player% hat nun einen Strike. Der Strike wurde von %striker% gegeben. Begruendung: %reason%\nAufgrund dessen sind hier die derzeiten Koordinaten: %pos%!"),
	ALERT_FIRST_STRIKE_NEVER_ONLINE("alerts.BOTS_ALERT.firstStrikeNeverOnline", "%player% hat nun einen Strike. Der Strike wurde von %striker% gegeben. Begruendung: %reason%\nDer Spieler war noch nicht online und wird an den Spawn-Koordinaten spawnen: %pos%!"),
	ALERT_GAME_STARTED("alerts.BOTS_ALERT.gameStarted", "%projectname% wurde gestartet!"),
	ALERT_GENERAL_STRIKE("alerts.BOTS_ALERT.generalStrike", "%player% hat nun den %strikeNumber%ten Strike! Der Strike wurde von %striker% gegeben. Begruendung: %reason%"),
	ALERT_JOIN_FINALE("alerts.BOTS_ALERT.finale", "%player% &7hat den Server zum Finale betreten."),
	ALERT_KICKED_PLAYER("alerts.BOTS_ALERT.kickedPlayer", "%player% wurde gekickt!"),
	ALERT_NEW_SESSIONS("alerts.BOTS_ALERT.newSessions", "Es wurden %newSessions% neue Folgen an die Spieler gegeben!"),
	ALERT_NEW_SESSIONS_FOR_ALL("alerts.BOTS_ALERT.newSessionsForAll", "Alle haben %newSessions% neue Folgen bekommen!"),
	ALERT_NO_BLOODLUST("alerts.BOTS_ALERT.noBloodlust", "%player% hat nun %days% Tage nicht gekaempft, was das Limit ueberschritten hat!"),
	ALERT_NO_BLOODLUST_STRIKE("alerts.BOTS_ALERT.noBloodlustStrike", "%player% hat nun %days% Tage nicht gekaempft, weswegen %player% jetzt gestriket wurde!"),
	ALERT_NOT_JOIN("alerts.BOTS_ALERT.notJoin", "%player% war nun %days% Tage nicht online, was das Limit ueberschritten hat!"),
	ALERT_NOT_JOIN_STRIKE("alerts.BOTS_ALERT.notJoinStrike", "%player% war nun %days% Tage nicht online, weswegen %player% jetzt gestriket wurde!"),
	ALERT_PLAYER_DC_TO_EARLY("alerts.BOTS_ALERT.playerQuitToEarly", "%player% hat das Spiel vorzeitig verlassen! %player% hat noch %seconds% Sekunden Spielzeit ueber!"),
	ALERT_PLAYER_JOIN_MASSREC("alerts.BOTS_ALERT.playerJoinMassrec", "%player% hat den Server in der Massenaufnahme betreten und spielt nun die %episodesPlayedPlus1%te Folge"),
	ALERT_PLAYER_JOIN_NORMAL("alerts.BOTS_ALERT.playerJoinNormal", "%player% hat das Spiel betreten!"),
	ALERT_PLAYER_JOINED("alerts.BOTS_ALERT.playerJoined", "%player% hat den Server betreten und spielt nun die %episodesPlayedPlus1%te Folge!"),
	ALERT_PLAYER_QUIT("alerts.BOTS_ALERT.playerQuit", "%player% hat das Spiel verlassen!"),
	ALERT_PLAYER_RECONNECT("alerts.BOTS_ALERT.playerReconnect", "%player% hatte das Spiel vorzeitig verlassen und ist rejoint! %player% hat noch %seconds% Sekunden verbleibend!"),
	ALERT_SECOND_STRIKE("alerts.BOTS_ALERT.secondStrike", "%player% hat nun zwei Strikes. Der Strike wurde von %striker% gegeben. Begruendung: %reason%\nAufgrund dessen wurde das Inventar geleert!"),
	ALERT_SWITCHED_NAME("alerts.BOTS_ALERT.switchedName", "%player% hat den Namen gewechselt und ist nun unter %newName% bekannt!"),
	ALERT_TELEPORTED_TO_MIDDLE("alerts.BOTS_ALERT.teleportedToMiddle", "%player% wurde zur Mitte teleportiert!"),
	ALERT_THRID_STRIKE("alerts.BOTS_ALERT.thirdStrike", "%player% hat nun drei Strikes. Der Strike wurde von %striker% gegeben. Begruendung: %reason%\nDamit ist %player% aus %projectname% ausgeschieden!"),
	ALERT_WINNER("alerts.BOTS_ALERT.win.player", "%player% hat %projectname% gewonnen! Gratulation!"),
	ALERT_WINNER_TEAM("alerts.BOTS_ALERT.win.team", "%winnerPlayers% haben %projectname% gewonnen! Gratulation!"),

	BOTS_DISCORD_NOT_REGISTERED_DISCORD("bots.notRegisteredDiscord", "&cDu bist noch nicht mit dem Discord authentifiziert!\n&7Um dich zu authentifizieren, schreibe in den #verify -Channel &c'varo verify <Deine ID>' &7auf dem Discord!\nLink zum Discordserver: &c%discordLink%\n&7Deine ID lautet: &c%code%"),
	BOTS_DISCORD_NO_SERVER_USER("bots.noServerUser", "&cDein Account ist nicht auf dem Discord!%nextLine%&7Joine dem Discord und versuche es erneut."),

	BORDER_MINIMUM_REACHED("border.minimumReached", "&cDie Border hat ihr Minimum erreicht!"),
	BORDER_DECREASE_DAYS("border.decreaseDays", "&7Die Border wird jetzt um &c%size% &7Bloecke mit &c%speed% &7Bloecken/s verkleinert. &7Naechste Verkleinerung in &c%days% &7Tagen!"),
	BORDER_DECREASE_DEATH("border.decreaseDeath", "&7Die Border wird jetzt um &c%size% &7Bloecke mit &c%speed% &7Bloecken/s aufgrund eines Todes verkleinert."),
	BORDER_MINUTE_TIME_UPDATE("border.minuteTimeUpdate", "&7Die Border wird in &c%minutes%&7:&c%seconds% &7verkleinert!"),
	BORDER_DECREASE_MINUTES("border.decreaseMinutes", "&7Die Border wird jetzt um &c%size% &7Bloecke mit &c%speed% &7Bloecken/s verkleinert. &7Naechste Verkleinerung in &c%days% &7Minuten!"),
	BORDER_DISTANCE("border.distanceToBorder", "&7Distanz zur Border: %colorcode%%size% &7Bloecke"),
	BORDER_COMMAND_SET_BORDER("border.borderSet", "&7Die Border wurde auf %colorcode%%size% &7gesetzt!"),

	CHAT_PLAYER_WITH_TEAM("chat.format.withTeam", "%colorcode%%team% &8| &7%player% &8» &f%message%"),
	CHAT_PLAYER_WITH_TEAM_RANK("chat.format.withTeamAndRank", "&7%rank% &8| %colorcode%%team% &8| &7%player% &8» &f%message%"),
	CHAT_PLAYER_WITHOUT_TEAM("chat.format.withoutTeam", "&7%player% &8» &f%message%"),
	CHAT_PLAYER_WITHOUT_TEAM_RANK("chat.format.withoutTeamWithRank", "&7%rank% &8| &7%player% &8» &f%message%"),

	CHAT_TEAMCHAT_FORMAT("chat.teamchatFormat", "&7[%team%&7] %player% &8» &f%message%"),
	CHAT_MUTED("chat.muted", "&7Du wurdest gemutet!"),
	CHAT_WHEN_START("chat.chatOnStart", "&7Du kannst erst ab dem Start wieder schreiben!"),

	COMBAT_FRIENDLY_FIRE("combat.friendlyfire", "&7Dieser Spieler ist in deinem Team!"),
	COMBAT_IN_FIGHT("combat.inFight", "&7Du bist nun im Kampf, logge dich &4NICHT &7aus!"),
	COMBAT_LOGGED_OUT("combat.loggedOut", "&c%player% &7hat den Server waehrend eines Kampfes verlassen!"),
	COMBAT_NOT_IN_FIGHT("combat.notInFight", "&7Du bist nun nicht mehr im &cKampf&7!"),

	SPAWN_WORLD("spawn.spawn", "%colorcode%Koordinaten&7 vom Spawn: %colorcode%%x%&7, %colorcode%%y%&7, %colorcode%%z%"),
	SPAWN_NETHER("spawn.spawnNether", "%colorcode%Koordinaten&7 vom Portal zur Oberwelt: %colorcode%%x%&7, %colorcode%%y%&7, %colorcode%%z%"),
	SPAWN_DISTANCE("spawn.spawnDistance", "&7Du bist %colorcode%%distance% &7Bloecke vom Spawn entfernt!"),
	SPAWN_DISTANCE_NETHER("spawn.spawnDistanceNether", "&7Du bist %colorcode%%distance% &7Bloecke vom Portal zur Oberwelt entfernt!"),

	DEATH_DEAD("death.killMessage", "&c%player% &7ist gestorben. &7Grund: &c%reason%"),
	DEATH_KILLED_BY("death.killed", "%colorcode%%player% &7wurde von &c%killer% &7getoetet!"),
	DEATH_LIFE_LOST("death.teamLifeLost", "%player% hat nun noch %colorcode%%teamLifes% &7Teamleben!"),
	DEATH_RESPAWN_PROTECTION("death.respawnProtection", "&c%player% hat nun ein Leben weniger und ist fuer %seconds% unverwundbar!"),
	DEATH_RESPAWN_PROTECTION_OVER("death.respawnProtectionOver", "&c%player% ist nun wieder verwundbar!"),
	DEATH_KILL_LIFE_ADD("death.killLifeAdd", "Dein Team hat aufgrund eines Kills ein Teamleben erhalten!"),

	GAME_START_COUNTDOWN("game.start.startCountdown", "%projectname% &7startet in %colorcode%%countdown% &7Sekunde(n)!"),
	GAME_VARO_START("game.start.varoStart", "%projectname% &7wurde gestartet! &5Viel Erfolg!"),
	GAME_VARO_START_TITLE("game.start.startTitle", "%colorcode%%countdown%\n&7Viel Glueck!"),
	GAME_WIN("game.win.single", "&5%player% &7hat %projectname% &7gewonnen! &5Gratulation!"),
	GAME_WIN_TEAM("game.win.team", "&5%winnerPlayers% &7haben %projectname% &7gewonnen! &5Gratulation!"),

	JOIN_MESSAGE("joinmessage.join", "%prefix%&a%player% &7hat den Server betreten!"),
	JOIN_FINALE("joinmessage.finale", "%prefix%&a%player% &7hat den Server zum Finale betreten."),
	JOIN_MASS_RECORDING("joinmessage.massrecording", "%prefix%&a%player% &7hat den Server in der Massenaufnahme betreten und ist in %colorcode%%protectionTime% &7Sekunden angreifbar!"),
	JOIN_NO_MOVE_IN_PROTECTION("joinmessage.notMoveinProtection", "&7Du kannst dich nicht bewegen, solange du noch in der %colorcode%Schutzzeit &7bist!"),
	JOIN_PROTECTION_OVER("joinmessage.joinProtectionOver", "%prefix%&a%player% &7ist nun angreifbar!"),
	JOIN_PROTECTION_TIME("joinmessage.joinProtectionTime", "%prefix%&a%player% &7hat den Server betreten und ist in %colorcode%%protectionTime% &7Sekunden angreifbar!"),
	JOIN_SPECTATOR("joinmessage.spectator", "&a%player% &7hat den Server als Spectator betreten!"),
	JOIN_WITH_REMAINING_TIME("joinmessage.joinWithRemainingTime", "%prefix%&a%player% &7hatte den Server zu frueh verlassen und hat jetzt noch %colorcode%%seconds% &7Sekunden uebrig! Verbleibende &cDisconnects&7: &c%remainingDisconnects%"),

	QUIT_MESSAGE("quitmessage.quit", "%prefix%&c%player%&7 hat den Server verlassen!"),
	QUIT_DISCONNECT_SESSION_END("quitmessage.disconnectKilled", "&c%player% &7hat das Spiel verlassen und ist seit &c%banTime% &7Minute(n) nicht mehr online.%nextLine%&7Damit ist er aus %projectname% &7ausgeschieden!"),
	QUIT_SPECTATOR("quitmessage.spectator", "&c%player% &7hat den Server als Spectator verlassen!"),
	QUIT_TOO_OFTEN("quitmessage.quitTooOften", "&c%player% &7hat den Server zu oft verlassen und dadurch seine Sitzung verloren."),
	QUIT_WITH_REMAINING_TIME("quitmessage.quitRemainingTime", "%prefix%&c%player% &7hat den Server vorzeitig verlassen!"),
	QUIT_KICK_BROADCAST("quitmessage.broadcast", "%colorcode%%player% &7wurde gekickt!"),
	QUIT_KICK_DELAY_OVER("quitmessage.protectionOver", "%colorcode%%player% &7wurde aufgrund seines Todes jetzt gekickt!"),
	QUIT_KICK_IN_SECONDS("quitmessage.kickInSeconds", "%colorcode%%player% &7wird in %colorcode%%countdown% &7Sekunde(n) gekickt!"),
	QUIT_KICK_PLAYER_NEARBY("quitmessage.noKickPlayerNearby", "&cEs befindet sich ein Spieler &4%distance% &cBloecke in deiner Naehe!%nextLine%&7Um gekickt zu werden, entferne dich von diesem Spieler!"),
	QUIT_KICK_SERVER_CLOSE_SOON("quitmessage.serverCloseSoon", "&7Der Server schliesst in &c%minutes% &7Minuten!"),

	DEATH_KICK_DEAD("kick.kickedKilled", "&cDu bist gestorben! %nextLine% &7Damit bist du aus %projectname% &7ausgeschieden"),
	DEATH_KICK_KILLED("kick.killedKick", "&7Du wurdest von &c%killer% &7getoetet! %nextLine% &7Damit bist du aus %projectname% &7ausgeschieden!"),
	JOIN_KICK_NOT_USER_OF_PROJECT("kick.notUserOfTheProject", "&7Du bist kein Teilnehmer dieses %projectname%&7's!"),
	JOIN_KICK_SERVER_FULL("kick.serverFull", "&cDer Server ist voll!%nextLine%&7Sprich mit dem Owner, falls das das ein Irrtum sein sollte!"),
	JOIN_KICK_STRIKE_BAN("kick.strikeBan", "&cDu wurdest aufgrund deines letzten Strikes fuer %hours% gebannt!\nVersuche es spaeter erneut"),
	JOIN_KICK_BANNED("kick.banned", "&4Du bist vom Server gebannt!\n&7Melde dich bei einem Admin, falls dies ein Fehler sein sollte.\n&7Grund: &c%reason%"),
	JOIN_KICK_NO_PREPRODUCES_LEFT("kick.noPreproduceLeft", "&cDu hast bereits vorproduziert! %nextLine%&7Versuche es morgen erneut."),
	JOIN_KICK_NO_SESSIONS_LEFT("kick.noSessionLeft", "&cDu hast keine Sessions mehr uebrig! %nextLine%&7Warte bis morgen, damit du wieder spielen kannst!"),
	JOIN_KICK_NO_TIME_LEFT("kick.noTimeLeft", "&cDu darfst nur alle &4%timeHours% &cStunden regulaer spielen! %nextLine%&7Du kannst erst in &c%stunden%&7:&c%minuten%&7:&c%sekunden% &7wieder joinen!"),
	JOIN_KICK_NOT_STARTED("kick.notStarted", "&cDer Server wurde noch nicht eroeffnet! %nextLine%&7Gedulde dich noch ein wenig!"),
	KICK_SESSION_OVER("kick.kickMessage", "&cDeine Aufnahmezeit ist abgelaufen, %nextLine%&7deswegen wurdest du gekickt!"),
	KICK_MASS_REC_SESSION_OVER("kick.kickMessageMassRec", "&cDie Massenaufnahme ist beendet, %nextLine%&7deswegen wurdest du gekickt!"),
	KICK_TOO_MANY_STRIKES("kick.tooManyStrikes", "&7Du hast zu viele Strikes bekommen und wurdest daher aus dem Projekt %projectname% &7entfernt."),
	KICK_COMMAND("kick.kick", "%colorcode%%player% &7wurde gekick.kickt!"),

	LABYMOD_DISABLED("labymod.labyModDisabled", "&7Alle deine LabyMod Funktionen wurden deaktiviert!"),
	LABYMOD_KICK("labymod.labyMod", "&cLabyMod isn't allowed on this server."),

	SERVER_MODT_CANT_JOIN_HOURS("motd.cantJoinHours", "&cDu kannst nur zwischen &4%minHour% &cund &4%maxHour%&c Uhr joinen! %nextLine%&7Versuche es spaeter erneut! &7%currHour%&7:&7%currMin%&7:&7%currSec%"),
	SERVER_MODT_NOT_OPENED("motd.serverNotOpened", "&cDer Server wurde noch nicht fuer alle geoeffnet! %nextLine%&7Versuche es spaeter erneut!"),
	SERVER_MODT_OPEN("motd.serverOpen", "&aSei nun bei %projectname% &adabei! \n&7Viel Spass!"),

	NAMETAG_NORMAL("nametag.normalNametagPrefix", "&7"),
	NAMETAG_SUFFIX("nametag.normalSuffix", "&c %kills%"),
	NAMETAG_TEAM_PREFIX("nametag.nametagWithTeam", "%colorcode%%team% &7"),

	CHEST_NOT_TEAM_CHEST("chest.notTeamChest", "&7Diese Kiste gehoert %colorcode%%player%&7!"),
	CHEST_NOT_TEAM_FURNACE("chest.notTeamFurnace", "&7Dieser Ofen gehoert %colorcode%%player%&7!"),
	CHEST_REMOVED_SAVEABLE("chest.removedChest", "&7Du hast diese/n %saveable% %colorcode%erfolgreich &7entfernt!"),
	CHEST_SAVED_CHEST("chest.newChestSaved", "&7Eine neue Kiste wurde gesichert!"),
	CHEST_SAVED_FURNACE("chest.newFurnaceSaved", "&7Ein neuer Ofen wurde gesichert!"),

	NOPERMISSION_NO_PERMISSION("nopermission.noPermission", "%colorcode%Dazu bist du nicht berechtigt!"),
	NOPERMISSION_NOT_ALLOWED_CRAFT("nopermission.notAllowedCraft", "&7Das darfst du nicht craften, benutzen oder brauen!"),
	NOPERMISSION_NO_LOWER_FLIGHT("nopermission.noLowerFlight", "&7Niedriger darfst du nicht fliegen!"),

	PROTECTION_NO_MOVE_START("protection.noMoveStart", "&7Du kannst dich nicht bewegen, solange das Projekt noch nicht gestartet wurde."),
	PROTECTION_START("protection.start", "&7Die &cSchutzzeit &7protection.startet jetzt und wird &c%seconds% &7Sekunden anhalten!"),
	PROTECTION_TIME_OVER("protection.protectionOver", "&7Die &cSchutzzeit &7ist nun vorrueber!"),
	PROTECTION_TIME_UPDATE("protection.protectionUpdate", "&7Die &cSchutzzeit &7ist in &c%minutes%&7:&c%seconds% &7vorrueber!"),
	PROTECTION_TIME_RUNNING("protection.timeRunning", "&7Die %colorcode%Schutzzeit &7laeuft noch!"),

	SORT_NO_HOLE_FOUND("sort.noHoleFound", "Es konnte fuer dich kein Loch gefunden werden!"),
	SORT_NO_HOLE_FOUND_TEAM("sort.noHoleFoundTeam", "Es konnte fuer dich kein Loch bei deinen Teampartnern gefunden werden."),
	SORT_NUMBER_HOLE("sort.numberHoleTeleport", "Du wurdest in das Loch %colorcode%%number% &7teleportiert!"),
	SORT_OWN_HOLE("sort.ownHoleTeleport", "Du wurdest in dein Loch einsortiert!"),
	SORT_SPECTATOR_TELEPORT("sort.spectatorTeleport", "Du wurdest, da du Spectator bist, zum Spawn teleportiert!"),
	SORT_SORTED("sort.sorted", "&7Du wurdest in das Loch %colorcode%%zahl% &7teleportiert!"),

	TABLIST_PLAYER_WITH_TEAM("tablist.player.withTeam", "%colorcode%%team% &8| &7%player%  &c%kills%"),
	TABLIST_PLAYER_WITH_TEAM_RANK("tablist.player.withTeamAndRank", "&7%rank% &8| %colorcode%%team% &8| &7%player%  &c%kills%"),
	TABLIST_PLAYER_WITHOUT_TEAM("tablist.player.withoutTeam", "&7%player%  &c%kills%"),
	TABLIST_PLAYER_WITHOUT_TEAM_RANK("tablist.player.withoutTeamWithRank", "&7%rank% &8| &7%player%  &c%kills%"),

	TEAMREQUEST_ENTER_TEAMNAME("teamrequest.enterTeamName", "%colorcode%&lGib jetzt den Teamnamen fuer dich und %invited% ein:"),
	TEAMREQUEST_MAX_TEAMNAME_LENGTH("teamrequest.maxTeamnameLength", "Dein Teamname darf maximal %colorcode%%maxLength% &7Zeichen enthalten!"),
	TEAMREQUEST_NO_COLORCODE("teamrequest.noColorCode", "Dein Teamname darf keine Farbcodes enthalten!"),
	TEAMREQUEST_NO_HASHTAG("teamrequest.noHashtag", "Dein Teamname darf kein '#' enthalten. (Wird automatisch hinzugefuegt)"),
	TEAMREQUEST_PLAYER_NOT_ONLINE("teamrequest.playerNotOnline", "%colorcode%%invitor% ist nicht mehr online!"),
	TEAMREQUEST_REVOKED("teamrequest.invationRevoked", "Einladung erfolgreich zurueckgezogen!"),
	TEAMREQUEST_TEAM_FULL("teamrequest.teamIsFull", "%invited% konnte dem Team nicht beitreten - es ist bereits voll."),
	TEAMREQUEST_TEAM_REQUEST_RECIEVED("teamrequest.teamRequestRecieved", "%colorcode%%invitor% &7hat dich in ein Team eingeladen (/varo tr)!"),
	TEAMREQUEST_INVITED_TEAM("teamrequest.invitedInTeam", "&7Du hast %colorcode%%invited% &7in das Team %colorcode%%team% &7eingeladen!"),
	TEAMREQUEST_NO_TEAMNAME("teamrequest.noteamname", "&7Du hast noch &7keinen &7Teamnamen!"),

	VARO_COMMANDS_HELP_HEADER("varoCommands.help.header", "&7-------- %colorcode% %category% &7-------"),
	VARO_COMMANDS_HELP_FOOTER("varoCommands.help.footer", "&7------------------------"),

	VARO_COMMANDS_ERROR_USER_NOT_FOUND("varoCommands.error.usernotfound", "&7Es konnte kein User für diesen Spieler gefunden werden!"),

	VARO_COMMANDS_ERROR_UNKNOWN_PLAYER("varoCommands.error.unknownplayer", "&7Der Spieler %colorcode%%player% &7hat den Server noch nie betreten!"),
	VARO_COMMANDS_ERROR_NO_CONSOLE("varoCommands.error.noconsole", "Du musst ein Spieler sein!"),
	VARO_COMMANDS_ERROR_NOT_STARTED("varoCommands.error.notstarted", "Das Spiel wurde noch nicht gestartet!"),
	VARO_COMMANDS_ERROR_USAGE("varoCommands.error.usage", "&cFehler! &7Nutze %colorcode%/varo %command% &7fuer Hilfe."),
	VARO_COMMANDS_ERROR_NO_NUMBER("varoCommands.error.nonumber", "%colorcode%%text% &7ist keine Zahl!"),
	VARO_COMMANDS_ERROR("varoCommands.error.error", "&7Es ist ein Fehler aufgetreten!"),

	VARO_COMMANDS_BUGREPORT_CREATED("varoCommands.bugreport.created", "Bugreport wurde unter &c%filename% &7gespeichert!"),
	VARO_COMMANDS_BUGREPORT_SEND_TO_DISCORD("varoCommands.bugreport.sendtodiscord", "Bitte sende den Bugreport als DATEI manuell auf den Discord in den Support, da das Hochladen nicht funktioniert hat!"),
	VARO_COMMANDS_BUGREPORT_OUTDATED_VERSION("varoCommands.bugreport.outdatedversion", "Du kannst keine Bugreports von einer alten Plugin-Version machen!"),
	VARO_COMMANDS_BUGREPORT_CURRENT_VERSION("varoCommands.bugreport.currentversion", "Derzeitige Version: &c%version%"),
	VARO_COMMANDS_BUGREPORT_NEWEST_VERSION("varoCommands.bugreport.newestversion", "Neueste Version: &a%version%"),
	VARO_COMMANDS_BUGREPORT_UPDATE("varoCommands.bugreport.update", "&7Nutze %colorcode%/varo update &7zum updaten."),
	VARO_COMMANDS_BUGREPORT_COLLECTING_DATA("varoCommands.bugreport.collectingdata", "Daten werden gesammelt..."),
	VARO_COMMANDS_BUGREPORT_UPLOADING("varoCommands.bugreport.uploading", "Lade Bugreport hoch..."),
	VARO_COMMANDS_BUGREPORT_UPLOAD_ERROR("varoCommands.bugreport.uploaderror", "Der Bugreport konnte nicht hochgeladen werden!"),
	VARO_COMMANDS_BUGREPORT_UPLOADED("varoCommands.bugreport.uploaded", "Bugreport wurde auf &c%url% &7hochgeladen!"),
	VARO_COMMANDS_BUGREPORT_CLICK_ME("varoCommands.bugreport.clickme", " &7(&aKlick mich&7)"),

	VARO_COMMANDS_ABORT_COUNTDOWN_NOT_ACTIVE("varoCommands.abort.notactive", "Der Startcountdown ist nicht aktiv!"),
	VARO_COMMANDS_ABORT_COUNTDOWN_STOPPED("varoCommands.abort.stopped", "Startcountdown erfolgreich gestoppt!"),

	VARO_COMMANDS_ACTIONBAR_ACTIVATED("varoCommands.actionbar.activated", "Du siehst nun die Zeit in der Actionbar!"),
	VARO_COMMANDS_ACTIONBAR_DEACTIVATED("varoCommands.actionbar.deactivated", "Du siehst nun nicht mehr die Zeit in der Actionbar!"),

	VARO_COMMANDS_AUTOSETUP_NOT_SETUP_YET("varoCommands.autostart.notsetupyet", "Der AutoSetup wurde noch nicht in der Config eingerichtet!"),
	VARO_COMMANDS_AUTOSETUP_FINISHED("varoCommands.autostart.finished", "Der AutoSetup ist fertig."),
	VARO_COMMANDS_AUTOSETUP_HELP("varoCommands.autostart.help", "%colorcode%/varo autosetup run &7startet den Autosetup"),
	VARO_COMMANDS_AUTOSETUP_ATTENTION("varoCommands.autostart.attention", "&cVorsicht: &7Dieser Befehl setzt neue Spawns, Lobby, Portal, Border und &loptional&7 einen Autostart."),

	VARO_COMMANDS_AUTOSTART_ALREADY_STARTED("varoCommands.autostart.alreadystarted", "%projectname% &7wurde bereits gestartet!"),
	VARO_COMMANDS_AUTOSTART_ALREADY_SETUP("varoCommands.autostart.alreadysetup", "&7Entferne erst den AutoStart, bevor du einen neuen setzt!"),
	VARO_COMMANDS_AUTOSTART_HELP_SET("varoCommands.autostart.helpset", "%colorcode%/autostart &7set <Hour> <Minute> <Day> <Month> <Year>"),
	VARO_COMMANDS_AUTOSTART_NO_NUMBER("varoCommands.autostart.nonumber", "Eines der Argumente ist &ckeine &7Zahl!"),
	VARO_COMMANDS_AUTOSTART_DATE_IN_THE_PAST("varoCommands.autostart.dateinthepast", "&7Das %colorcode%Datum &7darf nicht in der Vergangenheit sein!"),
	VARO_COMMANDS_AUTOSTART_NOT_SETUP_YET("varoCommands.autostart.notsetupyet", "&7Es wurde noch kein %colorcode%Autostart &7festegelegt!"),
	VARO_COMMANDS_AUTOSTART_REMOVED("varoCommands.autostart.removed", "%colorcode%AutoStart &7erfolgreich entfernt!"),
	VARO_COMMANDS_AUTOSTART_DELAY_HELP("varoCommands.autostart.delayhelp", "%colorcode%/autostart delay &7<Delay in Minutes>"),
	VARO_COMMANDS_AUTOSTART_DELAY_TO_SMALL("varoCommands.autostart.delaytosmall", "Der Delay darf nicht kleiner als 1 sein!"),
	VARO_COMMANDS_AUTOSTART_START_DELAYED("varoCommands.autostart.startdelayed", "&7Der Start wurde um %colorcode%%delay% &7Minuten verzoegert!"),
	VARO_COMMANDS_AUTOSTART_INFO_NOT_ACTIVE("varoCommands.autostart.notactive", "AutoStart nicht aktiv"),
	VARO_COMMANDS_AUTOSTART_INFO_ACTIVE("varoCommands.autostart.active", "AutoStart &aaktiv&7:"),
	VARO_COMMANDS_AUTOSTART_INFO_DATE("varoCommands.autostart.info.date", "%colorcode%Datum: &7%date%"),
	VARO_COMMANDS_AUTOSTART_INFO_AUTOSORT("varoCommands.autostart.info.autosort", "%colorcode%AutoSort: &7%active%"),
	VARO_COMMANDS_AUTOSTART_INFO_RANDOM_TEAM_SIZE("varoCommands.autostart.info.randomteamsize", "%colorcode%AutoRandomteamgroesse: &7%teamsize%"),
	VARO_COMMANDS_BACKPACK_PLAYER_DOESNT_EXIST("varoCommands.backpack.playerdoesntexist", "Der Spieler %colorcode%%player% &7existiert nicht."),
	VARO_COMMANDS_BACKPACK_TEAM_DOESNT_EXIST("varoCommands.backpack.teamdoesntexist", "Das Team %colorcode%%team% &7existiert nicht."),
	VARO_COMMANDS_BACKPACK_CANT_SHOW_BACKPACK("varoCommands.backpack.cantshowbackpack", "Der Rucksack kann dir daher nicht angezeigt werden."),
	VARO_COMMANDS_BACKPACK_NO_TEAM("varoCommands.backpack.noteam", "Du befindest dich in keinem Team und hast deshalb keinen Teamrucksack."),
	VARO_COMMANDS_BACKPACK_CHOOSE_TYPE("varoCommands.backpack.choosetype", "Bitte waehle aus, welchen Rucksack du oeffnen willst %colorcode%(Player/Team)&7."),
	VARO_COMMANDS_BACKPACK_NOT_ENABLED("varoCommands.backpack.notenabled", "Die Rucksacke sind nicht aktiviert."),
	VARO_COMMANDS_CONFIG_RELOADED("varoCommands.config.reloaded", "&7Alle %colorcode%Listen&7, %colorcode%Nachrichten &7und die %colorcode%Config &7wurden erfolgreich neu geladen."),
	VARO_COMMANDS_CONFIG_HELP_SET("varoCommands.config.helpset", "%colorcode%/varo config &7set <key> <value>"),
	VARO_COMMANDS_CONFIG_HELP_SEARCH("varoCommands.config.helpsearch", "%colorcode%/varo config &7search <key>"),
	VARO_COMMANDS_CONFIG_ENTRY_SET("varoCommands.config.entryset", "&7Der Eintrag '%colorcode%%entry%&7' wurde erfolgreich auf '%colorcode%%value%&7' gesetzt."),
	VARO_COMMANDS_CONFIG_ENTRY_NOT_FOUND("varoCommands.config.entrynotfound", "&7Der Eintrag '%colorcode%%entry%&7' wurde nicht gefunden."),
	VARO_COMMANDS_CONFIG_RESET("varoCommands.config.reset", "&7Alle Eintraege wurden erfolgreich zurueckgesetzt."),
	VARO_COMMANDS_CONFIG_SEARCH_LIST_TITLE("varoCommands.config.searchlisttitle", "&lFolgende Einstellungen wurden gefunden:"),
	VARO_COMMANDS_CONFIG_SEARCH_LIST_FORMAT("varoCommands.config.searchlistformat", "%colorcode%%entry% &8- &7%description%"),
	VARO_COMMANDS_EXPORT_SUCCESSFULL("varoCommands.export.players", "&7Alle Spieler wurden erfolgreich in '%colorcode%%file%&7' exportiert."),
	VARO_COMMANDS_DISCORD_PLEASE_RELOAD("varoCommands.discord.pleasereload", "&7Der DiscordBot wurde beim Start nicht aufgesetzt, bitte reloade!"),
	VARO_COMMANDS_DISCORD_VERIFY_DISABLED("varoCommands.discord.verifydisabled", "&7Das Verifzierungs-System wurde in der Config deaktiviert!"),
	VARO_COMMANDS_DISCORD_BOT_DISABLED("varoCommands.discord.botdisabled", "&7Der DiscordBot wurde nicht aktiviert!"),
	VARO_COMMANDS_DISCORD_USER_NOT_FOUND("varoCommands.discord.usernotfound", "&7User fuer diesen Spieler nicht gefunden!"),
	VARO_COMMANDS_INTRO_ALREADY_STARTED("varoCommands.intro.alreadystarted", "&7Das Intro wurde bereits gestartet!"),
	VARO_COMMANDS_INTRO_GAME_ALREADY_STARTED("varoCommands.intro.gamealreadystarted", "&7Das Spiel wurde bereits gestartet!"),
	VARO_COMMANDS_INTRO_STARTED("varoCommands.intro.started", "&7Und los geht's!"),
	VARO_COMMANDS_PRESET_NOT_FOUND("varoCommands.preset.notfound", "Das Preset %colorcode%%preset% &7wurde nicht gefunden."),
	VARO_COMMANDS_PRESET_LOADED("varoCommands.preset.loaded", "Das Preset %colorcode%%preset% &7wurde &aerfolgreich &7geladen."),
	VARO_COMMANDS_PRESET_SAVED("varoCommands.preset.saved", "Die aktuellen Einstellungen wurden &aerfolgreich &7als Preset %colorcode%%preset% &7gespeichert."),
	VARO_COMMANDS_PRESET_LIST("varoCommands.preset.list", "&lListe aller Presets:"),
	VARO_COMMANDS_PRESET_HELP_LOAD("varoCommands.preset.helploaded", "%colorcode%/varo preset &7load <PresetPath>"),
	VARO_COMMANDS_PRESET_HELP_SAVE("varoCommands.preset.helpsave", "%colorcode%/varo preset &7save <PresetPath>"),
	VARO_COMMANDS_RANDOMTEAM_HELP("varoCommands.randomteam.help", "%colorcode%/varo randomTeam <Teamgroesse>"),
	VARO_COMMANDS_RANDOMTEAM_TEAMSIZE_TOO_SMALL("varoCommands.randomteam.teamsizetoosmall", "&7Die Teamgroesse muss mindestens 1 betragen."),
	VARO_COMMANDS_RANDOMTEAM_SORTED("varoCommands.randomteam.sorted", "&7Alle Spieler ohne Team sind nun in %colorcode%%teamsize%er &7Teams!"),
	VARO_COMMANDS_RANDOMTEAM_NO_PARTNER("varoCommands.randomteam.sorted", "&7Fuer dich konnten nicht genug Teampartner gefunden werden."),

	VARO_COMMANDS_RESTART_IN_LOBBY("varoCommands.restart.inlobby", "&7Das Spiel befindet sich bereits in der Lobby-Phase!"),
	VARO_COMMANDS_RESTART_RESTARTED("varoCommands.restart.restarted", "&7Das Spiel wurde neugestartet."),
	VARO_COMMANDS_SCOREBOARD_DEACTIVATED("varoCommands.scoreboard.deactivated", "&7Scoreboard sind deaktiviert."),
	VARO_COMMANDS_SCOREBOARD_ENABLED("varoCommands.scoreboard.enabled", "&7Du siehst nun das Scoreboard."),
	VARO_COMMANDS_SCOREBOARD_DISABLED("varoCommands.scoreboard.disabled", "&7Du siehst nun das Scoreboard."),
	VARO_COMMANDS_SORT_HELP("varoCommands.sort.help", "%colorcode%/varo sort"),
	VARO_COMMANDS_SORT_SORTED_WELL("varoCommands.sort.sorted", "&7Alle Spieler wurden erfolgreich sortiert."),
	VARO_COMMANDS_SORT_NO_SPAWN_WITH_TEAM("varoCommands.sort.nospawnwithteam", "&7Es konnte nicht fuer jeden Spieler ein Loch bei den Teampartnern gefunden werden!"),
	VARO_COMMANDS_SORT_NO_SPAWN("varoCommands.sort.nospawn", "&7Es konnte nicht fuer jeden Spieler ein Loch gefunden werden!"),

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
	VARO_COMMANDS_DISCORD_NO_EVENT_CHANNEL("varoCommands.discord.noeventchannel", "&7Dem Bot wurde keine Event-Channel gegeben."),
	VARO_COMMANDS_DISCORD_VERIFY_ENABLED("varoCommands.discord.verifyenabled", "&7Das Verifysystem wurde aktiviert."),
	VARO_COMMANDS_DISCORD_DISCORD_MESSAGE_TITLE("varoCommands.discord.discordmessagetitle", "MESSAGE"),
	VARO_COMMANDS_DISCORD_BYPASS_ACTIVE("varoCommands.discord.bypassactive", "&7%player% umgeht nun das Verifysystem."),
	VARO_COMMANDS_DISCORD_BYPASS_INACTIVE("varoCommands.discord.bypassinactive", "&7%player% umgeht nicht mehr das Verifysystem."),
	VARO_COMMANDS_DISCORD_VERIFY_ACCOUNT("varoCommands.discord.account", "&7Account: %colorcode%%account%"),
	VARO_COMMANDS_DISCORD_VERIFY_REMOVE_USAGE("varoCommands.discord.account", "&7Nutze %colorcode%/varo discord verify remove &7ein, um die Verifizierung zu entfernen."),

	SPAWNS_SPAWN_NUMBER("spawns.spawnNameTag.number", "&7Spawn %colorcode%%number%"),
	SPAWNS_SPAWN_PLAYER("spawns.spawnNameTag.player", "&7Spawn von %colorcode%%player%"),

	OTHER_CONFIG("other.configReload", "&7Die %colorcode%Config &7wurde neu geladen"),
	OTHER_PING("other.ping", "&7Dein %colorcode%Ping &7betraegt: %colorcode%%ping%&7ms");

	private String path, defaultMessage, message;

	private ConfigMessages(String path, String message) {
		this.path = path;
		this.defaultMessage = message;
		this.message = message;
	}

	private String getMessage(Language lang) {
		String message = null;
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