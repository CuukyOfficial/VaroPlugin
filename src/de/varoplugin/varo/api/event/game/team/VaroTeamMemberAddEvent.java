package de.varoplugin.varo.api.event.game.team;

import de.varoplugin.varo.game.entity.team.VaroTeam;
import de.varoplugin.varo.game.entity.team.VaroTeamable;

public class VaroTeamMemberAddEvent extends VaroTeamEvent {

    private final VaroTeamable teamable;

    public VaroTeamMemberAddEvent(VaroTeam team, VaroTeamable teamable) {
        super(team);
        this.teamable = teamable;
    }

    public VaroTeamable getMember() {
        return this.teamable;
    }
}
