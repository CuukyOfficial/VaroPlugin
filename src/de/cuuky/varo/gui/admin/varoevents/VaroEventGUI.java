package de.cuuky.varo.gui.admin.varoevents;

import java.util.Arrays;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.Main;
import de.cuuky.varo.event.VaroEvent;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.gui.VaroListInventory;
import de.varoplugin.cfw.inventory.ItemClick;
import de.varoplugin.cfw.item.ItemBuilder;
import de.varoplugin.cfw.utils.JavaUtils;

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
        return ItemBuilder.material(event.getIcon()).displayName(event.getEventType().getName())
                .lore(JavaUtils.combineArrays(new String[]{"§7Enabled: " + (event.isEnabled() ? "§a" : "§c") + event.isEnabled(), ""},
                        Arrays.stream(event.getDescription().split("\n")).map(s -> "§7" + s).toArray(String[]::new))).build();
    }

    @Override
    protected ItemClick getClick(VaroEvent event) {
        return (e) -> {
            if (Main.getVaroGame().getGameState() != GameState.STARTED) {
                getPlayer().sendMessage(Main.getPrefix() + "Spiel wurde noch nicht gestartet!");
                return;
            }

            event.setEnabled(!event.isEnabled());
        };
    }
}