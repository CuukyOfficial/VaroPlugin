package de.cuuky.varo.api.objects.team;

import java.util.ArrayList;

import de.cuuky.varo.api.objects.player.VaroAPIPlayer;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.team.Team;

public class VaroAPITeam {

	private Team team;

	public VaroAPITeam(Team team) {
		this.team = team;
	}

	public String getColorcode() {
		return team.getColorCode();
	}

	public String getDisplayName() {
		return team.getDisplay();
	}

	public int getId() {
		return team.getId();
	}

	public int getKills() {
		return team.getKills();
	}

	public ArrayList<VaroAPIPlayer> getMember() {
		ArrayList<VaroAPIPlayer> teams = new ArrayList<>();
		for(VaroPlayer player : team.getMember())
			teams.add(new VaroAPIPlayer(player));

		return teams;
	}

	public String getName() {
		return team.getName();
	}

	public void setColorcode(String code) {
		team.setColorCode(code);
	}

	public static ArrayList<VaroAPITeam> getTeams() {
		ArrayList<VaroAPITeam> teams = new ArrayList<>();
		for(Team team : Team.getTeams())
			teams.add(new VaroAPITeam(team));

		return teams;
	}
}