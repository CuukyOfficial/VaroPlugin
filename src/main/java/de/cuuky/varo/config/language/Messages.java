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

package de.cuuky.varo.config.language;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.alert.Alert;
import de.cuuky.varo.alert.AlertType;
import de.cuuky.varo.config.language.Contexts.PlayerContext;
import de.cuuky.varo.config.language.Contexts.VaroContext;
import de.cuuky.varo.logger.logger.EventLogger.LogType;
import de.cuuky.varo.player.VaroPlayer;
import de.varoplugin.cfw.version.VersionUtils;
import io.github.almightysatan.jaskl.Resource;
import io.github.almightysatan.jaskl.yaml.YamlConfig;
import io.github.almightysatan.slams.Context;
import io.github.almightysatan.slams.InvalidTypeException;
import io.github.almightysatan.slams.MissingTranslationException;
import io.github.almightysatan.slams.PlaceholderResolver;
import io.github.almightysatan.slams.Slams;
import io.github.almightysatan.slams.parser.JasklParser;
import io.github.almightysatan.slams.standalone.StandaloneMessage;
import io.github.almightysatan.slams.standalone.StandaloneMessageArray;
import io.github.almightysatan.slams.standalone.StandaloneMessageArray2d;

public final class Messages {

    static final String DEFAULT_LANGUAGE = "en";
    private static final Slams SLAMS = Slams.create(DEFAULT_LANGUAGE);
    private static final PlaceholderResolver PLACEHOLDERS = Placeholders.getPlaceholders();

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
    public static final VaroMessage LOG_GAME_STARTED = message("log.gameStarted");
    public static final VaroMessage LOG_JOIN_FINALE = message("log.finale");
    public static final VaroMessage LOG_KICKED_PLAYER = message("log.kickedPlayer");
    public static final VaroMessage LOG_SESSIONS_ENDED = message("log.sessionsEnded");
    public static final VaroMessage LOG_STRIKE_GENERAL = message("log.strike.general");
    public static final VaroMessage LOG_STRIKE_COMBAT_LOG = message("log.strike.combatlog");
    public static final VaroMessage LOG_STRIKE_FIRST_NEVER_ONLINE = message("log.strike.firstNeverOnline");
    public static final VaroMessage LOG_STRIKE_FIRST = message("log.strike.first");
    public static final VaroMessage LOG_STRIKE_SECOND = message("log.strike.second");
    public static final VaroMessage LOG_STRIKE_THRID = message("log.strike.third");
    public static final VaroMessage LOG_NEW_SESSIONS = message("log.newSessions");
    public static final VaroMessage LOG_NEW_SESSIONS_FOR_ALL = message("log.newSessionsForAll");
    public static final VaroMessage LOG_PLAYER_DC_TO_EARLY = message("log.playerQuitToEarly");
    public static final VaroMessage LOG_PLAYER_JOIN_MASSREC = message("log.playerJoinMassrec");
    public static final VaroMessage LOG_PLAYER_JOIN_NORMAL = message("log.playerJoinNormal");
    public static final VaroMessage LOG_PLAYER_JOINED = message("log.playerJoined");
    public static final VaroMessage LOG_PLAYER_QUIT = message("log.playerQuit");
    public static final VaroMessage LOG_PLAYER_RECONNECT = message("log.playerReconnect");
    public static final VaroMessage LOG_SWITCHED_NAME = message("log.switchedName");
    public static final VaroMessage LOG_TELEPORTED_TO_MIDDLE = message("log.teleportedToMiddle");
    public static final VaroMessage LOG_WINNER = message("log.win.player");
    public static final VaroMessage LOG_WINNER_TEAM = message("log.win.team");

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
    
    public static final VaroMessage PLAYER_KICK_DEATH = message("player.kick.death");
    public static final VaroMessage PLAYER_KICK_KILL = message("player.kick.kill");
    public static final VaroMessage PLAYER_KICK_NOT_USER_OF_PROJECT = message("player.kick.notUserOfProject");
    public static final VaroMessage PLAYER_KICK_SERVER_FULL = message("player.kick.serverFull");
    public static final VaroMessage PLAYER_KICK_STRIKE_BAN = message("player.kick.strikeBan");
    public static final VaroMessage PLAYER_KICK_BANNED = message("player.kick.banned");
    public static final VaroMessage PLAYER_KICK_NO_PREPRODUCES_LEFT = message("player.kick.noPreproduceLeft");
    public static final VaroMessage PLAYER_KICK_NO_SESSIONS_LEFT = message("player.kick.noSessionLeft");
    public static final VaroMessage PLAYER_KICK_NO_TIME_LEFT = message("player.kick.noTimeLeft");
    public static final VaroMessage PLAYER_KICK_NOT_STARTED = message("player.kick.notStarted");
    public static final VaroMessage PLAYER_KICK_SESSION_OVER = message("player.kick.sessionOver");
    public static final VaroMessage PLAYER_KICK_MASS_REC_SESSION_OVER = message("player.kick.kickMessageMassRec");
    public static final VaroMessage PLAYER_KICK_TOO_MANY_STRIKES = message("player.kick.tooManyStrikes");
    public static final VaroMessage PLAYER_KICK_COMMAND = message("player.kick.command");

