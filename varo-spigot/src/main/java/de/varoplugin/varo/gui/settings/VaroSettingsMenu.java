package de.varoplugin.varo.gui.settings;

import org.bukkit.entity.Player;

import com.cryptomorin.xseries.XMaterial;

import de.varoplugin.cfw.item.ItemBuilder;
import de.varoplugin.varo.Main;
import de.varoplugin.varo.gui.VaroInventory;
import de.varoplugin.varo.player.VaroPlayer;

public class VaroSettingsMenu extends VaroInventory {

    private final VaroPlayer player;

    public VaroSettingsMenu(Player player) {
        super(Main.getInventoryManager(), player);

        this.player = VaroPlayer.getPlayer(player);
    }

    private String stringify(Object obj) {
        return obj != null ? obj.toString() : "/";
    }

    @Override
    public String getTitle() {
        return Main.getColorCode() + "Settings";
    }

    @Override
    public int getSize() {
        return 36;
    }

    @Override
    public void refreshContent() {
        this.addItem(this.getCenter() - 2, ItemBuilder.material(XMaterial.LIME_DYE).displayName("§fColor")
                .lore(Main.getColorCode() + this.stringify(this.player.getGuiFiller()), "", "§7Right click to remove").build(), (event) -> {
            if (event.isRightClick()) this.player.setGuiFiller(null);
            else
                this.openNext(new VaroColorMenu(getManager(), getPlayer(), this.player::setGuiFiller));
        });

        this.addItem(this.getCenter(), ItemBuilder.material(XMaterial.NOTE_BLOCK).displayName("§fSound")
                .lore(Main.getColorCode() + this.stringify(this.player.getGuiSound()), "", "§7Right click to remove").build(), (event) -> {
            if (event.isRightClick()) this.player.setGuiSound(null);
            else this.openNext(new VaroSoundMenu(getManager(), getPlayer(), this.player::setGuiSound));
        });

        this.addItem(this.getCenter() + 2, ItemBuilder.material(XMaterial.EMERALD).displayName("§fAnimation")
                .lore(Main.getColorCode() + this.player.hasGuiAnimation()).build(),
                (event) -> this.player.setGuiAnimation(!this.player.hasGuiAnimation()));
    }
}