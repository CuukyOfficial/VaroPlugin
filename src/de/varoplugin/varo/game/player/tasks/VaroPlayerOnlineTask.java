package de.varoplugin.varo.game.player.tasks;

import de.varoplugin.varo.game.CancelableTask;
import de.varoplugin.varo.game.player.VaroPlayer;

/**
 * Registers a specific varo player task, which only is activated if
 * the player is online.
 *
 * @author CuukyOfficial
 * @version v0.1
 */
public abstract class VaroPlayerOnlineTask extends VaroPlayerStateTask {

    private final CancelableTask activator;

    public VaroPlayerOnlineTask(VaroPlayer player) {
        super(player);

        this.activator = new OnlineTaskActivator(player, this);
    }

    public void unregisterOnlySelf() {
        super.unregister();
    }

    @Override
    public boolean register() {
        this.activator.register();
        if (!this.player.isOnline()) return false;
        return super.register();
    }

    @Override
    public boolean unregister() {
        this.activator.unregister();
        return super.unregister();
    }
}