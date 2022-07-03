package de.varoplugin.varo.api.task.trigger.builder;

import de.varoplugin.varo.api.task.trigger.VaroTrigger;
import de.varoplugin.varo.game.task.trigger.builder.VaroTriggerBuilder;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractTriggerBuilder implements TriggerBuilder {

    private final Set<VaroTrigger> when;
    private final Set<VaroTrigger> children;
    private final Plugin plugin;

    public AbstractTriggerBuilder(Plugin plugin) {
        this.plugin = plugin;
        this.when = new HashSet<>();
        this.children = new HashSet<>();
    }

    private VaroTrigger buildHead() {
        if (this.when.size() == 0) return null;
        else if (this.when.size() == 1) return this.when.iterator().next();

        VaroTrigger bitch = new BitchTrigger(this.plugin);
        this.when.forEach(bitch::addChildren);
        return bitch;
    }

    @Override
    public TriggerBuilder when(VaroTrigger trigger) {
        this.when.add(trigger);
        return this;
    }

    @Override
    public TriggerBuilder when(VaroTriggerBuilder when) {
        this.when(when.build());
        return this;
    }

    @Override
    public TriggerBuilder and(VaroTrigger... trigger) {
        this.children.addAll(Arrays.asList(trigger));
        return this;
    }

    @Override
    public TriggerBuilder and(VaroTriggerBuilder and) {
        this.children.add(and.build());
        return this;
    }

    @Override
    public VaroTrigger build() {
        VaroTrigger head = this.buildHead();
        if (head == null) return null;
        head.addChildren(this.children.toArray(new VaroTrigger[0]));
        return head;
    }

    @Override
    public VaroTrigger complete() {
        VaroTrigger built = this.build();
        built.activate();
        return built;
    }
}
