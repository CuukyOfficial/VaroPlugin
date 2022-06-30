package de.varoplugin.varo.task;

import de.varoplugin.varo.game.Varo;
import org.bukkit.event.HandlerList;

public abstract class AbstractListener implements VaroListener {

    @Override
    public void register(Varo varo) {
        varo.getPlugin().getServer().getPluginManager().registerEvents(this, varo.getPlugin());
    }

    @Override
    public void deregister() {
        HandlerList.unregisterAll(this);
    }

    @Override
    public void destroy() {
        this.deregister();
    }
}
