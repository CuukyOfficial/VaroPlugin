package de.cuuky.varo.config.config;

import de.cuuky.varo.config.ConfigHandler;

public enum ConfigEntry {

	// MAIN
	PREFIX(ConfigSection.MAIN, "prefix", "&7[&3Varo&7] ", "Prefix, der im Chat bzw. vor\nden Nachrichten angezeigt wird."),
	PROJECT_NAME(ConfigSection.MAIN, "projectname", "Varo", "Name deines Projektes, der in den\nNachrichten, am Scoreboard, etc. steht."),
	PROJECTNAME_COLORCODE(ConfigSection.MAIN, "projectnameColorcode", "&3", "Dieser Farbcode ist der Maßgebende,\nder überall im Projekt verwendet wird.."),
	SUPPORT_PLUGIN_ADS(ConfigSection.MAIN, "supportPluginAds", false, "Werbung wird im Plugin mit eingebaut,was das Plugin,\nalso mich, supportet. Danke an alle, die das aktivieren :3"),
	SCOREBOARD(ConfigSection.MAIN, "scoreboard", true, "Ob das Scoreboard aktiviert sein soll.\nHinweis: das Scoreboard kannst du in\nder scoreboard.yml bearbeiten."),
	NAMETAGS(ConfigSection.MAIN, "nametags", true, "Ob das Plugin die Nametags über\nden Köpfen der Spieler verändern soll.\nHinweis: du kannst diese in der messages.yml einstellen."),
	PLAY_TIME(ConfigSection.MAIN, "playTime", 15, "Zeit in Minuten, wie lange die Spieler\npro Session auf dem Server spielen können.\nUnlimited = -1"),
	DO_DAILY_BACKUPS(ConfigSection.MAIN, "dailyBackups", true, "Es werden immer Backups um 'ResetHour' gemacht."),
	UNREGISTERED_PLAYER_JOIN(ConfigSection.MAIN, "unregisteredPlayerJoin", true, "Ob unregistrierte Spieler joinen dürfen."),
	RESET_SESSION_HOUR(ConfigSection.MAIN, "resetSessionHour", 1, "Um welche Uhrzeit (24h) der Server den\nSpieler neue Sessions etc. gibt"),

	// OFFLINEVILLAGER
	OFFLINEVILLAGER(ConfigSection.OFFLINEVILLAGER, "enableOfflineVillager", false, "Ob Villager, welche representativ für den Spieler während\nseiner Onlinezeit auf dem Server warten und gekillt werden können."),
	NO_SATIATION_REGENERATE(ConfigSection.OFFLINEVILLAGER, "noSatiationRegenerate", false, "Ob Spieler nicht durch Sättigung regenerieren\nkönnen sondern nur durch Gapple etc."),

	// BACKPACKS
	BACKPACK_ALLOW(ConfigSection.BACKPACKS, "allowBackpacks", false, "Erlaubt den Spielern den Zugriff\nauf ihr Backpack mit /varo bp"),
	BACKPACK_SIZE(ConfigSection.BACKPACKS, "backpackSize", 54, "Größe der Backpacks (Max = 54)"),
	BACKPACK_ALLOW_TEAMACCESS(ConfigSection.BACKPACKS, "allowTeamAccess", true, "Ob Teampartner mit /varo vp auf den\nRucksackes Mitspielers zugreifen dürfen"),
	BACKPACK_DROP_CONTENT_DEATH(ConfigSection.BACKPACKS, "dropContentOnDeath", true, "Ob der Inhalt des Rucksacks bei\nTod gedropptwerden soll."),

	// PROTECTIONS
	JOIN_PROTECTIONTIME(ConfigSection.PROTECTIONS, "joinProtectionTime", 10, "Länge der Schutzzeit in Sekunden beim Betreten des Servers.\nOff = -1"),
	CANWALK_PROTECTIONTIME(ConfigSection.PROTECTIONS, "canWalkOnJoinProtection", false, "Ob Spieler während der Joinschutzzeit laufen können."),
	STARTPERIOD_PROTECTIONTIME(ConfigSection.PROTECTIONS, "startperiodProtectiontime", -1, "Länge der Schutzzeit nach dem Start.\nOff = -1"),

	// SERVERLIST
	CHANGE_MOTD(ConfigSection.SERVER_LIST, "changeMotd", true, "Ob das Plugin die Motd verändern soll.\nHinweis: du kannst die Motd in der messages.yml ändern."),
	FAKE_MAX_SLOTS(ConfigSection.SERVER_LIST, "fakeMaxSlots", -1, "Setzt die maximalen Slots des Servers gefaked.\nOff = -1"),

	// STRIKE
	STRIKE_POST_RESET_HOUR(ConfigSection.STRIKE, "postAtResetHour", false, "Ob die Strikes erst um die ResetHour gepostet werden sollen"),
	STRIKE_BAN_AFTER_STRIKE_HOURS(ConfigSection.STRIKE, "banOnPostHours", -1, "Für wie viele Stunden die Spieler\nnach einem Strike gestriket werden"),
	STRIKE_BAN_AT_POST(ConfigSection.STRIKE, "banAtPost", true, "Ob der Spieler beim Posten des Strikes\num die oben genannte Zahl gebannt werden soll.\nSonst wird dieser beim Erhalten gebannt"),

