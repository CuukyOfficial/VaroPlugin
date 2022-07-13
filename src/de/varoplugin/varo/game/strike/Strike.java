package de.varoplugin.varo.game.strike;

import de.varoplugin.varo.game.VaroObject;
import de.varoplugin.varo.game.entity.player.VaroPlayer;

public interface Strike extends VaroObject {

    void setTarget(VaroPlayer target);

    VaroPlayer getTarget();

    StrikeType getType();

    void execute();

    boolean wasExecuted();

}