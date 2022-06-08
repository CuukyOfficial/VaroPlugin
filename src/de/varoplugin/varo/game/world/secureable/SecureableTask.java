package de.varoplugin.varo.game.world.secureable;

import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.tasks.VaroTask;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public abstract class SecureableTask extends VaroTask {

    protected final VaroSecureable secureable;

    public SecureableTask(Varo varo, VaroSecureable secureable) {
        super(varo);

        this.secureable = secureable;
    }
}