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

package de.varoplugin.varo;

import de.varoplugin.varo.api.event.VaroEvent;
import de.varoplugin.varo.config.VaroConfig;
import de.varoplugin.varo.config.language.Language;
import de.varoplugin.varo.config.language.Messages;
import de.varoplugin.varo.game.Varo;
import org.bukkit.event.Cancellable;
import org.bukkit.plugin.Plugin;

import com.j256.ormlite.support.ConnectionSource;

import java.io.File;

public interface VaroPlugin extends Plugin {

    <T extends VaroEvent> T callEvent(T event);

    /**
     * Calls the event and returns if the event has been cancelled.
     *
     * @param event The event
     * @param <T> The type of event
     * @return If the event has been cancelled
     */
    <T extends VaroEvent & Cancellable> boolean isCancelled(T event);

    File getFile();

    VaroConfig getVaroConfig();
    
    Language[] getLanguages();
    
    Messages getMessages();
    
    ConnectionSource getConnectionSource();

    Varo getVaro();

    String getWebsite();

    String getGithub();

    String getDiscordInvite();
}