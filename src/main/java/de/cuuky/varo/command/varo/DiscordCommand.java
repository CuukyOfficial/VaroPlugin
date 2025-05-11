package de.cuuky.varo.command.varo;

import java.awt.Color;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.bot.discord.BotRegister;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.config.language.Messages;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.gui.admin.discordbot.DiscordBotGUI;
import de.cuuky.varo.player.VaroPlayer;
import io.github.almightysatan.slams.Placeholder;
import io.github.almightysatan.slams.PlaceholderResolver;
import net.dv8tion.jda.api.entities.User;

public class DiscordCommand extends VaroCommand {

	public DiscordCommand() {
		super("discord", "Der Hauptbefehl fuer den DiscordBot", null);
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(Main.getPrefix() + "§7----- " + Main.getColorCode() + "Discord-Commands §7-----");
			Messages.CATEGORY_HEADER.send(sender, Placeholder.constant("category", "Discord"));
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " discord verify §7<Spieler>");

			if (sender.hasPermission("varo.discord")) {
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " discord getLink §7<Spieler>");
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " discord unlink §7<Spieler>");
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " discord bypassRegister §7<Spieler> <true/false>");
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " discord sendMessage §7<Nachricht>");
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " discord reload");
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " discord shutdown");
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " discord settings");
			}
			Messages.CATEGORY_FOOTER.send(sender, Placeholder.constant("category", "Discord"));
			return;
		}

		if (Main.getBotLauncher().getDiscordbot() == null) {
			Messages.COMMANDS_VARO_DISCORD_NOT_SETUP.send(sender);
			return;
		}

		if (args[0].equalsIgnoreCase("verify")) {
			if (!(sender instanceof Player)) {
			    Messages.COMMANDS_ERROR_NO_CONSOLE.send(sender);
				return;
			}

			BotRegister reg = BotRegister.getRegister(vp.getUUID()) == null ? new BotRegister(vp.getUUID(), true) : BotRegister.getRegister(vp.getUUID());
			reg.setPlayerName(vp.getName());
			if (args.length == 1) {
				if (!reg.isActive()) {
				    Messages.COMMANDS_VARO_DISCORD_STATUS_INACTIVE.send(sender);
					Messages.PLAYER_KICK_DISCORD_NOT_REGISTERED.send(sender, Placeholder.constant("discord-code", String.valueOf(reg.getCode())));
				} else {
				    Messages.COMMANDS_VARO_DISCORD_STATUS_ACTIVE.send(sender);
					Messages.COMMANDS_VARO_DISCORD_VERIFY_ACCOUNT.send(sender, Placeholder.constant("target", reg.getMember().getNickname()));
					Messages.COMMANDS_VARO_DISCORD_VERIFY_REMOVE_USAGE.send(sender);
				}
			} else if (args[1].equals("remove")) {
				if (!reg.isActive()) {
					Messages.COMMANDS_VARO_DISCORD_NOT_VERIFIED.send(sender);
					return;
				}

				reg.delete();
				Messages.COMMANDS_VARO_DISCORD_VERIFICATION_REMOVED.send(sender);
			}

			return;
		} else if (!sender.hasPermission("varo.discord")) {
		    Messages.COMMANDS_ERROR_USAGE.send(sender, Placeholder.constant("command", "discord"));
			return;
		}

		BotRegister reg = null;
		try {
			reg = BotRegister.getBotRegisterByPlayerName(args[1]);
		} catch (Exception e) {} // wtf

		if (args[0].equalsIgnoreCase("getLink") || args[0].equalsIgnoreCase("link")) {
			if (!ConfigSetting.DISCORDBOT_VERIFYSYSTEM.getValueAsBoolean()) {
				Messages.COMMANDS_VARO_DISCORD_VERIFY_SYSTEM_DISABLED.send(sender);
				return;
			}

			if (Main.getBotLauncher().getDiscordbot() == null || !Main.getBotLauncher().getDiscordbot().isEnabled()) {
			    Messages.COMMANDS_VARO_DISCORD_DISCORDBOT_DISABLED.send(sender);
				return;
			}

			if (args.length != 2) {
                sender.sendMessage(Main.getPrefix() + "§7/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " discord link <Spieler>");
                return;
            }

			if (reg == null) {
				Messages.COMMANDS_ERROR_UNKNOWN_PLAYER.send(sender, Placeholder.constant("target", args[1]));
				return;
			}

			User user = Main.getBotLauncher().getDiscordbot().getJda().getUserById(reg.getUserId());
			if (user == null) {
				Messages.COMMANDS_ERROR_USER_NOT_FOUND.send(sender);
				return;
			}

			Messages.COMMANDS_VARO_DISCORD_GETLINK.send(sender, PlaceholderResolver.builder().constant("target-name", args[1]).constant("target-discord-name", user.getName()).constant("target-discord-id", user.getId()).build());
		} else if (args[0].equalsIgnoreCase("unlink")) {
			if (!ConfigSetting.DISCORDBOT_VERIFYSYSTEM.getValueAsBoolean()) {
				Messages.COMMANDS_VARO_DISCORD_VERIFY_SYSTEM_DISABLED.send(sender);
				return;
			}

			if (Main.getBotLauncher().getDiscordbot() == null || !Main.getBotLauncher().getDiscordbot().isEnabled()) {
				Messages.COMMANDS_VARO_DISCORD_DISCORDBOT_DISABLED.send(sender);
				return;
			}

			if (args.length != 2) {
                sender.sendMessage(Main.getPrefix() + "§7/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " discord unlink <Spieler>");
                return;
            }

			if (reg == null) {
				Messages.COMMANDS_ERROR_UNKNOWN_PLAYER.send(sender, Placeholder.constant("target", args[1]));
				return;
			}

			reg.setUserId(-1);
			Messages.COMMANDS_VARO_DISCORD_UNVERIFY.send(sender, Placeholder.constant("target", args[1]));

			Player target = Bukkit.getPlayerExact(reg.getPlayerName());
			if (target != null)
				Bukkit.getPlayerExact(reg.getPlayerName()).kickPlayer(Messages.PLAYER_KICK_DISCORD_NOT_REGISTERED.value(Placeholder.constant("discord-code", String.valueOf(reg.getCode()))));
		} else if (args[0].equalsIgnoreCase("bypassRegister") || args[0].equalsIgnoreCase("bypass")) {
			if (!ConfigSetting.DISCORDBOT_VERIFYSYSTEM.getValueAsBoolean()) {
				Messages.COMMANDS_VARO_DISCORD_VERIFY_DISABLED.send(sender);
				return;
			}

			if (Main.getBotLauncher().getDiscordbot() == null || !Main.getBotLauncher().getDiscordbot().isEnabled()) {
			    Messages.COMMANDS_VARO_DISCORD_DISCORDBOT_DISABLED.send(sender);
				return;
			}

			if (args.length != 3) {
                sender.sendMessage(Main.getPrefix() + "§7/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " discord bypass <Spieler> <true/false>");
                return;
            }

			if (reg == null) {
				Messages.COMMANDS_ERROR_UNKNOWN_PLAYER.send(sender, Placeholder.constant("target", args[1]));
				return;
			}

			if (args[2].equalsIgnoreCase("true") || args[2].equalsIgnoreCase("false")) {
				reg.setBypass(args[2].equalsIgnoreCase("true") ? true : false);
				if (reg.isBypass()) {
					Messages.COMMANDS_VARO_DISCORD_BYPASS_ACTIVE.send(sender, Placeholder.constant("target", args[1]));
				} else {
				    Messages.COMMANDS_VARO_DISCORD_BYPASS_INACTIVE.send(sender, Placeholder.constant("target", args[1]));
				}

			} else
				sender.sendMessage(Main.getPrefix() + "§7/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " discord bypass <add/remove> <Spielername>");
		} else if (args[0].equalsIgnoreCase("reload")) {
			Main.getBotLauncher().getDiscordbot().disconnect();
			Main.getBotLauncher().getDiscordbot().connect();
			for (Player pl : Bukkit.getOnlinePlayers())
				if (ConfigSetting.DISCORDBOT_VERIFYSYSTEM.getValueAsBoolean() && BotRegister.getBotRegisterByPlayerName(pl.getName()) == null)
				    Messages.COMMANDS_VARO_DISCORD_VERIFY_ENABLED.kick(VaroPlayer.getPlayer(pl));
			Messages.COMMANDS_VARO_DISCORD_RELOADED.send(sender);
		} else if (args[0].equalsIgnoreCase("settings")) {
			if (!(sender instanceof Player)) {
				Messages.COMMANDS_ERROR_NO_CONSOLE.send(sender);
				return;
			}

			new DiscordBotGUI((Player) sender);
		} else if (args[0].equalsIgnoreCase("shutdown")) {
			if (Main.getBotLauncher().getDiscordbot().getJda() == null) {
			    Messages.COMMANDS_VARO_DISCORD_BOT_OFFLINE.send(sender);
				return;
			}

			Messages.COMMANDS_VARO_DISCORD_SHUTDOWN.send(sender);
			Main.getBotLauncher().getDiscordbot().disconnect();
		} else if (args[0].equalsIgnoreCase("sendMessage")) {
			if (Main.getBotLauncher().getDiscordbot() == null || !Main.getBotLauncher().getDiscordbot().isEnabled()) {
				Messages.COMMANDS_VARO_DISCORD_DISCORDBOT_DISABLED.send(sender);
				return;
			}

			String message = "";
			for (String ar : args) {
				if (ar.equals(args[0]))
					continue;
				if (message.equals(""))
					message = ar;
				else
					message = message + " " + ar;
			}
			
			if (!Main.getBotLauncher().getDiscordbot().sendMessage(message, "MESSAGE", Color.YELLOW, ConfigSetting.DISCORDBOT_EVENT_CHANNELID.getValueAsLong()))
			    Messages.COMMANDS_VARO_DISCORD_NO_EVENT_CHANNEL.send(sender);
			return;
		} else
		    Messages.COMMANDS_ERROR_USAGE.send(sender, Placeholder.constant("command", "discord"));
		return;
	}
}