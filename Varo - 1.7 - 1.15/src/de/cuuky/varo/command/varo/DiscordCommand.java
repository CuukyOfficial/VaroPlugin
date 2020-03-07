package de.cuuky.varo.command.varo;

import java.awt.Color;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.bot.discord.register.BotRegister;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.config.ConfigEntry;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.gui.admin.discordbot.DiscordBotGUI;
import net.dv8tion.jda.api.entities.User;

public class DiscordCommand extends VaroCommand {

	public DiscordCommand() {
		super("discord", "Der Hauptbefehl für den DiscordBot", "varo.discord");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if(args.length == 0) {
			sender.sendMessage(Main.getPrefix() + "§7----- " + Main.getColorCode() + "Discord-Commands §7-----");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo discord getLink §7<Spieler>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo discord unlink §7<Spieler>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo discord bypassRegister §7<Spieler> <true/false>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo discord sendMessage §7<Nachricht>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo discord reload");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo discord shutdown");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo discord settings");
			sender.sendMessage(Main.getPrefix() + "§7--------------------------");
			return;
		}

		BotRegister reg = null;
		try {
			reg = BotRegister.getBotRegisterByPlayerName(args[1]);
		} catch(Exception e) {}

		if(Main.getBotLauncher().getDiscordbot() == null) {
			sender.sendMessage(Main.getPrefix() + "§7Der DiscordBot wurde beim Start nicht aufgesetzt, bitte reloade!");
			return;
		}

		if(args[0].equalsIgnoreCase("getLink") || args[0].equalsIgnoreCase("link")) {
			if(!ConfigEntry.DISCORDBOT_VERIFYSYSTEM.getValueAsBoolean()) {
				sender.sendMessage(Main.getPrefix() + "§7Das Verifzierungs-System wurde in der Config deaktiviert!");
				return;
			}

			if(Main.getBotLauncher().getDiscordbot() == null || !Main.getBotLauncher().getDiscordbot().isEnabled()) {
				sender.sendMessage(Main.getPrefix() + "§7Der DiscordBot wurde nicht aktiviert!");
				return;
			}

			if(reg == null) {
				sender.sendMessage(Main.getPrefix() + "§7Der Spieler §7" + args[1] + " §7hat den Server noch nie betreten!");
				return;
			}

			User user = Main.getBotLauncher().getDiscordbot().getJda().getUserById(reg.getUserId());
			if(user == null) {
				sender.sendMessage(Main.getPrefix() + "§7User für diesen Spieler nicht gefunden!");
				return;
			}

			sender.sendMessage(Main.getPrefix() + "§7Der Discord Account von " + args[1] + " heißt: " + Main.getColorCode() + user.getName() + "§7 und die ID lautet " + Main.getColorCode() + user.getId() + "§7!");
		} else if(args[0].equalsIgnoreCase("unlink")) {
			if(!ConfigEntry.DISCORDBOT_VERIFYSYSTEM.getValueAsBoolean()) {
				sender.sendMessage(Main.getPrefix() + "§7Das Verifzierungs-System wurde in der Config deaktiviert!");
				return;
			}

			if(Main.getBotLauncher().getDiscordbot() == null || !Main.getBotLauncher().getDiscordbot().isEnabled()) {
				sender.sendMessage(Main.getPrefix() + "§7Der DiscordBot wurde nicht aktiviert!");
				return;
			}

			if(reg == null) {
				sender.sendMessage(Main.getPrefix() + "§7Der Spieler §7" + args[1] + " §7hat den Server noch nie betreten!");
				return;
			}

			reg.setUserId(-1);
			sender.sendMessage(Main.getPrefix() + "§7Der Discord Account wurde erfolgreich von §7" + args[1] + "§7 entkoppelt!");
			if(Bukkit.getPlayerExact(reg.getPlayerName()) != null)
				Bukkit.getPlayerExact(reg.getPlayerName()).kickPlayer(reg.getKickMessage());
		} else if(args[0].equalsIgnoreCase("bypassRegister") || args[0].equalsIgnoreCase("bypass")) {
			if(!ConfigEntry.DISCORDBOT_VERIFYSYSTEM.getValueAsBoolean()) {
				sender.sendMessage(Main.getPrefix() + "§7Das Verifzierungs-System wurde in der Config deaktiviert!");
				return;
			}

			if(Main.getBotLauncher().getDiscordbot() == null || !Main.getBotLauncher().getDiscordbot().isEnabled()) {
				sender.sendMessage(Main.getPrefix() + "§7Der DiscordBot wurde nicht aktiviert!");
				return;
			}

			if(reg == null) {
				sender.sendMessage(Main.getPrefix() + "§7Der Spieler §7" + args[1] + " §7hat den Server noch nie betreten!");
				return;
			}

			if(args.length != 3) {
				sender.sendMessage(Main.getPrefix() + "§7/varo discord bypass <Spieler> <true/false>");
				return;
			}

			if(args[2].equalsIgnoreCase("true") || args[2].equalsIgnoreCase("false")) {
				reg.setBypass(args[2].equalsIgnoreCase("true") ? true : false);
				sender.sendMessage(Main.getPrefix() + "§7" + args[1] + "§7 bypasst jetzt " + (reg.isBypass() ? "" : "§7nicht mehr§7") + " das Register-System!");
			} else
				sender.sendMessage(Main.getPrefix() + "§7/varo discord bypass <add/remove> <Spielername>");
		} else if(args[0].equalsIgnoreCase("reload")) {
			Main.getBotLauncher().getDiscordbot().disconnect();
			Main.getBotLauncher().getDiscordbot().connect();
			for(Player pl : Bukkit.getOnlinePlayers())
				if(ConfigEntry.DISCORDBOT_VERIFYSYSTEM.getValueAsBoolean() && BotRegister.getBotRegisterByPlayerName(pl.getName()) == null)
					pl.kickPlayer("§7Das Discord Verify System wurde aktiviert!");
			sender.sendMessage(Main.getPrefix() + "§7DiscordBot §aerfolgreich §7neu geladen!");
		} else if(args[0].equalsIgnoreCase("settings")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage(Main.getPrefix() + "Only for players!");
				return;
			}

			new DiscordBotGUI((Player) sender);
		} else if(args[0].equalsIgnoreCase("shutdown")) {
			if(Main.getBotLauncher().getDiscordbot().getJda() == null) {
				sender.sendMessage(Main.getPrefix() + "Der §bDiscordBot §7ist nicht online!");
				return;
			}

			sender.sendMessage(Main.getPrefix() + "§bDiscordBot §7erfolgreich heruntergefahren!");
			Main.getBotLauncher().getDiscordbot().disconnect();
		} else if(args[0].equalsIgnoreCase("sendMessage")) {
			if(Main.getBotLauncher().getDiscordbot() == null || !Main.getBotLauncher().getDiscordbot().isEnabled()) {
				sender.sendMessage(Main.getPrefix() + "§7Der DiscordBot wurde nicht aktiviert!");
				return;
			}

			if(Main.getBotLauncher().getDiscordbot().getEventChannel() == null) {
				sender.sendMessage(Main.getPrefix() + "§7Dem Bot wurde kein eventChannel gegeben!");
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
			sender.sendMessage(Main.getPrefix() + "§7/varo discord " + args[0] + " not found! §7Type /discord for help.");
		return;
	}
}