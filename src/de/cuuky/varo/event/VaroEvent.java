package de.cuuky.varo.event;

import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.VaroElement;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collection;
import java.util.LinkedHashSet;

public abstract class VaroEvent extends VaroElement {

    private final String name;
    private final String displayName;
    private final Material icon;
    private final String description;

    private final Collection<BukkitTask> tasks;

    public VaroEvent(String name, String displayName, Materials icon, String description) {
        this.name = name;
        this.displayName = displayName;
        this.icon = icon.parseMaterial();
        this.description = description;
        this.tasks = new LinkedHashSet<>();
    }

    abstract void onEnable();

    abstract void onDisable();

    protected void registerTask(BukkitTask task) {
        this.tasks.add(task);
    }

    public void disable() {
        this.tasks.forEach(BukkitTask::cancel);
        this.onDisable();
        this.varo.removeElement(this);
    }

    @Override
    public String toString() {
        return this.name;
    }
}