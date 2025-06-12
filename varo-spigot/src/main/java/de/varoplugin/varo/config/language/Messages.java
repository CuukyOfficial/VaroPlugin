/* 
 * VaroPlugin
 * Copyright (C) 2022 Cuuky, Almighty-Satan
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package de.varoplugin.varo.config.language;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.varoplugin.cfw.version.VersionUtils;
import de.varoplugin.varo.Main;
import de.varoplugin.varo.alert.Alert;
import de.varoplugin.varo.alert.AlertType;
import de.varoplugin.varo.config.language.Contexts.PlayerContext;
import de.varoplugin.varo.config.language.Contexts.VaroContext;
import de.varoplugin.varo.logger.logger.EventLogger.LogType;
import de.varoplugin.varo.player.VaroPlayer;
import io.github.almightysatan.jaskl.Resource;
import io.github.almightysatan.jaskl.yaml.YamlConfig;
import io.github.almightysatan.slams.InvalidTypeException;
import io.github.almightysatan.slams.MissingTranslationException;
import io.github.almightysatan.slams.PlaceholderResolver;
import io.github.almightysatan.slams.Slams;
import io.github.almightysatan.slams.papi.SlamsPlaceholderExpansion;
import io.github.almightysatan.slams.parser.JasklParser;
import io.github.almightysatan.slams.standalone.StandaloneMessage;
import io.github.almightysatan.slams.standalone.StandaloneMessageArray;
import io.github.almightysatan.slams.standalone.StandaloneMessageArray2d;
import me.clip.placeholderapi.PlaceholderAPIPlugin;

public final class Messages {

    static String messageFilePath = "plugins/Varo/messages/";

    public static final String LANGUAGE_EN = "en";
    public static final String LANGUAGE_DE = "de";
    public static final String LANGUAGE_DEFAULT = LANGUAGE_EN;
    public static final List<String> LANGUAGES = Arrays.asList(LANGUAGE_EN, LANGUAGE_DE);

    static final Slams SLAMS = Slams.create(LANGUAGE_DEFAULT);
    private static final PlaceholderResolver PLACEHOLDERS;

    static {
        if (Bukkit.getServer() != null) {
            PLACEHOLDERS = Placeholders.getPlaceholders();
        } else {
            // Unit tests
            PLACEHOLDERS = PlaceholderResolver.empty();
        }
    }

    public static final VaroMessage ALERT_DISCONNECT_TOO_OFTEN = message("alert.disconnectTooOften");
    public static final VaroMessage ALERT_STRIKE_NO_BLOODLUST = message("alert.strike.noBloodlust");
    public static final VaroMessage ALERT_STRIKE_NOT_JOIN = message("alert.strike.noJoin");
    public static final VaroMessage ALERT_NO_BLOODLUST = message("alert.noBloodlust");
    public static final VaroMessage ALERT_NOT_JOIN = message("alert.noJoin");

    public static final VaroMessage LOG_BORDER_DECREASED_DEATH = message("log.borderDecrease.death");
    public static final VaroMessage LOG_BORDER_DECREASED_TIME_DAYS = message("log.borderDecrease.days");
    public static final VaroMessage LOG_BORDER_DECREASED_TIME_MINUTE = message("log.borderDecrease.minutes");
    public static final VaroMessage LOG_COMBAT_LOG = message("log.combatlog");
    public static final VaroMessage LOG_DEATH_ELIMINATED_OTHER = message("log.death.eliminated.other");
    public static final VaroMessage LOG_DEATH_ELIMINATED_PLAYER = message("log.death.eliminated.player");
    public static final VaroMessage LOG_DEATH_LIFE_OTHER = message("log.death.teamLifeLost.other");
    public static final VaroMessage LOG_DEATH_LIFE_PLAYER = message("log.death.teamLifeLost.player");
    public static final VaroMessage LOG_GAME_STARTED = message("log.gameStart");
    public static final VaroMessage LOG_FINALE_JOIN = message("log.finaleJoin");
    public static final VaroMessage LOG_FINALE_START = message("log.finaleStart");
    public static final VaroMessage LOG_KICKED_PLAYER = message("log.kickedPlayer");
    public static final VaroMessage LOG_SESSIONS_ENDED = message("log.sessionsEnded");
    public static final VaroMessage LOG_STRIKE_GENERAL = message("log.strike.general");
    public static final VaroMessage LOG_STRIKE_COORDINATES = message("log.strike.coordinates");
    public static final VaroMessage LOG_STRIKE_COORDINATES_NEVER_ONLINE = message("log.strike.coordinatesNeverOnline");
    public static final VaroMessage LOG_STRIKE_COMBAT_LOG = message("log.strike.combatlog");
    public static final VaroMessage LOG_NEW_SESSIONS = message("log.newSessions");
    public static final VaroMessage LOG_NEW_SESSIONS_FOR_ALL = message("log.newSessionsForAll");
    public static final VaroMessage LOG_PLAYER_DC_TOO_EARLY = message("log.playerQuitTooEarly");
    public static final VaroMessage LOG_PLAYER_JOIN_MASSREC = message("log.playerJoinMassrec");
    public static final VaroMessage LOG_PLAYER_JOIN_NORMAL = message("log.playerJoinNormal");
    public static final VaroMessage LOG_PLAYER_JOINED = message("log.playerJoined");
    public static final VaroMessage LOG_PLAYER_QUIT = message("log.playerQuit");
    public static final VaroMessage LOG_PLAYER_RECONNECT = message("log.playerReconnect");
    public static final VaroMessage LOG_SWITCHED_NAME = message("log.switchedName");
    public static final VaroMessage LOG_TELEPORTED_TO_MIDDLE = message("log.teleportedToMiddle");
    public static final VaroMessage LOG_WIN_PLAYER = message("log.win.player");
    public static final VaroMessage LOG_WIN_TEAM = message("log.win.team");

    public static final VaroMessage BORDER_DECREASE_DEATH = message("border.decrease.death");
    public static final VaroMessage BORDER_DECREASE_DAYS = message("border.decrease.days");
    public static final VaroMessage BORDER_DECREASE_MINUTES = message("border.decrease.minutes");
    public static final VaroMessage BORDER_MINUTE_TIME_UPDATE = message("border.decrease.minuteUpdate");
    public static final VaroMessage BORDER_MINIMUM_REACHED = message("border.minimum");

    public static final VaroMessage PLAYER_DISPLAYNAME = message("player.displayname");

    public static final VaroMessage PLAYER_JOIN_BROADCAST = message("player.join.broadcast");
    public static final VaroMessage PLAYER_JOIN_REQUIRED = message("player.join.required");
    public static final VaroMessage PLAYER_JOIN_FINALE = message("player.join.finale");
    public static final VaroMessage PLAYER_JOIN_PROTECTION = message("player.join.protection");
    public static final VaroMessage PLAYER_JOIN_PROTECTION_END = message("player.join.protectionEnd");
    public static final VaroMessage PLAYER_JOIN_SPECTATOR = message("player.join.spectator");
    public static final VaroMessage PLAYER_JOIN_MASS_RECORDING = message("player.join.massrecording");
    public static final VaroMessage PLAYER_JOIN_REMAINING_TIME = message("player.join.remainingTime");
    
    public static final VaroMessage PLAYER_DISCONNECT_BROADCAST = message("player.disconnect.broadcast");
    public static final VaroMessage PLAYER_DISCONNECT_SPECTATOR = message("player.disconnect.spectator");
    public static final VaroMessage PLAYER_DISCONNECT_TOO_OFTEN = message("player.disconnect.quitTooOften");
    public static final VaroMessage PLAYER_DISCONNECT_TOO_ERALY = message("player.disconnect.tooEarly");
    public static final VaroMessage PLAYER_DISCONNECT_KICK = message("player.disconnect.kick");
    public static final VaroMessage PLAYER_DISCONNECT_KICK_DELAY_OVER = message("player.disconnect.delay");
    public static final VaroMessage PLAYER_DISCONNECT_KICK_IN_SECONDS = message("player.disconnect.kickInSeconds");
    public static final VaroMessage PLAYER_DISCONNECT_KICK_PLAYER_NEARBY = message("player.disconnect.noKickPlayerNearby");
    public static final VaroMessage PLAYER_DISCONNECT_KICK_SERVER_CLOSE_SOON = message("player.disconnect.serverCloseSoon");
    
    public static final VaroMessage PLAYER_KICK_DEATH = message("player.kick.death");
    public static final VaroMessage PLAYER_KICK_KILL = message("player.kick.kill");
    public static final VaroMessage PLAYER_KICK_NOT_USER_OF_PROJECT = message("player.kick.notUserOfProject");
    public static final VaroMessage PLAYER_KICK_SERVER_FULL = message("player.kick.serverFull");
    public static final VaroMessage PLAYER_KICK_SERVER_CLOSED = message("player.kick.serverClosed");
    public static final VaroMessage PLAYER_KICK_STRIKE_BAN = message("player.kick.strikeBan");
    public static final VaroMessage PLAYER_KICK_BANNED = message("player.kick.banned");
    public static final VaroMessage PLAYER_KICK_NO_PREPRODUCES_LEFT = message("player.kick.noPreproduceLeft");
    public static final VaroMessage PLAYER_KICK_NO_SESSIONS_LEFT = message("player.kick.noSessionLeft");
    public static final VaroMessage PLAYER_KICK_NO_TIME_LEFT = message("player.kick.noTimeLeft");
    public static final VaroMessage PLAYER_KICK_NOT_STARTED = message("player.kick.notStarted");
    public static final VaroMessage PLAYER_KICK_SESSION_OVER = message("player.kick.sessionOver");
    public static final VaroMessage PLAYER_KICK_MASS_REC_SESSION_OVER = message("player.kick.kickMessageMassRec");
    public static final VaroMessage PLAYER_KICK_TOO_MANY_STRIKES = message("player.kick.tooManyStrikes");
    public static final VaroMessage PLAYER_KICK_DISCORD_NOT_REGISTERED = message("player.kick.notRegisteredDiscord");
    public static final VaroMessage PLAYER_KICK_DISCORD_NO_USER = message("player.kick.noDiscordUser");

    public static final VaroMessage PLAYER_DEATH_ELIMINATED_OTHER = message("player.death.eliminated.other");
    public static final VaroMessage PLAYER_DEATH_ELIMINATED_PLAYER = message("player.death.eliminated.player");
    public static final VaroMessage PLAYER_DEATH_ELIMINATED_TIME = message("player.death.eliminated.time");
    public static final VaroMessage PLAYER_DEATH_LIFE_OTHER = message("player.death.teamLifeLost.other");
    public static final VaroMessage PLAYER_DEATH_LIFE_PLAYER = message("player.death.teamLifeLost.player");
    public static final VaroMessage PLAYER_DEATH_RESPAWN_PROTECTION = message("player.death.respawnProtection");
    public static final VaroMessage PLAYER_DEATH_RESPAWN_PROTECTION_OVER = message("player.death.respawnProtectionOver");
    public static final VaroMessage PLAYER_DEATH_KILL_LIFE_ADD = message("player.death.killLifeAdd");
    public static final VaroMessage PLAYER_DEATH_KILL_TIME_ADD = message("player.death.killTimeAdd");
    
    public static final VaroMessage PLAYER_MOVE_PROTECTION = message("player.moveProtection");
    public static final VaroMessage PLAYER_SPECTATOR_HEIGHT = message("player.spectator.height");
    public static final VaroMessage PLAYER_CRAFTING_DISALLOWED = message("player.crafting.disallowed");
    
    public static final VaroMessage PLAYER_COMBAT_FRIENDLY_FIRE = message("player.combat.friendlyfire");
    public static final VaroMessage PLAYER_COMBAT_FIGHT = message("player.combat.fight");
    public static final VaroMessage PLAYER_COMBAT_FIGHT_END = message("player.combat.fightEnd");
    public static final VaroMessage PLAYER_COMBAT_LOGGED_OUT = message("player.combat.loggedOut");
    
    public static final VaroMessage PLAYER_CHEST_NEW = message("player.chests.new");
    public static final VaroMessage PLAYER_CHEST_REMOVED = message("player.chests.removed");
    public static final VaroMessage PLAYER_CHEST_DISALLOWED = message("player.chests.disallowed");
    public static final VaroMessage PLAYER_CHEST_ADMIN = message("player.chests.admin");

    public static final VaroMessageArray PLAYER_SCOREBOARD_TITLE = array("player.scoreboard.title");
    public static final VaroMessageArray2d PLAYER_SCOREBOARD_CONTENT = array2d("player.scoreboard.content");
    public static final VaroMessageArray PLAYER_ACTIONBAR = array("player.actionbar");
    public static final VaroMessageArray2d PLAYER_TABLIST_HEADER = array2d("player.tablist.header");
    public static final VaroMessageArray2d PLAYER_TABLIST_FOOTER = array2d("player.tablist.footer");
    public static final VaroMessage PLAYER_TABLIST_FORMAT = message("player.tablist.format");
    
    public static final VaroMessage PLAYER_NAMETAG_PREFIX = message("player.nametag.prefix");
    public static final VaroMessage PLAYER_NAMETAG_SUFFIX = message("player.nametag.suffix");

    public static final VaroMessage CHAT_DEFAULT = message("chat.default");
    public static final VaroMessage CHAT_TEAM = message("chat.team");
    public static final VaroMessage CHAT_MUTED = message("chat.muted");
    public static final VaroMessage CHAT_SPECTATOR = message("chat.spectator");
    public static final VaroMessage CHAT_START = message("chat.start");
    
    public static final VaroMessage GAME_SORT_NUMBER = message("game.sort.number");
    public static final VaroMessage GAME_SORT_PLAYER = message("game.sort.player");
    public static final VaroMessage GAME_SORT_NO_SPAWN_FOUND = message("game.sort.noSpawnFound");
    public static final VaroMessage GAME_SORT_NO_SPAWN_FOUND_TEAM = message("game.sort.noSpawnFoundTeam");
    public static final VaroMessage GAME_SORT_SPECTATOR_TELEPORT = message("game.sort.spectator");
    public static final VaroMessage GAME_START_ABORT = message("game.start.abort");
    public static final VaroMessage GAME_START_VARO_BROADCAST = message("game.start.varo.broadcast");
    public static final VaroMessage GAME_START_VARO_TITLE = message("game.start.varo.title");
    public static final VaroMessage GAME_START_VARO_SUBTITLE = message("game.start.varo.subtitle");
    public static final VaroMessage GAME_START_VARO_COUNTDOWN = message("game.start.varo.countdown");
    public static final VaroMessageArray GAME_START_SURO_TITLE = array("game.start.suro.title");
    public static final VaroMessage GAME_WIN_PLAYER = message("game.win.player");
    public static final VaroMessage GAME_WIN_TEAM = message("game.win.team");
    public static final VaroMessage GAME_FINALE_COUNTDOWN = message("game.finale.countdown");
    public static final VaroMessage GAME_FINALE_START = message("game.finale.start");
    public static final VaroMessage GAME_FINALE_FREEZE = message("game.finale.freeze");
    public static final VaroMessage GAME_FINALE_NOFREEZE = message("game.finale.noFreeze");
    public static final VaroMessage GAME_LOBBY_MOVE = message("game.lobby.move");

    public static final VaroMessage MOTD_OPEN = message("motd.open");
    public static final VaroMessage MOTD_CLOSED = message("motd.closed");
    public static final VaroMessage MOTD_CLOSED_HOURS = message("motd.closedHours");

    public static final VaroMessage CATEGORY_HEADER = message("category.header");
    public static final VaroMessage CATEGORY_FOOTER = message("category.footer");

    public static final VaroMessage COMMANDS_ERROR_USER_NOT_FOUND = message("commands.error.usernotfound");
    public static final VaroMessage COMMANDS_ERROR_UNKNOWN_PLAYER = message("commands.error.unknownplayer");
    public static final VaroMessage COMMANDS_ERROR_NO_CONSOLE = message("commands.error.noconsole");
    public static final VaroMessage COMMANDS_ERROR_NOT_STARTED = message("commands.error.notstarted");
    public static final VaroMessage COMMANDS_ERROR_USAGE = message("commands.error.usage");
    public static final VaroMessage COMMANDS_ERROR_NO_NUMBER = message("commands.error.nonumber");
    public static final VaroMessage COMMANDS_ERROR_WRONGVERSION = message("commands.error.wrongVersion");
    public static final VaroMessage COMMANDS_ERROR_GENERIC = message("commands.error.generic");
    public static final VaroMessage COMMANDS_ERROR_BLOCKED = message("commands.error.blocked");
    public static final VaroMessage COMMANDS_ERROR_PERMISSION = message("commands.error.permission");
    public static final VaroMessage COMMANDS_ERROR_UNKNOWN_COMMAND = message("commands.error.unknownCommand");

    public static final VaroMessage COMMANDS_VARO_BUGREPORT_UPDATE = message("commands.varo.bugreport.update");
    public static final VaroMessage COMMANDS_VARO_BUGREPORT_COLLECTING_DATA = message("commands.varo.bugreport.collectingdata");
    public static final VaroMessage COMMANDS_VARO_BUGREPORT_CREATED = message("commands.varo.bugreport.created");

    public static final VaroMessage COMMANDS_VARO_ABORT_COUNTDOWN_NOT_ACTIVE = message("commands.varo.abort.notactive");
    public static final VaroMessage COMMANDS_VARO_ABORT_COUNTDOWN_STOPPED = message("commands.varo.abort.stopped");

    public static final VaroMessage COMMANDS_VARO_ACTIONBAR_DEACTIVATED = message("commands.varo.actionbar.deactivated");
    public static final VaroMessage COMMANDS_VARO_ACTIONBAR_ENABLED = message("commands.varo.actionbar.enabled");
    public static final VaroMessage COMMANDS_VARO_ACTIONBAR_DISABLED = message("commands.varo.actionbar.disabled");

    public static final VaroMessage COMMANDS_VARO_AUTOSETUP_NOT_SETUP_YET = message("commands.varo.autosetup.notsetupyet");
    public static final VaroMessage COMMANDS_VARO_AUTOSETUP_FINISHED = message("commands.varo.autosetup.finished");
    public static final VaroMessage COMMANDS_VARO_AUTOSETUP_HELP = message("commands.varo.autosetup.help");

    public static final VaroMessage COMMANDS_VARO_AUTOSTART_ALREADY_STARTED = message("commands.varo.autostart.alreadystarted");
    public static final VaroMessage COMMANDS_VARO_AUTOSTART_ALREADY_SETUP = message("commands.varo.autostart.alreadysetup");
    public static final VaroMessage COMMANDS_VARO_AUTOSTART_HELP_SET = message("commands.varo.autostart.helpset");
    public static final VaroMessage COMMANDS_VARO_AUTOSTART_NO_NUMBER = message("commands.varo.autostart.nonumber");
    public static final VaroMessage COMMANDS_VARO_AUTOSTART_DATE_IN_THE_PAST = message("commands.varo.autostart.dateinthepast");
    public static final VaroMessage COMMANDS_VARO_AUTOSTART_NOT_SETUP_YET = message("commands.varo.autostart.notsetupyet");
    public static final VaroMessage COMMANDS_VARO_AUTOSTART_REMOVED = message("commands.varo.autostart.removed");
    public static final VaroMessage COMMANDS_VARO_AUTOSTART_DELAY_HELP = message("commands.varo.autostart.delayhelp");
    public static final VaroMessage COMMANDS_VARO_AUTOSTART_DELAY_TO_SMALL = message("commands.varo.autostart.delaytosmall");
    public static final VaroMessage COMMANDS_VARO_AUTOSTART_START_DELAYED = message("commands.varo.autostart.startdelayed");
    public static final VaroMessage COMMANDS_VARO_AUTOSTART_INACTIVE = message("commands.varo.autostart.notactive");
    public static final VaroMessage COMMANDS_VARO_AUTOSTART_INFO= message("commands.varo.autostart.info");
    public static final VaroMessage COMMANDS_VARO_BACKPACK_PLAYER_DOESNT_EXIST = message("commands.varo.backpack.playerdoesntexist");
    public static final VaroMessage COMMANDS_VARO_BACKPACK_TEAM_DOESNT_EXIST = message("commands.varo.backpack.teamdoesntexist");
    public static final VaroMessage COMMANDS_VARO_BACKPACK_NO_TEAM = message("commands.varo.backpack.noteam");
    public static final VaroMessage COMMANDS_VARO_BACKPACK_CHOOSE_TYPE = message("commands.varo.backpack.choosetype");
    public static final VaroMessage COMMANDS_VARO_BACKPACK_NOT_ENABLED = message("commands.varo.backpack.notenabled");
    public static final VaroMessage COMMANDS_VARO_CONFIG_RELOADED = message("commands.varo.config.reloaded");
    public static final VaroMessage COMMANDS_VARO_CONFIG_HELP_SET = message("commands.varo.config.helpset");
    public static final VaroMessage COMMANDS_VARO_CONFIG_NO_INGAME_SET = message("commands.varo.config.noimgameset");
    public static final VaroMessage COMMANDS_VARO_CONFIG_ERROR_SET = message("commands.varo.config.errorset");
    public static final VaroMessage COMMANDS_VARO_CONFIG_HELP_SEARCH = message("commands.varo.config.helpsearch");
    public static final VaroMessage COMMANDS_VARO_CONFIG_ENTRY_SET = message("commands.varo.config.entryset");
    public static final VaroMessage COMMANDS_VARO_CONFIG_ENTRY_NOT_FOUND = message("commands.varo.config.entrynotfound");
    public static final VaroMessage COMMANDS_VARO_CONFIG_RESET = message("commands.varo.config.reset");
    public static final VaroMessage COMMANDS_VARO_CONFIG_SEARCH_LIST_TITLE = message("commands.varo.config.searchlisttitle");
    public static final VaroMessage COMMANDS_VARO_CONFIG_SEARCH_LIST_FORMAT = message("commands.varo.config.searchlistformat");
    public static final VaroMessage COMMANDS_VARO_EXPORT_SUCCESSFULL = message("commands.varo.export.players");
    public static final VaroMessage COMMANDS_VARO_PRESET_NOT_FOUND = message("commands.varo.preset.notfound");
    public static final VaroMessage COMMANDS_VARO_PRESET_PATH_TRAVERSAL = message("commands.varo.preset.pathtraversal");
    public static final VaroMessage COMMANDS_VARO_PRESET_LOADED = message("commands.varo.preset.loaded");
    public static final VaroMessage COMMANDS_VARO_PRESET_SAVED = message("commands.varo.preset.saved");
    public static final VaroMessage COMMANDS_VARO_PRESET_LIST = message("commands.varo.preset.list");
    public static final VaroMessage COMMANDS_VARO_PRESET_HELP_LOAD = message("commands.varo.preset.helploaded");
    public static final VaroMessage COMMANDS_VARO_PRESET_HELP_SAVE = message("commands.varo.preset.helpsave");
    public static final VaroMessage COMMANDS_VARO_RANDOMTEAM_HELP = message("commands.varo.randomteam.help");
    public static final VaroMessage COMMANDS_VARO_RANDOMTEAM_TEAMSIZE_TOO_SMALL = message("commands.varo.randomteam.teamsizetoosmall");
    public static final VaroMessage COMMANDS_VARO_RANDOMTEAM_SORTED = message("commands.varo.randomteam.sorted");
    public static final VaroMessage COMMANDS_VARO_RANDOMTEAM_NO_PARTNER = message("commands.varo.randomteam.nopartner");

    public static final VaroMessage COMMANDS_VARO_CHECKCOMBAT_HELP = message("commands.varo.checkcombat.help");
    public static final VaroMessage COMMANDS_VARO_CHECKCOMBAT_INCOMBAT = message("commands.varo.checkcombat.incombat");
    public static final VaroMessage COMMANDS_VARO_CHECKCOMBAT_NOTINCOMBAT = message("commands.varo.checkcombat.notincombat");
    public static final VaroMessage COMMANDS_VARO_EPISODES = message("commands.varo.episodes");
    public static final VaroMessage COMMANDS_VARO_RESTART_IN_LOBBY = message("commands.varo.restart.inlobby");
    public static final VaroMessage COMMANDS_VARO_RESTART_RESTARTED = message("commands.varo.restart.restarted");
    public static final VaroMessage COMMANDS_VARO_SCOREBOARD_DEACTIVATED = message("commands.varo.scoreboard.deactivated");
    public static final VaroMessage COMMANDS_VARO_SCOREBOARD_ENABLED = message("commands.varo.scoreboard.enabled");
    public static final VaroMessage COMMANDS_VARO_SCOREBOARD_DISABLED = message("commands.varo.scoreboard.disabled");
    public static final VaroMessage COMMANDS_VARO_SORT_HELP = message("commands.varo.sort.help");
    public static final VaroMessage COMMANDS_VARO_SORT_SORTED_WELL = message("commands.varo.sort.sorted");
    public static final VaroMessage COMMANDS_VARO_SORT_NO_SPAWN_WITH_TEAM = message("commands.varo.sort.nospawnwithteam");
    public static final VaroMessage COMMANDS_VARO_SORT_NO_SPAWN = message("commands.varo.sort.nospawn");

    public static final VaroMessage COMMANDS_VARO_DISCORD_NOT_SETUP = message("commands.varo.discord.notsetup");
    public static final VaroMessage COMMANDS_VARO_DISCORD_STATUS_ACTIVE = message("commands.varo.discord.status.active");
    public static final VaroMessage COMMANDS_VARO_DISCORD_STATUS_INACTIVE = message("commands.varo.discord.status.inactive");
    public static final VaroMessage COMMANDS_VARO_DISCORD_NOT_VERIFIED = message("commands.varo.discord.notverified");
    public static final VaroMessage COMMANDS_VARO_DISCORD_VERIFICATION_REMOVED = message("commands.varo.discord.verificationremoved");
    public static final VaroMessage COMMANDS_VARO_DISCORD_DISABLED = message("commands.varo.discord.disabled");
    public static final VaroMessage COMMANDS_VARO_DISCORD_GETLINK = message("commands.varo.discord.getlink");
    public static final VaroMessage COMMANDS_VARO_DISCORD_UNVERIFY = message("commands.varo.discord.unverify");
    public static final VaroMessage COMMANDS_VARO_DISCORD_RELOADED = message("commands.varo.discord.reloaded");
    public static final VaroMessage COMMANDS_VARO_DISCORD_SHUTDOWN = message("commands.varo.discord.shutdown");
    public static final VaroMessage COMMANDS_VARO_DISCORD_BOT_OFFLINE = message("commands.varo.discord.botoffline");
    public static final VaroMessage COMMANDS_VARO_DISCORD_NO_EVENT_CHANNEL = message("commands.varo.discord.noeventchannel");
    public static final VaroMessage COMMANDS_VARO_DISCORD_VERIFY_ENABLED = message("commands.varo.discord.verifyenabled");
    public static final VaroMessage COMMANDS_VARO_DISCORD_DISCORD_MESSAGE_TITLE = message("commands.varo.discord.discordmessagetitle");
    public static final VaroMessage COMMANDS_VARO_DISCORD_BYPASS_ACTIVE = message("commands.varo.discord.bypassactive");
    public static final VaroMessage COMMANDS_VARO_DISCORD_BYPASS_INACTIVE = message("commands.varo.discord.bypassinactive");
    public static final VaroMessage COMMANDS_VARO_DISCORD_VERIFY_ACCOUNT = message("commands.varo.discord.account");
    public static final VaroMessage COMMANDS_VARO_DISCORD_VERIFY_REMOVE_USAGE = message("commands.varo.discord.remove.usage");
    public static final VaroMessage COMMANDS_VARO_DISCORD_VERIFY_DISABLED = message("commands.varo.discord.verifydisabled");

    public static final VaroMessage COMMANDS_VARO_PLAYTIME = message("commands.varo.playtime");

    public static final VaroMessage COMMANDS_BORDER_SIZE = message("commands.border.size");
    public static final VaroMessage COMMANDS_BORDER_DISTANCE = message("commands.border.distance");
    public static final VaroMessage COMMANDS_BORDER_USAGE = message("commands.border.usage");
    public static final VaroMessage COMMANDS_BORDER_SUCCESS = message("commands.border.success");

    public static final VaroMessage COMMANDS_BROADCAST_FORMAT = message("commands.broadcast.format");

    public static final VaroMessage COMMANDS_CHATCLEAR_CLEAR = message("commands.chatclear.cleared");

    public static final VaroMessage COMMANDS_COUNTDOWN_ABORT = message("commands.countdown.abort");
    public static final VaroMessage COMMANDS_COUNTDOWN_TOO_SMALL = message("commands.countdown.tooSmall");
    public static final VaroMessage COMMANDS_COUNTDOWN_START = message("commands.countdown.start");
    public static final VaroMessage COMMANDS_COUNTDOWN_FORMAT = message("commands.countdown.format");

    public static final VaroMessage COMMANDS_TIME_DAY = message("commands.time.day");
    public static final VaroMessage COMMANDS_TIME_NIGHT = message("commands.time.night");

    public static final VaroMessage COMMANDS_WEATHER_SUN = message("commands.weather.sun");
    public static final VaroMessage COMMANDS_WEATHER_RAIN = message("commands.weather.rain");
    public static final VaroMessage COMMANDS_WEATHER_THUNDER = message("commands.weather.thunder");

    public static final VaroMessage COMMANDS_SETWORLDSPAWN = message("commands.setworldspawn.setworldspawn");
    
    public static final VaroMessage COMMANDS_SPAWN_OVERWORLD = message("commands.spawn.overworld");
    public static final VaroMessage COMMANDS_SPAWN_NETHER = message("commands.spawn.nether");
    public static final VaroMessage COMMANDS_SPAWN_END = message("commands.spawn.end");

    public static final VaroMessage COMMANDS_PING = message("commands.ping");

    public static final VaroMessageArray BROADCAST = array("broadcast");

    public static final VaroMessage TEAMREQUEST_NAME_INVALID = message("teamrequest.name.invalid");
    public static final VaroMessage TEAMREQUEST_NAME_DUPLICATE = message("teamrequest.name.duplicate");
    public static final VaroMessage TEAMREQUEST_NAME_TOO_LONG = message("teamrequest.name.tooLong");
    public static final VaroMessage TEAMREQUEST_RENAME = message("teamrequest.rename");
    public static final VaroMessage TEAMREQUEST_RENAMED = message("teamrequest.renamed");
    public static final VaroMessage TEAMREQUEST_ENTER_TEAMNAME = message("teamrequest.enterTeamName");
    public static final VaroMessage TEAMREQUEST_PLAYER_NOT_ONLINE = message("teamrequest.playerNotOnline");
    public static final VaroMessage TEAMREQUEST_REVOKED = message("teamrequest.invationRevoked");
    public static final VaroMessage TEAMREQUEST_TEAM_FULL = message("teamrequest.teamIsFull");
    public static final VaroMessage TEAMREQUEST_RECEIVED = message("teamrequest.received");
    public static final VaroMessage TEAMREQUEST_SENT = message("teamrequest.sent");
    public static final VaroMessage TEAMREQUEST_LOBBYITEM_INVITE_NAME = message("teamrequest.items.invite.name");
    public static final VaroMessageArray TEAMREQUEST_LOBBYITEM_INVITE_LORE = array("teamrequest.items.invite.lore");
    public static final VaroMessage TEAMREQUEST_LOBBYITEM_LEAVE_NAME = message("teamrequest.items.leave.name");
    public static final VaroMessageArray TEAMREQUEST_LOBBYITEM_LEAVE_LORE = array("teamrequest.items.leave.lore");
    public static final VaroMessage TEAMREQUEST_LOBBYITEM_RENAME_NAME = message("teamrequest.items.rename.name");
    public static final VaroMessageArray TEAMREQUEST_LOBBYITEM_RENAME_LORE = array("teamrequest.items.rename.lore");
    
    public static final VaroMessage SPAWN_NUMBER = message("spawn.number");
    public static final VaroMessage SPAWN_PLAYER = message("spawn.player");
    
    public static final VaroMessage PROTECTION_START = message("protection.start");
    public static final VaroMessage PROTECTION_END = message("protection.end");
    public static final VaroMessage PROTECTION_UPDATE = message("protection.update");
    public static final VaroMessage PROTECTION_PROTECTED = message("protection.protected");
    
    public static final VaroMessage BLOCKLOGGER_FILTER_INVALID = message("blocklogger.filter.invalid");
    public static final VaroMessage BLOCKLOGGER_FILTER_SET = message("blocklogger.filter.set");
    public static final VaroMessage BLOCKLOGGER_FILTER_RESET = message("blocklogger.filter.reset");
    public static final VaroMessage BLOCKLOGGER_FILTER_PLAYER = message("blocklogger.filter.player");
    public static final VaroMessage BLOCKLOGGER_FILTER_MATERIAL = message("blocklogger.filter.material");

    public static void load() throws MissingTranslationException, InvalidTypeException, IOException {
        SLAMS.load("en", JasklParser.createReadParser(YamlConfig.of(Resource.of(Messages.class.getClassLoader().getResource("en.yml")))),
                JasklParser.createReadWriteParser(YamlConfig.of(new File(messageFilePath + "en.yml"),
                "English language configuration file\nSee https://almighty-satan.github.io/varoplugin-docs/category/messages for more information")));
        SLAMS.load("de", JasklParser.createReadParser(YamlConfig.of(Resource.of(Messages.class.getClassLoader().getResource("de.yml")))),
                JasklParser.createReadWriteParser(YamlConfig.of(new File(messageFilePath + "de.yml"),
                "German language configuration file\nSee https://almighty-satan.github.io/varoplugin-docs/category/messages for more information")));

        try {
            PlaceholderAPIPlugin.getInstance();
            new SlamsPlaceholderExpansion("varo", Main.getInstance().getDescription().getAuthors().get(0), Main.getInstance().getDescription().getVersion(), PLACEHOLDERS).register();
        } catch (NoClassDefFoundError e) {
            // nop
        }
    }

    static VaroMessage message(String key) {
        StandaloneMessage message = StandaloneMessage.of(key, SLAMS, PLACEHOLDERS);
        return new VaroMessage() {
            @Override
            public void broadcast(VaroContext context) {
                try {
                    for (VaroPlayer player : VaroPlayer.getOnlinePlayer())
                        this.send(player, context);
                    Bukkit.getConsoleSender().sendMessage(message.value(context.copy()));
                } catch (Throwable t) {
                    Main.getInstance().getLogger().log(Level.SEVERE, "Unable to send message " + message.path(), t);
                }
            }

            @Override
            public void broadcast(VaroPlayer subject, PlaceholderResolver placeholders, String link) {
                try {
                    if (link == null || link.isEmpty()) {
                        this.broadcast(subject, placeholders);
                        return;
                    }
    
                    PlayerContext context = new PlayerContext(subject);
                    for (VaroPlayer player : VaroPlayer.getOnlinePlayer()) {
                        PlayerContext newContext = context.copy();
                        newContext.getMessageData().language = player.getLanguage();
                        VersionUtils.getVersionAdapter().sendLinkedMessage(player.getPlayer(), message.value(newContext, placeholders), link);
                    }
                    Bukkit.getConsoleSender().sendMessage(message.value(context, placeholders));
                } catch (Throwable t) {
                    Main.getInstance().getLogger().log(Level.SEVERE, "Unable to send message " + message.path(), t);
                }
            }

            @Override
            public void broadcast(VaroPlayer subject, PlaceholderResolver placeholders) {
                try {
                    for (VaroPlayer player : VaroPlayer.getOnlinePlayer())
                        this.send(player, placeholders);
                    Bukkit.getConsoleSender().sendMessage(message.value(new PlayerContext(subject), placeholders));
                } catch (Throwable t) {
                    Main.getInstance().getLogger().log(Level.SEVERE, "Unable to send message " + message.path(), t);
                }
            }

            @Override
            public void broadcast(VaroPlayer subject) {
                try {
                    for (VaroPlayer player : VaroPlayer.getOnlinePlayer())
                        this.send(player);
                    Bukkit.getConsoleSender().sendMessage(message.value(new PlayerContext(subject)));
                } catch (Throwable t) {
                    Main.getInstance().getLogger().log(Level.SEVERE, "Unable to send message " + message.path(), t);
                }
            }

            @Override
            public void broadcast(PlaceholderResolver placeholders) {
                try {
                    for (VaroPlayer player : VaroPlayer.getOnlinePlayer())
                        this.send(player, placeholders);
                    Bukkit.getConsoleSender().sendMessage(message.value( placeholders));
                } catch (Throwable t) {
                    Main.getInstance().getLogger().log(Level.SEVERE, "Unable to send message " + message.path(), t);
                }
            }

            @Override
            public void broadcast() {
                try {
                    for (VaroPlayer player : VaroPlayer.getOnlinePlayer())
                        this.send(player);
                    Bukkit.getConsoleSender().sendMessage(message.value());
                } catch (Throwable t) {
                    Main.getInstance().getLogger().log(Level.SEVERE, "Unable to send message " + message.path(), t);
                }
            }

            @Override
            public void send(VaroPlayer recipient, VaroContext context) {
                try {
                    VaroContext copy = context.copy();
                    copy.getMessageData().language = recipient.getLanguage();
                    recipient.sendMessage(message.value(copy));
                } catch (Throwable t) {
                    Main.getInstance().getLogger().log(Level.SEVERE, "Unable to send message " + message.path(), t);
                }
            }

            @Override
            public void send(VaroPlayer recipient, VaroPlayer subject, PlaceholderResolver placeholders) {
                try {
                    VaroContext context = new PlayerContext(subject);
                    context.getMessageData().language = recipient.getLanguage();
                    recipient.sendMessage(message.value(context, placeholders));
                } catch (Throwable t) {
                    Main.getInstance().getLogger().log(Level.SEVERE, "Unable to send message " + message.path(), t);
                }
            }

            @Override
            public void send(VaroPlayer recipient, VaroPlayer subject) {
                try {
                    VaroContext context = new PlayerContext(subject);
                    context.getMessageData().language = recipient.getLanguage();
                    recipient.sendMessage(message.value(context));
                } catch (Throwable t) {
                    Main.getInstance().getLogger().log(Level.SEVERE, "Unable to send message " + message.path(), t);
                }
            }

            @Override
            public void send(VaroPlayer subject, PlaceholderResolver placeholders) {
                try {
                    VaroContext context = new PlayerContext(subject);
                    context.getMessageData().language = subject.getLanguage();
                    subject.sendMessage(message.value(context, placeholders));
                } catch (Throwable t) {
                    Main.getInstance().getLogger().log(Level.SEVERE, "Unable to send message " + message.path(), t);
                }
            }

            @Override
            public void send(VaroPlayer subject) {
                try {
                    VaroContext context = new PlayerContext(subject);
                    context.getMessageData().language = subject.getLanguage();
                    subject.sendMessage(message.value(context));
                } catch (Throwable t) {
                    Main.getInstance().getLogger().log(Level.SEVERE, "Unable to send message " + message.path(), t);
                }
            }

            @Override
            public void send(CommandSender recipient, VaroPlayer subject) {
                try {
                    if (recipient instanceof Player)
                        this.send(VaroPlayer.getPlayer((Player) recipient), subject);
                    else
                        recipient.sendMessage(message.value(new PlayerContext(subject)));
                } catch (Throwable t) {
                    Main.getInstance().getLogger().log(Level.SEVERE, "Unable to send message " + message.path(), t);
                }
            }

            @Override
            public void send(CommandSender subject, PlaceholderResolver placeholders) {
                try {
                    if (subject instanceof Player)
                        this.send(VaroPlayer.getPlayer((Player) subject), placeholders);
                    else
                        subject.sendMessage(message.value(placeholders));
                } catch (Throwable t) {
                    Main.getInstance().getLogger().log(Level.SEVERE, "Unable to send message " + message.path(), t);
                }
            }

            @Override
            public void send(CommandSender subject) {
                try {
                    if (subject instanceof Player)
                        this.send(VaroPlayer.getPlayer((Player) subject));
                    else
                        subject.sendMessage(message.value());
                } catch (Throwable t) {
                    Main.getInstance().getLogger().log(Level.SEVERE, "Unable to send message " + message.path(), t);
                }
            }

            @Override
            public void kick(VaroPlayer recipient, VaroContext context) {
                try {
                    Player player = recipient.getPlayer();
                    if (player != null) {
                        VaroContext copy = context.copy();
                        copy.getMessageData().language = recipient.getLanguage();
                        player.kickPlayer(message.value(copy));
                    }
                } catch (Throwable t) {
                    Main.getInstance().getLogger().log(Level.SEVERE, "Unable to send message " + message.path(), t);
                }
            }

            @Override
            public void kick(VaroPlayer subject, PlaceholderResolver placeholders) {
                try {
                    Player player = subject.getPlayer();
                    if (player != null) {
                        PlayerContext context = new PlayerContext(subject);
                        context.getMessageData().language = subject.getLanguage();
                        player.kickPlayer(message.value(context, placeholders));
                    }
                } catch (Throwable t) {
                    Main.getInstance().getLogger().log(Level.SEVERE, "Unable to send message " + message.path(), t);
                }
            }

            @Override
            public void kick(VaroPlayer subject) {
                try {
                    Player player = subject.getPlayer();
                    if (player != null) {
                        PlayerContext context = new PlayerContext(subject);
                        context.getMessageData().language = subject.getLanguage();
                        player.kickPlayer(message.value(context));
                    }
                } catch (Throwable t) {
                    Main.getInstance().getLogger().log(Level.SEVERE, "Unable to send message " + message.path(), t);
                }
            }

            @Override
            public String value(VaroPlayer subject, PlaceholderResolver placeholders) {
                try {
                    return message.value(new PlayerContext(subject), placeholders);
                } catch (Throwable t) {
                    Main.getInstance().getLogger().log(Level.SEVERE, "Unable to send message " + message.path(), t);
                    return "MESSAGE_ERROR";
                }
            }

            @Override
            public String value(VaroPlayer subject) {
                try {
                    return message.value(new PlayerContext(subject));
                } catch (Throwable t) {
                    Main.getInstance().getLogger().log(Level.SEVERE, "Unable to send message " + message.path(), t);
                    return "MESSAGE_ERROR";
                }
            }
            
            @Override
            public String value(PlaceholderResolver placeholders) {
                try {
                    return message.value(placeholders);
                    
                } catch (Throwable t) {
                    Main.getInstance().getLogger().log(Level.SEVERE, "Unable to send message " + message.path(), t);
                    return "MESSAGE_ERROR";
                }
            }
            
            @Override
            public String value() {
                try {
                    return message.value();
                } catch (Throwable t) {
                    Main.getInstance().getLogger().log(Level.SEVERE, "Unable to send message " + message.path(), t);
                    return "MESSAGE_ERROR";
                }
            }

            @Override
            public void log(LogType type, VaroContext context, PlaceholderResolver placeholders) {
                try {
                    Main.getDataManager().getVaroLoggerManager().getEventLogger().println(type, message.value(context, placeholders));
                } catch (Throwable t) {
                    Main.getInstance().getLogger().log(Level.SEVERE, "Unable to send message " + message.path(), t);
                }
            }

            @Override
            public void log(LogType type, VaroContext context) {
                try {
                    Main.getDataManager().getVaroLoggerManager().getEventLogger().println(type, message.value(context));
                } catch (Throwable t) {
                    Main.getInstance().getLogger().log(Level.SEVERE, "Unable to send message " + message.path(), t);
                }
            }

            @Override
            public void log(LogType type, VaroPlayer subject, PlaceholderResolver placeholders) {
                try {
                    Main.getDataManager().getVaroLoggerManager().getEventLogger().println(type, message.value(new PlayerContext(subject), placeholders), subject.getRealUUID());
                } catch (Throwable t) {
                    Main.getInstance().getLogger().log(Level.SEVERE, "Unable to send message " + message.path(), t);
                }
            }

            @Override
            public void log(LogType type, VaroPlayer subject) {
                try {
                    Main.getDataManager().getVaroLoggerManager().getEventLogger().println(type, message.value(new PlayerContext(subject)), subject.getRealUUID());
                } catch (Throwable t) {
                    Main.getInstance().getLogger().log(Level.SEVERE, "Unable to send message " + message.path(), t);
                }
            }

            @Override
            public void log(LogType type, PlaceholderResolver placeholders) {
                try {
                    Main.getDataManager().getVaroLoggerManager().getEventLogger().println(type, message.value(placeholders));
                } catch (Throwable t) {
                    Main.getInstance().getLogger().log(Level.SEVERE, "Unable to send message " + message.path(), t);
                }
            }

            @Override
            public void log(LogType type) {
                try {
                    Main.getDataManager().getVaroLoggerManager().getEventLogger().println(type, message.value());
                } catch (Throwable t) {
                    Main.getInstance().getLogger().log(Level.SEVERE, "Unable to send message " + message.path(), t);
                }
            }

            @Override
            public void alert(AlertType type, VaroPlayer subject, PlaceholderResolver placeholders) {
                try {
                    String value = message.value(new PlayerContext(subject), placeholders);
                    new Alert(type, value);
                    Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, value, subject.getRealUUID());
                } catch (Throwable t) {
                    Main.getInstance().getLogger().log(Level.SEVERE, "Unable to send message " + message.path(), t);
                }
            }

            @Override
            public void alert(AlertType type, VaroPlayer subject) {
                try {
                    String value = message.value(new PlayerContext(subject));
                    new Alert(type, value);
                    Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, value, subject.getRealUUID());
                } catch (Throwable t) {
                    Main.getInstance().getLogger().log(Level.SEVERE, "Unable to send message " + message.path(), t);
                }
            }
        };
    }

    static VaroMessageArray array(String key) {
        StandaloneMessageArray message = StandaloneMessageArray.of(key, SLAMS, PLACEHOLDERS);
        return new VaroMessageArray() {

            @Override
            public int size(VaroPlayer subject) {
                return message.translate(new PlayerContext(subject)).size();
            }

            @Override
            public int size() {
                return message.translate(null).size();
            }

            @Override
            public String[] value(VaroContext context) {
                try {
                    return message.value(context);
                } catch (Throwable t) {
                    Main.getInstance().getLogger().log(Level.SEVERE, "Unable to send message " + message.path(), t);
                    return Collections.nCopies(size(), "MESSAGE_ERROR").toArray(new String[0]);
                }
            }

            @Override
            public String[] value(VaroPlayer subject) {
                try {
                    VaroContext ctx = new PlayerContext(subject);
                    ctx.getMessageData().language = subject.getLanguage();
                    return message.value(ctx);
                } catch (Throwable t) {
                    Main.getInstance().getLogger().log(Level.SEVERE, "Unable to send message " + message.path(), t);
                    return Collections.nCopies(size(subject), "MESSAGE_ERROR").toArray(new String[0]);
                }
            }

            @Override
            public String value(int index, VaroContext context) {
                try {
                    return message.translate(context).get(index).value(context);
                } catch (Throwable t) {
                    Main.getInstance().getLogger().log(Level.SEVERE, "Unable to send message " + message.path(), t);
                    return "MESSAGE_ERROR";
                }
            }

            @Override
            public String value(int index, VaroPlayer subject) {
                try {
                    VaroContext ctx = new PlayerContext(subject);
                    ctx.getMessageData().language = subject.getLanguage();
                    return message.translate(ctx).get(index).value(ctx);
                } catch (Throwable t) {
                    Main.getInstance().getLogger().log(Level.SEVERE, "Unable to send message " + message.path(), t);
                    return "MESSAGE_ERROR";
                }
            }
        };
    }

    static VaroMessageArray2d array2d(String key) {
        StandaloneMessageArray2d message = StandaloneMessageArray2d.of(key, SLAMS, PLACEHOLDERS);
        return new VaroMessageArray2d() {

            @Override
            public int size(VaroPlayer subject) {
                return message.translate(new PlayerContext(subject)).size();
            }

            @Override
            public int size() {
                return message.translate(null).size();
            }

            @Override
            public String[][] value(VaroContext context) {
                try {
                    return message.value(context);
                } catch (Throwable t) {
                    Main.getInstance().getLogger().log(Level.SEVERE, "Unable to send message " + message.path(), t);
                    return Collections.nCopies(size(), new String[] {"MESSAGE_ERROR"}).toArray(new String[0][]);
                }
            }

            @Override
            public String[][] value(VaroPlayer subject) {
                try {
                    VaroContext ctx = new PlayerContext(subject);
                    ctx.getMessageData().language = subject.getLanguage();
                    return message.value(ctx);
                } catch (Throwable t) {
                    Main.getInstance().getLogger().log(Level.SEVERE, "Unable to send message " + message.path(), t);
                    return Collections.nCopies(size(subject), new String[] {"MESSAGE_ERROR"}).toArray(new String[0][]);
                }
            }

            @Override
            public String[] value(int index, VaroContext context) {
                try {
                    return message.translate(context).get(index).value(context);
                } catch (Throwable t) {
                    Main.getInstance().getLogger().log(Level.SEVERE, "Unable to send message " + message.path(), t);
                    return new String[] {"MESSAGE_ERROR"};
                }
            }

            @Override
            public String[] value(int index, VaroPlayer subject) {
                try {
                    VaroContext ctx = new PlayerContext(subject);
                    ctx.getMessageData().language = subject.getLanguage();
                    return message.translate(ctx).get(index).value(ctx);
                } catch (Throwable t) {
                    Main.getInstance().getLogger().log(Level.SEVERE, "Unable to send message " + message.path(), t);
                    return new String[] {"MESSAGE_ERROR"};
                }
            }
        };
    }

    public interface VaroMessage {
        void broadcast(VaroContext context);
        void broadcast(VaroPlayer subject, PlaceholderResolver placeholders, String link);
        void broadcast(VaroPlayer subject, PlaceholderResolver placeholders);
        void broadcast(VaroPlayer subject);
        void broadcast(PlaceholderResolver placeholders);
        void broadcast();

        void send(VaroPlayer recipient, VaroContext context);
        void send(VaroPlayer recipient, VaroPlayer subject, PlaceholderResolver placeholders);
        void send(VaroPlayer recipient, VaroPlayer subject);
        void send(VaroPlayer subject, PlaceholderResolver placeholders);
        void send(VaroPlayer subject);

        void send(CommandSender recipient, VaroPlayer subject);
        void send(CommandSender subject, PlaceholderResolver placeholders);
        void send(CommandSender subject);

        void kick(VaroPlayer recipient, VaroContext context);
        void kick(VaroPlayer subject, PlaceholderResolver placeholders);
        void kick(VaroPlayer subject);

        void log(LogType type, VaroContext context, PlaceholderResolver placeholders);
        void log(LogType type, VaroContext context);
        void log(LogType type, VaroPlayer subject, PlaceholderResolver placeholders);
        void log(LogType type, VaroPlayer subject);
        void log(LogType type, PlaceholderResolver placeholders);
        void log(LogType type);

        void alert(AlertType type, VaroPlayer subject, PlaceholderResolver placeholders);
        void alert(AlertType type, VaroPlayer subject);

        String value(VaroPlayer subject, PlaceholderResolver placeholders);
        String value(VaroPlayer subject);
        String value(PlaceholderResolver placeholders);
        String value();
    }

    public interface VaroMessageArray {
        int size(VaroPlayer subject);
        int size();

        String[] value(VaroContext context);
        String[] value(VaroPlayer subject);
        String value(int index, VaroContext context);
        String value(int index, VaroPlayer subject);
    }

    public interface VaroMessageArray2d {
        int size(VaroPlayer subject);
        int size();

        String[][] value(VaroContext context);
        String[][] value(VaroPlayer subject);
        String[] value(int index, VaroContext context);
        String[] value(int index, VaroPlayer subject);
    }
}
