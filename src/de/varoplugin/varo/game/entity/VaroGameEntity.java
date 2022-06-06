package de.varoplugin.varo.game.entity;

import de.varoplugin.varo.api.event.game.world.secureable.VaroSecureableAddEvent;
import de.varoplugin.varo.api.event.game.world.secureable.VaroSecureableRemoveEvent;
import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.world.secureable.BasicSecureableContainer;
import de.varoplugin.varo.game.world.secureable.SecureableContainer;
import de.varoplugin.varo.game.world.secureable.VaroSecureable;
import org.bukkit.block.Block;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public abstract class VaroGameEntity implements VaroEntity {

    private SecureableContainer secureableContainer;
    protected Varo varo;

    @Override
    public void initialize(Varo varo) {
        this.varo = varo;
        this.secureableContainer = new BasicSecureableContainer();
    }

    @Override
    public boolean addSecureable(VaroSecureable secureable) {
        if (this.hasSecureable(secureable) || this.varo.getPlugin().isCancelled(new VaroSecureableAddEvent(this.varo, secureable)))
            return false;
        return this.secureableContainer.addSecureable(secureable);
    }

    @Override
    public boolean removeSecureable(VaroSecureable secureable) {
        if (!this.hasSecureable(secureable) || this.varo.getPlugin().isCancelled(new VaroSecureableRemoveEvent(this.varo, secureable)))
            return false;
        return this.secureableContainer.removeSecureable(secureable);
    }

    @Override
    public boolean hasSecureable(VaroSecureable secureable) {
        return this.secureableContainer.hasSecureable(secureable);
    }

    @Override
    public VaroSecureable getSavable(Block block) {
        return this.secureableContainer.getSavable(block);
    }
}