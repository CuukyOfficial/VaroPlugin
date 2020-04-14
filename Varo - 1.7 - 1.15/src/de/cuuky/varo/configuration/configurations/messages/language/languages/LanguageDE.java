package de.cuuky.varo.configuration.configurations.messages.language.languages;

public enum LanguageDE implements LanguageMessage {

	ALERT_AUTOSTART_AT(0, "alerts.BOTS_ALERT.autostartAt", "%projectname% wird am %date% starten!"),
	ALERT_BORDER_CHANGED(1, "alerts.BOTS_ALERT.borderChanged", "Die Border wurde auf %size% gesetzt!"),
	ALERT_BORDER_DECREASED_DEATH(2, "alerts.BOTS_ALERT.borderDecrease.death", "Die Border wurde um %size% aufgrund eines Todes verringert!"),
	ALERT_BORDER_DECREASED_TIME_DAYS(3, "alerts.BOTS_ALERT.borderDecrease.days", "Die Border wurde um %size% verkleinert. Naechste Verkleinerung in %days% Tagen!"),
	ALERT_BORDER_DECREASED_TIME_MINUTE(4, "alerts.BOTS_ALERT.borderDecrease.minutes", "Die Border wurde um %size% verringert! Naechste Verkleinerung in %minutes% Minuten!"),
	ALERT_COMBAT_LOG(5, "alerts.BOTS_ALERT.combatlog", "%player% hat sich im Kampf ausgeloggt!"),
	ALERT_COMBAT_LOG_STRIKE(6, "alerts.BOTS_ALERT.combatlogStrike", "%player% hat sich im Kampf ausgeloggt und hat somit einen Strike erhalten!"),
	ALERT_DISCONNECT_TOO_OFTEN(7, "alerts.BOTS_ALERT.disconnectTooOften", "%player% hat das Spiel zu oft verlassen, weswegen seine Session entfernt wurde!"),
	ALERT_DISCORD_DEATH(8, "alerts.BOTS_ALERT.death", "%player% ist gestorben! Grund: %reason%"),
	ALERT_DISCORD_KILL(9, "alerts.BOTS_ALERT.kill", "%player% wurde von %killer% getoetet!"),
	ALERT_FIRST_STRIKE(10, "alerts.BOTS_ALERT.firstStrike", "%player% hat nun einen Strike. Der Strike wurde von %striker% gegeben. Begruendung: %reason%\nAufgrund dessen sind hier die derzeiten Koordinaten: %pos%!"),
	ALERT_FIRST_STRIKE_NEVER_ONLINE(11, "alerts.BOTS_ALERT.firstStrikeNeverOnline", "%player% hat nun einen Strike. Der Strike wurde von %striker% gegeben. Begruendung: %reason%\nDer Spieler war noch nicht online und wird an den Spawn-Koordinaten spawnen: %pos%!"),
	ALERT_GAME_STARTED(12, "alerts.BOTS_ALERT.gameStarted", "%projectname% wurde gestartet!"),
	ALERT_GENERAL_STRIKE(13, "alerts.BOTS_ALERT.generalStrike", "%player% hat nun den %strikeNumber%ten Strike! Der Strike wurde von %striker% gegeben. Begruendung: %reason%"),
	ALERT_JOIN_FINALE(14, "alerts.BOTS_ALERT.finale", "%player% &7hat den Server zum Finale betreten."),
	ALERT_KICKED_PLAYER(15, "alerts.BOTS_ALERT.kickedPlayer", "%player% wurde gekickt!"),
	ALERT_NEW_SESSIONS(16, "alerts.BOTS_ALERT.newSessions", "Es wurden %newSessions% neue Folgen an die Spieler gegeben!"),
	ALERT_NEW_SESSIONS_FOR_ALL(17, "alerts.BOTS_ALERT.newSessionsForAll", "Alle haben %newSessions% neue Folgen bekommen!"),
	ALERT_NO_BLOODLUST(18, "alerts.BOTS_ALERT.noBloodlust", "%player% hat nun %days% Tage nicht gekaempft, was das Limit ueberschritten hat!"),
	ALERT_NO_BLOODLUST_STRIKE(19, "alerts.BOTS_ALERT.noBloodlustStrike", "%player% hat nun %days% Tage nicht gekaempft, weswegen %player% jetzt gestriket wurde!"),
	ALERT_NOT_JOIN(20, "alerts.BOTS_ALERT.notJoin", "%player% war nun %days% Tage nicht online, was das Limit ueberschritten hat!"),
	ALERT_NOT_JOIN_STRIKE(21, "alerts.BOTS_ALERT.notJoinStrike", "%player% war nun %days% Tage nicht online, weswegen %player% jetzt gestriket wurde!"),
	ALERT_PLAYER_DC_TO_EARLY(22, "alerts.BOTS_ALERT.playerQuitToEarly", "%player% hat das Spiel vorzeitig verlassen! %player% hat noch %seconds% Sekunden Spielzeit ueber!"),
	ALERT_PLAYER_JOIN_MASSREC(23, "alerts.BOTS_ALERT.playerJoinMassrec", "%player% hat den Server in der Massenaufnahme betreten und spielt nun die %episodesPlayedPlus1%te Folge"),
	ALERT_PLAYER_JOIN_NORMAL(24, "alerts.BOTS_ALERT.playerJoinNormal", "%player% hat das Spiel betreten!"),
	ALERT_PLAYER_JOINED(25, "alerts.BOTS_ALERT.playerJoined", "%player% hat den Server betreten und spielt nun die %episodesPlayedPlus1%te Folge!"),
	ALERT_PLAYER_QUIT(26, "alerts.BOTS_ALERT.playerQuit", "%player% hat das Spiel verlassen!"),
	ALERT_PLAYER_RECONNECT(27, "alerts.BOTS_ALERT.playerReconnect", "%player% hatte das Spiel vorzeitig verlassen und ist rejoint! %player% hat noch %seconds% Sekunden verbleibend!"),
	ALERT_SECOND_STRIKE(28, "alerts.BOTS_ALERT.secondStrike", "%player% hat nun zwei Strikes. Der Strike wurde von %striker% gegeben. Begruendung: %reason%\nAufgrund dessen wurde das Inventar geleert!"),
	ALERT_SWITCHED_NAME(29, "alerts.BOTS_ALERT.switchedName", "%player% hat den Namen gewechselt und ist nun unter %newName% bekannt!"),
	ALERT_TELEPORTED_TO_MIDDLE(30, "alerts.BOTS_ALERT.teleportedToMiddle", "%player% wurde zur Mitte teleportiert!"),
	ALERT_THRID_STRIKE(31, "alerts.BOTS_ALERT.thirdStrike", "%player% hat nun drei Strikes. Der Strike wurde von %striker% gegeben. Begruendung: %reason%\nDamit ist %player% aus %projectname% ausgeschieden!"),
	ALERT_WINNER(32, "alerts.BOTS_ALERT.win.player", "%player% hat %projectname% gewonnen! Gratulation!"),
	ALERT_WINNER_TEAM(33, "alerts.BOTS_ALERT.win.team", "%winnerPlayers% haben %projectname% gewonnen! Gratulation!"),

