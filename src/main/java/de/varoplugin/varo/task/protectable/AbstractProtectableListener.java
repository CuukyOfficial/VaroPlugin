package de.varoplugin.varo.task.protectable;

import de.varoplugin.varo.api.task.Task;
import de.varoplugin.varo.task.VaroListenerTask;
import de.varoplugin.varo.game.world.protectable.Protectable;

public abstract class AbstractProtectableListener extends VaroListenerTask {

    private Protectable protectable;

    public AbstractProtectableListener(Protectable protectable) {
        super(protectable.getVaro());
        this.protectable = protectable;
    }

    public Protectable getProtectable() {
        return this.protectable;
    }

    @Override
    public Task clone() {
        AbstractProtectableListener listener = (AbstractProtectableListener) super.clone();
        listener.protectable = this.protectable;
        return listener;
    }
}
