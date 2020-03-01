package de.cuuky.varo.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.config.ConfigEntry;
import de.cuuky.varo.gui.utils.PageAction;
import de.cuuky.varo.item.ItemBuilder;
import de.cuuky.varo.version.BukkitVersion;
import de.cuuky.varo.version.VersionUtils;
import de.cuuky.varo.version.types.Materials;

public abstract class SuperInventory {

	// AUTHOR: "Cuuky",
	// VERSION: "0.3.1";

	private static boolean fill_inventory, animations;

	private static ItemStack forward, backwards;
	private static ArrayList<SuperInventory> guis;

	static {
		guis = new ArrayList<>();

		forward = new ItemBuilder().displayname("쬪Seite vorw채rts").itemstack(new ItemStack(Material.ARROW)).build();
		backwards = new ItemBuilder().displayname("쬬Seite r체ckw채rts").itemstack(new ItemStack(Material.ARROW)).build();
		
		fill_inventory = ConfigEntry.GUI_FILL_INVENTORY.getValueAsBoolean();
		animations = ConfigEntry.GUI_INVENTORY_ANIMATIONS.getValueAsBoolean();
	}

	protected String firstTitle, title;
	private HashMap<ItemMeta, Runnable> itemlinks;
	protected ArrayList<Integer> modifier;
	protected boolean hasMorePages, isLastPage, homePage, ignoreNextClose;
	protected Inventory inv;
	protected Player opener;
	protected int page, size;

	public SuperInventory(String title, Player opener, int size, boolean homePage) {
		this.firstTitle = title;
		this.opener = opener;
		this.page = 1;
		this.homePage = homePage;
		this.size = size;
		this.title = getPageUpdate();
		this.inv = Bukkit.createInventory(null, size != 54 ? size + 9 : size, getPageUpdate());
		this.itemlinks = new HashMap<ItemMeta, Runnable>();

		this.modifier = new ArrayList<Integer>(Arrays.asList(inv.getSize() - 1, inv.getSize() - 9, inv.getSize() - 5));

		SuperInventory inv = getInventory(opener);
		if(inv != null)
			inv.close(true);

		guis.add(this);
	}

