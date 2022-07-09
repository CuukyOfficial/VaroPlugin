package de.varoplugin.varo.game.task;

import de.varoplugin.varo.api.task.AbstractExecutable;
import de.varoplugin.varo.game.DefaultState;
import de.varoplugin.varo.game.Varo;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class AutoStartTask extends AbstractExecutable {

    public AutoStartTask(Varo varo) {
        super(varo);
    }

    @Override
    protected void schedule(BukkitRunnable runnable) {
        Calendar now = new GregorianCalendar();
        long millisDif = this.getVaro().getAutoStart().getTimeInMillis() - now.getTimeInMillis();
        if (millisDif <= 0) this.run();
        runnable.runTaskLater(this.getVaro().getPlugin(), millisDif / 50);
    }

    @Override
    public void run() {
        this.getVaro().setAutoStart(null);
        this.getVaro().setState(DefaultState.STARTING);
    }
}
