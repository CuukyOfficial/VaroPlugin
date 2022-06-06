package de.varoplugin.varo.game.world.secureable;

import org.bukkit.block.Block;

import java.util.HashSet;
import java.util.Set;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class BasicSecureableContainer implements SecureableContainer {

    protected final Set<VaroSecureable> secureables;

    public BasicSecureableContainer() {
        this.secureables = new HashSet<>();
    }

    @Override
    public boolean addSecureable(VaroSecureable savable) {
        return this.secureables.add(savable);
    }

    @Override
    public boolean removeSecureable(VaroSecureable savable) {
        return this.secureables.remove(savable);
    }

    @Override
    public boolean hasSecureable(VaroSecureable secureable) {
        return this.secureables.contains(secureable);
    }

    @Override
    public VaroSecureable getSavable(Block block) {
        return this.secureables.stream().filter(savable -> savable.getBlock().equals(block)).findAny().orElse(null);
    }
}