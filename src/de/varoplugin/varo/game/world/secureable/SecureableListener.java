package de.varoplugin.varo.game.world.secureable;

import de.varoplugin.varo.api.event.game.VaroStateChangeEvent;
import de.varoplugin.varo.api.event.game.world.secureable.VaroSecureableRemoveEvent;
import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.tasks.VaroStateTask;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public abstract class SecureableListener extends VaroStateTask {

    protected final VaroSecureable secureable;

    public SecureableListener(Varo varo, VaroSecureable secureable) {
        super(varo);

        this.secureable = secureable;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSecureableRemove(VaroSecureableRemoveEvent event) {
        if (this.secureable.equals(event.getSecureable()) && !event.isCancelled()) this.unregister();
    }

    @Override
    public boolean onGameStateChange(VaroStateChangeEvent event) {
        return super.onGameStateChange(event);
    }
}