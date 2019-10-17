package de.cuuky.varo.bot.discord.commands;

import java.awt.Color;

import de.cuuky.varo.bot.discord.DiscordBotCommand;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandCommand extends DiscordBotCommand {
	
	/*
	 * OLD CODE
	 */

	public CommandCommand() {
		super("command", new String[] { "consolecommand", "executecommand" }, "Führt einen Command in der Konsole aus.");
	}

	// private OfflinePlayer player = null;

	@Override
	public void onEnable(String[] args, MessageReceivedEvent event) {
		getDiscordBot().sendMessage("Feature aufgrund der 1.7-1.14 Kompatibilität entfernt.", "INFO", Color.MAGENTA, event.getTextChannel());

		// if(BotRegister.getRegister(event.getAuthor()) != null) {
		// BotRegister reg = BotRegister.getRegister(event.getAuthor());
		// try {
		// player = Bukkit.getOfflinePlayer(reg.getPlayerName());
		// } catch(NullPointerException e) {}
		// }
		//
		// if(args.length < 1) {
		// event.getTextChannel().sendMessage("Usage: command <Command>
		// [Arguments]").queue();
		// return;
		// }
		//
		// String command = Utils.getArgsToString(args, " ");
		// try {
		// Bukkit.dispatchCommand(VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_10__ONE_11)
		// ? new NewCommandSender(player, args, event) : new
		// OldCommandSender(player, args, event), command);
		// } catch(Exception e) {
		// event.getTextChannel().sendMessage("Failed to execute
		// command.").queue();
		// return;
		// }
	}
}
