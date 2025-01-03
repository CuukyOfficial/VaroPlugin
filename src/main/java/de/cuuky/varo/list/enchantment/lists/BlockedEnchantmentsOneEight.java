package de.cuuky.varo.list.enchantment.lists;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.enchantments.Enchantment;

import de.cuuky.varo.Main;

public class BlockedEnchantmentsOneEight extends BlockedEnchantmentList {

    public BlockedEnchantmentsOneEight() {
        super("BlockedEnchantments");
    }
    
    @Override
    public void onLoad(List<?> list) {
        super.onLoad(list);
        this.updateBlockedEnchantments();
    }
    
    @Override
    public void addEnchantment(Enchantment enc) {
        super.addEnchantment(enc);
        this.updateBlockedEnchantments();
    }
    
    @Override
    public void removeEnchantment(Enchantment enc) {
        super.removeEnchantment(enc);
        this.updateBlockedEnchantments();
    }
    
    private void updateBlockedEnchantments() {
        try {
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);

            Class<?> enchantmentClass = Class.forName("net.minecraft.server.v1_8_R3.Enchantment");
            Field enchantmentArrayField = enchantmentClass.getDeclaredField("b");
            enchantmentArrayField.setAccessible(true);
            modifiersField.setInt(enchantmentArrayField, ~Modifier.FINAL & enchantmentArrayField.getModifiers());

            Object[] filteredEnchantmentArray = Arrays.stream(Enchantment.values()).filter(enchantment -> !this.isBlocked(enchantment)).map(enchantment -> {
                try {
                    Field targetField = enchantment.getClass().getDeclaredField("target");
                    targetField.setAccessible(true);

                    return targetField.get(enchantment);
                } catch (Throwable t) {
                    throw new RuntimeException(t);
                }
            }).toArray(size -> (Object[]) Array.newInstance(enchantmentClass, size));

            enchantmentArrayField.set(null, filteredEnchantmentArray);
        } catch (Throwable t) {
            Main.getInstance().getLogger().log(Level.SEVERE, "Unable to block enchantments", t);
        }
    }
}