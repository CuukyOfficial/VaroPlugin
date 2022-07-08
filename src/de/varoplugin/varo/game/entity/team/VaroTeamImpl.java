package de.varoplugin.varo.game.entity.team;

import de.varoplugin.varo.api.event.game.team.VaroTeamMemberAddEvent;
import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.entity.VaroEntityImpl;
import de.varoplugin.varo.game.world.protectable.ProtectableHolder;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class VaroTeamImpl extends VaroEntityImpl implements VaroTeam {

    private Set<VaroTeamable> members;
    private String name;

    public VaroTeamImpl(String name) {
        super();
        this.name = name;
    }

    @Override
    public void initialize(Varo varo) {
        super.initialize(varo);
        if (this.members == null) this.members = new HashSet<>();
    }

    @Override
    public boolean isAlive() {
        return this.members.stream().allMatch(VaroTeamable::isAlive);
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
    public boolean canAccessSavings(ProtectableHolder holder) {
        return this.members.stream().anyMatch(p -> p.getUuid().equals(holder.getUuid()));
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
