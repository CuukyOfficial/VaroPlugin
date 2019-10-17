package de.cuuky.varo.bot.discord.commands;

import java.awt.Color;

import de.cuuky.varo.Main;
import de.cuuky.varo.bot.discord.DiscordBotCommand;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class InfoCommand extends DiscordBotCommand {
	
	/*
	 * OLD CODE
	 */

	public InfoCommand() {
		super("info", new String[] { "plugin", "author" }, "Zeigt Infos über das Plugin");
	}

	@Override
	public void onEnable(String[] args, MessageReceivedEvent event) {
		getDiscordBot().sendMessage("Varo Plugin | DiscordBot & more by Cuuky", "Version: " + Main.getInstance().getDescription().getVersion() + "\n  Link: https://discord.gg/CnDSVVx", Color.BLUE, event.getTextChannel());
	}

}
