package de.varoplugin.varo.util;

import java.util.AbstractMap;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class Pair<K, V> extends AbstractMap.SimpleEntry<K, V> {

    public Pair(K k, V v) {
        super(k, v);
    }
}