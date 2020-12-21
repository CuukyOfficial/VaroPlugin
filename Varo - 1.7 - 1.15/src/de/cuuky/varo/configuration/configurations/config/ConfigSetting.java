package de.cuuky.varo.configuration.configurations.config;

import org.bukkit.Bukkit;

import de.cuuky.cfw.version.BukkitVersion;
import de.cuuky.cfw.version.VersionUtils;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.SectionEntry;

public enum ConfigSetting implements SectionEntry {

	ADD_TEAM_LIFE_ON_KILL(ConfigSettingSection.DEATH, "teamLife.addOnKill", -1, "Wie viele Leben ein Team bekommen soll,\nsobald es einen Spieler toetet.", "addTeamLifesOnKill"),
	ALWAYS_TIME(ConfigSettingSection.WORLD, "setAlwaysTime", 1000, "Setzt die Zeit auf dem Server,\ndie dann so stehen bleibt.\nHinweis: Nacht = 13000, Tag = 1000", true),
	ALWAYS_TIME_USE_AFTER_START(ConfigSettingSection.WORLD, "alwaysTimeUseAfterStart", false, "Ob die Zeit auch stehen bleiben soll,\nwenn das Projekt gestartet wurde."),
	AUTOSETUP_BORDER(ConfigSettingSection.AUTOSETUP, "border", 2000, "Wie gross die Border beim\nAutoSetup gesetzt werden soll"),

	// AUTOSETUP
	AUTOSETUP_ENABLED(ConfigSettingSection.AUTOSETUP, "enabled", false, "Wenn Autosetup aktiviert ist, werden beim\nStart des Servers alle Spawns automatisch gesetzt und\noptional ein Autostart eingerichtet."),
	AUTOSETUP_LOBBY_ENABLED(ConfigSettingSection.AUTOSETUP, "lobby.enabled", true, "Ob eine Lobby beim AutoSetup gespawnt werden soll"),
	AUTOSETUP_LOBBY_HEIGHT(ConfigSettingSection.AUTOSETUP, "lobby.height", 10, "Hoehe der Lobby, die gespawnt werden soll"),
	AUTOSETUP_LOBBY_SCHEMATIC(ConfigSettingSection.AUTOSETUP, "lobby.schematic", "plugins/Varo/schematics/lobby.schematic", "Schreibe hier den Pfad deiner Lobby-Schematic\nhin, die gepastet werden soll.\nHinweis: WorldEdit benoetigt"),
	AUTOSETUP_LOBBY_SIZE(ConfigSettingSection.AUTOSETUP, "lobby.size", 25, "Groesse der Lobby, die gespawnt werden soll"),

	AUTOSETUP_PORTAL_ENABLED(ConfigSettingSection.AUTOSETUP, "portal.enabled", true, "Ob ein Portal gespawnt werden soll"),
	AUTOSETUP_PORTAL_HEIGHT(ConfigSettingSection.AUTOSETUP, "portal.height", 5, "Hoehe des gespawnten Portals"),
	AUTOSETUP_PORTAL_WIDTH(ConfigSettingSection.AUTOSETUP, "portal.width", 4, "Breite des gespawnten Portals"),

