package de.varoplugin.varo.game.task.player;

import de.varoplugin.varo.api.task.Task;
import de.varoplugin.varo.game.entity.player.Player;
import de.varoplugin.varo.game.task.VaroListenerTask;

public abstract class AbstractPlayerListener extends VaroListenerTask {

    private Player player;

    public AbstractPlayerListener(Player player) {
        super(player.getVaro());
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }

    @Override
    public Task clone() {
        AbstractPlayerListener listener = (AbstractPlayerListener) super.clone();
        listener.player = this.player;
        return listener;
    }
}
