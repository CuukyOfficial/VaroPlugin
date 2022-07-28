package de.varoplugin.varo.game.entity.team;

import java.util.Collection;
import java.util.UUID;

public interface TeamBuilder {

    TeamBuilder uuid(UUID uuid);

    TeamBuilder name(String name);

    TeamBuilder member(Collection<Teamable> teamables);

    TeamBuilder member(Teamable teamable);

    Team create();
}