	BOTS_DISCORD_NOT_REGISTERED_DISCORD(37, "bots.notRegisteredDiscord", "&cDu bist noch nicht mit dem Discord authentifiziert!\n&7Um dich zu authentifizieren, schreibe in den #verify -Channel &c'varo verify <Deine ID>' &7auf dem Discord!\nLink zum Discordserver: &c%discordLink%\n&7Deine ID lautet: &c%code%"),
	BOTS_DISCORD_NO_SERVER_USER(38, "bots.noServerUser", "&cDein Account ist nicht auf dem Discord!%nextLine%&7Joine dem Discord und versuche es erneut."),

	BORDER_MINIMUM_REACHED(42, "border.minimumReached", "&cDie Border hat ihr Minimum erreicht!"),
	BORDER_DECREASE_DAYS(43, "border.decreaseDays", "&7Die Border wird jetzt um &c%size% &7Bloecke mit &c%speed% &7Bloecken/s verkleinert. &7Naechste Verkleinerung in &c%days% &7Tagen!"),
	BORDER_DECREASE_DEATH(44, "border.decreaseDeath", "&7Die Border wird jetzt um &c%size% &7Bloecke mit &c%speed% &7Bloecken/s aufgrund eines Todes verkleinert."),
	BORDER_MINUTE_TIME_UPDATE(45, "border.minuteTimeUpdate", "&7Die Border wird in &c%minutes%&7:&c%seconds% &7verkleinert!"),
	BORDER_DECREASE_MINUTES(46, "border.decreaseMinutes", "&7Die Border wird jetzt um &c%size% &7Bloecke mit &c%speed% &7Bloecken/s verkleinert. &7Naechste Verkleinerung in &c%days% &7Minuten!"),
	BORDER_DISTANCE(47, "border.distanceToBorder", "&7Distanz zur Border: %colorcode%%size% &7Bloecke"),
	BORDER_COMMAND_SET_BORDER(48, "border.borderSet", "&7Die Border wurde auf %colorcode%%size% &7gesetzt!"),

	CHAT_PLAYER_WITH_TEAM(54, "chat.format.withTeam", "%colorcode%%team% &8| &7%player% &8» &f%message%"),
	CHAT_PLAYER_WITH_TEAM_RANK(55, "chat.format.withTeamAndRank", "&7%rank% &8| %colorcode%%team% &8| &7%player% &8» &f%message%"),
	CHAT_PLAYER_WITHOUT_TEAM(56, "chat.format.withoutTeam", "&7%player% &8» &f%message%"),
	CHAT_PLAYER_WITHOUT_TEAM_RANK(57, "chat.format.withoutTeamWithRank", "&7%rank% &8| &7%player% &8» &f%message%"),

	CHAT_TEAMCHAT_FORMAT(59, "chat.teamchatFormat", "&7[%team%&7] %player% &8» &f%message%"),
	CHAT_MUTED(60, "chat.muted", "&7Du wurdest gemutet!"),
	CHAT_WHEN_START(61, "chat.chatOnStart", "&7Du kannst erst ab dem Start wieder schreiben!"),

	COMBAT_FRIENDLY_FIRE(65, "combat.friendlyfire", "&7Dieser Spieler ist in deinem Team!"),
	COMBAT_IN_FIGHT(66, "combat.inFight", "&7Du bist nun im Kampf, logge dich &4NICHT &7aus!"),
	COMBAT_LOGGED_OUT(67, "combat.loggedOut", "&c%player% &7hat den Server waehrend eines Kampfes verlassen!"),
	COMBAT_NOT_IN_FIGHT(68, "combat.notInFight", "&7Du bist nun nicht mehr im &cKampf&7!"),

