package de.varoplugin.varo.game.heartbeat;

import de.varoplugin.varo.game.Varo;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractHeartbeat extends BukkitRunnable implements Heartbeat {

    private static final int SCHEDULE = 20;

    protected Varo varo;
    private final List<Listener> listener;

    AbstractHeartbeat() {
        this.listener = new LinkedList<>();
    }

    private void unregister(Listener listener) {
        HandlerList.unregisterAll(listener);
    }

    protected void registerListener(Listener listener) {
        this.varo.getPlugin().getServer().getPluginManager().registerEvents(listener, this.varo.getPlugin());
        this.listener.add(listener);
    }

    @Override
    public void initialize(Varo varo) {
        this.varo = varo;
        this.registerListener(this);
        this.runTaskTimer(varo.getPlugin(), SCHEDULE, SCHEDULE);
    }

    @Override
    public synchronized void cancel() {
        this.listener.forEach(this::unregister);
        super.cancel();
    }
}