    public static final VaroMessage PLAYER_MOVE_PROTECTION = message("player.moveProtection");
    public static final VaroMessage PLAYER_SPECTATOR_HEIGHT = message("player.spectator.height");
    public static final VaroMessage PLAYER_CRAFTING_DISALLOWED = message("player.crafting.disallowed");

    public static final VaroMessageArray PLAYER_SCOREBOARD_TITLE = array("player.scoreboard.title");
    public static final VaroMessageArray2d PLAYER_SCOREBOARD_CONTENT = array2d("player.scoreboard.content");
    public static final VaroMessageArray PLAYER_ACTIONBAR = array("player.actionbar");
    public static final VaroMessageArray2d PLAYER_TABLIST_HEADER = array2d("player.tablist.header");
    public static final VaroMessageArray2d PLAYER_TABLIST_FOOTER = array2d("player.tablist.footer");

    public static final VaroMessage CHAT_DEFAULT = message("chat.default");
    public static final VaroMessage CHAT_TEAM = message("chat.team");
    public static final VaroMessage CHAT_MUTED = message("chat.muted");
    public static final VaroMessage CHAT_SPECTATOR = message("chat.spectator");
    public static final VaroMessage CHAT_START = message("chat.start");

    public static final VaroMessage FINALE_START_FREEZE = message("finale.start.freeze");
    public static final VaroMessage FINALE_START_NOFREEZE = message("finale.start.noFreeze");

    public static final VaroMessage GAME_START_COUNTDOWN = message("game.start.countdown");

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
    public static final VaroMessage COMMANDS_VARO_DISCORD_PLEASE_RELOAD = message("commands.varo.discord.pleasereload");
    public static final VaroMessage COMMANDS_VARO_DISCORD_VERIFY_DISABLED = message("commands.varo.discord.verifydisabled");
    public static final VaroMessage COMMANDS_VARO_DISCORD_BOT_DISABLED = message("commands.varo.discord.botdisabled");
    public static final VaroMessage COMMANDS_VARO_DISCORD_USER_NOT_FOUND = message("commands.varo.discord.usernotfound");
    public static final VaroMessage COMMANDS_VARO_INTRO_ALREADY_STARTED = message("commands.varo.intro.alreadystarted");
    public static final VaroMessage COMMANDS_VARO_INTRO_GAME_ALREADY_STARTED = message("commands.varo.intro.gamealreadystarted");
    public static final VaroMessage COMMANDS_VARO_INTRO_STARTED = message("commands.varo.intro.started");
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
    public static final VaroMessage COMMANDS_VARO_DISCORD_VERIFY_SYSTEM_DISABLED = message("commands.varo.discord.verifysystemdisabled");
    public static final VaroMessage COMMANDS_VARO_DISCORD_DISCORDBOT_DISABLED = message("commands.varo.discord.discordbotdisabled");
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
    public static final VaroMessage TEAMREQUEST_TEAM_REQUEST_RECIEVED = message("teamrequest.teamRequestRecieved");
    public static final VaroMessage TEAMREQUEST_INVITED_TEAM = message("teamrequest.invitedInTeam");
    public static final VaroMessage TEAMREQUEST_NO_TEAMNAME = message("teamrequest.noteamname");
    public static final VaroMessage TEAMREQUEST_LOBBYITEM_INVITE_NAME = message("teamrequest.items.invite.name");
    public static final VaroMessage TEAMREQUEST_LOBBYITEM_INVITE_LORE = message("teamrequest.items.invite.lore");
    public static final VaroMessage TEAMREQUEST_LOBBYITEM_LEAVE_NAME = message("teamrequest.items.leave.name");
    public static final VaroMessage TEAMREQUEST_LOBBYITEM_LEAVE_LORE = message("teamrequest.items.leave.lore");
    public static final VaroMessage TEAMREQUEST_LOBBYITEM_RENAME_NAME = message("teamrequest.items.rename.name");
    public static final VaroMessage TEAMREQUEST_LOBBYITEM_RENAME_LORE = message("teamrequest.items.rename.lore");

    public static void load() throws MissingTranslationException, InvalidTypeException, IOException {
        SLAMS.load("en", JasklParser.createParser(YamlConfig.of(Resource.of(Messages.class.getClassLoader().getResource("en.yml")))));
    }

