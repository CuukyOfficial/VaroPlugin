package de.cuuky.varo.gui.admin.alert;

import de.cuuky.cfw.inventory.ItemClick;
import de.cuuky.cfw.utils.item.BuildItem;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.app.Main;
import de.cuuky.varo.Alert;
import de.cuuky.varo.gui.VaroListInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.function.Supplier;

public class AlertChooseGUI extends VaroListInventory<Alert> {

    public enum AlertGUIType {

        ALL("§fALL", Material.BOOK, Alert::getAlerts),
        CLOSED("§4CLOSED", Materials.SKELETON_SKULL.parseMaterial(), Alert::getClosedAlerts),
        OPEN("§eOPENED", Material.EMERALD, Alert::getOpenAlerts);

        private Material icon;
        private String typeName;
        private Supplier<List<Alert>> alertSupplier;

        AlertGUIType(String typeName, Material icon, Supplier<List<Alert>> alertSupplier) {
            this.typeName = typeName;
            this.icon = icon;
            this.alertSupplier = alertSupplier;
        }

        public Material getIcon() {
            return icon;
        }

        public List<Alert> getList() {
            return this.alertSupplier.get();
        }

        public String getTypeName() {
            return typeName;
        }
    }

    public AlertChooseGUI(Player player, AlertGUIType type) {
        super(Main.getCuukyFrameWork().getAdvancedInventoryManager(), player, type.getList());
    }

    @Override
    protected ItemStack getItemStack(Alert alert) {
        return new BuildItem().displayName("§c" + alert.getType() + " §8| §7" + alert.getId())
                .itemstack(new ItemStack(Material.BOOK))
                .lore(new String[]{"§7Message: §f" + alert.getMessage(), "§7Date: §f" + new SimpleDateFormat("dd.MM.yyy HH:mm:ss")
                        .format(alert.getCreated()), "§7Open: §f" + alert.isOpen()}).build();
    }

    @Override
    protected ItemClick getClick(Alert alert) {
        return (event) -> this.openNext(new AlertOptionGUI(getPlayer(), alert));
    }

    @Override
    public String getTitle() {
        return "§4Alerts";
    }

    @Override
    public int getSize() {
        return this.getRecommendedSize();
    }

    @Override
    public void refreshContent() {
        super.refreshContent();
        addItem(getSize() - 1, new BuildItem().displayName("§cClose all")
                        .itemstack(new ItemStack(Materials.REDSTONE.parseMaterial())).build(),
                (event) -> this.getList().forEach(alert -> alert.setOpen(false)));
    }
}