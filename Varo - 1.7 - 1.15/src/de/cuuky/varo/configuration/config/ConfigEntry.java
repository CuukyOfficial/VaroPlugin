package de.cuuky.varo.configuration.config;

import org.bukkit.Bukkit;

import de.cuuky.varo.Main;

public enum ConfigEntry {

	ADD_TEAM_LIFE_ON_KILL(ConfigEntrySection.DEATH, "addTeamLifesOnKill", -1, "Wie viele Leben ein Team bekommen soll,\nsobald es einen Spieler toetet."),
	ALWAYS_TIME(ConfigEntrySection.WORLD, "setAlwaysTime", 1000, "Setzt die Zeit auf dem Server,\ndie dann so stehen bleibt.\nHinweis: Nacht = 13000, Tag = 1000", true),
	ALWAYS_TIME_USE_AFTER_START(ConfigEntrySection.WORLD, "alwaysTimeUseAfterStart", false, "Ob die Zeit auch stehen bleiben soll,\nwenn das Projekt gestartet wurde."),
	AUTOSETUP_BORDER(ConfigEntrySection.AUTOSETUP, "border", 2000, "Wie gross die Border beim\nAutoSetup gesetzt werden soll"),

	// AUTOSETUP
	AUTOSETUP_ENABLED(ConfigEntrySection.AUTOSETUP, "enabled", false, "Wenn Autosetup aktiviert ist, werden beim\nStart des Servers alle Spawns automatisch gesetzt und\noptional ein Autostart eingerichtet."),
	AUTOSETUP_LOBBY_ENABLED(ConfigEntrySection.AUTOSETUP, "lobby", true, "Ob eine Lobby beim AutoSetup gespawnt werden soll"),
	AUTOSETUP_LOBBY_HEIGHT(ConfigEntrySection.AUTOSETUP, "lobby.height", 10, "Hoehe der Lobby, die gespawnt werden soll"),
	AUTOSETUP_LOBBY_SCHEMATIC(ConfigEntrySection.AUTOSETUP, "lobby.schematic", "plugins/Varo/schematics/lobby.schematic", "Schreibe hier den Pfad deiner Lobby-Schematic\nhin, die gepastet werden soll.\nHinweis: WorldEdit benoetigt"),
	AUTOSETUP_LOBBY_SIZE(ConfigEntrySection.AUTOSETUP, "lobby.size", 25, "Groesse der Lobby, die gespawnt werden soll"),
	AUTOSETUP_PORTAL_ENABLED(ConfigEntrySection.AUTOSETUP, "portal", true, "Ob ein Portal gespawnt werden soll"),

	AUTOSETUP_PORTAL_HEIGHT(ConfigEntrySection.AUTOSETUP, "portal.height", 5, "Hoehe des gespawnten Portals"),
	AUTOSETUP_PORTAL_WIDTH(ConfigEntrySection.AUTOSETUP, "portal.width", 4, "Breite des gespawnten Portals"),

	AUTOSETUP_SPAWNS_AMOUNT(ConfigEntrySection.AUTOSETUP, "spawns.amount", 40, "Zu welcher Anzahl die Loecher\ngeneriert werden sollen"),
	AUTOSETUP_SPAWNS_BLOCKID(ConfigEntrySection.AUTOSETUP, "spawns.block.material", "STONE_BRICK_SLAB", "Welche Block-ID der Halftstep am Spawn haben soll"),
	AUTOSETUP_SPAWNS_RADIUS(ConfigEntrySection.AUTOSETUP, "spawns.radius", 30, "In welchem Radius die Loecher\ngeneriert werden sollen"),
	AUTOSETUP_SPAWNS_SIDEBLOCKID(ConfigEntrySection.AUTOSETUP, "spawns.sideblock.material", "GRASS", "Welche Block-ID der Block,\nden man abbaut haben soll"),
	AUTOSETUP_TIME_HOUR(ConfigEntrySection.AUTOSETUP, "autostart.time.hour", -1, "Um welche Zeit der Stunde der\nAutoStart gesetzt werden soll"),
	AUTOSETUP_TIME_MINUTE(ConfigEntrySection.AUTOSETUP, "autostart.time.minute", -1, "Um welche Zeit der Minute der\nAutoStart gesetzt werden soll"),

	BACKPACK_PLAYER_DROP_ON_DEATH(ConfigEntrySection.BACKPACKS, "backpackPlayerDropOnDeath", true, "Ob der Inhalt des Spieler-Rucksacks beim Tod des Spielers gedroppt werden soll."),

	// BACKPACKS
	BACKPACK_PLAYER_ENABLED(ConfigEntrySection.BACKPACKS, "backpackPlayerEnabled", false, "Wenn dies aktiviert ist, haben Spieler einen eigenen Rucksack,\nauf den sie mit /varo bp zugreifen koennen.\nDieser wird pro Spieler und nicht pro Team gespeichert."),
	BACKPACK_PLAYER_SIZE(ConfigEntrySection.BACKPACKS, "backpackPlayerSize", 54, "Groesse des Spieler-Rucksacks (Max = 54)"),

	BACKPACK_TEAM_DROP_ON_DEATH(ConfigEntrySection.BACKPACKS, "backpackTeamDropOnDeath", true, "Ob der Inhalt des Team-Rucksacks beim Tod des letzten Teammitglieds gedroppt werden soll."),
	BACKPACK_TEAM_ENABLED(ConfigEntrySection.BACKPACKS, "backpackTeamEnabled", false, "Wenn dies aktiviert ist, haben Teams einen eigenen Rucksack,\nauf den sie mit /varo bp zugreifen koennen.\nDieser wird pro Team und nicht pro Spieler gespeichert."),

