package de.varoplugin.varo.game.entity;

import de.varoplugin.varo.game.VaroGameObject;
import de.varoplugin.varo.game.world.protectable.ProtectableHolder;

public interface VaroEntity extends VaroGameObject, ProtectableHolder {

    String getName();

}