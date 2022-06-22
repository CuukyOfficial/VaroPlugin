package de.varoplugin.varo.game.world.protectable;

import org.bukkit.block.Block;

import java.util.HashSet;
import java.util.Set;

public class ProtectableContainer implements VaroProtectableContainer {

    protected final Set<VaroProtectable> secureables;

    public ProtectableContainer() {
        this.secureables = new HashSet<>();
    }

    @Override
    public boolean addProtectable(VaroProtectable savable) {
        return this.secureables.add(savable);
    }

    @Override
    public boolean removeProtectable(VaroProtectable savable) {
        return this.secureables.remove(savable);
    }

    @Override
    public boolean hasProtectable(VaroProtectable secureable) {
        return this.secureables.contains(secureable);
    }

    @Override
    public VaroProtectable getProtectable(Block block) {
        return this.secureables.stream().filter(savable -> savable.getBlock().equals(block)).findAny().orElse(null);
    }
}