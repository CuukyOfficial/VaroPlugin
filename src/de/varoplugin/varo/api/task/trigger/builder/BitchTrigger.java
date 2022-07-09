package de.varoplugin.varo.api.task.trigger.builder;

import de.varoplugin.varo.api.task.trigger.ParentTrigger;
import de.varoplugin.varo.api.task.trigger.Trigger;
import org.bukkit.plugin.Plugin;

/**
 * Oh, yeah I'm going to do whatever you want from me
 * Parsing all infos to the child nodes like a good tree node, daddy :)
 * Give me all the data >.<
 */
public class BitchTrigger extends ParentTrigger implements Trigger {

    public BitchTrigger(Plugin plugin) {
        super(plugin, true);
    }

    @Override
    protected boolean isTriggered() {
        return true;
    }
}
