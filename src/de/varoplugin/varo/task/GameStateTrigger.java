package de.varoplugin.varo.task;

import de.varoplugin.varo.api.event.game.VaroStateChangeEvent;
import de.varoplugin.varo.game.VaroState;
import org.bukkit.event.EventHandler;

public class GameStateTrigger extends AbstractTrigger {

    private final VaroState state;

    public GameStateTrigger(VaroState state, boolean match) {
        super(match);
        this.state = state;
    }

    public GameStateTrigger(VaroState state) {
        this(state, true);
    }

    @EventHandler
    public void onGameStateChange(VaroStateChangeEvent event) {
        if (this.shallRegister(this.state == event.getState())) this.registerChildren();
        else this.deregisterChildren();
    }
}
