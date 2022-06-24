package de.varoplugin.varo.jobs.game.player;

import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.jobs.AbstractVaroTask;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Represents a heartbeat which schedules async at a given interval.
 */
public abstract class HeartbeatTask extends AbstractVaroTask {

    private static final long HEARTBEAT_SCHEDULE = 20L;

    private final VaroPlayer player;
    private final long init;
    private final long schedule;

    public HeartbeatTask(VaroPlayer player, long init, long schedule) {
        this.player = player;

        this.init = init;
        this.schedule = schedule;
    }

    public HeartbeatTask(VaroPlayer player) {
        this(player, HEARTBEAT_SCHEDULE, HEARTBEAT_SCHEDULE);
    }

    @Override
    protected void schedule(BukkitRunnable runnable) {
        runnable.runTaskTimerAsynchronously(this.getVaro().getPlugin(), this.init, this.schedule);
    }

    public VaroPlayer getPlayer() {
        return this.player;
    }
}