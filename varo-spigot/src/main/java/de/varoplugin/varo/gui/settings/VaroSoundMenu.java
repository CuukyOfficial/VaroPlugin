package de.varoplugin.varo.gui.settings;

import java.util.Arrays;
import java.util.function.Consumer;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.cryptomorin.xseries.XMaterial;

import de.varoplugin.cfw.inventory.AdvancedInventoryManager;
import de.varoplugin.cfw.inventory.ItemClick;
import de.varoplugin.cfw.item.ItemBuilder;
import de.varoplugin.varo.gui.VaroListInventory;

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
        return ItemBuilder.material(XMaterial.NOTE_BLOCK).displayName("§f" + sound.name()).build();
    }

    @Override
    protected ItemClick getClick(Sound sound) {
        return (event) -> chosenConsumer.accept(sound);
    }
}