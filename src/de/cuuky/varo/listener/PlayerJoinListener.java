package de.cuuky.varo.listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import de.cuuky.cfw.version.BukkitVersion;
import de.cuuky.cfw.version.ServerSoftware;
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
import de.cuuky.varo.utils.ModUtils;

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
		
		if (player.isOp()) {
			VaroUpdateResultSet updater = Main.getVaroUpdater().getLastResult();
			if (updater != null) {
				UpdateResult result = updater.getUpdateResult();
				String updateVersion = updater.getVersionName();

				switch (result) {
				case UPDATE_AVAILABLE:
					if (Main.getVaroGame().getGameState() == GameState.LOBBY)
						vplayer.getVersionAdapter().sendTitle("§cUpdate available", "You are using an outdated plugin version!");
					player.sendMessage(Main.getPrefix() + "§cVaro update available!§7 Use §l/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " update§7 to update now. New version: " + updateVersion);
					break;
				case TEST_BUILD:
					if (Main.getVaroGame().getGameState() == GameState.LOBBY)
						vplayer.getVersionAdapter().sendTitle("§cTEST-BUILD", "");
					player.sendMessage(Main.getPrefix() + "§cYou are running a test build! This version may be unstable! Please check our discord reguarly for updates " + Main.DISCORD_INVITE);
					break;
				default:
					break;
				}
			}
		}

		player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());

		vplayer.setPlayer(player);
		vplayer.onEvent(BukkitEventType.JOINED);

		if(vplayer.getStats().isSpectator())
			VersionUtils.getVersionAdapter().setXpCooldown(player, Integer.MAX_VALUE);
		else
			VersionUtils.getVersionAdapter().setXpCooldown(player, 0);

		event.setJoinMessage(null);
		if (VersionUtils.getServerSoftware() == ServerSoftware.MAGMA)
			ModUtils.checkForIllegalMods(player);

		if (ConfigSetting.ONLY_LABYMOD_PLAYER.getValueAsBoolean())
			PermissionSendListener.addCheck(player);

		if (Main.getVaroGame().getGameState() == GameState.LOBBY) {
			player.getInventory().clear();
			player.getInventory().setArmorContents(new ItemStack[]{});
			player.updateInventory();

			Spawn spawn = Spawn.getSpawn(vplayer);
			if (spawn != null && (ConfigSetting.SPAWN_TELEPORT_JOIN.getValueAsBoolean() || Main.getVaroGame().isStarting()))
				vplayer.saveTeleport(spawn.getLocation());
			else {
				vplayer.saveTeleport(Main.getVaroGame().getVaroWorldHandler().getTeleportLocation());
				LobbyItem.giveItems(player);
			}

			if (ConfigSetting.START_AT_PLAYERS.isIntActivated()) {
				if (VaroPlayer.getOnlineAndAlivePlayer().size() >= ConfigSetting.START_AT_PLAYERS.getValueAsInt())
					Main.getVaroGame().start();
				else
					Bukkit.broadcastMessage(ConfigMessages.JOIN_PLAYERS_REQUIRED.getValue().replace("%required%", String.valueOf(ConfigSetting.START_AT_PLAYERS.getValueAsInt() - VaroPlayer.getOnlineAndAlivePlayer().size())));
			}
		} else {
			MassRecordingVaroEvent massRecording = ((MassRecordingVaroEvent) VaroEvent.getEvent(VaroEventType.MASS_RECORDING));
			if (isOutsideOfBorder(player) && ConfigSetting.OUTSIDE_BORDER_SPAWN_TELEPORT.getValueAsBoolean()) {
				vplayer.saveTeleport(player.getWorld().getSpawnLocation());
				Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.JOIN_LEAVE, ConfigMessages.ALERT_TELEPORTED_TO_MIDDLE.getValue(null, vplayer));
			}

			if (vplayer.getStats().isSpectator() || vplayer.isAdminIgnore()) {
				Main.getLanguageManager().broadcastMessage(ConfigMessages.JOIN_SPECTATOR, vplayer);
			} else if (Main.getVaroGame().getFinaleJoinStart()) {
				Main.getLanguageManager().broadcastMessage(ConfigMessages.JOIN_FINALE, vplayer);
				Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.JOIN_LEAVE, ConfigMessages.ALERT_JOIN_FINALE.getValue(null, vplayer));
				vplayer.sendMessage(ConfigMessages.JOIN_FINALE_PLAYER);
				if (!player.isOp())
					new VaroCancelAble(CancelAbleType.FREEZE, vplayer);
			} else if (!ConfigSetting.PLAY_TIME.isIntActivated()) {
				Main.getLanguageManager().broadcastMessage(ConfigMessages.JOIN_MESSAGE, vplayer);
				Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.JOIN_LEAVE, ConfigMessages.ALERT_PLAYER_JOIN_NORMAL.getValue(null, vplayer));
			} else if (massRecording.isEnabled()) {
				if (massRecording.getCountdown(vplayer) == ConfigSetting.PLAY_TIME.getValueAsInt() * 60)
					vplayer.getStats().setCountdown(massRecording.getTimer());
				else
					vplayer.getStats().setCountdown(massRecording.getCountdown(vplayer) + massRecording.getTimer());

				if (!vplayer.getalreadyHadMassProtectionTime()) {
					vplayer.getStats().addSessionPlayed();
					Main.getLanguageManager().broadcastMessage(ConfigMessages.JOIN_MASS_RECORDING, vplayer);
					Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.JOIN_LEAVE, ConfigMessages.ALERT_PLAYER_JOIN_MASSREC.getValue(null, vplayer));
					vplayer.setalreadyHadMassProtectionTime(true);
					vplayer.setinMassProtectionTime(true);
					new BukkitRunnable() {
						@Override
						public void run() {
							vplayer.setinMassProtectionTime(false);
							Main.getLanguageManager().broadcastMessage(ConfigMessages.JOIN_PROTECTION_OVER, vplayer);
						}
					}.runTaskLater(Main.getInstance(), ConfigSetting.JOIN_PROTECTIONTIME.getValueAsInt() * 20);
				} else {
					Main.getLanguageManager().broadcastMessage(ConfigMessages.JOIN_WITH_REMAINING_TIME, vplayer);
					Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.JOIN_LEAVE, ConfigMessages.ALERT_PLAYER_RECONNECT.getValue(null, vplayer));
				}
			} else if (!vplayer.getStats().hasTimeLeft()) {
				Main.getLanguageManager().broadcastMessage(ConfigMessages.JOIN_PROTECTION_TIME, vplayer);
				Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.JOIN_LEAVE, ConfigMessages.ALERT_PLAYER_JOINED.getValue(null, vplayer));
			} else {
				Main.getLanguageManager().broadcastMessage(ConfigMessages.JOIN_WITH_REMAINING_TIME, vplayer);
				Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.JOIN_LEAVE, ConfigMessages.ALERT_PLAYER_RECONNECT.getValue(null, vplayer));
			}
			return;
		}

		Main.getLanguageManager().broadcastMessage(ConfigMessages.JOIN_MESSAGE, vplayer);
	}
}
