package de.cuuky.varo.command.varo;

import java.awt.Color;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.bot.discord.register.BotRegister;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.messages.language.languages.LanguageDE;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.gui.admin.discordbot.DiscordBotGUI;
import net.dv8tion.jda.api.entities.User;

public class DiscordCommand extends VaroCommand {

	public DiscordCommand() {
		super("discord", "Der Hauptbefehl fuer den DiscordBot", null);
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if(args.length == 0) {
			sender.sendMessage(Main.getPrefix() + "§7----- " + Main.getColorCode() + "Discord-Commands §7-----");
			sender.sendMessage(Main.getPrefix() + Main.getLanguageManager().getValue(LanguageDE.VARO_COMMANDS_HELP_HEADER, vp).replace("%category%", "Discord"));

			if(sender.hasPermission("varo.discord")) {
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo discord getLink §7<Spieler>");
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo discord unlink §7<Spieler>");
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo discord bypassRegister §7<Spieler> <true/false>");
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo discord sendMessage §7<Nachricht>");
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo discord reload");
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo discord shutdown");
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo discord settings");
			}
			sender.sendMessage(Main.getPrefix() + Main.getLanguageManager().getValue(LanguageDE.VARO_COMMANDS_HELP_FOOTER, vp));
			return;
		}

		if(Main.getBotLauncher().getDiscordbot() == null) {
			sender.sendMessage(Main.getPrefix() + Main.getLanguageManager().getValue(LanguageDE.VARO_COMMANDS_DISCORD_NOT_SETUP, vp));
			return;
		}

		if(args[0].equalsIgnoreCase("verify")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage(Main.getPrefix() + Main.getLanguageManager().getValue(LanguageDE.VARO_COMMANDS_ERROR_NO_CONSOLE, vp));
				return;
			}

			BotRegister reg = BotRegister.getRegister(vp.getUuid()) == null ? new BotRegister(vp.getUuid(), true) : BotRegister.getRegister(vp.getUuid());
			reg.setPlayerName(vp.getName());
			if(args.length == 1) {
				String status = Main.getLanguageManager().getValue(LanguageDE.VARO_COMMANDS_DISCORD_INACTIVE, vp);
				if(reg.isActive()) {
					status = Main.getLanguageManager().getValue(LanguageDE.VARO_COMMANDS_DISCORD_ACTIVE, vp);
				}
				sender.sendMessage(Main.getPrefix() + Main.getLanguageManager().getValue(LanguageDE.VARO_COMMANDS_DISCORD_STATUS, vp).replace("%status%", status));
				if(!reg.isActive())
					sender.sendMessage(reg.getKickMessage(vp));
				else {
					sender.sendMessage(Main.getPrefix() + Main.getLanguageManager().getValue(LanguageDE.VARO_COMMANDS_DISCORD_VERIFY_ACCOUNT, vp).replace("%account%", reg.getMember().getNickname()));
					sender.sendMessage(Main.getPrefix() + Main.getLanguageManager().getValue(LanguageDE.VARO_COMMANDS_DISCORD_VERIFY_REMOVE_USAGE, vp));
				}
			} else if(args[1].equals("remove")) {
				if(!reg.isActive()) {
					sender.sendMessage(Main.getPrefix() + Main.getLanguageManager().getValue(LanguageDE.VARO_COMMANDS_DISCORD_NOT_VERIFIED, vp));
					return;
				}

				reg.delete();
				sender.sendMessage(Main.getPrefix() + Main.getLanguageManager().getValue(LanguageDE.VARO_COMMANDS_DISCORD_VERIFICATION_REMOVED, vp));
			}

			return;
		} else if(sender.hasPermission("varo.discord")) {
			sender.sendMessage(Main.getPrefix() + Main.getLanguageManager().getValue(LanguageDE.VARO_COMMANDS_ERROR_USAGE, vp).replace("%command%", "discord"));
			return;
		}

		BotRegister reg = null;
		try {
			reg = BotRegister.getBotRegisterByPlayerName(args[1]);
		} catch(Exception e) {}

