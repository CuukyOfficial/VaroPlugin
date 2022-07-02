package de.varoplugin.varo.task;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractTriggerParent implements VaroTrigger {

    // TODO: Only activate children if not already activated and vice versa?
    private Set<VaroTrigger> children;
    private Set<VaroRegistrable> registrations;
    private boolean match;
    private boolean activated;

    /**
     * Cloning
     */
    protected AbstractTriggerParent() {
    }

    protected AbstractTriggerParent(boolean match, Set<VaroTrigger> children, Set<VaroRegistrable> registrations) {
        this.match = match;
        this.children = children;
        this.registrations = registrations;
    }

    public AbstractTriggerParent(boolean match) {
        this(match, new HashSet<>(), new HashSet<>());
    }

    private Set<VaroTrigger> getChildrenCloned() {
        return this.children.stream().map(VaroTrigger::clone).collect(Collectors.toSet());
    }

    private Set<VaroRegistrable> getRegistrationsCloned() {
        return this.registrations.stream().map(VaroRegistrable::clone).collect(Collectors.toSet());
    }

    private boolean isEnabled() {
        return this.isActivated() && this.isTriggered() == this.match;
    }

    protected boolean giveToChildren(VaroRegistrable... registrable) {
        if (this.children.size() == 0) return false;
        this.children.forEach(c -> c.register(registrable));
        return true;
    }

    protected void triggerIf(boolean trigger) {
        if (trigger == this.match) this.activateChildren();
        else this.deactivateChildren();
    }

    protected abstract boolean isTriggered();

    protected void activateChildren() {
        this.children.forEach(VaroTrigger::activate);
        this.registrations.forEach(VaroRegistrable::register);
    }

    protected void deactivateChildren() {
        this.children.forEach(VaroTrigger::deactivate);
        this.registrations.forEach(VaroRegistrable::deregister);
    }

    /**
     * Clone activated state?
     * @return Cloned
     */
    @Override
    public VaroTrigger clone() {
        try {
            AbstractTriggerParent trigger = (AbstractTriggerParent) super.clone();
            trigger.children = this.getChildrenCloned();
            trigger.registrations = this.getRegistrationsCloned();
            trigger.match = this.match;
            return trigger;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void activate() {
        this.activated = true;
        if (this.isEnabled()) this.activateChildren();
    }

    @Override
    public void deactivate() {
        this.activated = false;
        this.deactivateChildren();
    }

    @Override
    public boolean isActivated() {
        return this.activated;
    }

    @Override
    public void addChildren(VaroTrigger... children) {
        this.children.addAll(Arrays.asList(children));
        if (this.isEnabled()) Arrays.stream(children).forEach(VaroTrigger::activate);
    }

    @Override
    public void register(VaroRegistrable... register) {
        if (this.giveToChildren(register)) return;
        this.registrations.addAll(Arrays.asList(register));
        if (this.isEnabled()) Arrays.stream(register).forEach(VaroRegistrable::register);
    }
}
