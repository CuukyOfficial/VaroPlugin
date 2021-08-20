package de.cuuky.varo.entity.player.stats.stat.inventory;

import java.util.ArrayList;
import java.util.Date;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.serialize.identifier.VaroSerializeField;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;

public class InventoryBackup implements VaroSerializeable {

	private static ArrayList<InventoryBackup> allBackups;

	static {
		allBackups = new ArrayList<>();
	}

	@VaroSerializeField(path = "date")
	private Date date;

	@VaroSerializeField(path = "contents")
	private ArrayList<ItemStack> contents;

	private VaroPlayer varoplayer;

	@VaroSerializeField(path = "playerId")
	private int vpId;

	public InventoryBackup() {
		allBackups.add(this);
	}

	public InventoryBackup(VaroPlayer vp) {
		this.varoplayer = vp;
		this.date = new Date();
		this.contents = new ArrayList<>();
		this.clear(vp.getPlayer().getInventory().getContents().length == 36 ? 40 : vp.getPlayer().getInventory().getContents().length);

		if (vp.isOnline())
			addUpdate(vp.getPlayer());

		allBackups.add(this);
	}

	public void addUpdate(Player player) {
		ItemStack[] contents = player.getInventory().getContents();
		for (int i = 0; i < contents.length; i++)
			this.contents.set(i, contents[i]);

		if (player.getInventory().getContents().length == 36) {
			ItemStack[] armor = player.getInventory().getArmorContents();
			for(int i = 0; i < 4; i++)
				this.contents.set(i + 36, armor[i]);
		}
	}

	public ItemStack[] getArmor() {
		return new ItemStack[] {this.contents.get(36), this.contents.get(37), this.contents.get(38), this.contents.get(39)};
	}

	public Date getDate() {
		return date;
	}

	public ItemStack[] getAllContents() {
		return this.contents.toArray(new ItemStack[this.contents.size()]);
	}

	public void setItem(int index, ItemStack stack) {
		this.contents.set(index, stack);
	}

	public void clear() {
		this.clear(this.contents.size());
	}
	
	public void clear(int size) {
		for (int i = 0; i < size; i++)
			if (i < this.contents.size())
				this.contents.set(i, new ItemStack(Material.AIR));
			else
				this.contents.add(new ItemStack(Material.AIR));
	}

	public VaroPlayer getVaroPlayer() {
		return varoplayer;
	}

	@Override
	public void onDeserializeEnd() {
		this.varoplayer = VaroPlayer.getPlayer(vpId);
	}

	@Override
	public void onSerializeStart() {
		if (varoplayer != null)
			this.vpId = varoplayer.getId();
	}

	public void remove() {
		allBackups.remove(this);
	}

	public void restoreUpdate(Player player) {
		player.getInventory().clear();

		for (int i = 0; i < player.getInventory().getContents().length; i++)
			player.getInventory().setItem(i, this.contents.get(i));

		if (player.getInventory().getContents().length == 36)
			player.getInventory().setArmorContents(this.getArmor());

		player.updateInventory();
	}

	public static ArrayList<InventoryBackup> getAllBackups() {
		return allBackups;
	}
}