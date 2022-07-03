package de.varoplugin.varo.game.task;

import de.varoplugin.varo.game.GameState;
import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.api.task.AbstractExecutable;
import org.bukkit.scheduler.BukkitRunnable;

public class StartingTask extends AbstractExecutable {

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
        if (this.countdown == 0) this.getVaro().setState(GameState.RUNNING);
    }
}