package de.cuuky.varo.configuration.configurations.language.languages;

import de.cuuky.cfw.configuration.language.languages.LoadableMessage;

public enum LanguageEN implements LoadableMessage {

    BOTS_DISCORD_NOT_REGISTERED_DISCORD("bots.notRegisteredDiscord", "&cYour account is not verified!\n&7Please use &c/verify %code% &7to verify your Discord account!\nLink: &c%discordLink%"),
    BOTS_DISCORD_NO_SERVER_USER("bots.noServerUser", "&cYour account is not on the discord!%nextLine%&7Join the discord and try again."),

    BORDER_COMMAND_SET_BORDER("border.borderSet", "&7The border was set to %colorcode%%size% &7!"),

    COMBAT_FRIENDLY_FIRE("combat.friendlyfire", "&7This player is on your team!"),
    COMBAT_IN_FIGHT("combat.inFight", "&7You are now in combat, do &4NOT &7log &7out!"),
    COMBAT_LOGGED_OUT("combat.loggedOut", "&c%player% &7has left the server during a fight!"),
    COMBAT_NOT_IN_FIGHT("combat.notInFight", "&7You are now no longer in a &cfight&7!"),

    SPAWN_WORLD("spawn.spawn", "%colorcode%Coordinates&7 of the spawn: %colorcode%%x%&7, %colorcode%%y%&7, %colorcode%%z%"),
    SPAWN_NETHER("spawn.spawnNether", "%colorcode%Coordinates&7 of the portal to the world: %colorcode%%x%&7, %colorcode%%y%&7, %colorcode%%z%"),
    SPAWN_DISTANCE("spawn.spawnDistance", "&7You are %colorcode%%distance% &7blocks away from spawn!"),
    SPAWN_DISTANCE_NETHER("spawn.spawnDistanceNether", "&7You are %colorcode%%distance% &7blocks away from the portal to the world!"),
    
    DEATH_ELIMINATED_OTHER("death.eliminated.other", "%colorcode%%player% &7died. Reason: &c%reason%"),
    DEATH_ELIMINATED_PLAYER("death.eliminated.player", "%colorcode%%player% &7was killed by %colorcode%%killer%&7!"),
    DEATH_LIFE_OTHER("death.teamLifeLost.other", "%colorcode%%player% &7died! They now have %colorcode%%teamLifes% &7life/lives left! Reason: &c%reason%"),
    DEATH_LIFE_PLAYER("death.teamLifeLost.player", "%colorcode%%player% &7was killed by %colorcode%%killer%&7! They now have %colorcode%%teamLifes% &7life/lives left! Reason: &c%reason%"),
    DEATH_RESPAWN_PROTECTION("death.respawnProtection", "&c%player% now has one life less and is invulnerable for %seconds% seconds!"),
    DEATH_RESPAWN_PROTECTION_OVER("death.respawnProtectionOver", "&c%player% is now vulnerable again!"),
    DEATH_KILL_LIFE_ADD("death.killLifeAdd", "&7Your team got a team life because of a kill!"),
    DEATH_KILL_TIME_ADD("death.killTimeAdd", "You've recieved %colorcode%%timeAdded% &7seconds of additional time!"),

    GAME_VARO_START("game.start.varoStart", "%projectname% &7has started! &5Good luck!"),
    GAME_VARO_START_TITLE("game.start.startTitle", "%colorcode%%countdown%"),
    GAME_VARO_START_SUBTITLE("game.start.startSubtitle", "&7Good luck!"),
    GAME_WIN("game.win.single", "&5%player% &7has won %projectname%&7! &5Congratulations!"),
    GAME_WIN_TEAM("game.win.team", "&5%winnerPlayers% &7 have won %projectname% &7! &5Congratulations!"),

    QUIT_MESSAGE("quitmessage.quit", "%prefix%&c%player%&7 has left the server!"),
    QUIT_DISCONNECT_SESSION_END("quitmessage.disconnectKilled", "&c%player% &7has left the game and is no longer online since &c%banTime% &7minute(s).%nextLine%&7This means he has been removed from %projectname% &7!"),
    QUIT_SPECTATOR("quitmessage.spectator", "&c%player% &7has left the server as a spectator!"),
    QUIT_TOO_OFTEN("quitmessage.quitTooOften", "&c%player% &7has left the server too often and thus lost their session."),
    QUIT_WITH_REMAINING_TIME("quitmessage.quitRemainingTime", "%prefix%&c%player% &7has left the server early!"),
    QUIT_KICK_BROADCAST("quitmessage.broadcast", "%colorcode%%player% &7has been kicked!"),
    QUIT_KICK_DELAY_OVER("quitmessage.protectionOver", "%colorcode%%player% &7was kicked because of their death now!"),
    QUIT_KICK_IN_SECONDS("quitmessage.kickInSeconds", "%colorcode%%player% &7will be kicked in %colorcode%%countdown% &7second(s)!"),
    QUIT_KICK_PLAYER_NEARBY("quitmessage.noKickPlayerNearby", "&cThere is a player &4%distance% &cblocks near you!%nextLine%&7To get kicked, move away from this player!"),
    QUIT_KICK_SERVER_CLOSE_SOON("quitmessage.serverCloseSoon", "&7The server closes in &c%minutes% &7minutes!"),

