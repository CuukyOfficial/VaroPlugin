package de.varoplugin.varo.spawns;

import de.varoplugin.varo.serialize.VaroSerializeObject;

public class SpawnHandler extends VaroSerializeObject {

	public SpawnHandler() {
		super(Spawn.class, "/stats/spawns.yml");

		load();
	}

	@Override
	public void onSave() {
		clearOld();

		for (Spawn spawn : Spawn.getSpawns())
			save(String.valueOf(spawn.getNumber()), spawn, getConfiguration());

		saveFile();
	}
}
