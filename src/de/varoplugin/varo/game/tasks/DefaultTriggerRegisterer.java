package de.varoplugin.varo.game.tasks;

import de.varoplugin.varo.api.event.game.VaroGameInitializedEvent;
import de.varoplugin.varo.api.event.game.player.VaroPlayerInitializedEvent;
import de.varoplugin.varo.game.tasks.game.DefaultGameTrigger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.Arrays;

/**
 * Registers all default trigger.
 *
 * @author CuukyOfficial
 * @version v0.1
 */
public class DefaultTriggerRegisterer implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onGameInitialize(VaroGameInitializedEvent event) {
        Arrays.stream(DefaultGameTrigger.values()).map(DefaultGameTrigger::createTrigger)
            .forEach(t -> t.register(event.getVaro()));
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerInitialize(VaroPlayerInitializedEvent event) {
        // TODO: Register player trigger
    }
}