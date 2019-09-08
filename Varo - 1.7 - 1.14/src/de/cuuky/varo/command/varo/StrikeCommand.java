package de.cuuky.varo.command.varo;

import java.text.SimpleDateFormat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.player.stats.stat.Strike;

public class StrikeCommand extends VaroCommand {

	public StrikeCommand() {
		super("strike", "Benutze diesen Command um einen Spieler zu striken", "varo.strike");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if(args.length == 0) {
			sender.sendMessage(Main.getPrefix() + "§7------ " + Main.getColorCode() + "Strike §7-----");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo strike §7<Player> [Grund]");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo strike list §7<Player>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo strike remove §7<Player> <StrikeNummer>");
			sender.sendMessage(Main.getPrefix() + "§7-----------------------");
			return;
		}

		if(VaroPlayer.getPlayer(args[0]) != null) {
			VaroPlayer varoPlayer = VaroPlayer.getPlayer(args[0]);

			String reason = "";
			for(String key : args) {
				if(key.equals(args[0]))
					continue;

				reason = reason.isEmpty() ? key : " " + key;
			}

			Strike strike = new Strike(reason, varoPlayer, sender instanceof ConsoleCommandSender ? "CONSOLE" : "" + sender.getName());
			varoPlayer.getStats().addStrike(strike);
			sender.sendMessage(Main.getPrefix() + "Du hast " + varoPlayer.getName() + " gestriket!");
			return;
		} else if(args[0].equalsIgnoreCase("remove")) {
			if(args.length != 3) {
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/strike remove §7<Spieler> <Zahl>");
				return;
			}

			VaroPlayer varoPlayer = VaroPlayer.getPlayer(args[1]);

			if(varoPlayer == null) {
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + args[1] + " §7nicht gefunden!");
				return;
			}

			if(varoPlayer.getStats().getStrikes().size() < 1) {
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + varoPlayer.getName() + " §7hat keine Strikes!");
				return;
			}

			int num = -1;
			try {
				num = Integer.parseInt(args[2]) - 1;
			} catch(NumberFormatException e) {
				sender.sendMessage(Main.getPrefix() + args[2] + " ist keine Zahl!");
				return;
			}

			if(num >= varoPlayer.getStats().getStrikes().size()) {
				sender.sendMessage(Main.getPrefix() + "Strike " + args[2] + " nicht gefunden!");
				return;
			}

			varoPlayer.getStats().removeStrike(varoPlayer.getStats().getStrikes().get(num));
			sender.sendMessage(Main.getPrefix() + "§7Du hast " + Main.getColorCode() + vp.getName() + " §7einen Strike entfernt! Er hat noch " + Main.getColorCode() + vp.getStats().getStrikes().size() + " §7strikes!");
		} else if(args[0].equalsIgnoreCase("list")) {
			if(args.length != 2) {
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/strike list §7<Spieler>");
				return;
			}

			VaroPlayer varoPlayer = VaroPlayer.getPlayer(args[1]);

			if(varoPlayer == null) {
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + args[1] + " §7nicht gefunden!");
				return;
			}

			if(varoPlayer.getStats().getStrikes().size() < 1) {
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + varoPlayer.getName() + " §7hat keine Strikes!");
				return;
			}

			sender.sendMessage(Main.getPrefix() + "Strikes von " + Main.getColorCode() + varoPlayer.getName() + "§7:");
			for(int i = 1; i < varoPlayer.getStats().getStrikes().size(); i++) {
				Strike strike = varoPlayer.getStats().getStrikes().get(i);
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "Strike Nr." + i + "§8:");
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "Reason: §7" + strike.getReason());
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "Striker: §7" + strike.getStriker());
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "Acquired: §7" + new SimpleDateFormat("dd:MM:yyy HH:mm").format(strike.getAcquiredDate()));
			}
		} else
			sender.sendMessage(Main.getPrefix() + "§7Nicht gefunden! " + Main.getColorCode() + "/strike §7für hilfe.");
		return;

	}

}
