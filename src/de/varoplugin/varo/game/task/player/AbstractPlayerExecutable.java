package de.varoplugin.varo.game.task.player;

import de.varoplugin.varo.api.task.AbstractExecutable;
import de.varoplugin.varo.api.task.VaroTask;
import de.varoplugin.varo.game.entity.player.VaroPlayer;

public abstract class AbstractPlayerExecutable extends AbstractExecutable {

    private VaroPlayer player;

    public AbstractPlayerExecutable(VaroPlayer player) {
        super(player.getVaro());
        this.player = player;
    }

    public VaroPlayer getPlayer() {
        return this.player;
    }

    @Override
    public VaroTask clone() {
        AbstractPlayerExecutable task = (AbstractPlayerExecutable) super.clone();
        task.player = this.player;
        return task;
    }
}
