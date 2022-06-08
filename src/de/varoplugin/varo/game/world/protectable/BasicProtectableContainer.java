package de.varoplugin.varo.game.world.protectable;

import org.bukkit.block.Block;

import java.util.HashSet;
import java.util.Set;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class BasicProtectableContainer implements ProtectableContainer {

    protected final Set<VaroProtectable> secureables;

    public BasicProtectableContainer() {
        this.secureables = new HashSet<>();
    }

    @Override
    public boolean addSecureable(VaroProtectable savable) {
        return this.secureables.add(savable);
    }

    @Override
    public boolean removeSecureable(VaroProtectable savable) {
        return this.secureables.remove(savable);
    }

    @Override
    public boolean hasSecureable(VaroProtectable secureable) {
        return this.secureables.contains(secureable);
    }

    @Override
    public VaroProtectable getSavable(Block block) {
        return this.secureables.stream().filter(savable -> savable.getBlock().equals(block)).findAny().orElse(null);
    }
}