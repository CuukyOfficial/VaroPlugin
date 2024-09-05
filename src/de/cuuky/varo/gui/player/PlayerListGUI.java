package de.cuuky.varo.gui.player;

import de.varoplugin.cfw.inventory.ItemClick;
import de.cuuky.cfw.utils.item.BuildSkull;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.gui.VaroAsyncListInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.Supplier;

public class PlayerListGUI extends VaroAsyncListInventory<VaroPlayer> {

    public enum PlayerGUIType {

        ALIVE("§aALIVE", Material.POTION, VaroPlayer::getAlivePlayer),
        DEAD("§4DEAD", Materials.SKELETON_SKULL_17.parseMaterial(), VaroPlayer::getDeadPlayer),
        ONLINE("§eONLINE", Material.EMERALD, VaroPlayer::getOnlinePlayer),
        REGISTERED("§bREGISTERED", Material.BOOK, VaroPlayer::getVaroPlayers),
        SPECTATOR("§fSPECTATOR", Materials.REDSTONE.parseMaterial(), VaroPlayer::getSpectator);

        private final Material icon;
        private final String typeName;
        private final Supplier<List<VaroPlayer>> playerSupplier;

        PlayerGUIType(String typeName, Material icon, Supplier<List<VaroPlayer>> playerSupplier) {
            this.typeName = typeName;
            this.icon = icon;
            this.playerSupplier = playerSupplier;
        }

        public Material getIcon() {
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

    private final PlayerGUIType type;

    public PlayerListGUI(Player player, PlayerGUIType type) {
        super(Main.getInventoryManager(), player, type.getList());

        this.showStats = player.hasPermission("varo.viewStats");
        this.type = type;
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
        return new BuildSkull().displayName(Main.getColorCode() + player.getName())
                .player(player.getName()).lore((showStats ? player.getStats().getStatsListed() : null)).build();
    }

    @Override
    protected ItemClick getClick(VaroPlayer player) {
        return (event) -> {
            if (getPlayer().hasPermission("varo.player")) this.openNext(new PlayerGUI(getPlayer(), player));
        };
    }
}