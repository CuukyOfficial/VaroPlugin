package de.varoplugin.varo.game.task.protectable;

import de.varoplugin.varo.game.world.protectable.VaroProtectable;
import de.varoplugin.varo.task.AbstractListener;
import de.varoplugin.varo.task.VaroTask;

public abstract class AbstractProtectableListener extends AbstractListener {

    private VaroProtectable protectable;

    public AbstractProtectableListener(VaroProtectable protectable) {
        super(protectable.getVaro());
        this.protectable = protectable;
    }

    public VaroProtectable getProtectable() {
        return this.protectable;
    }

    @Override
    public VaroTask clone() {
        AbstractProtectableListener listener = (AbstractProtectableListener) super.clone();
        listener.protectable = this.protectable;
        return listener;
    }
}
