package de.varoplugin.varo.gui.admin.config;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;

import de.varoplugin.cfw.inventory.ItemClick;
import de.varoplugin.cfw.item.ItemBuilder;
import de.varoplugin.cfw.player.hook.chat.ChatHookTriggerEvent;
import de.varoplugin.cfw.player.hook.chat.PlayerChatHookBuilder;
import de.varoplugin.varo.Main;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;
import de.varoplugin.varo.configuration.configurations.config.ConfigSettingSection;
import de.varoplugin.varo.gui.VaroListInventory;

public class ConfigGUI extends VaroListInventory<ConfigSetting> {

    private ConfigSettingSection section;

    public ConfigGUI(Player opener, ConfigSettingSection section) {
        super(Main.getInventoryManager(), opener, section.getEntries());

        this.section = section;
    }

    private void hookChat(ConfigSetting entry) {
        new PlayerChatHookBuilder().message("§7Gib einen Wert ein fuer " + Main.getColorCode() + entry.getPath() + " §8(§7Aktuell: §a" + entry.getValue() + "§8):")
        .subscribe(ChatHookTriggerEvent.class, hookEvent -> {
            String message = hookEvent.getMessage();
            if (message.equalsIgnoreCase("cancel")) {
                getPlayer().sendMessage(Main.getPrefix() + "§7Aktion erfolgreich abgebrochen!");
            } else {
                try {
                    entry.setStringValue(message, true);
                } catch (Throwable t) {
                    getPlayer().sendMessage(Main.getPrefix() + t.getMessage());
                    return;
                }

                getPlayer().playSound(getPlayer().getLocation(), XSound.BLOCK_ANVIL_LAND.get(), 1, 1);
                getPlayer().sendMessage(Main.getPrefix() + "§7'§a" + entry.getPath() + "§7' erfolgreich auf '§a" + message + "§7' gesetzt!");
            }

            open();
            hookEvent.getHook().unregister();
        }).complete(getPlayer(), Main.getInstance());
        this.close();
        getPlayer().sendMessage(Main.getPrefix() + "§7Gib zum Abbruch §ccancel§7 ein.");
    }

    @Override
    protected ItemClick getClick(ConfigSetting setting) {
        return (event) -> {
        	if (setting.canParseFromString() && !setting.isSensitive()) {
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

        if (!setting.isSensitive()) {
            Object value = setting.getValue();
            lore.add(" ");
            lore.add("Value: " + (value instanceof Enum<?> ? ((Enum<?>) value).name() : value));
        }
        return ItemBuilder.material(XMaterial.OAK_SIGN).displayName("§7" + setting.getPath()).lore(lore).build();
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