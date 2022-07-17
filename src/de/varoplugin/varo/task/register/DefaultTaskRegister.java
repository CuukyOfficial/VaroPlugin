package de.varoplugin.varo.task.register;

import de.varoplugin.varo.api.event.game.VaroInitializedEvent;
import de.varoplugin.varo.api.event.game.player.PlayerInitializedEvent;
import de.varoplugin.varo.api.event.game.world.protectable.ProtectableInitializedEvent;
import de.varoplugin.varo.api.task.Task;
import de.varoplugin.varo.game.State;
import de.varoplugin.varo.task.*;
import de.varoplugin.varo.task.protectable.ProtectableAccessListener;
import de.varoplugin.varo.task.trigger.AutoStartTrigger;
import de.varoplugin.varo.task.trigger.ProtectableTrigger;
import de.varoplugin.varo.task.trigger.builder.IVaroPlayerTriggerBuilder;
import de.varoplugin.varo.task.trigger.builder.VaroPlayerTriggerBuilder;
import de.varoplugin.varo.task.trigger.builder.VaroTriggerBuilder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.function.Predicate;

public class DefaultTaskRegister implements Listener {

    private void stateBuilder(VaroInitializedEvent event, Predicate<State> test, Task... tasks) {
        new VaroTriggerBuilder(event.getVaro()).whenState(test).complete().register(tasks);
    }

    private IVaroPlayerTriggerBuilder playerBuilder(PlayerInitializedEvent event) {
        return new VaroPlayerTriggerBuilder(event.getPlayer());
    }

    @EventHandler
    public void onGameInitialize(VaroInitializedEvent event) {
        new VaroTriggerBuilder(event.getVaro()).whenState(State::allowsStart).and(new AutoStartTrigger(event.getVaro()))
                .complete().register(new AutoStartTask(event.getVaro()));

        // Merge register listener?
        this.stateBuilder(event, State::allowsNonRegistered, new RegisterPlayerListener(event.getVaro()));
        this.stateBuilder(event, s -> !s.allowsNonRegistered(), new KickNonRegisteredPlayerListener(event.getVaro()));
        this.stateBuilder(event, State::hasStartCountdown, new StartingTask(event.getVaro()));
        this.stateBuilder(event, State::canFillChests, new FillChestsTask(event.getVaro()));
        this.stateBuilder(event, State::canFinish, new EndGameListener(event.getVaro()));

        new VaroTriggerBuilder(event.getVaro()).whenState(State::canDoRandomTeam)
                .and(event.getVaro().getPlugin().getVaroConfig().random_team_at_start).complete().register(
                        new RandomPlayerTask(event.getVaro(), 2)); // TODO: Team size config entry and trigger for config entry
    }

    @EventHandler
    public void onPlayerInitialize(PlayerInitializedEvent event) {
//        this.builder(event.getVaro(), v -> !v.allowsPlayerMovement(), new NoMoveListener(event.getPlayer()));

//        new VaroPlayerTriggerBuilder(event.getPlayer()).when(VaroState.RUNNING).when(VaroState.MASS_RECORDING)
//                .and(VaroParticipantState.ALIVE).and(true).complete().register(
//                        new CountdownTask(event.getPlayer()),
//                        new PlayerNoKickRadiusListener(event.getPlayer()),
//                        new PlayerInGameJoinListener(event.getPlayer()),
//                        new PlayerKillOnDeathListener(event.getPlayer()),
//                        new PlayerRegisterProtectablesListener(event.getPlayer()));
    }

    @EventHandler
    public void onProtectableInitialize(ProtectableInitializedEvent event) {
        new VaroTriggerBuilder(event.getVaro()).whenState(State::blocksProtectableAccess)
                .and(new ProtectableTrigger(event.getProtectable())).complete().register(
                        new ProtectableAccessListener(event.getProtectable()));
    }
}
