package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.entity.player.VaroPlayer;

public class BackpackCommand extends VaroCommand {

	public BackpackCommand() {
		super("backpack", "Öffnet das Backpack von dir oder einem Spieler", null, "bp");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if(!Main.getGame().hasStarted()) {
			sender.sendMessage(Main.getPrefix() + "Spiel wurde noch nicht gestaret!");
			return;
		}

		if(vp == null) {
			sender.sendMessage(Main.getPrefix() + "Du musst ein Spieler sein!");
			return;
		}

		if(!ConfigEntry.BACKPACK_ALLOW.getValueAsBoolean()) {
			sender.sendMessage(Main.getPrefix() + "Rucksäcke sind nicht aktiviert!");
			return;
		}

		if(args.length == 0) {
			vp.getPlayer().openInventory(vp.getStats().getBackpack().getInventory());
		} else if(args.length == 1) {
			VaroPlayer p = VaroPlayer.getPlayer(args[0]);
			if(!vp.getPlayer().isOp()) {
				if(p == null) {
					sender.sendMessage(Main.getPrefix() + Main.getColorCode() + args[0] + " §7nicht gefunden!");
					return;
				}

				if(vp.getTeam() == null || !vp.getTeam().equals(p.getTeam())) {
					sender.sendMessage(Main.getPrefix() + Main.getColorCode() + args[0] + " §7ist nicht in deinem Team!");
					return;
				}

				if(!ConfigEntry.BACKPACK_ALLOW_TEAMACCESS.getValueAsBoolean()) {
					sender.sendMessage(Main.getPrefix() + "Du darfst nicht auf den Inhalt des Rucksacks deines Teampartners zugreifen!");
					return;
				}
			}

			vp.getPlayer().openInventory(p.getStats().getBackpack().getInventory());
		} else
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo bp §7[Player]");
	}
}