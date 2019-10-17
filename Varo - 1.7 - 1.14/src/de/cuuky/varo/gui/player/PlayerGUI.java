package de.cuuky.varo.gui.player;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.gui.SuperInventory;
import de.cuuky.varo.gui.admin.inventory.InventoryBackupListGUI;
import de.cuuky.varo.gui.player.PlayerListGUI.PlayerGUIType;
import de.cuuky.varo.gui.saveable.PlayerSaveableChooseGUI;
import de.cuuky.varo.gui.utils.PageAction;
import de.cuuky.varo.item.ItemBuilder;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.utils.LocationFormatter;
import de.cuuky.varo.version.types.Materials;

public class PlayerGUI extends SuperInventory {

	private VaroPlayer target;
	private PlayerGUIType type;

	public PlayerGUI(Player opener, VaroPlayer target, PlayerGUIType type) {
		super("§2" + target.getName() + " §7(" + target.getId() + ")", opener, 27, false);

		this.target = target;
		this.type = type;

		open();
	}

	@Override
	public boolean onOpen() {
		// TODO: Strikes

		linkItemTo(1, new ItemBuilder().displayname("§aInventory Backups").itemstack(new ItemStack(Material.DIAMOND_CHESTPLATE)).lore("§7Click to see more options").build(), new Runnable() {

			@Override
			public void run() {
				new InventoryBackupListGUI(opener, target);
			}
		});

		linkItemTo(4, new ItemBuilder().displayname("§2Last Location").itemstack(new ItemStack(Materials.MAP.parseMaterial())).lore(new String[] { "§cClick to teleport", "§7" + (target.getStats().getLastLocation() != null ? new LocationFormatter("x, y, z in world").format(target.getStats().getLastLocation()) : "/") }).build(), new Runnable() {

			@Override
			public void run() {
				if(target.getStats().getLastLocation() == null)
					return;

				opener.teleport(target.getStats().getLastLocation());
			}
		});

		linkItemTo(7, new ItemBuilder().displayname("§eKisten/Öfen").itemstack(new ItemStack(Materials.REDSTONE.parseMaterial())).amount(getFixedSize(target.getStats().getSaveables().size())).build(), new Runnable() {

			@Override
			public void run() {
				new PlayerSaveableChooseGUI(opener, target);
			}
		});

		linkItemTo(11, new ItemBuilder().displayname("§4Remove").itemstack(new ItemStack(Materials.SKELETON_SKULL.parseMaterial())).build(), new Runnable() {

			@Override
			public void run() {
				target.delete();
				if(type != null)
					new PlayerListGUI(opener, true, type);
				else
					new PlayerListChooseGUI(opener, true);
			}
		});

		linkItemTo(15, new ItemBuilder().displayname("§cReset").itemstack(new ItemStack(Material.BUCKET)).build(), new Runnable() {

			@Override
			public void run() {
				if(target.isOnline())
					target.getPlayer().kickPlayer("§7You've been resetted.\n§cPlease join again.");

				target.getStats().loadDefaults();
				updateInventory();
			}
		});

		linkItemTo(22, new ItemBuilder().displayname("§5More Options").itemstack(new ItemStack(Material.BOOK)).lore(target.getStats().getStatsListed()).build(), new Runnable() {

			@Override
			public void run() {
				new PlayerOptionsGUI(opener, target, type);
			}
		});

		return true;
	}

	@Override
	public void onClick(InventoryClickEvent event) {}

	@Override
	public void onInventoryAction(PageAction action) {}

	@Override
	public boolean onBackClick() {
		if(type != null)
			new PlayerListGUI(opener, true, type);
		else
			new PlayerListChooseGUI(opener, true);
		return true;
	}

	@Override
	public void onClose(InventoryCloseEvent event) {}
}
