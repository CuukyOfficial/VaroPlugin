package de.cuuky.varo.game.lobby;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.cuuky.cfw.hooking.hooks.item.ItemHook;
import de.cuuky.cfw.hooking.hooks.item.ItemHookHandler;
import de.cuuky.cfw.utils.item.BuildItem;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.team.request.VaroTeamRequest;
import de.cuuky.varo.game.state.GameState;

public class LobbyItem {

	private static List<ItemHook> lobbyItems = new ArrayList<>();

	private static void hookItem(ItemHook hook) {
		lobbyItems.add(hook);
		Main.getCuukyFrameWork().getHookManager().registerHook(hook);

		hook.setDragable(false);
		hook.setDropable(false);
	}

	public static void giveItems(Player player) {
		if (!ConfigSetting.TEAMREQUEST_ENABLED.getValueAsBoolean() || !ConfigSetting.TEAMREQUEST_LOBBYITEMS.getValueAsBoolean())
			return;
		
		VaroPlayer varoPlayer = VaroPlayer.getPlayer(player);

		hookItem(new ItemHook(player, new BuildItem().displayName(ConfigMessages.TEAMREQUEST_LOBBYITEM_INVITE_NAME.getValue(varoPlayer)).itemstack((ItemStack) ConfigSetting.TEAMREQUEST_LOBBYITEM_INVITE_ITEM.getValue())
				.lore(ConfigMessages.TEAMREQUEST_LOBBYITEM_INVITE_LORE.getValue(varoPlayer)).deleteDamageAnnotation().build(), 0, new ItemHookHandler() {

			@Override
			public void onInteractEntity(PlayerInteractEntityEvent event) {}

			@Override
			public void onInteract(PlayerInteractEvent event) {}

			@Override
			public void onEntityHit(EntityDamageByEntityEvent event) {
				if (Main.getVaroGame().getGameState() != GameState.LOBBY)
					return;

				Player invited = (Player) event.getEntity();

				if (VaroTeamRequest.getByAll(VaroPlayer.getPlayer(invited), varoPlayer) != null)
					player.performCommand("varo tr accept " + invited.getName());
				else
					player.performCommand("varo tr invite " + invited.getName());
				
				event.setCancelled(true);
				player.updateInventory();
			}
		}));
		
		giveOrRemoveTeamItems(varoPlayer);
	}

	public static void giveOrRemoveTeamItems(VaroPlayer varoPlayer) {
		if (!ConfigSetting.TEAMREQUEST_ENABLED.getValueAsBoolean() || !ConfigSetting.TEAMREQUEST_LOBBYITEMS.getValueAsBoolean())
			return;
		
		if (varoPlayer.getTeam() == null) {
			ItemStack air = Materials.AIR.parseItem();
			Inventory inventory = varoPlayer.getPlayer().getInventory();
			inventory.setItem(1, air);
			inventory.setItem(2, air);
			return;
		}
		
		hookItem(new ItemHook(varoPlayer.getPlayer(), new BuildItem().displayName(ConfigMessages.TEAMREQUEST_LOBBYITEM_LEAVE_NAME.getValue(varoPlayer)).itemstack((ItemStack) ConfigSetting.TEAMREQUEST_LOBBYITEM_LEAVE_ITEM.getValue())
				.lore(ConfigMessages.TEAMREQUEST_LOBBYITEM_LEAVE_LORE.getValue(varoPlayer)).deleteDamageAnnotation().build(), 1, new ItemHookHandler() {

			@Override
			public void onInteractEntity(PlayerInteractEntityEvent event) {}

			@Override
			public void onInteract(PlayerInteractEvent event) {
				if (Main.getVaroGame().getGameState() != GameState.LOBBY)
					return;

				varoPlayer.getPlayer().performCommand("varo tr leave");
				
				event.setCancelled(true);
				varoPlayer.getPlayer().updateInventory();
			}

			@Override
			public void onEntityHit(EntityDamageByEntityEvent event) {}
		}));
		
		if (ConfigSetting.TEAMREQUEST_LOBBYITEM_RENAME_ENABLED.getValueAsBoolean())
			hookItem(new ItemHook(varoPlayer.getPlayer(), new BuildItem().displayName(ConfigMessages.TEAMREQUEST_LOBBYITEM_RENAME_NAME.getValue(varoPlayer)).itemstack((ItemStack) ConfigSetting.TEAMREQUEST_LOBBYITEM_RENAME_ITEM.getValue())
					.lore(ConfigMessages.TEAMREQUEST_LOBBYITEM_RENAME_LORE.getValue(varoPlayer)).deleteDamageAnnotation().build(), 2, new ItemHookHandler() {
	
				@Override
				public void onInteractEntity(PlayerInteractEntityEvent event) {}
	
				@Override
				public void onInteract(PlayerInteractEvent event) {
					if (Main.getVaroGame().getGameState() != GameState.LOBBY)
						return;

					if (varoPlayer.getTeam() != null)
						varoPlayer.getTeam().createNameChangeChatHook(varoPlayer, null);

					event.setCancelled(true);
					varoPlayer.getPlayer().updateInventory();
				}
	
				@Override
				public void onEntityHit(EntityDamageByEntityEvent event) {}
			}));
	}

	public static void removeHooks() {
		lobbyItems.forEach(ItemHook::unregister);
	}
}