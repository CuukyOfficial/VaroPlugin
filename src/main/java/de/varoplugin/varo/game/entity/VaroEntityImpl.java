package de.varoplugin.varo.game.entity;

import de.varoplugin.varo.api.event.game.world.protectable.ProtectableAddEvent;
import de.varoplugin.varo.api.event.game.world.protectable.ProtectableRemoveEvent;
import de.varoplugin.varo.game.UniqueGameObject;
import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.world.protectable.Protectable;
import de.varoplugin.varo.game.world.protectable.ProtectableOwner;
import de.varoplugin.varo.util.map.HashUniqueIdMap;
import de.varoplugin.varo.util.map.UniqueIdMap;
import org.bukkit.block.Block;

import java.util.UUID;

public abstract class VaroEntityImpl extends UniqueGameObject implements VaroEntity {

    private UniqueIdMap<Protectable> protectables;

    public VaroEntityImpl(UUID uuid) {
        super(uuid);
    }

    @Override
    public void initialize(Varo varo) {
        super.initialize(varo);
        if (this.protectables == null) this.protectables = new HashUniqueIdMap<>();
    }

    @Override
    public boolean addProtectable(Protectable protectable) {
        if (this.protectables.contains(protectable)) return false;
        if (this.getVaro().getPlugin().isCancelled(new ProtectableAddEvent(this, protectable)))
            return false;
        protectable.setOwner(this);
        protectable.initialize(this.getVaro());
        return this.protectables.add(protectable);
    }

    @Override
    public boolean removeProtectable(Protectable secureable) {
        if (!this.protectables.contains(secureable)) return false;
        if (this.getVaro().getPlugin().isCancelled(new ProtectableRemoveEvent(this, secureable)))
            return false;
        secureable.setOwner(null);
        return this.protectables.remove(secureable);
    }

    @Override
    public boolean hasProtectable(Protectable secureable) {
        return this.protectables.contains(secureable);
    }

    @Override
    public boolean canAccessSavings(ProtectableOwner holder) {
        return this.getUuid().equals(holder.getUuid());
    }

    @Override
    public Protectable getProtectable(Block block) {
        return this.protectables.stream().filter(savable -> savable.getBlock().equals(block)).findAny().orElse(null);
    }
}