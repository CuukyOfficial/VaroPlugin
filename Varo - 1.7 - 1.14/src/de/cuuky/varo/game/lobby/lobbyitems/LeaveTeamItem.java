package de.cuuky.varo.game.lobby.lobbyitems;

import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.game.lobby.LobbyItem;
import de.cuuky.varo.item.ItemBuilder;

public class LeaveTeamItem extends LobbyItem {

	public LeaveTeamItem() {
		super(new ItemBuilder().displayname("§cTeam verlassen").itemstack(new ItemStack(Material.NETHER_STAR)).lore(new String[]{"§7Verlasse dein Team"}).build(), 1);
	}

	@Override
	public void onInteract(PlayerInteractEvent event) {
		VaroPlayer player = VaroPlayer.getPlayer(event.getPlayer());
		if (player.getTeam() != null)
			player.getTeam().removeMember(player);
	}
}
