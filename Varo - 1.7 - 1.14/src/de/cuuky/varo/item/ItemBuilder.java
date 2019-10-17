package de.cuuky.varo.item;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import de.cuuky.varo.Main;
import de.cuuky.varo.utils.Utils;
import de.cuuky.varo.version.types.Materials;

public class ItemBuilder {

	private String displayName;
	private String playerName;
	private ItemStack stack;
	private ArrayList<String> lore;
	private int amount;

	public ItemBuilder() {
		amount = 1;
	}

	public ItemBuilder displayname(String displayname) {
		this.displayName = displayname;
		return this;
	}

	public ItemBuilder player(Player player) {
		this.playerName = player.getName();
		return this;
	}

	public ItemBuilder amount(int amount) {
		this.amount = amount;
		return this;
	}

	public ItemBuilder itemstack(ItemStack stack) {
		this.stack = stack;
		return this;
	}

	public ItemBuilder lore(ArrayList<String> lore) {
		this.lore = lore;
		return this;
	}

	public ItemBuilder lore(String[] lore) {
		this.lore = Utils.collectionToArray(lore);
		return this;
	}

	public ItemBuilder lore(String lore) {
		this.lore = Utils.collectionToArray(new String[] { lore });
		return this;
	}

	public ItemBuilder playername(String playername) {
		this.playerName = playername;
		return this;
	}

	public ItemBuilder deleteDamageAnnotation() {
		ItemMeta Meta = stack.getItemMeta();
		Meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		Meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
		Meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		Meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
		Meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		Meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		stack.setItemMeta(Meta);
		return this;
	}

	public ItemStack build() {
		ItemMeta stackMeta = stack.getItemMeta();
		if(displayName != null && stack.getType() != Material.AIR)
			stackMeta.setDisplayName(displayName);

		if(lore != null)
			stackMeta.setLore(lore);
		stack.setItemMeta(stackMeta);
		stack.setAmount(amount);

		return stack;
	}

	@SuppressWarnings("deprecation")
	public ItemStack buildSkull() {
		stack = Materials.PLAYER_HEAD.parseItem();
		SkullMeta skullMeta = (SkullMeta) stack.getItemMeta();

		skullMeta.setDisplayName(displayName != null ? Main.getColorCode() + displayName : Main.getColorCode() + playerName);
		skullMeta.setOwner(playerName != null ? playerName : displayName);

		if(lore != null)
			skullMeta.setLore(lore);

		stack.setItemMeta(skullMeta);
		stack.setAmount(amount);

		return stack;
	}
}