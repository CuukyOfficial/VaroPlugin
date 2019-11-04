package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.stat.PlayerState;
import de.cuuky.varo.game.start.ProtectionTime;
import de.cuuky.varo.listener.helper.cancelable.CancelAbleType;
import de.cuuky.varo.listener.helper.cancelable.VaroCancelAble;
import de.cuuky.varo.logger.logger.EventLogger.LogType;
import de.cuuky.varo.world.border.VaroBorder;

public class FinaleCommand extends VaroCommand {

	public FinaleCommand() {
		super("finale", "Hauptcommand für das Managen des Finales", "varo.finale");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if(args.length == 0 || (!args[0].equalsIgnoreCase("joinstart") && !args[0].equalsIgnoreCase("hauptstart"))) {
			sender.sendMessage(Main.getPrefix() + Main.getProjectName() + " §7Finale Befehle:");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo finale joinStart");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo finale hauptStart");
			return;
		}

		if(args[0].equalsIgnoreCase("joinstart")) {
			for (VaroPlayer player : VaroPlayer.getAlivePlayer()) {
				Player pl = player.getPlayer();
				if(pl.isOp()) {
					continue;
				}
				
				if(VaroCancelAble.getCancelAble(pl, CancelAbleType.FREEZE) == null)
					new VaroCancelAble(CancelAbleType.FREEZE, pl);
				
				if (pl.isOnline())
					player.sendMessage(Main.getPrefix() + "Das Finale beginnt bald. Bis zum Finalestart wurden alle gefreezed.");
			}
			
			Main.getGame().setFinaleJoinStart(true);
			ConfigEntry.PLAY_TIME.setValue(-1, true);
			
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "Es können nun alle zum Finale joinen.");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "Es wird empfohlen, mindestens 5 Minuten zu warten, bis das Finale gestartet wird.");
			sender.sendMessage(Main.getPrefix() + "§c§lWARNUNG: §cBeim Starten mit §7§l/varo finale hauptStart§7 werden alle Spieler, die nicht online sind, getötet.");
			
			Main.getLoggerMaster().getEventLogger().println(LogType.ALERT, "Man kann nun zum Finale joinen!");
			
			return;
		} else if(args[0].equalsIgnoreCase("hauptstart") || args[0].equalsIgnoreCase("mainstart")) {
			
			for (VaroPlayer player : VaroPlayer.getVaroPlayer()) {
				if (player.getPlayer().isOnline()) {
					player.getPlayer().teleport(Main.getDataManager().getWorldHandler().getTeleportLocation());
				} else {
					if (ConfigEntry.PLAYER_SPECTATE_IN_FINALE.getValueAsBoolean()) {
						player.getStats().setState(PlayerState.SPECTATOR);
					} else {
						player.getStats().setState(PlayerState.DEAD);
					}
				}
				if(VaroCancelAble.getCancelAble(player.getPlayer(), CancelAbleType.FREEZE) != null)
					VaroCancelAble.getCancelAble(player.getPlayer(), CancelAbleType.FREEZE).remove();
			}
			
			VaroBorder border = Main.getDataManager().getWorldHandler().getBorder();
			border.setSize(ConfigEntry.BORDER_SIZE_IN_FINALE.getValueAsInt());
			
			Main.getGame().setProtection(new ProtectionTime(ConfigEntry.FINALE_PROTECTION_TIME.getValueAsInt()));
			return;
		}
	}
}
