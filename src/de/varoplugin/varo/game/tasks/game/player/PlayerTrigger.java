package de.varoplugin.varo.game.tasks.game.player;

import de.varoplugin.varo.api.event.game.player.VaroPlayerRemoveEvent;
import de.varoplugin.varo.game.VaroState;
import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.game.tasks.VaroTask;
import de.varoplugin.varo.game.tasks.VaroStateTrigger;
import org.bukkit.event.EventHandler;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public abstract class PlayerTrigger extends VaroStateTrigger implements VaroPlayerTrigger {

    private VaroPlayer player;

    public PlayerTrigger(VaroState state, VaroTask... tasks) {
        super(state, tasks);
    }

    @Override
    protected boolean isInitialized() {
        return this.player != null;
    }

    @Override
    public boolean register(VaroPlayer player) {
        return this.register(player.getVaro());
    }

    @EventHandler
    public void onPlayerRemove(VaroPlayerRemoveEvent event) {
        if (!event.getPlayer().equals(this.player)) return;
        this.unregister();
    }
}