	BACKPACK_TEAM_SIZE(ConfigEntrySection.BACKPACKS, "backpackTeamSize", 54, "Groesse des Team-Rucksacks (Max = 54)"),
	BAN_AFTER_DISCONNECT_MINUTES(ConfigEntrySection.DISCONNECT, "banAfterDisconnectMinutes", -1, "Wenn ein Spieler disconnected,\nob er nach dieser Anzahl an Minuten entfernt werden soll.\nOff = -1"),

	// CHAT
	BLOCK_CHAT_ADS(ConfigEntrySection.CHAT, "blockChatAds", true, "Wenn aktiviert, koennen keine Links in den oeffentlichen Chat gepostet werden."),

	// WORLD
	BLOCK_DESTROY_LOGGER(ConfigEntrySection.WORLD, "blockDestroyLogger", true, "Loggt alle abgebauten Bloecke, die ihr\nunten eintragt unter 'oreLogger.yml'", true),
	BLOCK_USER_PORTALS(ConfigEntrySection.WORLD, "blockUserPortals", true, "Ob Spieler nicht ihre eigenen\nPortale bauen koennen"),

	// ACTIVITY
	BLOODLUST_DAYS(ConfigEntrySection.ACTIVITY, "noBloodlustDays", -1, "Nach wie vielen Tagen ohne Gegnerkontakt\nder Spieler gemeldet werden soll.\nOff = -1"),
	BORDER_DAMAGE(ConfigEntrySection.BORDER, "borderDamage", 1, "Wie viel Schaden die Border\nin halben Herzen macht."),

	// BORDER
	BORDER_DEATH_DECREASE(ConfigEntrySection.BORDER, "deathBorderDecrease.enabled", true, "Ob sich die Border bei Tod verringern soll"),
	BORDER_DEATH_DECREASE_SIZE(ConfigEntrySection.BORDER, "deathBorderDecrease.size", 25, "Um wie viele Bloecke sich die\nBorder bei Tod verringern soll."),
	BORDER_DEATH_DECREASE_SPEED(ConfigEntrySection.BORDER, "deathBorderDecrease.speed", 1, "Mit welcher Geschwindigkeit sich\ndie Border beiTod verringern soll."),
	BORDER_SIZE_IN_FINALE(ConfigEntrySection.FINALE, "borderSizeInFinale", 300, "Auf diese Groesse wird die Border beim Starten des Finales gestellt."),

	BORDER_TIME_DAY_DECREASE(ConfigEntrySection.BORDER, "dayBorderDecrease.enabled", true, "Ob sich die Border nach Tagen verringern soll"),
	BORDER_TIME_DAY_DECREASE_DAYS(ConfigEntrySection.BORDER, "dayBorderDecrease.days", 3, "Nach wie vielen Tagen sich\ndie Border verkleinern soll."),
	BORDER_TIME_DAY_DECREASE_SIZE(ConfigEntrySection.BORDER, "dayBorderDecrease.size", 50, "Um wieviel sich die Bordernach den\noben genannten Tagen verkleinern soll."),
	BORDER_TIME_DAY_DECREASE_SPEED(ConfigEntrySection.BORDER, "dayBorderDecrease.speed", 5, "Wie viele Bloecke pro Sekunde sich\ndie Border nach Tagen verkleinern soll."),
	BORDER_TIME_MINUTE_DECREASE(ConfigEntrySection.BORDER, "minuteBorderDecrease.enabled", false, "Ob sich die Border nach Minuten verringern soll"),
	BORDER_TIME_MINUTE_DECREASE_MINUTES(ConfigEntrySection.BORDER, "minuteBorderDecrease.minutes", 30, "Nach wie vielen Minuten sich\ndie Border verkleinern soll."),
	BORDER_TIME_MINUTE_DECREASE_SIZE(ConfigEntrySection.BORDER, "minuteBorderDecrease.size", 50, "Um wieviel sich die Bordernach den oben\ngenannten Minuten verkleinern soll."),
	BORDER_TIME_MINUTE_DECREASE_SPEED(ConfigEntrySection.BORDER, "minuteBorderDecrease.speed", 5, "Wie viele Bloecke pro Sekunde sichdie\nBorder nach Minuten verkleinern soll."),
	BORDER_TIME_MINUTE_BC_INTERVAL(ConfigEntrySection.BORDER, "minuteBorderDecrease.bcInterval", 300, "In welchen Sekundenabstaenden die Zeit bis zur Verkleinerung\ngebroacastet werden soll"),
	BROADCAST_INTERVAL_IN_SECONDS(ConfigEntrySection.OTHER, "broadcastIntervalInSeconds", -1, "Interval in Sekunden, in welcher der\nBroadcaster eine Nachricht postet.\nHinweis: Die Nachrichten kannst du in der broadcasts.yml einstellen.\nOff = -1"),
	CAN_CHAT_BEFORE_START(ConfigEntrySection.CHAT, "canChatBeforeStart", true, "Ob die Spieler vor Start chatten koennen."),

	CAN_MOVE_BEFORE_START(ConfigEntrySection.START, "canMoveBeforeStart", false, "Ob die Spieler sich vor Start bewegen koennen."),
	CANWALK_PROTECTIONTIME(ConfigEntrySection.PROTECTIONS, "canWalkOnJoinProtection", false, "Ob Spieler waehrend der Joinschutzzeit laufen koennen."),
	CATCH_UP_SESSIONS(ConfigEntrySection.JOIN_SYSTEMS, "catchUpSessions", false, "NUR FÜR ERSTES JOIN SYSTEM\nStellt ein, ob man verpasste Folgen nachholen darf."),

