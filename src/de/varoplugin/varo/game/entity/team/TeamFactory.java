package de.varoplugin.varo.game.entity.team;

import java.util.Collection;
import java.util.UUID;

public interface TeamFactory {

    TeamFactory uuid(UUID uuid);

    TeamFactory name(String name);

    TeamFactory member(Collection<Teamable> teamables);

    TeamFactory member(Teamable teamable);

    Team create();
}
