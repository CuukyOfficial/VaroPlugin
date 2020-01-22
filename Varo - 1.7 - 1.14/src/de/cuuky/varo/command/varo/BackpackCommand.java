package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.config.ConfigEntry;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.team.Team;
import de.cuuky.varo.game.Game;

public class BackpackCommand extends VaroCommand {

	public BackpackCommand() {
		super("backpack", "Öffnet das Backpack von dir oder einem Spieler", null, "bp");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if(!Game.getInstance().hasStarted()) {
			sender.sendMessage(Main.getPrefix() + "Spiel wurde noch nicht gestartet!");
			return;
		}

		if(vp == null) {
			sender.sendMessage(Main.getPrefix() + "Du musst ein Spieler sein!");
			return;
		}

		if(ConfigEntry.BACKPACK_PLAYER_ENABLED.getValueAsBoolean() && ConfigEntry.BACKPACK_TEAM_ENABLED.getValueAsBoolean()) {
			if(args.length == 0 || (!args[0].equalsIgnoreCase("player") && !args[0].equalsIgnoreCase("team"))) {
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "Es wurden sowohl Spieler als auch Team-Backpacks aktiviert");
				if(vp.getPlayer().isOp()) {
					sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo bp player §7[Player]");
					sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo bp team §7[Team]");
				} else {
					sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo bp player");
					sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo bp team");
				}
			} else if(args[0].equalsIgnoreCase("player")) {
				playerBackPack(sender, vp, args, 1);
			} else {
				teamBackPack(sender, vp, args, 1);
			}
		} else if(ConfigEntry.BACKPACK_PLAYER_ENABLED.getValueAsBoolean() && !ConfigEntry.BACKPACK_TEAM_ENABLED.getValueAsBoolean()) {
			playerBackPack(sender, vp, args, 0);
		} else if(!ConfigEntry.BACKPACK_PLAYER_ENABLED.getValueAsBoolean() && ConfigEntry.BACKPACK_TEAM_ENABLED.getValueAsBoolean()) {
			teamBackPack(sender, vp, args, 0);
		} else {
			sender.sendMessage(Main.getPrefix() + "Rucksäcke sind nicht aktiviert!");
		}
	}

	private void playerBackPack(CommandSender sender, VaroPlayer vp, String[] args, int number) {
		if(vp.getPlayer().isOp() && args.length > number) {
			VaroPlayer p = VaroPlayer.getPlayer(args[number]);
			if(p != null) {
				vp.getPlayer().openInventory(p.getStats().getPlayerBackpack().getInventory());
			} else {
				sender.sendMessage("Der Spieler " + args[number] + " existiert nicht.");
				sender.sendMessage("Daher kann dessen Rucksack dir nicht angezeigt werden.");
			}
			return;
		}

		vp.getPlayer().openInventory(vp.getStats().getPlayerBackpack().getInventory());
		return;
	}

	private void teamBackPack(CommandSender sender, VaroPlayer vp, String[] args, int number) {
		if(vp.getPlayer().isOp() && args.length > number) {
			Team t = Team.getTeam(args[number]);
			if(t != null) {
				vp.getPlayer().openInventory(t.getTeamBackPack().getInventory());
			} else {
				sender.sendMessage("Das Team " + args[number] + " existiert nicht.");
				sender.sendMessage("Daher kann dessen Teamrucksack dir nicht angezeigt werden.");
			}
			return;
		}
		if(vp.getTeam() == null) {
			sender.sendMessage("Du bist in keinem Team. Daher hast du kein Team-Backpack.");
		} else {
			vp.getPlayer().openInventory(vp.getTeam().getTeamBackPack().getInventory());
		}
		return;
	}
}