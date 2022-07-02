package de.varoplugin.varo.game.world.protectable;

import de.varoplugin.varo.api.event.game.world.protectable.VaroProtectableAddEvent;
import de.varoplugin.varo.api.event.game.world.protectable.VaroProtectableRemoveEvent;
import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.util.map.HashUniqueIdMap;
import de.varoplugin.varo.util.map.UniqueIdMap;
import org.bukkit.block.Block;

public class ProtectableContainer implements VaroProtectableContainer {

    private final Varo varo;
    private final UniqueIdMap<VaroProtectable> protectables;

    public ProtectableContainer(Varo varo) {
        this.varo = varo;
        this.protectables = new HashUniqueIdMap<>();
    }

    @Override
    public boolean addProtectable(VaroProtectable protectable) {
        if (this.protectables.contains(protectable)) return false;
        if (this.varo.getPlugin().isCancelled(new VaroProtectableAddEvent(protectable)))
            return false;
        protectable.initialize(this.varo);
        return this.protectables.add(protectable);
    }

    @Override
    public boolean removeProtectable(VaroProtectable secureable) {
        if (!this.protectables.contains(secureable)) return false;
        if (this.varo.getPlugin().isCancelled(new VaroProtectableRemoveEvent(secureable)))
            return false;
        return this.protectables.remove(secureable);
    }

    @Override
    public boolean hasProtectable(VaroProtectable secureable) {
        return this.protectables.contains(secureable);
    }

    @Override
    public VaroProtectable getProtectable(Block block) {
        return this.protectables.stream().filter(savable -> savable.getBlock().equals(block)).findAny().orElse(null);
    }
}