package de.varoplugin.varo.game.world.protectable;

import de.varoplugin.varo.api.event.game.world.protectable.VaroProtectableAddEvent;
import de.varoplugin.varo.api.event.game.world.protectable.VaroProtectableRemoveEvent;
import de.varoplugin.varo.game.GameObject;
import org.bukkit.block.Block;

import java.util.HashSet;
import java.util.Set;

public class ProtectableContainer extends GameObject implements VaroProtectableContainer {

    protected final Set<VaroProtectable> secureables;

    public ProtectableContainer() {
        this.secureables = new HashSet<>();
    }

    @Override
    public boolean addProtectable(VaroProtectable secureable) {
        if (this.secureables.contains(secureable)) return false;
        if (this.getVaro().getPlugin().isCancelled(new VaroProtectableAddEvent(this.getVaro(), secureable)))
            return false;
        return this.secureables.add(secureable);
    }

    @Override
    public boolean removeProtectable(VaroProtectable secureable) {
        if (!this.secureables.contains(secureable)) return false;
        if (this.getVaro().getPlugin().isCancelled(new VaroProtectableRemoveEvent(this.getVaro(), secureable)))
            return false;
        return this.secureables.remove(secureable);
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