	SPAWN_WORLD(72, "spawn.spawn", "%colorcode%Koordinaten&7 vom Spawn: %colorcode%%x%&7, %colorcode%%y%&7, %colorcode%%z%"),
	SPAWN_NETHER(73, "spawn.spawnNether", "%colorcode%Koordinaten&7 vom Portal zur Oberwelt: %colorcode%%x%&7, %colorcode%%y%&7, %colorcode%%z%"),
	SPAWN_DISTANCE(74, "spawn.spawnDistance", "&7Du bist %colorcode%%distance% &7Bloecke vom Spawn entfernt!"),
	SPAWN_DISTANCE_NETHER(75, "spawn.spawnDistanceNether", "&7Du bist %colorcode%%distance% &7Bloecke vom Portal zur Oberwelt entfernt!"),

	DEATH_DEAD(79, "death.killMessage", "&c%player% &7ist gestorben. &7Grund: &c%reason%"),
	DEATH_KILLED_BY(80, "death.killed", "%colorcode%%player% &7wurde von &c%killer% &7getoetet!"),
	DEATH_LIFE_LOST(81, "death.teamLifeLost", "%player% hat nun noch %colorcode%%teamLifes% &7Teamleben!"),
	DEATH_RESPAWN_PROTECTION(82, "death.respawnProtection", "&c%player% hat nun ein Leben weniger und ist fuer %seconds% unverwundbar!"),
	DEATH_RESPAWN_PROTECTION_OVER(83, "death.respawnProtectionOver", "&c%player% ist nun wieder verwundbar!"),
	DEATH_KILL_LIFE_ADD(84, "death.killLifeAdd", "Dein Team hat aufgrund eines Kills ein Teamleben erhalten!"),

	GAME_START_COUNTDOWN(88, "game.start.startCountdown", "%projectname% &7startet in %colorcode%%countdown% &7Sekunde(n)!"),
	GAME_VARO_START(89, "game.start.varoStart", "%projectname% &7wurde gestartet! &5Viel Erfolg!"),
	GAME_VARO_START_TITLE(90, "game.start.startTitle", "%colorcode%%countdown%\n&7Viel Glueck!"),
	GAME_WIN(91, "game.win.single", "&5%player% &7hat %projectname% &7gewonnen! &5Gratulation!"),
	GAME_WIN_TEAM(92, "game.win.team", "&5%winnerPlayers% &7haben %projectname% &7gewonnen! &5Gratulation!"),

	JOIN_MESSAGE(96, "joinmessage.join", "%prefix%&a%player% &7hat den Server betreten!"),
	JOIN_FINALE(97, "joinmessage.finale", "%prefix%&a%player% &7hat den Server zum Finale betreten."),
	JOIN_MASS_RECORDING(98, "joinmessage.massrecording", "%prefix%&a%player% &7hat den Server in der Massenaufnahme betreten und ist in %colorcode%%protectionTime% &7Sekunden angreifbar!"),
	JOIN_NO_MOVE_IN_PROTECTION(99, "joinmessage.notMoveinProtection", "&7Du kannst dich nicht bewegen, solange du noch in der %colorcode%Schutzzeit &7bist!"),
	JOIN_PROTECTION_OVER(100, "joinmessage.joinProtectionOver", "%prefix%&a%player% &7ist nun angreifbar!"),
	JOIN_PROTECTION_TIME(101, "joinmessage.joinProtectionTime", "%prefix%&a%player% &7hat den Server betreten und ist in %colorcode%%protectionTime% &7Sekunden angreifbar!"),
	JOIN_SPECTATOR(102, "joinmessage.spectator", "&a%player% &7hat den Server als Spectator betreten!"),
	JOIN_WITH_REMAINING_TIME(103, "joinmessage.joinWithRemainingTime", "%prefix%&a%player% &7hatte den Server zu frueh verlassen und hat jetzt noch %colorcode%%seconds% &7Sekunden uebrig! Verbleibende &cDisconnects&7: &c%remainingDisconnects%"),

	QUIT_MESSAGE(107, "quitmessage.quit", "%prefix%&c%player%&7 hat den Server verlassen!"),
	QUIT_DISCONNECT_SESSION_END(108, "quitmessage.disconnectKilled", "&c%player% &7hat das Spiel verlassen und ist seit &c%banTime% &7Minute(n) nicht mehr online.%nextLine%&7Damit ist er aus %projectname% &7ausgeschieden!"),
	QUIT_SPECTATOR(109, "quitmessage.spectator", "&c%player% &7hat den Server als Spectator verlassen!"),
	QUIT_TOO_OFTEN(110, "quitmessage.quitTooOften", "&c%player% &7hat den Server zu oft verlassen und dadurch seine Sitzung verloren."),
	QUIT_WITH_REMAINING_TIME(111, "quitmessage.quitRemainingTime", "%prefix%&c%player% &7hat den Server vorzeitig verlassen!"),
	QUIT_KICK_BROADCAST(112, "quitmessage.broadcast", "%colorcode%%player% &7wurde gekickt!"),
	QUIT_KICK_DELAY_OVER(113, "quitmessage.protectionOver", "%colorcode%%player% &7wurde aufgrund seines Todes jetzt gekickt!"),
	QUIT_KICK_IN_SECONDS(114, "quitmessage.kickInSeconds", "%colorcode%%player% &7wird in %colorcode%%countdown% &7Sekunde(n) gekickt!"),
	QUIT_KICK_PLAYER_NEARBY(115, "quitmessage.noKickPlayerNearby", "&cEs befindet sich ein Spieler &4%distance% &cBloecke in deiner Naehe!%nextLine%&7Um gekickt zu werden, entferne dich von diesem Spieler!"),
	QUIT_KICK_SERVER_CLOSE_SOON(116, "quitmessage.serverCloseSoon", "&7Der Server schliesst in &c%minutes% &7Minuten!"),

