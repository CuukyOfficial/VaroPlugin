package de.cuuky.varo;

import de.cuuky.cfw.configuration.serialization.BasicSerializable;
import de.cuuky.cfw.configuration.serialization.Serialize;

import java.util.Objects;

public abstract class VaroElement extends BasicSerializable {

    protected static final int DEFAULT_ID = -1;

    protected Varo varo;

    @Serialize("id")
    protected int id = DEFAULT_ID;

    void initialize(Varo varo) {
        this.varo = varo;
        this.onInitialize(varo);
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
        final StringBuffer sb = new StringBuffer("VaroElement{");
        sb.append("varo=").append(varo);
        sb.append(", id=").append(id);
        sb.append('}');
        return sb.toString();
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