package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.config.language.Messages;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.team.VaroTeam;
import io.github.almightysatan.slams.Placeholder;

public class BackpackCommand extends VaroCommand {

	public BackpackCommand() {
		super("backpack", "Oeffnet das Backpack von dir oder einem Spieler", null, "bp");
	}

	private void playerBackPack(VaroPlayer vp, String[] args, int number) {
		if (vp.getPlayer().isOp() && args.length > number) {
			VaroPlayer p = VaroPlayer.getPlayer(args[number]);
			if (p != null) {
				vp.getPlayer().openInventory(p.getStats().getPlayerBackpack().getInventory());
			} else {
				Messages.COMMANDS_VARO_BACKPACK_PLAYER_DOESNT_EXIST.send(vp, Placeholder.constant("target", args[number]));
			}
			return;
		}

		vp.getPlayer().openInventory(vp.getStats().getPlayerBackpack().getInventory());
		return;
	}

	private void teamBackPack(VaroPlayer vp, String[] args, int number) {
		if (vp.getPlayer().isOp() && args.length > number) {
			VaroTeam t = VaroTeam.getTeam(args[number]);
			if (t != null) {
				vp.getPlayer().openInventory(t.getTeamBackPack().getInventory());
			} else {
			    Messages.COMMANDS_VARO_BACKPACK_TEAM_DOESNT_EXIST.send(vp, Placeholder.constant("target", args[number]));
			}
			return;
		}
		if (vp.getTeam() == null) {
		    Messages.COMMANDS_VARO_BACKPACK_NO_TEAM.send(vp);
		} else {
			vp.getPlayer().openInventory(vp.getTeam().getTeamBackPack().getInventory());
		}
		return;
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (!Main.getVaroGame().hasStarted()) {
			Messages.COMMANDS_ERROR_NOT_STARTED.send(vp);
			return;
		}

		if (vp == null) {
			Messages.COMMANDS_ERROR_NO_CONSOLE.send(vp);
			return;
		}

		if (ConfigSetting.BACKPACK_PLAYER_ENABLED.getValueAsBoolean() && ConfigSetting.BACKPACK_TEAM_ENABLED.getValueAsBoolean()) {
			if (args.length == 0 || (!args[0].equalsIgnoreCase("player") && !args[0].equalsIgnoreCase("team"))) {
				Messages.COMMANDS_VARO_BACKPACK_CHOOSE_TYPE.send(vp);
				if (vp.getPlayer().isOp()) {
					sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " bp player §7[Player]");
					sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " bp team §7[Team]");
				} else {
					sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " bp player");
					sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " bp team");
				}
			} else if (args[0].equalsIgnoreCase("player")) {
				playerBackPack(vp, args, 1);
			} else {
				teamBackPack(vp, args, 1);
			}
		} else if (ConfigSetting.BACKPACK_PLAYER_ENABLED.getValueAsBoolean() && !ConfigSetting.BACKPACK_TEAM_ENABLED.getValueAsBoolean()) {
			playerBackPack(vp, args, 0);
		} else if (!ConfigSetting.BACKPACK_PLAYER_ENABLED.getValueAsBoolean() && ConfigSetting.BACKPACK_TEAM_ENABLED.getValueAsBoolean()) {
			teamBackPack(vp, args, 0);
		} else {
			Messages.COMMANDS_VARO_BACKPACK_NOT_ENABLED.send(vp);
		}
	}
}