	// TEAMS
	FRIENDLYFIRE(ConfigSection.TEAMS, "friendlyFire", false, "Zufügen von Schaden unter Teamkameraden."),
	TEAM_PLACE_SPAWN(ConfigSection.TEAMS, "teamPlaceSpawn", -1, "Anzahl an Spawnplätzen in einer Teambasis\nWenn angeschaltet (nicht -1) wird eine Lücke für fehlende Teammitglieder gelassen.\nAnschalten, wenn jedes Team einen eigenen Spawnplatz besitzt und es keinen großen Kreis gibt."),
	TRIGGER_FOR_GLOBAL(ConfigSection.TEAMS, "triggerForGlobal", false, "Wenn aktiviert, wird standardmäßig in den Teamchat geschrieben und mit dem Triggerbuchstaben am Anfang in den globalen Chat, ansonsten umgekehrt."),
	CHAT_TRIGGER(ConfigSection.TEAMS, "chatTrigger", "#", "Definiert den Buchstaben am Anfang einer\nNachricht, der den Teamchat auslöst."),
	TEAMREQUESTS(ConfigSection.TEAMS, "teamRequests", false, "Ob Spieler sich gegenseitig in Teams\nmit /tr einladen können.\nSehr gute Funktion für ODV's."),
	TEAMREQUEST_EXPIRETIME(ConfigSection.TEAMS, "teamRequestExpiretime", 30, "Die Zeit in Sekunden, nachdem eine Teamanfrage ablaufen soll."),
	TEAMREQUEST_MAXTEAMMEMBERS(ConfigSection.TEAMS, "teamRequestMaxTeamMembers", 2, "Anzahl an Teammitglieder pro Team."),
	TEAMREQUEST_MAXTEAMNAMELENGTH(ConfigSection.TEAMS, "teamRequestMaxTeamnameLength", 10, "Maximal Länge eines Teamnamens."),

	// JOINSYSTEMS
	IGNORE_JOINSYSTEMS_AS_OP(ConfigSection.JOIN_SYSTEMS, "ignoreJoinSystemsAsOP", true, "Ob OP-Spieler die JoinSysteme ignorieren."),
	SESSIONS_PER_DAY(ConfigSection.JOIN_SYSTEMS, "sessionsPerDay", 1, "ERSTES JOIN SYSTEM\nStellt ein, wie oft Spieler am Tag\nden Server regulär betreten dürfen."),
	JOIN_AFTER_HOURS(ConfigSection.JOIN_SYSTEMS, "joinAfterHours", -1, "ZWEITES JOIN SYSTEM\nStellt ein, nach wie vielen Stunden\nSpieler regulär wieder den Server betreten dürfen"),
	PRE_PRODUCE_SESSIONS(ConfigSection.JOIN_SYSTEMS, "preProduceSessions", 3, "FÜR BEIDE JOIN SYSTEME\nStellt ein, wie viele Folgen der Spieler zusätzlich zu\nden Regulären vorproduzieren darf."),
	CATCH_UP_SESSIONS(ConfigSection.JOIN_SYSTEMS, "catchUpSessions", false, "NUR FÜR ERSTES JOIN SYSTEM\nStellt ein, ob man verpasste Folgen nachholen darf."),
	ONLY_JOIN_BETWEEN_HOURS(ConfigSection.JOIN_SYSTEMS, "onlyJoinBetweenHours", false, "FÜR BEIDE JOIN SYSTEME\nStellt ein, ob Spieler nur zwischen\n2 unten festgelegten Zeiten joinen dürfen."),
	ONLY_JOIN_BETWEEN_HOURS_HOUR1(ConfigSection.JOIN_SYSTEMS, "onlyJoinBetweenHoursHour1", 14, "Erste Uhrzeit, zwischen welchen\ndie Spieler joinen dürfen."),
	ONLY_JOIN_BETWEEN_HOURS_HOUR2(ConfigSection.JOIN_SYSTEMS, "onlyJoinBetweenHoursHour2", 16, "Zweite Uhrzeit, zwischen welchen\ndie Spieler joinen dürfen."),
	KICK_AT_SERVER_CLOSE(ConfigSection.JOIN_SYSTEMS, "kickAtServerClose", false, "Kickt den Spieler, sobald er außerhalb\ndererlaubten Zeit auf dem Server ist."),
	MASS_RECORDING_TIME(ConfigSection.JOIN_SYSTEMS, "massRecordingTime", 15, "Die Länge der Massenaufnahme, in der alle joinen können."),

