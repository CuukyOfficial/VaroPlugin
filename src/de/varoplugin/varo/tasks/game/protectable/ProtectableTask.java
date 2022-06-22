package de.varoplugin.varo.tasks.game.protectable;

import de.varoplugin.varo.game.world.protectable.VaroProtectable;
import de.varoplugin.varo.tasks.AbstractVaroTask;
import de.varoplugin.varo.tasks.register.VaroRegisterInfo;

// TODO: Extend task
public abstract class ProtectableTask extends AbstractVaroTask<VaroRegisterInfo> {

    protected final VaroProtectable secureable;

    public ProtectableTask(VaroProtectable secureable) {
        this.secureable = secureable;
    }
}