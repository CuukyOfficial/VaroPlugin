package de.cuuky.varo.list.mod.lists;

import de.cuuky.varo.list.mod.ModList;

public class BlockedMods extends ModList {

	public BlockedMods() {
		super("BlockedMods");

		if(!mods.isEmpty())
			return;

		mods.add("Xray");
	}

	public boolean isBlocked(String name) {
		if(mods.contains(name))
			return true;

		return false;
	}

}
