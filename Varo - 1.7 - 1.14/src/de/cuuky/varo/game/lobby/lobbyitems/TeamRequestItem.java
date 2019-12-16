package de.cuuky.varo.game.lobby.lobbyitems;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.team.request.TeamRequest;
import de.cuuky.varo.game.lobby.LobbyItem;
import de.cuuky.varo.item.ItemBuilder;

public class TeamRequestItem extends LobbyItem {

	public TeamRequestItem() {
		super(new ItemBuilder().lore(new String[]{"§7Mit diesem Item kannst du Spieler schlagen", "§7woraufhin sie in dein Team eingeladen werden!"}).itemstack(new ItemStack(Material.DIAMOND_SWORD)).displayname(Main.getColorCode() + "Schlagen §7für Team").build(), 0);
	}

	@Override
	public void onEntityHit(EntityDamageByEntityEvent event) {
		Player hitted = (Player) event.getEntity();
		Player player = (Player) event.getDamager();

		if (TeamRequest.getByAll(VaroPlayer.getPlayer(hitted), VaroPlayer.getPlayer(player)) != null)
			player.performCommand("varo tr accept " + hitted.getName());
		else
			player.performCommand("varo tr invite " + hitted.getName());
	}
}
