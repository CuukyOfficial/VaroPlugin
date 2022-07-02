package de.varoplugin.varo.ui.listener;

import de.varoplugin.varo.api.event.game.VaroGameInitializedEvent;
import de.varoplugin.varo.game.GameState;
import de.varoplugin.varo.game.task.trigger.builder.VaroTriggerBuilder;
import de.varoplugin.varo.ui.tasks.StartingUiTask;
import org.bukkit.event.EventHandler;

public class DefaultUiTasks extends UiListener {

    @EventHandler
    public void onGameInitialize(VaroGameInitializedEvent event) {
        new VaroTriggerBuilder(this.getPlugin()).or(GameState.STARTING).complete(event.getVaro()).register(
            new StartingUiTask(event.getVaro())
        );
    }
}
