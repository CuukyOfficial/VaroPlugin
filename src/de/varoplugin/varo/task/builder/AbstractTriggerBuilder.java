package de.varoplugin.varo.task.builder;

import de.varoplugin.varo.task.VaroTrigger;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AbstractTriggerBuilder<B> implements TriggerBuilder<B> {

    private final List<Function<B, VaroTrigger>> layerTrigger;
    private final List<VaroTrigger> children;

    public AbstractTriggerBuilder() {
        this.layerTrigger = new LinkedList<>();
        this.children = new LinkedList<>();
    }

    protected void orTrigger(Function<B, VaroTrigger> triggerFunction) {
        this.layerTrigger.add(triggerFunction);
    }

    @Override
    public TriggerBuilder<B> or(VaroTrigger trigger) {
        this.layerTrigger.add(b -> trigger.deepClone());
        return this;
    }

    @Override
    public TriggerBuilder<B> and(VaroTrigger... trigger) {
        this.children.addAll(Arrays.asList(trigger));
        return this;
    }

    @Override
    public VaroTrigger build(B build) {
        List<VaroTrigger> layer = this.layerTrigger.stream().map(trigger -> trigger.apply(build)).collect(Collectors.toList());
        layer.forEach(t -> this.children.forEach(c -> t.addChildren(c.deepClone())));

        if (layer.size() == 0) return null;
        else if (layer.size() == 1) return layer.get(0);

        VaroTrigger bitch = new BitchTrigger();
        layer.forEach(bitch::addChildren);
        return bitch;
    }
}
