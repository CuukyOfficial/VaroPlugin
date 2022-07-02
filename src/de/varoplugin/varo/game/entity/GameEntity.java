package de.varoplugin.varo.game.entity;

import de.varoplugin.varo.game.GameObject;
import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.world.protectable.ProtectableContainer;
import de.varoplugin.varo.game.world.protectable.VaroProtectable;
import de.varoplugin.varo.game.world.protectable.VaroProtectableContainer;
import org.bukkit.block.Block;

import java.util.UUID;

public abstract class GameEntity extends GameObject implements VaroEntity {

    private VaroProtectableContainer protectableContainer;

    public GameEntity(UUID uuid) {
        super(uuid);
    }

    public GameEntity() {
        super();
    }

    @Override
    public void initialize(Varo varo) {
        super.initialize(varo);
        if (this.protectableContainer != null) this.protectableContainer = new ProtectableContainer(varo);
    }

    @Override
    public boolean addProtectable(VaroProtectable secureable) {
        return this.protectableContainer.addProtectable(secureable);
    }

    @Override
    public boolean removeProtectable(VaroProtectable secureable) {
        return this.protectableContainer.removeProtectable(secureable);
    }

    @Override
    public boolean hasProtectable(VaroProtectable secureable) {
        return this.protectableContainer.hasProtectable(secureable);
    }

    @Override
    public VaroProtectable getProtectable(Block block) {
        return this.protectableContainer.getProtectable(block);
    }
}