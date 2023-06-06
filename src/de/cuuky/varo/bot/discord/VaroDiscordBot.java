package de.cuuky.varo.bot.discord;

import java.awt.Color;
import java.io.File;
import java.util.Random;

import de.cuuky.varo.Main;
import de.cuuky.varo.bot.VaroBot;
import de.cuuky.varo.bot.discord.listener.DiscordBotEventListener;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.FileUpload;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class VaroDiscordBot implements VaroBot {

    private JDA jda;
    private long eventChannel, announcementChannel, resultChannel, pingRole;

    private Color getRandomColor() {
        Random random = new Random();
        return new Color(random.nextFloat(), random.nextFloat(), random.nextFloat());
    }

    @Override
    public void connect() {
        System.out.println(Main.getConsolePrefix() + "Activating discord bot... (Errors might appear - don't mind them)");
        JDABuilder builder = JDABuilder.createLight(ConfigSetting.DISCORDBOT_TOKEN.getValueAsString());
        builder.setActivity(Activity.playing(ConfigSetting.DISCORDBOT_GAMESTATE.getValueAsString()));
        builder.setAutoReconnect(true);
        builder.setRequestTimeoutRetry(true);
        builder.setStatus(OnlineStatus.ONLINE);

        if(ConfigSetting.DISCORDBOT_ENABLED_PRIVILIGES.getValueAsBoolean()) {
            builder.setMemberCachePolicy(MemberCachePolicy.ALL);
            builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
            builder.setChunkingFilter(ChunkingFilter.ALL);
        }

        try {
            jda = builder.build();
            System.out.println(Main.getConsolePrefix() + "Waiting for the bot to be ready...");
            jda.awaitReady();
            jda.addEventListener(new DiscordBotEventListener());
        } catch (Throwable t) {
            t.printStackTrace();
            System.err.println(Main.getConsolePrefix() + "Couldn't connect to Discord");
            return;
        }

        loadChannel();
        
        Guild guild = getMainGuild();
        if (guild == null) {
            System.out.println(Main.getConsolePrefix() + "Cannot get server from ID " + ConfigSetting.DISCORDBOT_SERVERID.getValueAsLong());
            disconnect();
            return;
        }
        
        guild.updateCommands().addCommands(DiscordBotCommand.getCommands().stream()
                .map(command -> Commands.slash(command.getName(), command.getDescription()).addOptions(command.getOptions())).toArray(CommandData[]::new)).queue();

        System.out.println(Main.getConsolePrefix() + "DiscordBot enabled successfully!");
    }

    private void loadChannel() {
        try {
            announcementChannel = jda.getTextChannelById(ConfigSetting.DISCORDBOT_ANNOUNCEMENT_CHANNELID.getValueAsLong()).getIdLong();

            if (announcementChannel == -1)
                System.err.println(Main.getConsolePrefix() + "Could not load announcement-channel");
        } catch (ClassCastException | IllegalArgumentException | NullPointerException e) {
            System.err.println(Main.getConsolePrefix() + "Could not load announcement-channel");
        }

        try {
            eventChannel = jda.getTextChannelById(ConfigSetting.DISCORDBOT_EVENTCHANNELID.getValueAsLong()).getIdLong();
        } catch (ClassCastException | IllegalArgumentException | NullPointerException e) {
            System.err.println(Main.getConsolePrefix() + "Could not load event-channel");
        }

        try {
            resultChannel = jda.getTextChannelById(ConfigSetting.DISCORDBOT_RESULT_CHANNELID.getValueAsLong()).getIdLong();

            if (resultChannel == -1)
                System.err.println(Main.getConsolePrefix() + "Could not load result-channel");
        } catch (ClassCastException | IllegalArgumentException | NullPointerException e) {
            System.err.println(Main.getConsolePrefix() + "Could not load result-channel");
        }

        try {
            pingRole = -1;
            pingRole = jda.getRoleById(ConfigSetting.DISCORDBOT_ANNOUNCEMENT_PING_ROLEID.getValueAsLong()).getIdLong();

            if (pingRole == -1)
                System.err.println(Main.getConsolePrefix() + "Could not find role for: " + ConfigSetting.DISCORDBOT_ANNOUNCEMENT_PING_ROLEID.getValueAsLong());
        } catch (NullPointerException e) {
            System.err.println(Main.getConsolePrefix() + "Could not find role for: " + ConfigSetting.DISCORDBOT_ANNOUNCEMENT_PING_ROLEID.getValueAsLong());
        }
    }

    @Override
    public void disconnect() {
        if (!isEnabled())
            return;

        try {
            jda.shutdownNow();
        } catch (Exception | Error e) {
            System.err.println("[Varo] DiscordBot failed shutting down! Maybe you switched the version while the plugin was running?");
            try {
                jda.shutdown();
            } catch (Exception | Error e1) {
                // nop
            }
        }

        jda = null;
    }

    public void sendMessage(String message, String title, File file, Color color, MessageChannel channel) {
        String escapedMessage = message.replace("_", "\\_");
        try {
            MessageCreateAction action;
            if (ConfigSetting.DISCORDBOT_USE_EMBEDS.getValueAsBoolean()) {
                EmbedBuilder builder = new EmbedBuilder();
                if (!ConfigSetting.DISCORDBOT_MESSAGE_RANDOM_COLOR.getValueAsBoolean())
                    builder.setColor(color);
                else
                    builder.setColor(getRandomColor());
                builder.addField(title, escapedMessage, true);
                action = channel.sendMessageEmbeds(builder.build());
            } else
                action = channel.sendMessage(escapedMessage);

            if (file != null)
                action.addFiles(FileUpload.fromData(file));

            action.queue();
        } catch (InsufficientPermissionException e) {
            System.err.println(Main.getConsolePrefix() + "Bot failed to write a message because of missing permission in channel " + channel.getName() + "! MISSING: " + e.getPermission());
        }
    }

    public void sendMessage(String message, String title, Color color, MessageChannel channel) {
        this.sendMessage(message, title, null, color, channel);
    }

    public void sendMessage(String message, String title, Color color, long channelid) {
        GuildMessageChannel channel = null;
        try {
            channel = jda.getTextChannelById(channelid);
        } catch (Exception e) {
            System.err.println(Main.getConsolePrefix() + "Failed to print discord message!");
            return;
        }
        if(channel == null) {
            System.err.println(String.format("%sFailed to find discord channel %d", Main.getConsolePrefix(), channelid));
            return;
        }
        this.sendMessage(message, title, color, channel);
    }

    public void reply(String message, String title, Color color, IReplyCallback action) {
        String escapedMessage = message.replace("_", "\\_");

        if (ConfigSetting.DISCORDBOT_USE_EMBEDS.getValueAsBoolean()) {
            EmbedBuilder builder = new EmbedBuilder();
            if (!ConfigSetting.DISCORDBOT_MESSAGE_RANDOM_COLOR.getValueAsBoolean())
                builder.setColor(color);
            else
                builder.setColor(getRandomColor());
            builder.addField(title, escapedMessage, true);
            action.replyEmbeds(builder.build()).queue();
        } else
            action.reply(escapedMessage).queue();
    }

    public void replyError(String message, IReplyCallback action) {
        this.reply(message, "Error", Color.RED, action);
    }

    public String getMentionRole() {
        if (pingRole == -1)
            return "@everyone";

        return jda.getRoleById(pingRole).getAsMention();
    }

    public GuildMessageChannel getAnnouncementChannel() {
        return jda.getTextChannelById(announcementChannel);
    }

    public GuildMessageChannel getEventChannel() {
        return jda.getTextChannelById(eventChannel);
    }

    public GuildMessageChannel getResultChannel() {
        return jda.getTextChannelById(resultChannel);
    }

    public Guild getMainGuild() {
        return jda.getGuildById(ConfigSetting.DISCORDBOT_SERVERID.getValueAsLong());
    }

    public JDA getJda() {
        return jda;
    }

    public boolean isEnabled() {
        return jda != null;
    }
}
