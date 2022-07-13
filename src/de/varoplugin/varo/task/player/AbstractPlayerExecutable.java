package de.varoplugin.varo.task.player;

import de.varoplugin.varo.api.task.Task;
import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.task.VaroScheduledTask;

public abstract class AbstractPlayerExecutable extends VaroScheduledTask {

    private VaroPlayer player;

    public AbstractPlayerExecutable(VaroPlayer player) {
        super(player.getVaro());
        this.player = player;
    }

    public VaroPlayer getPlayer() {
        return this.player;
    }

    @Override
    public Task clone() {
        AbstractPlayerExecutable task = (AbstractPlayerExecutable) super.clone();
        task.player = this.player;
        return task;
    }
}
