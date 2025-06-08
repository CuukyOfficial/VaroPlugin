package de.varoplugin.varo.gui.settings;

import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;

import de.varoplugin.cfw.inventory.AdvancedInventoryManager;
import de.varoplugin.cfw.inventory.ItemClick;
import de.varoplugin.cfw.item.ItemBuilder;
import de.varoplugin.varo.gui.VaroListInventory;

public class VaroSoundMenu extends VaroListInventory<XSound> {

    private final Consumer<XSound> chosenConsumer;

    public VaroSoundMenu(AdvancedInventoryManager manager, Player player, Consumer<XSound> chosenConsumer) {
        super(manager, player, XSound.getValues().stream().filter(XSound::isSupported).collect(Collectors.toList()));

        this.chosenConsumer = chosenConsumer;
    }

    @Override
    public String getTitle() {
        return "§2Choose sound";
    }

    @Override
    protected ItemStack getItemStack(int index, XSound sound) {
        return ItemBuilder.material(XMaterial.NOTE_BLOCK).displayName("§f" + sound.name()).build();
    }

    @Override
    protected ItemClick getClick(XSound sound) {
        return (event) -> this.chosenConsumer.accept(sound);
    }
}