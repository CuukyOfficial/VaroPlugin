package de.cuuky.varo.listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.config.messages.ConfigMessages;
import de.cuuky.varo.event.VaroEvent;
import de.cuuky.varo.game.lobby.LobbyItem;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.logger.logger.EventLogger.LogType;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.player.event.BukkitEventType;
import de.cuuky.varo.spawns.Spawn;
import de.cuuky.varo.version.BukkitVersion;
import de.cuuky.varo.version.VersionUtils;

public class PlayerJoinListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		VaroPlayer vplayer = VaroPlayer.getPlayer(player);

		vplayer.setPlayer(player);
		vplayer.onEvent(BukkitEventType.JOINED);

		if (Main.getGame().getGameState() == GameState.LOBBY) {
			Spawn spawn = Spawn.getSpawn(vplayer);
			if (spawn != null && ConfigEntry.SPAWN_TELEPORT_JOIN.getValueAsBoolean())
				player.teleport(spawn.getLocation());
			else
				player.teleport(Main.getDataManager().getWorldHandler().getTeleportLocation());

			player.getInventory().clear();
			player.getInventory().setArmorContents(new ItemStack[] {});
			player.updateInventory();
			LobbyItem.giveItems(player);

			if (ConfigEntry.START_AT_PLAYERS.isIntActivated()) {
				if (VaroPlayer.getOnlineAndAlivePlayer().size() >= ConfigEntry.START_AT_PLAYERS.getValueAsInt())
					Main.getGame().start();
				else
					Bukkit.broadcastMessage(
							Main.getPrefix() + "Es werden noch "
									+ (ConfigEntry.START_AT_PLAYERS.getValueAsInt()
											- VaroPlayer.getOnlineAndAlivePlayer().size())
									+ " Spieler zum Start benötigt!");
			}
		} else {
			if (isOutsideOfBorder(player)) {
				player.teleport(player.getWorld().getSpawnLocation());
				Main.getLoggerMaster().getEventLogger().println(LogType.JOIN_LEAVE,
						ConfigMessages.ALERT_TELEPORTED_TO_MIDDLE.getValue(vplayer));
			}

			if (vplayer.getStats().isSpectator() || vplayer.isAdminIgnore()) {
				event.setJoinMessage(ConfigMessages.JOIN_SPECTATOR.getValue(vplayer));
			} else if (!ConfigEntry.PLAY_TIME.isIntActivated()) {
				event.setJoinMessage(ConfigMessages.JOIN.getValue(vplayer));
				Main.getLoggerMaster().getEventLogger().println(LogType.JOIN_LEAVE,
						ConfigMessages.ALERT_PLAYER_JOIN_NORMAL.getValue(vplayer));
			} else if (VaroEvent.getMassRecEvent().isEnabled()) {
				if (VaroEvent.getMassRecEvent().getCountdown(vplayer) == ConfigEntry.PLAY_TIME.getValueAsInt() * 60) {
					vplayer.getStats().setCountdown(VaroEvent.getMassRecEvent().getTimer());
					if (!vplayer.getalreadyHadMassProtectionTime()) {
						vplayer.getStats().addSessionPlayed();
					}
				} else
					vplayer.getStats().setCountdown(
							VaroEvent.getMassRecEvent().getCountdown(vplayer) + VaroEvent.getMassRecEvent().getTimer());

				if (!vplayer.getalreadyHadMassProtectionTime()) {
					event.setJoinMessage(ConfigMessages.JOIN_MASS_RECORDING.getValue(vplayer));
					Main.getLoggerMaster().getEventLogger().println(LogType.JOIN_LEAVE,
							ConfigMessages.ALERT_PLAYER_JOIN_MASSREC.getValue(vplayer));
					vplayer.setalreadyHadMassProtectionTime(true);
					vplayer.setinMassProtectionTime(true);
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
						@Override
						public void run() {
							vplayer.setinMassProtectionTime(false);
							Bukkit.broadcastMessage(ConfigMessages.JOIN_PROTECTION_OVER.getValue(vplayer));
						}
					}, ConfigEntry.JOIN_PROTECTIONTIME.getValueAsInt() * 20);
				} else {
					event.setJoinMessage(ConfigMessages.JOIN_WITH_REMAINING_TIME.getValue(vplayer));
					Main.getLoggerMaster().getEventLogger().println(LogType.JOIN_LEAVE,
							ConfigMessages.ALERT_PLAYER_RECONNECT.getValue(vplayer));
				}
			} else if (!vplayer.getStats().hasTimeLeft()) {
				event.setJoinMessage(ConfigMessages.JOIN_PROTECTION_TIME.getValue(vplayer));
				Main.getLoggerMaster().getEventLogger().println(LogType.JOIN_LEAVE,
						ConfigMessages.ALERT_PLAYER_JOINED.getValue(vplayer));
			} else {
				event.setJoinMessage(ConfigMessages.JOIN_WITH_REMAINING_TIME.getValue(vplayer));
				Main.getLoggerMaster().getEventLogger().println(LogType.JOIN_LEAVE,
						ConfigMessages.ALERT_PLAYER_RECONNECT.getValue(vplayer));
			}
			return;
		}

		event.setJoinMessage(ConfigMessages.JOIN.getValue(vplayer));
	}

	private boolean isOutsideOfBorder(Player p) {
		if (VersionUtils.getVersion() == BukkitVersion.ONE_7)
			return false;

		try {
			Location loc = p.getLocation();
			Object border = p.getWorld().getClass().getDeclaredMethod("getWorldBorder").invoke(p.getWorld());
			double size = ((double) border.getClass().getDeclaredMethod("getSize").invoke(border)) / 2;
			Location center = (Location) border.getClass().getDeclaredMethod("getCenter").invoke(border);
			double x = loc.getX() - center.getX(), z = loc.getZ() - center.getZ();
			return ((x > size || (-x) > size) || (z > size || (-z) > size));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
