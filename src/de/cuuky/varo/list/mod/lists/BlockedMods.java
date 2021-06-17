package de.cuuky.varo.list.mod.lists;

import de.cuuky.varo.list.mod.ModList;

public class BlockedMods extends ModList {

	public BlockedMods() {
		super("BlockedMods");
	}
	
	@Override
	public void loadDefaultValues() {
		this.mods.add("Xray");
	}

	public boolean isBlocked(String name) {
		if (mods.contains(name))
			return true;

		return false;
	}

}
