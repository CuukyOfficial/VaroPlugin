package de.cuuky.varo;

import java.util.LinkedHashSet;
import java.util.List;

public class VaroElementList<T extends VaroElement> extends LinkedHashSet<T> {

    private int highest;

    public VaroElementList() {}

    public VaroElementList(List<T> copy) {
        super(copy);
    }

    private void checkId(T t) {
        if (t.getId() == VaroElement.DEFAULT_ID)
            t.setId(this.highest++);
        else if (this.highest <= t.getId())
            this.highest = t.getId() + 1;
    }

    public boolean add(T t, Varo varo) {
        if (!this.add(t))
            return false;

        this.checkId(t);
        t.initialize(varo);
        return true;
    }
}