    DEATH_KICK_DEAD("kick.kickedKilled", "&cYou died! %nextLine% &7With this you are removed from %projectname%"),
    DEATH_KICK_KILLED("kick.killedKick", "&7You were killed by &c%killer%&7! %nextLine% &7You have been removed from %projectname%&7!"),
    JOIN_KICK_NOT_USER_OF_PROJECT("kick.notUserOfTheProject", "&7You are not a participant of this %projectname%&7!"),
    JOIN_KICK_SERVER_FULL("kick.serverFull", "&cThe server is full!%nextLine%&7Talk to the owner if this is a mistake!"),
    JOIN_KICK_STRIKE_BAN("kick.strikeBan", "&cYou were banned for %hours% hours because of your last strike!\nTry again later."),
    JOIN_KICK_BANNED("kick.banned", "&4You are banned from the server!\n&7Report to an admin if this is an error.\n&7Reason: &c%reason%"),
    JOIN_KICK_NO_PREPRODUCES_LEFT("kick.noPreproduceLeft", "&cYou already have pre-produced! %nextLine%&7Try again tomorrow."),
    JOIN_KICK_NO_SESSIONS_LEFT("kick.noSessionLeft", "&cYou have no more sessions left! %nextLine%&7Wait until tomorrow so you can play again!"),
    JOIN_KICK_NO_TIME_LEFT("kick.noTimeLeft", "You are only allowed to play all &4%timeHours% &chours regularly! %nextLine%&7You can only join in &c%stunden%&7:&c%minuten%&7:&c%sekunden% &7join again!"),
    JOIN_KICK_NOT_STARTED("kick.notStarted", "&cThe server has not been opened yet! %nextLine%&7Please be patient a little longer!"),
    KICK_SESSION_OVER("kick.kickMessage", "&cYour recording time has expired, %nextLine%&7this is why you got kicked!"),
    KICK_MASS_REC_SESSION_OVER("kick.kickMessageMassRec", "&cThe mass recording is finished, %nextLine%&7that's why you got kicked!"),
    KICK_TOO_MANY_STRIKES("kick.tooManyStrikes", "&7You got too many strikes and were therefore removed from the project %projectname%"),
    KICK_COMMAND("kick.kick", "%colorcode%%player% &7was kicked!"),

    SERVER_MODT_CANT_JOIN_HOURS("motd.cantJoinHours", "&cYou can only join between &4%minHour% &cand &4%maxHour%&c o'clock! %nextLine%&7Please try again later! &7%currHour%&7:&7%currMin%&7:&7%currSec%"),
    SERVER_MODT_NOT_OPENED("motd.serverNotOpened", "&cThe server has not yet been opened for everyone! %nextLine%&7Try again later!"),
    SERVER_MODT_OPEN("motd.serverOpen", "&aBe now with us in %projectname%&a!\n&7Have fun!"),

    NAMETAG_PREFIX_NO_TEAM("nametag.prefix.noTeam", "&7"),
	NAMETAG_PREFIX_TEAM("nametag.prefix.team", "%colorcode%%team% &7"),
	NAMETAG_SUFFIX_NO_TEAM("nametag.suffix.noTeam", "&c %kills%"),
	NAMETAG_SUFFIX_TEAM("nametag.suffix.team", "&c %kills%"),

    CHEST_NOT_TEAM_CHEST("chest.notTeamChest", "&7This chest belongs to %colorcode%%player%&7!"),
    CHEST_NOT_TEAM_FURNACE("chest.notTeamFurnace", "&7This furnace belongs to %colorcode%%player%&7!"),
    CHEST_REMOVED_SAVEABLE("chest.removedChest", "&7You have removed this %saveable% %colorcode%successfully"),
    CHEST_SAVED_CHEST("chest.newChestSaved", "&7A new chest was secured!"),
    CHEST_SAVED_FURNACE("chest.newFurnaceSaved", "&7A new furnace was secured!"),

