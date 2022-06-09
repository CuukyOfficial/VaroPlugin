package de.varoplugin.varo.game.entity;

import de.varoplugin.varo.api.event.game.world.protectable.VaroProtectableAddEvent;
import de.varoplugin.varo.api.event.game.world.protectable.VaroProtectableRemoveEvent;
import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.world.protectable.ProtectableContainer;
import de.varoplugin.varo.game.world.protectable.VaroProtectableContainer;
import de.varoplugin.varo.game.world.protectable.VaroProtectable;
import org.bukkit.block.Block;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public abstract class VaroGameEntity implements VaroEntity {

    private VaroProtectableContainer protectableContainer;
    protected Varo varo;

    @Override
    public void initialize(Varo varo) {
        this.varo = varo;
        this.protectableContainer = new ProtectableContainer();
    }

    @Override
    public boolean addProtectable(VaroProtectable secureable) {
        if (this.hasProtectable(secureable) || this.varo.getPlugin().isCancelled(new VaroProtectableAddEvent(this.varo, secureable)))
            return false;
        return this.protectableContainer.addProtectable(secureable);
    }

    @Override
    public boolean removeProtectable(VaroProtectable secureable) {
        if (!this.hasProtectable(secureable) || this.varo.getPlugin().isCancelled(new VaroProtectableRemoveEvent(this.varo, secureable)))
            return false;
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