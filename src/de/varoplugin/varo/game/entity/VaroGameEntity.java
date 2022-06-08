package de.varoplugin.varo.game.entity;

import de.varoplugin.varo.api.event.game.world.secureable.VaroSecureableAddEvent;
import de.varoplugin.varo.api.event.game.world.secureable.VaroSecureableRemoveEvent;
import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.world.protectable.BasicProtectableContainer;
import de.varoplugin.varo.game.world.protectable.ProtectableContainer;
import de.varoplugin.varo.game.world.protectable.VaroProtectable;
import org.bukkit.block.Block;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public abstract class VaroGameEntity implements VaroEntity {

    private ProtectableContainer protectableContainer;
    protected Varo varo;

    @Override
    public void initialize(Varo varo) {
        this.varo = varo;
        this.protectableContainer = new BasicProtectableContainer();
    }

    @Override
    public boolean addProtectable(VaroProtectable secureable) {
        if (this.hasProtectable(secureable) || this.varo.getPlugin().isCancelled(new VaroSecureableAddEvent(this.varo, secureable)))
            return false;
        return this.protectableContainer.addProtectable(secureable);
    }

    @Override
    public boolean removeProtectable(VaroProtectable secureable) {
        if (!this.hasProtectable(secureable) || this.varo.getPlugin().isCancelled(new VaroSecureableRemoveEvent(this.varo, secureable)))
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