	// DEATH
	DEATH_SOUND(ConfigSection.DEATH, "deathSound", false, "Ob ein Withersound für alle abgespielt werden soll,\nsobald ein Spieler stirbt."),
	KICK_DELAY_AFTER_DEATH(ConfigSection.DEATH, "kickDelayAfterDeath", -1, "Zeit in Sekunden, nach der ein Spieler\nnach Tod gekickt wird.\nOff = -1"),
	STOP_SERVER_ON_WIN(ConfigSection.DEATH, "stopServerOnWin", -1, "Zeit in Sekunden, nachdem der Server nach\nWin eines Teams heruntergefahren wird."),
	KILLER_ADD_HEALTH_ON_KILL(ConfigSection.DEATH, "killerHealthAddOnKill", -1, "Anzahl halber Herzen, die der Killer nach Kill bekommt.\nOff = -1"),
	PLAYER_SPECTATE_AFTER_DEATH(ConfigSection.DEATH, "playerSpectateAfterDeath", false, "Ob ein Spieler nach seinem Tod Spectator wird."),
	TEAM_LIFES(ConfigSection.DEATH, "teamLifes", 1, "Wie viele Leben ein Team hat"),
	RESPAWN_PROTECTION(ConfigSection.DEATH, "respawnProtection", 120, "Wie lange in Sekunden Spieler\nnach Respawn geschützt sind"),
	ADD_TEAM_LIFE_ON_KILL(ConfigSection.DEATH, "addTeamLifesOnKill", -1, "Wie viele Leben ein Team bekommen soll,\nsobald es einen Spieler tötet."),

	// FINALE
	PLAYER_SPECTATE_IN_FINALE(ConfigSection.FINALE, "playerSpectateInFinale", true, "Ob die toten Spieler während des Finales spectaten dürfen."),
	BORDER_SIZE_IN_FINALE(ConfigSection.FINALE, "borderSizeInFinale", 300, "Auf diese Größe wird die Border beim Starten des Finales gestellt."),
	FINALE_PROTECTION_TIME(ConfigSection.FINALE, "finaleProtectionTime", 30, "Länge der Schutzzeit nachdem alle Spieler in die Mitte teleportiert werden."),

	// WORLD
	BLOCK_DESTROY_LOGGER(ConfigSection.WORLD, "blockDestroyLogger", true, "Loggt alle abgebauten Blöcke, die ihr\nunten eintragt unter 'oreLogger.yml'."),
	ALWAYS_TIME(ConfigSection.WORLD, "setAlwaysTime", 1000, "Setzt die Zeit auf dem Server,\ndie dann so stehen bleibt.\nHinweis: Nacht = 13000, Tag = 1000"),
	ALWAYS_TIME_USE_AFTER_START(ConfigSection.WORLD, "alwaysTimeUseAfterStart", false, "Ob die Zeit auch stehen bleiben soll,\nwenn das Projekt gestartet wurde."),
	RANDOM_CHEST_FILL_RADIUS(ConfigSection.WORLD, "randomChestFillRadius", -1, "In welchem Radius die Kisten um den\nSpawn mit den in der Config angegebenen\nItems befüllt werden sollen.\nOff = -1"),
	RANDOM_CHEST_MAX_ITEMS_PER_CHEST(ConfigSection.WORLD, "randomChestMaxItems", 5, "Wie viele Items in eine Kiste sollen."),
	SPAWN_PROTECTION_RADIUS(ConfigSection.WORLD, "spawnProtectionRadius", 0, "Radius, in dem die Spieler\nnicht am Spawn bauen können."),
	SET_NAMETAGS_OVER_SPAWN(ConfigSection.WORLD, "setNameTagOverSpawn", false, "Ob Nametags über den\nSpawns erscheinen sollen"),
	NAMETAG_SPAWN_HEIGHT(ConfigSection.WORLD, "nametagSpawnHeight", 3, "Wie hoch über den Spawns\ndie Nametags sein sollen"),
	BLOCK_USER_PORTALS(ConfigSection.WORLD, "blockUserPortals", true, "Ob Spieler nicht ihre eigenen\nPortale bauen können"),

	// START
	STARTCOUNTDOWN(ConfigSection.START, "startCountdown", 30, "Wie lange der Startcountdown\nbei Start in Sekunden ist."),
	CAN_MOVE_BEFORE_START(ConfigSection.START, "canMoveBeforeStart", false, "Ob die Spieler sich vor Start bewegen können."),
	DO_RANDOMTEAM_AT_START(ConfigSection.START, "doRandomTeamAtStart", -1, "Größe der Teams, in die die Teamlosen beim Start eingeordnet werden.\nAusgeschaltet = -1"),
	DO_SORT_AT_START(ConfigSection.START, "doSortAtStart", true, "Ob beim Start /sort ausgeführt werden soll."),
	REMOVE_PLAYERS_ARENT_AT_START(ConfigSection.START, "removePlayersArentAtStart", true, "Ob das Plugin alle Spieler, die nicht beim\nStart dabei sind vom Projekt entferenen soll."),
	SPAWN_TELEPORT_JOIN(ConfigSection.START, "spawnTeleportAtLobbyPhase", true, "Ob die Spieler, wenn\nfür sie ein Spawn gesetzt wurde auch in\ndiesem spawnen sollen, sobald sie joinen."),
	START_AT_PLAYERS(ConfigSection.START, "startAtPlayers", -1, "Startet das Projekt automatisch wenn die\nAnzahl der Online Spieler dieser entspricht."),

