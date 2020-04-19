package de.cuuky.varo.vanish;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import de.cuuky.varo.Main;

public class Vanish {

	private static class VanishListener implements Listener {

		@EventHandler
		public void onPlayerJoin(PlayerJoinEvent event) {
			for (Vanish vanish : vanishes)
				vanish.hideFor(event.getPlayer());
		}

		@EventHandler
		public void onPlayerLeave(PlayerQuitEvent event) {
			Vanish v = Vanish.getVanish(event.getPlayer());
			if (v != null)
				v.remove();

			for (Vanish vanish : vanishes)
				vanish.unHideFor(event.getPlayer());
		}
	}

	private static ArrayList<Vanish> vanishes;

	static {
		vanishes = new ArrayList<Vanish>();

		Bukkit.getPluginManager().registerEvents(new VanishListener(), Main.getInstance());
	}

	private Player player;

	public Vanish(Player player) {
		this.player = player;

		hide();
		vanishes.add(this);
	}

	private void hide() {
		for (Player allplayer : Bukkit.getOnlinePlayers())
			allplayer.hidePlayer(player);
	}

	private void unhide() {
		for (Player allplayer : Bukkit.getOnlinePlayers())
			allplayer.showPlayer(player);
	}

	public void hideFor(Player player) {
		player.hidePlayer(this.player);
	}

	public void remove() {
		unhide();
		vanishes.remove(this);
	}

	public void unHideFor(Player player) {
		player.showPlayer(this.player);
	}

	public Player getPlayer() {
		return player;
	}

	public static Vanish getVanish(Player player) {
		for (Vanish vanish : vanishes)
			if (vanish.getPlayer().equals(player))
				return vanish;

		return null;
	}

	public static ArrayList<Vanish> getVanishes() {
		return vanishes;
	}
}