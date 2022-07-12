package de.varoplugin.varo;

import de.varoplugin.varo.api.event.VaroEvent;
import de.varoplugin.varo.config.VaroConfig;
import de.varoplugin.varo.config.language.Language;
import de.varoplugin.varo.config.language.Messages;
import de.varoplugin.varo.game.Varo;
import org.bukkit.event.Cancellable;
import org.bukkit.plugin.Plugin;

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

    Varo getVaro();

    String getWebsite();

    String getGithub();

    String getDiscordInvite();
}