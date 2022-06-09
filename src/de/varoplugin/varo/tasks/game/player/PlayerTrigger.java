package de.varoplugin.varo.tasks.game.player;

import de.varoplugin.varo.api.event.game.player.VaroPlayerRemoveEvent;
import de.varoplugin.varo.game.VaroState;
import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.tasks.game.VaroStateTrigger;
import org.bukkit.event.EventHandler;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class PlayerTrigger extends VaroStateTrigger<VaroPlayerTask> implements VaroPlayerTrigger {

    private VaroPlayer player;

    public PlayerTrigger(VaroState state) {
        super(state);
    }

    @Override
    protected void register(VaroPlayerTask task) {
        task.register(this.player);
    }

    @EventHandler
    public void onPlayerRemove(VaroPlayerRemoveEvent event) {
        if (!event.getPlayer().equals(this.player)) return;
        this.unregister();
    }

    @Override
    public void setPlayer(VaroPlayer player) {
        this.player = player;
    }

    @Override
    public VaroPlayer getPlayer() {
        return this.player;
    }
}