	DEATH_KICK_DEAD(120, "kick.kickedKilled", "&cDu bist gestorben! %nextLine% &7Damit bist du aus %projectname% &7ausgeschieden"),
	DEATH_KICK_KILLED(121, "kick.killedKick", "&7Du wurdest von &c%killer% &7getoetet! %nextLine% &7Damit bist du aus %projectname% &7ausgeschieden!"),
	JOIN_KICK_NOT_USER_OF_PROJECT(122, "kick.notUserOfTheProject", "&7Du bist kein Teilnehmer dieses %projectname%&7's!"),
	JOIN_KICK_SERVER_FULL(123, "kick.serverFull", "&cDer Server ist voll!%nextLine%&7Sprich mit dem Owner, falls das das ein Irrtum sein sollte!"),
	JOIN_KICK_STRIKE_BAN(124, "kick.strikeBan", "&cDu wurdest aufgrund deines letzten Strikes fuer %hours% gebannt!\nVersuche es spaeter erneut"),
	JOIN_KICK_BANNED(125, "kick.banned", "&4Du bist vom Server gebannt!\n&7Melde dich bei einem Admin, falls dies ein Fehler sein sollte.\n&7Grund: &c%reason%"),
	JOIN_KICK_NO_PREPRODUCES_LEFT(126, "kick.noPreproduceLeft", "&cDu hast bereits vorproduziert! %nextLine%&7Versuche es morgen erneut."),
	JOIN_KICK_NO_SESSIONS_LEFT(127, "kick.noSessionLeft", "&cDu hast keine Sessions mehr uebrig! %nextLine%&7Warte bis morgen, damit du wieder spielen kannst!"),
	JOIN_KICK_NO_TIME_LEFT(128, "kick.noTimeLeft", "&cDu darfst nur alle &4%timeHours% &cStunden regulaer spielen! %nextLine%&7Du kannst erst in &c%stunden%&7:&c%minuten%&7:&c%sekunden% &7wieder joinen!"),
	JOIN_KICK_NOT_STARTED(129, "kick.notStarted", "&cDer Server wurde noch nicht eroeffnet! %nextLine%&7Gedulde dich noch ein wenig!"),
	KICK_SESSION_OVER(130, "kick.kickMessage", "&cDeine Aufnahmezeit ist abgelaufen, %nextLine%&7deswegen wurdest du gekickt!"),
	KICK_MASS_REC_SESSION_OVER(131, "kick.kickMessageMassRec", "&cDie Massenaufnahme ist beendet, %nextLine%&7deswegen wurdest du gekickt!"),
	KICK_TOO_MANY_STRIKES(132, "kick.tooManyStrikes", "&7Du hast zu viele Strikes bekommen und wurdest daher aus dem Projekt %projectname% &7entfernt."),
	KICK_COMMAND(133, "kick.kick", "%colorcode%%player% &7wurde gekick.kickt!"),

	LABYMOD_DISABLED(137, "labymod.labyModDisabled", "&7Alle deine LabyMod Funktionen wurden deaktiviert!"),
	LABYMOD_KICK(138, "labymod.labyMod", "&cLabyMod isn't allowed on this server."),

	SERVER_MODT_CANT_JOIN_HOURS(142, "motd.cantJoinHours", "&cDu kannst nur zwischen &4%minHour% &cund &4%maxHour%&c Uhr joinen! %nextLine%&7Versuche es spaeter erneut! &7%currHour%&7:&7%currMin%&7:&7%currSec%"),
	SERVER_MODT_NOT_OPENED(143, "motd.serverNotOpened", "&cDer Server wurde noch nicht fuer alle geoeffnet! %nextLine%&7Versuche es spaeter erneut!"),
	SERVER_MODT_OPEN(144, "motd.serverOpen", "&aSei nun bei %projectname% &adabei! \n&7Viel Spass!"),

	NAMETAG_NORMAL(148, "nametag.normalNametagPrefix", "&7"),
	NAMETAG_SUFFIX(149, "nametag.normalSuffix", "&c %kills%"),
	NAMETAG_TEAM_PREFIX(150, "nametag.nametagWithTeam", "%colorcode%%team% &7"),

	CHEST_NOT_TEAM_CHEST(154, "chest.notTeamChest", "&7Diese Kiste gehoert %colorcode%%player%&7!"),
	CHEST_NOT_TEAM_FURNACE(155, "chest.notTeamFurnace", "&7Dieser Ofen gehoert %colorcode%%player%&7!"),
	CHEST_REMOVED_SAVEABLE(156, "chest.removedChest", "&7Du hast diese/n %saveable% %colorcode%erfolgreich &7entfernt!"),
	CHEST_SAVED_CHEST(157, "chest.newChestSaved", "&7Eine neue Kiste wurde gesichert!"),
	CHEST_SAVED_FURNACE(158, "chest.newFurnaceSaved", "&7Ein neuer Ofen wurde gesichert!"),

