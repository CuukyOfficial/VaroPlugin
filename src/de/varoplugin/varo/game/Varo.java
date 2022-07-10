package de.varoplugin.varo.game;

import de.varoplugin.varo.VaroPlugin;
import de.varoplugin.varo.data.VaroDataGame;
import de.varoplugin.varo.game.entity.player.PlayerContainer;
import de.varoplugin.varo.game.entity.team.TeamContainer;
import de.varoplugin.varo.game.world.ItemChestContainer;

public interface Varo extends PlayerContainer, TeamContainer, AutoStartable, ItemChestContainer, VaroDataGame {

    void initialize(VaroPlugin plugin);

    State getState();

    boolean setState(State state);

    VaroPlugin getPlugin();

}