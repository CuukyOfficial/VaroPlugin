package de.varoplugin.varo.task;

import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.entity.player.VaroParticipantState;
import de.varoplugin.varo.game.entity.player.VaroPlayerMode;
import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.game.entity.team.EmptyTeamFactory;
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
            Team team = new EmptyTeamFactory().name(name).create();
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
