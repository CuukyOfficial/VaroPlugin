package de.cuuky.varo.gui.team;

import de.varoplugin.cfw.inventory.ItemClick;
import de.cuuky.cfw.utils.item.BuildItem;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.Main;
import de.cuuky.varo.entity.team.VaroTeam;
import de.cuuky.varo.gui.VaroListInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.Supplier;

public class TeamListGUI extends VaroListInventory<VaroTeam> {

    public enum TeamGUIType {
        ALIVE("§aALIVE", Material.POTION, VaroTeam::getAliveTeams),
        DEAD("§4DEAD", Materials.REDSTONE.parseMaterial(), VaroTeam::getDeadTeams),
        ONLINE("§eONLINE", Material.EMERALD, VaroTeam::getOnlineTeams),
        REGISTERED("§bREGISTERED", Material.BOOK, VaroTeam::getTeams);

        private final Material icon;
        private final String typeName;
        private final Supplier<List<VaroTeam>> teamSupplier;

        TeamGUIType(String typeName, Material icon, Supplier<List<VaroTeam>> teamSupplier) {
            this.typeName = typeName;
            this.icon = icon;
            this.teamSupplier = teamSupplier;
        }

        public Material getIcon() {
            return icon;
        }

        public List<VaroTeam> getList() {
            return this.teamSupplier.get();
        }

        public String getTypeName() {
            return typeName;
        }
    }

    private TeamGUIType type;

    public TeamListGUI(Player player, TeamGUIType type) {
        super(Main.getInventoryManager(), player, type.getList());

        this.type = type;
    }

    @Override
    protected ItemStack getItemStack(VaroTeam team) {
        return new BuildItem().displayName((team.getColorCode() == null ? Main.getColorCode() : "") + team.getDisplay()).material(Material.DIAMOND_HELMET).build();
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