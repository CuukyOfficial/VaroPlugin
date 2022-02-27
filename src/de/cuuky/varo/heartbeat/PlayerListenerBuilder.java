package de.cuuky.varo.heartbeat;

import de.cuuky.varo.entity.player.VaroPlayer;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class PlayerListenerBuilder implements Function<VaroPlayer, PlayerListener> {

    private final BiFunction<VaroPlayer, List<Predicate<VaroPlayer>>, PlayerListener> creator;
    private final List<Predicate<VaroPlayer>> requirements;

    public PlayerListenerBuilder(BiFunction<VaroPlayer, List<Predicate<VaroPlayer>>, PlayerListener> creator) {
        this.creator = creator;
        this.requirements = new LinkedList<>();
    }

    @SafeVarargs
    public final PlayerListenerBuilder requirements(Predicate<VaroPlayer>... requirements) {
        this.requirements.addAll(Arrays.asList(requirements));
        return this;
    }

    @Override
    public PlayerListener apply(VaroPlayer varoPlayer) {
        return this.creator.apply(varoPlayer, this.requirements);
    }
}
