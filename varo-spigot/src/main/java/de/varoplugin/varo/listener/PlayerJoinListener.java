package de.varoplugin.varo.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import de.varoplugin.cfw.version.VersionUtils;
import de.varoplugin.varo.Main;
import de.varoplugin.varo.config.language.Messages;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;
import de.varoplugin.varo.event.VaroEvent;
import de.varoplugin.varo.event.VaroEventType;
import de.varoplugin.varo.event.events.MassRecordingVaroEvent;
import de.varoplugin.varo.game.LobbyItem;
import de.varoplugin.varo.listener.helper.cancelable.CancelableType;
import de.varoplugin.varo.listener.helper.cancelable.VaroCancelable;
import de.varoplugin.varo.logger.logger.EventLogger.LogType;
import de.varoplugin.varo.player.VaroPlayer;
import de.varoplugin.varo.player.event.BukkitEventType;
import de.varoplugin.varo.spawns.Spawn;
import de.varoplugin.varo.spigot.VaroUpdateResultSet;
import de.varoplugin.varo.spigot.VaroUpdateResultSet.UpdateResult;
import io.github.almightysatan.slams.Placeholder;

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
					if (!Main.getVaroGame().hasStarted())
						vplayer.getVersionAdapter().sendTitle("§cUpdate available", "You are using an outdated plugin version!");
					player.sendMessage(Main.getPrefix() + "§cVaro update available! §7Use §l/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " update§7 to update now. New version: " + updateVersion);
					break;
				case MAJOR_UPDATE_AVAILABLE:
                    if (!Main.getVaroGame().hasStarted())
                        vplayer.getVersionAdapter().sendTitle("§cMajor Update available", "You are using an outdated plugin version!");
                    player.sendMessage(Main.getPrefix() + "§cVaro update available! Updating to this version will reset all stats and configs! §7Use §l/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " update§7 to update now. New version: " + updateVersion + "\n§7Need help? Join our Discord for support: §e" + Main.DISCORD_INVITE);
                    break;
				case TEST_BUILD:
					if (!Main.getVaroGame().hasStarted())
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

		if (!Main.getVaroGame().hasStarted()) {
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
				    Messages.PLAYER_JOIN_REQUIRED.broadcast(Placeholder.constant("num-required", String.valueOf(ConfigSetting.START_AT_PLAYERS.getValueAsInt() - VaroPlayer.getOnlineAndAlivePlayer().size())));
			}
		} else {
			MassRecordingVaroEvent massRecording = ((MassRecordingVaroEvent) VaroEvent.getEvent(VaroEventType.MASS_RECORDING));
			if (Main.getVaroGame().getVaroWorldHandler().getVaroWorld(player.getWorld()).getVaroBorder().isOutside(player) && ConfigSetting.OUTSIDE_BORDER_SPAWN_TELEPORT.getValueAsBoolean()) {
				vplayer.saveTeleport(player.getWorld().getSpawnLocation());
				Messages.LOG_TELEPORTED_TO_MIDDLE.log(LogType.JOIN_LEAVE, vplayer);
			}

			if (vplayer.getStats().isSpectator() || vplayer.isAdminIgnore()) {
			    Messages.PLAYER_JOIN_SPECTATOR.broadcast(vplayer);
			} else if (Main.getVaroGame().isFinaleJoin()) {
				Messages.PLAYER_JOIN_FINALE.broadcast(vplayer);
				Messages.LOG_FINALE_JOIN.log(LogType.JOIN_LEAVE, vplayer);
				if (ConfigSetting.FINALE_FREEZE.getValueAsBoolean()) {
				    Messages.GAME_FINALE_FREEZE.send(vplayer);
    				if (!player.isOp())
    					new VaroCancelable(CancelableType.FREEZE, vplayer);
				} else
				    Messages.GAME_FINALE_NOFREEZE.send(vplayer);
			} else if (!Main.getVaroGame().isPlayTimeLimited()) {
			    Messages.PLAYER_JOIN_BROADCAST.broadcast(vplayer);
			    Messages.LOG_PLAYER_JOIN_NORMAL.log(LogType.JOIN_LEAVE, vplayer);
			} else if (massRecording.isEnabled()) {
				vplayer.getStats().setCountdown(massRecording.getTimer());

				if (!vplayer.getalreadyHadMassProtectionTime()) {
					vplayer.getStats().addSessionPlayed();
					Messages.PLAYER_JOIN_MASS_RECORDING.broadcast(vplayer);
					Messages.LOG_PLAYER_JOIN_MASSREC.log(LogType.JOIN_LEAVE, vplayer);
					vplayer.setalreadyHadMassProtectionTime(true);
					vplayer.setinMassProtectionTime(true);
					new BukkitRunnable() {
						@Override
						public void run() {
							vplayer.setinMassProtectionTime(false);
							Messages.PLAYER_JOIN_PROTECTION_END.broadcast(vplayer);
						}
					}.runTaskLater(Main.getInstance(), ConfigSetting.JOIN_PROTECTIONTIME.getValueAsInt() * 20);
				} else {
				    Messages.PLAYER_JOIN_REMAINING_TIME.broadcast(vplayer);
				    Messages.LOG_PLAYER_RECONNECT.log(LogType.JOIN_LEAVE, vplayer);
				}
			} else if (!vplayer.getStats().hasTimeLeft()) {
			    Messages.PLAYER_JOIN_PROTECTION.broadcast(vplayer);
			    Messages.LOG_PLAYER_JOINED.log(LogType.JOIN_LEAVE, vplayer);
			} else {
			    Messages.PLAYER_JOIN_REMAINING_TIME.broadcast(vplayer);
			    Messages.LOG_PLAYER_RECONNECT.log(LogType.JOIN_LEAVE, vplayer);
			}
			return;
		}

		Messages.PLAYER_JOIN_BROADCAST.broadcast(vplayer);
		Messages.LOG_PLAYER_JOIN_NORMAL.log(LogType.JOIN_LEAVE, vplayer);
	}
}