		if(args[0].equalsIgnoreCase("getLink") || args[0].equalsIgnoreCase("link")) {
			if(!ConfigSetting.DISCORDBOT_VERIFYSYSTEM.getValueAsBoolean()) {
				sender.sendMessage(Main.getPrefix() + Main.getLanguageManager().getValue(LanguageDE.VARO_COMMANDS_DISCORD_VERIFY_SYSTEM_DISABLED, vp));
				return;
			}

			if(Main.getBotLauncher().getDiscordbot() == null || !Main.getBotLauncher().getDiscordbot().isEnabled()) {
				sender.sendMessage(Main.getPrefix() + Main.getLanguageManager().getValue(LanguageDE.VARO_COMMANDS_DISCORD_DISCORDBOT_DISABLED, vp));
				return;
			}

			if(reg == null) {
				sender.sendMessage(Main.getPrefix() + Main.getLanguageManager().getValue(LanguageDE.VARO_COMMANDS_ERROR_UNKNOWN_PLAYER, vp).replace("%player%", args[1]));
				return;
			}

			User user = Main.getBotLauncher().getDiscordbot().getJda().getUserById(reg.getUserId());
			if(user == null) {
				sender.sendMessage(Main.getPrefix() + Main.getLanguageManager().getValue(LanguageDE.VARO_COMMANDS_ERROR_USER_NOT_FOUND, vp));
				return;
			}

			sender.sendMessage(Main.getPrefix() + "§7Der Discord Account von " + args[1] + " heisst: " + Main.getColorCode() + user.getName() + "§7 und die ID lautet " + Main.getColorCode() + user.getId() + "§7!");
			sender.sendMessage(Main.getPrefix() + Main.getLanguageManager().getValue(LanguageDE.VARO_COMMANDS_DISCORD_GETLINK, vp).replace("%player%", args[1]).replace("%user%", user.getName().replace("%id%", user.getId())));
		} else if(args[0].equalsIgnoreCase("unlink")) {
			if(!ConfigSetting.DISCORDBOT_VERIFYSYSTEM.getValueAsBoolean()) {
				sender.sendMessage(Main.getPrefix() + Main.getLanguageManager().getValue(LanguageDE.VARO_COMMANDS_DISCORD_VERIFY_SYSTEM_DISABLED, vp));
				return;
			}

			if(Main.getBotLauncher().getDiscordbot() == null || !Main.getBotLauncher().getDiscordbot().isEnabled()) {
				sender.sendMessage(Main.getPrefix() + Main.getLanguageManager().getValue(LanguageDE.VARO_COMMANDS_DISCORD_DISCORDBOT_DISABLED, vp));
				return;
			}

			if(reg == null) {
				sender.sendMessage(Main.getPrefix() + Main.getLanguageManager().getValue(LanguageDE.VARO_COMMANDS_ERROR_UNKNOWN_PLAYER, vp).replace("%player%", args[1]));
				return;
			}

			reg.setUserId(-1);
			sender.sendMessage(Main.getPrefix() + Main.getLanguageManager().getValue(LanguageDE.VARO_COMMANDS_DISCORD_UNVERIFY, vp).replace("%player%", args[1]));
			
			Player target = Bukkit.getPlayerExact(reg.getPlayerName());
			if(target != null)
				Bukkit.getPlayerExact(reg.getPlayerName()).kickPlayer(reg.getKickMessage(VaroPlayer.getPlayer(target)));
		} else if(args[0].equalsIgnoreCase("bypassRegister") || args[0].equalsIgnoreCase("bypass")) {
			if(!ConfigSetting.DISCORDBOT_VERIFYSYSTEM.getValueAsBoolean()) {
				sender.sendMessage(Main.getPrefix() + Main.getLanguageManager().getValue(LanguageDE.VARO_COMMANDS_DISCORD_VERIFY_SYSTEM_DISABLED, vp));
				return;
			}

			if(Main.getBotLauncher().getDiscordbot() == null || !Main.getBotLauncher().getDiscordbot().isEnabled()) {
				sender.sendMessage(Main.getPrefix() + Main.getLanguageManager().getValue(LanguageDE.VARO_COMMANDS_DISCORD_DISCORDBOT_DISABLED, vp));
				return;
			}

			if(reg == null) {
				sender.sendMessage(Main.getPrefix() + Main.getLanguageManager().getValue(LanguageDE.VARO_COMMANDS_ERROR_UNKNOWN_PLAYER, vp).replace("%player%", args[1]));
				return;
			}

			if(args.length != 3) {
				sender.sendMessage(Main.getPrefix() + "§7/varo discord bypass <Spieler> <true/false>");
				return;
			}

			if(args[2].equalsIgnoreCase("true") || args[2].equalsIgnoreCase("false")) {
				reg.setBypass(args[2].equalsIgnoreCase("true") ? true : false);
				if(reg.isBypass()) {
					sender.sendMessage(Main.getPrefix() + Main.getLanguageManager().getValue(LanguageDE.VARO_COMMANDS_DISCORD_BYPASS_ACTIVE, vp).replace("%player%", args[1]));
				} else {
					sender.sendMessage(Main.getPrefix() + Main.getLanguageManager().getValue(LanguageDE.VARO_COMMANDS_DISCORD_BYPASS_INACTIVE, vp).replace("%player%", args[1]));
				}

			} else
				sender.sendMessage(Main.getPrefix() + "§7/varo discord bypass <add/remove> <Spielername>");
		} else if(args[0].equalsIgnoreCase("reload")) {
			Main.getBotLauncher().getDiscordbot().disconnect();
			Main.getBotLauncher().getDiscordbot().connect();
			for(Player pl : Bukkit.getOnlinePlayers())
				if(ConfigSetting.DISCORDBOT_VERIFYSYSTEM.getValueAsBoolean() && BotRegister.getBotRegisterByPlayerName(pl.getName()) == null)
					pl.kickPlayer(Main.getLanguageManager().getValue(LanguageDE.VARO_COMMANDS_DISCORD_VERIFY_ENABLED, vp));
			sender.sendMessage(Main.getPrefix() + Main.getLanguageManager().getValue(LanguageDE.VARO_COMMANDS_DISCORD_RELOADED, vp));
		} else if(args[0].equalsIgnoreCase("settings")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage(Main.getPrefix() + Main.getLanguageManager().getValue(LanguageDE.VARO_COMMANDS_ERROR_NO_CONSOLE, vp));
				return;
			}

