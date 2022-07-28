package de.varoplugin.varo.api.task;

import org.bukkit.plugin.Plugin;

public interface Task extends Cloneable {

    void enable();

    void disable();

    boolean isEnabled();

    Plugin getPlugin();

    Task clone();

}
