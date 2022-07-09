package de.varoplugin.varo.ui.listener;

import de.varoplugin.varo.api.event.game.world.protectable.VaroProtectableAddEvent;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.event.EventHandler;

public class PlayerAddProtectablePrinter extends UiListener {

    @EventHandler
    public void onProtectableAdd(VaroProtectableAddEvent event) {
        // Won't work for anything other than a player
        Bukkit.getPlayer(event.getProtectable().getHolder().getUuid()).sendMessage("Block saved");

        for (int i = 0; i < 6; i++)
            event.getProtectable().getBlock().getWorld().playEffect(
                    event.getProtectable().getBlock().getLocation(), Effect.ENDER_SIGNAL, 1);
    }
}