    static VaroMessage message(String key) {
        StandaloneMessage message = StandaloneMessage.of(key, SLAMS, PLACEHOLDERS);
        return new VaroMessage() {
            @Override
            public void broadcast(VaroContext context) {
                Bukkit.broadcastMessage(message.value(context));
            }

            @Override
            public void broadcast(VaroPlayer subject, PlaceholderResolver placeholders, String link) {
                if (link == null || link.isEmpty()) {
                    this.broadcast(subject, placeholders);
                    return;
                }

                String value = message.value(new PlayerContext(subject), placeholders);
                for (Player player : Bukkit.getOnlinePlayers())
                    VersionUtils.getVersionAdapter().sendLinkedMessage(player, value, link);
                Bukkit.getConsoleSender().sendMessage(value);
            }

            @Override
            public void broadcast(VaroPlayer subject, PlaceholderResolver placeholders) {
                Bukkit.broadcastMessage(message.value(new PlayerContext(subject), placeholders));
            }

            @Override
            public void broadcast(VaroPlayer subject) {
                Bukkit.broadcastMessage(message.value(new PlayerContext(subject)));
            }

            @Override
            public void broadcast(PlaceholderResolver placeholders) {
                Bukkit.broadcastMessage(message.value(null, placeholders));
            }

            @Override
            public void broadcast() {
                Bukkit.broadcastMessage(message.value());
            }

            @Override
            public void send(VaroPlayer recipient, VaroContext context) {
                recipient.sendMessage(message.value(context));
            }

            @Override
            public void send(VaroPlayer recipient, VaroPlayer subject, PlaceholderResolver placeholders) {
                recipient.sendMessage(message.value(new PlayerContext(subject), placeholders));
            }

            @Override
            public void send(VaroPlayer recipient, VaroPlayer subject) {
                recipient.sendMessage(message.value(new PlayerContext(subject)));
            }

            @Override
            public void send(VaroPlayer subject, PlaceholderResolver placeholders) {
                subject.sendMessage(message.value(new PlayerContext(subject), placeholders));
            }

            @Override
            public void send(VaroPlayer subject) {
                subject.sendMessage(message.value(new PlayerContext(subject)));
            }

            @Override
            public void send(CommandSender recipient, VaroPlayer subject) {
                if (recipient instanceof Player)
                    this.send(VaroPlayer.getPlayer((Player) recipient), subject);
                else
                    recipient.sendMessage(message.value(new PlayerContext(subject)));
            }

            @Override
            public void send(CommandSender subject, PlaceholderResolver placeholders) {
                if (subject instanceof Player)
                    this.send(VaroPlayer.getPlayer((Player) subject), placeholders);
                else
                    subject.sendMessage(message.value(placeholders));
            }

            @Override
            public void send(CommandSender subject) {
                if (subject instanceof Player)
                    this.send(VaroPlayer.getPlayer((Player) subject));
                else
                    subject.sendMessage(message.value());
            }

            @Override
            public void kick(VaroPlayer recipient, VaroContext context) {
                // TODO Auto-generated method stub
   
            }
            
            @Override
            public void kick(VaroPlayer subject) {
                // TODO Auto-generated method stub

            }

            @Override
            public String value(VaroPlayer subject, PlaceholderResolver placeholders) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public String value(VaroPlayer subject) {
                return message.value(new PlayerContext(subject));
            }
            
            @Override
            public String value(PlaceholderResolver placeholders) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public void log(LogType type, VaroContext context, PlaceholderResolver placeholders) {
                // TODO
                // Main.getDataManager().getVaroLoggerManager().getEventLogger().println();
            }

            @Override
            public void log(LogType type, VaroContext context) {
                // TODO Auto-generated method stub

            }

            @Override
            public void log(LogType type, VaroPlayer subject, PlaceholderResolver placeholders) {
                // TODO Auto-generated method stub

            }

            @Override
            public void log(LogType type, VaroPlayer subject) {
                // TODO Auto-generated method stub

            }

            @Override
            public void log(LogType type, PlaceholderResolver placeholders) {
                // TODO Auto-generated method stub

            }

            @Override
            public void log(LogType type) {
                // TODO Auto-generated method stub

            }

            @Override
            public void alert(AlertType type, VaroPlayer subject, PlaceholderResolver placeholders) {
                // TODO Auto-generated method stub

            }

            @Override
            public void alert(AlertType type, VaroPlayer subject) {
                String value = message.value(new PlayerContext(subject));
                new Alert(type, value);
                // TODO log
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
            public String[] value(VaroPlayer subject) {
                return message.value(new PlayerContext(subject));
            }

            @Override
            public String value(int index, VaroPlayer subject) {
                Context ctx = new PlayerContext(subject);
                return message.translate(ctx).get(index).value(ctx);
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
            public String[][] value(VaroPlayer subject) {
                return message.value(new PlayerContext(subject));
            }

            @Override
            public String[] value(int index, VaroPlayer subject) {
                Context ctx = new PlayerContext(subject);
                return message.translate(ctx).get(index).value(ctx);
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
    }

    public interface VaroMessageArray {
        int size(VaroPlayer subject);

        String[] value(VaroPlayer subject);
        String value(int index, VaroPlayer subject);
    }

    public interface VaroMessageArray2d {
        int size(VaroPlayer subject);

        String[][] value(VaroPlayer subject);
        String[] value(int index, VaroPlayer subject);
    }
}