	// SERVERLIST
	CHANGE_MOTD(ConfigEntrySection.SERVER_LIST, "changeMotd", true, "Ob das Plugin die Motd veraendern soll.\nHinweis: du kannst die Motd in der messages.yml aendern."),
	CHAT_COOLDOWN_IF_STARTED(ConfigEntrySection.CHAT, "chatCooldownIfStarted", false, "Ob der Chatcooldown auch aktiviert sein\\nsoll wenn das Projekt gestartet wurde."),
	CHAT_COOLDOWN_IN_SECONDS(ConfigEntrySection.CHAT, "chatCooldownInSeconds", 3, "Der Cooldown der Spieler im Chat,\nbevor sie wieder eine Nachricht senden koennen.\nOff = -1"),
	CHAT_TRIGGER(ConfigEntrySection.TEAMS, "chatTrigger", "#", "Definiert den Buchstaben am Anfang einer\nNachricht, der den Teamchat ausloest."),

	// COMBATLOG
	COMBATLOG_TIME(ConfigEntrySection.COMBATLOG, "combatlogTime", 30, "Zeit, nachdem sich ein Spieler\nnach dem Kampf wieder ausloggen kann.\nOff = -1"),

	// DEATH
	DEATH_SOUND(ConfigEntrySection.DEATH, "deathSound", false, "Ob ein Withersound fuer alle abgespielt werden soll,\nsobald ein Spieler stirbt", true),
	DEBUG_OPTIONS(ConfigEntrySection.OTHER, "debugOptions", false, "Ob Debug Funktionen verfuegbar sein sollen.\nVorsicht: Mit Bedacht oder nur\nauf Anweisung nutzen!"),
	DISABLE_LABYMOD_FUNCTIONS(ConfigEntrySection.OTHER, "disableLabyModFunctions", false, "Ob die Addons von LabyMod beim Spieler\ndeaktviert werden sollen.\nFuer diese Funktion wird dieses Plugin benoetigt:\nhttps://www.spigotmc.org/resources/52423/"),

	// DISCONNECT
	DISCONNECT_PER_SESSION(ConfigEntrySection.DISCONNECT, "maxDisconnectsPerSessions", 3, "Wie oft ein Spieler pro\nSession maximal disconnecten darf,\nbevor er bestraft wird.Off = -1"),
	DISCORDBOT_ADD_POKAL_ON_WIN(ConfigEntrySection.DISCORD, "addPokalOnWin", true, "Ob den Gewinnern Pokale hinter den\nNamen gesetzt werden sollen."),
	DISCORDBOT_ANNOUNCEMENT_CHANNELID(ConfigEntrySection.DISCORD, "announcementChannelID", -1, "Gib hier den Channel an,\nin dem Nachrichten vom AutoStart geschrieben werden.\nBeispiel: Varo startet in ... Minuten."),
	DISCORDBOT_ANNOUNCEMENT_PING_ROLEID(ConfigEntrySection.DISCORD, "announcementPingRoleID", -1, "Gib hier die ID der Rolle ein, welche\nbei Nachrichtenauf Discord gepingt werden sollen.\nHinweis: -1 = everyone"),
	DISCORDBOT_COMMANDTRIGGER(ConfigEntrySection.DISCORD, "commandTrigger", "!varo ", "Stelle hier ein, womit man die\nVaro Commands Triggern kann.\nBeispiel: '!varo remaining'"),

	// DISCORDBOT
	DISCORDBOT_ENABLED(ConfigEntrySection.DISCORD, "discordBotEnabled", false, "Ob der DiscordBot fuer Events aktiviert werden soll.\nHinweis: bitte fuer diesen Informationen unten ausfuellen"),
	DISCORDBOT_EVENT_ALERT(ConfigEntrySection.DISCORD, "eventChannel.alert", -1, "ID's des Channels, wo die Benachrichtigungen gepostet werden.\n-1= EventChannelID wird genutzt"),
	DISCORDBOT_EVENT_DEATH(ConfigEntrySection.DISCORD, "eventChannel.death", -1, "ID's des Channels, wo die Tode gepostet werden.\n-1= EventChannelID wird genutzt"),
	DISCORDBOT_EVENT_JOIN_LEAVE(ConfigEntrySection.DISCORD, "eventChannel.joinLeave", -1, "ID's des Channels, wo die Joins/Leaves gepostet werden.\n-1= EventChannelID wird genutzt"),

	DISCORDBOT_EVENT_KILL(ConfigEntrySection.DISCORD, "eventChannel.kill", -1, "ID's des Channels, wo die Kills gepostet werden.\n-1= EventChannelID wird genutzt"),
	DISCORDBOT_EVENT_STRIKE(ConfigEntrySection.DISCORD, "eventChannel.strike", -1, "ID's des Channels, wo die Strikes gepostet werden.\n-1= EventChannelID wird genutzt"),
	DISCORDBOT_EVENT_WIN(ConfigEntrySection.DISCORD, "eventChannel.win", -1, "ID's des Channels, wo die Winnachricht gepostet wird.\n-1= EventChannelID wird genutzt"),
	DISCORDBOT_EVENT_YOUTUBE(ConfigEntrySection.DISCORD, "eventChannel.youtube", -1, "ID's des Channels, wo die YT-Videos gepostet werden.\n-1= EventChannelID wird genutzt"),
	DISCORDBOT_EVENTCHANNELID(ConfigEntrySection.DISCORD, "eventChannelID", -1, "Gib hier die ChannelID des Channels an,\nin welchen der Bot Events posten soll.\nRechtsklick auf den Channel -> 'Copy ChannelID'\nWenn Option nicht vorhanden, schalte\n'developer options' in den Einstellungen von Discord ein."),
	DISCORDBOT_GAMESTATE(ConfigEntrySection.DISCORD, "gameState", "Varo | Plugin by Cuuky - Contributors: Korne127", "Stelle hier ein, was der Bot\nim Spiel als Name haben soll."),
	DISCORDBOT_INVITELINK(ConfigEntrySection.DISCORD, "inviteLink", "ENTER LINK HERE", "Stelle hier deinen Link zum Discord ein"),

