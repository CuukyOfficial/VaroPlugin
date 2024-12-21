package de.cuuky.varo.configuration.configurations.config;

import org.bukkit.Bukkit;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.SectionEntry;
import de.cuuky.varo.game.start.SuroStart;
import de.cuuky.varo.game.world.AutoSetup;

public enum ConfigSetting implements SectionEntry {

	ADD_TEAM_LIFE_ON_KILL(ConfigSettingSection.DEATH, "teamLife.addOnKill", -1D, "Wie viele Leben ein Team bekommen soll,\nsobald es einen Spieler toetet.", "addTeamLifesOnKill"),
	ALWAYS_TIME(ConfigSettingSection.WORLD, "setAlwaysTime", 1000, "Setzt die Zeit auf dem Server,\ndie dann bis zum Start so stehen bleibt.\nHinweis: Nacht = 13000, Tag = 1000, Deaktiviert = -1", true),
	ALWAYS_TIME_USE_AFTER_START(ConfigSettingSection.WORLD, "alwaysTimeUseAfterStart", false, "Ob die Zeit auch stehen bleiben soll,\nwenn das Projekt gestartet wurde."),
	AUTOSETUP_BORDER(ConfigSettingSection.AUTOSETUP, "border", 2000, "Wie gross die Border beim\nAutoSetup gesetzt werden soll"),

	// AUTOSETUP
	AUTOSETUP_ENABLED(ConfigSettingSection.AUTOSETUP, "enabled", false, "Wenn Autosetup aktiviert ist, werden beim\nStart des Servers alle Spawns automatisch gesetzt und\noptional ein Autostart eingerichtet."),
	AUTOSETUP_LOBBY_ENABLED(ConfigSettingSection.AUTOSETUP, "lobby.enabled", true, "Ob eine Lobby beim AutoSetup gespawnt werden soll"),
	AUTOSETUP_LOBBY_SNAP_TYPE(ConfigSettingSection.AUTOSETUP, "lobby.snap.type", AutoSetup.LobbySnap.MAX_HEIGHT, "Naeherungsweise von welcher Position auf der Y-Achse\ndie Lobby gespawnt werden soll\n\nMoegliche Werte: GROUND, MAX_HEIGHT, ABSOLUTE"),
	AUTOSETUP_LOBBY_SNAP_GROUND_OFFSET(ConfigSettingSection.AUTOSETUP, "lobby.snap.ground.offset", 0, "Wie weit über dem Boden\ndie Lobby gespawnt werden soll.\n\nWird genutzt, wenn lobby.snap.type = GROUND"),
	AUTOSETUP_LOBBY_SNAP_MAX_HEIGHT_OFFSET(ConfigSettingSection.AUTOSETUP, "lobby.snap.maxHeight.offset", 50, "Wie weit unter der maximalen Höhe\ndie Lobby gespawnt werden soll.\n\nWird genutzt, wenn lobby.snap.type = MAX_HEIGHT"),
	AUTOSETUP_LOBBY_SNAP_ABSOLUTE_YPOS(ConfigSettingSection.AUTOSETUP, "lobby.snap.absolute.ypos", 64, "Auf welcher Y-Koordinate\ndie Lobby gespawnt werden soll.\n\nWird genutzt, wenn lobby.snap.type = ABSOLUTE"),
	AUTOSETUP_LOBBY_SCHEMATIC_ENABLED(ConfigSettingSection.AUTOSETUP, "lobby.schematic.enabled", false, "Wenn diese Option aktiviert ist, wird die Lobby\nanhand der angegeben Schematic gespawnt.\nAndernfalls wird die Lobby generiert.\n\nHinweis: WorldEdit benoetigt"),
	AUTOSETUP_LOBBY_SCHEMATIC_FILE(ConfigSettingSection.AUTOSETUP, "lobby.schematic.file", "plugins/Varo/schematics/lobby.schematic", "Schreibe hier den Pfad deiner Lobby-Schematic\nhin, die gepastet werden soll."),
	AUTOSETUP_LOBBY_GENERATED_HEIGHT(ConfigSettingSection.AUTOSETUP, "lobby.generated.height", 10, "Wand-Hoehe der Lobby, die generiert werden soll"),
	AUTOSETUP_LOBBY_GENERATED_SIZE(ConfigSettingSection.AUTOSETUP, "lobby.generated.width", 25, "Breite der Lobby, die generiert werden soll"),

	AUTOSETUP_PORTAL_ENABLED(ConfigSettingSection.AUTOSETUP, "portal.enabled", true, "Ob ein Portal gespawnt werden soll"),
	AUTOSETUP_PORTAL_HEIGHT(ConfigSettingSection.AUTOSETUP, "portal.height", 5, "Hoehe des gespawnten Portals"),
	AUTOSETUP_PORTAL_WIDTH(ConfigSettingSection.AUTOSETUP, "portal.width", 4, "Breite des gespawnten Portals"),

	AUTOSETUP_SPAWNS_AMOUNT(ConfigSettingSection.AUTOSETUP, "spawns.amount", 40, "Zu welcher Anzahl die Loecher\ngeneriert werden sollen"),
	AUTOSETUP_SPAWNS_BLOCKID(ConfigSettingSection.AUTOSETUP, "spawns.block.material", XMaterial.STONE_BRICK_SLAB, "Welche Block-ID der Halftstep am Spawn haben soll"),
	AUTOSETUP_SPAWNS_RADIUS(ConfigSettingSection.AUTOSETUP, "spawns.radius", -1, "In welchem Radius die Löcher\ngeneriert werden sollen\n-1 wählt automatisch einen passenden radius"),
	AUTOSETUP_SPAWNS_SIDEBLOCKID(ConfigSettingSection.AUTOSETUP, "spawns.sideblock.material", XMaterial.GRASS_BLOCK, "Welche Block-ID der Block,\nden man abbaut haben soll"),
	AUTOSETUP_TIME_HOUR(ConfigSettingSection.AUTOSETUP, "autostart.time.hour", -1, "Um welche Zeit der Stunde der\nAutoStart gesetzt werden soll"),
	AUTOSETUP_TIME_MINUTE(ConfigSettingSection.AUTOSETUP, "autostart.time.minute", -1, "Um welche Zeit der Minute der\nAutoStart gesetzt werden soll"),
	WORLD_SPAWNS_GENERATE_Y_TOLERANCE(ConfigSettingSection.AUTOSETUP, "spawnGeneratorYTolerance", 8, "Wie viel Hoehe die Spawns von einander\nAbstand haben duerfen beim\ngenerieren der Spawns\nBeispiel: Spawn ist 10 Bloecke hoeher als andere\n->wird weiter nach Terrain gesucht"),

	BACKPACK_PLAYER_DROP_ON_DEATH(ConfigSettingSection.BACKPACKS, "backpackPlayerDropOnDeath", true, "Ob der Inhalt des Spieler-Rucksacks beim Tod des Spielers gedroppt werden soll."),

