package de.cuuky.varo.list;

import de.cuuky.varo.list.enchantment.lists.BlockedEnchantmentList;
import de.cuuky.varo.list.enchantment.lists.BlockedEnchantments;
import de.cuuky.varo.list.enchantment.lists.BlockedEnchantmentsOneEight;
import de.cuuky.varo.list.item.lists.BlockedItems;
import de.cuuky.varo.list.item.lists.BlockedRecipes;
import de.cuuky.varo.list.item.lists.ChestItems;
import de.cuuky.varo.list.item.lists.DeathItems;
import de.cuuky.varo.list.item.lists.LogDestroyedBlocks;
import de.cuuky.varo.list.item.lists.StartItems;
import de.cuuky.varo.list.mod.lists.BlockedMods;
import de.varoplugin.cfw.version.VersionUtils;

public class VaroListManager {

	private BlockedEnchantmentList blockedEnchantments;
	private BlockedItems blockedItems;
	private BlockedRecipes blockedRecipes;
	private ChestItems chestItems;
	private DeathItems deathItems;
	private LogDestroyedBlocks destroyedBlocks;
	private StartItems startItems;
	private BlockedMods blockedMods;

	public VaroListManager() {
	    switch (VersionUtils.getVersion()) {
	        case ONE_8:
	            this.blockedEnchantments = new BlockedEnchantmentsOneEight();
	            break;
	        default:
	            this.blockedEnchantments = new BlockedEnchantments();
	            break;
	    }
		this.blockedItems = new BlockedItems();
		this.blockedRecipes = new BlockedRecipes();
		this.chestItems = new ChestItems();
		this.destroyedBlocks = new LogDestroyedBlocks();
		this.startItems = new StartItems();
		this.deathItems = new DeathItems();
		this.blockedMods = new BlockedMods();

		VaroList.saveLists();
	}

	public BlockedEnchantmentList getBlockedEnchantments() {
		return blockedEnchantments;
	}

	public BlockedItems getBlockedItems() {
		return blockedItems;
	}

	public BlockedRecipes getBlockedRecipes() {
		return blockedRecipes;
	}

	public ChestItems getChestItems() {
		return chestItems;
	}

	public DeathItems getDeathItems() {
		return deathItems;
	}

	public LogDestroyedBlocks getDestroyedBlocks() {
		return destroyedBlocks;
	}

	public StartItems getStartItems() {
		return startItems;
	}

	public BlockedMods getBlockedMods() {
		return blockedMods;
	}
}