package de.varoplugin.varo.team;

import de.varoplugin.varo.serialize.VaroSerializeObject;

public class VaroTeamHandler extends VaroSerializeObject {

	public VaroTeamHandler() {
		super(VaroTeam.class, "/stats/teams.yml");

		load();
	}

	@Override
	public void onSave() {
		clearOld();

		for (VaroTeam team : VaroTeam.getTeams())
			save(String.valueOf(team.getId()), team, getConfiguration());

		saveFile();
	}
}