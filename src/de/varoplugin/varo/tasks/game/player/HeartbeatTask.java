package de.varoplugin.varo.tasks.game.player;

/**
 * Represents a heartbeat which schedules async at a given interval.
 *
 * @author CuukyOfficial
 * @version v0.1
 */
public abstract class HeartbeatTask extends AbstractPlayerTask {

    private static final long HEARTBEAT_SCHEDULE = 20L;

    private final long init;
    private final long schedule;

    public HeartbeatTask(long init, long schedule) {
        this.init = init;
        this.schedule = schedule;
    }

    public HeartbeatTask() {
        this(HEARTBEAT_SCHEDULE, HEARTBEAT_SCHEDULE);
    }

    @Override
    protected void schedule() {
        this.createRunnable().runTaskTimerAsynchronously(this.getInfo().getVaro().getPlugin(), this.init, this.schedule);
    }

    public abstract void run();

}