	DISCORDBOT_MESSAGE_RANDOM_COLOR(ConfigEntrySection.DISCORD, "randomMessageColor", false, "Ob die Nachrichten eine zufaellige Farbe haben sollen"),
	DISCORDBOT_REGISTERCHANNELID(ConfigEntrySection.DISCORD, "registerChannelID", -1, "Gib hier die Channel ID des #verify - Channels\nan, wo sich die User verifizieren koennen."),
	DISCORDBOT_RESULT_CHANNELID(ConfigEntrySection.DISCORD, "resultChannelID", -1, "Gib hier die Channel ID an, in der spaeter\ndie Logs und die Gewinner gepostet werden sollen."),
	DISCORDBOT_SERVERID(ConfigEntrySection.DISCORD, "serverGuildID", -1, "Gib hier die ServerID deines Servers an.\nHinweis: Vorgangsweise, um die ID zu bekommen wie beim Channel."),

	DISCORDBOT_SET_TEAM_AS_GROUP(ConfigEntrySection.DISCORD, "setTeamAsGroup", false, "Ob die Spieler, die ein Team bekommen,\ndiesen auch als Gruppe im Discord bekommen sollen."),
	DISCORDBOT_TOKEN(ConfigEntrySection.DISCORD, "botToken", "ENTER TOKEN HERE", "Gib hier den Token an, welchen du auf\nder Bot Seite und 'create bot user' bekommst."),
	DISCORDBOT_USE_VERIFYSTSTEM_MYSQL(ConfigEntrySection.DISCORD, "verifySystemMySQL", false, "Ob fuer die Speicherung der BotRegister\neine MySQL Datenbank genutzt werden soll"),

	DISCORDBOT_VERIFY_DATABASE(ConfigEntrySection.DISCORD, "mysql_verify.mysql_database", "DATABASE_HERE", "Datenbank, wo die BotRegister\ngespeichert werden sollen"),
	DISCORDBOT_VERIFY_HOST(ConfigEntrySection.DISCORD, "mysql_verify.mysql_host", "HOST_HERE", "MySQL Host, zu welchem das Plugin sich verbinden soll"),
	DISCORDBOT_VERIFY_PASSWORD(ConfigEntrySection.DISCORD, "mysql_verify.mysql_password", "PASSWORD_HERE", "Passwort fuer MySQL Nutzer,\nwelcher auf die Datenbank zugreifen soll"),

	DISCORDBOT_VERIFY_USER(ConfigEntrySection.DISCORD, "mysql_verify.mysql_user", "USER_HERE", "MySQL Nutzer, welcher auf die Datenbank zugreifen soll"),
	DISCORDBOT_VERIFYSYSTEM(ConfigEntrySection.DISCORD, "verifySystem", false, "Ob das Verify System aktiviert werden soll.\nDieses laesst nur mit dem Discord\nverifizierte Spieler auf den MC-Server."),
	DISTANCE_TO_BORDER_REQUIRED(ConfigEntrySection.BORDER, "distanceToBorderRequired", -1, "Die Distanz, die der Spieler haben muss,\ndamit die Distanz angezeigt wird."),
	DO_DAILY_BACKUPS(ConfigEntrySection.MAIN, "dailyBackups", true, "Es werden immer Backups um 'ResetHour' gemacht."),

	DO_RANDOMTEAM_AT_START(ConfigEntrySection.START, "doRandomTeamAtStart", -1, "Groesse der Teams, in die die Teamlosen beim Start eingeordnet werden.\nAusgeschaltet = -1"),
	DO_SORT_AT_START(ConfigEntrySection.START, "doSortAtStart", true, "Ob beim Start /sort ausgefuehrt werden soll."),
	DO_SPAWN_GENERATE_AT_START(ConfigEntrySection.START, "doSpawnGenerateAtStart", false, "Ob die Spawnloecher am Start basierend auf den\nderzeitigen Spielern generiert werden sollen"),
	FAKE_MAX_SLOTS(ConfigEntrySection.SERVER_LIST, "fakeMaxSlots", -1, "Setzt die maximalen Slots des Servers gefaked.\nOff = -1"),
	FINALE_PROTECTION_TIME(ConfigEntrySection.FINALE, "finaleProtectionTime", 30, "Laenge der Schutzzeit nachdem alle Spieler in die Mitte teleportiert werden."),

	// TEAMS
	FRIENDLYFIRE(ConfigEntrySection.TEAMS, "friendlyFire", false, "Zufuegen von Schaden unter Teamkameraden."),
	GUI_FILL_INVENTORY(ConfigEntrySection.GUI, "guiFillInventory", true, "Bestimmt, ob die leeren Felder der Gui mit Kacheln aufgefuellt werden."),

	// GUI
	GUI_INVENTORY_ANIMATIONS(ConfigEntrySection.GUI, "guiInventoryAnimations", false, "Bestimmt, ob beim Klicken in der Gui eine Animation abgespielt wird."),

