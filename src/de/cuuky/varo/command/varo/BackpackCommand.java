package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.team.VaroTeam;

public class BackpackCommand extends VaroCommand {

	public BackpackCommand() {
		super("backpack", "Oeffnet das Backpack von dir oder einem Spieler", null, "bp");
	}

	private void playerBackPack(CommandSender sender, VaroPlayer vp, String[] args, int number) {
		if (vp.getPlayer().isOp() && args.length > number) {
			VaroPlayer p = VaroPlayer.getPlayer(args[number]);
			if (p != null) {
				vp.getPlayer().openInventory(p.getStats().getPlayerBackpack().getInventory());
			} else {
				sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_BACKPACK_PLAYER_DOESNT_EXIST.getValue(vp, p));
				sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_BACKPACK_CANT_SHOW_BACKPACK.getValue(vp, p));
			}
			return;
		}

		vp.getPlayer().openInventory(vp.getStats().getPlayerBackpack().getInventory());
		return;
	}

	private void teamBackPack(CommandSender sender, VaroPlayer vp, String[] args, int number) {
		if (vp.getPlayer().isOp() && args.length > number) {
			VaroTeam t = VaroTeam.getTeam(args[number]);
			if (t != null) {
				vp.getPlayer().openInventory(t.getTeamBackPack().getInventory());
			} else {
				sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_BACKPACK_TEAM_DOESNT_EXIST.getValue(vp).replace("%team%", args[number]));
				sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_BACKPACK_CANT_SHOW_BACKPACK.getValue(vp));
			}
			return;
		}
		if (vp.getTeam() == null) {
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_BACKPACK_NO_TEAM.getValue(vp));
		} else {
			vp.getPlayer().openInventory(vp.getTeam().getTeamBackPack().getInventory());
		}
		return;
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (!Main.getVaroGame().hasStarted()) {
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_ERROR_NOT_STARTED.getValue(vp));
			return;
		}

		if (vp == null) {
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_ERROR_NO_CONSOLE.getValue(vp));
			return;
		}

		if (ConfigSetting.BACKPACK_PLAYER_ENABLED.getValueAsBoolean() && ConfigSetting.BACKPACK_TEAM_ENABLED.getValueAsBoolean()) {
			if (args.length == 0 || (!args[0].equalsIgnoreCase("player") && !args[0].equalsIgnoreCase("team"))) {
				sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_BACKPACK_CHOOSE_TYPE.getValue(vp));
				if (vp.getPlayer().isOp()) {
					sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " bp player §7[Player]");
					sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " bp team §7[Team]");
				} else {
					sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " bp player");
					sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " bp team");
				}
			} else if (args[0].equalsIgnoreCase("player")) {
				playerBackPack(sender, vp, args, 1);
			} else {
				teamBackPack(sender, vp, args, 1);
			}
		} else if (ConfigSetting.BACKPACK_PLAYER_ENABLED.getValueAsBoolean() && !ConfigSetting.BACKPACK_TEAM_ENABLED.getValueAsBoolean()) {
			playerBackPack(sender, vp, args, 0);
		} else if (!ConfigSetting.BACKPACK_PLAYER_ENABLED.getValueAsBoolean() && ConfigSetting.BACKPACK_TEAM_ENABLED.getValueAsBoolean()) {
			teamBackPack(sender, vp, args, 0);
		} else {
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_BACKPACK_NOT_ENABLED.getValue(vp));
		}
	}
}