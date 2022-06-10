package de.varoplugin.varo;

import de.varoplugin.varo.api.event.VaroEvent;
import de.varoplugin.varo.config.VaroConfig;
import de.varoplugin.varo.game.Varo;
import org.bukkit.event.Cancellable;
import org.bukkit.plugin.Plugin;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public interface VaroPlugin extends Plugin {

    VaroConfig getVaroConfig();

    Varo getVaro();

    <T extends VaroEvent> T callEvent(T event);

    /**
     * Calls the event and returns if the event has been cancelled.
     *
     * @param event The event
     * @param <T> The type of event
     * @return If the event has been cancelled
     */
    <T extends VaroEvent & Cancellable> boolean isCancelled(T event);

    String getWebsite();
    
    String getGithub();

    String getDiscordInvite();
}