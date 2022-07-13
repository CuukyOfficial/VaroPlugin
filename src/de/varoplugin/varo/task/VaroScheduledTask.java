package de.varoplugin.varo.task;

import de.varoplugin.varo.api.task.ScheduledTask;
import de.varoplugin.varo.game.Varo;

public abstract class VaroScheduledTask extends ScheduledTask {

    private final Varo varo;

    public VaroScheduledTask(Varo varo) {
        super(varo.getPlugin());
        this.varo = varo;
    }

    public Varo getVaro() {
        return this.varo;
    }
}
