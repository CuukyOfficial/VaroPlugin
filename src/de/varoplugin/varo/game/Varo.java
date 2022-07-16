package de.varoplugin.varo.game;

import de.varoplugin.varo.VaroPlugin;
import de.varoplugin.varo.data.VaroDataGame;
import de.varoplugin.varo.game.entity.player.PlayerContainer;
import de.varoplugin.varo.game.entity.team.TeamContainer;
import de.varoplugin.varo.game.world.ItemChestContainer;

import java.util.stream.Stream;

public interface Varo extends PlayerContainer, TeamContainer, AutoStartable, ItemChestContainer, VaroDataGame {

    void initialize(VaroPlugin plugin);

    State getState();

    void nextState();

    boolean setState(State state);

    Stream<State> getStates();

    VaroPlugin getPlugin();

}