package de.varoplugin.varo.api.event.game.team;

import de.varoplugin.varo.api.event.game.VaroCancelableEvent;
import de.varoplugin.varo.game.entity.team.Team;

public abstract class TeamEvent extends VaroCancelableEvent {

    private final Team team;

    public TeamEvent(Team team) {
        super(team.getVaro());
        this.team = team;
    }

    public Team getTeam() {
        return this.team;
    }
}
