package de.cuuky.varo.command.varo;

import java.awt.Color;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.bot.BotLauncher;
import de.cuuky.varo.bot.discord.register.BotRegister;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.config.ConfigEntry;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.gui.admin.discordbot.DiscordBotGUI;
import net.dv8tion.jda.api.entities.User;

public class DiscordCommand extends VaroCommand {

	public DiscordCommand() {
		super("discord", "Der Hauptbefehl f√ºr den DiscordBot", "varo.discord");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if(args.length == 0) {
			sender.sendMessage(Main.getPrefix() + "ß7----- " + Main.getColorCode() + "Discord-Commands ß7-----");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo discord getLink ß7<Spieler>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo discord unlink ß7<Spieler>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo discord bypassRegister ß7<Spieler> <true/false>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo discord sendMessage ß7<Nachricht>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo discord reload");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo discord shutdown");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo discord settings");
			sender.sendMessage(Main.getPrefix() + "ß7--------------------------");
			return;
		}

		BotRegister reg = null;
		try {
			reg = BotRegister.getBotRegisterByPlayerName(args[1]);
		} catch(Exception e) {}

		if(BotLauncher.getDiscordBot() == null) {
			sender.sendMessage(Main.getPrefix() + "ß7Der DiscordBot wurde beim Start nicht aufgesetzt, bitte reloade!");
			return;
		}

		if(args[0].equalsIgnoreCase("getLink") || args[0].equalsIgnoreCase("link")) {
			if(!ConfigEntry.DISCORDBOT_VERIFYSYSTEM.getValueAsBoolean()) {
				sender.sendMessage(Main.getPrefix() + "ß7Das Verifzierungs-System wurde in der Config deaktiviert!");
				return;
			}

			if(BotLauncher.getDiscordBot() == null || !BotLauncher.getDiscordBot().isEnabled()) {
				sender.sendMessage(Main.getPrefix() + "ß7Der DiscordBot wurde nicht aktiviert!");
				return;
			}

			if(reg == null) {
				sender.sendMessage(Main.getPrefix() + "ß7Der Spieler ß7" + args[1] + " ß7hat den Server noch nie betreten!");
				return;
			}

			User user = BotLauncher.getDiscordBot().getJda().getUserById(reg.getUserId());
			if(user == null) {
				sender.sendMessage(Main.getPrefix() + "ß7User f√ºr diesen Spieler nicht gefunden!");
				return;
			}

			sender.sendMessage(Main.getPrefix() + "ß7Der Discord Account von " + args[1] + " hei√üt: " + Main.getColorCode() + user.getName() + "ß7 und die ID lautet " + Main.getColorCode() + user.getId() + "ß7!");
		} else if(args[0].equalsIgnoreCase("unlink")) {
			if(!ConfigEntry.DISCORDBOT_VERIFYSYSTEM.getValueAsBoolean()) {
				sender.sendMessage(Main.getPrefix() + "ß7Das Verifzierungs-System wurde in der Config deaktiviert!");
				return;
			}

			if(BotLauncher.getDiscordBot() == null || !BotLauncher.getDiscordBot().isEnabled()) {
				sender.sendMessage(Main.getPrefix() + "ß7Der DiscordBot wurde nicht aktiviert!");
				return;
			}

			if(reg == null) {
				sender.sendMessage(Main.getPrefix() + "ß7Der Spieler ß7" + args[1] + " ß7hat den Server noch nie betreten!");
				return;
			}

			reg.setUserId(-1);
			sender.sendMessage(Main.getPrefix() + "ß7Der Discord Account wurde erfolgreich von ß7" + args[1] + "ß7 entkoppelt!");
			if(Bukkit.getPlayerExact(reg.getPlayerName()) != null)
				Bukkit.getPlayerExact(reg.getPlayerName()).kickPlayer(reg.getKickMessage());
		} else if(args[0].equalsIgnoreCase("bypassRegister") || args[0].equalsIgnoreCase("bypass")) {
			if(!ConfigEntry.DISCORDBOT_VERIFYSYSTEM.getValueAsBoolean()) {
				sender.sendMessage(Main.getPrefix() + "ß7Das Verifzierungs-System wurde in der Config deaktiviert!");
				return;
			}

			if(BotLauncher.getDiscordBot() == null || !BotLauncher.getDiscordBot().isEnabled()) {
				sender.sendMessage(Main.getPrefix() + "ß7Der DiscordBot wurde nicht aktiviert!");
				return;
			}

			if(reg == null) {
				sender.sendMessage(Main.getPrefix() + "ß7Der Spieler ß7" + args[1] + " ß7hat den Server noch nie betreten!");
				return;
			}

			if(args.length != 3) {
				sender.sendMessage(Main.getPrefix() + "ß7/varo discord bypass <Spieler> <true/false>");
				return;
			}

			if(args[2].equalsIgnoreCase("true") || args[2].equalsIgnoreCase("false")) {
				reg.setBypass(args[2].equalsIgnoreCase("true") ? true : false);
				sender.sendMessage(Main.getPrefix() + "ß7" + args[1] + "ß7 bypasst jetzt " + (reg.isBypass() ? "" : "ß7nicht mehrß7") + " das Register-System!");
			} else
				sender.sendMessage(Main.getPrefix() + "ß7/varo discord bypass <add/remove> <Spielername>");
		} else if(args[0].equalsIgnoreCase("reload")) {
			BotLauncher.getDiscordBot().disconnect();
			BotLauncher.getDiscordBot().connect();
			for(Player pl : Bukkit.getOnlinePlayers())
				if(ConfigEntry.DISCORDBOT_VERIFYSYSTEM.getValueAsBoolean() && BotRegister.getBotRegisterByPlayerName(pl.getName()) == null)
					pl.kickPlayer("ß7Das Discord Verify System wurde aktiviert!");
			sender.sendMessage(Main.getPrefix() + "ß7DiscordBot ßaerfolgreich ß7neu geladen!");
		} else if(args[0].equalsIgnoreCase("settings")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage(Main.getPrefix() + "Only for players!");
				return;
			}

			new DiscordBotGUI((Player) sender);
		} else if(args[0].equalsIgnoreCase("shutdown")) {
			if(BotLauncher.getDiscordBot().getJda() == null) {
				sender.sendMessage(Main.getPrefix() + "Der ßbDiscordBot ß7ist nicht online!");
				return;
			}

			sender.sendMessage(Main.getPrefix() + "ßbDiscordBot ß7erfolgreich heruntergefahren!");
			BotLauncher.getDiscordBot().disconnect();
		} else if(args[0].equalsIgnoreCase("sendMessage")) {
			if(BotLauncher.getDiscordBot() == null || !BotLauncher.getDiscordBot().isEnabled()) {
				sender.sendMessage(Main.getPrefix() + "ß7Der DiscordBot wurde nicht aktiviert!");
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

			BotLauncher.getDiscordBot().sendMessage(message, "MESSAGE", Color.YELLOW, BotLauncher.getDiscordBot().getEventChannel());
		} else
			sender.sendMessage(Main.getPrefix() + "ß7/varo discord " + args[0] + " not found! ß7Type /discord for help.");
		return;
	}
}