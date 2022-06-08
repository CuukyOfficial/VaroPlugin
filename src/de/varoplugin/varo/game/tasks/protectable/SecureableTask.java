package de.varoplugin.varo.game.tasks.protectable;

import de.varoplugin.varo.game.tasks.VaroTask;
import de.varoplugin.varo.game.world.protectable.VaroProtectable;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public abstract class SecureableTask extends VaroTask {

    protected final VaroProtectable secureable;

    public SecureableTask(VaroProtectable secureable) {
        this.secureable = secureable;
    }
}