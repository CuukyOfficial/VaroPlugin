package de.varoplugin.varo.game.task.trigger;

import de.varoplugin.varo.api.event.game.VaroStateChangeEvent;
import de.varoplugin.varo.api.task.trigger.Trigger;
import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.State;
import org.bukkit.event.EventHandler;

public class GameStateTrigger extends GameTrigger {

    private State state;

    public GameStateTrigger(Varo varo, State state, boolean match) {
        super(varo, match);
        this.state = state;
    }

    public GameStateTrigger(Varo varo, State state) {
        this(varo, state, true);
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
    public Trigger clone() {
        GameStateTrigger trigger = (GameStateTrigger) super.clone();
        trigger.state = state;
        return trigger;
    }
}
