package de.varoplugin.varo.game.entity.team;

import de.varoplugin.varo.game.VaroObject;

public interface Teamable extends VaroObject {

    boolean isAlive();

    boolean hasTeam();

    void setTeam(Team team);

    Team getTeam();

}