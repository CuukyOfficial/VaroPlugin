package de.varoplugin.varo.ui.listener;

import de.cuuky.cfw.version.VersionUtils;
import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.game.task.player.AbstractPlayerExecutable;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerShowCountdownTask extends AbstractPlayerExecutable {

    public PlayerShowCountdownTask(VaroPlayer player) {
        super(player);
    }

    @Override
    protected void schedule(BukkitRunnable runnable) {
        runnable.runTaskTimerAsynchronously(this.getVaro().getPlugin(), 20L, 20L);
    }

    @Override
    public void run() {
        VersionUtils.getVersionAdapter().sendActionbar(this.getPlayer().getPlayer(), String.valueOf(this.getPlayer().getCountdown()));
    }
}
