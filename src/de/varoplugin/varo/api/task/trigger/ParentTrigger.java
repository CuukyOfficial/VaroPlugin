package de.varoplugin.varo.api.task.trigger;

import de.varoplugin.varo.api.task.trigger.event.TriggerDestroyEvent;
import de.varoplugin.varo.api.task.Task;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class ParentTrigger implements Trigger {

    private Plugin plugin;
    private Set<Trigger> children;
    private Set<Task> tasks;
    private boolean match;
    private boolean activated;
    private boolean enabled;

    public ParentTrigger(Plugin plugin, boolean match) {
        this.plugin = plugin;
        this.match = match;
        this.children = new HashSet<>();
        this.tasks = new HashSet<>();
    }

    private Set<Trigger> getChildrenCloned() {
        return this.children.stream().map(Trigger::clone).collect(Collectors.toSet());
    }

    private Set<Task> getTasksCloned() {
        return this.tasks.stream().map(Task::clone).collect(Collectors.toSet());
    }

    private boolean isEnabled() {
        return this.isActivated() && (this.isTriggered() == this.match);
    }

    @SafeVarargs
    protected final <T> boolean giveToChildren(BiConsumer<Trigger, T> adder, Function<T, T> clone, T... registrable) {
        if (this.children.size() == 0) return false;
        Arrays.stream(registrable).forEach(reg -> adder.accept(this.children.iterator().next(), reg));
        this.children.stream().skip(1).forEach(c -> Arrays.stream(registrable).map(clone).forEach(r -> adder.accept(c, r)));
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
        this.children.stream().filter(c -> !c.isActivated()).forEach(Trigger::activate);
        this.tasks.stream().filter(r -> !r.isEnabled()).forEach(Task::enable);
    }

    protected void deactivateChildren() {
        this.children.stream().filter(Trigger::isActivated).forEach(Trigger::deactivate);
        this.tasks.stream().filter(Task::isEnabled).forEach(Task::disable);
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
        this.children.forEach(Trigger::destroy);
        this.tasks.stream().filter(Task::isEnabled).forEach(Task::disable);
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
    public void addChildren(Trigger... children) {
        if (this.giveToChildren(Trigger::addChildren, Trigger::clone, children)) return;
        this.children.addAll(Arrays.asList(children));
        if (this.enabled) Arrays.stream(children).filter(c -> !c.isActivated()).forEach(Trigger::activate);
    }

    @Override
    public void register(Task... tasks) {
        if (this.giveToChildren(Trigger::register, Task::clone, tasks)) return;
        this.tasks.addAll(Arrays.asList(tasks));
        if (this.enabled) Arrays.stream(tasks).filter(c -> !c.isEnabled()).forEach(Task::enable);
    }

    /**
     * Clone activated state?
     *
     * @return Cloned
     */
    @Override
    public Trigger clone() {
        try {
            ParentTrigger trigger = (ParentTrigger) super.clone();
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
