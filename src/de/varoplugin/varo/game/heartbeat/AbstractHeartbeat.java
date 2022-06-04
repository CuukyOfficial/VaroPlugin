package de.varoplugin.varo.game.heartbeat;

import de.varoplugin.varo.game.Varo;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public abstract class AbstractHeartbeat extends BukkitRunnable implements Heartbeat {

    private static final int SCHEDULE = 20;

    protected Varo varo;

    @Override
    public void initialize(Varo varo) {
        this.varo = varo;
        this.runTaskTimer(varo.getPlugin(), SCHEDULE, SCHEDULE);
    }
}
