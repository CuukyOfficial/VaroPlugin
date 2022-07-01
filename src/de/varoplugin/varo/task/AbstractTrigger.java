package de.varoplugin.varo.task;

import de.varoplugin.varo.game.Varo;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractTrigger extends AbstractTriggerParent implements Listener {

    private final Varo varo;

    protected AbstractTrigger(Varo varo, boolean match, Set<VaroTrigger> children, Set<VaroRegistrable> registrations) {
        super(match, children, registrations);
        this.varo = varo;
    }

    public AbstractTrigger(Varo varo, boolean match) {
        this(varo, match, new HashSet<>(), new HashSet<>());
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

    public Varo getVaro() {
        return this.varo;
    }
}
