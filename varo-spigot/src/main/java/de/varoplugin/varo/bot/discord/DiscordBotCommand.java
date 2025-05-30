package de.varoplugin.varo.bot.discord;

import java.util.ArrayList;
import java.util.List;

import de.varoplugin.varo.Main;
import de.varoplugin.varo.bot.discord.commands.GetLinkCommand;
import de.varoplugin.varo.bot.discord.commands.HelpCommand;
import de.varoplugin.varo.bot.discord.commands.InfoCommand;
import de.varoplugin.varo.bot.discord.commands.OnlineCommand;
import de.varoplugin.varo.bot.discord.commands.PlayerCommand;
import de.varoplugin.varo.bot.discord.commands.RegisterCommand;
import de.varoplugin.varo.bot.discord.commands.RegisteredCommand;
import de.varoplugin.varo.bot.discord.commands.RemainingCommand;
import de.varoplugin.varo.bot.discord.commands.ServerCommand;
import de.varoplugin.varo.bot.discord.commands.ShutdownCommand;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public abstract class DiscordBotCommand {

    /*
     * OLD CODE
     */

    private static List<DiscordBotCommand> commands;

    static {
        commands = new ArrayList<>();

        new HelpCommand();
        new InfoCommand();
        new ServerCommand();
        new RemainingCommand();
        new OnlineCommand();
        new RegisteredCommand();
        new RegisterCommand();
        new ShutdownCommand();
        new GetLinkCommand();
        new PlayerCommand();
    }

    private final String name;
    private final String desc;
    private final OptionData[] options;

    public DiscordBotCommand(String name, String description, OptionData... options) {
        this.name = name;
        this.desc = description;
        this.options = options;

        commands.add(this);
    }

    public String getName() {
        return this.name;
    }

    public OptionData[] getOptions() {
        return this.options;
    }

    public String getDescription() {
        return this.desc;
    }

    public VaroDiscordBot getDiscordBot() {
        return Main.getBotLauncher().getDiscordbot();
    }

    public abstract void onExecute(SlashCommandInteraction event);

    public static List<DiscordBotCommand> getCommands() {
        return commands;
    }
}