	AUTOSETUP_SPAWNS_AMOUNT(ConfigSettingSection.AUTOSETUP, "spawns.amount", 40, "Zu welcher Anzahl die Loecher\ngeneriert werden sollen"),
	AUTOSETUP_SPAWNS_BLOCKID(ConfigSettingSection.AUTOSETUP, "spawns.block.material", "STONE_BRICK_SLAB", "Welche Block-ID der Halftstep am Spawn haben soll"),
	AUTOSETUP_SPAWNS_RADIUS(ConfigSettingSection.AUTOSETUP, "spawns.radius", 30, "In welchem Radius die Loecher\ngeneriert werden sollen"),
	AUTOSETUP_SPAWNS_SIDEBLOCKID(ConfigSettingSection.AUTOSETUP, "spawns.sideblock.material", VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_13) ? "GRASS_BLOCK" : "GRASS", "Welche Block-ID der Block,\nden man abbaut haben soll"),
	AUTOSETUP_TIME_HOUR(ConfigSettingSection.AUTOSETUP, "autostart.time.hour", -1, "Um welche Zeit der Stunde der\nAutoStart gesetzt werden soll"),
	AUTOSETUP_TIME_MINUTE(ConfigSettingSection.AUTOSETUP, "autostart.time.minute", -1, "Um welche Zeit der Minute der\nAutoStart gesetzt werden soll"),
	WORLD_SPAWNS_GENERATE_Y_TOLERANCE(ConfigSettingSection.AUTOSETUP, "spawnGeneratorYTolerance", 4, "Wie viel Hoehe die Spawns von einander\nAbstand haben duerfen beim\ngenerieren der Spawns\nBeispiel: Spawn ist 10 Bloecke hoeher als andere\n->wird weiter nach Terrain gesucht"),

	BACKPACK_PLAYER_DROP_ON_DEATH(ConfigSettingSection.BACKPACKS, "backpackPlayerDropOnDeath", true, "Ob der Inhalt des Spieler-Rucksacks beim Tod des Spielers gedroppt werden soll."),

	// BACKPACKS
	BACKPACK_PLAYER_ENABLED(ConfigSettingSection.BACKPACKS, "backpackPlayerEnabled", false, "Wenn dies aktiviert ist, haben Spieler einen eigenen Rucksack,\nauf den sie mit /varo bp zugreifen koennen.\nDieser wird pro Spieler und nicht pro Team gespeichert."),
	BACKPACK_PLAYER_SIZE(ConfigSettingSection.BACKPACKS, "backpackPlayerSize", 54, "Groesse des Spieler-Rucksacks (Max = 54)"),

	BACKPACK_TEAM_DROP_ON_DEATH(ConfigSettingSection.BACKPACKS, "backpackTeamDropOnDeath", true, "Ob der Inhalt des Team-Rucksacks beim Tod des letzten Teammitglieds gedroppt werden soll."),
	BACKPACK_TEAM_ENABLED(ConfigSettingSection.BACKPACKS, "backpackTeamEnabled", false, "Wenn dies aktiviert ist, haben Teams einen eigenen Rucksack,\nauf den sie mit /varo bp zugreifen koennen.\nDieser wird pro Team und nicht pro Spieler gespeichert."),

	BACKPACK_TEAM_SIZE(ConfigSettingSection.BACKPACKS, "backpackTeamSize", 54, "Groesse des Team-Rucksacks (Max = 54)"),
	BAN_AFTER_DISCONNECT_MINUTES(ConfigSettingSection.DISCONNECT, "banAfterDisconnectMinutes", -1, "Wenn ein Spieler disconnected,\nob er nach dieser Anzahl an Minuten entfernt werden soll.\nOff = -1"),

	// BAN
	BAN_HACKING_ENABLED(ConfigSettingSection.BAN, "hacking", true, "Ob Spieler, die wegen Hacking von allen\nServer gebannt wurden,\nauch hier gebannt sein sollen"),
	BAN_SCAMMING_ENABLED(ConfigSettingSection.BAN, "scamming", true, "Ob Spieler, die wegen Scammen von allen\nServer gebannt wurden,\nauch hier gebannt sein sollen"),
	BAN_BAD_BEHAVIOUR_ENABLED(ConfigSettingSection.BAN, "behaviour", true, "Ob Spieler, die aufgrund schlechten Verhaltens von allen\nServer gebannt wurden,\nauch hier gebannt sein sollen"),
	BAN_IDENTITY_THEFT_ENABLED(ConfigSettingSection.BAN, "identityTheft", true, "Ob Spieler, die wegen Itenditaetsdiebstahls von allen\nServer gebannt wurden,\nauch hier gebannt sein sollen"),
	BAN_OTHER_ENABLED(ConfigSettingSection.BAN, "other", true, "Ob Spieler, die aus anderen Gruenden von allen\nServer gebannt wurden,\nauch hier gebannt sein sollen"),

	// CHAT
	BLOCK_CHAT_ADS(ConfigSettingSection.CHAT, "blockChatAds", true, "Wenn aktiviert, koennen keine Links in den oeffentlichen Chat gepostet werden."),

	// WORLD
	BLOCK_DESTROY_LOGGER(ConfigSettingSection.WORLD, "blockDestroyLogger", true, "Loggt alle abgebauten Bloecke, die ihr\nunten eintragt unter 'oreLogger.yml'", true),
	BLOCK_USER_PORTALS(ConfigSettingSection.WORLD, "blockUserPortals", true, "Ob Spieler nicht ihre eigenen\nPortale bauen koennen"),
	WORLD_ENTITY_TRACER(ConfigSettingSection.WORLD, "entityTracer", false, "Ob Wassertropfen Items oder Projektilen\nfolgen sollen"),

	// ACTIVITY
	BLOODLUST_DAYS(ConfigSettingSection.ACTIVITY, "noBloodlustDays", -1, "Nach wie vielen Tagen ohne Gegnerkontakt\nder Spieler gemeldet werden soll.\nOff = -1"),
	BORDER_DAMAGE(ConfigSettingSection.BORDER, "borderDamage", 1, "Wie viel Schaden die Border\nin halben Herzen macht."),

	// BORDER
	WORLD_SNCHRONIZE_BORDER(ConfigSettingSection.BORDER, "synchronizeBorders", true, "Ob die Groesse der Border\nfuer alle Welten zaehlen soll"),
	BORDER_DEATH_DECREASE(ConfigSettingSection.BORDER, "deathBorderDecrease.enabled", true, "Ob sich die Border bei Tod verringern soll"),
	BORDER_DEATH_DECREASE_SIZE(ConfigSettingSection.BORDER, "deathBorderDecrease.size", 25, "Um wie viele Bloecke sich die\nBorder bei Tod verringern soll."),
	BORDER_DEATH_DECREASE_SPEED(ConfigSettingSection.BORDER, "deathBorderDecrease.speed", 1, "Mit welcher Geschwindigkeit sich\ndie Border beiTod verringern soll."),
	BORDER_SIZE_IN_FINALE(ConfigSettingSection.FINALE, "borderSizeInFinale", 300, "Auf diese Groesse wird die Border beim Starten des Finales gestellt."),

	BORDER_TIME_DAY_DECREASE(ConfigSettingSection.BORDER, "dayBorderDecrease.enabled", true, "Ob sich die Border nach Tagen verringern soll"),
	BORDER_TIME_DAY_DECREASE_DAYS(ConfigSettingSection.BORDER, "dayBorderDecrease.days", 3, "Nach wie vielen Tagen sich\ndie Border verkleinern soll."),
	BORDER_TIME_DAY_DECREASE_SIZE(ConfigSettingSection.BORDER, "dayBorderDecrease.size", 50, "Um wieviel sich die Bordernach den\noben genannten Tagen verkleinern soll."),
	BORDER_TIME_DAY_DECREASE_SPEED(ConfigSettingSection.BORDER, "dayBorderDecrease.speed", 5, "Wie viele Bloecke pro Sekunde sich\ndie Border nach Tagen verkleinern soll."),
	BORDER_TIME_MINUTE_DECREASE(ConfigSettingSection.BORDER, "minuteBorderDecrease.enabled", false, "Ob sich die Border nach Minuten verringern soll"),
	BORDER_TIME_MINUTE_DECREASE_MINUTES(ConfigSettingSection.BORDER, "minuteBorderDecrease.minutes", 30, "Nach wie vielen Minuten sich\ndie Border verkleinern soll."),
	BORDER_TIME_MINUTE_DECREASE_SIZE(ConfigSettingSection.BORDER, "minuteBorderDecrease.size", 50, "Um wieviel sich die Bordernach den oben\ngenannten Minuten verkleinern soll."),
	BORDER_TIME_MINUTE_DECREASE_SPEED(ConfigSettingSection.BORDER, "minuteBorderDecrease.speed", 5, "Wie viele Bloecke pro Sekunde sichdie\nBorder nach Minuten verkleinern soll."),
	BORDER_TIME_MINUTE_BC_INTERVAL(ConfigSettingSection.BORDER, "minuteBorderDecrease.bcInterval", 300, "In welchen Sekundenabstaenden die Zeit bis zur Verkleinerung\ngebroacastet werden soll"),
	BROADCAST_INTERVAL_IN_SECONDS(ConfigSettingSection.OTHER, "broadcastIntervalInSeconds", -1, "Interval in Sekunden, in welcher der\nBroadcaster eine Nachricht postet.\nHinweis: Die Nachrichten kannst du in der broadcasts.yml einstellen.\nOff = -1"),
	CAN_CHAT_BEFORE_START(ConfigSettingSection.CHAT, "canChatBeforeStart", true, "Ob die Spieler vor Start chatten koennen."),

	CAN_MOVE_BEFORE_START(ConfigSettingSection.START, "canMoveBeforeStart", false, "Ob die Spieler sich vor Start bewegen koennen."),
	CANWALK_PROTECTIONTIME(ConfigSettingSection.PROTECTIONS, "canWalkOnJoinProtection", false, "Ob Spieler waehrend der Joinschutzzeit laufen koennen."),
	CATCH_UP_SESSIONS(ConfigSettingSection.JOIN_SYSTEMS, "catchUpSessions", false, "NUR FÜR ERSTES JOIN SYSTEM\nStellt ein, ob man verpasste Folgen nachholen darf."),

	// SERVERLIST
	CHANGE_MOTD(ConfigSettingSection.SERVER_LIST, "changeMotd", true, "Ob das Plugin die Motd veraendern soll.\nHinweis: du kannst die Motd in der messages.yml aendern."),
	CHAT_COOLDOWN_IF_STARTED(ConfigSettingSection.CHAT, "chatCooldownIfStarted", false, "Ob der Chatcooldown auch aktiviert sein\\nsoll wenn das Projekt gestartet wurde."),
	CHAT_COOLDOWN_IN_SECONDS(ConfigSettingSection.CHAT, "chatCooldownInSeconds", 3, "Der Cooldown der Spieler im Chat,\nbevor sie wieder eine Nachricht senden koennen.\nOff = -1"),
	CHAT_TRIGGER(ConfigSettingSection.TEAMS, "chatTrigger", "#", "Definiert den Buchstaben am Anfang einer\nNachricht, der den Teamchat ausloest."),

	// COMBATLOG
	COMBATLOG_TIME(ConfigSettingSection.COMBATLOG, "combatlogTime", 30, "Zeit, nachdem sich ein Spieler\nnach dem Kampf wieder ausloggen kann.\nOff = -1"),

	// DEATH
	DEATH_SOUND(ConfigSettingSection.DEATH, "deathSound", false, "Ob ein Withersound fuer alle abgespielt werden soll,\nsobald ein Spieler stirbt", true),
	DEBUG_OPTIONS(ConfigSettingSection.OTHER, "debugOptions", false, "Ob Debug Funktionen verfuegbar sein sollen.\nVorsicht: Mit Bedacht oder nur\nauf Anweisung nutzen!"),
	BLOCK_ADVANCEMENTS(ConfigSettingSection.OTHER, "blockAdvancements", true, "Ob Advancements deaktiviert werden sollen [1.12+]"),
	DISABLE_LABYMOD_FUNCTIONS(ConfigSettingSection.OTHER, "disableLabyModFunctions", false, "Ob die Addons von LabyMod beim Spieler\ndeaktviert werden sollen.\nFuer diese Funktion wird dieses Plugin benoetigt:\nhttps://www.spigotmc.org/resources/52423/"),

	// DISCONNECT
	DISCONNECT_PER_SESSION(ConfigSettingSection.DISCONNECT, "maxDisconnectsPerSessions", 3, "Wie oft ein Spieler pro\nSession maximal disconnecten darf,\nbevor er bestraft wird.Off = -1"),
	DISCORDBOT_ADD_POKAL_ON_WIN(ConfigSettingSection.DISCORD, "addPokalOnWin", true, "Ob den Gewinnern Pokale hinter den\nNamen gesetzt werden sollen."),
	DISCORDBOT_ANNOUNCEMENT_CHANNELID(ConfigSettingSection.DISCORD, "announcementChannelID", -1, "Gib hier den Channel an,\nin dem Nachrichten vom AutoStart geschrieben werden.\nBeispiel: Varo startet in ... Minuten."),
	DISCORDBOT_ANNOUNCEMENT_PING_ROLEID(ConfigSettingSection.DISCORD, "announcementPingRoleID", -1, "Gib hier die ID der Rolle ein, welche\nbei Nachrichtenauf Discord gepingt werden sollen.\nHinweis: -1 = everyone"),
	DISCORDBOT_COMMANDTRIGGER(ConfigSettingSection.DISCORD, "commandTrigger", "!varo ", "Stelle hier ein, womit man die\nVaro Commands Triggern kann.\nBeispiel: '!varo remaining'"),

	// DISCORDBOT
	DISCORDBOT_ENABLED(ConfigSettingSection.DISCORD, "discordBotEnabled", false, "Ob der DiscordBot fuer Events aktiviert werden soll.\nHinweis: bitte fuer diesen Informationen unten ausfuellen"),
	DISCORDBOT_EVENT_ALERT(ConfigSettingSection.DISCORD, "eventChannel.alert", -1, "ID's des Channels, wo die Benachrichtigungen gepostet werden.\n-1= EventChannelID wird genutzt"),
	DISCORDBOT_EVENT_BORDER(ConfigSettingSection.DISCORD, "eventChannel.border", -1, "ID's des Channels, wo die Border Veränderungen gepostet werden.\n-1= EventChannelID wird genutzt"),
	DISCORDBOT_EVENT_DEATH(ConfigSettingSection.DISCORD, "eventChannel.death", -1, "ID's des Channels, wo die Tode gepostet werden.\n-1= EventChannelID wird genutzt"),
	DISCORDBOT_EVENT_JOIN_LEAVE(ConfigSettingSection.DISCORD, "eventChannel.joinLeave", -1, "ID's des Channels, wo die Joins/Leaves gepostet werden.\n-1= EventChannelID wird genutzt"),

	DISCORDBOT_EVENT_KILL(ConfigSettingSection.DISCORD, "eventChannel.kill", -1, "ID's des Channels, wo die Kills gepostet werden.\n-1= EventChannelID wird genutzt"),
	DISCORDBOT_EVENT_STRIKE(ConfigSettingSection.DISCORD, "eventChannel.strike", -1, "ID's des Channels, wo die Strikes gepostet werden.\n-1= EventChannelID wird genutzt"),
	DISCORDBOT_EVENT_WIN(ConfigSettingSection.DISCORD, "eventChannel.win", -1, "ID's des Channels, wo die Winnachricht gepostet wird.\n-1= EventChannelID wird genutzt"),
	DISCORDBOT_EVENT_YOUTUBE(ConfigSettingSection.DISCORD, "eventChannel.youtube", -1, "ID's des Channels, wo die YT-Videos gepostet werden.\n-1= EventChannelID wird genutzt"),
	DISCORDBOT_EVENTCHANNELID(ConfigSettingSection.DISCORD, "eventChannelID", -1, "Gib hier die ChannelID des Channels an,\nin welchen der Bot Events posten soll.\nRechtsklick auf den Channel -> 'Copy ChannelID'\nWenn Option nicht vorhanden, schalte\n'developer options' in den Einstellungen von Discord ein."),
	DISCORDBOT_GAMESTATE(ConfigSettingSection.DISCORD, "gameState", "Varo | Plugin by Cuuky - Contributors: Korne127, UeberallGebannt", "Stelle hier ein, was der Bot\nim Spiel als Name haben soll."),
	DISCORDBOT_INVITELINK(ConfigSettingSection.DISCORD, "inviteLink", "ENTER LINK HERE", "Stelle hier deinen Link zum Discord ein"),

	DISCORDBOT_MESSAGE_RANDOM_COLOR(ConfigSettingSection.DISCORD, "randomMessageColor", false, "Ob die Nachrichten eine zufaellige Farbe haben sollen (nur bei Embeds)"),
	DISCORDBOT_USE_EMBEDS(ConfigSettingSection.DISCORD, "useEmbeds", true, "Ob die Nachrichten als Embed gesendet werden sollen"),
	DISCORDBOT_RESULT_CHANNELID(ConfigSettingSection.DISCORD, "resultChannelID", -1, "Gib hier die Channel ID an, in der spaeter\ndie Logs und die Gewinner gepostet werden sollen."),
	DISCORDBOT_SERVERID(ConfigSettingSection.DISCORD, "serverGuildID", -1, "Gib hier die ServerID deines Servers an.\nHinweis: Vorgangsweise, um die ID zu bekommen wie beim Channel."),

	DISCORDBOT_SET_TEAM_AS_GROUP(ConfigSettingSection.DISCORD, "setTeamAsGroup", false, "Ob die Spieler, die ein Team bekommen,\ndiesen auch als Gruppe im Discord bekommen sollen."),
	DISCORDBOT_TOKEN(ConfigSettingSection.DISCORD, "botToken", "ENTER TOKEN HERE", "Gib hier den Token an, welchen du auf\nder Bot Seite und 'create bot user' bekommst."),

	DISCORDBOT_VERIFYSYSTEM(ConfigSettingSection.DISCORD, "verify.enabled", false, "Ob das Verify System aktiviert werden soll.\nDieses laesst die Spieler sich mit Discord-Accounts verbinden."),
	DISCORDBOT_VERIFYSYSTEM_OPTIONAL(ConfigSettingSection.DISCORD, "verify.optional", false, "Ob das Verify-System optional sein soll\nWenn deaktiviert: Nur verifizierte Spieler koennen\nden Server betreten"),
	DISCORDBOT_REGISTERCHANNELID(ConfigSettingSection.DISCORD, "verify.registerChannelID", -1, "Gib hier die Channel ID des #verify - Channels\nan, wo sich die User verifizieren koennen."),
	DISCORDBOT_USE_VERIFYSTSTEM_MYSQL(ConfigSettingSection.DISCORD, "verify.mysql.enabled", false, "Ob fuer die Speicherung der BotRegister\neine MySQL Datenbank genutzt werden soll"),
	DISCORDBOT_VERIFY_DATABASE(ConfigSettingSection.DISCORD, "verify.mysql.database", "DATABASE_HERE", "Datenbank, wo die BotRegister\ngespeichert werden sollen"),
	DISCORDBOT_VERIFY_HOST(ConfigSettingSection.DISCORD, "verify.mysql.host", "HOST_HERE", "MySQL Host, zu welchem das Plugin sich verbinden soll"),
	DISCORDBOT_VERIFY_PASSWORD(ConfigSettingSection.DISCORD, "verify.mysql.password", "PASSWORD_HERE", "Passwort fuer MySQL Nutzer,\nwelcher auf die Datenbank zugreifen soll"),
	DISCORDBOT_VERIFY_USER(ConfigSettingSection.DISCORD, "verify.mysql.user", "USER_HERE", "MySQL Nutzer, welcher auf die Datenbank zugreifen soll"),

	DISTANCE_TO_BORDER_REQUIRED(ConfigSettingSection.BORDER, "distanceToBorderRequired", -1, "Die Distanz, die der Spieler haben muss,\ndamit die Distanz angezeigt wird."),
	DO_DAILY_BACKUPS(ConfigSettingSection.MAIN, "dailyBackups", true, "Es werden immer Backups um 'ResetHour' gemacht."),

	DO_RANDOMTEAM_AT_START(ConfigSettingSection.START, "doRandomTeamAtStart", -1, "Groesse der Teams, in die die Teamlosen beim Start eingeordnet werden.\nAusgeschaltet = -1"),
	DO_SORT_AT_START(ConfigSettingSection.START, "doSortAtStart", true, "Ob beim Start /sort ausgefuehrt werden soll."),
	DO_SPAWN_GENERATE_AT_START(ConfigSettingSection.START, "doSpawnGenerateAtStart", false, "Ob die Spawnloecher am Start basierend auf den\nderzeitigen Spielern generiert werden sollen"),
	FAKE_MAX_SLOTS(ConfigSettingSection.SERVER_LIST, "fakeMaxSlots", -1, "Setzt die maximalen Slots des Servers gefaked.\nOff = -1"),
	FINALE_PROTECTION_TIME(ConfigSettingSection.FINALE, "finaleProtectionTime", 30, "Laenge der Schutzzeit nachdem alle Spieler in die Mitte teleportiert werden."),

	// TEAMS
	FRIENDLYFIRE(ConfigSettingSection.TEAMS, "friendlyFire", false, "Zufuegen von Schaden unter Teamkameraden."),
	GUI_FILL_INVENTORY(ConfigSettingSection.GUI, "guiFillInventory", true, "Bestimmt, ob die leeren Felder der Gui mit Kacheln aufgefuellt werden."),

	// GUI
	GUI_INVENTORY_ANIMATIONS(ConfigSettingSection.GUI, "guiInventoryAnimations", false, "Bestimmt, ob beim Klicken in der Gui eine Animation abgespielt wird."),

	// JOINSYSTEMS
	IGNORE_JOINSYSTEMS_AS_OP(ConfigSettingSection.JOIN_SYSTEMS, "ignoreJoinSystemsAsOP", true, "Ob OP-Spieler die JoinSysteme ignorieren."),
	JOIN_AFTER_HOURS(ConfigSettingSection.JOIN_SYSTEMS, "joinAfterHours", -1, "ZWEITES JOIN SYSTEM\nStellt ein, nach wie vielen Stunden\nSpieler regulaer wieder den Server betreten duerfen"),

	// PROTECTIONS
	JOIN_PROTECTIONTIME(ConfigSettingSection.PROTECTIONS, "joinProtectionTime", 10, "Laenge der Schutzzeit in Sekunden beim Betreten des Servers.\nOff = -1"),
	KICK_AT_SERVER_CLOSE(ConfigSettingSection.JOIN_SYSTEMS, "kickAtServerClose", false, "Kickt den Spieler, sobald er ausserhalb\ndererlaubten Zeit auf dem Server ist."),
	KICK_DELAY_AFTER_DEATH(ConfigSettingSection.DEATH, "kickDelayAfterDeath", -1, "Zeit in Sekunden, nach der ein Spieler\nnach Tod gekickt wird.\nOff = -1"),
	KICK_LABYMOD_PLAYER(ConfigSettingSection.OTHER, "kickLabyModPlayer", false, "Ob Spieler, die LabyMod nutzen,\ngekickt werden sollen.\nFuer diese Funktion wird dieses Plugin benoetigt:\nhttps://www.spigotmc.org/resources/52423/"),

	KILL_ON_COMBATLOG(ConfigSettingSection.COMBATLOG, "killOnCombatlog", true, "Ob ein Spieler, wenn er\nsich in der oben genannten Zeit ausloggt,\ngetoetet werden soll."),
	KILLER_ADD_HEALTH_ON_KILL(ConfigSettingSection.DEATH, "killerHealthAddOnKill", -1, "Anzahl halber Herzen, die der Killer nach Kill bekommt.\nOff = -1"),
	LOG_REPORTS(ConfigSettingSection.REPORT, "logReports", true, "Ob alle Reports in der reports.yml\nfestgehalten werden sollen."),
	MASS_RECORDING_TIME(ConfigSettingSection.JOIN_SYSTEMS, "massRecordingTime", 15, "Die Laenge der Massenaufnahme, in der alle joinen koennen."),
	MIN_BORDER_SIZE(ConfigSettingSection.BORDER, "minBorderSize", 300, "Wie klein die Border maximal werden kann."),

	MINIMAL_SPECTATOR_HEIGHT(ConfigSettingSection.OTHER, "minimalSpectatorHeight", 70, "Wie tief die Spectator maximal fliegen koennen.\nOff = 0"),
	NAMETAG_SPAWN_HEIGHT(ConfigSettingSection.WORLD, "nametagSpawnHeight", 3, "Wie hoch ueber den Spawns\ndie Nametags sein sollen"),
	NAMETAGS_ENABLED(ConfigSettingSection.MAIN, "nametags.enabled", true, "Ob das Plugin die Nametags ueber\nden Koepfen der Spieler veraendern soll.\nHinweis: du kannst diese in der messages.yml einstellen.", true),
	NAMETAGS_VISIBLE(ConfigSettingSection.MAIN, "nametags.visible", true, "Ob NameTags sichtbar sein sollen"),
	NO_ACTIVITY_DAYS(ConfigSettingSection.ACTIVITY, "noActivityDays", -1, "Nach wie vielen Tagen ohne Aktiviaet auf dem\nServer der Spieler gemeldet werden soll.\nOff = -1"),

	NO_DISCONNECT_PING(ConfigSettingSection.DISCONNECT, "noDisconnectPing", 200, "Ab welchem Ping ein Disconnect\nnicht mehr als einer zaehlt."),
	NO_KICK_DISTANCE(ConfigSettingSection.OTHER, "noKickDistance", 30, "In welcher Distanz zum Gegner\nein Spieler nicht gekickt wird.\nOff = 0"),
	NO_SATIATION_REGENERATE(ConfigSettingSection.OFFLINEVILLAGER, "noSatiationRegenerate", false, "Ob Spieler nicht durch Saettigung regenerieren\nkoennen sondern nur durch Gapple etc."),

	// OFFLINEVILLAGER
	OFFLINEVILLAGER(ConfigSettingSection.OFFLINEVILLAGER, "enableOfflineVillager", false, "Ob Villager, welche representativ fuer den Spieler waehrend\nseiner Onlinezeit auf dem Server warten und gekillt werden koennen."),

	ONLY_JOIN_BETWEEN_HOURS(ConfigSettingSection.JOIN_SYSTEMS, "onlyJoinBetweenHours", false, "FÜR BEIDE JOIN SYSTEME\nStellt ein, ob Spieler nur zwischen\n2 unten festgelegten Zeiten joinen duerfen."),
	ONLY_JOIN_BETWEEN_HOURS_HOUR1(ConfigSettingSection.JOIN_SYSTEMS, "onlyJoinBetweenHoursHour1", 14, "Erste Uhrzeit, zwischen welchen\ndie Spieler joinen duerfen."),
	ONLY_JOIN_BETWEEN_HOURS_MINUTE1(ConfigSettingSection.JOIN_SYSTEMS, "onlyJoinBetweenHoursMinute1", 0, "Erste Minuten-Uhrzeit, zwischen welchen\ndie Spieler joinen duerfen."),
	ONLY_JOIN_BETWEEN_HOURS_HOUR2(ConfigSettingSection.JOIN_SYSTEMS, "onlyJoinBetweenHoursHour2", 16, "Zweite Uhrzeit, zwischen welchen\ndie Spieler joinen duerfen."),
	ONLY_JOIN_BETWEEN_HOURS_MINUTE2(ConfigSettingSection.JOIN_SYSTEMS, "onlyJoinBetweenHoursMinute2", 0, "Zweite Minuten-Uhrzeit, zwischen welchen\ndie Spieler joinen duerfen."),

	ONLY_LABYMOD_PLAYER(ConfigSettingSection.OTHER, "onlyLabyModPlayer", false, "Ob nur Spieler mit LabyMod joinen duerfen.\nFuer diese Funktion wird dieses Plugin benoetigt:\nhttps://www.spigotmc.org/resources/52423/"),
	OUTSIDE_BORDER_SPAWN_TELEPORT(ConfigSettingSection.BORDER, "outsideBorderSpawnTeleport", true, "Ob, wenn ein Spieler ausserhalb der Border joint, er in die Mitte teleportiert werden soll."),
	PLAY_TIME(ConfigSettingSection.MAIN, "playTime", 15, "Zeit in Minuten, wie lange die Spieler\npro Session auf dem Server spielen koennen.\nUnlimited = -1"),

	// OTHER
	PLAYER_CHEST_LIMIT(ConfigSettingSection.OTHER, "playerChestLimit", 2, "Wie viele Chests ein Team\nregistrieren darf.\nOff = 0, Unendlich = -1"),
	PLAYER_FURNACE_LIMIT(ConfigSettingSection.OTHER, "playerFurnaceLimit", -1, "Wie viele Furnaces ein\nSpieler registrieren darf.\nOff = 0, Undendlich = -1"),
	PLAYER_SPECTATE_AFTER_DEATH(ConfigSettingSection.DEATH, "playerSpectateAfterDeath", false, "Ob ein Spieler nach seinem Tod Spectator wird."),

	// FINALE
	PLAYER_SPECTATE_IN_FINALE(ConfigSettingSection.FINALE, "playerSpectateInFinale", true, "Ob die toten Spieler waehrend des Finales spectaten duerfen."),
	POST_COORDS_DAYS(ConfigSettingSection.ACTIVITY, "postCoordsDays", -1, "Postet nach den genannten Tagen\nvon allen Spielern die Koordinatenum die Uhrzeit,\num der auch Sessions etc. geprueft werden"),
	PRE_PRODUCE_SESSIONS(ConfigSettingSection.JOIN_SYSTEMS, "preProduceSessions", 3, "FÜR BEIDE JOIN SYSTEME\nStellt ein, wie viele Folgen der Spieler zusaetzlich zu\nden Regulaeren vorproduzieren darf."),

	// MAIN
	PREFIX(ConfigSettingSection.MAIN, "prefix", "&7[&3Varo&7] ", "Prefix, der im Chat bzw. vor\nden Nachrichten angezeigt wird."),
	PROJECT_NAME(ConfigSettingSection.MAIN, "projectname", "Varo", "Name deines Projektes, der in den\nNachrichten, am Scoreboard, etc. steht."),
	PROJECTNAME_COLORCODE(ConfigSettingSection.MAIN, "projectnameColorcode", "&3", "Dieser Farbcode ist der Massgebende,\nder ueberall im Projekt verwendet wird.."),
	MAIN_LANGUAGE(ConfigSettingSection.MAIN, "language.main", "de_de", "Alle Sprachentypen hier zu finden: https://minecraft-el.gamepedia.com/Language"),
	MAIN_LANGUAGE_ALLOW_OTHER(ConfigSettingSection.MAIN, "language.allowOther", true, "Ob jeder Spieler eine eigene Sprache\nnutzen darf"),
	RANDOM_CHEST_FILL_RADIUS(ConfigSettingSection.WORLD, "randomChestFillRadius", -1, "In welchem Radius die Kisten um den\nSpawn mit den in der Config angegebenen\nItems befuellt werden sollen.\nOff = -1"),
	RANDOM_CHEST_MAX_ITEMS_PER_CHEST(ConfigSettingSection.WORLD, "randomChestMaxItems", 5, "Wie viele Items in eine Kiste sollen."),
	REMOVE_HIT_COOLDOWN(ConfigSettingSection.OTHER, "removeHitDelay", false, "Entfernt den 1.9+ Hit delay"),
	REMOVE_PLAYERS_ARENT_AT_START(ConfigSettingSection.START, "removePlayersArentAtStart", false, "Ob das Plugin alle Spieler, die nicht beim\nStart dabei sind vom Projekt entferenen soll."),
	REPORT_SEND_DELAY(ConfigSettingSection.REPORT, "reportDelay", 30, "Zeit in Sekunden, die ein Spieler warten muss,\nbevor er einen neuen Spieler reporten kann.\nOff = -1"),
	REPORT_STAFF_MEMBER(ConfigSettingSection.REPORT, "reportStaffMember", true, "Ob Spieler mit der Permission\n'varo.report' reportet werden koennen."),

	// REPORT
	REPORTSYSTEM_ENABLED(ConfigSettingSection.REPORT, "enabled", true, "Ob das Report-System angeschaltet sein soll."),
	RESET_SESSION_HOUR(ConfigSettingSection.MAIN, "resetSessionHour", 1, "Um welche Uhrzeit (24h) der Server den\nSpieler neue Sessions etc. gibt"),
	RESPAWN_PROTECTION(ConfigSettingSection.DEATH, "respawnProtection", 120, "Wie lange in Sekunden Spieler\nnach Respawn geschuetzt sind"),
	SCOREBOARD(ConfigSettingSection.MAIN, "scoreboard", true, "Ob das Scoreboard aktiviert sein soll.\nHinweis: das Scoreboard kannst du in\nder scoreboard.yml bearbeiten.", true),
	SESSIONS_PER_DAY(ConfigSettingSection.JOIN_SYSTEMS, "sessionsPerDay", 1, "ERSTES JOIN SYSTEM\nStellt ein, wie oft Spieler am Tag\nden Server regulaer betreten duerfen."),

	SET_NAMETAGS_OVER_SPAWN(ConfigSettingSection.WORLD, "setNameTagOverSpawn", true, "Ob Nametags ueber den\nSpawns erscheinen sollen"),
	SHOW_DISTANCE_TO_BORDER(ConfigSettingSection.BORDER, "showDistanceToBorder", false, "Ob die Distanz zur Border in der\nActionBar angezeigt werden soll."),
	SHOW_TIME_IN_ACTIONBAR(ConfigSettingSection.OTHER, "showTimeInActionbar", false, "Ob die verbleibende Sessionzeit in\nder Actionbar angezeigt werden soll."),
	SPAWN_PROTECTION_RADIUS(ConfigSettingSection.WORLD, "spawnProtectionRadius", 0, "Radius, in dem die Spieler\nnicht am Spawn bauen koennen."),

	SPAWN_TELEPORT_JOIN(ConfigSettingSection.START, "spawnTeleportAtLobbyPhase", true, "Ob die Spieler, wenn\nfuer sie ein Spawn gesetzt wurde auch in\ndiesem spawnen sollen, sobald sie joinen."),
	START_AT_PLAYERS(ConfigSettingSection.START, "startAtPlayers", -1, "Startet das Projekt automatisch wenn die\nAnzahl der Online Spieler dieser entspricht."),

	// START
	STARTCOUNTDOWN(ConfigSettingSection.START, "startCountdown", 30, "Wie lange der Startcountdown\nbei Start in Sekunden ist."),
	STARTPERIOD_PROTECTIONTIME(ConfigSettingSection.PROTECTIONS, "startperiodProtectiontime", -1, "Laenge der Schutzzeit nach dem Start.\nOff = -1"),
	STARTPERIOD_PROTECTIONTIME_BROADCAST_INTERVAL(ConfigSettingSection.PROTECTIONS, "startperiodProtectiontimeBcInterval", 60, "In welchen Sekundenabstaenden die restliche Schutzzeit\ngebroacastet werden soll"),
	STOP_SERVER_ON_WIN(ConfigSettingSection.DEATH, "stopServerOnWin", -1, "Zeit in Sekunden, nachdem der Server nach\nWin eines Teams heruntergefahren wird."),
	STRIKE_BAN_AFTER_STRIKE_HOURS(ConfigSettingSection.STRIKE, "banOnPostHours", -1, "Fuer wie viele Stunden die Spieler\nnach einem Strike gestriket werden"),
	STRIKE_BAN_AT_POST(ConfigSettingSection.STRIKE, "banAtPost", true, "Ob der Spieler beim Posten des Strikes\num die oben genannte Zahl gebannt werden soll.\nSonst wird dieser beim Erhalten gebannt"),
	STRIKE_ON_BLOODLUST(ConfigSettingSection.ACTIVITY, "strikeOnBloodlust", false, "Ob der Spieler nach den oben\ngenannten Tagen ohne Gegnerkontakt\ngestriket werden soll."),
	STRIKE_ON_COMBATLOG(ConfigSettingSection.COMBATLOG, "strikeOnCombatlog", true, "Ob ein Spieler, wenn er sich in\nder oben genannten Zeit ausloggt,\ngestriket werden soll."),
	STRIKE_ON_DISCONNECT(ConfigSettingSection.DISCONNECT, "strikeOnMaxDisconnect", false, "Ob ein Spieler gestriket werden soll\nwenn zu oft disconnected wurde."),
	STRIKE_ON_NO_ACTIVITY(ConfigSettingSection.ACTIVITY, "strikeOnNoActivity", false, "Ob der Spieler nach den oben genannten Tagen\nohne Aktivitaet auf dem Servergestriket werden soll."),

	// STRIKE
	STRIKE_POST_RESET_HOUR(ConfigSettingSection.STRIKE, "postAtResetHour", false, "Ob die Strikes erst um die ResetHour gepostet werden sollen"),
	SUPPORT_PLUGIN_ADS(ConfigSettingSection.MAIN, "supportPluginAds", false, "Werbung wird im Plugin mit eingebaut,was das Plugin,\nalso mich, supportet. Danke an alle, die das aktivieren :3"),
	TABLIST(ConfigSettingSection.MAIN, "tablist", true, "Ob das Plugin die Tablist modfizieren soll", true),
	TEAM_LIFES(ConfigSettingSection.DEATH, "teamLife.default", 1, "Wie viele Leben ein Team hat", "teamLifes"),
	MAX_TEAM_LIFES(ConfigSettingSection.DEATH, "teamLife.maxLifes", 5, "Wie viele Leben ein maximal haben kann"),

	TEAM_PLACE_SPAWN(ConfigSettingSection.TEAMS, "teamPlaceSpawn", -1, "Anzahl an Spawnplaetzen in einer Teambasis\nWenn angeschaltet (nicht -1) wird eine Luecke fuer fehlende Teammitglieder gelassen.\nAnschalten, wenn jedes Team einen eigenen Spawnplatz besitzt und es keinen grossen Kreis gibt."),

	TEAMREQUEST_EXPIRETIME(ConfigSettingSection.TEAMS, "teamRequest.expiretime", 30, "Die Zeit in Sekunden, nachdem eine Teamanfrage ablaufen soll."),
	TEAMREQUEST_MAXTEAMMEMBERS(ConfigSettingSection.TEAMS, "teamRequest.maxTeamMembers", 2, "Anzahl an Teammitglieder pro Team."),
	TEAMREQUEST_MAXTEAMNAMELENGTH(ConfigSettingSection.TEAMS, "teamRequest.maxTeamnameLength", 10, "Maximal Laenge eines Teamnamens."),
	TEAMREQUEST_ENABLED(ConfigSettingSection.TEAMS, "teamRequest.enabled", false, "Ob Spieler sich gegenseitig in Teams\nmit /tr einladen koennen.\nSehr gute Funktion fuer ODV's."),
	TEAMREQUEST_LOBBYITEMS(ConfigSettingSection.TEAMS, "teamRequest.lobbyItems", true, "Ob die Spieler Items in\nder Lobby erhalten sollen,\nwomit sie sich einladen können"),

	TELEGRAM_BOT_TOKEN(ConfigSettingSection.TELEGRAM, "botToken", "ENTER TOKEN HERE", "Setzt den Bot Token des Telegrambots"),

	// TELEGRAM
	TELEGRAM_ENABLED(ConfigSettingSection.TELEGRAM, "telegrambotEnabled", false, "Ob der Telegrambot aktiviert werden soll."),
	TELEGRAM_EVENT_CHAT_ID(ConfigSettingSection.TELEGRAM, "eventChatId", -1, "In diesen Chat werden alle Events gepostet."),
	TELEGRAM_VIDEOS_CHAT_ID(ConfigSettingSection.TELEGRAM, "videosChatId", -1, "Hier kannst du die ID des Chats angeben, wo\ndie Videos der User gepostet werden sollen."),
	TRIGGER_FOR_GLOBAL(ConfigSettingSection.TEAMS, "triggerForGlobal", false, "Wenn aktiviert, wird standardmaessig in den Teamchat geschrieben und mit dem Triggerbuchstaben am Anfang in den globalen Chat, ansonsten umgekehrt."),
	UNREGISTERED_PLAYER_JOIN(ConfigSettingSection.MAIN, "unregisteredPlayerJoin", true, "Ob unregistrierte Spieler joinen duerfen."),

	// YOUTUBE
	YOUTUBE_ENABLED(ConfigSettingSection.YOUTUBE, "enabled", false, "Checkt jeden Tag bei den Spielern,\ndie einen YouTube Link registriert haben,\nnach den Uploads"),
	YOUTUBE_SET_OWN_LINK(ConfigSettingSection.YOUTUBE, "setOwnLink", true, "Ob die Spieler sich selbst den\nYouTube-Link per /yt setzen duerfen"),
	YOUTUBE_VIDEO_IDENTIFIER(ConfigSettingSection.YOUTUBE, "videoIdentifier", "Varo", "Was die Videotitel enthalten\nmuessen, um als Varovideo zu gelten.");

	private Object defaultValue, value;
	private String path, description, oldPaths[];
	private ConfigSettingSection section;
	private boolean reducesPerformance;

	private ConfigSetting(ConfigSettingSection section, String path, Object value, String description, String... oldPaths) {
		this.section = section;
		this.path = path;
		this.value = value;
		this.defaultValue = value;
		this.description = description;
		this.oldPaths = oldPaths;
	}

	private ConfigSetting(ConfigSettingSection section, String path, Object value, String description, boolean reducesPerformance) {
		this(section, path, value, description);

		this.reducesPerformance = reducesPerformance;
	}

	private void save() {
		Main.getDataManager().getConfigHandler().saveValue(this);
	}

	private void sendFalseCast(Class<?> failedToCast) {
		if (value instanceof Integer && failedToCast.equals(Long.class) || value instanceof Long && failedToCast.equals(Integer.class))
			throw new IllegalArgumentException("'" + value + "' (" + value.getClass().getName() + ") is not applyable for " + failedToCast.getName() + " for entry " + getFullPath());

		try {
			throw new IllegalArgumentException("'" + value + "' (" + value.getClass().getName() + ") is not applyable for " + failedToCast.getName() + " for entry " + getFullPath());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Bukkit.getServer().shutdown();
		}
	}

	@Override
	public String getFullPath() {
		return section.getName() + "." + this.path;
	}

	@Override
	public Object getDefaultValue() {
		return this.defaultValue;
	}

	@Override
	public String[] getDescription() {
		return description.split("\n");
	}

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public ConfigSettingSection getSection() {
		return section;
	}

	@Override
	public void setValue(Object value) {
		setValue(value, false);
	}

	@Override
	public Object getValue() {
		return this.value;
	}

	@Override
	public String[] getOldPaths() {
		return this.oldPaths;
	}

	public void setValue(Object value, boolean save) {
		this.value = value;

		if (save)
			save();
	}

	public boolean getValueAsBoolean() {
		try {
			return (boolean) this.value;
		} catch (Exception e) {
			sendFalseCast(Boolean.class);
		}

		return (boolean) defaultValue;
	}

	public double getValueAsDouble() {
		try {
			return (double) this.value;
		} catch (Exception e) {
			try {
				return Double.valueOf(getValueAsInt());
			} catch (Exception e2) {
				sendFalseCast(Double.class);
			}
		}

		return (double) defaultValue;
	}

	public int getValueAsInt() {
		try {
			return (int) this.value;
		} catch (Exception e) {
			sendFalseCast(Integer.class);
		}

		return (int) defaultValue;
	}

	public long getValueAsLong() {
		try {
			return (long) this.value;
		} catch (Exception e) {
			try {
				return Long.valueOf(getValueAsInt());
			} catch (Exception e2) {
				sendFalseCast(Long.class);
			}
		}

		return (long) defaultValue;
	}

	public String getValueAsString() {
		if (this.value instanceof String)
			return ((String) (this.value)).replace("&", "§");

		try {
			return (String) (this.value = String.valueOf(this.value).replace("&", "§"));
		} catch (Exception e) {
			sendFalseCast(String.class);
		}

		return (String) defaultValue;
	}

	public boolean isIntActivated() {
		return getValueAsInt() > -1;
	}

	public boolean isReducingPerformance() {
		return this.reducesPerformance;
	}

	public static ConfigSetting getEntryByPath(String path) {
		for (ConfigSetting entry : ConfigSetting.values()) {
			if (!entry.getPath().equals(path))
				continue;

			return entry;
		}
		return null;
	}
}