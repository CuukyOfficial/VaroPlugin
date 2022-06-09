package de.varoplugin.varo.game.entity;

import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.VaroGameObject;
import de.varoplugin.varo.game.world.protectable.VaroProtectableHolder;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public interface VaroEntity extends VaroGameObject, VaroProtectableHolder {

    void initialize(Varo varo);

}