	// JOINSYSTEMS
	IGNORE_JOINSYSTEMS_AS_OP(ConfigEntrySection.JOIN_SYSTEMS, "ignoreJoinSystemsAsOP", true, "Ob OP-Spieler die JoinSysteme ignorieren."),
	JOIN_AFTER_HOURS(ConfigEntrySection.JOIN_SYSTEMS, "joinAfterHours", -1, "ZWEITES JOIN SYSTEM\nStellt ein, nach wie vielen Stunden\nSpieler regulaer wieder den Server betreten duerfen"),

	// PROTECTIONS
	JOIN_PROTECTIONTIME(ConfigEntrySection.PROTECTIONS, "joinProtectionTime", 10, "Laenge der Schutzzeit in Sekunden beim Betreten des Servers.\nOff = -1"),
	KICK_AT_SERVER_CLOSE(ConfigEntrySection.JOIN_SYSTEMS, "kickAtServerClose", false, "Kickt den Spieler, sobald er ausserhalb\ndererlaubten Zeit auf dem Server ist."),
	KICK_DELAY_AFTER_DEATH(ConfigEntrySection.DEATH, "kickDelayAfterDeath", -1, "Zeit in Sekunden, nach der ein Spieler\nnach Tod gekickt wird.\nOff = -1"),
	KICK_LABYMOD_PLAYER(ConfigEntrySection.OTHER, "kickLabyModPlayer", false, "Ob Spieler, die LabyMod nutzen,\ngekickt werden sollen.\nFuer diese Funktion wird dieses Plugin benoetigt:\nhttps://www.spigotmc.org/resources/52423/"),

	KILL_ON_COMBATLOG(ConfigEntrySection.COMBATLOG, "killOnCombatlog", true, "Ob ein Spieler, wenn er\nsich in der oben genannten Zeit ausloggt,\ngetoetet werden soll."),
	KILLER_ADD_HEALTH_ON_KILL(ConfigEntrySection.DEATH, "killerHealthAddOnKill", -1, "Anzahl halber Herzen, die der Killer nach Kill bekommt.\nOff = -1"),
	LOG_REPORTS(ConfigEntrySection.REPORT, "logReports", true, "Ob alle Reports in der reports.yml\nfestgehalten werden sollen."),
	MASS_RECORDING_TIME(ConfigEntrySection.JOIN_SYSTEMS, "massRecordingTime", 15, "Die Laenge der Massenaufnahme, in der alle joinen koennen."),
	MIN_BORDER_SIZE(ConfigEntrySection.BORDER, "minBorderSize", 300, "Wie klein die Border maximal werden kann."),

	MINIMAL_SPECTATOR_HEIGHT(ConfigEntrySection.OTHER, "minimalSpectatorHeight", 70, "Wie tief die Spectator maximal fliegen koennen.\nOff = 0"),
	NAMETAG_SPAWN_HEIGHT(ConfigEntrySection.WORLD, "nametagSpawnHeight", 3, "Wie hoch ueber den Spawns\ndie Nametags sein sollen"),
	NAMETAGS(ConfigEntrySection.MAIN, "nametags", true, "Ob das Plugin die Nametags ueber\nden Koepfen der Spieler veraendern soll.\nHinweis: du kannst diese in der messages.yml einstellen.", true),
	NO_ACTIVITY_DAYS(ConfigEntrySection.ACTIVITY, "noActivityDays", -1, "Nach wie vielen Tagen ohne Aktiviaet auf dem\nServer der Spieler gemeldet werden soll.\nOff = -1"),

	NO_DISCONNECT_PING(ConfigEntrySection.DISCONNECT, "noDisconnectPing", 200, "Ab welchem Ping ein Disconnect\nnicht mehr als einer zaehlt."),
	NO_KICK_DISTANCE(ConfigEntrySection.OTHER, "noKickDistance", 30, "In welcher Distanz zum Gegner\nein Spieler nicht gekickt wird.\nOff = 0"),
	NO_SATIATION_REGENERATE(ConfigEntrySection.OFFLINEVILLAGER, "noSatiationRegenerate", false, "Ob Spieler nicht durch Saettigung regenerieren\nkoennen sondern nur durch Gapple etc."),

	// OFFLINEVILLAGER
	OFFLINEVILLAGER(ConfigEntrySection.OFFLINEVILLAGER, "enableOfflineVillager", false, "Ob Villager, welche representativ fuer den Spieler waehrend\nseiner Onlinezeit auf dem Server warten und gekillt werden koennen."),

	ONLY_JOIN_BETWEEN_HOURS(ConfigEntrySection.JOIN_SYSTEMS, "onlyJoinBetweenHours", false, "FÜR BEIDE JOIN SYSTEME\nStellt ein, ob Spieler nur zwischen\n2 unten festgelegten Zeiten joinen duerfen."),
	ONLY_JOIN_BETWEEN_HOURS_HOUR1(ConfigEntrySection.JOIN_SYSTEMS, "onlyJoinBetweenHoursHour1", 14, "Erste Uhrzeit, zwischen welchen\ndie Spieler joinen duerfen."),
	ONLY_JOIN_BETWEEN_HOURS_MINUTE1(ConfigEntrySection.JOIN_SYSTEMS, "onlyJoinBetweenHoursMinute1", 0, "Erste Minuten-Uhrzeit, zwischen welchen\ndie Spieler joinen duerfen."),
	ONLY_JOIN_BETWEEN_HOURS_HOUR2(ConfigEntrySection.JOIN_SYSTEMS, "onlyJoinBetweenHoursHour2", 16, "Zweite Uhrzeit, zwischen welchen\ndie Spieler joinen duerfen."),
	ONLY_JOIN_BETWEEN_HOURS_MINUTE2(ConfigEntrySection.JOIN_SYSTEMS, "onlyJoinBetweenHoursMinute2", 0, "Zweite Minuten-Uhrzeit, zwischen welchen\ndie Spieler joinen duerfen."),