	// DISCONNECT
	DISCONNECT_PER_SESSION(ConfigSection.DISCONNECT, "maxDisconnectsPerSessions", 3, "Wie oft ein Spieler pro\nSession maximal disconnecten darf,\nbevor er bestraft wird.Off = -1"),
	STRIKE_ON_DISCONNECT(ConfigSection.DISCONNECT, "strikeOnMaxDisconnect", false, "Ob ein Spieler gestriket werden soll\nwenn zu oft disconnected wurde."),
	BAN_AFTER_DISCONNECT_MINUTES(ConfigSection.DISCONNECT, "banAfterDisconnectMinutes", -1, "Wenn ein Spieler disconnected,\nob er nach dieser Anzahl an Minuten entfernt werden soll.\nOff = -1"),
	NO_DISCONNECT_PING(ConfigSection.DISCONNECT, "noDisconnectPing", 200, "Ab welchem Ping ein Disconnect\nnicht mehr als einer zählt."),

	// COMBATLOG
	COMBATLOG_TIME(ConfigSection.COMBATLOG, "combatlogTime", 30, "Zeit, nachdem sich ein Spieler\nnach dem Kampf wieder ausloggen kann.\nOff = -1"),
	STRIKE_ON_COMBATLOG(ConfigSection.COMBATLOG, "strikeOnCombatlog", true, "Ob ein Spieler, wenn er sich in\nder oben genannten Zeit ausloggt,\ngestriket werden soll."),
	KILL_ON_COMBATLOG(ConfigSection.COMBATLOG, "killOnCombatlog", true, "Ob ein Spieler, wenn er\nsich in der oben genannten Zeit ausloggt,\ngetötet werden soll."),

	// BORDER
	BORDER_DEATH_DECREASE(ConfigSection.BORDER, "deathBorderDecrease.enabled", true, "Ob sich die Border bei Tod verringern soll"),
	BORDER_DEATH_DECREASE_SIZE(ConfigSection.BORDER, "deathBorderDecrease.size", 25, "Um wie viele Blöcke sich die\nBorder bei Tod verringern soll."),
	BORDER_DEATH_DECREASE_SPEED(ConfigSection.BORDER, "deathBorderDecrease.speed", 1, "Mit welcher Geschwindigkeit sich\ndie Border beiTod verringern soll."),

	BORDER_TIME_MINUTE_DECREASE(ConfigSection.BORDER, "minuteBorderDecrease.enabled", false, "Ob sich die Border nach Minuten verringern soll"),
	BORDER_TIME_MINUTE_DECREASE_MINUTES(ConfigSection.BORDER, "minuteBorderDecrease.minutes", 30, "Nach wie vielen Minuten sich\ndie Border verkleinern soll."),
	BORDER_TIME_MINUTE_DECREASE_SIZE(ConfigSection.BORDER, "minuteBorderDecrease.size", 50, "Um wieviel sich die Bordernach den oben\ngenannten Minuten verkleinern soll."),
	BORDER_TIME_MINUTE_DECREASE_SPEED(ConfigSection.BORDER, "minuteBorderDecrease.speed", 5, "Wie viele Blöcke pro Sekunde sichdie\nBorder nach Minuten verkleinern soll."),

	BORDER_TIME_DAY_DECREASE(ConfigSection.BORDER, "dayBorderDecrease.enabled", true, "Ob sich die Border nach Tagen verringern soll"),
	BORDER_TIME_DAY_DECREASE_DAYS(ConfigSection.BORDER, "dayBorderDecrease.days", 3, "Nach wie vielen Tagen sich\ndie Border verkleinern soll."),
	BORDER_TIME_DAY_DECREASE_SIZE(ConfigSection.BORDER, "dayBorderDecrease.size", 50, "Um wieviel sich die Bordernach den\noben genannten Tagen verkleinern soll."),
	BORDER_TIME_DAY_DECREASE_SPEED(ConfigSection.BORDER, "dayBorderDecrease.speed", 5, "Wie viele Blöcke pro Sekunde sich\ndie Border nach Tagen verkleinern soll."),

	MIN_BORDER_SIZE(ConfigSection.BORDER, "minBorderSize", 300, "Wie klein die Border maximal werden kann."),
	SHOW_DISTANCE_TO_BORDER(ConfigSection.BORDER, "showDistanceToBorder", false, "Ob die Distanz zur Border in der\nActionBar angezeigt werden soll."),
	DISTANCE_TO_BORDER_REQUIRED(ConfigSection.BORDER, "distanceToBorderRequired", -1, "Die Distanz, die der Spieler haben muss,\ndamit die Distanz angezeigt wird."),
	BORDER_DAMAGE(ConfigSection.BORDER, "borderDamage", 1, "Wie viel Schaden die Border\nin halben Herzen macht."),
	OUTSIDE_BORDER_SPAWN_TELEPORT(ConfigSection.BORDER, "outsideBorderSpawnTeleport", true, "Ob, wenn ein Spieler außerhalb der Border joint, er in die Mitte teleportiert werden soll."),

