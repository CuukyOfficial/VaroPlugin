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
		lists = new ArrayList<>();

		reloadConfig();
	}

	private String location;

	public VaroList(String location) {
		this.location = location;
		config.options().copyDefaults(true);

		loadList(config.getList(location));

		lists.add(this);
	}

	public void loadList(List<?> loadList) {
		if (loadList == null)
			loadList = new ArrayList<>();

		onLoad(loadList);
	}

	public abstract ArrayList<?> getAsList();

	public abstract void onLoad(List<?> list);

	public String getLocation() {
		return location;
	}

	public void saveList() {
		config.set(location, null);
		config.set(location, getAsList());

		try {
			config.save(file);
		} catch (IOException e) {}
	}

	private static void reloadConfig() {
		file = new File("plugins/Varo/config", "lists.yml");
		config = YamlConfiguration.loadConfiguration(file);
	}

	public static ArrayList<VaroList> getLists() {
		return lists;
	}
	
	public static void loadLists() {
		reloadConfig();

		for (VaroList list : lists)
			list.loadList(config.getList(list.getLocation()));
	}

	public static void reloadLists() {
		saveLists();
		loadLists();
	}

	public static void saveLists() {
		for (VaroList list : lists) {
			config.set(list.getLocation(), null);
			config.set(list.getLocation(), list.getAsList());
		}

		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}