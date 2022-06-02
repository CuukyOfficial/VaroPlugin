package de.varoplugin.varo;

import de.varoplugin.varo.event.VaroEvent;
import org.bukkit.plugin.Plugin;

public interface VaroPlugin extends Plugin {

    void callEvent(VaroEvent event);

}