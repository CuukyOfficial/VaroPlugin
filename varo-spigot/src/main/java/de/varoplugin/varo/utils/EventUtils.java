package de.varoplugin.varo.utils;

import org.bukkit.Bukkit;

import de.varoplugin.varo.api.VaroEvent;

public final class EventUtils {

    private EventUtils() {
        throw new UnsupportedOperationException();
    }

    public static boolean callEvent(VaroEvent event) {
        Bukkit.getPluginManager().callEvent(event);
        return event.isCancelled();
    }
}
