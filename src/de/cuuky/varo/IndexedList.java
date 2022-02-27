package de.cuuky.varo;

import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

public class IndexedList<T extends VaroElement> extends CopyOnWriteArraySet<T> {

    private int highest;

    public IndexedList() {}

    public IndexedList(List<T> copy) {
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