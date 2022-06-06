package de.varoplugin.varo.game.entity.player.tasks;

import de.varoplugin.varo.game.entity.player.VaroPlayer;

/**
 * Represents a heartbeat which schedules async at a given interval if the player is online.
 *
 * @author CuukyOfficial
 * @version v0.1
 */
public abstract class OnlineHeartbeatTask extends VaroPlayerOnlineTask {

    private static final long HEARTBEAT_SCHEDULE = 20L;

    private final long init;
    private final long schedule;

    public OnlineHeartbeatTask(VaroPlayer player, long init, long schedule) {
        super(player);

        this.init = init;
        this.schedule = schedule;
    }

    public OnlineHeartbeatTask(VaroPlayer player) {
        this(player, HEARTBEAT_SCHEDULE, HEARTBEAT_SCHEDULE);
    }

        @Override
    protected void schedule() {
        this.runTaskTimerAsynchronously(this.varo.getPlugin(), this.init, this.schedule);
    }

    public abstract void run();

}