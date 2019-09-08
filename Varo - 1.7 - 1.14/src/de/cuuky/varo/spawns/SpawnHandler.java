package de.cuuky.varo.spawns;

import de.cuuky.varo.serialize.VaroSerializeHandler;
import de.cuuky.varo.spawns.spawn.SpawnType;

public class SpawnHandler extends VaroSerializeHandler {

	static {
		VaroSerializeHandler.registerEnum(SpawnType.class);
	}

	public SpawnHandler() {
		super(Spawn.class, "/stats/spawns.yml");

		load();
	}

	@Override
	public void onSave() {
		clearOld();

		for(Spawn spawn : Spawn.getSpawns())
			save(String.valueOf(spawn.getNumber()), spawn, getConfiguration());

		saveFile();
	}
}
