package de.varoplugin.varo.list;

import de.varoplugin.varo.list.item.lists.BlockedItems;
import de.varoplugin.varo.list.item.lists.BlockedRecipes;
import de.varoplugin.varo.list.item.lists.ChestItems;
import de.varoplugin.varo.list.item.lists.DeathItems;
import de.varoplugin.varo.list.item.lists.LogDestroyedBlocks;
import de.varoplugin.varo.list.item.lists.StartItems;

public class VaroListManager {

	private BlockedItems blockedItems;
	private BlockedRecipes blockedRecipes;
	private ChestItems chestItems;
	private DeathItems deathItems;
	private LogDestroyedBlocks destroyedBlocks;
	private StartItems startItems;

	public VaroListManager() {
		this.blockedItems = new BlockedItems();
		this.blockedRecipes = new BlockedRecipes();
		this.chestItems = new ChestItems();
		this.destroyedBlocks = new LogDestroyedBlocks();
		this.startItems = new StartItems();
		this.deathItems = new DeathItems();

		VaroList.saveLists();
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
}