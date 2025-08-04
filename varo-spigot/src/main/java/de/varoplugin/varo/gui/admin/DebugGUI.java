package de.varoplugin.varo.gui.admin;

import org.bukkit.entity.Player;

import com.cryptomorin.xseries.XMaterial;

import de.varoplugin.cfw.item.ItemBuilder;
import de.varoplugin.cfw.location.LocationFormat;
import de.varoplugin.cfw.location.SimpleLocationFormat;
import de.varoplugin.cfw.player.hook.chat.ChatHookTriggerEvent;
import de.varoplugin.cfw.player.hook.chat.PlayerChatHookBuilder;
import de.varoplugin.varo.Main;
import de.varoplugin.varo.gui.VaroInventory;
import de.varoplugin.varo.logger.logger.EventLogger.LogType;
import de.varoplugin.varo.player.VaroPlayer;

public class DebugGUI extends VaroInventory {
    
    private static final LocationFormat LOCATION_FORMAT = new SimpleLocationFormat("X:x Y:y Z:z in world");

    public DebugGUI(Player player) {
        super(Main.getInventoryManager(), player);
    }

    @Override
    public String getTitle() {
        return "§6DEBUG-OPTIONS";
    }

    @Override
    public int getSize() {
        return 36;
    }

    @Override
    public void refreshContent() {
        addItem(10, ItemBuilder.material(XMaterial.OAK_SIGN).displayName("§cTrigger Event").lore("§7Fuehrt ein Event aus, um den DiscordBot,", "§7TelegramBot, Config etc. zu testen").build(), (event) -> {
            new PlayerChatHookBuilder().message("§7Enter Event Message:")
            .subscribe(ChatHookTriggerEvent.class, hookEvent -> {
                Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, hookEvent.getMessage());
                getPlayer().sendMessage(Main.getPrefix() + "§aErfolgreich!");
                open();
                hookEvent.getHook().unregister();
            }).complete(getPlayer(), Main.getInstance());
            this.close();
        });

        addItem(13, ItemBuilder.material(XMaterial.DAYLIGHT_DETECTOR).displayName("§cDo daily timer").lore("§7Fuehrt die Dinge aus, die sonst immer", "§7Nachts ausgefuehrt werden, wie Sessionreset").build(), (event) -> {
            Main.getDataManager().getDailyTimer().doDailyTasks();
            getPlayer().sendMessage(Main.getPrefix() + "§aErfolgreich!");
        });

        addItem(16, ItemBuilder.material(XMaterial.ANVIL).displayName("§cTrigger Coordpost").amount(1).build(), (event) -> {
            StringBuilder post = new StringBuilder();
            for (VaroPlayer vp : VaroPlayer.getAlivePlayer())
                post.append((post.length() == 0) ? "Liste der Koordinaten aller Spieler:\n\n" : "\n").append(vp.getName()).append(vp.getTeam() != null ? " (#" + vp.getTeam().getName() + ")" : "").append(": ").append(vp.getStats().getLastLocation() != null ? LOCATION_FORMAT.format(vp.getStats().getLastLocation()) : "/");

            Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, post.toString());
        });
    }
}