	ONLY_LABYMOD_PLAYER(ConfigEntrySection.OTHER, "onlyLabyModPlayer", false, "Ob nur Spieler mit LabyMod joinen duerfen.\nFuer diese Funktion wird dieses Plugin benoetigt:\nhttps://www.spigotmc.org/resources/52423/"),
	OUTSIDE_BORDER_SPAWN_TELEPORT(ConfigEntrySection.BORDER, "outsideBorderSpawnTeleport", true, "Ob, wenn ein Spieler ausserhalb der Border joint, er in die Mitte teleportiert werden soll."),
	PLAY_TIME(ConfigEntrySection.MAIN, "playTime", 15, "Zeit in Minuten, wie lange die Spieler\npro Session auf dem Server spielen koennen.\nUnlimited = -1"),

	// OTHER
	PLAYER_CHEST_LIMIT(ConfigEntrySection.OTHER, "playerChestLimit", 2, "Wie viele Chests ein Team\nregistrieren darf.\nOff = 0, Unendlich = -1"),
	PLAYER_FURNACE_LIMIT(ConfigEntrySection.OTHER, "playerFurnaceLimit", -1, "Wie viele Furnaces ein\nSpieler registrieren darf.\nOff = 0, Undendlich = -1"),
	PLAYER_SPECTATE_AFTER_DEATH(ConfigEntrySection.DEATH, "playerSpectateAfterDeath", false, "Ob ein Spieler nach seinem Tod Spectator wird."),

	// FINALE
	PLAYER_SPECTATE_IN_FINALE(ConfigEntrySection.FINALE, "playerSpectateInFinale", true, "Ob die toten Spieler waehrend des Finales spectaten duerfen."),
	POST_COORDS_DAYS(ConfigEntrySection.ACTIVITY, "postCoordsDays", -1, "Postet nach den genannten Tagen\nvon allen Spielern die Koordinatenum die Uhrzeit,\num der auch Sessions etc. geprueft werden"),
	PRE_PRODUCE_SESSIONS(ConfigEntrySection.JOIN_SYSTEMS, "preProduceSessions", 3, "FÜR BEIDE JOIN SYSTEME\nStellt ein, wie viele Folgen der Spieler zusaetzlich zu\nden Regulaeren vorproduzieren darf."),

	// MAIN
	PREFIX(ConfigEntrySection.MAIN, "prefix", "&7[&3Varo&7] ", "Prefix, der im Chat bzw. vor\nden Nachrichten angezeigt wird."),
	PROJECT_NAME(ConfigEntrySection.MAIN, "projectname", "Varo", "Name deines Projektes, der in den\nNachrichten, am Scoreboard, etc. steht."),
	PROJECTNAME_COLORCODE(ConfigEntrySection.MAIN, "projectnameColorcode", "&3", "Dieser Farbcode ist der Massgebende,\nder ueberall im Projekt verwendet wird.."),
	RANDOM_CHEST_FILL_RADIUS(ConfigEntrySection.WORLD, "randomChestFillRadius", -1, "In welchem Radius die Kisten um den\nSpawn mit den in der Config angegebenen\nItems befuellt werden sollen.\nOff = -1"),
	RANDOM_CHEST_MAX_ITEMS_PER_CHEST(ConfigEntrySection.WORLD, "randomChestMaxItems", 5, "Wie viele Items in eine Kiste sollen."),
	REMOVE_HIT_COOLDOWN(ConfigEntrySection.OTHER, "removeHitDelay", false, "Entfernt den 1.9+ Hit delay"),
	REMOVE_PLAYERS_ARENT_AT_START(ConfigEntrySection.START, "removePlayersArentAtStart", true, "Ob das Plugin alle Spieler, die nicht beim\nStart dabei sind vom Projekt entferenen soll."),
	REPORT_SEND_DELAY(ConfigEntrySection.REPORT, "reportDelay", 30, "Zeit in Sekunden, die ein Spieler warten muss,\nbevor er einen neuen Spieler reporten kann.\nOff = -1"),
	REPORT_STAFF_MEMBER(ConfigEntrySection.REPORT, "reportStaffMember", true, "Ob Spieler mit der Permission\n'varo.report' reportet werden koennen."),

	// REPORT
	REPORTSYSTEM_ENABLED(ConfigEntrySection.REPORT, "enabled", true, "Ob das Report-System angeschaltet sein soll."),
	RESET_SESSION_HOUR(ConfigEntrySection.MAIN, "resetSessionHour", 1, "Um welche Uhrzeit (24h) der Server den\nSpieler neue Sessions etc. gibt"),
	RESPAWN_PROTECTION(ConfigEntrySection.DEATH, "respawnProtection", 120, "Wie lange in Sekunden Spieler\nnach Respawn geschuetzt sind"),
	SCOREBOARD(ConfigEntrySection.MAIN, "scoreboard", true, "Ob das Scoreboard aktiviert sein soll.\nHinweis: das Scoreboard kannst du in\nder scoreboard.yml bearbeiten.", true),
	SESSIONS_PER_DAY(ConfigEntrySection.JOIN_SYSTEMS, "sessionsPerDay", 1, "ERSTES JOIN SYSTEM\nStellt ein, wie oft Spieler am Tag\nden Server regulaer betreten duerfen."),

