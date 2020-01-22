package de.cuuky.varo.list;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

public abstract class VaroList {

	private static YamlConfiguration config;
	private static File file;
	private static ArrayList<VaroList> lists;

	static {
		reloadConfig();
	}

	private String location;

	public VaroList(String location) {
		this.location = location;
		config.options().copyDefaults(true);

		List<?> loadList = config.getList(location);
		if(loadList == null)
			loadList = new ArrayList<>();

		onLoad(loadList);

		lists.add(this);
	}

	public abstract ArrayList<?> getAsList();

	public String getLocation() {
		return location;
	}

	public abstract void onLoad(List<?> list);

	public void saveList() {
		config.set(location, null);
		config.set(location, getAsList());

		try {
			config.save(file);
		} catch(IOException e) {}
	}

	private static void reloadConfig() {
		file = new File("plugins/Varo", "lists.yml");
		config = YamlConfiguration.loadConfiguration(file);
		lists = new ArrayList<VaroList>();
	}

	public static ArrayList<VaroList> getLists() {
		return lists;
	}

	public static void reloadLists() {
		saveLists();
		reloadConfig();

		for(VaroList list : lists)
			list.onLoad(config.getStringList(list.getLocation()));
	}

	public static void saveLists() {
		for(VaroList list : lists) {
			config.set(list.getLocation(), null);
			config.set(list.getLocation(), list.getAsList());
		}

		try {
			config.save(file);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}