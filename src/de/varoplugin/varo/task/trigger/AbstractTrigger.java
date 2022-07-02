package de.varoplugin.varo.task.trigger;

import de.varoplugin.varo.task.VaroTask;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractTrigger implements VaroTrigger {

    private Set<VaroTrigger> children;
    private Set<VaroTask> tasks;
    private boolean match;
    private boolean activated;
    private boolean enabled;

    public AbstractTrigger(boolean match) {
        this.match = match;
        this.children = new HashSet<>();
        this.tasks = new HashSet<>();
    }

    private Set<VaroTrigger> getChildrenCloned() {
        return this.children.stream().map(VaroTrigger::clone).collect(Collectors.toSet());
    }

    private Set<VaroTask> getTasksCloned() {
        return this.tasks.stream().map(VaroTask::clone).collect(Collectors.toSet());
    }

    private boolean isEnabled() {
        return this.isActivated() && this.isTriggered() == this.match;
    }

    protected boolean giveToChildren(VaroTask... registrable) {
        if (this.children.size() == 0) return false;
        this.children.forEach(c -> c.register(registrable));
        return true;
    }

    protected void triggerIf(boolean trigger) {
        if (trigger == this.match) {
            if (!this.enabled) this.activateChildren();
            this.enabled = true;
        } else {
            if (this.enabled) this.deactivateChildren();
            this.enabled = false;
        }
    }

    protected abstract boolean isTriggered();

    protected void activateChildren() {
        this.children.stream().filter(c -> !c.isActivated()).forEach(VaroTrigger::activate);
        this.tasks.stream().filter(r -> !r.isRegistered()).forEach(VaroTask::register);
    }

    protected void deactivateChildren() {
        this.children.stream().filter(VaroTrigger::isActivated).forEach(VaroTrigger::deactivate);
        this.tasks.stream().filter(VaroTask::isRegistered).forEach(VaroTask::deregister);
    }

    /**
     * Clone activated state?
     * @return Cloned
     */
    @Override
    public VaroTrigger clone() {
        try {
            AbstractTrigger trigger = (AbstractTrigger) super.clone();
            trigger.children = this.getChildrenCloned();
            trigger.tasks = this.getTasksCloned();
            trigger.match = this.match;
            return trigger;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void activate() {
        this.activated = true;
        this.enabled = this.isEnabled();
        if (this.enabled) this.activateChildren();
    }

    @Override
    public void deactivate() {
        this.activated = false;
        this.enabled = false;
        this.deactivateChildren();
    }

    @Override
    public boolean isActivated() {
        return this.activated;
    }

    @Override
    public void addChildren(VaroTrigger... children) {
        this.children.addAll(Arrays.asList(children));
        if (this.enabled) Arrays.stream(children).filter(c -> !c.isActivated()).forEach(VaroTrigger::activate);
    }

    @Override
    public void register(VaroTask... tasks) {
        if (this.giveToChildren(tasks)) return;
        this.tasks.addAll(Arrays.asList(tasks));
        if (this.enabled) Arrays.stream(tasks).filter(c -> !c.isRegistered()).forEach(VaroTask::register);
    }
}
