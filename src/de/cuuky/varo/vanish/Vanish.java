package de.cuuky.varo.vanish;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import de.varoplugin.cfw.version.VersionUtils;
import de.cuuky.varo.Main;

public class Vanish {

	private static class VanishListener implements Listener {

		@EventHandler
		public void onPlayerJoin(PlayerJoinEvent event) {
			vanishes.values().forEach(va -> va.hideFor(event.getPlayer()));
		}

		@EventHandler
		public void onPlayerLeave(PlayerQuitEvent event) {
			Vanish v = Vanish.getVanish(event.getPlayer());
			if (v != null)
				v.remove();
			vanishes.values().forEach(va -> va.unHideFor(event.getPlayer()));
		}
	}

	private static final Map<UUID, Vanish> vanishes = new ConcurrentHashMap<>();

	static {
		Bukkit.getPluginManager().registerEvents(new VanishListener(), Main.getInstance());
	}

	private final Player player;

	public Vanish(Player player) {
		this.player = player;
		this.hide();
		vanishes.put(player.getUniqueId(), this);
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
		vanishes.remove(this.player.getUniqueId());
	}

	public Player getPlayer() {
		return this.player;
	}

	public static Vanish getVanish(Player player) {
		return vanishes.values().stream().filter(v -> v.getPlayer().equals(player)).findFirst().orElse(null);
	}
}