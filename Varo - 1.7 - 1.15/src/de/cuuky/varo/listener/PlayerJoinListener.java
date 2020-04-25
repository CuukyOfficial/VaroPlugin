package de.cuuky.varo.listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.cfw.version.BukkitVersion;
import de.cuuky.cfw.version.VersionUtils;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.event.BukkitEventType;
import de.cuuky.varo.event.VaroEvent;
import de.cuuky.varo.event.VaroEventType;
import de.cuuky.varo.event.events.MassRecordingVaroEvent;
import de.cuuky.varo.game.lobby.LobbyItem;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.game.world.VaroWorld;
import de.cuuky.varo.listener.helper.cancelable.CancelAbleType;
import de.cuuky.varo.listener.helper.cancelable.VaroCancelAble;
import de.cuuky.varo.logger.logger.EventLogger.LogType;
import de.cuuky.varo.spawns.Spawn;
import de.cuuky.varo.spigot.updater.VaroUpdateResultSet;
import de.cuuky.varo.spigot.updater.VaroUpdateResultSet.UpdateResult;

public class PlayerJoinListener implements Listener {

	private boolean isOutsideOfBorder(Player p) {
		if (VersionUtils.getVersion() == BukkitVersion.ONE_7)
			return false;

		try {
			Location loc = p.getLocation();
			VaroWorld world = Main.getVaroGame().getVaroWorldHandler().getVaroWorld(p.getWorld());
			double size = world.getVaroBorder().getBorderSize() / 2;
			Location center = world.getVaroBorder().getCenter();
			double x = loc.getX() - center.getX(), z = loc.getZ() - center.getZ();
			return ((x > size || (-x) > size) || (z > size || (-z) > size));
		} catch (Exception e) {
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

		if (Main.getVaroGame().getGameState() == GameState.LOBBY) {
			player.getInventory().clear();
			player.getInventory().setArmorContents(new ItemStack[] {});
			player.updateInventory();

			Spawn spawn = Spawn.getSpawn(vplayer);
			if (spawn != null && (ConfigSetting.SPAWN_TELEPORT_JOIN.getValueAsBoolean() || Main.getVaroGame().isStarting()))
				player.teleport(spawn.getLocation());
			else {
				player.teleport(Main.getVaroGame().getVaroWorldHandler().getTeleportLocation());
				LobbyItem.giveItems(player);
			}

			if (ConfigSetting.START_AT_PLAYERS.isIntActivated()) {
				if (VaroPlayer.getOnlineAndAlivePlayer().size() >= ConfigSetting.START_AT_PLAYERS.getValueAsInt())
					Main.getVaroGame().start();
				else
					Bukkit.broadcastMessage(Main.getPrefix() + "Es werden noch " + (ConfigSetting.START_AT_PLAYERS.getValueAsInt() - VaroPlayer.getOnlineAndAlivePlayer().size()) + " Spieler zum Start benoetigt!");
			}

			if (player.isOp()) {
				VaroUpdateResultSet updater = Main.getVaroUpdater().getLastResult();
				if (updater != null) {
					UpdateResult result = updater.getUpdateResult();
					String updateVersion = updater.getVersionName();

					if (result == UpdateResult.UPDATE_AVAILABLE) {
						if (Main.getVaroGame().getGameState() == GameState.LOBBY)
							vplayer.getNetworkManager().sendTitle("§cUpdate verfuegbar", "Deine Pluginversion ist nicht aktuell!");

						player.sendMessage("§cUpdate auf Version " + updateVersion + " verfuegbar!§7 Mit §l/varo update§7 kannst du das Update installieren.");
					}
				}
			}
		} else {
			MassRecordingVaroEvent massRecording = ((MassRecordingVaroEvent) VaroEvent.getEvent(VaroEventType.MASS_RECORDING));
			if (vplayer.getStats().getSessionsPlayed() == 0) {
				int countdown = massRecording.isEnabled() ? massRecording.getCountdown(vplayer) : vplayer.getStats().getCountdown();
				if (countdown == ConfigSetting.PLAY_TIME.getValueAsInt() * 60 && ConfigSetting.PLAY_TIME.getValueAsInt() > 0) {
					player.teleport(Main.getVaroGame().getVaroWorldHandler().getMainWorld().getWorld().getSpawnLocation());
				}
			}

			if (isOutsideOfBorder(player) && ConfigSetting.OUTSIDE_BORDER_SPAWN_TELEPORT.getValueAsBoolean()) {
				player.teleport(player.getWorld().getSpawnLocation());
				Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.JOIN_LEAVE, ConfigMessages.ALERT_TELEPORTED_TO_MIDDLE.getValue(null, vplayer));
			}

			if (vplayer.getStats().isSpectator() || vplayer.isAdminIgnore()) {
				event.setJoinMessage(ConfigMessages.JOIN_SPECTATOR.getValue(null, vplayer));
			} else if (Main.getVaroGame().getFinaleJoinStart()) {
				event.setJoinMessage(ConfigMessages.JOIN_FINALE.getValue(null, vplayer));
				Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.JOIN_LEAVE, ConfigMessages.ALERT_JOIN_FINALE.getValue(null, vplayer));
				vplayer.sendMessage(Main.getPrefix() + "Das Finale beginnt bald. Bis zum Finalestart wurden alle gefreezed.");
				if (!player.isOp())
					new VaroCancelAble(CancelAbleType.FREEZE, vplayer);
			} else if (!ConfigSetting.PLAY_TIME.isIntActivated()) {
				event.setJoinMessage(ConfigMessages.JOIN_MESSAGE.getValue(null, vplayer));
				Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.JOIN_LEAVE, ConfigMessages.ALERT_PLAYER_JOIN_NORMAL.getValue(null, vplayer));
			} else if (massRecording.isEnabled()) {
				if (massRecording.getCountdown(vplayer) == ConfigSetting.PLAY_TIME.getValueAsInt() * 60)
					vplayer.getStats().setCountdown(massRecording.getTimer());
				else
					vplayer.getStats().setCountdown(massRecording.getCountdown(vplayer) + massRecording.getTimer());

				if (!vplayer.getalreadyHadMassProtectionTime()) {
					vplayer.getStats().addSessionPlayed();
					event.setJoinMessage(ConfigMessages.JOIN_MASS_RECORDING.getValue(null, vplayer));
					Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.JOIN_LEAVE, ConfigMessages.ALERT_PLAYER_JOIN_MASSREC.getValue(null, vplayer));
					vplayer.setalreadyHadMassProtectionTime(true);
					vplayer.setinMassProtectionTime(true);
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
						@Override
						public void run() {
							vplayer.setinMassProtectionTime(false);
							Bukkit.broadcastMessage(ConfigMessages.JOIN_PROTECTION_OVER.getValue(null, vplayer));
						}
					}, ConfigSetting.JOIN_PROTECTIONTIME.getValueAsInt() * 20);
				} else {
					event.setJoinMessage(ConfigMessages.JOIN_WITH_REMAINING_TIME.getValue(null, vplayer));
					Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.JOIN_LEAVE, ConfigMessages.ALERT_PLAYER_RECONNECT.getValue(null, vplayer));
				}
			} else if (!vplayer.getStats().hasTimeLeft()) {
				event.setJoinMessage(ConfigMessages.JOIN_PROTECTION_TIME.getValue(null, vplayer));
				Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.JOIN_LEAVE, ConfigMessages.ALERT_PLAYER_JOINED.getValue(null, vplayer));
			} else {
				event.setJoinMessage(ConfigMessages.JOIN_WITH_REMAINING_TIME.getValue(null, vplayer));
				Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.JOIN_LEAVE, ConfigMessages.ALERT_PLAYER_RECONNECT.getValue(null, vplayer));
			}
			return;
		}

		event.setJoinMessage(ConfigMessages.JOIN_MESSAGE.getValue(null, vplayer));
	}
}
