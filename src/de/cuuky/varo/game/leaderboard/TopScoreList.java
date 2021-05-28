package de.cuuky.varo.game.leaderboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.team.VaroTeam;

public class TopScoreList {

	private Comparator<VaroPlayer> playerSort;
	private Comparator<VaroTeam> teamSort;

	private List<VaroPlayer> topPlayer;
	private List<VaroTeam> topTeams;

	public TopScoreList() {
		this.topPlayer = new ArrayList<>();
		this.topTeams = new ArrayList<>();

		playerSort = new Comparator<VaroPlayer>() {

			@Override
			public int compare(VaroPlayer o1, VaroPlayer o2) {
				if (o1.getStats().getKills() == o2.getStats().getKills())
					return 0;

				return o1.getStats().getKills() > o2.getStats().getKills() ? -1 : 1;
			}
		};

		teamSort = new Comparator<VaroTeam>() {

			@Override
			public int compare(VaroTeam o1, VaroTeam o2) {
				if (o1.getKills() == o2.getKills())
					return 0;

				return o1.getKills() > o2.getKills() ? -1 : 1;
			}
		};

		update();
	}

	public VaroPlayer getPlayer(int rank) {
		if (rank - 1 < topPlayer.size())
			return (VaroPlayer) topPlayer.toArray()[rank - 1];
		else
			return null;
	}

	public VaroTeam getTeam(int rank) {
		if (rank - 1 < topTeams.size())
			return (VaroTeam) topTeams.toArray()[rank - 1];
		else
			return null;
	}

	public void update() {
		topPlayer.clear();
		topTeams.clear();

		for (VaroPlayer player : VaroPlayer.getVaroPlayer()) {
			int kills = player.getStats().getKills();

			if (kills > 0)
				topPlayer.add(player);
		}

		for (VaroTeam team : VaroTeam.getTeams()) {
			int kills = team.getKills();

			if (kills > 0)
				topTeams.add(team);
		}

		Collections.sort(topPlayer, playerSort);
		Collections.sort(topTeams, teamSort);
	}
}