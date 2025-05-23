package de.cuuky.varo.gui.player;

import java.util.List;
import java.util.function.Supplier;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.cryptomorin.xseries.XMaterial;

import de.cuuky.varo.Main;
import de.cuuky.varo.gui.VaroPlayerAsyncListInventory;
import de.cuuky.varo.player.VaroPlayer;
import de.varoplugin.cfw.inventory.ItemClick;
import de.varoplugin.cfw.item.ItemBuilder;

public class PlayerListGUI extends VaroPlayerAsyncListInventory<VaroPlayer> {

    public enum PlayerGUIType {

        ALIVE("§aALIVE", XMaterial.POTION, VaroPlayer::getAlivePlayer),
        DEAD("§4DEAD", XMaterial.SKELETON_SKULL, VaroPlayer::getDeadPlayer),
        ONLINE("§eONLINE", XMaterial.EMERALD, VaroPlayer::getOnlinePlayer),
        REGISTERED("§bREGISTERED", XMaterial.BOOK, VaroPlayer::getVaroPlayers),
        SPECTATOR("§fSPECTATOR", XMaterial.REDSTONE, VaroPlayer::getSpectator);

        private final XMaterial icon;
        private final String typeName;
        private final Supplier<List<VaroPlayer>> playerSupplier;

        PlayerGUIType(String typeName, XMaterial icon, Supplier<List<VaroPlayer>> playerSupplier) {
            this.typeName = typeName;
            this.icon = icon;
            this.playerSupplier = playerSupplier;
        }

        public XMaterial getIcon() {
            return icon;
        }

        public List<VaroPlayer> getList() {
            return this.playerSupplier.get();
        }

        public String getTypeName() {
            return typeName;
        }

        public static PlayerGUIType getType(String name) {
            for (PlayerGUIType type : values())
                if (type.getTypeName().equals(name))
                    return type;

            return null;
        }
    }

    private final boolean showStats;


    public PlayerListGUI(Player player, PlayerGUIType type) {
        super(Main.getInventoryManager(), player, type.getList());

        this.showStats = player.hasPermission("varo.viewStats");
    }

    @Override
    public String getTitle() {
        return "§cChoose Player";
    }

    @Override
    public int getSize() {
        return this.getRecommendedSize();
    }

    @Override
    protected ItemStack getItemStack(VaroPlayer player) {
        return ItemBuilder.skull(player.getName()).displayName("§3" + player.getName())
                .lore((showStats ? player.getStats().getStatsListed() : null)).build();
    }

    @Override
    protected ItemClick getClick(VaroPlayer player) {
        return (event) -> {
            if (getPlayer().hasPermission("varo.player")) this.openNext(new PlayerGUI(getPlayer(), player));
        };
    }
}