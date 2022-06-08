package de.varoplugin.varo.game.world.secureable;

import de.varoplugin.varo.game.tasks.VaroTask;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public abstract class SecureableTask extends VaroTask {

    protected final VaroSecureable secureable;

    public SecureableTask(VaroSecureable secureable) {
        this.secureable = secureable;
    }
}