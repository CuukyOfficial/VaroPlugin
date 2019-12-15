package de.cuuky.varo.bot.discord;

import java.util.ArrayList;

import de.cuuky.varo.bot.BotLauncher;
import de.cuuky.varo.bot.discord.commands.CommandCommand;
import de.cuuky.varo.bot.discord.commands.GetLinkCommand;
import de.cuuky.varo.bot.discord.commands.HelpCommand;
import de.cuuky.varo.bot.discord.commands.InfoCommand;
import de.cuuky.varo.bot.discord.commands.OnlineCommand;
import de.cuuky.varo.bot.discord.commands.RegisterCommand;
import de.cuuky.varo.bot.discord.commands.RegisteredCommand;
import de.cuuky.varo.bot.discord.commands.RemainingCommand;
import de.cuuky.varo.bot.discord.commands.ServerCommand;
import de.cuuky.varo.bot.discord.commands.ShutdownCommand;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public abstract class DiscordBotCommand {

	/*
	 * OLD CODE
	 */

	private static ArrayList<DiscordBotCommand> commands;

	static {
		commands = new ArrayList<>();

		new HelpCommand();
		new InfoCommand();
		new ServerCommand();
		new RemainingCommand();
		new OnlineCommand();
		new RegisteredCommand();
		new RegisterCommand();
		new CommandCommand();
		new ShutdownCommand();
		new GetLinkCommand();
	}

	private String name;
	private String desc;
	private String[] aliases;

	public DiscordBotCommand(String name, String[] aliases, String description) {
		this.name = name;
		this.desc = description;
		this.aliases = aliases;

		commands.add(this);
	}

	public String getDescription() {
		return this.desc;
	}

	public String[] getAliases() {
		return this.aliases;
	}

	public String getName() {
		return this.name;
	}

	public VaroDiscordBot getDiscordBot() {
		return BotLauncher.getDiscordBot();
	}

	public static ArrayList<DiscordBotCommand> getCommands() {
		return commands;
	}

	public abstract void onEnable(String[] args, MessageReceivedEvent event);
}
