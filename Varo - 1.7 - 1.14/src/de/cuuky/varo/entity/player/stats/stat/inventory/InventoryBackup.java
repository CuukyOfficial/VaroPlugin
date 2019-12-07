package de.cuuky.varo.entity.player.stats.stat.inventory;

import java.util.ArrayList;
import java.util.Date;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.VaroInventory;
import de.cuuky.varo.serialize.identifier.VaroSerializeField;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;

public class InventoryBackup implements VaroSerializeable {

	private static ArrayList<InventoryBackup> allBackups;

	static {
		allBackups = new ArrayList<>();
	}

	@VaroSerializeField(path = "inventory")
	private VaroInventory inventory;

	@VaroSerializeField(path = "armor")
	private ArrayList<ItemStack> armor;

	@VaroSerializeField(path = "date")
	private Date date;

	@VaroSerializeField(path = "playerId")
	private int vpId;

	private VaroPlayer varoplayer;

	public InventoryBackup() {
		allBackups.add(this);
	}

	public InventoryBackup(VaroPlayer vp) {
		this.varoplayer = vp;
		this.date = new Date();
		this.inventory = new VaroInventory(45);
		this.armor = new ArrayList<>();

		if(vp.isOnline())
			addUpdate(vp.getPlayer());

		allBackups.add(this);
	}

	@Override
	public void onDeserializeEnd() {
		this.varoplayer = VaroPlayer.getPlayer(vpId);
	}

	@Override
	public void onSerializeStart() {
		if(varoplayer != null)
			this.vpId = varoplayer.getId();
	}

	public void addUpdate(Player player) {
		this.inventory.getInventory().setContents(player.getInventory().getContents());
		this.armor = new ArrayList<>();

		for(ItemStack l : player.getInventory().getArmorContents())
			armor.add(l);
	}

	public void restoreUpdate(Player player) {
		player.getInventory().clear();

		try {
			for(int i = 0; i < inventory.getInventory().getContents().length; i++)
				player.getInventory().setItem(i, inventory.getInventory().getContents()[i]);
		} catch(ArrayIndexOutOfBoundsException e) {}

		ItemStack[] armorc = new ItemStack[4];
		for(int i = 0; i < armor.size(); i++)
			armorc[i] = armor.get(i);
		player.getInventory().setArmorContents(armorc);

		player.updateInventory();
	}

	public ArrayList<ItemStack> getArmor() {
		return armor;
	}

	public void remove() {
		allBackups.remove(this);
	}

	public Date getDate() {
		return date;
	}

	public VaroInventory getInventory() {
		return inventory;
	}

	public VaroPlayer getVaroPlayer() {
		return varoplayer;
	}

	public static ArrayList<InventoryBackup> getAllBackups() {
		return allBackups;
	}
}
