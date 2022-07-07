package de.varoplugin.varo.game.task;

import de.varoplugin.varo.api.task.AbstractTask;
import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.entity.player.ParticipantState;
import de.varoplugin.varo.game.entity.player.PlayerMode;
import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.game.entity.team.GameTeam;
import de.varoplugin.varo.game.entity.team.VaroTeam;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RandomPlayerTask extends AbstractTask {

    private final int teamSize;

    public RandomPlayerTask(Varo varo, int teamSize) {
        super(varo);
        this.teamSize = teamSize;
    }

    private void doRandomTeam() {
//        int maxNameLength = ConfigSetting.TEAM_MAX_NAME_LENGTH.getValueAsInt();
        int maxNameLength = 16;
        List<VaroPlayer> random = this.getVaro().getPlayers()
                .filter(pl -> pl.getTeam() == null && pl.getMode() == PlayerMode.NONE && pl.getState() == ParticipantState.ALIVE).collect(Collectors.toList());
        Collections.shuffle(random);

        for (int i = 0; i < random.size(); i += this.teamSize) {
            int actualSize = Math.min(i + this.teamSize, random.size());
            Collection<VaroPlayer> member = random.subList(i, actualSize);
//            if (member.size() < this.teamSize) // TODO: Event
//                member.forEach(m -> m.sendMessage(ConfigMessages.VARO_COMMANDS_RANDOMTEAM_NO_PARTNER));

            // name
            String name = member.stream().map(m -> m.getName()
                    .substring(0, Math.min(m.getName().length(), maxNameLength / member.size()))).collect(Collectors.joining());

            // add
            VaroTeam team = new GameTeam(name);
            this.getVaro().addTeam(team);
            member.forEach(team::addMember);
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
