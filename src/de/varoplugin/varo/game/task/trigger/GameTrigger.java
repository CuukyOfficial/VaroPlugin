package de.varoplugin.varo.game.task.trigger;

import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.task.trigger.AbstractTrigger;
import de.varoplugin.varo.task.trigger.VaroTrigger;

public abstract class GameTrigger extends AbstractTrigger {

    private Varo varo;

    public GameTrigger(Varo varo, boolean match) {
        super(varo.getPlugin(), match);
        this.varo = varo;
    }

    public Varo getVaro() {
        return this.varo;
    }

    @Override
    public VaroTrigger clone() {
        GameTrigger trigger = (GameTrigger) super.clone();
        trigger.varo = this.varo;
        return trigger;
    }
}
