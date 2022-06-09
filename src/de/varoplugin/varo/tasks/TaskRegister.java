package de.varoplugin.varo.tasks;

import de.varoplugin.varo.api.event.game.VaroGameInitializedEvent;
import de.varoplugin.varo.api.event.game.player.VaroPlayerInitializedEvent;
import de.varoplugin.varo.tasks.game.DefaultGameTrigger;
import de.varoplugin.varo.tasks.game.player.DefaultPlayerTrigger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.Arrays;
import java.util.function.Consumer;

/**
 * Registers all default trigger.
 *
 * @author CuukyOfficial
 * @version v0.1
 */
public class TaskRegister implements Listener {

    private <T extends VaroTaskTrigger<V>, V extends VaroTask> void register(VaroTriggerHolder<T, V>[] holders, Consumer<T> register) {
        Arrays.stream(holders).map(VaroTriggerHolder::createTrigger).forEach(register);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onGameInitialize(VaroGameInitializedEvent event) {
        this.register(DefaultGameTrigger.values(), t -> t.register(event.getVaro()));
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerInitialize(VaroPlayerInitializedEvent event) {
        this.register(DefaultPlayerTrigger.values(), t -> t.register(event.getPlayer()));
    }
}