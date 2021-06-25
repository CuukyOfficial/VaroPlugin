package de.cuuky.varo.gui.settings;

import de.cuuky.cfw.inventory.AdvancedInventoryManager;
import de.cuuky.cfw.inventory.ItemClick;
import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.gui.VaroListInventory;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.function.Consumer;

public class VaroSoundMenu extends VaroListInventory<Sound> {

    private final Consumer<Sound> chosenConsumer;

    public VaroSoundMenu(AdvancedInventoryManager manager, Player player, Consumer<Sound> chosenConsumer) {
        super(manager, player, Arrays.asList(Sound.values()));

        this.chosenConsumer = chosenConsumer;
    }

    @Override
    public String getTitle() {
        return "§2Choose sound";
    }

    @Override
    protected ItemStack getItemStack(Sound sound) {
        return new ItemBuilder().itemstack(Materials.NOTE_BLOCK.parseItem()).displayname("§f" + sound.name()).build();
    }

    @Override
    protected ItemClick getClick(Sound sound) {
        return (event) -> chosenConsumer.accept(sound);
    }
}