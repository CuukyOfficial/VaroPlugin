package de.varoplugin.varo.game.task;

import de.varoplugin.varo.api.task.RunnableTask;
import de.varoplugin.varo.game.Varo;

public abstract class VaroRunnableTask extends RunnableTask {

    private final Varo varo;

    public VaroRunnableTask(Varo varo) {
        super(varo.getPlugin());
        this.varo = varo;
    }

    public Varo getVaro() {
        return this.varo;
    }
}
