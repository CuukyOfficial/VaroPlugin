package de.varoplugin.varo.api.task.trigger;

import de.varoplugin.varo.api.event.TriggerDestroyEvent;
import de.varoplugin.varo.api.task.VaroTask;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractTrigger implements VaroTrigger {

    private Plugin plugin;
    private Set<VaroTrigger> children;
    private Set<VaroTask> tasks;
    private boolean match;
    private boolean activated;
    private boolean enabled;

    public AbstractTrigger(Plugin plugin, boolean match) {
        this.plugin = plugin;
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
        return this.isActivated() && (this.isTriggered() == this.match);
    }

    protected boolean giveToChildren(VaroTask... registrable) {
        if (this.children.size() == 0) return false;
        this.children.iterator().next().register(registrable);
        this.children.stream().skip(1).forEach(c -> Arrays.stream(registrable).map(VaroTask::clone).forEach(c::register));
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
        this.tasks.stream().filter(r -> !r.isEnabled()).forEach(VaroTask::enable);
    }

    protected void deactivateChildren() {
        this.children.stream().filter(VaroTrigger::isActivated).forEach(VaroTrigger::deactivate);
        this.tasks.stream().filter(VaroTask::isEnabled).forEach(VaroTask::disable);
    }

    @EventHandler
    public void onPluginDisable(PluginDisableEvent event) {
        if (!event.getPlugin().equals(this.plugin)) return;
        if (!this.wasDestroyed()) this.destroy();
    }

    @EventHandler
    public void onTriggerDestroy(TriggerDestroyEvent event) {
        if (this.wasDestroyed()) return;
        if (!this.children.remove(event.getTrigger())) return;
        if (this.children.isEmpty() && this.tasks.isEmpty()) this.destroy();
    }

    @Override
    public void activate() {
        this.activated = true;
        this.enabled = this.isEnabled();
        if (this.enabled) this.activateChildren();
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @Override
    public void deactivate() {
        this.activated = false;
        this.enabled = false;
        this.deactivateChildren();
        HandlerList.unregisterAll(this);
    }

    @Override
    public void destroy() {
        if (this.activated) this.deactivate();
        this.children.forEach(VaroTrigger::destroy);
        this.tasks.stream().filter(VaroTask::isEnabled).forEach(VaroTask::disable);
        this.children = null;
        this.tasks = null;
        this.plugin.getServer().getPluginManager().callEvent(new TriggerDestroyEvent(this));
    }

    @Override
    public boolean wasDestroyed() {
        return this.children == null && this.tasks == null;
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
        if (this.enabled) Arrays.stream(tasks).filter(c -> !c.isEnabled()).forEach(VaroTask::enable);
    }

    /**
     * Clone activated state?
     *
     * @return Cloned
     */
    @Override
    public VaroTrigger clone() {
        try {
            AbstractTrigger trigger = (AbstractTrigger) super.clone();
            trigger.plugin = this.plugin;
            trigger.children = this.getChildrenCloned();
            trigger.tasks = this.getTasksCloned();
            trigger.match = this.match;
            return trigger;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}