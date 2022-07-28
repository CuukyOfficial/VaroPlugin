package de.varoplugin.varo.task.player;

import de.varoplugin.varo.game.entity.player.VaroPlayer;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class AbstractHeartbeatTask extends AbstractPlayerExecutable {

    private static final long HEARTBEAT = 20L;

    private final boolean sync;

    public AbstractHeartbeatTask(VaroPlayer player, boolean sync) {
        super(player);
        this.sync = sync;
    }

    protected void runSynchronized(Runnable runnable) {
        new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }.runTask(this.getPlugin());
    }

    @Override
    protected void schedule(BukkitRunnable runnable) {
        if (this.sync) runnable.runTaskTimer(this.getPlugin(), HEARTBEAT, HEARTBEAT);
        else runnable.runTaskTimerAsynchronously(this.getPlugin(), HEARTBEAT, HEARTBEAT);
    }
}
