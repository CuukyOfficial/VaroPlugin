package de.cuuky.varo.gui.saveable;

import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.stats.stat.inventory.VaroSaveable;
import de.cuuky.varo.gui.VaroInventory;
import org.bukkit.entity.Player;

public class PlayerSavableGUI extends VaroInventory {

    private final VaroSaveable savable;

    public PlayerSavableGUI(Player player, VaroSaveable savable) {
        super(Main.getCuukyFrameWork().getAdvancedInventoryManager(), player);

        this.savable = savable;
    }

    @Override
    public String getTitle() {
        return "§7Savable §e" + savable.getId();
    }

    @Override
    public int getSize() {
        return 18;
    }

    @Override
    public void refreshContent() {
        addItem(1, new ItemBuilder().displayname("§cDelete").itemstack(Materials.REDSTONE.parseItem()).build(), (event) -> {
            savable.remove();
            this.back();
        });
    }
}