package de.cuuky.varo.list.item;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.list.VaroList;

public class ItemList extends VaroList {

	protected ArrayList<ItemStack> items;

	public ItemList(String location) {
		super(location);
	}

	public void addItem(ItemStack item) {
		items.add(fixItem(item));

		saveList();
	}

	public void removeItem(ItemStack item) {
		items.remove(fixItem(item));

		saveList();
	}

	public boolean hasItem(ItemStack item) {
		return items.contains(fixItem(item));
	}

	@Override
	public ArrayList<?> getAsList() {
		return items;
	}

	@Override
	public void onLoad(List<?> list) {
		items = new ArrayList<ItemStack>();

		for(Object id : list) {
			try {
				ItemStack c = (ItemStack) id;
				items.add(c);
			} catch(Exception e) {
				continue;
			}
		}
	}

	protected ItemStack fixItem(ItemStack item) {
		item = item.clone();
		item.setAmount(1);
		if(isDamageable(item))
			item.setDurability((short) 0);
		return item;
	}

	protected boolean isDamageable(ItemStack item) {
		if(item == null)
			return false;

		String[] split = item.getType().toString().split("_");
		int length = split.length;
		switch(split[length - 1]) {
		case "HELMET":
			return true;
		case "CHESTPLATE":
			return true;
		case "LEGGINGS":
			return true;
		case "BOOTS":
			return true;
		case "SWORD":
			return true;
		case "AXE":
			return true;
		case "PICKAXE":
			return true;
		case "SHOVEL":
			return true;
		case "HOE":
			return true;
		case "ELYTRA":
			return true;
		case "TURTLE_HELMET":
			return true;
		case "TRIDENT":
			return true;
		case "HORSE_ARMOR":
			return true;
		case "SHEARS":
			return true;
		default:
			return false;
		}
	}

	public ArrayList<ItemStack> getItems() {
		return items;
	}

	public static ArrayList<ItemList> getItemLists() {
		ArrayList<ItemList> iList = new ArrayList<>();

		for(VaroList vlist : VaroList.getLists())
			if(vlist instanceof ItemList)
				iList.add((ItemList) vlist);

		return iList;
	}

	public static ItemList getItemList(String list) {
		for(ItemList iList : getItemLists())
			if(iList.getLocation().equalsIgnoreCase(list))
				return iList;

		return null;
	}
}