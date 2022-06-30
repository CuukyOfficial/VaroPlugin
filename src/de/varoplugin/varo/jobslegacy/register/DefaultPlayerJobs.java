package de.varoplugin.varo.jobslegacy.register;

import de.varoplugin.varo.game.GameState;
import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.jobslegacy.VaroTrigger;
import de.varoplugin.varo.jobslegacy.game.VaroStateTrigger;
import de.varoplugin.varo.jobslegacy.game.player.NoMoveListener;
import de.varoplugin.varo.jobslegacy.game.player.PlayerOnlineTrigger;

import java.util.function.Function;

public enum DefaultPlayerJobs {

    LOBBY_ONLINE_PLAYER((player) -> new VaroStateTrigger(GameState.LOBBY, new PlayerOnlineTrigger(player, true, new NoMoveListener(player))));

    private final Function<VaroPlayer, VaroTrigger> trigger;

    DefaultPlayerJobs(Function<VaroPlayer, VaroTrigger> trigger) {
        this.trigger = trigger;
    }

    public VaroTrigger createTrigger(VaroPlayer player) {
        return this.trigger.apply(player);
    }
}