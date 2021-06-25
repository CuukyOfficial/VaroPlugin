package de.cuuky.varo.gui.settings;

import de.cuuky.cfw.inventory.AdvancedInventoryManager;
import de.cuuky.cfw.inventory.ItemClick;
import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.varo.gui.VaroListInventory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.function.Consumer;

public class VaroColorMenu extends VaroListInventory<VaroMenuColor> {

    private final Consumer<VaroMenuColor> chosenConsumer;

    public VaroColorMenu(AdvancedInventoryManager manager, Player player, Consumer<VaroMenuColor> chosenConsumer) {
        super(manager, player, Arrays.asList(VaroMenuColor.values()));

        this.chosenConsumer = chosenConsumer;
    }

    @Override
    public String getTitle() {
        return "§2Choose color";
    }

    @Override
    protected ItemStack getItemStack(VaroMenuColor varoMenuColor) {
        return new ItemBuilder().itemstack(varoMenuColor.getColorPane()).displayname("§f" + varoMenuColor.name()).build();
    }

    @Override
    protected ItemClick getClick(VaroMenuColor varoMenuColor) {
        return (event) -> chosenConsumer.accept(varoMenuColor);
    }
}