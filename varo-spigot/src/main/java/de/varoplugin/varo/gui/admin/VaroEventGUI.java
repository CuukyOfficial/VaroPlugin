package de.varoplugin.varo.gui.admin;

import java.util.Arrays;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.varoplugin.cfw.inventory.ItemClick;
import de.varoplugin.cfw.item.ItemBuilder;
import de.varoplugin.cfw.utils.JavaUtils;
import de.varoplugin.varo.Main;
import de.varoplugin.varo.event.VaroEvent;
import de.varoplugin.varo.gui.VaroListInventory;

public class VaroEventGUI extends VaroListInventory<VaroEvent> {

    public VaroEventGUI(Player player) {
        super(Main.getInventoryManager(), player, VaroEvent.getEvents());
    }

    @Override
    public String getTitle() {
        return "§5VaroEvents";
    }

    @Override
    public int getSize() {
        return this.getRecommendedSize();
    }

    @Override
    protected ItemStack getItemStack(VaroEvent event) {
        return ItemBuilder.material(event.getIcon()).displayName(event.getEventType().getName()).deleteDamageAnnotation()
                .lore(JavaUtils.combineArrays(new String[]{"§7Enabled: " + (event.isEnabled() ? "§a" : "§c") + event.isEnabled(), ""},
                        Arrays.stream(event.getDescription().split("\n")).map(s -> "§7" + s).toArray(String[]::new))).build();
    }

    @Override
    protected ItemClick getClick(VaroEvent event) {
        return (e) -> {
            if (!Main.getVaroGame().isRunning()) {
                getPlayer().sendMessage(Main.getPrefix() + "Spiel wurde noch nicht gestartet!");
                return;
            }

            event.setEnabled(!event.isEnabled());
        };
    }
}