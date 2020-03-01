package de.cuuky.varo.command.varo;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.placeholder.MessagePlaceholder;
import de.cuuky.varo.configuration.placeholder.placeholder.GeneralMessagePlaceholder;
import de.cuuky.varo.configuration.placeholder.placeholder.PlayerMessagePlaceholder;
import de.cuuky.varo.entity.player.VaroPlayer;

public class PlaceholderCommand extends VaroCommand {

	public PlaceholderCommand() {
		super("placeholder", "Zeigt alle Platzhalter fuer messages, scoreboard etc.", "varo.placeholder", "ph");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if(args.length == 0) {
			sender.sendMessage(Main.getPrefix() + Main.getProjectName() + " §7Placeholder Befehle:");
			sender.sendMessage(Main.getPrefix());
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo placeholder §7info <name> §8- §7Zeigt Wert und Info vom gegebenen Placeholder");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo placeholder §7general §8- §7Zeigt alle ueberall anwendbaren Placeholder");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo placeholder §7player §8- §7Zeigt alle im Spielerkontext anwendbaren Placeholder");
			sender.sendMessage(Main.getPrefix());
			sender.sendMessage(Main.getPrefix() + "Player-Beispiele: Killmessage, Scoreboard, Kickmessage, Tab");
			return;
		}

		if(args[0].equalsIgnoreCase("info") || args[0].equalsIgnoreCase("get")) {
			if(args.length != 2) {
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo placeholder §7get <name> §8- §7Zeigt Wert vom gegebenen Placeholder");
				return;
			}

			MessagePlaceholder mp = null;
			for(MessagePlaceholder mp1 : MessagePlaceholder.getPlaceholder()) {
				if(mp1.getIdentifier().replace("%", "").equalsIgnoreCase(args[1].replace("%", ""))) {
					mp = mp1;
				}
			}

			if(mp == null) {
				sender.sendMessage(Main.getPrefix() + "Placeholder nicht gefunden!");
				return;
			}

			String value = "/";
			if(mp instanceof PlayerMessagePlaceholder) {
				if(vp != null)
					value = "(" + vp.getName() + ") " + ((PlayerMessagePlaceholder) mp).replacePlaceholder(mp.getIdentifier(), vp);
			} else if(mp instanceof GeneralMessagePlaceholder)
				value = ((GeneralMessagePlaceholder) mp).replacePlaceholder(mp.getIdentifier());
			else {
				sender.sendMessage(Main.getPrefix() + "Undefinierter Placeholder gefunden!?");
				return;
			}

			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + mp.getIdentifier() + " §7Info§8:");
			sender.sendMessage(Main.getPrefix() + "§7Wert§8: " + Main.getColorCode() + value);
			sender.sendMessage(Main.getPrefix() + "§7Refresh-Delay§8: " + Main.getColorCode() + mp.getDefaultRefresh() + "s");
			return;
		}

		ArrayList<MessagePlaceholder> placeholders = new ArrayList<>();
		if(args[0].equalsIgnoreCase("general")) {
			for(MessagePlaceholder mp : MessagePlaceholder.getPlaceholder())
				if(mp instanceof GeneralMessagePlaceholder)
					placeholders.add(mp);
		} else if(args[0].equalsIgnoreCase("player")) {
			for(MessagePlaceholder mp : MessagePlaceholder.getPlaceholder())
				if(mp instanceof PlayerMessagePlaceholder)
					placeholders.add(mp);
		}

		if(placeholders.isEmpty()) {
			sender.sendMessage(Main.getPrefix() + "Falsche Argumente! §c/varo ph");
			return;
		}

		sender.sendMessage(Main.getPrefix() + "- Placeholder -");
		for(MessagePlaceholder mp : placeholders)
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + mp.getIdentifier() + " §8- §7" + mp.getDescription());

		if(args[0].equalsIgnoreCase("general")) {
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "%topplayer-<RANK>% §8- §7Ersetzt durch den Spieler, der an RANK auf dem Leaderboard ist");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "%topplayerkills-<RANK>% §8- §7Ersetzt durch die Kills des Spielers, der an RANK auf dem Leaderboard ist");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "%topteam-<RANK>% §8- §7Ersetzt durch das Team, das an RANK auf dem Leaderboard ist");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "%topteamkills-<RANK>% §8- §7Ersetzt durch die Kills des Teams, das an RANK auf dem Leaderboard ist");
		}

		sender.sendMessage(Main.getPrefix() + "----------------");
	}
}