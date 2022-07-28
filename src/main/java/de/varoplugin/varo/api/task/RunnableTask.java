package de.varoplugin.varo.api.task;

import org.bukkit.plugin.Plugin;

public abstract class RunnableTask implements Task {

    private Plugin plugin;
    private boolean registered;

    public RunnableTask(Plugin plugin) {
        this.plugin = plugin;
    }

    protected abstract void onEnable();

    protected abstract void onDisable();

    @Override
    public final void enable() {
        this.registered = true;
        this.onEnable();
    }

    @Override
    public final void disable() {
        this.registered = false;
        this.onDisable();
    }

    @Override
    public boolean isEnabled() {
        return this.registered;
    }

    @Override
    public Plugin getPlugin() {
        return this.plugin;
    }

    @Override
    public Task clone() {
        try {
            RunnableTask task = (RunnableTask) super.clone();
            task.plugin = this.plugin;
            return task;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
