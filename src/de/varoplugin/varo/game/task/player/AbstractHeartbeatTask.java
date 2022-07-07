package de.varoplugin.varo.game.task.player;

import de.varoplugin.varo.game.entity.player.VaroPlayer;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class AbstractHeartbeatTask extends AbstractPlayerExecutable {

    public AbstractHeartbeatTask(VaroPlayer player) {
        super(player);
    }

    @Override
    protected void schedule(BukkitRunnable runnable) {
        runnable.runTaskTimer(this.getVaro().getPlugin(), 20L, 20L);
    }
}
