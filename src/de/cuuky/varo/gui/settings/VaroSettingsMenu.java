package de.cuuky.varo.gui.settings;

import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.gui.VaroInventory;
import org.bukkit.entity.Player;

public class VaroSettingsMenu extends VaroInventory {

    private final VaroPlayer player;

    public VaroSettingsMenu(Player player) {
        super(Main.getCuukyFrameWork().getAdvancedInventoryManager(), player);

        this.player = VaroPlayer.getPlayer(player);
    }

    private String stringify(Object obj) {
        return obj != null ? obj.toString() : "/";
    }

    public String getTitle() {
        return Main.getColorCode() + "Settings";
    }

    @Override
    public int getSize() {
        return 36;
    }

    @Override
    public void refreshContent() {
        this.addItem(this.getCenter() - 2, new ItemBuilder().displayname("§fColor")
                .lore(Main.getColorCode() + this.stringify(this.player.getGuiFiller()), "", "§7Right click to remove")
                .itemstack(Materials.LIME_DYE.parseItem()).build(), (event) -> {
            if (event.isRightClick()) this.player.setGuiFiller(null);
            else
                this.openNext(new VaroColorMenu(getManager(), getPlayer(), (color) -> this.player.setGuiFiller(color)));
        });

        this.addItem(this.getCenter(), new ItemBuilder().displayname("§fSound")
                .lore(Main.getColorCode() + this.stringify(this.player.getGuiSound()), "", "§7Right click to remove")
                .itemstack(Materials.NOTE_BLOCK.parseItem()).build(), (event) -> {
            if (event.isRightClick()) this.player.setGuiSound(null);
            else this.openNext(new VaroSoundMenu(getManager(), getPlayer(), (sound) -> this.player.setGuiSound(sound)));
        });

        this.addItem(this.getCenter() + 2, new ItemBuilder().displayname("§fAnimation")
                        .material(Materials.EMERALD.parseMaterial()).lore(Main.getColorCode() + this.player.hasGuiAnimation()).build(),
                (event) -> this.player.setGuiAnimation(!this.player.hasGuiAnimation()));
    }
}