package de.varoplugin.varo.task.trigger.player;

import de.varoplugin.varo.api.event.game.player.PlayerParticipantStateChangeEvent;
import de.varoplugin.varo.api.task.trigger.Trigger;
import de.varoplugin.varo.game.entity.player.ParticipantState;
import de.varoplugin.varo.game.entity.player.VaroPlayer;
import org.bukkit.event.EventHandler;

public class VaroParticipantStateTrigger extends AbstractPlayerTrigger {

    private ParticipantState state;

    public VaroParticipantStateTrigger(VaroPlayer player, ParticipantState state, boolean match) {
        super(player, match);
        this.state = state;
    }

    public VaroParticipantStateTrigger(VaroPlayer player, ParticipantState state) {
        this(player, state, true);
    }

    @Override
    protected boolean isTriggered() {
        return this.getPlayer().getState().equals(this.state);
    }

    @EventHandler
    public void onPlayerStateChange(PlayerParticipantStateChangeEvent event) {
        if (!this.getPlayer().equals(event.getPlayer())) return;
        if (this.state == null) this.triggerIf(true);
        this.triggerIf(event.getState().equals(this.state));
    }

    @Override
    public Trigger clone() {
        VaroParticipantStateTrigger trigger = (VaroParticipantStateTrigger) super.clone();
        trigger.state = this.state;
        return trigger;
    }
}
