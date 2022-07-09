package de.varoplugin.varo.api.task;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public abstract class ListenerTask extends RunnableTask implements Task, Listener {

    public ListenerTask(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void onEnable() {
        this.getPlugin().getServer().getPluginManager().registerEvents(this, this.getPlugin());
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }
}
