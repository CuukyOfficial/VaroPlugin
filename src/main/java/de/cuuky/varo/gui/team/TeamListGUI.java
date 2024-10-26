package de.cuuky.varo.gui.team;

import java.util.List;
import java.util.function.Supplier;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.cryptomorin.xseries.XMaterial;

import de.cuuky.varo.Main;
import de.cuuky.varo.gui.VaroListInventory;
import de.cuuky.varo.team.VaroTeam;
import de.varoplugin.cfw.inventory.ItemClick;
import de.varoplugin.cfw.item.ItemBuilder;

public class TeamListGUI extends VaroListInventory<VaroTeam> {

    public enum TeamGUIType {
        ALIVE("§aALIVE", XMaterial.POTION, VaroTeam::getAliveTeams),
        DEAD("§4DEAD", XMaterial.REDSTONE, VaroTeam::getDeadTeams),
        ONLINE("§eONLINE", XMaterial.EMERALD, VaroTeam::getOnlineTeams),
        REGISTERED("§bREGISTERED", XMaterial.BOOK, VaroTeam::getTeams);

        private final XMaterial icon;
        private final String typeName;
        private final Supplier<List<VaroTeam>> teamSupplier;

        TeamGUIType(String typeName, XMaterial icon, Supplier<List<VaroTeam>> teamSupplier) {
            this.typeName = typeName;
            this.icon = icon;
            this.teamSupplier = teamSupplier;
        }

        public XMaterial getIcon() {
            return icon;
        }

        public List<VaroTeam> getList() {
            return this.teamSupplier.get();
        }

        public String getTypeName() {
            return typeName;
        }
    }

    public TeamListGUI(Player player, TeamGUIType type) {
        super(Main.getInventoryManager(), player, type.getList());
    }

    @Override
    protected ItemStack getItemStack(VaroTeam team) {
        return ItemBuilder.material(XMaterial.DIAMOND_HELMET).displayName((team.getColorCode() == null ? Main.getColorCode() : "") + team.getDisplay()).build();
    }

    @Override
    protected ItemClick getClick(VaroTeam team) {
        return (event) -> {
            if (getPlayer().hasPermission("varo.admin")) this.openNext(new TeamGUI(getPlayer(), team));
        };
    }

    @Override
    public String getTitle() {
        return "§3Choose Team";
    }

    @Override
    public int getSize() {
        return this.getRecommendedSize();
    }
}