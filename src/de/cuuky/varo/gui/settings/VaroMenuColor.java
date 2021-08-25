package de.cuuky.varo.gui.settings;

import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.serialize.identifier.VaroSerializeField;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;
import org.bukkit.inventory.ItemStack;

public enum VaroMenuColor implements VaroSerializeable {

    @VaroSerializeField(enumValue = "GRAY")
    GRAY(Materials.GRAY_STAINED_GLASS_PANE.parseItem(), "§7"),

    @VaroSerializeField(enumValue = "WHITE")
    WHITE(Materials.WHITE_STAINED_GLASS_PANE.parseItem(), "§f"),

    @VaroSerializeField(enumValue = "BROWN")
    BROWN(Materials.BROWN_STAINED_GLASS_PANE.parseItem(), null),

    @VaroSerializeField(enumValue = "BLACK")
    BLACK(Materials.BLACK_STAINED_GLASS_PANE.parseItem(), "§0"),

    @VaroSerializeField(enumValue = "RED")
    RED(Materials.RED_STAINED_GLASS_PANE.parseItem(), "§4"),

    @VaroSerializeField(enumValue = "GREEN")
    GREEN(Materials.GREEN_STAINED_GLASS_PANE.parseItem(), "§2"),

    @VaroSerializeField(enumValue = "BLUE")
    BLUE(Materials.BLUE_STAINED_GLASS_PANE.parseItem(), "§1"),

    @VaroSerializeField(enumValue = "YELLOW")
    YELLOW(Materials.YELLOW_STAINED_GLASS_PANE.parseItem(), "§e"),

    @VaroSerializeField(enumValue = "MAGENTA")
    MAGENTA(Materials.MAGENTA_STAINED_GLASS_PANE.parseItem(), "§d"),

    @VaroSerializeField(enumValue = "ORANGE")
    ORANGE(Materials.ORANGE_STAINED_GLASS_PANE.parseItem(), "§6"),

    @VaroSerializeField(enumValue = "PINK")
    PINK(Materials.PINK_STAINED_GLASS_PANE.parseItem(), null),

    @VaroSerializeField(enumValue = "PURPLE")
    PURPLE(Materials.PURPLE_STAINED_GLASS_PANE.parseItem(), "§5"),

    @VaroSerializeField(enumValue = "LIME")
    LIME(Materials.LIME_STAINED_GLASS_PANE.parseItem(), "§a"),

    @VaroSerializeField(enumValue = "CYAN")
    CYAN(Materials.CYAN_STAINED_GLASS_PANE.parseItem(), "§b");

    private final String colorCode;
    private final ItemStack colorPane;

    VaroMenuColor(ItemStack colorPane, String colorCode) {
        this.colorCode = colorCode;
        this.colorPane = colorPane;
    }

    @Override
    public void onDeserializeEnd() {
    }

    @Override
    public void onSerializeStart() {
    }

    public String getColorCode() {
        return colorCode;
    }

    public ItemStack getColorPane() {
        return colorPane;
    }
}