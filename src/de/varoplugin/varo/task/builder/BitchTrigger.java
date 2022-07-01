package de.varoplugin.varo.task.builder;

import de.varoplugin.varo.task.AbstractTriggerParent;
import de.varoplugin.varo.task.VaroTrigger;

/**
 * Oh, yeah I'm going to do whatever you want from me
 * Parsing all infos to the child nodes like a good tree node, daddy :)
 * Give me all the data >.<
 */
public class BitchTrigger extends AbstractTriggerParent implements VaroTrigger {

    public BitchTrigger() {
        super(true);
    }

    @Override
    protected boolean shouldTrigger() {
        return true;
    }

    @Override
    public VaroTrigger deepClone() {
        return new BitchTrigger();
    }
}
