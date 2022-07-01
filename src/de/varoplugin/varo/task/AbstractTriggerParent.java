package de.varoplugin.varo.task;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractTriggerParent implements VaroTrigger {

    // TODO: Only activate children if not already activated and vice versa?
    private final Set<VaroTrigger> children;
    private final Set<VaroRegistrable> registrations;
    private final boolean match;
    private boolean activated;

    protected AbstractTriggerParent(boolean match, Set<VaroTrigger> children, Set<VaroRegistrable> registrations) {
        this.match = match;
        this.children = children;
        this.registrations = registrations;
    }

    public AbstractTriggerParent(boolean match) {
        this(match, new HashSet<>(), new HashSet<>());
    }

    private boolean isEnabled() {
        return this.isActivated() && this.shouldTrigger() == this.match;
    }

    protected boolean giveToChildren(VaroRegistrable... registrable) {
        if (this.children.size() == 0) return false;
        this.children.forEach(c -> c.register(registrable));
        return true;
    }

    protected void shouldTrigger(boolean shouldRegister) {
        if (shouldRegister == this.match) this.activateChildren();
        else this.deactivateChildren();
    }

    protected abstract boolean shouldTrigger();

    protected void activateChildren() {
        this.children.forEach(VaroTrigger::activate);
        this.registrations.forEach(VaroRegistrable::register);
    }

    protected void deactivateChildren() {
        this.children.forEach(VaroTrigger::deactivate);
        this.registrations.forEach(VaroRegistrable::deregister);
    }

    protected Set<VaroTrigger> getSubTriggerCloned() {
        return this.children.stream().map(VaroTrigger::deepClone).collect(Collectors.toSet());
    }

    protected Set<VaroRegistrable> getRegistrationsCloned() {
        return this.registrations.stream().map(VaroRegistrable::deepClone).collect(Collectors.toSet());
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

    public boolean isMatch() {
        return this.match;
    }
}
