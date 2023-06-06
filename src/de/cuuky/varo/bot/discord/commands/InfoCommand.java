package de.cuuky.varo.bot.discord.commands;

import java.awt.Color;

import de.cuuky.varo.Main;
import de.cuuky.varo.bot.discord.DiscordBotCommand;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;

public class InfoCommand extends DiscordBotCommand {

    /*
     * OLD CODE
     */

    public InfoCommand() {
        super("info", "Zeigt Infos ueber das Plugin");
    }

    @Override
    public void onExecute(SlashCommandInteraction event) {
        getDiscordBot().reply("Version: " + Main.getInstance().getDescription().getVersion() + "\n  Link: " + Main.DISCORD_INVITE, Main.getPluginName(), Color.BLUE, event);
    }

}