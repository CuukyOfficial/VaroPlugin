package de.varoplugin.varo.ui.listener;

import de.varoplugin.varo.api.event.game.VaroGameInitializedEvent;
import de.varoplugin.varo.api.event.game.player.VaroPlayerInitializedEvent;
import de.varoplugin.varo.game.GameState;
import de.varoplugin.varo.game.entity.player.ParticipantState;
import de.varoplugin.varo.game.task.trigger.builder.VaroPlayerTriggerBuilder;
import de.varoplugin.varo.game.task.trigger.builder.VaroTriggerBuilder;
import de.varoplugin.varo.ui.tasks.StartingUiTask;
import org.bukkit.event.EventHandler;

public class DefaultUiTasks extends UiListener {

    @EventHandler
    public void onGameInitialize(VaroGameInitializedEvent event) {
        new VaroTriggerBuilder(event.getVaro()).when(GameState.STARTING).complete().register(
            new StartingUiTask(event.getVaro())
        );
    }

    @EventHandler
    public void onPlayerInitialize(VaroPlayerInitializedEvent event) {
        new VaroPlayerTriggerBuilder(event.getPlayer()).when(GameState.RUNNING).when(GameState.MASS_RECORDING).andVp(
                b -> b.when(ParticipantState.ALIVE).and(true)).complete().register(new PlayerShowCountdownTask(event.getPlayer()));
    }
}
