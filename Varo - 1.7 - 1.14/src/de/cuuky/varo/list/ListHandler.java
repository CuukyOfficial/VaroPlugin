package de.cuuky.varo.list;

import de.cuuky.varo.list.enchantment.lists.BlockedEnchantments;
import de.cuuky.varo.list.item.lists.BlockedItems;
import de.cuuky.varo.list.item.lists.BlockedRecipes;
import de.cuuky.varo.list.item.lists.ChestItems;
import de.cuuky.varo.list.item.lists.DeathItems;
import de.cuuky.varo.list.item.lists.LogDestroyedBlocks;
import de.cuuky.varo.list.item.lists.StartItems;

public class ListHandler {

	public static ListHandler instance;

	private BlockedEnchantments blockedEnchantments;
	private BlockedItems blockedItems;
	private BlockedRecipes blockedRecipes;
	private ChestItems chestItems;
	private DeathItems deathItems;
	private LogDestroyedBlocks destroyedBlocks;
	private StartItems startItems;

	private ListHandler() {
		this.blockedEnchantments = new BlockedEnchantments();
		this.blockedItems = new BlockedItems();
		this.blockedRecipes = new BlockedRecipes();
		this.chestItems = new ChestItems();
		this.destroyedBlocks = new LogDestroyedBlocks();
		this.startItems = new StartItems();
		this.deathItems = new DeathItems();

		VaroList.saveLists();
	}

	public BlockedEnchantments getBlockedEnchantments() {
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

	public static ListHandler getInstance() {
		if(instance == null) {
			instance = new ListHandler();
		}
		return instance;
	}
}
