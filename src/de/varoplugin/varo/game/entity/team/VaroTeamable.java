package de.varoplugin.varo.game.entity.team;

import de.varoplugin.varo.game.VaroGameObject;

public interface VaroTeamable extends VaroGameObject {

    boolean hasTeam();

    void setTeam(VaroTeam team);

    VaroTeam getTeam();

}