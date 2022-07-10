package de.varoplugin.varo.api.event.game.team;

import de.varoplugin.varo.api.event.game.VaroGameCancelableEvent;
import de.varoplugin.varo.game.entity.team.Team;

public abstract class VaroTeamEvent extends VaroGameCancelableEvent {

    private final Team team;

    public VaroTeamEvent(Team team) {
        super(team.getVaro());
        this.team = team;
    }

    public Team getTeam() {
        return this.team;
    }
}
