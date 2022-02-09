package de.cuuky.varo.gui.admin.config;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.cfw.hooking.hooks.chat.ChatHook;
import de.cuuky.cfw.hooking.hooks.chat.ChatHookHandler;
import de.cuuky.cfw.inventory.ItemClick;
import de.cuuky.cfw.utils.item.BuildItem;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.cfw.version.types.Sounds;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.config.ConfigSettingSection;
import de.cuuky.varo.gui.VaroListInventory;

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
                        entry.setStringValue(message, true);
                    } catch (Throwable t) {
                        getPlayer().sendMessage(Main.getPrefix() + t.getMessage());
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
        	if (setting.canParseFromString()) {
	            this.close();
	            this.hookChat(setting);
        	} else
        		this.getPlayer().sendMessage(Main.getPrefix() + "§7Du kannst diese Einstellung nur in der Config Datei ändern!");
        };
    }

    @Override
    protected ItemStack getItemStack(ConfigSetting setting) {
        List<String> lore = new ArrayList<>();
        for (String strin : setting.getDescription())
            lore.add(Main.getColorCode() + strin);

        lore.add(" ");
        lore.add("Value: " + setting.getValue());
        return new BuildItem().displayName("§7" + setting.getPath())
                .material(Materials.SIGN).lore(lore).build();
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