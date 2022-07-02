package de.varoplugin.varo.task.game.player;

import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.task.AbstractTrigger;
import de.varoplugin.varo.task.VaroTrigger;

public abstract class AbstractPlayerTrigger extends AbstractTrigger {

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
