package de.cuuky.varo.gui.player.option;

import de.cuuky.cfw.inventory.page.InventoryPage;
import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.Stats;
import de.cuuky.varo.gui.player.PlayerOptionsGUI;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class OptionsPage1 implements InventoryPage<PlayerOptionsGUI> {

    private PlayerOptionsGUI gui;
    private VaroPlayer target;
    private Stats stats;

    public OptionsPage1(PlayerOptionsGUI gui, VaroPlayer target) {
        this.gui = gui;
        this.target = target;
        this.stats = target.getStats();
    }

    @Override
    public PlayerOptionsGUI getInventory() {
        return gui;
    }

    @Override
    public void refreshContent() {
        addItem(4, new ItemBuilder().displayname("§cKills").itemstack(new ItemStack(Material.DIAMOND_SWORD)).lore(new String[]{"§7Current: " + stats.getKills()}).build());
        addItem(13, new ItemBuilder().displayname("§6Rank").itemstack(new ItemStack(Materials.SIGN.parseMaterial())).lore(new String[]{"§7Current: " + (target.getRank() == null ? "/" : target.getRank().getDisplay())}).build());
        addItem(22, new ItemBuilder().displayname("§5YouTube-Link").itemstack(new ItemStack(Materials.PAPER.parseMaterial())).lore(new String[]{"§7Current: " + stats.getYoutubeLink()}).build());
        addItem(31, new ItemBuilder().displayname("§bSessions").itemstack(new ItemStack(Material.DIAMOND)).lore(new String[]{"§7Current: " + stats.getSessions()}).build());

        addItem(8, new ItemBuilder().displayname("§aAdd Kill").itemstack(new ItemStack(Material.APPLE)).build());
        addItem(0, new ItemBuilder().displayname("§cRemove Kill").itemstack(Materials.REDSTONE.parseItem()).build());

        addItem(17, new ItemBuilder().displayname("§aSet Rank").itemstack(new ItemStack(Material.APPLE)).build());
        addItem(9, new ItemBuilder().displayname("§cRemove Rank").itemstack(Materials.REDSTONE.parseItem()).build());

        addItem(26, new ItemBuilder().displayname("§aSet Link").itemstack(new ItemStack(Material.APPLE)).build());
        addItem(18, new ItemBuilder().displayname("§cRemove Link").itemstack(Materials.REDSTONE.parseItem()).build());

        addItem(35, new ItemBuilder().displayname("§aAdd Session").itemstack(new ItemStack(Material.APPLE)).build());
        addItem(27, new ItemBuilder().displayname("§cRemove Session").itemstack(Materials.REDSTONE.parseItem()).build());
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public String getTitle() {
        return null;
    }
}