	// CHAT
	BLOCK_CHAT_ADS(ConfigSection.CHAT, "blockChatAds", true, "Wenn aktiviert, können keine Links in den öffentlichen Chat gepostet werden."),
	CAN_CHAT_BEFORE_START(ConfigSection.CHAT, "canChatBeforeStart", true, "Ob die Spieler vor Start chatten können."),
	CHAT_COOLDOWN_IN_SECONDS(ConfigSection.CHAT, "chatCooldownInSeconds", 3, "Der Cooldown der Spieler im Chat,\nbevor sie wieder eine Nachricht senden können.\nOff = -1"),
	CHAT_COOLDOWN_IF_STARTED(ConfigSection.CHAT, "chatCooldownIfStarted", false, "Ob der Chatcooldown auch aktiviert sein\\nsoll wenn das Projekt gestartet wurde."),

	// ACTIVITY
	BLOODLUST_DAYS(ConfigSection.ACTIVITY, "noBloodlustDays", -1, "Nach wie vielen Tagen ohne Gegnerkontakt\nder Spieler gemeldet werden soll.\nOff = -1"),
	STRIKE_ON_BLOODLUST(ConfigSection.ACTIVITY, "strikeOnBloodlust", false, "Ob der Spieler nach den oben\ngenannten Tagen ohne Gegnerkontakt\ngestriket werden soll."),
	NO_ACTIVITY_DAYS(ConfigSection.ACTIVITY, "noActivityDays", -1, "Nach wie vielen Tagen ohne Aktiviät auf dem\nServer der Spieler gemeldet werden soll.\nOff = -1"),
	STRIKE_ON_NO_ACTIVITY(ConfigSection.ACTIVITY, "strikeOnNoActivity", false, "Ob der Spieler nach den oben genannten Tagen\nohne Aktivität auf dem Servergestriket werden soll."),
	POST_COORDS_DAYS(ConfigSection.ACTIVITY, "postCoordsDays", -1, "Postet nach den genannten Tagen\nvon allen Spielern die Koordinatenum die Uhrzeit,\num der auch Sessions etc. geprüft werden"),

	// REPORT
	REPORTSYSTEM_ENABLED(ConfigSection.REPORT, "enabled", true, "Ob das Report-System angeschaltet sein soll."),
	REPORT_SEND_DELAY(ConfigSection.REPORT, "reportDelay", 30, "Zeit in Sekunden, die ein Spieler warten muss,\nbevor er einen neuen Spieler reporten kann.\nOff = -1"),
	LOG_REPORTS(ConfigSection.REPORT, "logReports", true, "Ob alle Reports in der reports.yml\nfestgehalten werden sollen."),
	REPORT_STAFF_MEMBER(ConfigSection.REPORT, "reportStaffMember", true, "Ob Spieler mit der Permission\n'varo.report' reportet werden können."),

	// YOUTUBE
	YOUTUBE_ENABLED(ConfigSection.YOUTUBE, "enabled", false, "Checkt jeden Tag bei den Spielern,\ndie einen YouTube Link registriert haben,\nnach den Uploads"),
	YOUTUBE_SET_OWN_LINK(ConfigSection.YOUTUBE, "setOwnLink", true, "Ob die Spieler sich selbst den\nYouTube-Link per /yt setzen dürfen"),
	YOUTUBE_VIDEO_IDENTIFIER(ConfigSection.YOUTUBE, "videoIdentifier", "Varo", "Was die Videotitel enthalten\nmüssen, um als Varovideo zu gelten."),

	// DISCORDBOT
	DISCORDBOT_ENABLED(ConfigSection.DISCORD, "discordBotEnabled", false, "Ob der DiscordBot für Events aktiviert werden soll.\nHinweis: bitte für diesen Informationen unten ausfüllen."),
	DISCORDBOT_TOKEN(ConfigSection.DISCORD, "botToken", "ENTER TOKEN HERE", "Gib hier den Token an, welchen du auf\nder Bot Seite und 'create bot user' bekommst."),
	DISCORDBOT_EVENTCHANNELID(ConfigSection.DISCORD, "eventChannelID", -1, "Gib hier die ChannelID des Channels an,\nin welchen der Bot Events posten soll.\nRechtsklick auf den Channel -> 'Copy ChannelID'\nWenn Option nicht vorhanden, schalte\n'developer options' in den Einstellungen von Discord ein."),