	NOPERMISSION_NO_PERMISSION(162, "nopermission.noPermission", "%colorcode%Dazu bist du nicht berechtigt!"),
	NOPERMISSION_NOT_ALLOWED_CRAFT(163, "nopermission.notAllowedCraft", "&7Das darfst du nicht craften, benutzen oder brauen!"),
	NOPERMISSION_NO_LOWER_FLIGHT(164, "nopermission.noLowerFlight", "&7Niedriger darfst du nicht fliegen!"),

	PROTECTION_NO_MOVE_START(168, "protection.noMoveStart", "&7Du kannst dich nicht bewegen, solange das Projekt noch nicht gestartet wurde."),
	PROTECTION_START(169, "protection.start", "&7Die &cSchutzzeit &7protection.startet jetzt und wird &c%seconds% &7Sekunden anhalten!"),
	PROTECTION_TIME_OVER(170, "protection.protectionOver", "&7Die &cSchutzzeit &7ist nun vorrueber!"),
	PROTECTION_TIME_UPDATE(171, "protection.protectionUpdate", "&7Die &cSchutzzeit &7ist in &c%minutes%&7:&c%seconds% &7vorrueber!"),
	PROTECTION_TIME_RUNNING(172, "protection.timeRunning", "&7Die %colorcode%Schutzzeit &7laeuft noch!"),

	SORT_NO_HOLE_FOUND(176, "sort.noHoleFound", "Es konnte fuer dich kein Loch gefunden werden!"),
	SORT_NO_HOLE_FOUND_TEAM(177, "sort.noHoleFoundTeam", "Es konnte fuer dich kein Loch bei deinen Teampartnern gefunden werden."),
	SORT_NUMBER_HOLE(178, "sort.numberHoleTeleport", "Du wurdest in das Loch %colorcode%%number% &7teleportiert!"),
	SORT_OWN_HOLE(179, "sort.ownHoleTeleport", "Du wurdest in dein Loch einsortiert!"),
	SORT_SPECTATOR_TELEPORT(180, "sort.spectatorTeleport", "Du wurdest, da du Spectator bist, zum Spawn teleportiert!"),
	SORT_SORTED(181, "sort.sorted", "&7Du wurdest in das Loch %colorcode%%zahl% &7teleportiert!"),

	TABLIST_PLAYER_WITH_TEAM(185, "tablist.player.withTeam", "%colorcode%%team% &8| &7%player%  &c%kills%"),
	TABLIST_PLAYER_WITH_TEAM_RANK(186, "tablist.player.withTeamAndRank", "&7%rank% &8| %colorcode%%team% &8| &7%player%  &c%kills%"),
	TABLIST_PLAYER_WITHOUT_TEAM(187, "tablist.player.withoutTeam", "&7%player%  &c%kills%"),
	TABLIST_PLAYER_WITHOUT_TEAM_RANK(188, "tablist.player.withoutTeamWithRank", "&7%rank% &8| &7%player%  &c%kills%"),

	TEAMREQUEST_ENTER_TEAMNAME(192, "teamrequest.enterTeamName", "%colorcode%&lGib jetzt den Teamnamen fuer dich und %invited% ein:"),
	TEAMREQUEST_MAX_TEAMNAME_LENGTH(193, "teamrequest.maxTeamnameLength", "Dein Teamname darf maximal %colorcode%%maxLength% &7Zeichen enthalten!"),
	TEAMREQUEST_NO_COLORCODE(194, "teamrequest.noColorCode", "Dein Teamname darf keine Farbcodes enthalten!"),
	TEAMREQUEST_NO_HASHTAG(195, "teamrequest.noHashtag", "Dein Teamname darf kein '#' enthalten. (Wird automatisch hinzugefuegt)"),
	TEAMREQUEST_PLAYER_NOT_ONLINE(196, "teamrequest.playerNotOnline", "%colorcode%%invitor% ist nicht mehr online!"),
	TEAMREQUEST_REVOKED(197, "teamrequest.invationRevoked", "Einladung erfolgreich zurueckgezogen!"),
	TEAMREQUEST_TEAM_FULL(198, "teamrequest.teamIsFull", "%invited% konnte dem Team nicht beitreten - es ist bereits voll."),
	TEAMREQUEST_TEAM_REQUEST_RECIEVED(199, "teamrequest.teamRequestRecieved", "%colorcode%%invitor% &7hat dich in ein Team eingeladen (/varo tr)!"),
	TEAMREQUEST_INVITED_TEAM(200, "teamrequest.invitedInTeam", "&7Du hast %colorcode%%invited% &7in das Team %colorcode%%team% &7eingeladen!"),
	TEAMREQUEST_NO_TEAMNAME(201, "teamrequest.noteamname", "&7Du hast noch &7keinen &7Teamnamen!"),

	VARO_COMMANDS_HELP_HEADER(205, "varoCommands.help.header", "&7-------- %colorcode% %category% &7-------"),
	VARO_COMMANDS_HELP_FOOTER(206, "varoCommands.help.footer", "&7------------------------"),