	// BACKPACKS
	BACKPACK_PLAYER_ENABLED(ConfigSettingSection.BACKPACKS, "backpackPlayerEnabled", false, "Wenn dies aktiviert ist, haben Spieler einen eigenen Rucksack,\nauf den sie mit /varo bp zugreifen koennen.\nDieser wird pro Spieler und nicht pro Team gespeichert."),
	BACKPACK_PLAYER_SIZE(ConfigSettingSection.BACKPACKS, "backpackPlayerSize", 54, "Groesse des Spieler-Rucksacks (Muss ein Vielfaches von 9 und <=54 sein)"),

	BACKPACK_TEAM_DROP_ON_DEATH(ConfigSettingSection.BACKPACKS, "backpackTeamDropOnDeath", true, "Ob der Inhalt des Team-Rucksacks beim Tod des letzten Teammitglieds gedroppt werden soll."),
	BACKPACK_TEAM_ENABLED(ConfigSettingSection.BACKPACKS, "backpackTeamEnabled", false, "Wenn dies aktiviert ist, haben Teams einen eigenen Rucksack,\nauf den sie mit /varo bp zugreifen koennen.\nDieser wird pro Team und nicht pro Spieler gespeichert."),

	BACKPACK_TEAM_SIZE(ConfigSettingSection.BACKPACKS, "backpackTeamSize", 54, "Groesse des Team-Rucksacks (Muss ein Vielfaches von 9 und <=54 sein)"),

	// CHAT
	CAN_CHAT_BEFORE_START(ConfigSettingSection.CHAT, "canChatBeforeStart", true, "Ob die Spieler vor Start chatten koennen."),
	BLOCK_CHAT_ADS(ConfigSettingSection.CHAT, "blockChatAds.enabled", true, "Wenn aktiviert, koennen keine Links in den oeffentlichen Chat gepostet werden."),
	BLOCK_CHAT_ADS_AGRESSIVE(ConfigSettingSection.CHAT, "blockChatAds.agressive", true, "Wenn aktiviert, wird z.b. nicht nur \"varoplugin.de\" sondern auch \"varoplugin, de\" geblockt. Es kann aber in seltenen fällen zu false positives kommen. Diese Einstellung ist nur relevant wenn blockChatAds aktiviert ist."),
	CHAT_COOLDOWN_IF_STARTED(ConfigSettingSection.CHAT, "chatCooldown.ifStarted", false, "Ob der Chatcooldown auch aktiviert sein\\nsoll wenn das Projekt gestartet wurde.", "chatCooldownIfStarted"),
	CHAT_COOLDOWN_IN_SECONDS(ConfigSettingSection.CHAT, "chatCooldown.inSeconds", 3, "Der Cooldown der Spieler im Chat,\nbevor sie wieder eine Nachricht senden koennen.\nOff = -1", "chatCooldownInSeconds"),

	// WORLD
	BLOCK_DESTROY_LOGGER(ConfigSettingSection.WORLD, "blockDestroyLogger", true, "Loggt alle abgebauten Bloecke, die ihr\nunten eintragt unter 'oreLogger.yml'", true),
	BLOCK_USER_PORTALS(ConfigSettingSection.WORLD, "blockUserPortals", true, "Ob geblockt werden soll, dass\nSpieler ihre eigenen Portal\nbauen koennen"),
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

	CAN_MOVE_BEFORE_START(ConfigSettingSection.START, "canMoveBeforeStart", false, "Ob die Spieler sich vor Start bewegen koennen."),
	CANWALK_PROTECTIONTIME(ConfigSettingSection.PROTECTIONS, "canWalkOnJoinProtection", false, "Ob Spieler waehrend der Joinschutzzeit laufen koennen."),
	CATCH_UP_SESSIONS(ConfigSettingSection.JOIN_SYSTEMS, "catchUpSessions", false, "NUR FÜR ERSTES JOIN SYSTEM\nStellt ein, ob man verpasste Folgen nachholen darf."),

	// SERVERLIST
	CHANGE_MOTD(ConfigSettingSection.SERVER_LIST, "changeMotd", true, "Ob das Plugin die Motd veraendern soll.\nHinweis: du kannst die Motd in den Language-Dateien ändern."),
	CHAT_TRIGGER(ConfigSettingSection.TEAMS, "chatTrigger", "#", "Definiert den Buchstaben am Anfang einer\nNachricht, der den Teamchat ausloest."),

	// COMBATLOG
	COMBATLOG_TIME(ConfigSettingSection.COMBATLOG, "combatlogTime", 30, "Zeit, nachdem sich ein Spieler\nnach dem Kampf wieder ausloggen kann.\nOff = -1"),

	// DEATH
	DEATH_SOUND_ENABLED(ConfigSettingSection.DEATH, "deathSound.enabled", false, "Ob ein Sound fuer alle abgespielt werden soll,\nsobald ein Spieler stirbt", true),
	DEATH_SOUND(ConfigSettingSection.DEATH, "deathSound.sound", XSound.ENTITY_WITHER_AMBIENT.get(), "Sound der abgespielt werden soll", true),
	DEBUG_OPTIONS(ConfigSettingSection.OTHER, "debugOptions", false, "Ob Debug Funktionen verfuegbar sein sollen.\nVorsicht: Mit Bedacht oder nur\nauf Anweisung nutzen!"),
	BLOCK_ADVANCEMENTS(ConfigSettingSection.OTHER, "blockAdvancements", true, "Ob Advancements deaktiviert werden sollen [1.12+]"),

	// DISCONNECT
	DISCONNECT_PER_SESSION(ConfigSettingSection.DISCONNECT, "maxDisconnectsPerSessions", 3, "Wie oft ein Spieler pro\nSession maximal disconnecten darf,\nbevor er bestraft wird.Off = -1"),
	BAN_AFTER_DISCONNECT_MINUTES(ConfigSettingSection.DISCONNECT, "banAfterDisconnectMinutes", -1, "Wenn ein Spieler disconnected,\nob er nach dieser Anzahl an Minuten entfernt werden soll.\nOff = -1"),
	NO_DISCONNECT_PING(ConfigSettingSection.DISCONNECT, "noDisconnectPing", 200, "Ab welchem Ping ein Disconnect\nnicht mehr als einer zaehlt."),
	DISCONNECT_IGNORE_KICK(ConfigSettingSection.DISCONNECT, "ignoreKick", false, "Ob ein Disconnect ignoriert werden\nsoll wenn der Spieler gekickt wurde."),

