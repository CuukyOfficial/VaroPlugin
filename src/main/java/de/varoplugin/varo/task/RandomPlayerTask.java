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

package de.varoplugin.varo.task;

import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.entity.player.VaroParticipantState;
import de.varoplugin.varo.game.entity.player.VaroPlayerMode;
import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.game.entity.team.EmptyTeamBuilder;
import de.varoplugin.varo.game.entity.team.Team;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RandomPlayerTask extends VaroRunnableTask {

    private final int teamSize;

    public RandomPlayerTask(Varo varo, int teamSize) {
        super(varo);
        this.teamSize = teamSize;
    }

    private void doRandomTeam() {
//        int maxNameLength = ConfigSetting.TEAM_MAX_NAME_LENGTH.getValueAsInt();
        int maxNameLength = 16;
        List<VaroPlayer> random = this.getVaro().getPlayers()
                .filter(pl -> pl.getTeam() != null && pl.getMode() == VaroPlayerMode.NONE && pl.getState() == VaroParticipantState.ALIVE).collect(Collectors.toList());
        Collections.shuffle(random);

        for (int i = 0; i < random.size(); i += this.teamSize) {
            int actualSize = Math.min(i + this.teamSize, random.size());
            Collection<VaroPlayer> members = random.subList(i, actualSize);
//            if (member.size() < this.teamSize) // TODO: Event
//                member.forEach(m -> m.sendMessage(ConfigMessages.VARO_COMMANDS_RANDOMTEAM_NO_PARTNER));

            // name
            String name = members.stream().map(m -> m.getName()
                    .substring(0, Math.min(m.getName().length(), maxNameLength / members.size()))).collect(Collectors.joining());

            // add
            Team team = new EmptyTeamBuilder().name(name).create();
            this.getVaro().addTeam(team);
            members.forEach(member -> this.getVaro().addTeamMember(team, member));
        }
    }

    @Override
    protected void onEnable() {
        this.doRandomTeam();
    }

    @Override
    protected void onDisable() {
    }
}
