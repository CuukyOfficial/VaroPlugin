package de.varoplugin.varo.task;

import de.varoplugin.varo.api.task.ListenerTask;
import de.varoplugin.varo.game.Varo;

public abstract class VaroListenerTask extends ListenerTask {

    private final Varo varo;

    public VaroListenerTask(Varo varo) {
        super(varo.getPlugin());
        this.varo = varo;
    }

    public Varo getVaro() {
        return this.varo;
    }
}
