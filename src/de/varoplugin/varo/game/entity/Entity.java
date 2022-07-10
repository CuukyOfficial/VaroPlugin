package de.varoplugin.varo.game.entity;

import de.varoplugin.varo.game.VaroObject;
import de.varoplugin.varo.game.world.protectable.ProtectableHolder;

public interface Entity extends VaroObject, ProtectableHolder {

    String getName();

    boolean isAlive();

}