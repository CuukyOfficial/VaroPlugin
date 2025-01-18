package de.cuuky.varo.configuration.configurations.language.languages;

import de.cuuky.cfw.configuration.language.languages.LoadableMessage;

public enum LanguageEN implements LoadableMessage {

    COMBAT_FRIENDLY_FIRE("combat.friendlyfire", "&7This player is on your team!"),
    COMBAT_IN_FIGHT("combat.inFight", "&7You are now in combat, do &4NOT &7log &7out!"),
    COMBAT_LOGGED_OUT("combat.loggedOut", "&c%player% &7has left the server during a fight!"),
    COMBAT_NOT_IN_FIGHT("combat.notInFight", "&7You are now no longer in a &cfight&7!"),

    SPAWN_WORLD("spawn.spawn", "%colorcode%Coordinates&7 of the spawn: %colorcode%%x%&7, %colorcode%%y%&7, %colorcode%%z%"),
    SPAWN_NETHER("spawn.spawnNether", "%colorcode%Coordinates&7 of the portal to the world: %colorcode%%x%&7, %colorcode%%y%&7, %colorcode%%z%"),
    SPAWN_DISTANCE("spawn.spawnDistance", "&7You are %colorcode%%distance% &7blocks away from spawn!"),
    SPAWN_DISTANCE_NETHER("spawn.spawnDistanceNether", "&7You are %colorcode%%distance% &7blocks away from the portal to the world!"),

    GAME_VARO_START("game.start.varoStart", "%projectname% &7has started! &5Good luck!"),
    GAME_VARO_START_TITLE("game.start.startTitle", "%colorcode%%countdown%"),
    GAME_VARO_START_SUBTITLE("game.start.startSubtitle", "&7Good luck!"),
    GAME_WIN("game.win.single", "&5%player% &7has won %projectname%&7! &5Congratulations!"),
    GAME_WIN_TEAM("game.win.team", "&5%winnerPlayers% &7 have won %projectname% &7! &5Congratulations!"),

    SERVER_MODT_CANT_JOIN_HOURS("motd.cantJoinHours", "&cYou can only join between &4%minHour% &cand &4%maxHour%&c o'clock! %nextLine%&7Please try again later! &7%currHour%&7:&7%currMin%&7:&7%currSec%"),
    SERVER_MODT_NOT_OPENED("motd.serverNotOpened", "&cThe server has not yet been opened for everyone! %nextLine%&7Try again later!"),
    SERVER_MODT_OPEN("motd.serverOpen", "&aBe now with us in %projectname%&a!\n&7Have fun!"),

    CHEST_NOT_TEAM_CHEST("chest.notTeamChest", "&7This chest belongs to %colorcode%%player%&7!"),
    CHEST_NOT_TEAM_FURNACE("chest.notTeamFurnace", "&7This furnace belongs to %colorcode%%player%&7!"),
    CHEST_REMOVED_SAVEABLE("chest.removedChest", "&7You have removed this %saveable% %colorcode%successfully"),
    CHEST_SAVED_CHEST("chest.newChestSaved", "&7A new chest was secured!"),
    CHEST_SAVED_FURNACE("chest.newFurnaceSaved", "&7A new furnace was secured!"),

    PLACEHOLDER_NO_TOP_PLAYER("placeholder.noTopPlayer", "-"),
    PLACEHOLDER_NO_TOP_TEAM("placeholder.noTopTeam", "-"),

    PROTECTION_NO_MOVE_START("protection.noMoveStart", "&7You cannot move until the project has been started."),
    PROTECTION_START("protection.start", "&7The &cProtection time &7starts now and will stop in &c%seconds% &7seconds!"),
    PROTECTION_TIME_OVER("protection.protectionOver", "&7The &cprotection time &7is now over!"),
    PROTECTION_TIME_UPDATE("protection.protectionUpdate", "&7The &cprotection time &7is over in &c%minutes%&7:&c%seconds% &7!"),
    PROTECTION_TIME_RUNNING("protection.timeRunning", "&7The %colorcode%protection time &7 is still running!"),

    SORT_NO_HOLE_FOUND("sort.noHoleFound", "&7There is no spawn available for you!"),
    SORT_NO_HOLE_FOUND_TEAM("sort.noHoleFoundTeam", "&7There is no spawn available for you that's next to your team partner(s)!"),
    SORT_NUMBER_HOLE("sort.numberHoleTeleport", "&7You've been teleported to spawn %colorcode%%number%&7!"),
    SORT_OWN_HOLE("sort.ownHoleTeleport", "&7You've been teleported to your spawn!"),
    SORT_SPECTATOR_TELEPORT("sort.spectatorTeleport", "&7You've been teleported to the world spawn because you're a spectator!"),
    SORT_SORTED("sort.sorted", "&7You've been teleported to spawn %colorcode%%number%&7!"),

    SPAWNS_SPAWN_NUMBER("spawns.spawnNameTag.number", "&7Spawn %colorcode%%number%"),
    SPAWNS_SPAWN_PLAYER("spawns.spawnNameTag.player", "&7Spawn of %colorcode%%player%"),

    MODS_BLOCKED_MODS_KICK("mods.blockedModsKick", "&7Please remove the following mods: %colorcode%%mods%"),
    MODS_BLOCKED_MODLIST_SPLIT("mods.blockedModsListSplit", "&7, "),
    MODS_BLOCKED_MODS_BROADCAST("mods.blockedModsBroadcast", "%colorcode%%player% &7tried to join while having the following mods installed: %colorcode%%mods%"),

    LOGGER_FILTER_INVALID_FILTER("blockLoggerFilter.invalidFilter", "&c%filterName% \"%content%\" is invalid!"),
    LOGGER_FILTER_SET_FILTER("blockLoggerFilter.setFilter", "&7Set %filterName% to %colorcode%%newContent%&7 (before: %oldContent%)."),
    LOGGER_FILTER_RESET_FILTER("blockLoggerFilter.resetFilter", "&7Reset %filterName% (before: %oldContent%)."),
    LOGGER_FILTER_PLAYER_FILTER_MESSAGE("blockLoggerFilter.playerFilterMessage", "&7Please enter a player name to filter for:"),
    LOGGER_FILTER_MATERIAL_FILTER_MESSAGE("blockLoggerFilter.materialFilterMessage", "&7Please enter a material name to filter for:");

    private String path, message;

    private LanguageEN(String path, String message) {
        this.path = path;
        this.message = message;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String getDefaultMessage() {
        return message;
    }
}
