package de.varoplugin.varo.tasks.register;

import de.varoplugin.varo.game.entity.player.VaroPlayer;

public interface VaroPlayerTaskInfo extends VaroRegisterInfo {

    VaroPlayer getPlayer();

}