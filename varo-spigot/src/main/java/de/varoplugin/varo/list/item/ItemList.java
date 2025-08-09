package de.varoplugin.varo.list.item;

import org.bukkit.inventory.ItemStack;

import de.varoplugin.varo.list.VaroList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class ItemList extends VaroList {

	private final int maxSize;
	private final boolean uniqueType, publicc;
	private List<ItemStack> items;

	public ItemList(String location, int maxSize, boolean uniqueType, boolean publicc) {
		super(location);

		this.publicc = publicc;
		this.maxSize = maxSize;
		this.uniqueType = uniqueType;
	}

	public ItemList(String location, int maxSize, boolean uniqueType) {
		this(location, maxSize, uniqueType, true);
	}

	protected ItemStack fixItem(ItemStack item) {
		item = item.clone();
		if (!this.uniqueType) return item;
		item.setAmount(1);
		if (isDamageable(item)) item.setDurability((short) 0);
		return item;
	}

	protected boolean isDamageable(ItemStack item) {
		if (item == null) return false;
		String[] split = item.getType().toString().split("_");
		int length = split.length;
		switch (split[length - 1]) {
		case "HELMET":
			return true;
		case "CHESTPLATE":
			return true;
		case "LEGGINGS":
			return true;
		case "BOOTS":
			return true;
		case "SWORD":
			return true;
		case "AXE":
			return true;
		case "PICKAXE":
			return true;
		case "SHOVEL":
			return true;
		case "HOE":
			return true;
		case "ELYTRA":
			return true;
		case "TURTLE_HELMET":
			return true;
		case "TRIDENT":
			return true;
		case "HORSE_ARMOR":
			return true;
		case "SHEARS":
			return true;
		default:
			return false;
		}
	}

	private void processItem(ItemStack stack, Consumer<ItemStack> process) {
		process.accept(fixItem(stack));
	}

	public boolean addItem(ItemStack item) {
	    Objects.requireNonNull(item);
		if (this.maxSize > 0 && this.items.size() >= this.maxSize) return false;
		this.processItem(item, this.items::add);
		return true;
	}

	public void removeItem(ItemStack item) {
		this.processItem(item, this.items::remove);
	}

	public boolean hasItem(ItemStack item) {
		return items.contains(fixItem(item));
	}

	@Override
	public void init() {
		this.items = new ArrayList<>();
	}

	@Override
	public void onLoad(List<?> list) {
		for (Object object : list) {
		    if (object != null)
    			try {
    				ItemStack itemStack = (ItemStack) object;
    				items.add(itemStack);
    			} catch (Exception e) {
    			    e.printStackTrace();
    			}
		}
	}

	@Override
	public List<?> getAsList() {
		return items;
	}

	public List<ItemStack> getItems() {
		return items;
	}

	public void setItems(List<ItemStack> items) {
		this.items = items;
	}

	public boolean isUniqueType() {
		return uniqueType;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public boolean isPublic() {
		return publicc;
	}

	private static Stream<ItemList> getItemList() {
		return getLists().stream().filter(list -> list instanceof ItemList).map(v -> (ItemList) v);
	}

	public static ItemList getItemList(String list) {
		return getItemLists().stream()
				.filter(iList -> iList.getLocation().equalsIgnoreCase(list)).findFirst().orElse(null);
	}

	public static List<ItemList> getItemLists() {
		return getItemList().collect(Collectors.toList());
	}
}