	// DISCORDBOT
	DISCORDBOT_ENABLED(ConfigSettingSection.DISCORD, "discordBotEnabled", false, "Ob der DiscordBot fuer Events aktiviert werden soll.\nHinweis: bitte fuer diesen Informationen unten ausfuellen"),
	DISCORDBOT_ENABLED_PRIVILIGES(ConfigSettingSection.DISCORD, "enabledPriviliges", false, "Aktiviere diesen Eintrag nur, wenn\ndu die besonderen Rechte\ndes Discordbots aktiviert hast\n-> Priviliged Gateway Intents\nAktiviere dies, sobald das Plugin meldet\ndass die Nutzer nicht auf dem Discord sind."),
	DISCORDBOT_EVENT_ALERT(ConfigSettingSection.DISCORD, "eventChannel.alert", -1L, "ID's des Channels, wo die Benachrichtigungen gepostet werden.\n-1= EventChannelID wird genutzt"),
	DISCORDBOT_EVENT_BORDER(ConfigSettingSection.DISCORD, "eventChannel.border", -1L, "ID's des Channels, wo die Border Veränderungen gepostet werden.\n-1= EventChannelID wird genutzt"),
	DISCORDBOT_EVENT_DEATH(ConfigSettingSection.DISCORD, "eventChannel.death", -1L, "ID's des Channels, wo die Tode gepostet werden.\n-1= EventChannelID wird genutzt"),
	DISCORDBOT_EVENT_JOIN_LEAVE(ConfigSettingSection.DISCORD, "eventChannel.joinLeave", -1L, "ID's des Channels, wo die Joins/Leaves gepostet werden.\n-1= EventChannelID wird genutzt"),

	DISCORDBOT_EVENT_KILL(ConfigSettingSection.DISCORD, "eventChannel.kill", -1L, "ID's des Channels, wo die Kills gepostet werden.\n-1= EventChannelID wird genutzt"),
	DISCORDBOT_EVENT_STRIKE(ConfigSettingSection.DISCORD, "eventChannel.strike", -1L, "ID's des Channels, wo die Strikes gepostet werden.\n-1= EventChannelID wird genutzt"),
	DISCORDBOT_EVENT_WIN(ConfigSettingSection.DISCORD, "eventChannel.win", -1L, "ID's des Channels, wo die Winnachricht gepostet wird.\n-1= EventChannelID wird genutzt"),
	DISCORDBOT_EVENT_YOUTUBE(ConfigSettingSection.DISCORD, "eventChannel.youtube", -1L, "ID's des Channels, wo die YT-Videos gepostet werden.\n-1= EventChannelID wird genutzt"),
	DISCORDBOT_EVENT_CHANNELID(ConfigSettingSection.DISCORD, "eventChannelID", -1L, "Gib hier die ChannelID des Channels an,\nin welchen der Bot Events posten soll.\nRechtsklick auf den Channel -> 'Copy ChannelID'\nWenn Option nicht vorhanden, schalte\n'developer options' in den Einstellungen von Discord ein."),
	DISCORDBOT_GAMESTATE(ConfigSettingSection.DISCORD, "gameState", "Varo | Plugin by Cuuky - Contributors: Korne127, UeberallGebannt", "Stelle hier ein, was der Bot\nim Spiel als Name haben soll."),
	DISCORDBOT_INVITELINK(ConfigSettingSection.DISCORD, "inviteLink", "ENTER LINK HERE", "Stelle hier deinen Link zum Discord ein"),

	DISCORDBOT_ADD_POKAL_ON_WIN(ConfigSettingSection.DISCORD, "addPokalOnWin", true, "Ob den Gewinnern Pokale hinter den\nNamen gesetzt werden sollen."),
    DISCORDBOT_ANNOUNCEMENT_CHANNELID(ConfigSettingSection.DISCORD, "announcementChannelID", -1L, "Gib hier den Channel an,\nin dem Nachrichten vom AutoStart geschrieben werden.\nBeispiel: Varo startet in ... Minuten."),
    DISCORDBOT_ANNOUNCEMENT_PING_ROLEID(ConfigSettingSection.DISCORD, "announcementPingRoleID", -1L, "Gib hier die ID der Rolle ein, welche\nbei Nachrichtenauf Discord gepingt werden sollen.\nHinweis: -1 = everyone"),
	
	DISCORDBOT_MESSAGE_RANDOM_COLOR(ConfigSettingSection.DISCORD, "randomMessageColor", false, "Ob die Nachrichten eine zufaellige Farbe haben sollen (nur bei Embeds)"),
	DISCORDBOT_USE_EMBEDS(ConfigSettingSection.DISCORD, "useEmbeds", true, "Ob die Nachrichten als Embed gesendet werden sollen"),
	DISCORDBOT_SHOW_PLAYER_HEADS(ConfigSettingSection.DISCORD, "showPlayerHeads", true, "Nutzt die Minotar API (https://minotar.net/) um in einigen Nachrichten die Köpfe von Spielern anzuzeigen.\nFunktioniert nur mit Embeds."),
	DISCORDBOT_RESULT_CHANNELID(ConfigSettingSection.DISCORD, "resultChannelID", -1L, "Gib hier die Channel ID an, in der spaeter\ndie Logs und die Gewinner gepostet werden sollen."),
	DISCORDBOT_SERVERID(ConfigSettingSection.DISCORD, "serverGuildID", -1L, "Gib hier die ServerID deines Servers an.\nHinweis: Vorgangsweise, um die ID zu bekommen wie beim Channel."),

	DISCORDBOT_SET_TEAM_AS_GROUP(ConfigSettingSection.DISCORD, "setTeamAsGroup", false, "Ob die Spieler, die ein Team bekommen,\ndiesen auch als Gruppe im Discord bekommen sollen."),
	DISCORDBOT_TOKEN(ConfigSettingSection.DISCORD, "botToken", "ENTER TOKEN HERE", "Gib hier den Token an, welchen du auf\nder Bot Seite und 'create bot user' bekommst.", false, true),

	DISCORDBOT_VERIFYSYSTEM(ConfigSettingSection.DISCORD, "verify.enabled", false, "Ob das Verify System aktiviert werden soll.\nDieses laesst die Spieler sich mit Discord-Accounts verbinden."),
	DISCORDBOT_VERIFYSYSTEM_OPTIONAL(ConfigSettingSection.DISCORD, "verify.optional", false, "Ob das Verify-System optional sein soll\nWenn deaktiviert: Nur verifizierte Spieler koennen\nden Server betreten"),
	DISCORDBOT_USE_VERIFYSTSTEM_MYSQL(ConfigSettingSection.DISCORD, "verify.mysql.enabled", false, "Ob fuer die Speicherung der BotRegister\neine MySQL Datenbank genutzt werden soll"),
	DISCORDBOT_VERIFY_DATABASE(ConfigSettingSection.DISCORD, "verify.mysql.database", "DATABASE_HERE", "Datenbank, wo die BotRegister\ngespeichert werden sollen", false, true),
	DISCORDBOT_VERIFY_HOST(ConfigSettingSection.DISCORD, "verify.mysql.host", "HOST_HERE", "MySQL Host, zu welchem das Plugin sich verbinden soll", false, true),
	DISCORDBOT_VERIFY_PASSWORD(ConfigSettingSection.DISCORD, "verify.mysql.password", "PASSWORD_HERE", "Passwort fuer MySQL Nutzer,\nwelcher auf die Datenbank zugreifen soll", false, true),
	DISCORDBOT_VERIFY_USER(ConfigSettingSection.DISCORD, "verify.mysql.user", "USER_HERE", "MySQL Nutzer, welcher auf die Datenbank zugreifen soll", false, true),

