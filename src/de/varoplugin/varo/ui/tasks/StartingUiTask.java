package de.varoplugin.varo.ui.tasks;

import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.task.AbstractExecutable;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class StartingUiTask extends AbstractExecutable {

    private int countdown;

    public StartingUiTask(Varo varo) {
        super(varo);
    }

    @Override
    protected void schedule(BukkitRunnable runnable) {
        runnable.runTaskTimer(this.getVaro().getPlugin(), 20, 20);
        this.countdown = this.getVaro().getPlugin().getVaroConfig().start_countdown.getValue();
    }

    @Override
    public void run() {
        Bukkit.getServer().broadcastMessage(String.valueOf(this.countdown--));
    }
}
