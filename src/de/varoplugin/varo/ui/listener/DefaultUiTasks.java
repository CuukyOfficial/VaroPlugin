package de.varoplugin.varo.ui.listener;

import de.varoplugin.varo.api.event.game.VaroGameInitializedEvent;
import de.varoplugin.varo.api.event.game.player.VaroPlayerInitializedEvent;
import de.varoplugin.varo.game.DefaultState;
import de.varoplugin.varo.game.entity.player.ParticipantState;
import de.varoplugin.varo.game.task.trigger.builder.VaroPlayerTriggerBuilder;
import de.varoplugin.varo.game.task.trigger.builder.VaroTriggerBuilder;
import de.varoplugin.varo.ui.tasks.StartingUiTask;
import org.bukkit.event.EventHandler;

public class DefaultUiTasks extends UiListener {

    @EventHandler
    public void onGameInitialize(VaroGameInitializedEvent event) {
        new VaroTriggerBuilder(event.getVaro()).when(DefaultState.STARTING).complete().register(
            new StartingUiTask(event.getVaro())
        );
    }

    @EventHandler
    public void onPlayerInitialize(VaroPlayerInitializedEvent event) {
        new VaroPlayerTriggerBuilder(event.getPlayer()).when(DefaultState.RUNNING).when(DefaultState.MASS_RECORDING)
                .and(ParticipantState.ALIVE).and(true).complete().register(new PlayerShowCountdownTask(event.getPlayer()));
    }
}
