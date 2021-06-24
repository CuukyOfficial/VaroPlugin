package de.cuuky.varo.gui.admin.debug;

import de.cuuky.cfw.hooking.hooks.chat.ChatHook;
import de.cuuky.cfw.hooking.hooks.chat.ChatHookHandler;
import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.cfw.utils.LocationFormat;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.gui.VaroInventory;
import de.cuuky.varo.logger.logger.EventLogger.LogType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

public class DebugGUI extends VaroInventory {

    public DebugGUI(Player player) {
        super(Main.getCuukyFrameWork().getAdvancedInventoryManager(), player);
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
        addItem(10, new ItemBuilder().displayname("§cTrigger Event").itemstack(new ItemStack(Materials.SIGN.parseMaterial())).lore(new String[]{"§7Fuehrt ein Event aus, um den DiscordBot,", "TelegramBot, Config etc. zu testen"}).build(), (event) -> {
            close(false);

            Main.getCuukyFrameWork().getHookManager().registerHook(new ChatHook(getPlayer(), "§7Enter Event Message:", new ChatHookHandler() {

                @Override
                public boolean onChat(AsyncPlayerChatEvent event) {
                    Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, event.getMessage());
                    getPlayer().sendMessage(Main.getPrefix() + "§aErfolgreich!");
                    return true;
                }
            }));
        });

        addItem(13, new ItemBuilder().displayname("§cDo daily timer").itemstack(new ItemStack(Material.DAYLIGHT_DETECTOR)).lore(new String[]{"§7Fuehrt die Dinge aus, die sonst immer", "§7Nachts ausgefuehrt werden, wie Sessionreset"}).build(), (event) -> {
            Main.getDataManager().getDailyTimer().doDailyChecks();
            getPlayer().sendMessage(Main.getPrefix() + "§aErfolgreich!");
        });

        addItem(16, new ItemBuilder().displayname("§cTrigger Coordpost").itemstack(new ItemStack(Material.ANVIL)).amount(1).build(), (event) -> {
            StringBuilder post = new StringBuilder();
            for (VaroPlayer vp : VaroPlayer.getAlivePlayer())
                post.append((post.length() == 0) ? "Liste der Koordinaten aller Spieler:\n\n" : "\n").append(vp.getName()).append(vp.getTeam() != null ? " (#" + vp.getTeam().getName() + ")" : "").append(": ").append(vp.getStats().getLastLocation() != null ? new LocationFormat(vp.getStats().getLastLocation()).format("X:x Y:y Z:z in world") : "/");

            Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, post.toString());
        });
    }
}