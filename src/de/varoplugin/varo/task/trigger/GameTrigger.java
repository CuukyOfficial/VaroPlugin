package de.varoplugin.varo.task.trigger;

import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.api.task.trigger.ParentTrigger;
import de.varoplugin.varo.api.task.trigger.Trigger;

public abstract class GameTrigger extends ParentTrigger {

    private Varo varo;

    public GameTrigger(Varo varo, boolean match) {
        super(varo.getPlugin(), match);
        this.varo = varo;
    }

    public Varo getVaro() {
        return this.varo;
    }

    @Override
    public Trigger clone() {
        GameTrigger trigger = (GameTrigger) super.clone();
        trigger.varo = this.varo;
        return trigger;
    }
}