	VARO_COMMANDS_ERROR_UNKNOWN_PLAYER(208, "varoCommands.error.unknownplayer", "&7Der Spieler %colorcode%%player% &7hat den Server noch nie betreten!"),
	VARO_COMMANDS_ERROR_NO_CONSOLE(209, "varoCommands.error.noconsole", "Du musst ein Spieler sein!"),
	VARO_COMMANDS_ERROR_NOT_STARTED(210, "varoCommands.error.notstarted", "Das Spiel wurde noch nicht gestartet!"),
	VARO_COMMANDS_ERROR_USAGE(211, "varoCommands.error.usage", "&cFehler! &7Nutze %colorcode%/varo %command% &7fuer Hilfe."),
	VARO_COMMANDS_ERROR_NO_NUMBER(212, "varoCommands.error.nonumber", "%colorcode%%text% &7ist keine Zahl!"),
	VARO_COMMANDS_ERROR(213, "varoCommands.error.error", "&7Es ist ein Fehler aufgetreten!"),

	VARO_COMMANDS_BUGREPORT_CREATED(215, "varoCommands.bugreport.created", "Bugreport wurde unter &c%filename% &7gespeichert!"),
	VARO_COMMANDS_BUGREPORT_SEND_TO_DISCORD(216, "varoCommands.bugreport.sendtodiscord", "Bitte sende den Bugreport auf den Discord in den Support!"),
	VARO_COMMANDS_BUGREPORT_OUTDATED_VERSION(217, "varoCommands.bugreport.outdatedversion", "Du kannst keine Bugreports von einer alten Plugin-Version machen!"),
	VARO_COMMANDS_BUGREPORT_CURRENT_VERSION(218, "varoCommands.bugreport.currentversion", "Derzeitige Version: &c%version%"),
	VARO_COMMANDS_BUGREPORT_NEWEST_VERSION(219, "varoCommands.bugreport.newestversion", "Neueste Version: &a%version%"),
	VARO_COMMANDS_BUGREPORT_UPDATE(220, "varoCommands.bugreport.update", "&7Nutze %colorcode%/varo update &7zum updaten."),
	VARO_COMMANDS_BUGREPORT_COLLECTING_DATA(221, "varoCommands.bugreport.collectingdata", "Daten werden gesammelt..."),
	VARO_COMMANDS_BUGREPORT_UPLOADING(222, "varoCommands.bugreport.uploading", "Lade Bugreport hoch..."),
	VARO_COMMANDS_BUGREPORT_UPLOAD_ERROR(223, "varoCommands.bugreport.uploaderror", "Der Bugreport konnte nicht hochgeladen werden!"),
	VARO_COMMANDS_BUGREPORT_UPLOADED(224, "varoCommands.bugreport.uploaded", "Bugreport wurde auf &c%url% &7hochgeladen!"),
	VARO_COMMANDS_BUGREPORT_CLICK_ME(225, "varoCommands.bugreport.clickme", " &7(&aKlick mich&7)"),

	VARO_COMMANDS_ABORT_COUNTDOWN_NOT_ACTIVE(227, "varoCommands.abort.notactive", "Der Startcountdown ist nicht aktiv!"),
	VARO_COMMANDS_ABORT_COUNTDOWN_STOPPED(228, "varoCommands.abort.stopped", "Startcountdown erfolgreich gestoppt!"),

	VARO_COMMANDS_ACTIONBAR_ACTIVATED(230, "varoCommands.actionbar.activated", "Du siehst nun die Zeit in der Actionbar!"),
	VARO_COMMANDS_ACTIONBAR_DEACTIVATED(231, "varoCommands.actionbar.deactivated", "Du siehst nun nicht mehr die Zeit in der Actionbar!"),

	VARO_COMMANDS_AUTOSETUP_NOT_SETUP_YET(233, "varoCommands.autostart.notsetupyet", "Der AutoSetup wurde noch nicht in der Config eingerichtet!"),
	VARO_COMMANDS_AUTOSETUP_FINISHED(234, "varoCommands.autostart.finished", "Der AutoSetup ist fertig."),
	VARO_COMMANDS_AUTOSETUP_HELP(235, "varoCommands.autostart.help", "%colorcode%/varo autosetup run &7startet den Autosetup"),
	VARO_COMMANDS_AUTOSETUP_ATTENTION(236, "varoCommands.autostart.attention", "&cVorsicht: &7Dieser Befehl setzt neue Spawns, Lobby, Portal, Border und &loptional&7 einen Autostart."),

