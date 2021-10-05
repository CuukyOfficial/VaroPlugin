package de.cuuky.varo.list.mod;

import de.cuuky.varo.list.VaroList;

import java.util.ArrayList;
import java.util.List;

public abstract class ModList extends VaroList {

	protected List<String> mods;

	public ModList(String location) {
		super(location);
	}

	public void addMod(String name) {
		mods.add(name);

		saveList();
	}
	
	@Override
	public void init() {
		this.mods = new ArrayList<>();
	}

	@Override
	public void onLoad(List<?> list) {
		for (Object mod : list)
			mods.add((String) mod);
	}

	public void removeMod(String name) {
		mods.remove(name);

		saveList();
	}

	@Override
	public List<String> getAsList() {
		return mods;
	}

	public List<String> getModList() {
		return mods;
	}

	public boolean hasMod(String name) {
		return mods.contains(name);
	}

	public static ModList getModList(String list) {
		for (ModList mList : getModLists())
			if (mList.getLocation().equalsIgnoreCase(list))
				return mList;

		return null;
	}

	public static ArrayList<ModList> getModLists() {
		ArrayList<ModList> mList = new ArrayList<>();

		for (VaroList vlist : VaroList.getLists())
			if (vlist instanceof ModList)
				mList.add((ModList) vlist);

		return mList;
	}
}
