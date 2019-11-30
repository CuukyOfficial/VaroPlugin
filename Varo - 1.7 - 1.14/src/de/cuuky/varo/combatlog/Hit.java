package de.cuuky.varo.combatlog;

import java.util.ArrayList;
import java.util.Date;

import de.cuuky.varo.game.Game;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.config.messages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;

public class Hit {

	/*
	 * OLD CODE
	 */

	private static ArrayList<Hit> hits = new ArrayList<>();

	static {
		Bukkit.getPluginManager().registerEvents(new HitListener(), Main.getInstance());
	}

	private Player player;
	private Player opponent;
	private int task;

	@SuppressWarnings("deprecation")
	public Hit(Player player, Player opponent) {
		if(!hasOld(player))
			player.sendMessage(Main.getPrefix() + ConfigMessages.COMBAT_IN_FIGHT.getValue());

		this.player = player;
		this.opponent = opponent;

		task = Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new BukkitRunnable() {

			@Override
			public void run() {
				over();
			}
		}, ConfigEntry.COMBATLOG_TIME.getValueAsInt() * 20);

		hits.add(this);
	}

	public Player getOpponent() {
		return this.opponent;
	}

	public Player getPlayer() {
		return this.player;
	}

	public void over() {
		player.sendMessage(Main.getPrefix() + ConfigMessages.COMBAT_NOT_IN_FIGHT.getValue());
		remove();
	}

	public void remove() {
		Bukkit.getScheduler().cancelTask(task);
		hits.remove(this);
	}

	public boolean hasOld(Player p) {
		for(Hit hit : hits) {
			if(!hit.getPlayer().equals(p))
				continue;

			hit.remove();
			return true;
		}

		return false;
	}

	public static Hit getHit(Player p) {
		for(Hit hit : hits) {
			if(!hit.getPlayer().equals(p))
				continue;

			return hit;
		}

		return null;
	}

	private static class HitListener implements Listener {

		@EventHandler(priority = EventPriority.HIGHEST)
		public void onHit(EntityDamageByEntityEvent event) {
			if(!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player))
				return;

			if(!Game.getInstance().isRunning() || event.isCancelled())
				return;

			VaroPlayer vp = VaroPlayer.getPlayer(((Player) event.getEntity()).getName());
			VaroPlayer vp1 = VaroPlayer.getPlayer(((Player) event.getDamager()).getName());

			if(vp.getTeam() == null || vp1.getTeam() == null || vp.getTeam().equals(vp1.getTeam()))
				return;

			Date current = new Date();
			vp.getStats().setLastEnemyContact(current);
			vp1.getStats().setLastEnemyContact(current);

			if(!ConfigEntry.COMBATLOG_TIME.isIntActivated())
				return;

			Player player1 = (Player) event.getDamager();
			Player player2 = (Player) event.getEntity();
			new Hit(player1, player2);
			new Hit(player2, player1);
		}
	}
}
