package de.cuuky.varo.list.enchantment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.enchantments.Enchantment;

import de.cuuky.varo.Main;
import de.cuuky.varo.list.VaroList;

public abstract class EnchantmentList extends VaroList {

	protected ArrayList<Enchantment> enchantments;

	public EnchantmentList(String location) {
		super(location);
	}

	public void addEnchantment(Enchantment enc) {
		enchantments.add(enc);

		saveList();
	}
	
	@Override
	public void init() {
		this.enchantments = new ArrayList<>();
	}

	@Override
	public void onLoad(List<?> list) {
		for (Object id : list) {
		    String name = ((String) id).split(":")[0]; // legacy
		    Enchantment enchantment = Enchantment.getByName(name);
		    if (enchantment == null) {
		        Main.getInstance().getLogger().warning("Unknown enchantment '" + enchantment + "'");
		        continue;
		    }
			enchantments.add(enchantment);
		}
	}

	public void removeEnchantment(Enchantment enc) {
		enchantments.remove(enc);

		saveList();
	}

	@Override
	public List<String> getAsList() {
		return enchantments.stream().map(Enchantment::getName).collect(Collectors.toList());
	}

	public ArrayList<Enchantment> getEnchantments() {
		return enchantments;
	}

	public boolean hasEnchantment(Enchantment enc) {
		return enchantments.contains(enc);
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