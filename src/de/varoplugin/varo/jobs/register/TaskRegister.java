package de.varoplugin.varo.jobs.register;

import de.varoplugin.varo.api.event.game.VaroGameInitializedEvent;
import de.varoplugin.varo.api.event.game.player.VaroPlayerInitializedEvent;
import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.jobs.VaroJob;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.Arrays;
import java.util.function.Function;

/**
 * Registers all default trigger.
 */
public class TaskRegister implements Listener {

    private <T> void register(T[] holders, Function<T, VaroJob> creator, Varo varo) {
        Arrays.stream(holders).map(creator).forEach(t -> t.register(varo));
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onGameInitialize(VaroGameInitializedEvent event) {
        this.register(DefaultGameTrigger.values(), DefaultGameTrigger::createTrigger, event.getVaro());
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerInitialize(VaroPlayerInitializedEvent event) {
        this.register(DefaultPlayerTrigger.values(), t -> t.createTrigger(event.getPlayer()), event.getVaro());
    }
}