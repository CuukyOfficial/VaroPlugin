package de.varoplugin.varo.game.entity;

import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.world.secureable.SecureableHolder;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public interface VaroEntity extends SecureableHolder {

    void initialize(Varo varo);

    Varo getVaro();

}