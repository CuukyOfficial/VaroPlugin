package de.cuuky.varo.bot.discord.commands;

import java.awt.Color;
import java.util.List;

import de.cuuky.varo.bot.discord.DiscordBotCommand;
import de.cuuky.varo.bot.discord.register.BotRegister;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class GetLinkCommand extends DiscordBotCommand {

    /*
     * OLD CODE
     */

    public GetLinkCommand() {
        super("lookup", "Gibt den verlinkten MC-Account des Spielers zurück.", new OptionData(OptionType.STRING, "user", "The user/mc-name", true));
    }

    @Override
    public void onExecute(SlashCommandInteraction event) {
        if (!ConfigSetting.DISCORDBOT_VERIFYSYSTEM.getValueAsBoolean()) {
            event.getChannel().sendMessage("").queue();
            getDiscordBot().replyError("Das Verifzierungs-System wurde in der Config deaktiviert!", event);
            return;
        }

        OptionMapping option = event.getOption("user");
        List<User> mentionedUsers = option.getMentions().getUsers();

        User user;
        if (mentionedUsers.size() != 0) {
            user = mentionedUsers.get(0);
        } else {
            user = super.getDiscordBot().getJda().getUserById(option.getAsString());
            if (user == null) {
                getDiscordBot().replyError("User fuer diesen Spieler nicht gefunden!", event);
                return;
            }
        }

        BotRegister reg = BotRegister.getRegister(user);

        if (reg == null) {
            getDiscordBot().replyError("Dieser Spieler ist nicht verifiziert!", event);
            return;
        }

        getDiscordBot().reply("Der MC-Account von " + user.getAsMention() + " lautet " + reg.getPlayerName() + "!", "Lookup", Color.BLUE, event);
    }
}