	DO_DAILY_BACKUPS(ConfigSettingSection.MAIN, "dailyBackups", true, "Es werden immer Backups um 'ResetHour' gemacht."),

	DO_RANDOMTEAM_AT_START(ConfigSettingSection.START, "doRandomTeamAtStart", -1, "Groesse der Teams, in die die Teamlosen beim Start eingeordnet werden.\nAusgeschaltet = -1"),
	DO_SORT_AT_START(ConfigSettingSection.START, "doSortAtStart", true, "Ob beim Start /sort ausgefuehrt werden soll."),
	DO_SPAWN_GENERATE_AT_START(ConfigSettingSection.START, "doSpawnGenerateAtStart", false, "Ob die Spawnloecher am Start basierend auf den\nderzeitigen Spielern generiert werden sollen"),
	FAKE_MAX_SLOTS(ConfigSettingSection.SERVER_LIST, "fakeMaxSlots", -1, "Setzt die maximalen Slots des Servers gefaked.\nOff = -1"),
	FINALE_PROTECTION_TIME(ConfigSettingSection.FINALE, "finaleProtectionTime", 30, "Laenge der Schutzzeit nachdem alle Spieler in die Mitte teleportiert werden."),

	// TEAMS
	FRIENDLYFIRE(ConfigSettingSection.TEAMS, "friendlyFire", false, "Zufuegen von Schaden unter Teamkameraden."),

	// JOINSYSTEMS
	IGNORE_JOINSYSTEMS_AS_OP(ConfigSettingSection.JOIN_SYSTEMS, "ignoreJoinSystemsAsOP", true, "Ob OP-Spieler die JoinSysteme ignorieren."),
	JOIN_AFTER_HOURS(ConfigSettingSection.JOIN_SYSTEMS, "joinAfterHours", -1, "ZWEITES JOIN SYSTEM\nStellt ein, nach wie vielen Stunden\nSpieler regulaer wieder den Server betreten duerfen"),

	// PROTECTIONS
	JOIN_PROTECTIONTIME(ConfigSettingSection.PROTECTIONS, "joinProtectionTime", 10, "Laenge der Schutzzeit in Sekunden beim Betreten des Servers.\nOff = -1"),
	KICK_AT_SERVER_CLOSE(ConfigSettingSection.JOIN_SYSTEMS, "kickAtServerClose", false, "Kickt den Spieler, sobald er ausserhalb\ndererlaubten Zeit auf dem Server ist."),
	KICK_DELAY_AFTER_DEATH(ConfigSettingSection.DEATH, "kickDelayAfterDeath", -1, "Zeit in Sekunden, nach der ein Spieler\nnach Tod gekickt wird.\nOff = -1"),

	KILL_ON_COMBATLOG(ConfigSettingSection.COMBATLOG, "killOnCombatlog", true, "Ob ein Spieler, wenn er\nsich in der oben genannten Zeit ausloggt,\ngetoetet werden soll."),
	KILLER_ADD_HEALTH_ON_KILL(ConfigSettingSection.DEATH, "killerHealthAddOnKill", -1, "Anzahl halber Herzen, die der Killer nach Kill bekommt.\nOff = -1"),
	LOG_REPORTS(ConfigSettingSection.REPORT, "logReports", true, "Ob alle Reports in der reports.yml\nfestgehalten werden sollen."),
	MASS_RECORDING_TIME(ConfigSettingSection.JOIN_SYSTEMS, "massRecordingTime", 15, "Die Laenge der Massenaufnahme, in der alle joinen koennen."),
	MIN_BORDER_SIZE(ConfigSettingSection.BORDER, "minBorderSize", 300, "Wie klein die Border maximal werden kann."),

	MINIMAL_SPECTATOR_HEIGHT(ConfigSettingSection.OTHER, "minimalSpectatorHeight", 70, "Wie tief die Spectator maximal fliegen koennen.\nOff = 0"),
	SPECTATOR_CHAT(ConfigSettingSection.OTHER, "spectatorChat", false, "Ob Spectator in den Chat schreiben können"),

	NAMETAGS_ENABLED(ConfigSettingSection.MAIN, "nametags.enabled", true, "Ob das Plugin die Nametags ueber\nden Koepfen der Spieler veraendern soll.\nHinweis: du kannst diese in den Language-Dateien einstellen.", true),
	NAMETAGS_VISIBLE_DEFAULT(ConfigSettingSection.MAIN, "nametags.visible.default", true, "Ob NameTags sichtbar sein sollen"),
	NAMETAGS_VISIBLE_TEAM(ConfigSettingSection.MAIN, "nametags.visible.team", true, "Ob NameTags für das eigene Team sichtbar sein sollen\nwenn nametags.visible.default deaktiviert ist"),
	NAMETAGS_VISIBLE_SPECTATOR(ConfigSettingSection.MAIN, "nametags.visible.spectator", true, "Ob NameTags für Spectator sichtbar sein sollen\nwenn nametags.visible.default deaktiviert ist"),

	NO_ACTIVITY_DAYS(ConfigSettingSection.ACTIVITY, "noActivityDays", -1, "Nach wie vielen Tagen ohne Aktiviaet auf dem\nServer der Spieler gemeldet werden soll.\nOff = -1"),

	NO_KICK_DISTANCE(ConfigSettingSection.OTHER, "noKickDistance", 30, "In welcher Distanz zum Gegner\nein Spieler nicht gekickt wird.\nOff = 0"),
	NO_SATIATION_REGENERATE(ConfigSettingSection.OTHER, "noSatiationRegenerate", false, "Ob Spieler nicht durch Saettigung regenerieren\nkoennen sondern nur durch Gapple etc."),

	// OFFLINEVILLAGER
	OFFLINEVILLAGER(ConfigSettingSection.OFFLINEVILLAGER, "enableOfflineVillager", false, "Ob Villager, welche representativ fuer den Spieler waehrend\nseiner Onlinezeit auf dem Server warten und gekillt werden koennen."),

	ONLY_JOIN_BETWEEN_HOURS(ConfigSettingSection.JOIN_SYSTEMS, "onlyJoinBetweenHours", false, "FÜR BEIDE JOIN SYSTEME\nStellt ein, ob Spieler nur zwischen\n2 unten festgelegten Zeiten joinen duerfen."),
	ONLY_JOIN_BETWEEN_HOURS_HOUR1(ConfigSettingSection.JOIN_SYSTEMS, "onlyJoinBetweenHoursHour1", 14, "Erste Uhrzeit, zwischen welchen\ndie Spieler joinen duerfen."),
	ONLY_JOIN_BETWEEN_HOURS_MINUTE1(ConfigSettingSection.JOIN_SYSTEMS, "onlyJoinBetweenHoursMinute1", 0, "Erste Minuten-Uhrzeit, zwischen welchen\ndie Spieler joinen duerfen."),
	ONLY_JOIN_BETWEEN_HOURS_HOUR2(ConfigSettingSection.JOIN_SYSTEMS, "onlyJoinBetweenHoursHour2", 16, "Zweite Uhrzeit, zwischen welchen\ndie Spieler joinen duerfen."),
	ONLY_JOIN_BETWEEN_HOURS_MINUTE2(ConfigSettingSection.JOIN_SYSTEMS, "onlyJoinBetweenHoursMinute2", 0, "Zweite Minuten-Uhrzeit, zwischen welchen\ndie Spieler joinen duerfen."),