	DISCORDBOT_EVENT_STRIKE(ConfigSection.DISCORD, "eventChannel.strike", -1, "ID's des Channels, wo die Strikes gepostet werden.\n-1= EventChannelID wird genutzt"),
	DISCORDBOT_EVENT_JOIN_LEAVE(ConfigSection.DISCORD, "eventChannel.joinLeave", -1, "ID's des Channels, wo die Joins/Leaves gepostet werden.\n-1= EventChannelID wird genutzt"),
	DISCORDBOT_EVENT_WIN(ConfigSection.DISCORD, "eventChannel.win", -1, "ID's des Channels, wo die Winnachricht gepostet wird.\n-1= EventChannelID wird genutzt"),
	DISCORDBOT_EVENT_KILL(ConfigSection.DISCORD, "eventChannel.kill", -1, "ID's des Channels, wo die Kills gepostet werden.\n-1= EventChannelID wird genutzt"),
	DISCORDBOT_EVENT_DEATH(ConfigSection.DISCORD, "eventChannel.death", -1, "ID's des Channels, wo die Tode gepostet werden.\n-1= EventChannelID wird genutzt"),
	DISCORDBOT_EVENT_ALERT(ConfigSection.DISCORD, "eventChannel.alert", -1, "ID's des Channels, wo die Benachrichtigungen gepostet werden.\n-1= EventChannelID wird genutzt"),
	DISCORDBOT_EVENT_YOUTUBE(ConfigSection.DISCORD, "eventChannel.youtube", -1, "ID's des Channels, wo die YT-Videos gepostet werden.\n-1= EventChannelID wird genutzt"),

	DISCORDBOT_SERVERID(ConfigSection.DISCORD, "serverGuildID", -1, "Gib hier die ServerID deines Servers an.\nHinweis: Vorgangsweise, um die ID zu bekommen wie beim Channel."),
	DISCORDBOT_VERIFYSYSTEM(ConfigSection.DISCORD, "verifySystem", false, "Ob das Verify System aktiviert werden soll.\nDieses lässt nur mit dem Discord\nverifizierte Spieler auf den MC-Server."),
	DISCORDBOT_USE_VERIFYSTSTEM_MYSQL(ConfigSection.DISCORD, "verifySystemMySQL", false, "Ob für die Speicherung der BotRegister\neine MySQL Datenbank genutzt werden soll"),
	DISCORDBOT_VERIFY_HOST(ConfigSection.DISCORD, "mysql_verify.mysql_host", "HOST_HERE", "MySQL Host, zu welchem das Plugin sich verbinden soll"),
	DISCORDBOT_VERIFY_USER(ConfigSection.DISCORD, "mysql_verify.mysql_user", "USER_HERE", "MySQL Nutzer, welcher auf die Datenbank zugreifen soll"),
	DISCORDBOT_VERIFY_PASSWORD(ConfigSection.DISCORD, "mysql_verify.mysql_password", "PASSWORD_HERE", "Passwort für MySQL Nutzer,\nwelcher auf die Datenbank zugreifen soll"),
	DISCORDBOT_VERIFY_DATABASE(ConfigSection.DISCORD, "mysql_verify.mysql_database", "DATABASE_HERE", "Datenbank, wo die BotRegister\ngespeichert werden sollen"),
	DISCORDBOT_REGISTERCHANNELID(ConfigSection.DISCORD, "registerChannelID", -1, "Gib hier die Channel ID des #verify - Channels\nan, wo sich die User verifizieren können."),
	DISCORDBOT_ANNOUNCEMENT_CHANNELID(ConfigSection.DISCORD, "announcementChannelID", -1, "Gib hier den Channel an,\nin dem Nachrichten vom AutoStart geschrieben werden.\nBeispiel: Varo startet in ... Minuten."),
	DISCORDBOT_ANNOUNCEMENT_PING_ROLEID(ConfigSection.DISCORD, "announcementPingRoleID", -1, "Gib hier die ID der Rolle ein, welche\nbei Nachrichtenauf Discord gepingt werden sollen.\nHinweis: -1 = everyone"),
	DISCORDBOT_RESULT_CHANNELID(ConfigSection.DISCORD, "resultChannelID", -1, "Gib hier die Channel ID an, in der später\ndie Logs und die Gewinner gepostet werden sollen."),
	DISCORDBOT_ADD_POKAL_ON_WIN(ConfigSection.DISCORD, "addPokalOnWin", true, "Ob den Gewinnern Pokale hinter den\nNamen gesetzt werden sollen."),
	DISCORDBOT_SET_TEAM_AS_GROUP(ConfigSection.DISCORD, "setTeamAsGroup", false, "Ob die Spieler, die ein Team bekommen,\ndiesen auch als Gruppe im Discord bekommen sollen."),
	DISCORDBOT_INVITELINK(ConfigSection.DISCORD, "inviteLink", "ENTER LINK HERE", "Stelle hier deinen Link zum Discord ein"),
	DISCORDBOT_COMMANDTRIGGER(ConfigSection.DISCORD, "commandTrigger", "!varo ", "Stelle hier ein, womit man die\nVaro Commands Triggern kann.\nBeispiel: '!varo remaining'"),
	DISCORDBOT_MESSAGE_RANDOM_COLOR(ConfigSection.DISCORD, "randomMessageColor", false, "Ob die Nachrichten eine zufällige Farbe haben sollen"),
	DISCORDBOT_GAMESTATE(ConfigSection.DISCORD, "gameState", "Varo | Plugin by Cuuky - Contributors: Korne127", "Stelle hier ein, was der Bot\nim Spiel als Name haben soll."),

