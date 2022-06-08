package de.varoplugin.varo.game.tasks.game.protectable;

import de.varoplugin.varo.game.tasks.VaroGameTask;
import de.varoplugin.varo.game.world.protectable.VaroProtectable;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public abstract class ProtectableTask extends VaroGameTask {

    protected final VaroProtectable secureable;

    public ProtectableTask(VaroProtectable secureable) {
        this.secureable = secureable;
    }
}