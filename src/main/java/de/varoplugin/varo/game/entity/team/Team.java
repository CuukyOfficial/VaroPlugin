package de.varoplugin.varo.game.entity.team;

import de.varoplugin.varo.game.entity.VaroEntity;

import java.util.Collection;

public interface Team extends VaroEntity {

    void setName(String name);

    boolean addMember(Teamable teamable);

    Collection<Teamable> getMember();

}