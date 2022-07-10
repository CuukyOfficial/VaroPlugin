package de.varoplugin.varo.game.entity.team;

import java.util.stream.Stream;

public interface TeamContainer {

    boolean addTeamMember(Team to, Teamable member);

    boolean addTeam(Team team);

    Stream<Team> getTeams();
}
