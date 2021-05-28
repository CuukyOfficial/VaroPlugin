package de.cuuky.varo.command.varo;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.stat.PlayerState;

public class ReviveCommand extends VaroCommand {

	public ReviveCommand() {
		super("revive", "Belebt einen Spieler wieder", "varo.revive", "unkill");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + label + " <Spieler> - Belebt einen Spieler wieder");
			return;
		}

		VaroPlayer target = VaroPlayer.getPlayer(args[0]);
		if (target == null) {
			sender.sendMessage(Main.getPrefix() + "Spieler konnte nicht gefunden werden!");
			return;
		}

		if (target.getStats().isAlive()) {
			sender.sendMessage(Main.getPrefix() + "Dieser Spieler lebt bereits!");
			return;
		}

		target.getStats().setState(PlayerState.ALIVE);
		if (target.isOnline()) {
			target.getPlayer().setGameMode(GameMode.SURVIVAL);
		}

		sender.sendMessage(Main.getPrefix() + "Spieler erfolgreich wiederbelebt!");
	}
}