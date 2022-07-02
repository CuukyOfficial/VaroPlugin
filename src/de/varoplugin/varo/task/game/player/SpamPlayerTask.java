package de.varoplugin.varo.task.game.player;

import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.task.AbstractTask;
import de.varoplugin.varo.task.VaroRegistrable;
import org.bukkit.scheduler.BukkitRunnable;

public class SpamPlayerTask extends AbstractTask {

    private VaroPlayer player;

    public SpamPlayerTask(VaroPlayer player) {
        super(player.getVaro());
        this.player = player;
    }

    @Override
    protected void schedule(BukkitRunnable runnable) {
        runnable.runTaskTimer(this.getVaro().getPlugin(), 20, 20);
    }

    @Override
    public void run() {
        player.getPlayer().sendMessage("Fick dich");
    }

    @Override
    public VaroRegistrable clone() {
        SpamPlayerTask task = (SpamPlayerTask) super.clone();
        task.player = this.player;
        return task;
    }
}
