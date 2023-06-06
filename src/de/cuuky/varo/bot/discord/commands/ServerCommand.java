package de.cuuky.varo.bot.discord.commands;

import java.awt.Color;

import org.bukkit.Bukkit;

import de.cuuky.varo.Main;
import de.cuuky.varo.bot.discord.DiscordBotCommand;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;

public class ServerCommand extends DiscordBotCommand {

	/*
	 * OLD CODE
	 */

	public ServerCommand() {
		super("status", "Zeigt Infos und Status des Servers");
	}

	@Override
	public void onExecute(SlashCommandInteraction event) {
	    getDiscordBot().reply("IP: " + Bukkit.getServer().getIp() + ":" + Bukkit.getServer().getPort() + "\n  Whitelist: " + Bukkit.getServer().hasWhitelist() + "\n  GameState: " + Main.getVaroGame().getGameState().toString(), "Server Info", Color.BLUE, event);
	}
}