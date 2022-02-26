package de.cuuky.varo.event;

import de.cuuky.varo.Varo;
import org.bukkit.scheduler.BukkitRunnable;

abstract class VaroScheduledEvent extends VaroEvent {

    private final int schedule;

    VaroScheduledEvent(Varo varo, VaroEventType type, int schedule) {
        super(varo, type);

        this.schedule = schedule;
    }

    abstract void onSchedule();

    @Override
    void onEnable() {
        this.registerTask(new BukkitRunnable() {
            @Override
            public void run() {
                onSchedule();
            }
        }.runTaskTimer(this.varo.getPlugin(), 0, this.schedule));
    }
}