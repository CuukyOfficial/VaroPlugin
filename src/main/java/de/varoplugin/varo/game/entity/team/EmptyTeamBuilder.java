/* 
 * VaroPlugin
 * Copyright (C) 2022 Cuuky, Almighty-Satan
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/

package de.varoplugin.varo.game.entity.team;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

public class EmptyTeamBuilder implements TeamBuilder {

    private UUID uuid;
    private String name;
    private final Collection<Teamable> members;

    public EmptyTeamBuilder() {
        this.members = new HashSet<>();
    }

    @Override
    public TeamBuilder uuid(UUID uuid) {
        this.uuid = Objects.requireNonNull(uuid);
        return this;
    }

    @Override
    public TeamBuilder name(String name) {
        this.name = Objects.requireNonNull(name);
        return this;
    }

    @Override
    public TeamBuilder member(Collection<Teamable> members) {
        this.members.addAll(Objects.requireNonNull(members));
        return this;
    }

    @Override
    public TeamBuilder member(Teamable member) {
        this.members.add(Objects.requireNonNull(member));
        return this;
    }

    @Override
    public Team create() {
        if (this.name == null) throw new IllegalArgumentException("No name provided");
        return new TeamImpl(this.uuid == null ? UUID.randomUUID() : this.uuid, this.name, this.members);
    }
}
