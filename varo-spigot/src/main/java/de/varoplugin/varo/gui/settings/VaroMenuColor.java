package de.varoplugin.varo.gui.settings;

import org.bukkit.inventory.ItemStack;

import com.cryptomorin.xseries.XMaterial;

import de.varoplugin.varo.serialize.identifier.VaroSerializeField;
import de.varoplugin.varo.serialize.identifier.VaroSerializeable;

public enum VaroMenuColor implements VaroSerializeable {

    @VaroSerializeField(enumValue = "GRAY")
    GRAY(XMaterial.GRAY_STAINED_GLASS_PANE.parseItem(), "§7"),

    @VaroSerializeField(enumValue = "WHITE")
    WHITE(XMaterial.WHITE_STAINED_GLASS_PANE.parseItem(), "§f"),

    @VaroSerializeField(enumValue = "BROWN")
    BROWN(XMaterial.BROWN_STAINED_GLASS_PANE.parseItem(), null),

    @VaroSerializeField(enumValue = "BLACK")
    BLACK(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem(), "§0"),

    @VaroSerializeField(enumValue = "RED")
    RED(XMaterial.RED_STAINED_GLASS_PANE.parseItem(), "§4"),

    @VaroSerializeField(enumValue = "GREEN")
    GREEN(XMaterial.GREEN_STAINED_GLASS_PANE.parseItem(), "§2"),

    @VaroSerializeField(enumValue = "BLUE")
    BLUE(XMaterial.BLUE_STAINED_GLASS_PANE.parseItem(), "§1"),

    @VaroSerializeField(enumValue = "YELLOW")
    YELLOW(XMaterial.YELLOW_STAINED_GLASS_PANE.parseItem(), "§e"),

    @VaroSerializeField(enumValue = "MAGENTA")
    MAGENTA(XMaterial.MAGENTA_STAINED_GLASS_PANE.parseItem(), "§d"),

    @VaroSerializeField(enumValue = "ORANGE")
    ORANGE(XMaterial.ORANGE_STAINED_GLASS_PANE.parseItem(), "§6"),

    @VaroSerializeField(enumValue = "PINK")
    PINK(XMaterial.PINK_STAINED_GLASS_PANE.parseItem(), null),

    @VaroSerializeField(enumValue = "PURPLE")
    PURPLE(XMaterial.PURPLE_STAINED_GLASS_PANE.parseItem(), "§5"),

    @VaroSerializeField(enumValue = "LIME")
    LIME(XMaterial.LIME_STAINED_GLASS_PANE.parseItem(), "§a"),

    @VaroSerializeField(enumValue = "CYAN")
    CYAN(XMaterial.CYAN_STAINED_GLASS_PANE.parseItem(), "§b");

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