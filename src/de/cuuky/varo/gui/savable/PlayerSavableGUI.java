package de.cuuky.varo.gui.savable;

import de.cuuky.cfw.utils.item.BuildItem;
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
        return 36;
    }

    @Override
    public void refreshContent() {
        if (getPlayer().hasPermission("varo.setup"))
            addItem(this.getUsableSize(), new BuildItem().displayName("§bTeleport").material(Materials.ENDER_PEARL).build(),
                    (event) -> getPlayer().teleport(savable.getBlock().getLocation()));

        addItem(this.getCenter(), new BuildItem().displayName("§cDelete").material(Materials.ROSE_RED).build(), (event) -> {
            savable.remove();
            this.back();
        });
    }
}