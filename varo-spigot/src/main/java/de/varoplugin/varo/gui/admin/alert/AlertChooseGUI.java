package de.varoplugin.varo.gui.admin.alert;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.function.Supplier;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.cryptomorin.xseries.XMaterial;

import de.varoplugin.cfw.inventory.ItemClick;
import de.varoplugin.cfw.item.ItemBuilder;
import de.varoplugin.varo.Main;
import de.varoplugin.varo.alert.Alert;
import de.varoplugin.varo.gui.VaroListInventory;

public class AlertChooseGUI extends VaroListInventory<Alert> {

    public enum AlertGUIType {

        ALL("§fALL", XMaterial.BOOK.get(), Alert::getAlerts),
        CLOSED("§4CLOSED", XMaterial.SKELETON_SKULL.get(), Alert::getClosedAlerts),
        OPEN("§eOPENED", XMaterial.EMERALD.get(), Alert::getOpenAlerts);

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
        super(Main.getInventoryManager(), player, type.getList());
    }

    @Override
    protected ItemStack getItemStack(Alert alert) {
        return ItemBuilder.material(XMaterial.BOOK).displayName("§c" + alert.getType() + " §8| §7" + alert.getId())
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
        addItem(getSize() - 1, ItemBuilder.material(XMaterial.REDSTONE).displayName("§cClose all").build(),
                (event) -> this.getList().forEach(alert -> alert.setOpen(false)));
    }
}