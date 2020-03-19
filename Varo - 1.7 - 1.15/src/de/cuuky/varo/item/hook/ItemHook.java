package de.cuuky.varo.item.hook;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemHook {

	private static ArrayList<ItemHook> itemHooks;

	static {
		itemHooks = new ArrayList<ItemHook>();
	}

	private ItemHookListener hookListener;
	private boolean dropable, dragable;
	private Player player;
	private ItemStack stack;

	public ItemHook(Player player, ItemStack stack, int slot, ItemHookListener listener) {
		this.hookListener = listener;
		this.stack = stack;
		this.player = player;
		this.dropable = false;
		this.dragable = false;
		
		player.getInventory().setItem(slot, stack);

		if(getItemHook(stack, player) != null)
			getItemHook(stack, player).remove();
		
		player.updateInventory();

		itemHooks.add(this);
	}
	
	public void setDropable(boolean dropable) {
		this.dropable = dropable;
	}
	
	public boolean isDropable() {
		return dropable;
	}
	
	public void setDragable(boolean dragable) {
		this.dragable = dragable;
	}
	
	public boolean isDragable() {
		return dragable;
	}

	public ItemStack getItemStack() {
		return stack;
	}
	
	public Player getPlayer() {
		return player;
	}

	public void remove() {
		itemHooks.remove(this);
	}
	
	public ItemHookListener getHookListener() {
		return hookListener;
	}

	public static ItemHook getItemHook(ItemStack stack, Player player) {
		for(ItemHook hook : itemHooks)
			if(hook.getItemStack().equals(stack) && hook.getPlayer().equals(player))
				return hook;

		return null;
	}

	public static ArrayList<ItemHook> getItemHooks() {
		return itemHooks;
	}
}