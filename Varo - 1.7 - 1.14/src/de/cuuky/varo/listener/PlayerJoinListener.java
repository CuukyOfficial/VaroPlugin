package de.cuuky.varo.listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.config.ConfigEntry;
import de.cuuky.varo.configuration.messages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.event.BukkitEventType;
import de.cuuky.varo.entity.team.VaroTeam;
import de.cuuky.varo.event.VaroEvent;
import de.cuuky.varo.game.Game;
import de.cuuky.varo.game.lobby.LobbyItem;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.listener.helper.cancelable.CancelAbleType;
import de.cuuky.varo.listener.helper.cancelable.VaroCancelAble;
import de.cuuky.varo.logger.logger.EventLogger;
import de.cuuky.varo.logger.logger.EventLogger.LogType;
import de.cuuky.varo.spawns.Spawn;
import de.cuuky.varo.utils.VaroUtils;
import de.cuuky.varo.version.BukkitVersion;
import de.cuuky.varo.version.VersionUtils;

public class PlayerJoinListener implements Listener {

	private boolean isOutsideOfBorder(Player p) {
		if(VersionUtils.getVersion() == BukkitVersion.ONE_7)
			return false;

		try {
			Location loc = p.getLocation();
			Object border = p.getWorld().getClass().getDeclaredMethod("getWorldBorder").invoke(p.getWorld());
			double size = ((double) border.getClass().getDeclaredMethod("getSize").invoke(border)) / 2;
			Location center = (Location) border.getClass().getDeclaredMethod("getCenter").invoke(border);
			double x = loc.getX() - center.getX(), z = loc.getZ() - center.getZ();
			return((x > size || (-x) > size) || (z > size || (-z) > size));
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		VaroPlayer vplayer = VaroPlayer.getPlayer(player);

		vplayer.setPlayer(player);
		vplayer.onEvent(BukkitEventType.JOINED);

		if(Game.getInstance().getGameState() == GameState.LOBBY) {
			Spawn spawn = Spawn.getSpawn(vplayer);
			if(spawn != null && ConfigEntry.SPAWN_TELEPORT_JOIN.getValueAsBoolean())
				player.teleport(spawn.getLocation());
			else
				player.teleport(VaroUtils.getTeleportLocation());

			player.getInventory().clear();
			player.getInventory().setArmorContents(new ItemStack[] {});
			player.updateInventory();
			LobbyItem.giveItems(player);

			if(ConfigEntry.START_AT_PLAYERS.isIntActivated()) {
				if(VaroPlayer.getOnlineAndAlivePlayer().size() >= ConfigEntry.START_AT_PLAYERS.getValueAsInt())
					Game.getInstance().start();
				else
					Bukkit.broadcastMessage(Main.getPrefix() + "Es werden noch " + (ConfigEntry.START_AT_PLAYERS.getValueAsInt() - VaroPlayer.getOnlineAndAlivePlayer().size()) + " Spieler zum Start benötigt!");
			}

			if(player.isOp()) {
				Object[] updater = VaroUtils.checkForUpdates();
				VaroUtils.UpdateResult result = (VaroUtils.UpdateResult) updater[0];
				String updateVersion = (String) updater[1];

				if(result == VaroUtils.UpdateResult.UPDATE_AVAILABLE) {
					if(VaroTeam.getTeams().isEmpty()) {
						vplayer.getNetworkManager().sendTitle("§cUpdate verfügbar", "Deine Pluginversion ist nicht aktuell!");
					}
					player.sendMessage("§cUpdate auf Version " + updateVersion + " verfügbar!§7 Mit §l/varo update§7 kannst du das Update installieren.");
				}
			}
		} else {
			if(vplayer.getStats().getSessionsPlayed() == 0) {
				int countdown = VaroEvent.getMassRecEvent().isEnabled() ? VaroEvent.getMassRecEvent().getCountdown(vplayer) : vplayer.getStats().getCountdown();
				if(countdown == ConfigEntry.PLAY_TIME.getValueAsInt() * 60 && ConfigEntry.PLAY_TIME.getValueAsInt() > 0) {
					player.teleport(VaroUtils.getMainWorld().getSpawnLocation());
				}
			}
			if(isOutsideOfBorder(player) && ConfigEntry.OUTSIDE_BORDER_SPAWN_TELEPORT.getValueAsBoolean()) {
				player.teleport(player.getWorld().getSpawnLocation());
				EventLogger.getInstance().println(LogType.JOIN_LEAVE, ConfigMessages.ALERT_TELEPORTED_TO_MIDDLE.getValue(vplayer));
			}

			if(vplayer.getStats().isSpectator() || vplayer.isAdminIgnore()) {
				event.setJoinMessage(ConfigMessages.JOIN_SPECTATOR.getValue(vplayer));
			} else if(Game.getInstance().getFinaleJoinStart()) {
				event.setJoinMessage(ConfigMessages.JOIN_FINALE.getValue(vplayer));
				EventLogger.getInstance().println(LogType.JOIN_LEAVE, ConfigMessages.ALERT_JOIN_FINALE.getValue(vplayer));
				vplayer.sendMessage(Main.getPrefix() + "Das Finale beginnt bald. Bis zum Finalestart wurden alle gefreezed.");
				if(!player.isOp()) {
					new VaroCancelAble(CancelAbleType.FREEZE, vplayer);
				}
			} else if(!ConfigEntry.PLAY_TIME.isIntActivated()) {
				event.setJoinMessage(ConfigMessages.JOIN.getValue(vplayer));
				EventLogger.getInstance().println(LogType.JOIN_LEAVE, ConfigMessages.ALERT_PLAYER_JOIN_NORMAL.getValue(vplayer));
			} else if(VaroEvent.getMassRecEvent().isEnabled()) {
				if(VaroEvent.getMassRecEvent().getCountdown(vplayer) == ConfigEntry.PLAY_TIME.getValueAsInt() * 60) {
					vplayer.getStats().setCountdown(VaroEvent.getMassRecEvent().getTimer());
				} else {
					vplayer.getStats().setCountdown(VaroEvent.getMassRecEvent().getCountdown(vplayer) + VaroEvent.getMassRecEvent().getTimer());
				}

				if(!vplayer.getalreadyHadMassProtectionTime()) {
					vplayer.getStats().addSessionPlayed();
					event.setJoinMessage(ConfigMessages.JOIN_MASS_RECORDING.getValue(vplayer));
					EventLogger.getInstance().println(LogType.JOIN_LEAVE, ConfigMessages.ALERT_PLAYER_JOIN_MASSREC.getValue(vplayer));
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
					EventLogger.getInstance().println(LogType.JOIN_LEAVE, ConfigMessages.ALERT_PLAYER_RECONNECT.getValue(vplayer));
				}
			} else if(!vplayer.getStats().hasTimeLeft()) {
				event.setJoinMessage(ConfigMessages.JOIN_PROTECTION_TIME.getValue(vplayer));
				EventLogger.getInstance().println(LogType.JOIN_LEAVE, ConfigMessages.ALERT_PLAYER_JOINED.getValue(vplayer));
			} else {
				event.setJoinMessage(ConfigMessages.JOIN_WITH_REMAINING_TIME.getValue(vplayer));
				EventLogger.getInstance().println(LogType.JOIN_LEAVE, ConfigMessages.ALERT_PLAYER_RECONNECT.getValue(vplayer));
			}
			return;
		}

		event.setJoinMessage(ConfigMessages.JOIN.getValue(vplayer));
	}
}
