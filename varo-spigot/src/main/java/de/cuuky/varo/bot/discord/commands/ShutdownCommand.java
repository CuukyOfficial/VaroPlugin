package de.cuuky.varo.bot.discord.commands;

import java.awt.Color;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;

import de.cuuky.varo.Main;
import de.cuuky.varo.bot.discord.BotRegister;
import de.cuuky.varo.bot.discord.DiscordBotCommand;
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
        new BukkitRunnable() {
            @Override
            public void run() {
                getDiscordBot().disconnect();
            }
        }.runTaskLater(Main.getInstance(), 20L);
    }
}