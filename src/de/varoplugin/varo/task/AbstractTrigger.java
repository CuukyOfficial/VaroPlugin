package de.varoplugin.varo.task;

import de.varoplugin.varo.game.Varo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractTrigger extends AbstractListener implements VaroTrigger {
    private final Set<VaroRegistrable> registrations;
    private final boolean match;
    private Varo varo;

    public AbstractTrigger(boolean match) {
        this.registrations = new HashSet<>();
        this.match = match;
    }

    protected boolean shallRegister(boolean shouldRegister) {
        return shouldRegister == this.match;
    }

    protected void registerChildren() {
        this.registrations.forEach(registrable -> registrable.register(this.varo));
    }

    protected void deregisterChildren() {
        this.registrations.forEach(registrable -> registrable.register(varo));
    }

    @Override
    public void register(Varo varo) {
        this.varo = varo;
        super.register(varo);
        this.registrations.forEach(registrable -> registrable.register(varo));
    }

    @Override
    public void deregister() {
        this.varo = null;
        super.deregister();
        this.registrations.forEach(VaroRegistrable::deregister);
        this.registrations.forEach(VaroRegistrable::destroy);
    }

    @Override
    public void destroy() {
        this.deregister();
    }

    @Override
    public Varo getVaro() {
        return this.varo;
    }

    @Override
    public void addChildren(VaroRegistrable... registrable) {
        this.registrations.addAll(Arrays.asList(registrable));
    }
}