	VARO_COMMANDS_AUTOSTART_ALREADY_STARTED(238, "varoCommands.autostart.alreadystarted", "%projectname% &7wurde bereits gestartet!"),
	VARO_COMMANDS_AUTOSTART_ALREADY_SETUP(239, "varoCommands.autostart.alreadysetup", "&7Entferne erst den AutoStart, bevor du einen neuen setzt!"),
	VARO_COMMANDS_AUTOSTART_HELP_SET(240, "varoCommands.autostart.helpset", "%colorcode%/autostart &7set <Hour> <Minute> <Day> <Month> <Year>"),
	VARO_COMMANDS_AUTOSTART_NO_NUMBER(241, "varoCommands.autostart.nonumber", "Eines der Argumente ist &ckeine &7Zahl!"),
	VARO_COMMANDS_AUTOSTART_DATE_IN_THE_PAST(242, "varoCommands.autostart.dateinthepast", "&7Das %colorcode%Datum &7darf nicht in der Vergangenheit sein!"),
	VARO_COMMANDS_AUTOSTART_NOT_SETUP_YET(243, "varoCommands.autostart.notsetupyet", "&7Es wurde noch kein %colorcode%Autostart &7festegelegt!"),
	VARO_COMMANDS_AUTOSTART_REMOVED(244, "varoCommands.autostart.removed", "%colorcode%AutoStart &7erfolgreich entfernt!"),
	VARO_COMMANDS_AUTOSTART_DELAY_HELP(245, "varoCommands.autostart.delayhelp", "%colorcode%/autostart delay &7<Delay in Minutes>"),
	VARO_COMMANDS_AUTOSTART_DELAY_TO_SMALL(246, "varoCommands.autostart.delaytosmall", "Der Delay darf nicht kleiner als 1 sein!"),
	VARO_COMMANDS_AUTOSTART_START_DELAYED(247, "varoCommands.autostart.startdelayed", "&7Der Start wurde um %colorcode%%delay% &7Minuten verzoegert!"),
	VARO_COMMANDS_AUTOSTART_INFO_NOT_ACTIVE(248, "varoCommands.autostart.notactive", "AutoStart nicht aktiv"),
	VARO_COMMANDS_AUTOSTART_INFO_ACTIVE(249, "varoCommands.autostart.active", "AutoStart &aaktiv&7:"),
	VARO_COMMANDS_AUTOSTART_INFO_DATE(250, "varoCommands.autostart.info.date", "%colorcode%Datum: &7%date%"),
	VARO_COMMANDS_AUTOSTART_INFO_AUTOSORT(251, "varoCommands.autostart.info.autosort", "%colorcode%AutoSort: &7%active%"),
	VARO_COMMANDS_AUTOSTART_INFO_RANDOM_TEAM_SIZE(252, "varoCommands.autostart.info.randomteamsize", "%colorcode%AutoRandomteamgroesse: &7%teamsize%"),
	VARO_COMMANDS_BACKPACK_PLAYER_DOESNT_EXIST(254, "varoCommands.backpack.playerdoesntexist", "Der Spieler %colorcode%%player% &7existiert nicht."),
	VARO_COMMANDS_BACKPACK_TEAM_DOESNT_EXIST(255, "varoCommands.backpack.teamdoesntexist", "Das Team %colorcode%%team% &7existiert nicht."),
	VARO_COMMANDS_BACKPACK_CANT_SHOW_BACKPACK(256, "varoCommands.backpack.cantshowbackpack", "Der Rucksack kann dir daher nicht angezeigt werden."),
	VARO_COMMANDS_BACKPACK_NO_TEAM(257, "varoCommands.backpack.noteam", "Du befindest dich in keinem Team und hast deshalb keinen Teamrucksack."),
	VARO_COMMANDS_BACKPACK_CHOOSE_TYPE(258, "varoCommands.backpack.choosetype", "Bitte waehle aus, welchen Rucksack du oeffnen willst %colorcode%(Player/Team)&7."),
	VARO_COMMANDS_BACKPACK_NOT_ENABLED(259, "varoCommands.backpack.notenabled", "Die Rucksacke sind nicht aktiviert."),
	VARO_COMMANDS_CONFIG_RELOADED(261, "varoCommands.config.reloaded", "&7Alle %colorcode%Listen&7, %colorcode%Nachrichten &7und die %colorcode%Config &7wurden erfolgreich neu geladen."),
	VARO_COMMANDS_CONFIG_HELP_SET(262, "varoCommands.config.helpset", "%colorcode%/varo config &7set <key> <value>"),
	VARO_COMMANDS_CONFIG_HELP_SEARCH(263, "varoCommands.config.helpsearch", "%colorcode%/varo config &7search <key>"),
	VARO_COMMANDS_CONFIG_ENTRY_SET(264, "varoCommands.config.entryset", "&7Der Eintrag '%colorcode%%entry%&7' wurde erfolgreich auf '%colorcode%%value%&7'gesetzt."),
	VARO_COMMANDS_CONFIG_ENTRY_NOT_FOUND(265, "varoCommands.config.entrynotfound", "&7Der Eintrag '%colorcode%%entry%&7' wurde nicht gefunden."),
	VARO_COMMANDS_CONFIG_RESET(266, "varoCommands.config.reset", "&7Alle Eintraege wurden erfolgreich zurueckgesetzt."),
	VARO_COMMANDS_CONFIG_SEARCH_LIST_TITLE(267, "varoCommands.config.searchlisttitle", "&lFolgende Einstellungen wurden gefunden:"),
	VARO_COMMANDS_CONFIG_SEARCH_LIST_FORMAT(268, "varoCommands.config.searchlistformat", "%colorcode%%entry% &8- &7%description%"),
	VARO_COMMANDS_EXPORT_SUCCESSFULL(270, "varoCommands.export.players", "&7Alle Spieler wurden erfolgreich in '%colorcode%%file%&7' exportiert."),
	VARO_COMMANDS_DISCORD_PLEASE_RELOAD(272, "varoCommands.discord.pleasereload", "&7Der DiscordBot wurde beim Start nicht aufgesetzt, bitte reloade!"),
	VARO_COMMANDS_DISCORD_VERIFY_DISABLED(273, "varoCommands.discord.verifydisabled", "&7Das Verifzierungs-System wurde in der Config deaktiviert!"),
	VARO_COMMANDS_DISCORD_BOT_DISABLED(274, "varoCommands.discord.botdisabled", "&7Der DiscordBot wurde nicht aktiviert!"),
	VARO_COMMANDS_DISCORD_USER_NOT_FOUND(275, "varoCommands.discord.usernotfound", "&7User fuer diesen Spieler nicht gefunden!"),
	VARO_COMMANDS_DISCORD_GETLINK(276, "varoCommands.discord.getlink", "&7Der Discord Account von %colorcode%%player% heisst %colorcode%%user%&7 und die ID lautet %colorcode%%id%&7!"),
	VARO_COMMANDS_DISCORD_UNVERIFY(277, "varoCommands.discord.unverify", "&7Der Discord Account wurde erfolgreich von %colorcode%%player% &7entkoppelt!"),
	VARO_COMMANDS_INTRO_ALREADY_STARTED(279, "varoCommands.intro.alreadystarted", "&7Das Intro wurde bereits gestartet!"),
	VARO_COMMANDS_INTRO_GAME_ALREADY_STARTED(280, "varoCommands.intro.gamealreadystarted", "&7Das Spiel wurde bereits gestartet!"),
	VARO_COMMANDS_INTRO_STARTED(281, "varoCommands.intro.started", "&7Und los geht's!"),
	VARO_COMMANDS_PRESET_NOT_FOUND(283, "varoCommands.preset.notfound", "Das Preset %colorcode%%preset% &7wurde nicht gefunden."),
	VARO_COMMANDS_PRESET_LOADED(284, "varoCommands.preset.loaded", "Das Preset %colorcode%%preset% &7wurde &aerfolgreich &7geladen."),
	VARO_COMMANDS_PRESET_SAVED(285, "varoCommands.preset.saved", "Die aktuellen Einstellungen wurden &aerfolgreich &7als Preset %colorcode%%preset% &7gespeichert."),
	VARO_COMMANDS_PRESET_LIST(286, "varoCommands.preset.list", "&lListe aller Presets:"),
	VARO_COMMANDS_PRESET_HELP_LOAD(287, "varoCommands.preset.helploaded", "%colorcode%/varo preset &7load <PresetPath>"),
	VARO_COMMANDS_PRESET_HELP_SAVE(288, "varoCommands.preset.helpsave", "%colorcode%/varo preset &7save <PresetPath>"),
	VARO_COMMANDS_RANDOMTEAM_HELP(290, "varoCommands.randomteam.help", "%colorcode%/varo randomTeam <Teamgroesse>"),
	VARO_COMMANDS_RANDOMTEAM_TEAMSIZE_TOO_SMALL(291, "varoCommands.randomteam.teamsizetoosmall", "&7Die Teamgroesse muss mindestens 1 betragen."),
	VARO_COMMANDS_RANDOMTEAM_SORTED(292, "varoCommands.randomteam.sorted", "&7Alle Spieler ohne Team sind nun in %colorcode%%teamsize%er &7Teams!"),
	VARO_COMMANDS_RANDOMTEAM_NO_PARTNER(293, "varoCommands.randomteam.sorted", "&7Fuer dich konnten nicht genug Teampartner gefunden werden."),

