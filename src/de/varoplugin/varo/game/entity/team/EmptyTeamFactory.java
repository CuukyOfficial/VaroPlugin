package de.varoplugin.varo.game.entity.team;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

public class EmptyTeamFactory implements TeamFactory {

    private UUID uuid;
    private String name;
    private final Collection<Teamable> members;

    public EmptyTeamFactory() {
        this.members = new HashSet<>();
    }

    @Override
    public TeamFactory uuid(UUID uuid) {
        this.uuid = Objects.requireNonNull(uuid);
        return this;
    }

    @Override
    public TeamFactory name(String name) {
        this.name = Objects.requireNonNull(name);
        return this;
    }

    @Override
    public TeamFactory member(Collection<Teamable> members) {
        this.members.addAll(Objects.requireNonNull(members));
        return this;
    }

    @Override
    public TeamFactory member(Teamable member) {
        this.members.add(Objects.requireNonNull(member));
        return this;
    }

    @Override
    public Team create() {
        if (this.name == null) throw new IllegalArgumentException("No name provided");
        return new TeamImpl(this.uuid == null ? UUID.randomUUID() : this.uuid, this.name, this.members);
    }
}
