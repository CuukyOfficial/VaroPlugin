package de.varoplugin.varo.util.map;

import java.util.HashMap;
import java.util.UUID;
import java.util.stream.Stream;

public class HashUniqueIdMap<E extends UniqueObject> extends HashMap<UUID, E> implements UniqueIdMap<E> {

    @Override
    public boolean contains(E o) {
        return super.containsKey(o.getUuid());
    }

    @Override
    public boolean add(E add) {
        return super.put(add.getUuid(), add) != null;
    }

    @Override
    public boolean remove(E remove) {
        return super.remove(remove.getUuid()) != null;
    }

    @Override
    public Stream<E> stream() {
        return super.values().stream();
    }
}