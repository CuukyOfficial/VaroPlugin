package de.varoplugin.varo.game.entity;

import de.varoplugin.varo.game.VaroObject;
import de.varoplugin.varo.game.world.protectable.ProtectableOwner;

public interface VaroEntity extends VaroObject, ProtectableOwner {

    String getName();

    boolean isAlive();

}