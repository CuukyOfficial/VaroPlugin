package de.cuuky.varo.event;

import de.cuuky.cfw.configuration.serialization.Serialize;
import de.cuuky.cfw.version.types.Materials;
import org.bukkit.scheduler.BukkitRunnable;

abstract class VaroScheduledEvent extends VaroEvent {

    @Serialize("schedule")
    private final int schedule;

    VaroScheduledEvent(String name, String displayName, Materials icon, String description, int schedule) {
        super(name, displayName, icon, description);

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