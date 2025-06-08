package de.varoplugin.varo.gui.settings;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.varoplugin.cfw.inventory.AdvancedInventoryManager;
import de.varoplugin.cfw.inventory.ItemClick;
import de.varoplugin.cfw.item.ItemBuilder;
import de.varoplugin.varo.gui.VaroListInventory;

public class VaroColorMenu extends VaroListInventory<VaroMenuColor> {

    private final Consumer<VaroMenuColor> chosenConsumer;

    public VaroColorMenu(AdvancedInventoryManager manager, Player player, Consumer<VaroMenuColor> chosenConsumer) {
        super(manager, player, Arrays.asList(VaroMenuColor.values()));

        this.chosenConsumer = chosenConsumer;
    }

    public VaroColorMenu(AdvancedInventoryManager manager, Player player, Consumer<VaroMenuColor> chosenConsumer, boolean onlyColorCodes) {
        super(manager, player, !onlyColorCodes ? Arrays.asList(VaroMenuColor.values()) :
                Arrays.stream(VaroMenuColor.values()).filter(varoMenuColor -> varoMenuColor.getColorCode() != null).collect(Collectors.toList()));

        this.chosenConsumer = chosenConsumer;
    }

    @Override
    public String getTitle() {
        return "§2Choose color";
    }

    @Override
    protected ItemStack getItemStack(int index, VaroMenuColor varoMenuColor) {
        return ItemBuilder.itemStack(varoMenuColor.getColorPane())
                .displayName((varoMenuColor.getColorCode() != null ? varoMenuColor.getColorCode() : "§f")
                        + varoMenuColor.name()).build();
    }

    @Override
    protected ItemClick getClick(VaroMenuColor varoMenuColor) {
        return (event) -> chosenConsumer.accept(varoMenuColor);
    }
}