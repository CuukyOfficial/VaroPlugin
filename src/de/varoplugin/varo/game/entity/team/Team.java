package de.varoplugin.varo.game.entity.team;

import de.varoplugin.varo.game.entity.Entity;

import java.util.Collection;

public interface Team extends Entity {

    void setName(String name);

    boolean addMember(Teamable teamable);

    Collection<Teamable> getMember();

}