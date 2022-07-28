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
