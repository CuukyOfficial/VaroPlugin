package de.varoplugin.varo.task;

import de.varoplugin.varo.game.VaroState;
import de.varoplugin.varo.game.Varo;
import org.bukkit.scheduler.BukkitRunnable;

public class StartingTask extends VaroScheduledTask {

    private int countdown;

    public StartingTask(Varo varo) {
        super(varo);
    }

    @Override
    protected void schedule(BukkitRunnable runnable) {
        runnable.runTaskTimer(this.getVaro().getPlugin(), 20, 20);
        this.countdown = this.getVaro().getPlugin().getVaroConfig().start_countdown.getValue();
    }

    @Override
    public void run() {
        this.countdown--;
        if (this.countdown == 0) this.getVaro().setState(VaroState.RUNNING);
    }
}