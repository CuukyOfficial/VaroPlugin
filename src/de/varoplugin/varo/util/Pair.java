package de.varoplugin.varo.util;

import java.util.AbstractMap;

public class Pair<K, V> extends AbstractMap.SimpleEntry<K, V> {

    public Pair(K k, V v) {
        super(k, v);
    }
}