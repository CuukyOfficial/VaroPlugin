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

import de.cuuky.varo.config.language.Contexts.PlayerContext;
import de.cuuky.varo.player.VaroPlayer;
import io.github.almightysatan.jaskl.Resource;
import io.github.almightysatan.jaskl.yaml.YamlConfig;
import io.github.almightysatan.slams.InvalidTypeException;
import io.github.almightysatan.slams.MissingTranslationException;
import io.github.almightysatan.slams.PlaceholderResolver;
import io.github.almightysatan.slams.Slams;
import io.github.almightysatan.slams.parser.JasklParser;
import io.github.almightysatan.slams.standalone.StandaloneMessage;

public final class Messages {

    static final String DEFAULT_LANGUAGE = "en";
    private static final Slams SLAMS = Slams.create(DEFAULT_LANGUAGE);
    private static final PlaceholderResolver PLACEHOLDERS = Placeholders.getPlaceholders();
    
    public static final VaroMessage PLAYER_JOIN_BROADCAST = message("player.join.broadcast");
    public static final VaroMessage PLAYER_JOIN_REQUIRED = message("player.join.required");
    public static final VaroMessage PLAYER_JOIN_FINALE = message("player.join.finale");
    public static final VaroMessage PLAYER_JOIN_PROTECTION = message("player.join.protection");
    public static final VaroMessage PLAYER_JOIN_PROTECTION_END = message("player.join.protectionEnd");
    public static final VaroMessage PLAYER_JOIN_SPECTATOR = message("player.join.spectator");
    public static final VaroMessage PLAYER_JOIN_MASS_RECORDING = message("player.join.massrecording");
    public static final VaroMessage PLAYER_JOIN_REMAINING_TIME = message("player.join.remainingTime");
    
    public static final VaroMessage PLAYER_MOVE_PROTECTION = message("player.moveProtection");
    
    public static final VaroMessage FINALE_START_FREEZE = message("finale.start.freeze");
    public static final VaroMessage FINALE_START_NOFREEZE = message("finale.start.noFreeze");
    
    public static final VaroMessage GAME_START_COUNTDOWN = message("game.start.countdown");
    
    public static void load() throws MissingTranslationException, InvalidTypeException, IOException {
        SLAMS.load("en", JasklParser.createParser(YamlConfig.of(Resource.of(Messages.class.getClassLoader().getResource("en.yml")))));
    }
    
    public static VaroMessage message(String key) {
        StandaloneMessage message = StandaloneMessage.of(key, SLAMS, PLACEHOLDERS);
        return new VaroMessage() {
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
            public void send(VaroPlayer subject) {
                subject.sendMessage(message.value(new PlayerContext(subject)));
            }
        };
    }
    
    public interface VaroMessage {
        void broadcast(VaroPlayer subject, PlaceholderResolver placeholders);
        void broadcast(VaroPlayer subject);
        void broadcast(PlaceholderResolver placeholders);
        
        void send(VaroPlayer subject);
    }
}
