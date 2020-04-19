package de.cuuky.varo.list.enchantment;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.enchantments.Enchantment;

import de.cuuky.varo.list.VaroList;

public class EnchantmentList extends VaroList {

	protected ArrayList<String> enchantments;

	public EnchantmentList(String location) {
		super(location);
	}

	public void addEnchantment(Enchantment enc, int amplifier) {
		enchantments.add(enc.getName() + ":" + amplifier);

		saveList();
	}

	@Override
	public void onLoad(List<?> list) {
		enchantments = new ArrayList<String>();

		for (Object id : list)
			enchantments.add((String) id);
	}

	public void removeEnchantment(Enchantment enc, int amplifier) {
		enchantments.remove(enc.getName() + ":" + amplifier);

		saveList();
	}

	@Override
	public ArrayList<String> getAsList() {
		return enchantments;
	}

	public ArrayList<String> getEnchantments() {
		return enchantments;
	}

	public boolean hasEnchantment(Enchantment enc, int amplifier) {
		return enchantments.contains(enc.getName() + ":" + amplifier);
	}

	public static EnchantmentList getEnchantmentList(String list) {
		for (EnchantmentList eList : getEnchantmentLists())
			if (eList.getLocation().equalsIgnoreCase(list))
				return eList;

		return null;
	}

	public static ArrayList<EnchantmentList> getEnchantmentLists() {
		ArrayList<EnchantmentList> eList = new ArrayList<>();

		for (VaroList vlist : VaroList.getLists())
			if (vlist instanceof EnchantmentList)
				eList.add((EnchantmentList) vlist);

		return eList;
	}
}