	SET_NAMETAGS_OVER_SPAWN(ConfigEntrySection.WORLD, "setNameTagOverSpawn", true, "Ob Nametags ueber den\nSpawns erscheinen sollen"),
	SHOW_DISTANCE_TO_BORDER(ConfigEntrySection.BORDER, "showDistanceToBorder", false, "Ob die Distanz zur Border in der\nActionBar angezeigt werden soll."),
	SHOW_TIME_IN_ACTIONBAR(ConfigEntrySection.OTHER, "showTimeInActionbar", false, "Ob die verbleibende Sessionzeit in\nder Actionbar angezeigt werden soll."),
	SPAWN_PROTECTION_RADIUS(ConfigEntrySection.WORLD, "spawnProtectionRadius", 0, "Radius, in dem die Spieler\nnicht am Spawn bauen koennen."),

	SPAWN_TELEPORT_JOIN(ConfigEntrySection.START, "spawnTeleportAtLobbyPhase", true, "Ob die Spieler, wenn\nfuer sie ein Spawn gesetzt wurde auch in\ndiesem spawnen sollen, sobald sie joinen."),
	START_AT_PLAYERS(ConfigEntrySection.START, "startAtPlayers", -1, "Startet das Projekt automatisch wenn die\nAnzahl der Online Spieler dieser entspricht."),

	// START
	STARTCOUNTDOWN(ConfigEntrySection.START, "startCountdown", 30, "Wie lange der Startcountdown\nbei Start in Sekunden ist."),
	STARTPERIOD_PROTECTIONTIME(ConfigEntrySection.PROTECTIONS, "startperiodProtectiontime", -1, "Laenge der Schutzzeit nach dem Start.\nOff = -1"),
	STARTPERIOD_PROTECTIONTIME_BROADCAST_INTERVAL(ConfigEntrySection.PROTECTIONS, "startperiodProtectiontimeBcInterval", 60, "In welchen Sekundenabstaenden die restliche Schutzzeit\ngebroacastet werden soll"),
	STOP_SERVER_ON_WIN(ConfigEntrySection.DEATH, "stopServerOnWin", -1, "Zeit in Sekunden, nachdem der Server nach\nWin eines Teams heruntergefahren wird."),
	STRIKE_BAN_AFTER_STRIKE_HOURS(ConfigEntrySection.STRIKE, "banOnPostHours", -1, "Fuer wie viele Stunden die Spieler\nnach einem Strike gestriket werden"),
	STRIKE_BAN_AT_POST(ConfigEntrySection.STRIKE, "banAtPost", true, "Ob der Spieler beim Posten des Strikes\num die oben genannte Zahl gebannt werden soll.\nSonst wird dieser beim Erhalten gebannt"),
	STRIKE_ON_BLOODLUST(ConfigEntrySection.ACTIVITY, "strikeOnBloodlust", false, "Ob der Spieler nach den oben\ngenannten Tagen ohne Gegnerkontakt\ngestriket werden soll."),
	STRIKE_ON_COMBATLOG(ConfigEntrySection.COMBATLOG, "strikeOnCombatlog", true, "Ob ein Spieler, wenn er sich in\nder oben genannten Zeit ausloggt,\ngestriket werden soll."),
	STRIKE_ON_DISCONNECT(ConfigEntrySection.DISCONNECT, "strikeOnMaxDisconnect", false, "Ob ein Spieler gestriket werden soll\nwenn zu oft disconnected wurde."),
	STRIKE_ON_NO_ACTIVITY(ConfigEntrySection.ACTIVITY, "strikeOnNoActivity", false, "Ob der Spieler nach den oben genannten Tagen\nohne Aktivitaet auf dem Servergestriket werden soll."),

	// STRIKE
	STRIKE_POST_RESET_HOUR(ConfigEntrySection.STRIKE, "postAtResetHour", false, "Ob die Strikes erst um die ResetHour gepostet werden sollen"),
	SUPPORT_PLUGIN_ADS(ConfigEntrySection.MAIN, "supportPluginAds", false, "Werbung wird im Plugin mit eingebaut,was das Plugin,\nalso mich, supportet. Danke an alle, die das aktivieren :3"),
	TABLIST(ConfigEntrySection.MAIN, "tablist", true, "Ob das Plugin die Tablist modfizieren soll", true),
	TEAM_LIFES(ConfigEntrySection.DEATH, "teamLifes", 1, "Wie viele Leben ein Team hat"),

	TEAM_PLACE_SPAWN(ConfigEntrySection.TEAMS, "teamPlaceSpawn", -1, "Anzahl an Spawnplaetzen in einer Teambasis\nWenn angeschaltet (nicht -1) wird eine Luecke fuer fehlende Teammitglieder gelassen.\nAnschalten, wenn jedes Team einen eigenen Spawnplatz besitzt und es keinen grossen Kreis gibt."),
	TEAMREQUEST_EXPIRETIME(ConfigEntrySection.TEAMS, "teamRequestExpiretime", 30, "Die Zeit in Sekunden, nachdem eine Teamanfrage ablaufen soll."),

