package de.varoplugin.varo.bot.discord.commands;

import java.awt.Color;

import de.varoplugin.varo.bot.discord.BotRegister;
import de.varoplugin.varo.bot.discord.DiscordBotCommand;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class RegisterCommand extends DiscordBotCommand {

    /*
     * OLD CODE
     */

    public RegisterCommand() {
        super("verify", "Registriert dich per Code mit dem Discordbot", new OptionData(OptionType.STRING, "code", "Your verify code", true));
    }

    @Override
    public void onExecute(SlashCommandInteraction event) {
        if (event.getUser().isBot())
            return;

        if (!ConfigSetting.DISCORDBOT_VERIFYSYSTEM.getValueAsBoolean()) {
            getDiscordBot().replyError("Verify is disabled!", event);
            return;
        }

        String codeString = event.getOption("code").getAsString();

        int code;
        try {
            code = Integer.valueOf(codeString);
        } catch (NumberFormatException e) {
            getDiscordBot().replyError("Invalid code!", event);
            return;
        }

        for (BotRegister reg : BotRegister.getBotRegister())
            if (reg.isActive() && reg.getUserId() == event.getUser().getIdLong()) {
                getDiscordBot().replyError("Discord Account already verified!", event);
                return;
            }

        for (BotRegister reg : BotRegister.getBotRegister()) {
            if (reg.getCode() == code && !reg.isActive()) {
                reg.setUserId(Long.valueOf(event.getUser().getId()));
                getDiscordBot().reply("Discord Account '" + event.getUser().getAsMention() + "' successfully linked with the MC-Account '" + reg.getPlayerName() + "'!", "Success", Color.GREEN, event);
                reg.setCode(reg.generateCode());
                return;
            }
        }
        getDiscordBot().replyError("Invalid code!", event);
    }
}