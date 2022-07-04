package de.varoplugin.varo.api.event.game.team;

import de.varoplugin.varo.api.event.game.VaroGameCancelableEvent;
import de.varoplugin.varo.game.entity.team.VaroTeam;

public abstract class VaroTeamEvent extends VaroGameCancelableEvent {

    private final VaroTeam team;

    public VaroTeamEvent(VaroTeam team) {
        super(team.getVaro());
        this.team = team;
    }

    public VaroTeam getTeam() {
        return this.team;
    }
}