	TEAMREQUEST_MAXTEAMMEMBERS(ConfigEntrySection.TEAMS, "teamRequestMaxTeamMembers", 2, "Anzahl an Teammitglieder pro Team."),
	TEAMREQUEST_MAXTEAMNAMELENGTH(ConfigEntrySection.TEAMS, "teamRequestMaxTeamnameLength", 10, "Maximal Laenge eines Teamnamens."),
	TEAMREQUESTS(ConfigEntrySection.TEAMS, "teamRequests", false, "Ob Spieler sich gegenseitig in Teams\nmit /tr einladen koennen.\nSehr gute Funktion fuer ODV's."),
	TELEGRAM_BOT_TOKEN(ConfigEntrySection.TELEGRAM, "botToken", "ENTER TOKEN HERE", "Setzt den Bot Token des Telegrambots"),

	// TELEGRAM
	TELEGRAM_ENABLED(ConfigEntrySection.TELEGRAM, "telegrambotEnabled", false, "Ob der Telegrambot aktiviert werden soll."),
	TELEGRAM_EVENT_CHAT_ID(ConfigEntrySection.TELEGRAM, "eventChatId", -1, "In diesen Chat werden alle Events gepostet."),
	TELEGRAM_VIDEOS_CHAT_ID(ConfigEntrySection.TELEGRAM, "videosChatId", -1, "Hier kannst du die ID des Chats angeben, wo\ndie Videos der User gepostet werden sollen."),
	TRIGGER_FOR_GLOBAL(ConfigEntrySection.TEAMS, "triggerForGlobal", false, "Wenn aktiviert, wird standardmaessig in den Teamchat geschrieben und mit dem Triggerbuchstaben am Anfang in den globalen Chat, ansonsten umgekehrt."),
	UNREGISTERED_PLAYER_JOIN(ConfigEntrySection.MAIN, "unregisteredPlayerJoin", true, "Ob unregistrierte Spieler joinen duerfen."),

	// YOUTUBE
	YOUTUBE_ENABLED(ConfigEntrySection.YOUTUBE, "enabled", false, "Checkt jeden Tag bei den Spielern,\ndie einen YouTube Link registriert haben,\nnach den Uploads"),
	YOUTUBE_SET_OWN_LINK(ConfigEntrySection.YOUTUBE, "setOwnLink", true, "Ob die Spieler sich selbst den\nYouTube-Link per /yt setzen duerfen"),
	YOUTUBE_VIDEO_IDENTIFIER(ConfigEntrySection.YOUTUBE, "videoIdentifier", "Varo", "Was die Videotitel enthalten\nmuessen, um als Varovideo zu gelten.");

	private Object defaultValue, value;
	private String path, description;
	private ConfigEntrySection section;
	private boolean reducesPerformance;

	private ConfigEntry(ConfigEntrySection section, String path, Object value, String description) {
		this.section = section;
		this.path = path;
		this.value = value;
		this.defaultValue = value;
		this.description = description;
	}

	private ConfigEntry(ConfigEntrySection section, String path, Object value, String description, boolean reducesPerformance) {
		this(section, path, value, description);

		this.reducesPerformance = reducesPerformance;
	}

	private void save() {
		Main.getDataManager().getConfigHandler().saveValue(this);
	}

	private void sendFalseCast(Class<?> failedToCast) {
		if(value instanceof Integer && failedToCast.equals(Long.class) || value instanceof Long && failedToCast.equals(Integer.class))
			throw new IllegalArgumentException("'" + value + "' (" + value.getClass().getName() + ") is not applyable for " + failedToCast.getName() + " for entry " + getFullPath());

		try {
			throw new IllegalArgumentException("'" + value + "' (" + value.getClass().getName() + ") is not applyable for " + failedToCast.getName() + " for entry " + getFullPath());
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			Bukkit.getServer().shutdown();
		}
	}

	public Object getDefaultValue() {
		return this.defaultValue;
	}

	public String[] getDescription() {
		return description.split("\n");
	}

	public String getFullPath() {
		return section.getPath() + path;
	}

	public String getName() {
		return path;
	}

	public String getPath() {
		return path;
	}

	public ConfigEntrySection getSection() {
		return section;
	}

	public Object getValue() {
		return this.value;
	}

	public boolean getValueAsBoolean() {
		try {
			return (boolean) this.value;
		} catch(Exception e) {
			sendFalseCast(Boolean.class);
		}

		return (boolean) defaultValue;
	}

	public double getValueAsDouble() {
		try {
			return (double) this.value;
		} catch(Exception e) {
			try {
				return Double.valueOf(getValueAsInt());
			} catch(Exception e2) {
				sendFalseCast(Double.class);
			}
		}

		return (double) defaultValue;
	}

	public int getValueAsInt() {
		try {
			return (int) this.value;
		} catch(Exception e) {
			sendFalseCast(Integer.class);
		}

		return (int) defaultValue;
	}

	public long getValueAsLong() {
		try {
			return (long) this.value;
		} catch(Exception e) {
			try {
				return Long.valueOf(getValueAsInt());
			} catch(Exception e2) {
				sendFalseCast(Long.class);
			}
		}
		
		return (long) defaultValue;
	}

	public String getValueAsString() {
		if(this.value instanceof String)
			return ((String) (this.value)).replace("&", "§");

		try {
			return (String) (this.value = String.valueOf(this.value).replace("&", "§"));
		} catch(Exception e) {
			sendFalseCast(String.class);
		}

		return (String) defaultValue;
	}

	public boolean isIntActivated() {
		return getValueAsInt() > -1;
	}

	public void setValue(Object value, boolean save) {
		this.value = value;

		if(save)
			save();
	}

	public boolean isReducingPerformance() {
		return this.reducesPerformance;
	}

	public static ConfigEntry getEntryByPath(String path) {
		for(ConfigEntry entry : ConfigEntry.values()) {
			if(!entry.getPath().equals(path))
				continue;

			return entry;
		}
		return null;
	}
}