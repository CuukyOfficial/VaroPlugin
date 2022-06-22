package de.varoplugin.varo.tasks.register;

import de.varoplugin.varo.api.event.game.VaroGameInitializedEvent;
import de.varoplugin.varo.api.event.game.player.VaroPlayerInitializedEvent;
import de.varoplugin.varo.tasks.VaroTriggerHolder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.Arrays;

/**
 * Registers all default trigger.
 */
public class TaskRegister implements Listener {

    /**
     * Registers all triggers of a @{@link VaroTriggerHolder}
     *
     * @param holders The holders
     * @param register The register consume to register the triggers
     * @param <T> The trigger type
     */
    private <T extends VaroRegisterInfo> void register(VaroTriggerHolder<T>[] holders, T register) {
        Arrays.stream(holders).map(VaroTriggerHolder::createTrigger).forEach(t -> t.register(register));
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onGameInitialize(VaroGameInitializedEvent event) {
        this.register(DefaultGameTrigger.values(), new RegisterInfo(event.getVaro()));
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerInitialize(VaroPlayerInitializedEvent event) {
        this.register(DefaultPlayerTrigger.values(), new PlayerTaskInfo(event.getPlayer()));
    }
}