	OUTSIDE_BORDER_SPAWN_TELEPORT(ConfigSettingSection.BORDER, "outsideBorderSpawnTeleport", true, "Ob, wenn ein Spieler ausserhalb der Border joint, er in die Mitte teleportiert werden soll."),
	PLAY_TIME(ConfigSettingSection.MAIN, "playTime", 15, "Zeit in Minuten, wie lange die Spieler\npro Session auf dem Server spielen koennen.\nUnlimited = -1"),

	// OTHER
	PLAYER_CHEST_LIMIT(ConfigSettingSection.OTHER, "playerChestLimit", 2, "Wie viele Chests ein Team\nregistrieren darf.\nOff = 0, Unendlich = -1"),
	PLAYER_FURNACE_LIMIT(ConfigSettingSection.OTHER, "playerFurnaceLimit", -1, "Wie viele Furnaces ein\nSpieler registrieren darf.\nOff = 0, Undendlich = -1"),
	PLAYER_SPECTATE_AFTER_DEATH(ConfigSettingSection.DEATH, "playerSpectateAfterDeath", false, "Ob ein Spieler nach seinem Tod Spectator wird."),
	PLAYER_SHOW_DEATH_SCREEN(ConfigSettingSection.DEATH, "showDeathScreen", false, "Ob ein Spieler seinen death screen sehen soll."),

	// FINALE
	PLAYER_SPECTATE_IN_FINALE(ConfigSettingSection.FINALE, "playerSpectateInFinale", true, "Ob die toten Spieler waehrend des Finales spectaten duerfen."),
	FINALE_ALLOW_NETHER(ConfigSettingSection.FINALE, "allowNether", false, "Ob Spieler während des Finales den Nether betreteb dürfen."),
	FINALE_FREEZE(ConfigSettingSection.FINALE, "freeze", true, "Ob Spieler während des Starts gefreezed werden sollen."),
	
	POST_COORDS_DAYS(ConfigSettingSection.ACTIVITY, "postCoordsDays", -1, "Postet nach den genannten Tagen\nvon allen Spielern die Koordinatenum die Uhrzeit,\num der auch Sessions etc. geprueft werden"),
	PRE_PRODUCE_SESSIONS(ConfigSettingSection.JOIN_SYSTEMS, "preProduceSessions", 0, "FÜR BEIDE JOIN SYSTEME\nStellt ein, wie viele Folgen der Spieler zusaetzlich zu\nden Regulaeren vorproduzieren darf."),

	// MAIN
	PREFIX(ConfigSettingSection.MAIN, "prefix", "&7[&3Varo&7] ", "Prefix, der im Chat bzw. vor\nden Nachrichten angezeigt wird."),
	PROJECT_NAME(ConfigSettingSection.MAIN, "projectname", "Varo", "Name deines Projektes, der in den\nNachrichten, am Scoreboard, etc. steht."),
	PROJECTNAME_COLORCODE(ConfigSettingSection.MAIN, "projectnameColorcode", "&3", "Dieser Farbcode ist der Massgebende,\nder ueberall im Projekt verwendet wird.."),
	MAIN_LANGUAGE(ConfigSettingSection.MAIN, "language.main", "de_de", "Alle Sprachentypen hier zu finden: https://minecraft-el.gamepedia.com/Language"),
	MAIN_LANGUAGE_ALLOW_OTHER(ConfigSettingSection.MAIN, "language.allowOther", true, "Ob jeder Spieler eine eigene Sprache\nnutzen darf"),
	RANDOM_CHEST_FILL_RADIUS(ConfigSettingSection.WORLD, "randomChestFillRadius", -1, "In welchem Radius die Kisten um den\nSpawn mit den in der Config angegebenen\nItems befuellt werden sollen.\nOff = -1"),
	RANDOM_CHEST_MAX_ITEMS_PER_CHEST(ConfigSettingSection.WORLD, "randomChestMaxItems", 5, "Wie viele Items in eine Kiste sollen."),
	REMOVE_HIT_COOLDOWN(ConfigSettingSection.OTHER, "removeHitDelay", false, "Entfernt den 1.9+ Hit delay"),
	REMOVE_PLAYERS_ABSENT_AT_START(ConfigSettingSection.START, "removePlayersAbsentAtStart", false, "Ob das Plugin alle Spieler, die nicht beim\nStart dabei sind vom Projekt entferenen soll."),
	REPORT_SEND_DELAY(ConfigSettingSection.REPORT, "reportDelay", 30, "Zeit in Sekunden, die ein Spieler warten muss,\nbevor er einen neuen Spieler reporten kann.\nOff = -1"),
	REPORT_STAFF_MEMBER(ConfigSettingSection.REPORT, "reportStaffMember", true, "Ob Spieler mit der Permission\n'varo.report' reportet werden koennen."),
	CRACKED_SERVER(ConfigSettingSection.MAIN, "crackedServer", false, "Ob der Server mit Mojang-UUIDs arbeiten soll.\nVorsicht: Spieler müssen neu eingetragen werden."),

	// REPORT
	REPORTSYSTEM_ENABLED(ConfigSettingSection.REPORT, "enabled", true, "Ob das Report-System angeschaltet sein soll."),
	RESET_SESSION_HOUR(ConfigSettingSection.MAIN, "resetSessionHour", 1, "Um welche Uhrzeit (24h) der Server den\nSpieler neue Sessions etc. gibt"),
	
	RESPAWN_PROTECTION(ConfigSettingSection.DEATH, "respawnProtection", 120, "Wie lange in Sekunden Spieler\nnach Respawn geschuetzt sind"),
	SCOREBOARD(ConfigSettingSection.MAIN, "scoreboard", true, "Ob das Scoreboard aktiviert sein soll.\nHinweis: das Scoreboard kannst du in\nder scoreboard.yml bearbeiten.", true),
	ACTIONBAR(ConfigSettingSection.MAIN, "actionbar", true, "Ob die Actionbar aktiviert sein soll.\nHinweis: die Actionbar kannst du in\nder actionbar.yml bearbeiten.", true),
	SESSIONS_PER_DAY(ConfigSettingSection.JOIN_SYSTEMS, "sessionsPerDay", 1, "ERSTES JOIN SYSTEM\nStellt ein, wie oft Spieler am Tag\nden Server regulaer betreten duerfen."),

	NAMETAGS_OVER_SPAWN(ConfigSettingSection.WORLD, "setNameTagOverSpawn", true, "Ob Nametags ueber den\nSpawns erscheinen sollen"),
	NAMETAG_SPAWN_HEIGHT(ConfigSettingSection.WORLD, "nametagSpawnHeight", 3, "Wie hoch ueber den Spawns\ndie Nametags sein sollen"),

