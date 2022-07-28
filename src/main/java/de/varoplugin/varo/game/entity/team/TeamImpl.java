package de.varoplugin.varo.game.entity.team;

import de.varoplugin.varo.api.event.game.team.TeamMemberAddEvent;
import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.entity.VaroEntityImpl;
import de.varoplugin.varo.game.world.protectable.ProtectableOwner;

import java.util.*;

final class TeamImpl extends VaroEntityImpl implements Team {

    private String name;
    private Set<Teamable> members;

    TeamImpl(UUID uuid, String name, Collection<Teamable> members) {
        super(uuid);
        this.name = name;
        this.members = new HashSet<>();
        members.forEach(this::addMember);
    }

    @Override
    public void initialize(Varo varo) {
        super.initialize(varo);
        if (this.members == null) this.members = new HashSet<>();
    }

    @Override
    public boolean isAlive() {
        return this.members.stream().allMatch(Teamable::isAlive);
    }

    @Override
    public boolean addMember(Teamable teamable) {
        if (this.members.contains(teamable) || !this.getVaro().getPlugin().isCancelled(new TeamMemberAddEvent(this, teamable))) return false;
        return this.members.add(teamable);
    }

    @Override
    public Collection<Teamable> getMember() {
        return new LinkedList<>(this.members);
    }

    @Override
    public boolean canAccessSavings(ProtectableOwner holder) {
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
