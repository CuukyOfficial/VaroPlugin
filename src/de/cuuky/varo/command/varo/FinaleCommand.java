package de.cuuky.varo.command.varo;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.stat.PlayerState;
import de.cuuky.varo.game.start.ProtectionTime;
import de.cuuky.varo.listener.helper.cancelable.CancelAbleType;
import de.cuuky.varo.listener.helper.cancelable.VaroCancelAble;
import de.cuuky.varo.logger.logger.EventLogger.LogType;

// This file is absolute garbage but I'm to lazy to fix it
public class FinaleCommand extends VaroCommand {

	private enum FinalState {
		COUNTDOWN_PHASE,
		JOIN_PHASE,
		NONE,
		STARTED
	}

	private BukkitTask startScheduler;
	private int countdown;

	private FinalState status;

	public FinaleCommand() {
		super("finale", "Hauptcommand fuer das Managen des Finales", "varo.finale");

		this.status = FinalState.NONE;
	}

	private void finaleStart() {
		status = FinalState.STARTED;

		Bukkit.broadcastMessage(Main.getPrefix() + "§cDAS FINALE STARTET!");
		if (ConfigSetting.FINALE_PROTECTION_TIME.getValueAsInt() > 0) {
			Bukkit.broadcastMessage(Main.getPrefix() + "§7Es gibt " + ConfigSetting.FINALE_PROTECTION_TIME.getValueAsInt() + " Sekunden Schutzzeit.");
			Main.getVaroGame().setProtection(new ProtectionTime(ConfigSetting.FINALE_PROTECTION_TIME.getValueAsInt()));
		} else {
			Bukkit.broadcastMessage(Main.getPrefix() + "§7Es gibt keine Schutzzeit");
		}

		for (VaroPlayer player : VaroPlayer.getVaroPlayers()) {
			VaroCancelAble.removeCancelAble(player, CancelAbleType.FREEZE);
			if (player.getPlayer() != null) {
				if (player.getPlayer().isOnline()) {
					player.saveTeleport(Main.getVaroGame().getVaroWorldHandler().getMainWorld().getWorld().getSpawnLocation());
					continue;
				}
			}
			if (ConfigSetting.PLAYER_SPECTATE_IN_FINALE.getValueAsBoolean()) {
				player.getStats().setState(PlayerState.SPECTATOR);
			} else {
				player.getStats().setState(PlayerState.DEAD);
			}
		}

		Main.getVaroGame().getVaroWorldHandler().setBorderSize(ConfigSetting.BORDER_SIZE_IN_FINALE.getValueAsInt(), 0, null);
		Main.getVaroGame().setFinaleJoinStart(false);

		int playerNumber = VaroPlayer.getOnlinePlayer().size();
		Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, "DAS FINALE STARTET!\nEs nehmen " + playerNumber + "Spieler teil.");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (args.length == 0 || (!args[0].equalsIgnoreCase("joinstart") && !args[0].equalsIgnoreCase("hauptstart") && !args[0].equalsIgnoreCase("abort") && !args[0].equalsIgnoreCase("abbruch") && !!args[0].equalsIgnoreCase("abbrechen") && !!args[0].equalsIgnoreCase("stop"))) {
			sender.sendMessage(Main.getPrefix() + Main.getProjectName() + " §7Finale Befehle:");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " finale joinStart");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " finale hauptStart [Countdown]");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " finale abort");
			return;
		}

		if (args[0].equalsIgnoreCase("joinstart")) {
			if (status == FinalState.JOIN_PHASE) {
				sender.sendMessage(Main.getPrefix() + "Der JoinStart wurde bereits aktiviert.");
				return;
			} else if (status == FinalState.COUNTDOWN_PHASE) {
				sender.sendMessage(Main.getPrefix() + "Der Finale-Countdown wurde bereits aktiviert.");
				return;
			} else if (status == FinalState.STARTED) {
				sender.sendMessage(Main.getPrefix() + "Das Finale wurde bereits gestartet.");
				return;
			}

			for (VaroPlayer player : VaroPlayer.getOnlineAndAlivePlayer()) {
				Player pl = player.getPlayer();
				if (pl.isOp()) {
					continue;
				}

				new VaroCancelAble(CancelAbleType.FREEZE, player);

				if (pl.isOnline())
					player.sendMessage(Main.getPrefix() + "Das Finale beginnt bald. Bis zum Finalestart wurden alle gefreezed.");
			}

			Main.getVaroGame().setFinaleJoinStart(true);
			status = FinalState.JOIN_PHASE;
			ConfigSetting.PLAY_TIME.setValue(-1, true);

			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "Es koennen nun alle zum Finale joinen.");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "Es wird empfohlen, mindestens 5 Minuten zu warten, bis das Finale gestartet wird.");
			sender.sendMessage(Main.getPrefix() + "§c§lWARNUNG: §cBeim Starten mit §7§l/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " finale hauptStart§7 werden alle Spieler, die nicht online sind, getötet.");

			Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, "Man kann nun zum Finale joinen!");

			return;
		} else if (args[0].equalsIgnoreCase("hauptstart") || args[0].equalsIgnoreCase("mainstart")) {
			if (status == FinalState.COUNTDOWN_PHASE) {
				sender.sendMessage(Main.getPrefix() + "Der Finale-Countdown laeuft bereits.");
				return;
			} else if (status == FinalState.STARTED) {
				sender.sendMessage(Main.getPrefix() + "Das Finale wurde bereits gestartet.");
				return;
			}

			this.countdown = 0;
			if (args.length == 1) {
				sender.sendMessage(Main.getPrefix() + "§c§lWARNUNG: §cBeim Start werden alle Spieler die nicht online sind getötet. Zum Abbrechen nutze §7§l/"  + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " finale abort");
				this.countdown = 120;
			} else if (args.length == 2) {
				try {
					this.countdown = Integer.parseUnsignedInt(args[1]);
				} catch (NumberFormatException e) {
					sender.sendMessage(Main.getPrefix() + "§cUngültiger Countdown!");
					return;
				}
			} else {
				sender.sendMessage(Main.getPrefix() + "§cUngültige Argumente!");
				return;
			}

			Main.getVaroGame().setFinaleJoinStart(true);
			ConfigSetting.PLAY_TIME.setValue(-1, true);

			if (countdown != 0) {
				for (VaroPlayer player : VaroPlayer.getOnlineAndAlivePlayer())
					if (!player.getPlayer().isOp())
						new VaroCancelAble(CancelAbleType.FREEZE, player);

				status = FinalState.COUNTDOWN_PHASE;
				startScheduler = new BukkitRunnable() {
					@Override
					public void run() {
						if (countdown != 0) {
							Bukkit.broadcastMessage(Main.getPrefix() + "Das Finale startet in " + countdown + " Sekunden!");
						} else {
							finaleStart();
							startScheduler.cancel();
						}
						countdown--;
					}
				}.runTaskTimer(Main.getInstance(), 0L, 20L);
			} else {
				finaleStart();
			}

			return;
		} else if (args[0].equalsIgnoreCase("abort") || args[0].equalsIgnoreCase("abbruch") || args[0].equalsIgnoreCase("abbrechen") || args[0].equalsIgnoreCase("stop")) {
			if (status == FinalState.NONE || status == FinalState.JOIN_PHASE) {
				sender.sendMessage(Main.getPrefix() + "Es gibt keinen Countdown zum Abbrechen.");
				return;
			} else if (status == FinalState.STARTED) {
				sender.sendMessage(Main.getPrefix() + "Das Finale wurde bereits gestartet.");
				return;
			}

			startScheduler.cancel();
			status = FinalState.JOIN_PHASE;
			Bukkit.broadcastMessage("§7Der Finale-Start wurde §cabgebrochen§7!");
		}
	}
}