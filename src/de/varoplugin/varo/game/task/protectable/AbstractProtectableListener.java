package de.varoplugin.varo.game.task.protectable;

import de.varoplugin.varo.game.world.protectable.Protectable;
import de.varoplugin.varo.api.task.AbstractListener;
import de.varoplugin.varo.api.task.VaroTask;

public abstract class AbstractProtectableListener extends AbstractListener {

    private Protectable protectable;

    public AbstractProtectableListener(Protectable protectable) {
        super(protectable.getVaro());
        this.protectable = protectable;
    }

    public Protectable getProtectable() {
        return this.protectable;
    }

    @Override
    public VaroTask clone() {
        AbstractProtectableListener listener = (AbstractProtectableListener) super.clone();
        listener.protectable = this.protectable;
        return listener;
    }
}
