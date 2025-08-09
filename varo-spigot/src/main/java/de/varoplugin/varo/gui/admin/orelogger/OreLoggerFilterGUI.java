package de.varoplugin.varo.gui.admin.orelogger;

import java.util.List;
import java.util.function.Predicate;

import io.github.almightysatan.slams.Placeholder;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.cryptomorin.xseries.XMaterial;

import de.varoplugin.cfw.inventory.ItemClick;
import de.varoplugin.cfw.item.ItemBuilder;
import de.varoplugin.cfw.player.hook.chat.ChatHookTriggerEvent;
import de.varoplugin.cfw.player.hook.chat.PlayerChatHookBuilder;
import de.varoplugin.varo.Main;
import de.varoplugin.varo.config.language.Messages;
import de.varoplugin.varo.config.language.Messages.VaroMessage;
import de.varoplugin.varo.gui.VaroInventory;
import de.varoplugin.varo.logger.logger.LoggedBlock;
import de.varoplugin.varo.player.VaroPlayer;
import io.github.almightysatan.slams.PlaceholderResolver;

public class OreLoggerFilterGUI extends VaroInventory {

    private class Filter {

        private final String name;

        private String content;

        private final Predicate<String> contentChecker;

        private Filter(String name, Predicate<String> contentChecker) {
            this.name = name;
            this.contentChecker = contentChecker;
        }

        private ItemClick setFilter(VaroPlayer player, VaroMessage message) {
            return click -> {
                if (click.isRightClick()) {
                    Messages.BLOCKLOGGER_FILTER_RESET.send(player, Placeholder.constant("filter-name", Filter.this.name));
                    setContent(null);
                } else {
                    new PlayerChatHookBuilder().message(message.value(player)).subscribe(ChatHookTriggerEvent.class, hookEvent -> {
                        String newContent = hookEvent.getMessage();

                        if (!Filter.this.contentChecker.test(newContent)) {
                            Messages.BLOCKLOGGER_FILTER_INVALID.send(player, PlaceholderResolver.builder()
                                    .constant("filter-name", Filter.this.name).constant("filter-content", newContent).build());
                        } else {
                            Messages.BLOCKLOGGER_FILTER_SET.send(player, PlaceholderResolver.builder()
                                    .constant("filter-name", Filter.this.name).constant("filter-content", newContent).build());
                            Filter.this.setContent(newContent);
                        }

                        OreLoggerFilterGUI.this.open();
                        hookEvent.getHook().unregister();
                    }).complete(player.getPlayer(), Main.getInstance());

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
        super(Main.getInventoryManager(), player);
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
        addItem(base, ItemBuilder.material(XMaterial.OAK_SIGN).displayName(
                    Main.getColorCode() + "Filter Player " + ChatColor.GRAY + "(" + this.playerFilter.getContent() + ")")
                .lore("§7Right-Click to reset").build(),
            this.playerFilter.setFilter(this.player, Messages.BLOCKLOGGER_FILTER_PLAYER)
        );
        
        // Filter material
        addItem(base + 4, ItemBuilder.material(XMaterial.OAK_SIGN).displayName(
                    Main.getColorCode() + "Filter Material" + ChatColor.GRAY + " (" + this.materialFilter.getContent() + ")")
            .lore("§7Right-Click to reset").build(),
            this.materialFilter.setFilter(this.player, Messages.BLOCKLOGGER_FILTER_MATERIAL));

        // Open
        addItem(base + 2, ItemBuilder.material(XMaterial.EMERALD).displayName(Main.getColorCode() + "Open").build(),
            click -> OreLoggerFilterGUI.this.openFiltered(this.playerFilter.getContentRaw(),
                this.materialFilter.getContentRaw())
        );
    }

    private void openFiltered(String player, String material) {
        List<LoggedBlock> blocks;
        if (player == null && material == null)
            blocks = Main.getDataManager().getVaroLoggerManager().getBlockLogger().getLogs();
        else if (player == null)
            blocks = Main.getDataManager().getVaroLoggerManager().getBlockLogger().getMaterialLogs(material);
        else if (material == null)
            blocks = Main.getDataManager().getVaroLoggerManager().getBlockLogger().getNameLogs(player);
        else
            blocks = Main.getDataManager().getVaroLoggerManager().getBlockLogger().getNameMaterialLogs(player, material);

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
