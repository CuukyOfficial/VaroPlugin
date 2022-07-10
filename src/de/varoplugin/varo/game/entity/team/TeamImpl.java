package de.varoplugin.varo.game.entity.team;

import de.varoplugin.varo.api.event.game.team.VaroTeamMemberAddEvent;
import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.entity.EntityImpl;
import de.varoplugin.varo.game.world.protectable.ProtectableHolder;

import java.util.*;

public class TeamImpl extends EntityImpl implements Team {

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
        if (this.members.contains(teamable) || !this.getVaro().getPlugin().isCancelled(new VaroTeamMemberAddEvent(this, teamable))) return false;
        return this.members.add(teamable);
    }

    @Override
    public Collection<Teamable> getMember() {
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
