package de.varoplugin.varo.task.game;

import de.varoplugin.varo.api.event.game.VaroStateChangeEvent;
import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.VaroState;
import de.varoplugin.varo.task.AbstractTrigger;
import de.varoplugin.varo.task.VaroTrigger;
import org.bukkit.event.EventHandler;

public class GameStateTrigger extends AbstractTrigger {

    private VaroState state;

    protected GameStateTrigger() {
    }

    public GameStateTrigger(Varo varo, VaroState state, boolean match) {
        super(varo, match);
        this.state = state;
    }

    public GameStateTrigger(Varo varo, VaroState state) {
        super(varo);
        this.state = state;
    }

    @Override
    protected boolean isTriggered() {
        return this.state == this.getVaro().getState();
    }

    @EventHandler
    public void onGameStateChange(VaroStateChangeEvent event) {
        this.triggerIf(this.state == event.getState());
    }

    @Override
    public VaroTrigger clone() {
        GameStateTrigger trigger = (GameStateTrigger) super.clone();
        trigger.state = state;
        return trigger;
    }
}
