package de.varoplugin.varo.game.player.listener;

import de.varoplugin.varo.api.event.game.player.VaroPlayerRemoveEvent;
import de.varoplugin.varo.api.event.game.player.VaroPlayerStateChangeEvent;
import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.VaroStateListener;
import de.varoplugin.varo.game.VaroState;
import de.varoplugin.varo.game.player.VaroPlayer;
import de.varoplugin.varo.game.player.VaroPlayerState;
import org.bukkit.event.EventHandler;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public abstract class VaroPlayerStateListener extends VaroStateListener {

    private final VaroPlayerState state;
    protected final VaroPlayer player;

    public VaroPlayerStateListener(Varo varo, VaroState state, VaroPlayer player) {
        super(varo, state);

        this.state = player.getState();
        this.player = player;
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