			new DiscordBotGUI((Player) sender);
		} else if(args[0].equalsIgnoreCase("shutdown")) {
			if(Main.getBotLauncher().getDiscordbot().getJda() == null) {
				sender.sendMessage(Main.getPrefix() + Main.getLanguageManager().getValue(LanguageDE.VARO_COMMANDS_DISCORD_BOT_OFFLINE, vp));
				return;
			}

			sender.sendMessage(Main.getPrefix() + Main.getLanguageManager().getValue(LanguageDE.VARO_COMMANDS_DISCORD_SHUTDOWN, vp));
			Main.getBotLauncher().getDiscordbot().disconnect();
		} else if(args[0].equalsIgnoreCase("sendMessage")) {
			if(Main.getBotLauncher().getDiscordbot() == null || !Main.getBotLauncher().getDiscordbot().isEnabled()) {
				sender.sendMessage(Main.getPrefix() + Main.getLanguageManager().getValue(LanguageDE.VARO_COMMANDS_DISCORD_DISCORDBOT_DISABLED, vp));
				return;
			}

			if(Main.getBotLauncher().getDiscordbot().getEventChannel() == null) {
				sender.sendMessage(Main.getPrefix() + Main.getLanguageManager().getValue(LanguageDE.VARO_COMMANDS_DISCORD_NO_EVENT_CHANNEL, vp));
				return;
			}

			String message = "";
			for(String ar : args) {
				if(ar.equals(args[0]))
					continue;
				if(message.equals(""))
					message = ar;
				else
					message = message + " " + ar;
			}

			Main.getBotLauncher().getDiscordbot().sendMessage(message, "MESSAGE", Color.YELLOW, Main.getBotLauncher().getDiscordbot().getEventChannel());
		} else
			sender.sendMessage(Main.getPrefix() + Main.getLanguageManager().getValue(LanguageDE.VARO_COMMANDS_ERROR_USAGE, vp).replace("%command%", "discord"));
		return;
	}
}