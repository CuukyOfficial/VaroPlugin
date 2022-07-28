package de.varoplugin.varo.util.map;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Is it a map? Is it a set? Who knows
 *
 * @param <V> Type
 */
public interface UniqueIdMap<V extends UniqueObject> extends Map<UUID, V> {

    boolean contains(V o);

    boolean add(V add);

    boolean remove(V remove);

    Stream<V> stream();

}