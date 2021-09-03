package de.cuuky.varo.gui.admin.varoevents;

import de.cuuky.cfw.inventory.ItemClick;
import de.cuuky.cfw.utils.JavaUtils;
import de.cuuky.cfw.utils.item.BuildItem;
import de.cuuky.varo.Main;
import de.cuuky.varo.event.VaroEvent;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.gui.VaroListInventory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class VaroEventGUI extends VaroListInventory<VaroEvent> {

    public VaroEventGUI(Player player) {
        super(Main.getCuukyFrameWork().getAdvancedInventoryManager(), player, VaroEvent.getEvents());
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
        return new BuildItem().displayName(event.getEventType().getName())
                .itemstack(new ItemStack(event.getIcon()))
                .lore(JavaUtils.combineArrays(new String[]{"§7Enabled: " + (event.isEnabled() ? "§a" : "§c") + event.isEnabled(), ""},
                        JavaUtils.addIntoEvery(event.getDescription().split("\n"), "§7", true))).build();
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