    NOPERMISSION_NO_PERMISSION("nopermission.noPermission", "%colorcode%You are not authorized for this!"),
    NOPERMISSION_NOT_ALLOWED_CRAFT("nopermission.notAllowedCraft", "&7You may not craft, use or brew that!"),
    NOPERMISSION_NO_LOWER_FLIGHT("nopermission.noLowerFlight", "&7You can not fly lower!"),

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

    TABLIST_PLAYER_WITH_TEAM("tablist.player.withTeam", "%colorcode%%team% &8| &7%player%  &c%kills%"),
    TABLIST_PLAYER_WITH_TEAM_RANK("tablist.player.withTeamAndRank", "&7%rank% &8| %colorcode%%team% &8| &7%player%  &c%kills%"),
    TABLIST_PLAYER_WITHOUT_TEAM("tablist.player.withoutTeam", "&7%player%  &c%kills%"),
    TABLIST_PLAYER_WITHOUT_TEAM_RANK("tablist.player.withoutTeamWithRank", "&7%rank% &8| &7%player%  &c%kills%"),

    TEAM_NAME_INVALID("team.name.invalid", "%prefix%&cInvalid team name!"),
    TEAM_NAME_DUPLICATE("team.name.duplicate", "%prefix%&cThat name is already taken!"),
    TEAM_NAME_TOO_LONG("team.name.tooLong", "%prefix%&cYour team name must not contain more than %colorcode%%maxLength% &ccharacters!"),
    TEAM_RENAME("team.rename", "%prefix%%colorcode%&lPlease enter a new name"),
    TEAM_RENAMED("team.renamed", "%prefix%&7Team %colorcode%%teamId% &7changed their name to %colorcode%%newName%"),
    
    TEAMREQUEST_ENTER_TEAMNAME("teamrequest.enterTeamName", "%prefix%%colorcode%&lPlease enter a team name for you and %invited%:"),
    TEAMREQUEST_PLAYER_NOT_ONLINE("teamrequest.playerNotOnline", "%colorcode%%invitor% is no longer online!"),
    TEAMREQUEST_REVOKED("teamrequest.invationRevoked", "Invitation successfully withdrawn!"),
    TEAMREQUEST_TEAM_FULL("teamrequest.teamIsFull", "%invited% wasn't able to join your team - team member limit reached."),
    TEAMREQUEST_TEAM_REQUEST_RECIEVED("teamrequest.teamRequestRecieved", "%colorcode%%invitor% &7has invited you to join their team (/varo tr)!"),
    TEAMREQUEST_INVITED_TEAM("teamrequest.invitedInTeam", "&7You have invited %colorcode%%invited% &7 in the team %colorcode%%team% &7!"),
    TEAMREQUEST_NO_TEAMNAME("teamrequest.noteamname", "&7You still have no team name!"),
	TEAMREQUEST_LOBBYITEM_INVITE_NAME("teamRequest.items.invite.name", "&6Invite Player"),
	TEAMREQUEST_LOBBYITEM_INVITE_LORE("teamRequest.items.invite.lore", "&7Hit another player in\n&7order to invite them\n&7to your team"),
	TEAMREQUEST_LOBBYITEM_LEAVE_NAME("teamRequest.items.leave.name", "&cLeave Team"),
	TEAMREQUEST_LOBBYITEM_LEAVE_LORE("teamRequest.items.leave.lore", "&7Leave your team forever.\n&7You may never return."),
	TEAMREQUEST_LOBBYITEM_RENAME_NAME("teamRequest.items.rename.name", "&cRename Team"),
	TEAMREQUEST_LOBBYITEM_RENAME_LORE("teamRequest.items.rename.lore", "&7Change the name of your team"),

    SPAWNS_SPAWN_NUMBER("spawns.spawnNameTag.number", "&7Spawn %colorcode%%number%"),
    SPAWNS_SPAWN_PLAYER("spawns.spawnNameTag.player", "&7Spawn of %colorcode%%player%"),

    MODS_BLOCKED_MODS_KICK("mods.blockedModsKick", "&7Please remove the following mods: %colorcode%%mods%"),
    MODS_BLOCKED_MODLIST_SPLIT("mods.blockedModsListSplit", "&7, "),
    MODS_BLOCKED_MODS_BROADCAST("mods.blockedModsBroadcast", "%colorcode%%player% &7tried to join while having the following mods installed: %colorcode%%mods%"),

    OTHER_CONFIG("other.configReload", "&7The %colorcode%Config &7was reloaded"),

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
