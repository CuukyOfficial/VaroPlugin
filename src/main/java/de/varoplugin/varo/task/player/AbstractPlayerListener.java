package de.varoplugin.varo.task.player;

import de.varoplugin.varo.api.task.Task;
import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.task.VaroListenerTask;

public abstract class AbstractPlayerListener extends VaroListenerTask {

    private VaroPlayer player;

    public AbstractPlayerListener(VaroPlayer player) {
        super(player.getVaro());
        this.player = player;
    }

    public VaroPlayer getPlayer() {
        return this.player;
    }

    @Override
    public Task clone() {
        AbstractPlayerListener listener = (AbstractPlayerListener) super.clone();
        listener.player = this.player;
        return listener;
    }
}
