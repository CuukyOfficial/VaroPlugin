package de.varoplugin.varo.task.trigger;

import de.varoplugin.varo.game.Varo;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public abstract class AbstractListenedTrigger extends AbstractTrigger implements Listener {

    private Varo varo;

    public AbstractListenedTrigger(Varo varo, boolean match) {
        super(match);
        this.varo = varo;
    }

    public AbstractListenedTrigger(Varo varo) {
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
        AbstractListenedTrigger trigger = (AbstractListenedTrigger) super.clone();
        trigger.varo = this.varo;
        return trigger;
    }

    public Varo getVaro() {
        return this.varo;
    }
}
