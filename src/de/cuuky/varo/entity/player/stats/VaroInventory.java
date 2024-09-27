package de.cuuky.varo.entity.player.stats;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.serialize.identifier.VaroSerializeField;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;

public class VaroInventory implements VaroSerializeable {

	@VaroSerializeField(path = "inventory")
	private HashMap<String, ItemStack> inventoryList;

	@VaroSerializeField(path = "size")
	private int size;

	private Inventory inventory;

	public VaroInventory() {} // Serialization

	public VaroInventory(int size) {
		inventoryList = new HashMap<>();
		if (size >= 54)
		    this.size = 54;
		else if (size < 9)
		    this.size = 9;
		else
		    this.size = Math.min(54, (size % 9 == 0 ? 0 : (9 - (size % 9))) + size);

		createInventory();
	}

    private void createInventory() {
		inventory = Bukkit.createInventory(null, size, "§aBackpack");
	}

	public void clear() {
		inventoryList.clear();
		inventory.clear();
	}

	@Override
	public void onDeserializeEnd() {
		createInventory();

		for (String i : inventoryList.keySet())
			inventory.setItem(Integer.valueOf(i), inventoryList.get(i));
	}

	@Override
	public void onSerializeStart() {
		for (int i = 0; i < inventory.getSize(); i++) {
			ItemStack stack = inventory.getItem(i);
			if (stack != null && stack.getType() != Material.AIR)
				inventoryList.put(String.valueOf(i), stack);
		}
	}

	public Inventory getInventory() {
		return inventory;
	}
}