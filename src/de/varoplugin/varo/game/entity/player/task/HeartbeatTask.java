package de.varoplugin.varo.game.entity.player.task;

import de.varoplugin.varo.game.entity.player.VaroPlayer;

/**
 * Represents a heartbeat which schedules async at a given interval.
 *
 * @author CuukyOfficial
 * @version v0.1
 */
public abstract class HeartbeatTask extends VaroPlayerTask {

    private static final long HEARTBEAT_SCHEDULE = 20L;

    private final long init;
    private final long schedule;

    public HeartbeatTask(VaroPlayer player, long init, long schedule) {
        super(player);

        this.init = init;
        this.schedule = schedule;
    }

    public HeartbeatTask(VaroPlayer player) {
        this(player, HEARTBEAT_SCHEDULE, HEARTBEAT_SCHEDULE);
    }

    @Override
    protected void schedule() {
        this.createRunnable().runTaskTimerAsynchronously(this.varo.getPlugin(), this.init, this.schedule);
    }

    public abstract void run();

}