	VARO_COMMANDS_RESTART_IN_LOBBY(295, "varoCommands.restart.inlobby", "&7Das Spiel befindet sich bereits in der Lobby-Phase!"),
	VARO_COMMANDS_RESTART_RESTARTED(296, "varoCommands.restart.restarted", "&7Das Spiel wurde neugestartet."),
	VARO_COMMANDS_SCOREBOARD_DEACTIVATED(298, "varoCommands.scoreboard.deactivated", "&7Scoreboard sind deaktiviert."),
	VARO_COMMANDS_SCOREBOARD_ENABLED(299, "varoCommands.scoreboard.enabled", "&7Du siehst nun das Scoreboard."),
	VARO_COMMANDS_SCOREBOARD_DISABLED(300, "varoCommands.scoreboard.disabled", "&7Du siehst nun das Scoreboard."),
	VARO_COMMANDS_SORT_HELP(302, "varoCommands.sort.help", "%colorcode%/varo sort"),
	VARO_COMMANDS_SORT_SORTED_WELL(303, "varoCommands.sort.sorted", "&7Alle Spieler wurden erfolgreich sortiert."),
	VARO_COMMANDS_SORT_NO_SPAWN_WITH_TEAM(304, "varoCommands.sort.nospawnwithteam", "&7Es konnte nicht fuer jeden Spieler ein Loch bei den Teampartnern gefunden werden!"),
	VARO_COMMANDS_SORT_NO_SPAWN(305, "varoCommands.sort.nospawn", "&7Es konnte nicht fuer jeden Spieler ein Loch gefunden werden!"),

	SPAWNS_SPAWN_NUMBER(309, "spawns.spawnNameTag.number", "&7Spawn %colorcode%%number%"),
	SPAWNS_SPAWN_PLAYER(310, "spawns.spawnNameTag.player", "&7Spawn von %colorcode%%player%"),

	OTHER_CONFIG(314, "other.configReload", "&7Die %colorcode%Config &7wurde neu geladen"),
	OTHER_PING(315, "other.ping", "&7Dein %colorcode%Ping &7betraegt: %colorcode%%other.ping%&7ms");

	private int messageId;
	private String path, message;

	private LanguageDE(int messageId, String path, String message) {
		this.messageId = messageId;
		this.path = path;
		this.message = message;
	}

	@Override
	public int getMessageID() {
		return messageId;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String getPath() {
		return path;
	}
}