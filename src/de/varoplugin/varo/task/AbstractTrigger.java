package de.varoplugin.varo.task;

import de.varoplugin.varo.game.Varo;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public abstract class AbstractTrigger extends AbstractTriggerParent implements Listener {

    private Varo varo;

    public AbstractTrigger(Varo varo, boolean match) {
        super(match);
        this.varo = varo;
    }

    public AbstractTrigger(Varo varo) {
        this(varo, true);
    }

    @Override
    public void activate() {
        super.activate();
        this.varo.getPlugin().getServer().getPluginManager().registerEvents(this, this.varo.getPlugin());
    }

    @Override
    public void deactivate() {
        super.deactivate();
        HandlerList.unregisterAll(this);
    }

    @Override
    public VaroTrigger clone() {
        AbstractTrigger trigger = (AbstractTrigger) super.clone();
        trigger.varo = this.varo;
        return trigger;
    }

    public Varo getVaro() {
        return this.varo;
    }
}
