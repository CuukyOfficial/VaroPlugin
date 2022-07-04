package de.varoplugin.varo.game.entity.team;

import de.varoplugin.varo.game.entity.VaroEntity;

import java.util.Collection;

public interface VaroTeam extends VaroEntity {

    void setName(String name);

    boolean addMember(VaroTeamable teamable);

    Collection<VaroTeamable> getMember();

}