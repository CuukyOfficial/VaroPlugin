package de.varoplugin.varo.game.world.secureable;

import de.varoplugin.varo.game.CancelableTask;
import de.varoplugin.varo.game.TaskProvider;
import de.varoplugin.varo.game.VaroState;
import de.varoplugin.varo.util.Pair;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public enum VaroBlockSecureableType implements VaroSecureableType {

    FURNACE,
    CHEST;

    private final Map<VaroState, TaskProvider<VaroSecureable>> infos;

    @SafeVarargs
    VaroBlockSecureableType(Pair<VaroState, TaskProvider<VaroSecureable>>... infos) {
        this.infos = new HashMap<>();
        Arrays.stream(infos).forEach(info -> this.infos.put(info.getKey(), info.getValue()));
    }

    @Override
    public Collection<CancelableTask> getTasks(VaroState state, VaroSecureable secureable) {
        return this.infos.get(state).getTasks(secureable);
    }
}