package de.cuuky.varo.event;

import de.cuuky.varo.Varo;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collection;
import java.util.LinkedHashSet;

abstract class VaroEvent implements IVaroEvent {

    protected final Varo varo;
    private final VaroEventType type;
    private final Collection<BukkitTask> tasks;

    VaroEvent(Varo varo, VaroEventType type) {
        this.varo = varo;
        this.type = type;
        this.tasks = new LinkedHashSet<>();

        this.onEnable();
    }

    abstract void onEnable();

    abstract void onDisable();

    protected void registerTask(BukkitTask task) {
        this.tasks.add(task);
    }

    public void disable() {
        this.tasks.forEach(BukkitTask::cancel);
        this.onDisable();
        this.varo.removeEvent(this);
    }

    @Override
    public VaroEventType getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return this.type.getName();
    }
}