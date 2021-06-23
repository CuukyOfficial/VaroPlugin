package de.cuuky.varo.gui.player.option;

import de.cuuky.cfw.inventory.page.InventoryPage;
import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.Stats;
import de.cuuky.varo.gui.player.PlayerOptionsGUI;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class OptionsPage2 implements InventoryPage<PlayerOptionsGUI> {

    private PlayerOptionsGUI gui;
    private VaroPlayer target;
    private Stats stats;

    public OptionsPage2(PlayerOptionsGUI gui, VaroPlayer target) {
        this.gui = gui;
        this.target = target;
        this.stats = target.getStats();
    }

    @Override
    public PlayerOptionsGUI getInventory() {
        return this.gui;
    }

    @Override
    public void refreshContent() {
        addItem(4, new ItemBuilder().displayname("§5EpisodesPlayed").itemstack(new ItemStack(Material.BLAZE_POWDER)).lore(new String[]{"§7Current: " + stats.getSessionsPlayed()}).build());
        addItem(13, new ItemBuilder().displayname("§6Countdown").itemstack(new ItemStack(Materials.SIGN.parseMaterial())).lore(new String[]{"§7Current: " + stats.getCountdown()}).build());

        addItem(36, new ItemBuilder().displayname("§7Change §cWill InventoryClear").itemstack(new ItemStack(Material.ARROW)).lore(new String[]{"§7Current: " + stats.isWillClear()}).build());
        addItem(37, new ItemBuilder().displayname("§7Change §6State").itemstack(new ItemStack(Material.GOLDEN_APPLE)).lore(new String[]{"§7Current: " + stats.getState().getName()}).build());
        addItem(38, new ItemBuilder().displayname("§7Remove §cTimeUntilAddSession").itemstack(new ItemStack(Materials.PAPER.parseMaterial())).lore(new String[]{"§7Current: " + (stats.getTimeUntilAddSession())}).build());

        addItem(8, new ItemBuilder().displayname("§aAdd EpisodesPlayed").itemstack(new ItemStack(Material.APPLE)).build());
        addItem(0, new ItemBuilder().displayname("§cRemove EpisodesPlayed").itemstack(Materials.REDSTONE.parseItem()).build());

        addItem(17, new ItemBuilder().displayname("§aReset Countdown").itemstack(new ItemStack(Material.APPLE)).build());
        addItem(9, new ItemBuilder().displayname("§cRemove Countdown").itemstack(Materials.REDSTONE.parseItem()).build());
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