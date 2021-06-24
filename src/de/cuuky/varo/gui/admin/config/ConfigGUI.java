package de.cuuky.varo.gui.admin.config;

import de.cuuky.cfw.hooking.hooks.chat.ChatHook;
import de.cuuky.cfw.hooking.hooks.chat.ChatHookHandler;
import de.cuuky.cfw.inventory.ItemClick;
import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.cfw.utils.JavaUtils;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.cfw.version.types.Sounds;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.config.ConfigSettingSection;
import de.cuuky.varo.gui.VaroListInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ConfigGUI extends VaroListInventory<ConfigSetting> {

    private ConfigSettingSection section;

    public ConfigGUI(Player opener, ConfigSettingSection section) {
        super(Main.getCuukyFrameWork().getAdvancedInventoryManager(), opener, section.getEntries());

        this.section = section;
    }

    private void hookChat(ConfigSetting entry) {
        Main.getCuukyFrameWork().getHookManager().registerHook(new ChatHook(getPlayer(), "§7Gib einen Wert ein fuer "
                + Main.getColorCode() + entry.getPath() + " §8(§7Aktuell: §a" + entry.getValue() + "§8):", new ChatHookHandler() {

            @Override
            public boolean onChat(AsyncPlayerChatEvent event) {
                String message = event.getMessage();
                if (message.equalsIgnoreCase("cancel")) {
                    getPlayer().sendMessage(Main.getPrefix() + "§7Aktion erfolgreich abgebrochen!");
                } else {
                    try {
                        entry.setValue(JavaUtils.getStringObject(message), true);
                    } catch (Exception e) {
                        getPlayer().sendMessage(Main.getPrefix() + e.getMessage());
                        return false;
                    }

                    getPlayer().playSound(getPlayer().getLocation(), Sounds.ANVIL_LAND.bukkitSound(), 1, 1);
                    getPlayer().sendMessage(Main.getPrefix() + "§7'§a" + entry.getPath() + "§7' erfolgreich auf '§a" + message + "§7' gesetzt!");
                }

                open();
                return true;
            }
        }));
        this.close();
        getPlayer().sendMessage(Main.getPrefix() + "§7Gib zum Abbruch §ccancel§7 ein.");
    }

    @Override
    protected ItemClick getClick(ConfigSetting setting) {
        return (event) -> {
            this.close();
            this.hookChat(setting);
        };
    }

    @Override
    protected ItemStack getItemStack(ConfigSetting setting) {
        List<String> lore = new ArrayList<>();
        for (String strin : setting.getDescription())
            lore.add(Main.getColorCode() + strin);

        lore.add(" ");
        lore.add("Value: " + setting.getValue());
        return new ItemBuilder().displayname("§7" + setting.getPath())
                .itemstack(new ItemStack(Materials.SIGN.parseMaterial())).lore(lore).build();
    }

    @Override
    public String getTitle() {
        return Main.getColorCode() + section.getName();
    }

    @Override
    public int getSize() {
        return this.getRecommendedSize();
    }
}