package de.cuuky.varo.gui.admin.varoevents;

import de.cuuky.cfw.inventory.ItemClick;
import de.cuuky.cfw.utils.JavaUtils;
import de.cuuky.cfw.utils.item.BuildItem;
import de.cuuky.varo.app.Main;
import de.cuuky.varo.event.VaroEvent;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.gui.VaroListInventory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class VaroEventGUI extends VaroListInventory<VaroEventType> {

    public VaroEventGUI(Player player) {
        super(Main.getCuukyFrameWork().getAdvancedInventoryManager(), player, Arrays.asList(VaroEventType.values()));
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
    protected ItemStack getItemStack(VaroEventType type) {
        boolean enabled = VaroEvent.getEvent(type).isEnabled();
        return new BuildItem().displayName(type.getName())
                .itemstack(new ItemStack(type.getMaterial()))
                .lore(JavaUtils.combineArrays(new String[]{"§7Enabled: " + (enabled ? "§a" : "§c") + enabled, ""},
                        JavaUtils.addIntoEvery(type.getDescription().split("\n"), "§7", true))).build();
    }

    @Override
    protected ItemClick getClick(VaroEventType type) {
        return (e) -> {
            if (Main.getVaroGame().getGameState() != GameState.STARTED) {
                getPlayer().sendMessage(Main.getPrefix() + "Spiel wurde noch nicht gestartet!");
                return;
            }


            event.setEnabled(!event.isEnabled());
        };
    }
}