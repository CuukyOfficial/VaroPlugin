package de.cuuky.varo.heartbeat;

import de.cuuky.varo.entity.player.VaroPlayer;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.function.Predicate;

public abstract class PlayerListener implements Listener {

    private final List<Predicate<VaroPlayer>> checks;
    protected final VaroPlayer player;

    public PlayerListener(VaroPlayer player, List<Predicate<VaroPlayer>> checks) {
        this.player = player;
        this.checks = checks;
    }

    protected boolean isDisabled() {
        return !this.checks.stream().allMatch(c -> c.test(player));
    }

    public boolean isPlayer(VaroPlayer player) {
        return this.player.equals(player);
    }
}