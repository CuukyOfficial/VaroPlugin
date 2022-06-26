package de.varoplugin.varo.game.entity;

import de.varoplugin.varo.game.GameObject;
import de.varoplugin.varo.game.world.protectable.ProtectableContainer;
import de.varoplugin.varo.game.world.protectable.VaroProtectable;
import de.varoplugin.varo.game.world.protectable.VaroProtectableContainer;
import org.bukkit.block.Block;

import java.util.UUID;

public abstract class GameEntity extends GameObject implements VaroEntity {

    private final VaroProtectableContainer protectableContainer;

    public GameEntity(UUID uuid) {
        super(uuid);

        this.protectableContainer = new ProtectableContainer();
    }

    public GameEntity() {
        super();

        this.protectableContainer = new ProtectableContainer();
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