package de.cuuky.varo.bot.discord;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DiscordBotEventListener extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        for (DiscordBotCommand command : DiscordBotCommand.getCommands())
            if (command.getName().equals(event.getName())) {
                command.onExecute(event);
                break;
            }
    }
}