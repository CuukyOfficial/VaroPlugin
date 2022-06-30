package de.varoplugin.varo.task.factory;

import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.task.*;

public abstract class AbstractTriggerWrapper extends AbstractListener implements VaroTrigger {

    private final VaroTrigger internal;
    private Varo varo;
    private boolean register;
    private boolean deregister;

    public AbstractTriggerWrapper() {
        this.internal = new EmptyTrigger();
    }

    protected void test() {
        this.testRegister();
        this.testDeregister();
    }

    protected void testRegister() {
        if (this.register) this.internal.register(this.varo);
        this.register = false;
    }

    protected void testDeregister() {
        if (this.deregister) this.internal.deregister();
        this.deregister = false;
    }

    @Override
    public void register(Varo varo) {
        if (this.varo != null) {
            this.varo = varo;
            super.register(varo);
        }
        this.register = true;
    }

    @Override
    public void deregister() {
        this.deregister = true;
    }

    @Override
    public void destroy() {
        super.destroy();
        this.internal.destroy();
    }

    @Override
    public Varo getVaro() {
        return this.internal.getVaro();
    }

    @Override
    public void addChildren(VaroRegistrable... registrable) {
        this.internal.addChildren(registrable);
    }
}
