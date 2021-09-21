package de.cuuky.varo.vanish;

import de.cuuky.cfw.version.VersionUtils;
import de.cuuky.varo.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Vanish {

	private static class VanishListener implements Listener {

		@EventHandler
		public void onPlayerJoin(PlayerJoinEvent event) {
			vanishes.forEach(va -> va.hideFor(event.getPlayer()));
		}

		@EventHandler
		public void onPlayerLeave(PlayerQuitEvent event) {
			Vanish v = Vanish.getVanish(event.getPlayer());
			if (v != null) v.remove();
			vanishes.forEach(va -> va.unHideFor(event.getPlayer()));
		}
	}

	private static final List<Vanish> vanishes = new ArrayList<>();

	static {
		Bukkit.getPluginManager().registerEvents(new VanishListener(), Main.getInstance());
	}

	private final Player player;

	public Vanish(Player player) {
		this.player = player;
		this.hide();
		vanishes.add(this);
	}

	private void changeVisibility(Consumer<Player> func) {
		VersionUtils.getVersionAdapter().getOnlinePlayers().forEach(func);
	}

	private void hide() {
		this.changeVisibility(this::hideFor);
	}

	private void unhide() {
		this.changeVisibility(this::unHideFor);
	}

	public void hideFor(Player player) {
		player.hidePlayer(this.player);
	}

	public void unHideFor(Player player) {
		player.showPlayer(this.player);
	}

	public void remove() {
		this.unhide();
		vanishes.remove(this);
	}

	public Player getPlayer() {
		return this.player;
	}

	public static Vanish getVanish(Player player) {
		return vanishes.stream().filter(v -> v.getPlayer().equals(player)).findFirst().orElse(null);
	}
}