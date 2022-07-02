package de.varoplugin.varo.task.trigger.game.player;

import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.task.trigger.AbstractListenedTrigger;
import de.varoplugin.varo.task.trigger.VaroTrigger;

public abstract class AbstractPlayerTrigger extends AbstractListenedTrigger {

    private VaroPlayer player;

    public AbstractPlayerTrigger(VaroPlayer player, boolean match) {
        super(player.getVaro(), match);
        this.player = player;
    }

    public VaroPlayer getPlayer() {
        return this.player;
    }

    @Override
    public VaroTrigger clone() {
        AbstractPlayerTrigger trigger = (AbstractPlayerTrigger) super.clone();
        trigger.player = this.player;
        return trigger;
    }
}
