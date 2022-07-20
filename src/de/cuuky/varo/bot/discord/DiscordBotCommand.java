package de.cuuky.varo.bot.discord;

import java.util.ArrayList;

import de.cuuky.varo.Main;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class DiscordBotCommand {

	/*
	 * OLD CODE
	 */

	private static ArrayList<DiscordBotCommand> commands;

	static {
		commands = new ArrayList<>();
	}

	private String[] aliases;
	private String desc;
	private String name;

	public DiscordBotCommand(String name, String[] aliases, String description) {
		this.name = name;
		this.desc = description;
		this.aliases = aliases;

		commands.add(this);
	}

	public String[] getAliases() {
		return this.aliases;
	}

	public String getDescription() {
		return this.desc;
	}

	public VaroDiscordBot getDiscordBot() {
		return Main.getBotLauncher().getDiscordbot();
	}

	public String getName() {
		return this.name;
	}

	public abstract void onEnable(String[] args, MessageReceivedEvent event);

	public static ArrayList<DiscordBotCommand> getCommands() {
		return commands;
	}
}
