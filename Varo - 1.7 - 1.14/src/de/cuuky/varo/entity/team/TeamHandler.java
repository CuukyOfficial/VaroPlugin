package de.cuuky.varo.team;

import de.cuuky.varo.serialize.VaroSerializeHandler;

public class TeamHandler extends VaroSerializeHandler {

	public TeamHandler() {
		super(Team.class, "/stats/teams.yml");

		load();
	}

	@Override
	public void onSave() {
		clearOld();

		for(Team team : Team.getTeams())
			save(String.valueOf(team.getId()), team, getConfiguration());

		saveFile();
	}
}
