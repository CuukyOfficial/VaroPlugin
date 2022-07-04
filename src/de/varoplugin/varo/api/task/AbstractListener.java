package de.varoplugin.varo.api.task;

import de.varoplugin.varo.game.Varo;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public abstract class AbstractListener extends AbstractTask implements VaroTask, Listener {

    public AbstractListener(Varo varo) {
        super(varo);
    }

    @Override
    public void onEnable() {
        this.getVaro().getPlugin().getServer().getPluginManager().registerEvents(this, this.getVaro().getPlugin());
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }
}
