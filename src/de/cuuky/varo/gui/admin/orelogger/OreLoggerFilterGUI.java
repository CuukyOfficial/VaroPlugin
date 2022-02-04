package de.cuuky.varo.gui.admin.orelogger;

import de.cuuky.cfw.hooking.hooks.chat.ChatHook;
import de.cuuky.cfw.hooking.hooks.chat.ChatHookHandler;
import de.cuuky.cfw.inventory.ItemClick;
import de.cuuky.cfw.utils.item.BuildItem;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.gui.VaroInventory;
import de.cuuky.varo.logger.logger.LoggedBlock;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class OreLoggerFilterGUI extends VaroInventory {

    private class Filter {

        private final String name;

        private String content;

        private final Predicate<String> contentChecker;

        private Filter(String name, Predicate<String> contentChecker) {
            this.name = name;
            this.contentChecker = contentChecker;
        }

        private ItemClick setFilter(VaroPlayer player, String message) {
            return click -> {
                if (click.isRightClick()) {
                    setContent(null);
                } else {
                    Main.getCuukyFrameWork().getHookManager().registerHook(
                        new ChatHook(player.getPlayer(), message, new ChatHookHandler() {
                            @Override
                            public boolean onChat(AsyncPlayerChatEvent event) {
                                String newContent = event.getMessage();

                                if (!Filter.this.contentChecker.test(newContent)) {
                                    String msg = ConfigMessages.LOGGER_FILTER_INVALID_FILTER.getValue(player)
                                        .replace("%filterName%", Filter.this.name)
                                        .replace("%content%", newContent);
                                    player.sendMessage(Main.getPrefix() + msg);
                                } else {
                                    String msg = ConfigMessages.LOGGER_FILTER_SET_FILTER.getValue(player)
                                        .replace("%filterName%", Filter.this.name)
                                        .replace("%newContent%", newContent)
                                        .replace("%oldContent%", Filter.this.getContent());
                                    player.sendMessage(Main.getPrefix() + msg);

                                    Filter.this.setContent(newContent);
                                }

                                OreLoggerFilterGUI.this.open();
                                return true;
                            }
                        })
                    );

                    OreLoggerFilterGUI.this.close();
                }
            };
        }

        private void setContent(String content) {
            this.content = content;
        }

        // Returns the first String in content readable
        private String getContent() {
            return this.content == null ? "/" : this.content;
        }

        private String getContentRaw() {
            return this.content;
        }
    }

    private final VaroPlayer player;

    private final Filter playerFilter = new Filter("Player Filter", this.validVaroPlayerName()),
        materialFilter = new Filter("Material Filter", this.validLoggerMaterialName());

    public OreLoggerFilterGUI(Player player) {
        super(Main.getCuukyFrameWork().getAdvancedInventoryManager(), player);
        this.player = VaroPlayer.getPlayer(player);
    }

    @Override
    public String getTitle() {
        return "§6OreLogger";
    }

    @Override
    public int getSize() {
        return 9 * 4;
    }

    @Override
    public void refreshContent() {
        // Filter player
        int base = 11;
        addItem(base, new BuildItem().material(Materials.SIGN).displayName(
                    Main.getColorCode() + "Filter Player " + ChatColor.GRAY + "(" + this.playerFilter.getContent() + ")")
                .lore("§7Right-Click to reset").build(),
            this.playerFilter.setFilter(this.player,
                Main.getPrefix() + ConfigMessages.LOGGER_FILTER_PLAYER_FILTER_MESSAGE.getValue(this.player))
        );
        
        // Filter material
        addItem(base + 4, new BuildItem().material(Materials.SIGN).displayName(
                    Main.getColorCode() + "Filter Material" + ChatColor.GRAY + " (" + this.materialFilter.getContent() + ")")
            .lore("§7Right-Click to reset").build(),
            this.materialFilter.setFilter(this.player,
                Main.getPrefix() + ConfigMessages.LOGGER_FILTER_MATERIAL_FILTER_MESSAGE.getValue(this.player)));

        // Open
        addItem(base + 2, new BuildItem().material(Materials.EMERALD).displayName(Main.getColorCode() + "Open").build(),
            click -> OreLoggerFilterGUI.this.openFiltered(this.playerFilter.getContentRaw(),
                this.materialFilter.getContentRaw())
        );
    }

    private void openFiltered(String player, String material) {
        Predicate<LoggedBlock> filter = block -> (player == null || block.getName().equals(player)) &&
            (material == null || block.getMaterial().equals(material));

        List<LoggedBlock> blocks = Main.getDataManager().getVaroLoggerManager().getBlockLogger().getLogs().stream()
            .filter(filter).collect(Collectors.toList());

        this.openNext(new OreLoggerListGUI(this.getPlayer(), blocks));
    }

    private Predicate<String> validVaroPlayerName() {
        return str -> VaroPlayer.getVaroPlayers().stream().anyMatch(player -> player.getName().equals(str));
    }

    private Predicate<String> validLoggerMaterialName() {
        return str -> Main.getDataManager().getListManager().getDestroyedBlocks().getItems().stream()
            .anyMatch(item -> item.getType().toString().equals(str));
    }
}
