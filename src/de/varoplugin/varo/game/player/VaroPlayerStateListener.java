package de.varoplugin.varo.game.player;

import de.varoplugin.varo.api.event.game.player.VaroPlayerRemoveEvent;
import de.varoplugin.varo.api.event.game.player.VaroPlayerStateChangeEvent;
import de.varoplugin.varo.game.VaroStateListener;
import de.varoplugin.varo.game.player.state.VaroPlayerState;
import org.bukkit.event.EventHandler;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public abstract class VaroPlayerStateListener extends VaroStateListener {

    private final VaroPlayerState state;
    protected final VaroPlayer player;

    public VaroPlayerStateListener(VaroPlayer player) {
        super(player.getVaro(), player.getVaro().getState());

        this.state = player.getState();
        this.player = player;
    }

    @Override
    public boolean shallListen() {
        return super.shallListen() && this.player.getState().equals(this.state);
    }

    @EventHandler
    public void onPlayerStateChange(VaroPlayerStateChangeEvent event) {
        if (this.player.equals(event.getPlayer()) && !this.state.equals(event.getPlayer().getState()))
            this.unregister();
    }

    @EventHandler
    public void onPlayerRemove(VaroPlayerRemoveEvent event) {
        if (this.player.equals(event.getPlayer())) this.unregister();
    }
}