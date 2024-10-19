package de.cuuky.varo.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

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
import de.cuuky.varo.listener.helper.cancelable.CancelableType;
import de.cuuky.varo.listener.helper.cancelable.VaroCancelable;
import de.cuuky.varo.logger.logger.EventLogger.LogType;
import de.cuuky.varo.spawns.Spawn;
import de.cuuky.varo.spigot.updater.VaroUpdateResultSet;
import de.cuuky.varo.spigot.updater.VaroUpdateResultSet.UpdateResult;
import de.cuuky.varo.utils.MagmaUtils;
import de.varoplugin.cfw.version.ServerSoftware;
import de.varoplugin.cfw.version.VersionUtils;

public class PlayerJoinListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		VaroPlayer vplayer = VaroPlayer.getPlayer(player);

		player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());

		vplayer.setPlayer(player);
		vplayer.onEvent(BukkitEventType.JOINED);

		if (player.isOp()) {
			VaroUpdateResultSet updater = Main.getVaroUpdater().getLastResult();
			if (updater != null) {
				UpdateResult result = updater.getUpdateResult();
				String updateVersion = updater.getVersionName();

				switch (result) {
				case UPDATE_AVAILABLE:
					if (Main.getVaroGame().getGameState() == GameState.LOBBY)
						vplayer.getVersionAdapter().sendTitle("§cUpdate available", "You are using an outdated plugin version!");
					player.sendMessage(Main.getPrefix() + "§cVaro update available! §7Use §l/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " update§7 to update now. New version: " + updateVersion);
					break;
				case MAJOR_UPDATE_AVAILABLE:
                    if (Main.getVaroGame().getGameState() == GameState.LOBBY)
                        vplayer.getVersionAdapter().sendTitle("§cMajor Update available", "You are using an outdated plugin version!");
                    player.sendMessage(Main.getPrefix() + "§cVaro update available! Updating to this version will reset all stats and configs! §7Use §l/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " update§7 to update now. New version: " + updateVersion + "\n§7Need help? Join our Discord for support: §e" + Main.DISCORD_INVITE);
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

		if(vplayer.getStats().isSpectator())
			VersionUtils.getVersionAdapter().setXpCooldown(player, Integer.MAX_VALUE);
		else
			VersionUtils.getVersionAdapter().setXpCooldown(player, 0);

		event.setJoinMessage(null);
		if (VersionUtils.getServerSoftware() == ServerSoftware.MAGMA_1_12)
			MagmaUtils.checkForIllegalMods(player);

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
					Main.getVaroGame().prepareStart();
				else
					Bukkit.broadcastMessage(ConfigMessages.JOIN_PLAYERS_REQUIRED.getValue().replace("%required%", String.valueOf(ConfigSetting.START_AT_PLAYERS.getValueAsInt() - VaroPlayer.getOnlineAndAlivePlayer().size())));
			}
		} else {
			MassRecordingVaroEvent massRecording = ((MassRecordingVaroEvent) VaroEvent.getEvent(VaroEventType.MASS_RECORDING));
			if (Main.getVaroGame().getVaroWorldHandler().getVaroWorld(player.getWorld()).getVaroBorder().isOutside(player) && ConfigSetting.OUTSIDE_BORDER_SPAWN_TELEPORT.getValueAsBoolean()) {
				vplayer.saveTeleport(player.getWorld().getSpawnLocation());
				Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.JOIN_LEAVE, ConfigMessages.ALERT_TELEPORTED_TO_MIDDLE.getValue(null, vplayer), vplayer.getRealUUID());
			}

			if (vplayer.getStats().isSpectator() || vplayer.isAdminIgnore()) {
				Main.getLanguageManager().broadcastMessage(ConfigMessages.JOIN_SPECTATOR, vplayer);
			} else if (Main.getVaroGame().isFinaleJoin()) {
				Main.getLanguageManager().broadcastMessage(ConfigMessages.JOIN_FINALE, vplayer);
				Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.JOIN_LEAVE, ConfigMessages.ALERT_JOIN_FINALE.getValue(null, vplayer), vplayer.getRealUUID());
				if (ConfigSetting.FINALE_FREEZE.getValueAsBoolean()) {
    				vplayer.sendMessage(ConfigMessages.JOIN_FINALE_PLAYER);
    				if (!player.isOp())
    					new VaroCancelable(CancelableType.FREEZE, vplayer);
				} else
				    vplayer.sendMessage(ConfigMessages.JOIN_FINALE_PLAYER_NO_FREEZE);
			} else if (!Main.getVaroGame().isPlayTimeLimited()) {
				Main.getLanguageManager().broadcastMessage(ConfigMessages.JOIN_MESSAGE, vplayer);
				Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.JOIN_LEAVE, ConfigMessages.ALERT_PLAYER_JOIN_NORMAL.getValue(null, vplayer), vplayer.getRealUUID());
			} else if (massRecording.isEnabled()) {
				vplayer.getStats().setCountdown(massRecording.getTimer());

				if (!vplayer.getalreadyHadMassProtectionTime()) {
					vplayer.getStats().addSessionPlayed();
					Main.getLanguageManager().broadcastMessage(ConfigMessages.JOIN_MASS_RECORDING, vplayer);
					Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.JOIN_LEAVE, ConfigMessages.ALERT_PLAYER_JOIN_MASSREC.getValue(null, vplayer), vplayer.getRealUUID());
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
					Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.JOIN_LEAVE, ConfigMessages.ALERT_PLAYER_RECONNECT.getValue(null, vplayer), vplayer.getRealUUID());
				}
			} else if (!vplayer.getStats().hasTimeLeft()) {
				Main.getLanguageManager().broadcastMessage(ConfigMessages.JOIN_PROTECTION_TIME, vplayer);
				Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.JOIN_LEAVE, ConfigMessages.ALERT_PLAYER_JOINED.getValue(null, vplayer), vplayer.getRealUUID());
			} else {
				Main.getLanguageManager().broadcastMessage(ConfigMessages.JOIN_WITH_REMAINING_TIME, vplayer);
				Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.JOIN_LEAVE, ConfigMessages.ALERT_PLAYER_RECONNECT.getValue(null, vplayer), vplayer.getRealUUID());
			}
			return;
		}

		Main.getLanguageManager().broadcastMessage(ConfigMessages.JOIN_MESSAGE, vplayer);
	}
}
