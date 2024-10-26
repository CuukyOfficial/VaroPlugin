package de.cuuky.varo.player.stats.stat.inventory;

import java.util.ArrayList;
import java.util.Date;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.player.stats.VaroInventory;
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

	@VaroSerializeField(path = "exp")
	private double exp; // TODO this is a double because the serializer does not like floats for some reason

	@VaroSerializeField(path = "expLevel")
    private int expLevel;

	@Deprecated
	@VaroSerializeField(path = "inventory")
	private VaroInventory inventory;

	@Deprecated
	@VaroSerializeField(path = "armor")
	private ArrayList<ItemStack> armor;

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
			populate(vp.getPlayer());

		allBackups.add(this);
	}

	private void populate(Player player) {
	    this.exp = player.getExp();
	    this.expLevel = player.getLevel();
	    
		ItemStack[] contents = player.getInventory().getContents();
		for (int i = 0; i < contents.length; i++)
			this.setItem(i, contents[i]);

		if (player.getInventory().getContents().length == 36) {
			ItemStack[] armor = player.getInventory().getArmorContents();
			for(int i = 0; i < 4; i++)
				this.setItem(i + 36, armor[i]);
		}
	}
	
	public void restore(Player player) {
	    player.setExp(this.getExp());
	    player.setLevel(this.getExpLevel());
        player.getInventory().clear();

        for (int i = 0; i < player.getInventory().getContents().length; i++)
            player.getInventory().setItem(i, this.getItem(i));

        if (player.getInventory().getContents().length == 36)
            player.getInventory().setArmorContents(this.getArmor());

        player.updateInventory();
    }

	public Date getDate() {
		return date;
	}

	public ItemStack[] getAllContents() {
	    return this.contents.stream().map(ItemStack::new).toArray(ItemStack[]::new);
	}

	private ItemStack[] getArmor() {
        return new ItemStack[] {this.getItem(36), this.getItem(37), this.getItem(38), this.getItem(39)};
    }
	
	private ItemStack getItem(int index) {
	    return new ItemStack(this.contents.get(index));
	}

	public void setItem(int index, ItemStack stack) {
		this.contents.set(index, stack == null ? new ItemStack(Material.AIR) : new ItemStack(stack));
	}

	public float getExp() {
        return (float) this.exp;
    }

	public int getExpLevel() {
        return this.expLevel;
    }

	private void clear(int size) {
	    this.exp = 0;
	    this.expLevel = 0;
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

		//legacy support
		if (this.inventory != null) {
			this.contents = new ArrayList<>();
			
			ItemStack[] contents = this.inventory.getInventory().getContents();
			for(int i = 0; i < 36; i++)
				this.contents.add(contents[i]);
			this.inventory = null;
			
			for(int i = 0; i < 4; i++)
				this.contents.add(this.armor.get(i));
			this.armor = null;
		}
	}

	@Override
	public void onSerializeStart() {
		if (varoplayer != null)
			this.vpId = varoplayer.getId();
	}

	public void remove() {
		allBackups.remove(this);
	}

	public static ArrayList<InventoryBackup> getAllBackups() {
		return allBackups;
	}
}