package de.varoplugin.varo.ui.listener;

import de.varoplugin.varo.api.event.game.VaroInitializedEvent;
import de.varoplugin.varo.api.event.game.player.PlayerInitializedEvent;
import de.varoplugin.varo.game.State;
import de.varoplugin.varo.task.trigger.builder.VaroTriggerBuilder;
import de.varoplugin.varo.ui.tasks.StartingUiTask;
import org.bukkit.event.EventHandler;

public class DefaultUiTasks extends UiListener {

    @EventHandler
    public void onGameInitialize(VaroInitializedEvent event) {
        new VaroTriggerBuilder(event.getVaro()).whenState(State::hasStartCountdown).complete().register(new StartingUiTask(event.getVaro()));
    }

    @EventHandler
    public void onPlayerInitialize(PlayerInitializedEvent event) {
//        new VaroPlayerTriggerBuilder(event.getPlayer()).when(VaroState.RUNNING).when(VaroState.MASS_RECORDING)
//                .and(VaroParticipantState.ALIVE).and(true).complete().register(new PlayerShowCountdownTask(event.getPlayer()));
    }
}