	// TELEGRAM
	TELEGRAM_ENABLED(ConfigSection.TELEGRAM, "telegrambotEnabled", false, "Ob der Telegrambot aktiviert werden soll."),
	TELEGRAM_BOT_TOKEN(ConfigSection.TELEGRAM, "botToken", "ENTER TOKEN HERE", "Setzt den Bot Token des Telegrambots"),
	TELEGRAM_EVENT_CHAT_ID(ConfigSection.TELEGRAM, "eventChatId", -1, "In diesen Chat werden alle Events gepostet."),
	TELEGRAM_VIDEOS_CHAT_ID(ConfigSection.TELEGRAM, "videosChatId", -1, "Hier kannst du die ID des Chats angeben, wo\ndie Videos der User gepostet werden sollen."),

	// AUTOSETUP
	AUTOSETUP_ENABLED(ConfigSection.AUTOSETUP, "enabled", false, "Wenn Autosetup aktiviert ist, werden beim\nStart des Servers alle Spawns automatisch gesetzt und\noptional ein Autostart eingerichtet."),
	AUTOSETUP_BORDER(ConfigSection.AUTOSETUP, "border", 2000, "Wie groß die Border beim\nAutoSetup gesetzt werden soll"),
	AUTOSETUP_TIME_HOUR(ConfigSection.AUTOSETUP, "autostart.time.hour", -1, "Um welche Zeit der Stunde der\nAutoStart gesetzt werden soll"),
	AUTOSETUP_TIME_MINUTE(ConfigSection.AUTOSETUP, "autostart.time.minute", -1, "Um welche Zeit der Minute der\nAutoStart gesetzt werden soll"),
	AUTOSETUP_PORTAL_ENABLED(ConfigSection.AUTOSETUP, "portal", true, "Ob ein Portal gespawnt werden soll"),
	AUTOSETUP_PORTAL_WIDTH(ConfigSection.AUTOSETUP, "portal.width", 4, "Breite des gespawnten Portals"),
	AUTOSETUP_PORTAL_HEIGHT(ConfigSection.AUTOSETUP, "portal.height", 5, "Höhe des gespawnten Portals"),
	AUTOSETUP_LOBBY_ENABLED(ConfigSection.AUTOSETUP, "lobby", true, "Ob eine Lobby beim AutoSetup gespawnt werden soll"),
	AUTOSETUP_LOBBY_SCHEMATIC(ConfigSection.AUTOSETUP, "lobby.schematic", "plugins/Varo/schematics/lobby.schematic", "Schreibe hier den Pfad deiner Lobby-Schematic\nhin, die gepastet werden soll.\nHinweis: WorldEdit benötigt"),
	AUTOSETUP_LOBBY_SIZE(ConfigSection.AUTOSETUP, "lobby.size", 25, "Größe der Lobby, die gespawnt werden soll"),
	AUTOSETUP_LOBBY_HEIGHT(ConfigSection.AUTOSETUP, "lobby.height", 10, "Höhe der Lobby, die gespawnt werden soll"),
	AUTOSETUP_SPAWNS_RADIUS(ConfigSection.AUTOSETUP, "spawns.radius", 30, "In welchem Radius die Löcher\ngeneriert werden sollen"),
	AUTOSETUP_SPAWNS_AMOUNT(ConfigSection.AUTOSETUP, "spawns.amount", 40, "Zu welcher Anzahl die Löcher\ngeneriert werden sollen"),
	AUTOSETUP_SPAWNS_BLOCKID(ConfigSection.AUTOSETUP, "spawns.block.material", "STONE_BRICK_SLAB", "Welche Block-ID der Halftstep am Spawn haben soll"),
	AUTOSETUP_SPAWNS_SIDEBLOCKID(ConfigSection.AUTOSETUP, "spawns.sideblock.material", "GRASS", "Welche Block-ID der Block,\nden man abbaut haben soll"),

	// GUI
	GUI_INVENTORY_ANIMATIONS(ConfigSection.GUI, "guiInventoryAnimations", false, "Bestimmt, ob beim Klicken in der Gui eine Animation abgespielt wird."),
	GUI_FILL_INVENTORY(ConfigSection.GUI, "guiFillInventory", true, "Bestimmt, ob die leeren Felder der Gui mit Kacheln aufgefüllt werden."),

