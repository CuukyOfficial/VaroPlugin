package de.varoplugin.varo.tasks.game.protectable;

import de.varoplugin.varo.game.world.protectable.VaroProtectable;
import de.varoplugin.varo.tasks.AbstractVaroTask;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public abstract class ProtectableTask extends AbstractVaroTask {

    protected final VaroProtectable secureable;

    public ProtectableTask(VaroProtectable secureable) {
        this.secureable = secureable;
    }
}