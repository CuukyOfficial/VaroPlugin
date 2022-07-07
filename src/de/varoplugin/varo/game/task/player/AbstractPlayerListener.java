package de.varoplugin.varo.game.task.player;

import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.api.task.AbstractListener;
import de.varoplugin.varo.api.task.VaroTask;

public abstract class AbstractPlayerListener extends AbstractListener {

    private VaroPlayer player;

    public AbstractPlayerListener(VaroPlayer player) {
        super(player.getVaro());
        this.player = player;
    }

    public VaroPlayer getPlayer() {
        return this.player;
    }

    @Override
    public VaroTask clone() {
        AbstractPlayerListener listener = (AbstractPlayerListener) super.clone();
        listener.player = this.player;
        return listener;
    }
}