	// OTHER
	PLAYER_CHEST_LIMIT(ConfigSection.OTHER, "playerChestLimit", 2, "Wie viele Chests ein Team\nregistrieren darf.\nOff = 0, Unendlich = -1"),
	PLAYER_FURNACE_LIMIT(ConfigSection.OTHER, "playerFurnaceLimit", -1, "Wie viele Furnaces ein\nSpieler registrieren darf.\nOff = 0, Undendlich = -1"),
	NO_KICK_DISTANCE(ConfigSection.OTHER, "noKickDistance", 30, "In welcher Distanz zum Gegner\nein Spieler nicht gekickt wird.\nOff = 0"),
	SHOW_TIME_IN_ACTIONBAR(ConfigSection.OTHER, "showTimeInActionbar", false, "Ob die verbleibende Sessionzeit in\nder Actionbar angezeigt werden soll."),
	DISABLE_LABYMOD_FUNCTIONS(ConfigSection.OTHER, "disableLabyModFunctions", false, "Ob die Addons von LabyMod beim Spieler\ndeaktviert werden sollen.\nFür diese Funktion wird dieses Plugin benötigt:\nhttps://www.spigotmc.org/resources/52423/"),
	KICK_LABYMOD_PLAYER(ConfigSection.OTHER, "kickLabyModPlayer", false, "Ob Spieler, die LabyMod nutzen,\ngekickt werden sollen.\nFür diese Funktion wird dieses Plugin benötigt:\nhttps://www.spigotmc.org/resources/52423/"),
	ONLY_LABYMOD_PLAYER(ConfigSection.OTHER, "onlyLabyModPlayer", false, "Ob nur Spieler mit LabyMod joinen dürfen.\nFür diese Funktion wird dieses Plugin benötigt:\nhttps://www.spigotmc.org/resources/52423/"),
	MINIMAL_SPECTATOR_HEIGHT(ConfigSection.OTHER, "minimalSpectatorHeight", 70, "Wie tief die Spectator maximal fliegen können.\nOff = 0"),
	BROADCAST_INTERVAL_IN_SECONDS(ConfigSection.OTHER, "broadcastIntervalInSeconds", -1, "Interval in Sekunden, in welcher der\nBroadcaster eine Nachricht postet.\nHinweis: Die Nachrichten kannst du in der broadcasts.yml einstellen.\nOff = -1"),
	REMOVE_HIT_COOLDOWN(ConfigSection.OTHER, "removeHitDelay", false, "Entfernt den 1.9+ Hit delay"),
	ALLOW_TROLL(ConfigSection.OTHER, "allowTrolling", false, "Ob die Troll Module erlaubt\nsein sollen (/varo troll)"),
	DEBUG_OPTIONS(ConfigSection.OTHER, "debugOptions", false, "Ob Debug Funktionen verfügbar sein sollen.\nVorsicht: Mit Bedacht oder nur\nauf Anweisung nutzen!");

	private String path;
	private String description;
	private Object value;
	private Object defaultValue;
	private ConfigSection section;

	ConfigEntry(ConfigSection section, String path, Object value, String description) {
		this.section = section;
		this.path = path;
		this.value = value;
		this.defaultValue = value;
		this.description = description;
	}

	public static ConfigEntry getEntryByPath(String path) {
		for (ConfigEntry entry : ConfigEntry.values()) {
			if (!entry.getFullPath().equals(path))
				continue;

			return entry;
		}
		return null;
	}

	private void sendFalseCast() {
		throw new IllegalArgumentException("'" + value + "' (" + value.getClass().getName() + ") is not applyable for " + defaultValue.getClass() + " for entry " + getFullPath());
	}

	public void setValue(Object value, boolean save) {
		this.value = value;

		if (save)
			save();
	}

	private void save() {
		ConfigHandler.getInstance().getConfigCfg().set(section.getPath() + path, value);
		ConfigHandler.getInstance().saveConfig();
	}

	public Object getValue() {
		return this.value;
	}

	public String getValueAsString() {
		try {
			return String.valueOf(this.value).replaceAll("&", "§");
		} catch (Exception e) {
			sendFalseCast();
		}

		return (String) defaultValue;
	}

	public int getValueAsInt() {
		try {
			return (int) this.value;
		} catch (Exception e) {
			sendFalseCast();
		}

		return (int) defaultValue;
	}

	public double getValueAsDouble() {
		try {
			return (double) this.value;
		} catch (Exception e) {
			sendFalseCast();
		}

		return (double) defaultValue;
	}

	public boolean getValueAsBoolean() {
		try {
			return (boolean) this.value;
		} catch (Exception e) {
			sendFalseCast();
		}

		return (boolean) defaultValue;
	}

	public long getValueAsLong() {
		try {
			return (long) this.value;
		} catch (Exception e) {
			sendFalseCast();
		}

		return (long) defaultValue;
	}

	public ConfigSection getSection() {
		return section;
	}

	public String getPath() {
		return path;
	}

	public String getFullPath() {
		return section.getPath() + path;
	}

	public String[] getDescription() {
		return description.split("\n");
	}

	public Object getDefaultValue() {
		return this.defaultValue;
	}

	public boolean isIntActivated() {
		return getValueAsInt() > -1;
	}

	public String getName() {
		return path;
	}
}