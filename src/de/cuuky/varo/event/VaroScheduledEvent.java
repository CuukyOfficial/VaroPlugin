package de.cuuky.varo.event;

import de.cuuky.cfw.configuration.serialization.Serialize;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class VaroScheduledEvent extends VaroEvent {

    @Serialize("schedule")
    private final int schedule;

    public VaroScheduledEvent(EventInformationHolder informationHolder, int schedule) {
        super(informationHolder);

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