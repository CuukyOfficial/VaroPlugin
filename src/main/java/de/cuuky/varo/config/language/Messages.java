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

    public static final VaroMessage PLAYER_MOVE_PROTECTION = message("player.moveProtection");

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
            public String value(VaroPlayer subject) {
                return message.value(new PlayerContext(subject));
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
        
        void log(LogType type, VaroContext context, PlaceholderResolver placeholders);
        void log(LogType type, VaroContext context);
        void log(LogType type, VaroPlayer subject, PlaceholderResolver placeholders);
        void log(LogType type, VaroPlayer subject);
        void log(LogType type, PlaceholderResolver placeholders);
        void log(LogType type);
        
        void alert(AlertType type, VaroPlayer subject, PlaceholderResolver placeholders);
        void alert(AlertType type, VaroPlayer subject);
        
        String value(VaroPlayer subject); // TODO remove this?
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
