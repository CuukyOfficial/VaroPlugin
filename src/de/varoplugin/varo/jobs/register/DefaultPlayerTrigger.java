package de.varoplugin.varo.jobs.register;

import de.varoplugin.varo.game.GameState;
import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.jobs.VaroTrigger;
import de.varoplugin.varo.jobs.game.VaroStateTrigger;
import de.varoplugin.varo.jobs.game.player.NoMoveTask;
import de.varoplugin.varo.jobs.game.player.PlayerOnlineTrigger;

import java.util.function.Function;

public enum DefaultPlayerTrigger {

    LOBBY_ONLINE_PLAYER((player) -> new VaroStateTrigger(GameState.LOBBY, new PlayerOnlineTrigger(player, true, new NoMoveTask(player))));

    private final Function<VaroPlayer, VaroTrigger> trigger;

    DefaultPlayerTrigger(Function<VaroPlayer, VaroTrigger> trigger) {
        this.trigger = trigger;
    }

    public VaroTrigger createTrigger(VaroPlayer player) {
        return this.trigger.apply(player);
    }
}