	SPAWN_PROTECTION_RADIUS(ConfigSettingSection.WORLD, "spawnProtectionRadius", 0, "Radius, in dem die Spieler\nnicht am Spawn bauen koennen."),

	SPAWN_TELEPORT_JOIN(ConfigSettingSection.START, "spawnTeleportAtLobbyPhase", true, "Ob die Spieler, wenn\nfuer sie ein Spawn gesetzt wurde auch in\ndiesem spawnen sollen, sobald sie joinen."),
	LOBBY_INTERACT_VEHICLES(ConfigSettingSection.START, "lobbyInteractVehicles", true, "Ob Spieler in der lobby mit Booten und Minecrats interagieren können sollen"),
	START_AT_PLAYERS(ConfigSettingSection.START, "startAtPlayers", -1, "Startet das Projekt automatisch wenn die\nAnzahl der Online Spieler dieser entspricht."),

	// START
	STARTCOUNTDOWN(ConfigSettingSection.START, "startCountdown", 30, "Wie lange der Startcountdown\nbei Start in Sekunden ist."),
	STARTPERIOD_PROTECTIONTIME(ConfigSettingSection.PROTECTIONS, "startperiodProtectiontime", -1, "Laenge der Schutzzeit nach dem Start.\nOff = -1"),
	STARTPERIOD_PROTECTIONTIME_BROADCAST_INTERVAL(ConfigSettingSection.PROTECTIONS, "startperiodProtectiontimeBcInterval", 60, "In welchen Sekundenabstaenden die restliche Schutzzeit\ngebroacastet werden soll"),
	STOP_SERVER_ON_WIN(ConfigSettingSection.DEATH, "stopServerOnWin", -1, "Zeit in Sekunden, nachdem der Server nach\nWin eines Teams heruntergefahren wird."),
	
	// STRIKE
	STRIKE_BAN_AFTER_STRIKE_HOURS(ConfigSettingSection.STRIKE, "banOnPostHours", -1, "Fuer wie viele Stunden die Spieler\nnach einem Strike gestriket werden"),
	STRIKE_CLEAR_ARMOR(ConfigSettingSection.STRIKE, "clearArmor", true, "Ob die Rüstung beim 2ten Strike\nauch gecleart werden soll"),
	STRIKE_BAN_AT_POST(ConfigSettingSection.STRIKE, "banAtPost", true, "Ob der Spieler beim Posten des Strikes\num die oben genannte Zahl gebannt werden soll.\nSonst wird dieser beim Erhalten gebannt"),
	STRIKE_ON_BLOODLUST(ConfigSettingSection.ACTIVITY, "strikeOnBloodlust", false, "Ob der Spieler nach den oben\ngenannten Tagen ohne Gegnerkontakt\ngestriket werden soll."),
	STRIKE_ON_COMBATLOG(ConfigSettingSection.COMBATLOG, "strikeOnCombatlog", true, "Ob ein Spieler, wenn er sich in\nder oben genannten Zeit ausloggt,\ngestriket werden soll."),
	STRIKE_ON_DISCONNECT(ConfigSettingSection.DISCONNECT, "strikeOnMaxDisconnect", false, "Ob ein Spieler gestriket werden soll\nwenn zu oft disconnected wurde."),
	STRIKE_ON_NO_ACTIVITY(ConfigSettingSection.ACTIVITY, "strikeOnNoActivity", false, "Ob der Spieler nach den oben genannten Tagen\nohne Aktivitaet auf dem Servergestriket werden soll."),
	STRIKE_POST_RESET_HOUR(ConfigSettingSection.STRIKE, "postAtResetHour", false, "Ob die Strikes erst um die ResetHour gepostet werden sollen"),
	
	SUPPORT_PLUGIN_ADS(ConfigSettingSection.MAIN, "supportPluginAds", false, "Werbung wird im Plugin mit eingebaut, was das Plugin,\nalso mich, supportet. Danke an alle, die das aktivieren :3"),
	
	TEAM_MAX_NAME_LENGTH(ConfigSettingSection.TEAMS, "teamNameLength", 10, "Maximal Laenge eines Teamnamens."),
	TEAM_PLACE_SPAWN(ConfigSettingSection.TEAMS, "teamPlaceSpawn", -1, "Anzahl an Spawnplaetzen in einer Teambasis\nWenn angeschaltet (nicht -1) wird eine Luecke fuer fehlende Teammitglieder gelassen.\nAnschalten, wenn jedes Team einen eigenen Spawnplatz besitzt und es keinen grossen Kreis gibt."),
	TEAM_LIFES(ConfigSettingSection.DEATH, "teamLife.default", 1D, "Wie viele Leben ein Team hat", "teamLifes"),
	MAX_TEAM_LIFES(ConfigSettingSection.DEATH, "teamLife.maxLifes", 5D, "Wie viele Leben ein maximal haben kann"),
	DEATH_LIGHTNING_EFFECT(ConfigSettingSection.DEATH, "deathLightningEffect", true, "Ob beim Tod eines Spielers\nein Blitz-Effekt kommen soll"),
	DEATH_TIME_ADD(ConfigSettingSection.DEATH, "deathTimeAdd", -1, "Zusätzliche zeit die der Killer\ndurch einen kill bekommt (in Sekunden)"),
	DEATH_TIME_MIN(ConfigSettingSection.DEATH, "deathTimeMin", 30, "Zeit die der Killer\nnach einem Kill nicht gekickt werden soll (in Sekunden)"),

	TEAMREQUEST_EXPIRETIME(ConfigSettingSection.TEAMS, "teamRequest.expiretime", 30, "Die Zeit in Sekunden, nachdem eine Teamanfrage ablaufen soll."),
	TEAMREQUEST_MAXTEAMMEMBERS(ConfigSettingSection.TEAMS, "teamRequest.maxTeamMembers", 2, "Anzahl an Teammitglieder pro Team."),
	TEAMREQUEST_ENABLED(ConfigSettingSection.TEAMS, "teamRequest.enabled", false, "Ob Spieler sich gegenseitig in Teams\nmit /tr einladen koennen.\nSehr gute Funktion fuer ODV's."),
	TEAMREQUEST_RANDOM_NAME(ConfigSettingSection.TEAMS, "teamRequest.randomName", false, "Ob ein zufälliger Teamname festgelegt werden soll."),
	TEAMREQUEST_LOBBYITEMS(ConfigSettingSection.TEAMS, "teamRequest.items.enabled", true, "Ob die Spieler Items in\nder Lobby erhalten sollen,\nwomit sie sich einladen können", "teamRequest.lobbyItems"),
	TEAMREQUEST_LOBBYITEM_INVITE_ITEM(ConfigSettingSection.TEAMS, "teamRequest.items.invite.item", XMaterial.DIAMOND_SWORD.parseItem(), "Item um andere Spieler in ein Team einzuladen"),
	TEAMREQUEST_LOBBYITEM_INVITE_SLOT(ConfigSettingSection.TEAMS, "teamRequest.items.invite.slot", 0, "Slot des Items um andere Spieler in ein Team einzuladen"),
	TEAMREQUEST_LOBBYITEM_LEAVE_ITEM(ConfigSettingSection.TEAMS, "teamRequest.items.leave.item", XMaterial.REDSTONE.parseItem(), "Item zum Verlassen eines Teams"),
	TEAMREQUEST_LOBBYITEM_LEAVE_SLOT(ConfigSettingSection.TEAMS, "teamRequest.items.leave.slot", 8, "Slot des Items zum Verlassen eines Teams"),
	TEAMREQUEST_LOBBYITEM_RENAME_ENABLED(ConfigSettingSection.TEAMS, "teamRequest.items.rename.enabled", true, "Ob die spieler in\nder Lobby ein Item haben\nsollen um ihren Teamnamen\nzu ändern"),
	TEAMREQUEST_LOBBYITEM_RENAME_ITEM(ConfigSettingSection.TEAMS, "teamRequest.items.rename.item", XMaterial.NAME_TAG.parseItem(), "Item zum Umbenennen eines Teams"),
	TEAMREQUEST_LOBBYITEM_RENAME_SLOT(ConfigSettingSection.TEAMS, "teamRequest.items.rename.slot", 4, "Slot des Items zum Umbenennen eines Teams"),

