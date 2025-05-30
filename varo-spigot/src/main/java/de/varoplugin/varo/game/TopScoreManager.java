package de.varoplugin.varo.game;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import de.varoplugin.varo.player.VaroPlayer;
import de.varoplugin.varo.team.VaroTeam;

public class TopScoreManager {

	private static final Comparator<VaroPlayer> PLAYER_COMPARATOR = new Comparator<VaroPlayer>() {

        @Override
        public int compare(VaroPlayer o1, VaroPlayer o2) {
            return Integer.signum(o2.getStats().getKills() - o1.getStats().getKills());
        }
    };
    
	private static final Comparator<VaroTeam> TEAM_COMPARATOR = new Comparator<VaroTeam>() {

        @Override
        public int compare(VaroTeam o1, VaroTeam o2) {
            return Integer.signum(o2.getKills() - o1.getKills());
        }
    };

	private List<VaroPlayer> topPlayers;
	private List<VaroTeam> topTeams;

	public TopScoreManager() {
		this.update();
	}
	
	public void update() {
        this.topPlayers = VaroPlayer.getVaroPlayers().stream().filter(player -> player.getStats().getKills() > 0)
                .sorted(PLAYER_COMPARATOR).collect(Collectors.toList());
        this.topTeams = VaroTeam.getTeams().stream().filter(player -> player.getKills() > 0)
                .sorted(TEAM_COMPARATOR).collect(Collectors.toList());
    }

	public VaroPlayer getPlayer(int rank) {
	    List<VaroPlayer> topPlayers = this.topPlayers;
		if (rank >= 1 && rank - 1 < topPlayers.size())
		    return topPlayers.get(rank - 1);
        return null;
	}

	public VaroTeam getTeam(int rank) {
	    List<VaroTeam> topTeams = this.topTeams;
        if (rank >= 1 && rank - 1 < topTeams.size())
            return topTeams.get(rank - 1);
        return null;
	}
}