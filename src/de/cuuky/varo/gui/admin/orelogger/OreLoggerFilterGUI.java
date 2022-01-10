package de.cuuky.varo.gui.admin.orelogger;

import de.cuuky.cfw.hooking.hooks.chat.ChatHook;
import de.cuuky.cfw.hooking.hooks.chat.ChatHookHandler;
import de.cuuky.cfw.inventory.ItemClick;
import de.cuuky.cfw.utils.item.BuildItem;
import de.cuuky.varo.Main;
import de.cuuky.varo.gui.VaroInventory;
import de.cuuky.varo.logger.logger.LoggedBlock;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class OreLoggerFilterGUI extends VaroInventory {

    private class Filter {

        private final String name;

        private String content;

        Filter(String name) {
            this.name = name;
        }

        ItemClick setFilter(Player player, String message) {
            return click -> {
                Main.getCuukyFrameWork().getHookManager().registerHook(
                        new ChatHook(player, message, new ChatHookHandler() {
                            @Override
                            public boolean onChat(AsyncPlayerChatEvent event) {
                                String newContent = event.getMessage();

                                player.sendMessage(Main.getPrefix() + ChatColor.GRAY + "Set " + Filter.this.name + " to " +
                                        Main.getColorCode() + newContent + ChatColor.GRAY + " (before: " + Filter.this.getContent() + ").");

                                Filter.this.setContent(newContent);

                                OreLoggerFilterGUI.this.open();
                                return true;
                            }
                        })
                );

                OreLoggerFilterGUI.this.close();
            };
        }

        ItemClick resetFilter(Player player) {
            return click -> {
                player.sendMessage(Main.getPrefix() + ChatColor.GRAY + "Reset " + this.name + " (before: " + Filter.this.getContent() + ").");

                setContent(null);
            };
        }

        public void setContent(String content) {
            this.content = content;
        }

        // Returns the first String in content readable
        String getContent() {
            return this.content == null ? "/" : this.content;
        }

        String getContentRaw() {
            return this.content;
        }
    }

    private final Filter playerFilter = new Filter("Player Filter"), materialFilter = new Filter("Material Filter");

    public OreLoggerFilterGUI(Player player) {
        super(Main.getCuukyFrameWork().getAdvancedInventoryManager(), player);
    }

    @Override
    public int getSize() {
        return 9 * 6;
    }

    @Override
    public void refreshContent() {
        // Filter player
        addItem(20, new BuildItem().material(Material.SIGN).displayName(Main.getColorCode() + "Filter Player " + ChatColor.GRAY + "(" + this.playerFilter.getContent() + ")").build(),
                this.playerFilter.setFilter(this.getPlayer(), Main.getPrefix() + "Please enter a player name to filter for:")
        );

        // Reset Player
        addItem(20 + 9, new BuildItem().material(Material.REDSTONE).displayName(ChatColor.RED + "Reset Player").build(),
                this.playerFilter.resetFilter(this.getPlayer())
        );

        // Filter material
        addItem(24, new BuildItem().material(Material.SIGN).displayName(Main.getColorCode() + "Filter Material" + ChatColor.GRAY + " (" + this.materialFilter.getContent() + ")").build(),
                this.materialFilter.setFilter(this.getPlayer(), Main.getPrefix() + "Please enter a material name to filter for:"));

        // Reset material
        addItem(24 + 9, new BuildItem().material(Material.REDSTONE).displayName(ChatColor.RED + "Reset Material").build(),
                this.materialFilter.resetFilter(this.getPlayer())
        );

        // Open
        addItem(22-9, new BuildItem().material(Material.EMERALD).displayName(Main.getColorCode() + "Open").build(),
                click -> OreLoggerFilterGUI.this.openFiltered(this.playerFilter.getContentRaw(), this.materialFilter.getContentRaw())
        );
    }

    private void openFiltered(String player, String material) {
        Predicate<LoggedBlock> filter = block -> (player == null || block.getName().equals(player)) && (material == null || block.getMaterial().equals(material));

        List<LoggedBlock> blocks = Main.getDataManager().getVaroLoggerManager().getBlockLogger().getLogs().stream()
                .filter(filter).collect(Collectors.toList());

        this.openNext(new OreLoggerListGUI(this.getPlayer(), blocks));
    }
}
