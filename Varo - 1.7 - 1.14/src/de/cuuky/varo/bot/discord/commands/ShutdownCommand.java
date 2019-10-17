package de.cuuky.varo.bot.discord.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import de.cuuky.varo.Main;
import de.cuuky.varo.bot.discord.DiscordBotCommand;
import de.cuuky.varo.bot.discord.register.BotRegister;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class ShutdownCommand extends DiscordBotCommand {

	/*
	 * OLD CODE
	 */

	public ShutdownCommand() {
		super("shutdown", new String[] { "disconnect" }, "Fährt den Bot herunter.");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onEnable(String[] args, MessageReceivedEvent event) {
		try {
			if (BotRegister.getRegister(event.getAuthor()) == null) {
				event.getTextChannel().sendMessage("Du musst mit dem Bot authentifiziert sein!").queue();
				return;
			}

			BotRegister reg = BotRegister.getRegister(event.getAuthor());
			try {
				if (Bukkit.getOfflinePlayer(reg.getPlayerName()) == null) {
					event.getTextChannel().sendMessage("Spieler nicht gefunden!").queue();
					return;
				}
			} catch (NullPointerException e) {
				return;
			}

			OfflinePlayer player = Bukkit.getOfflinePlayer(reg.getPlayerName());
			if (!player.isOp()) {
				event.getTextChannel().sendMessage("Dazu bist du nicht berechtigt!").queue();
				return;
			}

			event.getTextChannel().sendMessage("Bye, bye.").queue();
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

				@Override
				public void run() {
					getDiscordBot().disconnect();
				}
			}, 20);
		} catch (Exception e) {
			super.getDiscordBot().disconnect();
		}
	}

}
