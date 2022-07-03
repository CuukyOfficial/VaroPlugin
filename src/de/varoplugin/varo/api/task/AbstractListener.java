package de.varoplugin.varo.api.task;

import de.varoplugin.varo.game.Varo;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public abstract class AbstractListener implements VaroTask, Listener {

    private Varo varo;
    private boolean registered;

    public AbstractListener(Varo varo) {
        this.varo = varo;
    }

    @Override
    public void register() {
        this.registered = true;
        this.varo.getPlugin().getServer().getPluginManager().registerEvents(this, this.varo.getPlugin());
    }

    @Override
    public void deregister() {
        this.registered = false;
        HandlerList.unregisterAll(this);
    }

    @Override
    public boolean isRegistered() {
        return this.registered;
    }

    public Varo getVaro() {
        return this.varo;
    }

    @Override
    public VaroTask clone() {
        try {
            AbstractListener listener = (AbstractListener) super.clone();
            listener.varo = this.varo;
            return listener;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
