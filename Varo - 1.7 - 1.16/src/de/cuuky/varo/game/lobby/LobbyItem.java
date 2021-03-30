package de.cuuky.varo.game.lobby;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.cfw.hooking.hooks.item.ItemHook;
import de.cuuky.cfw.hooking.hooks.item.ItemHookHandler;
import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.team.request.VaroTeamRequest;
import de.cuuky.varo.game.state.GameState;

public class LobbyItem {

	private static void hookItem(ItemHook hook) {
		Main.getCuukyFrameWork().getHookManager().registerHook(hook);

		hook.setDragable(false);
		hook.setDropable(false);
	}

	public static void giveItems(Player player) {
		if (!ConfigSetting.TEAMREQUEST_ENABLED.getValueAsBoolean() || !ConfigSetting.TEAMREQUEST_LOBBYITEMS.getValueAsBoolean())
			return;

		hookItem(new ItemHook(player, new ItemBuilder().displayname("§cTeam verlassen").itemstack(new ItemStack(Material.NETHER_STAR)).lore(new String[] { "§7Verlasse dein Team" }).build(), 1, new ItemHookHandler() {

			@Override
			public void onInteractEntity(PlayerInteractEntityEvent event) {}

			@Override
			public void onInteract(PlayerInteractEvent event) {
				if (Main.getVaroGame().getGameState() != GameState.LOBBY)
					return;

				VaroPlayer player = VaroPlayer.getPlayer(event.getPlayer());
				if (player.getTeam() != null) {
					player.getTeam().removeMember(player);
					player.sendMessage(Main.getPrefix() + "Team erfolgreich verlassen!");
				} else
					player.sendMessage(Main.getPrefix() + "Du bist in keinem Team!");

				event.setCancelled(true);
			}

			@Override
			public void onEntityHit(EntityDamageByEntityEvent event) {}
		}));

		hookItem(new ItemHook(player, new ItemBuilder().lore(new String[] { "§7Mit diesem Item kannst du Spieler schlagen", "§7woraufhin sie in dein Team eingeladen werden!" }).itemstack(new ItemStack(Material.DIAMOND_SWORD)).displayname(Main.getColorCode() + "Schlagen §7fuer Team").build(), 0, new ItemHookHandler() {

			@Override
			public void onInteractEntity(PlayerInteractEntityEvent event) {}

			@Override
			public void onInteract(PlayerInteractEvent event) {}

			@Override
			public void onEntityHit(EntityDamageByEntityEvent event) {
				if (Main.getVaroGame().getGameState() != GameState.LOBBY)
					return;

				Player hitted = (Player) event.getEntity();
				Player damager = (Player) event.getDamager();

				if (VaroTeamRequest.getByAll(VaroPlayer.getPlayer(hitted), VaroPlayer.getPlayer(damager)) != null)
					damager.performCommand("varo tr accept " + hitted.getName());
				else
					damager.performCommand("varo tr invite " + hitted.getName());

				event.setCancelled(true);
				damager.getItemInHand().setDurability((short) 0);
			}
		}));
	}
}