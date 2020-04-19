package de.cuuky.varo.command.varo;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.stat.PlayerState;
import de.cuuky.varo.game.start.ProtectionTime;
import de.cuuky.varo.listener.helper.cancelable.CancelAbleType;
import de.cuuky.varo.listener.helper.cancelable.VaroCancelAble;
import de.cuuky.varo.logger.logger.EventLogger.LogType;

public class FinaleCommand extends VaroCommand {

	private enum FinalState {
		COUNTDOWN_PHASE,
		JOIN_PHASE,
		NONE,
		STARTED;
	}

	private int startScheduler, countdown;

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

		for (VaroPlayer player : VaroPlayer.getVaroPlayer()) {
			VaroCancelAble.removeCancelAble(player, CancelAbleType.FREEZE);
			if (player.getPlayer() != null) {
				if (player.getPlayer().isOnline()) {
					player.getPlayer().teleport(Main.getVaroGame().getVaroWorldHandler().getMainWorld().getWorld().getSpawnLocation());
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
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo finale joinStart");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo finale hauptStart [Countdown]");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo finale abort");
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
			sender.sendMessage(Main.getPrefix() + "§c§lWARNUNG: §cBeim Starten mit §7§l/varo finale hauptStart§7 werden alle Spieler, die nicht online sind, getoetet.");

			Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, "Man kann nun zum Finale joinen!");

			return;
		} else if (args[0].equalsIgnoreCase("hauptstart") || args[0].equalsIgnoreCase("mainstart")) {
			if (status == FinalState.NONE) {
				sender.sendMessage(Main.getPrefix() + "Der Join-Start wurde noch nicht aktiviert. Dies muss vor dem Hauptstart geschehen.");
				return;
			} else if (status == FinalState.COUNTDOWN_PHASE) {
				sender.sendMessage(Main.getPrefix() + "Der Finale-Countdown laeuft bereits.");
				return;
			} else if (status == FinalState.STARTED) {
				sender.sendMessage(Main.getPrefix() + "Das Finale wurde bereits gestartet.");
				return;
			}

			countdown = 0;
			if (args.length != 1) {
				try {
					countdown = Integer.parseInt(args[1]);
				} catch (NumberFormatException e) {
					countdown = 0;
				}
			}
			if (countdown != 0) {
				status = FinalState.COUNTDOWN_PHASE;
				startScheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
					@Override
					public void run() {
						if (countdown != 0) {
							Bukkit.broadcastMessage(Main.getPrefix() + "Das Finale startet in " + countdown + " Sekunden!");
						} else {
							finaleStart();
							Bukkit.getScheduler().cancelTask(startScheduler);
						}
						countdown--;
					}
				}, 0, 20);
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

			Bukkit.getScheduler().cancelTask(startScheduler);
			status = FinalState.JOIN_PHASE;
			Bukkit.broadcastMessage("§7Der Finale-Start wurde §cabgebrochen§7!");
		}
	}
}