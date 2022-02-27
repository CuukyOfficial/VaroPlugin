package de.cuuky.varo;

import de.cuuky.cfw.configuration.serialization.BasicSerializable;
import de.cuuky.cfw.configuration.serialization.Serialize;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

public abstract class VaroElement<> extends BasicSerializable {

    protected static final int DEFAULT_ID = -1;

    protected Varo varo;

    @Serialize("id")
    protected int id = DEFAULT_ID;

    void initialize(Varo varo) {
        this.varo = varo;
        this.onInitialize(varo);

        this.registerPolicy(UUID.class, UUID::toString, UUID::fromString);
    }

    protected <P, C> void registerPolicy(Class<C> clazz, Function<C, P> supplier, Function<P, C> consumer) {

    }

    protected abstract void onInitialize(Varo varo);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VaroElement that = (VaroElement) o;
        return id == that.id && Objects.equals(varo, that.varo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(varo, id);
    }

    @Override
    public String toString() {
        return "VaroElement{" + "varo=" + varo + ", id=" + id + '}';
    }

    public void remove() {
        this.varo.removeElement(this);
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        if (this.varo != null)
            throw new IllegalStateException("Cannot set id after being initialized");

        this.id = id;
    }
}