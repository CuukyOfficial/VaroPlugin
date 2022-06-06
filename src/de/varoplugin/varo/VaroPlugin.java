package de.varoplugin.varo;

import de.varoplugin.varo.api.event.VaroEvent;
import de.varoplugin.varo.api.event.game.VaroGameCancelableEvent;
import org.bukkit.plugin.Plugin;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public interface VaroPlugin extends Plugin {

    <T extends VaroEvent> T callEvent(T event);

    /**
     * Calls the event and returns if the event has been cancelled.
     *
     * @param event The event
     * @param <T> The type of event
     * @return If the event has been cancelled
     */
    <T extends VaroGameCancelableEvent> boolean isCancelled(T event);

}