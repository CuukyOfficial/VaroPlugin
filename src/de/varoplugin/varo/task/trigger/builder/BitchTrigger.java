package de.varoplugin.varo.task.trigger.builder;

import de.varoplugin.varo.task.trigger.AbstractTrigger;
import de.varoplugin.varo.task.trigger.VaroTrigger;

/**
 * Oh, yeah I'm going to do whatever you want from me
 * Parsing all infos to the child nodes like a good tree node, daddy :)
 * Give me all the data >.<
 */
public class BitchTrigger extends AbstractTrigger implements VaroTrigger {

    public BitchTrigger() {
        super(true);
    }

    @Override
    protected boolean isTriggered() {
        return true;
    }
}
