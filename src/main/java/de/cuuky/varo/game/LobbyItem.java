package de.cuuky.varo.game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.cryptomorin.xseries.XMaterial;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.language.Messages;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.team.VaroTeamRequest;
import de.varoplugin.cfw.item.ItemBuilder;
import de.varoplugin.cfw.player.hook.item.HookItemHitEvent;
import de.varoplugin.cfw.player.hook.item.HookItemInteractEntityEvent;
import de.varoplugin.cfw.player.hook.item.HookItemInteractEvent;
import de.varoplugin.cfw.player.hook.item.ItemHook;
import de.varoplugin.cfw.player.hook.item.ItemHookBuilder;
import de.varoplugin.cfw.player.hook.item.PlayerItemHookBuilder;

public class LobbyItem {

    private static List<ItemHook> lobbyItems = new ArrayList<>();

    private static void hookItem(Player player, ItemHookBuilder hookBuilder) {
        hookBuilder.droppable(false).movable(false);

        ItemHook hook = hookBuilder.complete(player, Main.getInstance());
        lobbyItems.add(hook);
    }

    public static void giveItems(VaroPlayer varoPlayer, Player player) {
        if (!ConfigSetting.TEAMREQUEST_ENABLED.getValueAsBoolean() || !ConfigSetting.TEAMREQUEST_LOBBYITEMS.getValueAsBoolean() || Main.getVaroGame() == null || Main.getVaroGame().hasStarted() || Main.getVaroGame().isStarting())
            return;

        hookItem(player, new PlayerItemHookBuilder().item(ItemBuilder.itemStack((ItemStack) ConfigSetting.TEAMREQUEST_LOBBYITEM_INVITE_ITEM.getValue()).displayName(Messages.TEAMREQUEST_LOBBYITEM_INVITE_NAME.value(varoPlayer))
                .lore(Messages.TEAMREQUEST_LOBBYITEM_INVITE_LORE.value(varoPlayer)).deleteDamageAnnotation().build()).slot(ConfigSetting.TEAMREQUEST_LOBBYITEM_INVITE_SLOT.getValueAsInt())
                .subscribe(HookItemInteractEntityEvent.class, hookEvent -> {
                    PlayerInteractEntityEvent event = hookEvent.getSource();

                    event.setCancelled(true);
                    event.getPlayer().updateInventory();
                })
                .subscribe(HookItemHitEvent.class, hookEvent -> {
                    EntityDamageByEntityEvent event = hookEvent.getSource();

                    if (Main.getVaroGame().hasStarted() || !(event.getEntity() instanceof Player))
                        return;

                    Player invited = (Player) event.getEntity();

                    if (VaroTeamRequest.getByAll(VaroPlayer.getPlayer(invited), varoPlayer) != null)
                        player.performCommand("varoplugin tr accept " + invited.getName());
                    else
                        player.performCommand("varoplugin tr invite " + invited.getName());

                    event.setCancelled(true);
                    player.updateInventory();
                }));

        giveOrRemoveTeamItems(varoPlayer);
    }

    public static void giveItems(VaroPlayer varoplayer) {
        if (!varoplayer.isOnline())
            return;
        giveItems(varoplayer, varoplayer.getPlayer());
    }

    public static void giveItems(Player player) {
        giveItems(VaroPlayer.getPlayer(player), player);
    }

    public static void giveOrRemoveTeamItems(VaroPlayer varoPlayer) {
        if (!ConfigSetting.TEAMREQUEST_ENABLED.getValueAsBoolean() || !ConfigSetting.TEAMREQUEST_LOBBYITEMS.getValueAsBoolean() || Main.getVaroGame() == null || Main.getVaroGame().hasStarted() || Main.getVaroGame().isStarting())
            return;

        if (varoPlayer.getTeam() == null) {
            ItemStack air = XMaterial.AIR.parseItem();
            Inventory inventory = varoPlayer.getPlayer().getInventory();
            inventory.setItem(ConfigSetting.TEAMREQUEST_LOBBYITEM_LEAVE_SLOT.getValueAsInt(), air);
            inventory.setItem(ConfigSetting.TEAMREQUEST_LOBBYITEM_RENAME_SLOT.getValueAsInt(), air);
            return;
        }

        hookItem(varoPlayer.getPlayer(), new PlayerItemHookBuilder().item(ItemBuilder.itemStack((ItemStack) ConfigSetting.TEAMREQUEST_LOBBYITEM_LEAVE_ITEM.getValue()).displayName(Messages.TEAMREQUEST_LOBBYITEM_LEAVE_NAME.value(varoPlayer))
                .lore(Messages.TEAMREQUEST_LOBBYITEM_LEAVE_LORE.value(varoPlayer)).deleteDamageAnnotation().build()).slot(ConfigSetting.TEAMREQUEST_LOBBYITEM_LEAVE_SLOT.getValueAsInt())
                .subscribe(HookItemInteractEntityEvent.class, hookEvent -> {
                    PlayerInteractEntityEvent event = hookEvent.getSource();

                    event.setCancelled(true);
                    event.getPlayer().updateInventory();
                })
                .subscribe(HookItemInteractEvent.class, hookEvent -> {
                    PlayerInteractEvent event = hookEvent.getSource();

                    if (Main.getVaroGame().hasStarted()) {
                        event.setCancelled(true);
                        return;
                    }

                    varoPlayer.getPlayer().performCommand("varoplugin tr leave");

                    event.setCancelled(true);
                    varoPlayer.getPlayer().updateInventory();
                }));

        if (ConfigSetting.TEAMREQUEST_LOBBYITEM_RENAME_ENABLED.getValueAsBoolean())
            hookItem(varoPlayer.getPlayer(), new PlayerItemHookBuilder().item(ItemBuilder.itemStack((ItemStack) ConfigSetting.TEAMREQUEST_LOBBYITEM_RENAME_ITEM.getValue()).displayName(Messages.TEAMREQUEST_LOBBYITEM_RENAME_NAME.value(varoPlayer))
                    .lore(Messages.TEAMREQUEST_LOBBYITEM_RENAME_LORE.value(varoPlayer)).deleteDamageAnnotation().build()).slot(ConfigSetting.TEAMREQUEST_LOBBYITEM_RENAME_SLOT.getValueAsInt())
                    .subscribe(HookItemInteractEntityEvent.class, hookEvent -> {
                        PlayerInteractEntityEvent event = hookEvent.getSource();

                        event.setCancelled(true);
                        event.getPlayer().updateInventory();
                    })
                    .subscribe(HookItemInteractEvent.class, hookEvent -> {
                        PlayerInteractEvent event = hookEvent.getSource();

                        if (Main.getVaroGame().hasStarted())
                            return;

                        if (varoPlayer.getTeam() != null)
                            varoPlayer.getTeam().createNameChangeChatHook(varoPlayer, null);

                        event.setCancelled(true);
                        varoPlayer.getPlayer().updateInventory();
                    }));
    }

    public static void removeHooks() {
        lobbyItems.forEach(ItemHook::unregister);
    }
}