package de.varoplugin.varo.game.entity.team;

import de.varoplugin.varo.api.event.game.team.VaroTeamMemberAddEvent;
import de.varoplugin.varo.game.entity.GameEntity;
import de.varoplugin.varo.game.entity.VaroEntity;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class GameTeam extends GameEntity implements VaroTeam {

    private final Set<VaroTeamable> members;
    private String name;

    public GameTeam(String name) {
        super();
        this.name = name;
        this.members = new HashSet<>();
    }

    @Override
    public boolean addMember(VaroTeamable teamable) {
        if (this.members.contains(teamable) || !this.getVaro().getPlugin().isCancelled(new VaroTeamMemberAddEvent(this, teamable))) return false;
        teamable.setTeam(this);
        return this.members.add(teamable);
    }

    @Override
    public Collection<VaroTeamable> getMember() {
        return new LinkedList<>(this.members);
    }

    @Override
    public boolean canAccessSavings(VaroEntity player) {
        return this.members.stream().anyMatch(p -> p.getUuid().equals(player.getUuid()));
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
