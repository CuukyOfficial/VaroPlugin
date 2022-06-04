package de.varoplugin.varo.game.player.state;

import de.varoplugin.varo.game.VaroState;

import java.util.Objects;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public abstract class AbstractPlayerInfo implements PlayerInfo {

    protected final VaroState state;

    public AbstractPlayerInfo(VaroState state) {
        this.state = state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.state);
    }

    @Override
    public VaroState getState() {
        return this.state;
    }
}