	TELEGRAM_BOT_TOKEN(ConfigSettingSection.TELEGRAM, "botToken", "ENTER TOKEN HERE", "Setzt den Bot Token des Telegrambots", false, true),

	// TELEGRAM
	TELEGRAM_ENABLED(ConfigSettingSection.TELEGRAM, "telegrambotEnabled", false, "Ob der Telegrambot aktiviert werden soll."),
	TELEGRAM_EVENT_CHAT_ID(ConfigSettingSection.TELEGRAM, "eventChatId", -1L, "In diesen Chat werden alle Events gepostet."),
	TELEGRAM_VIDEOS_CHAT_ID(ConfigSettingSection.TELEGRAM, "videosChatId", -1L, "Hier kannst du die ID des Chats angeben, wo\ndie Videos der User gepostet werden sollen."),
	TRIGGER_FOR_GLOBAL(ConfigSettingSection.TEAMS, "triggerForGlobal", false, "Wenn aktiviert, wird standardmaessig in den Teamchat geschrieben und mit dem Triggerbuchstaben am Anfang in den globalen Chat, ansonsten umgekehrt."),
	UNREGISTERED_PLAYER_JOIN(ConfigSettingSection.MAIN, "unregisteredPlayerJoin", true, "Ob unregistrierte Spieler joinen duerfen."),
	UNREGISTERED_PLAYER_JOIN_DURING_GAME(ConfigSettingSection.MAIN, "unregisteredPlayerJoinDuringGame", false, "Ob unregistrierte Spieler wärend des Projektes nachträglich joinen dürfen."),

	// YOUTUBE
	YOUTUBE_ENABLED(ConfigSettingSection.YOUTUBE, "enabled", false, "Checkt jeden Tag bei den Spielern,\ndie einen YouTube Link registriert haben,\nnach den Uploads"),
	YOUTUBE_STRIKE(ConfigSettingSection.YOUTUBE, "strike", false, "Ob ein spieler einen Strike erhalten soll wenn er kein video hochläd"),
	YOUTUBE_VIDEO_IDENTIFIER(ConfigSettingSection.YOUTUBE, "videoIdentifier", "Varo", "Was die Videotitel enthalten\nmüssen, um als Varo-Video zu gelten."),

	// CUSTOM COMMAND
	CUSTOMCOMMAND_USEPREFIX(ConfigSettingSection.OTHER, "customCommandUsePrefix", true, "Ob bei allen Custom Commands automatisch\nder Prefix genutzt werden soll."),

	// TABLIST
	TABLIST(ConfigSettingSection.TABLIST, "tablist", true, "Ob das Plugin die Tablist modfizieren soll\nMuss aktiviert sein, um folgende Einstellungen\nvorzunehmen.", true),
	TABLIST_USE_HEADER(ConfigSettingSection.TABLIST, "useHeader", true, "Ob die Tablist einen Header haben soll.\nErfordert config reload und ggf. rejoin."),
	TABLIST_USE_FOOTER(ConfigSettingSection.TABLIST, "useFooter", true, "Ob die Tablist einen Footer haben soll.\nErfordert config reload und ggf. rejoin."), // enable: config reload
	TABLIST_CHANGE_NAMES(ConfigSettingSection.TABLIST, "changeNames", true, "Ob die Namen in der Tablist modfiziert werden sollen."),
	
