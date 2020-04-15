package de.cuuky.varo.combatlog;

import java.util.ArrayList;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.messages.language.languages.defaults.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;

public class PlayerHit {

	/*
	 * OLD CODE
	 */

	private static class HitListener implements Listener {

		@EventHandler(priority = EventPriority.HIGHEST)
		public void onHit(EntityDamageByEntityEvent event) {
			if(!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player))
				return;

			if(!Main.getVaroGame().isRunning() || event.isCancelled())
				return;

			VaroPlayer vp = VaroPlayer.getPlayer(((Player) event.getEntity()).getName());
			VaroPlayer vp1 = VaroPlayer.getPlayer(((Player) event.getDamager()).getName());

			if(vp.getTeam() == null || vp1.getTeam() == null || vp.getTeam().equals(vp1.getTeam()))
				return;

			Date current = new Date();
			vp.getStats().setLastEnemyContact(current);
			vp1.getStats().setLastEnemyContact(current);

			if(!ConfigSetting.COMBATLOG_TIME.isIntActivated())
				return;

			Player player1 = (Player) event.getDamager();
			Player player2 = (Player) event.getEntity();
			new PlayerHit(player1, player2);
			new PlayerHit(player2, player1);
		}
	}

	private static ArrayList<PlayerHit> hits;

	static {
		hits = new ArrayList<>();

		Bukkit.getPluginManager().registerEvents(new HitListener(), Main.getInstance());
	}

	private int task;
	private Player player, opponent;

	@SuppressWarnings("deprecation")
	public PlayerHit(Player player, Player opponent) {
		VaroPlayer vp = VaroPlayer.getPlayer(player);
		if(!hasOld(player))
			player.sendMessage(Main.getPrefix() + ConfigMessages.COMBAT_IN_FIGHT.getValue(vp));

		this.player = player;
		this.opponent = opponent;

		scheduleOvertime();

		hits.add(this);
	}

	private void scheduleOvertime() {
		task = Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new BukkitRunnable() {

			@Override
			public void run() {
				over();
			}
		}, ConfigSetting.COMBATLOG_TIME.getValueAsInt() * 20);
	}

	public boolean hasOld(Player p) {
		for(PlayerHit hit : hits) {
			if(!hit.getPlayer().equals(p))
				continue;

			hit.remove();
			return true;
		}

		return false;
	}

	public void over() {
		VaroPlayer vp = VaroPlayer.getPlayer(player);
		player.sendMessage(Main.getPrefix() + ConfigMessages.COMBAT_NOT_IN_FIGHT.getValue(vp));
		remove();
	}

	public void remove() {
		Bukkit.getScheduler().cancelTask(task);
		hits.remove(this);
	}

	public Player getOpponent() {
		return this.opponent;
	}

	public Player getPlayer() {
		return this.player;
	}

	public static PlayerHit getHit(Player p) {
		for(PlayerHit hit : hits) {
			if(!hit.getPlayer().equals(p))
				continue;

			return hit;
		}

		return null;
	}
}