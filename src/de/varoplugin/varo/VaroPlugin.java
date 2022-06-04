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

    <T extends VaroGameCancelableEvent> boolean isCancelled(T event);

}