package de.varoplugin.varo.task.game;

import de.varoplugin.varo.api.event.game.VaroStateChangeEvent;
import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.VaroState;
import de.varoplugin.varo.task.AbstractTrigger;
import de.varoplugin.varo.task.VaroRegistrable;
import de.varoplugin.varo.task.VaroTrigger;
import org.bukkit.event.EventHandler;

import java.util.Set;

public class GameStateTrigger extends AbstractTrigger {

    private final VaroState state;

    private GameStateTrigger(Varo varo, VaroState state, boolean match, Set<VaroTrigger> children, Set<VaroRegistrable> registrations) {
        super(varo, match, children, registrations);
        this.state = state;
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
    protected boolean shouldTrigger() {
        return this.state == this.getVaro().getState();
    }

    @EventHandler
    public void onGameStateChange(VaroStateChangeEvent event) {
        this.shouldTrigger(this.state == event.getState());
    }

    // TODO: Clone activated state?
    @Override
    public GameStateTrigger deepClone() {
        return new GameStateTrigger(this.getVaro(), this.state, this.isMatch(),
                this.getSubTriggerCloned(), this.getRegistrationsCloned());
    }
}
