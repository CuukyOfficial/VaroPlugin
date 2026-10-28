package de.varoplugin.varo.bot.discord.commands;

import java.awt.Color;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import de.varoplugin.cfw.scheduler.TaskRunnable;
import de.varoplugin.varo.Main;
import de.varoplugin.varo.bot.discord.BotRegister;
import de.varoplugin.varo.bot.discord.DiscordBotCommand;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;

public class ShutdownCommand extends DiscordBotCommand {

    /*
     * OLD CODE
     */

    public ShutdownCommand() {
        super("shutdown", "Fährt den Bot herunter.");
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onExecute(SlashCommandInteraction event) {
        BotRegister reg = BotRegister.getRegister(event.getUser());
        if (reg == null) {
            getDiscordBot().replyError("Du musst mit dem Bot authentifiziert sein!", event);
            return;
        }

        OfflinePlayer player = Bukkit.getOfflinePlayer(reg.getPlayerName());
        if (player == null) {
            getDiscordBot().replyError("Spieler nicht gefunden!", event);
            return;
        }

        if (!player.isOp()) {
            getDiscordBot().replyError("Dazu bist du nicht berechtigt!", event);
            return;
        }

        getDiscordBot().reply("Shutting down", "Success", Color.GREEN, event);
        new TaskRunnable() {
            @Override
            public void run() {
                getDiscordBot().disconnect();
            }
        }.runTaskLater(Main.getInstance(), 20L);
    }
}