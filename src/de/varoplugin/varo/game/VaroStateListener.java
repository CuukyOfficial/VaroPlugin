package de.varoplugin.varo.game;

import de.varoplugin.varo.api.event.game.VaroStateChangeEvent;
import org.bukkit.event.EventHandler;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
// TODO: Delete I guess
public abstract class VaroStateListener extends VaroListener {

    private final VaroState state;

    public VaroStateListener(Varo varo, VaroState state) {
        super(varo);

        this.state = state;
    }

    @EventHandler
    public void onGameStateChange(VaroStateChangeEvent event) {
        if (!this.state.equals(event.getState())) this.unregister();
    }
}