package de.cuuky.varo.list.mod;

import java.util.ArrayList;
import java.util.List;

import de.cuuky.varo.list.VaroList;

public class ModList extends VaroList {

	protected ArrayList<String> mods;

	public ModList(String location) {
		super(location);
	}

	public void addEnchantment(String name) {
		mods.add(name);

		saveList();
	}

	@Override
	public void onLoad(List<?> list) {
		mods = new ArrayList<String>();

		for(Object mod : list)
			mods.add((String) mod);
	}

	public void removeMod(String name) {
		mods.remove(name);

		saveList();
	}

	@Override
	public ArrayList<String> getAsList() {
		return mods;
	}

	public ArrayList<String> getModList() {
		return mods;
	}

	public boolean hasMod(String name) {
		return mods.contains(name);
	}

	public static ModList getModList(String list) {
		for(ModList mList : getModLists())
			if(mList.getLocation().equalsIgnoreCase(list))
				return mList;

		return null;
	}

	public static ArrayList<ModList> getModLists() {
		ArrayList<ModList> mList = new ArrayList<>();

		for(VaroList vlist : VaroList.getLists())
			if(vlist instanceof ModList)
				mList.add((ModList) vlist);

		return mList;
	}
}
