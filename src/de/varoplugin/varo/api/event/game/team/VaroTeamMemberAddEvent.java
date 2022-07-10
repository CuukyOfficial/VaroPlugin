package de.varoplugin.varo.api.event.game.team;

import de.varoplugin.varo.game.entity.team.Team;
import de.varoplugin.varo.game.entity.team.Teamable;

public class VaroTeamMemberAddEvent extends VaroTeamEvent {

    private final Teamable teamable;

    public VaroTeamMemberAddEvent(Team team, Teamable teamable) {
        super(team);
        this.teamable = teamable;
    }

    public Teamable getMember() {
        return this.teamable;
    }
}
