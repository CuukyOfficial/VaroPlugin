package de.cuuky.varo.spawns;

import de.cuuky.varo.serialize.VaroSerializeObject;
import de.cuuky.varo.spawns.spawn.SpawnType;

public class SpawnHandler extends VaroSerializeObject {

	private static SpawnHandler instance;

	static {
		registerEnum(SpawnType.class);
	}

	private SpawnHandler() {
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

	public static void initialise() {
		if(instance == null) {
			instance = new SpawnHandler();
		}
	}
}
