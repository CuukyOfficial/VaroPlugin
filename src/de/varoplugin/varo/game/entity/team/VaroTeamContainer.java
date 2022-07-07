package de.varoplugin.varo.game.entity.team;

import java.util.stream.Stream;

public interface VaroTeamContainer {

    boolean addTeam(VaroTeam team);

    Stream<VaroTeam> getTeams();
}
