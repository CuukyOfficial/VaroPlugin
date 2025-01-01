package de.cuuky.varo.list.enchantment.lists;

import org.bukkit.enchantments.Enchantment;

import de.cuuky.varo.list.enchantment.EnchantmentList;

public class BlockedEnchantments extends EnchantmentList {

	public BlockedEnchantments() {
		super("BlockedEnchantments");
	}
	
	@Override
	public void loadDefaultValues() {

	}

	@SuppressWarnings("deprecation")
	public boolean isBlocked(Enchantment ench, int amplifier) {
		return enchantments.contains(ench.getName() + ":" + amplifier);
	}
}