	@SuppressWarnings("deprecation")
	private void doAnimation() {
		if(!animations)
			return;

		HashMap<Integer, ItemStack> itemlist = new HashMap<Integer, ItemStack>();
		for(int i = 0; i < (inv.getSize() - 9); i++)
			itemlist.put(i, inv.getItem(i));

		for(int i = 0; i < (inv.getSize() - 9); i++)
			inv.setItem(i, null);
		opener.updateInventory();

		int delay = (int) (600) / (getSize());

		Bukkit.getScheduler().scheduleAsyncDelayedTask(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				int middle = (int) Math.ceil(itemlist.size() / 2);
				for(int radius = 0; middle + radius != itemlist.size(); radius++) {
					if(!isOpen())
						break;

					try {
						Thread.sleep(delay);
					} catch(InterruptedException e) {
						e.printStackTrace();
					}

					inv.setItem(middle + radius, itemlist.get(middle + radius));
					opener.updateInventory();

					try {
						Thread.sleep(delay);
					} catch(InterruptedException e) {
						e.printStackTrace();
					}

					inv.setItem(middle - radius, itemlist.get(middle - radius));
					opener.updateInventory();
				}

				if((inv.getSize() - 9) % 2 == 0 && isOpen()) {
					try {
						Thread.sleep(delay);
					} catch(InterruptedException e) {
						e.printStackTrace();
					}
					inv.setItem(0, itemlist.get(0));
					opener.updateInventory();
				}
			}
		}, 0);
	}

	private void fillSpace() {
		if(!fill_inventory)
			return;

		for(int i = 0; i < inv.getSize(); i++)
			if(inv.getItem(i) == null)
				inv.setItem(i, new ItemBuilder().displayname("쬬").itemstack(new ItemStack(Materials.BLACK_STAINED_GLASS_PANE.parseMaterial(), 1, (short) 15)).build());
	}

	/*
	 * Getter for the back button
	 */
	private String getBack() {
		if(!homePage)
			return "�4Zur체ck";
		else
			return "�4Schlie횩en";
	}

	/*
	 * String for page title
	 */
	private String getPageUpdate() {
		String suff = (hasMorePages ? " �7" + page : "");
		return firstTitle + (firstTitle.length() + suff.length() > 32 ? "" : suff);
	}

	/*
	 * Set Back and Forwards
	 */
	private void setSwitcher() {
		inv.setItem(modifier.get(2), new ItemBuilder().displayname(getBack()).itemstack(getBack().equals("�4Zur체ck") ? new ItemStack(Materials.STONE_BUTTON.parseMaterial()) : Materials.REDSTONE.parseItem()).build());
		if(!hasMorePages)
			return;

		if(!isLastPage)
			inv.setItem(modifier.get(0), forward);

		if(page != 1)
			inv.setItem(modifier.get(1), backwards);
	}

	protected void close(boolean unregister) {
		if(!unregister)
			ignoreNextClose = true;
		else
			guis.remove(this);

		this.opener.closeInventory();
	}

	/*
	 * Enter a runnable where which is being executed when this item was clicked
	 */
	protected void linkItemTo(int location, ItemStack stack, Runnable runnable) {
		inv.setItem(location, stack);
		itemlinks.put(stack.getItemMeta(), runnable);
	}

	public void back() {
		close(true);

		if(!onBackClick())
			new MainMenu(opener);
	}

	public void clear() {
		for(int i = 0; i < inv.getContents().length; i++) {
			if(modifier.contains(i))
				continue;

			inv.setItem(i, new ItemStack(Material.AIR));
		}
	}

	public void closeInventory() {
		if(ignoreNextClose) {
			ignoreNextClose = false;
			return;
		}

		guis.remove(this);
		this.opener.closeInventory();
	}

	/*
	 * Executes itemlinks
	 */
	public void executeLink(ItemStack item) {
		for(ItemMeta stack : itemlinks.keySet())
			if(stack.getDisplayName().equals(item.getItemMeta().getDisplayName())) {
				itemlinks.get(stack).run();
				break;
			}
	}

	public int getFixedSize(int size) {
		if(VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_8))
			return(size < 1 ? 1 : (size > 64 ? 64 : size));
		else
			return size;
	}

	/*
	 * String for page title
	 */
	public void open() {
		isLastPage = this.onOpen();
		if(!isLastPage)
			hasMorePages = true;

		setSwitcher();
		fillSpace();
		this.opener.openInventory(inv);
		doAnimation();
	}

	public void pageActionChanged(PageAction action) {
		onInventoryAction(action);
	}

	public void pageBackwards() {
		page--;
		updateInventory();
	}

	public void pageForwards() {
		page++;
		updateInventory();
	}

	/*
	 * A little method to reopen the gui, for example if you used a AnvilGUI
	 */
	public void reopenSoon() {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				updateInventory();
			}
		}, 1);
	}

	/*
	 * Updating inventory items
	 */
	public void updateInventory() {
		if(opener.getOpenInventory() != null) {
			ignoreNextClose = true;
			opener.closeInventory();
		}

		title = getPageUpdate();
		Inventory newInv = Bukkit.createInventory(null, size != 54 ? size + 9 : size, title);

		this.itemlinks = new HashMap<ItemMeta, Runnable>();
		this.inv = newInv;
		open();
	}

	public abstract boolean onBackClick();

	@Deprecated
	/*
	 * @deprecated: Please use linkItemTo() instead
	 */
	public abstract void onClick(InventoryClickEvent event);

	public abstract void onClose(InventoryCloseEvent event);

	public abstract void onInventoryAction(PageAction action);

	/*
	 * @return Return if this is the last page
	 */
	public abstract boolean onOpen();

	public Inventory getInventory() {
		return this.inv;
	}

	public Player getOpener() {
		return this.opener;
	}

	public int getPage() {
		return page;
	}

	public int getSize() {
		return size;
	}

	public String getTitle() {
		return this.title;
	}

	public boolean isHomePage() {
		return homePage;
	}

	public boolean isOpen() {
		return guis.contains(this);
	}

	/*
	 * Calculates based on the list size you enter, how many pages you need
	 */
	protected static int calculatePages(int amount, int pageSize) {
		int res = (int) Math.ceil((double) amount / pageSize);
		if(res == 0)
			res = 1;
		return res;
	}

	public static SuperInventory getInventory(Player player) {
		for(SuperInventory inventory : guis)
			if(inventory.getOpener().equals(player))
				return inventory;

		return null;
	}
	
	public static ArrayList<SuperInventory> getGUIS() {
		return guis;
	}
}