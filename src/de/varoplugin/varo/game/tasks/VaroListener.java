package de.varoplugin.varo.game.tasks;

import de.varoplugin.varo.game.Varo;
import org.bukkit.event.HandlerList;

/**
 * Represents any Varo task.
 * Registers itself as a bukkit listener and calls "schedule" on registration.
 *
 * @author CuukyOfficial
 * @version v0.1
 */
public abstract class VaroListener implements TaskRegistrable {

    private boolean registered;
    protected final Varo varo;

    public VaroListener(Varo varo) {
        this.varo = varo;
    }

    protected void doRegister() {
        this.varo.getPlugin().getServer().getPluginManager().registerEvents(this, this.varo.getPlugin());
    }

    protected void doUnregister() {
        HandlerList.unregisterAll(this);
    }

    @Override
    public boolean register() {
        if (this.registered) return false;
        this.doRegister();
        return (this.registered = true);
    }

    @Override
    public boolean unregister() {
        if (!this.registered) return false;
        this.doUnregister();
        return (this.registered = false);
    }
}