package de.varoplugin.varo;

import de.varoplugin.varo.event.VaroEvent;
import org.bukkit.plugin.Plugin;

public interface VaroPlugin extends Plugin {

    <T extends VaroEvent> T callEvent(T event);

}