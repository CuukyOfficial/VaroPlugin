package de.varoplugin.varo.game.task.trigger.player;

import de.varoplugin.varo.api.event.game.player.VaroPlayerParticipantStateChangeEvent;
import de.varoplugin.varo.api.task.trigger.VaroTrigger;
import de.varoplugin.varo.game.entity.player.VaroParticipantState;
import de.varoplugin.varo.game.entity.player.VaroPlayer;
import org.bukkit.event.EventHandler;

public class VaroParticipantStateTrigger extends AbstractPlayerTrigger {

    private VaroParticipantState state;

    public VaroParticipantStateTrigger(VaroPlayer player, VaroParticipantState state, boolean match) {
        super(player, match);
        this.state = state;
    }

    public VaroParticipantStateTrigger(VaroPlayer player, VaroParticipantState state) {
        this(player, state, true);
    }

    @Override
    protected boolean isTriggered() {
        return this.getPlayer().getState().equals(this.state);
    }

    @EventHandler
    public void onPlayerStateChange(VaroPlayerParticipantStateChangeEvent event) {
        if (!this.getPlayer().equals(event.getPlayer())) return;
        this.triggerIf(event.getState().equals(this.state));
    }

    @Override
    public VaroTrigger clone() {
        VaroParticipantStateTrigger trigger = (VaroParticipantStateTrigger) super.clone();
        trigger.state = this.state;
        return trigger;
    }
}