	// COMMANDS
	COMMAND_ANTIXRAY_ENABLED(ConfigSettingSection.COMMANDS, "antixray.enabled", true, "Ob /antixray aktiviert sein soll"),
	COMMAND_BORDER_ENABLED(ConfigSettingSection.COMMANDS, "border.enabled", true, "Ob /border aktiviert sein soll"),
	COMMAND_BROADCAST_ENABLED(ConfigSettingSection.COMMANDS, "broadcast.enabled", true, "Ob /broadcast aktiviert sein soll"),
	COMMAND_CHATCLEAR_ENABLED(ConfigSettingSection.COMMANDS, "chatclear.enabled", true, "Ob /chatclear aktiviert sein soll"),
	COMMAND_COUNTDOWN_ENABLED(ConfigSettingSection.COMMANDS, "countdown.enabled", true, "Ob /countdown aktiviert sein soll"),
	COMMAND_FLY_ENABLED(ConfigSettingSection.COMMANDS, "fly.enabled", true, "Ob /fly und /unfly aktiviert sein soll"),
	COMMAND_FREEZE_ENABLED(ConfigSettingSection.COMMANDS, "freeze.enabled", true, "Ob /freeze und /unfreeze aktiviert sein soll"),
	COMMAND_GAMEMODE_ENABLED(ConfigSettingSection.COMMANDS, "gamemode.enabled", true, "Ob /gamemode aktiviert sein soll"),
	COMMAND_HEAL_ENABLED(ConfigSettingSection.COMMANDS, "heal.enabled", true, "Ob /heal aktiviert sein soll"),
	COMMAND_INFO_ENABLED(ConfigSettingSection.COMMANDS, "info.enabled", true, "Ob /info aktiviert sein soll"),
	COMMAND_INVSEE_ENABLED(ConfigSettingSection.COMMANDS, "invsee.enabled", true, "Ob /invsee aktiviert sein soll"),
	COMMAND_LANGUAGE_ENABLED(ConfigSettingSection.COMMANDS, "language.enabled", true, "Ob /language aktiviert sein soll"),
	COMMAND_MESSAGE_ENABLED(ConfigSettingSection.COMMANDS, "message.enabled", true, "Ob /message und /reply aktiviert sein soll"),
	COMMAND_MUTE_ENABLED(ConfigSettingSection.COMMANDS, "mute.enabled", true, "Ob /mute und /unmute aktiviert sein soll"),
	COMMAND_PERFORMANCE_ENABLED(ConfigSettingSection.COMMANDS, "performance.enabled", true, "Ob /performance aktiviert sein soll"),
	COMMAND_PING_ENABLED(ConfigSettingSection.COMMANDS, "ping.enabled", true, "Ob /ping aktiviert sein soll"),
	COMMAND_PROTECT_ENABLED(ConfigSettingSection.COMMANDS, "protect.enabled", true, "Ob /protect und /unprotect aktiviert sein soll"),
	COMMAND_REPORT_ENABLED(ConfigSettingSection.COMMANDS, "report.enabled", true, "Ob /report aktiviert sein soll"),
	COMMAND_SETSPAWN_ENABLED(ConfigSettingSection.COMMANDS, "setworldspawn.enabled", true, "Ob /setworldspawn aktiviert sein soll"),
	COMMAND_SPAWN_ENABLED(ConfigSettingSection.COMMANDS, "spawn.enabled", true, "Ob /spawn aktiviert sein soll"),
	COMMAND_SPEED_ENABLED(ConfigSettingSection.COMMANDS, "speed.enabled", true, "Ob /speed aktiviert sein soll"),
	COMMAND_TIME_ENABLED(ConfigSettingSection.COMMANDS, "time.enabled", true, "Ob /day und /night aktiviert sein soll"),
	COMMAND_TR_ENABLED(ConfigSettingSection.COMMANDS, "tr.enabled", true, "Ob /tr aktiviert sein soll"),
	COMMAND_TR_NAME(ConfigSettingSection.COMMANDS, "tr.name", "tr", "Custom name für /tr"),
	COMMAND_USAGE_ENABLED(ConfigSettingSection.COMMANDS, "usage.enabled", true, "Ob /usage aktiviert sein soll"),
	COMMAND_VANISH_ENABLED(ConfigSettingSection.COMMANDS, "vanish.enabled", true, "Ob /vanish aktiviert sein soll"),
	COMMAND_VARO_ENABLED(ConfigSettingSection.COMMANDS, "varo.enabled", true, "Ob /varo aktiviert sein soll"),
	COMMAND_VARO_NAME(ConfigSettingSection.COMMANDS, "varo.name", "varo", "Custom name für /varo"),
	COMMAND_VAROTIME_ENABLED(ConfigSettingSection.COMMANDS, "varotime.enabled", true, "Ob /varotime aktiviert sein soll"),
	COMMAND_WEATHER_ENABLED(ConfigSettingSection.COMMANDS, "weather.enabled", true, "Ob /sun, /rain und /thunder aktiviert sein soll"),

	// INTRO
	INTRO_INTRO_LINES(ConfigSettingSection.INTRO, "intro.lines", SuroStart.DEFAULT_TITLES, "Alle Titel für das Suro-Intro");

	private Object defaultValue, value;
	private String path, description, oldPaths[];
	private ConfigSettingSection section;
	private boolean reducesPerformance;
	private boolean sensitive;

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
	
	private ConfigSetting(ConfigSettingSection section, String path, Object value, String description, boolean reducesPerformance, boolean sensitive) {
        this(section, path, value, description, reducesPerformance);

        this.sensitive = sensitive;
    }

	private void save() {
		Main.getDataManager().getConfigHandler().saveValue(this);
	}

	private void sendFalseCast(Class<?> failedToCast) {
		if (value instanceof Integer && failedToCast.equals(Long.class) || value instanceof Long && failedToCast.equals(Integer.class))
			throw new IllegalArgumentException("'" + value + "' (" + value.getClass().getName() + ") is not applicable for " + failedToCast.getName() + " for entry " + getFullPath());

		try {
			throw new IllegalArgumentException("'" + value + "' (" + value.getClass().getName() + ") is not applicable for " + failedToCast.getName() + " for entry " + getFullPath());
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
    public Object getDefaultValueToWrite() {
        return Enum.class.isAssignableFrom(this.defaultValue.getClass()) ? ((Enum<?>) this.defaultValue).name() : this.defaultValue;
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
    public Object getValueToWrite() {
        return Enum.class.isAssignableFrom(this.defaultValue.getClass()) ? ((Enum<?>) this.value).name() : this.value;
    }

	@Override
	public String[] getOldPaths() {
		return this.oldPaths;
	}

	public void setValue(Object value, boolean save) {
		Class<?> valueClass = value.getClass();
		Class<?> defaultClass = this.defaultValue.getClass();
		if (valueClass != defaultClass) {
			if (!defaultClass.isAssignableFrom(valueClass)
			        && (valueClass != Integer.class || defaultClass != Long.class)
			        && (valueClass != Integer.class || defaultClass != Double.class)
			        && (valueClass != String.class || !Enum.class.isAssignableFrom(defaultClass)))
				throw new IllegalArgumentException("'" + value + "' (" + valueClass.getName() + ") is not applicable for " + defaultClass.getName() + " for entry " + getFullPath());
		}
	
		this.value = value;

		if (save)
			save();
	}

	/**
	 * Parses the argument and sets it as value
	 * 
	 * @param value
	 * @param save
	 * @throws IllegalArgumentException
	 * @throws NumberFormatException
	 */
	@Override
	public void setStringValue(String value, boolean save) {
		this.setValue(this.parseFromString(value), save);
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

	public Enum<?> getValueAsEnum() {
        try {
            return (Enum<?>) this.value;
        } catch (ClassCastException e) {
           this.sendFalseCast(Enum.class);
        }

        return (Enum<?>) this.defaultValue;
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

	public boolean canParseFromString() {
		Class<?> type = this.defaultValue.getClass();
		return type == String.class || type == Boolean.class || type == Integer.class || type == Long.class || type == Double.class || Enum.class.isAssignableFrom(type);
	}
	
	/**
	 * Parses the argument
	 * 
	 * @param input
	 * @return The value
	 * @throws IllegalArgumentException
	 * @throws NumberFormatException
	 */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Object parseFromString(String input) {
        Class defaultValueClass = this.defaultValue.getClass();
		switch (defaultValueClass.getName()) {
		case "java.lang.String":
			return input;
		case "java.lang.Boolean":
			return input.equalsIgnoreCase("true");
		case "java.lang.Integer":
			return Integer.parseInt(input);
		case "java.lang.Long":
			return Long.parseLong(input);
		case "java.lang.Double":
			return Double.parseDouble(input);
		case "com.cryptomorin.xseries.XMaterial":
		    return XMaterial.matchXMaterial(input).orElseThrow(() -> new IllegalArgumentException("Unknown material: " + input));
		default:
		    if (Enum.class.isAssignableFrom(defaultValueClass))
		        return Enum.valueOf(defaultValueClass, input);

			throw new IllegalArgumentException("Unknown type");
		}
	}

	public boolean isReducingPerformance() {
		return this.reducesPerformance;
	}
	
	public boolean isSensitive() {
        return this.sensitive;
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
