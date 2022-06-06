package de.varoplugin.varo.game.player.tasks;

import de.varoplugin.varo.game.player.VaroPlayer;

/**
 * Represents a heartbeat which schedules async at a given interval if the player is online.
 *
 * @author CuukyOfficial
 * @version v0.1
 */
public abstract class OnlineHeartbeatTask extends VaroPlayerOnlineTask {

    private static final long HEARTBEAT_SCHEDULE = 20L;

    public OnlineHeartbeatTask(VaroPlayer player) {
        super(player);
    }

    @Override
    protected void schedule() {
        this.runTaskTimerAsynchronously(this.varo.getPlugin(), HEARTBEAT_SCHEDULE, HEARTBEAT_SCHEDULE);
    }

    public abstract void run();

}