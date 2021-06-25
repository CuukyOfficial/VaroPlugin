package de.cuuky.varo.gui.settings;

import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.serialize.identifier.VaroSerializeField;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;
import org.bukkit.inventory.ItemStack;

public enum VaroMenuColor implements VaroSerializeable {

    @VaroSerializeField(enumValue = "GRAY")
    GRAY(Materials.GRAY_STAINED_GLASS_PANE.parseItem()),

    @VaroSerializeField(enumValue = "WHITE")
    WHITE(Materials.WHITE_STAINED_GLASS_PANE.parseItem()),

    @VaroSerializeField(enumValue = "BROWN")
    BROWN(Materials.BROWN_STAINED_GLASS_PANE.parseItem()),

    @VaroSerializeField(enumValue = "BLACK")
    BLACK(Materials.BLACK_STAINED_GLASS_PANE.parseItem()),

    @VaroSerializeField(enumValue = "RED")
    RED(Materials.RED_STAINED_GLASS_PANE.parseItem()),

    @VaroSerializeField(enumValue = "GREEN")
    GREEN(Materials.GREEN_STAINED_GLASS_PANE.parseItem()),

    @VaroSerializeField(enumValue = "BLUE")
    BLUE(Materials.BLUE_STAINED_GLASS_PANE.parseItem()),

    @VaroSerializeField(enumValue = "YELLOW")
    YELLOW(Materials.YELLOW_STAINED_GLASS_PANE.parseItem()),

    @VaroSerializeField(enumValue = "MAGENTA")
    MAGENTA(Materials.MAGENTA_STAINED_GLASS_PANE.parseItem()),

    @VaroSerializeField(enumValue = "ORANGE")
    ORANGE(Materials.ORANGE_STAINED_GLASS_PANE.parseItem()),

    @VaroSerializeField(enumValue = "PINK")
    PINK(Materials.PINK_STAINED_GLASS_PANE.parseItem()),

    @VaroSerializeField(enumValue = "PURPLE")
    PURPLE(Materials.PURPLE_STAINED_GLASS_PANE.parseItem()),

    @VaroSerializeField(enumValue = "LIME")
    LIME(Materials.LIME_STAINED_GLASS_PANE.parseItem()),

    @VaroSerializeField(enumValue = "CYAN")
    CYAN(Materials.CYAN_STAINED_GLASS_PANE.parseItem());

    private final ItemStack colorPane;

    VaroMenuColor(ItemStack colorPane) {
        this.colorPane = colorPane;
    }

    @Override
    public void onDeserializeEnd() {
    }

    @Override
    public void onSerializeStart() {
    }

    public ItemStack getColorPane() {
        return colorPane;
    }
}