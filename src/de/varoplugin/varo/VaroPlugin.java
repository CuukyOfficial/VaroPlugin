package de.varoplugin.varo;

import de.varoplugin.varo.api.event.VaroEvent;
import org.bukkit.plugin.Plugin;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public interface VaroPlugin extends Plugin {

    <T extends VaroEvent> T callEvent(T event);

}