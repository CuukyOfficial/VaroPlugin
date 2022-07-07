package de.varoplugin.varo.game.task.register;

import de.varoplugin.varo.api.event.game.VaroGameInitializedEvent;
import de.varoplugin.varo.api.event.game.player.VaroPlayerInitializedEvent;
import de.varoplugin.varo.api.event.game.world.protectable.VaroProtectableInitializedEvent;
import de.varoplugin.varo.game.GameState;
import de.varoplugin.varo.game.entity.player.ParticipantState;
import de.varoplugin.varo.game.task.*;
import de.varoplugin.varo.game.task.player.NoMoveListener;
import de.varoplugin.varo.game.task.player.PlayerDeathListener;
import de.varoplugin.varo.game.task.player.PlayerRegisterProtectablesListener;
import de.varoplugin.varo.game.task.protectable.ProtectableAccessListener;
import de.varoplugin.varo.game.task.trigger.ProtectableTrigger;
import de.varoplugin.varo.game.task.trigger.builder.VaroPlayerTriggerBuilder;
import de.varoplugin.varo.game.task.trigger.builder.VaroTriggerBuilder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class DefaultTaskRegister implements Listener {

    @EventHandler
    public void onGameInitialize(VaroGameInitializedEvent event) {
        new VaroTriggerBuilder(event.getVaro()).when(GameState.LOBBY).complete().register(
                new RegisterPlayerListener(event.getVaro())
        );

        new VaroTriggerBuilder(event.getVaro()).whenNot(GameState.LOBBY).complete().register(
                new KickNonRegisteredPlayerListener(event.getVaro())
        );

        new VaroTriggerBuilder(event.getVaro()).when(GameState.STARTING).complete().register(
                new StartingTask(event.getVaro())
        );

        new VaroTriggerBuilder(event.getVaro()).when(GameState.RUNNING).complete().register(
                new EndGameListener(event.getVaro())
        );

        new VaroTriggerBuilder(event.getVaro()).when(GameState.STARTING)
                .and(event.getVaro().getPlugin().getVaroConfig().random_team_at_start).complete().register(
                new RandomPlayerTask(event.getVaro(), 2) // TODO: Team size config entry and trigger for config entry
        );
    }

    @EventHandler
    public void onPlayerInitialize(VaroPlayerInitializedEvent event) {
        new VaroTriggerBuilder(event.getVaro()).when(GameState.LOBBY).when(GameState.STARTING).complete().register(
                new NoMoveListener(event.getPlayer())
        );

        new VaroPlayerTriggerBuilder(event.getPlayer()).when(GameState.RUNNING).when(GameState.MASS_RECORDING).vp(
                b -> b.when(ParticipantState.ALIVE).and(true)
        ).complete().register(
                new PlayerDeathListener(event.getPlayer()), new PlayerRegisterProtectablesListener(event.getPlayer()));
    }

    @EventHandler
    public void onProtectableInitialize(VaroProtectableInitializedEvent event) {
        new VaroTriggerBuilder(event.getVaro()).when(GameState.RUNNING).when(GameState.MASS_RECORDING)
                .and(new ProtectableTrigger(event.getProtectable())).complete().register(
                new ProtectableAccessListener(event.getProtectable())
        );
    }
}
