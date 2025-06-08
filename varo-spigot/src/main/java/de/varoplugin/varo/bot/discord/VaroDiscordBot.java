package de.varoplugin.varo.bot.discord;

import java.awt.Color;
import java.io.File;
import java.util.Random;
import java.util.logging.Level;

import de.varoplugin.varo.Main;
import de.varoplugin.varo.bot.VaroBot;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
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
import net.dv8tion.jda.internal.utils.JDALogger;

public class VaroDiscordBot implements VaroBot {

    private JDA jda;
    private String pingRole;

    private Color getRandomColor() {
        Random random = new Random();
        return new Color(random.nextFloat(), random.nextFloat(), random.nextFloat());
    }

    @Override
    public void connect() {
        Main.getInstance().getLogger().log(Level.INFO, "Activating discord bot...");
        JDALogger.setFallbackLoggerEnabled(false);
        JDABuilder builder = JDABuilder.createLight(ConfigSetting.DISCORDBOT_TOKEN.getValueAsString());
        builder.setActivity(Activity.customStatus(ConfigSetting.DISCORDBOT_GAMESTATE.getValueAsString()));
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
            Main.getInstance().getLogger().log(Level.INFO, "Waiting for the bot to be ready...");
            jda.awaitReady();
            jda.addEventListener(new DiscordBotEventListener());
        } catch (Throwable t) {
            t.printStackTrace();
            Main.getInstance().getLogger().log(Level.SEVERE, "Couldn't connect to Discord");
            return;
        }

        loadRole();
        
        Guild guild = getMainGuild();
        if (guild == null) {
            Main.getInstance().getLogger().log(Level.INFO, "Cannot get server from ID " + ConfigSetting.DISCORDBOT_SERVERID.getValueAsLong());
            disconnect();
            return;
        }
        
        guild.updateCommands().addCommands(DiscordBotCommand.getCommands().stream()
                .map(command -> Commands.slash(command.getName(), command.getDescription()).addOptions(command.getOptions())).toArray(CommandData[]::new)).queue();

        Main.getInstance().getLogger().log(Level.INFO, "DiscordBot enabled successfully!");
    }

    private void loadRole() {
        long roleId = ConfigSetting.DISCORDBOT_ANNOUNCEMENT_PING_ROLEID.getValueAsLong();
        
        if (roleId != 0 && roleId != -1) {
            Role role = jda.getRoleById(roleId);

            if (role != null) {
                this.pingRole = role.getAsMention();
                return;
            }
            Main.getInstance().getLogger().log(Level.SEVERE, "Could not find role: " + ConfigSetting.DISCORDBOT_ANNOUNCEMENT_PING_ROLEID.getValueAsLong());
        }
        this.pingRole = "@everyone";
    }

    @Override
    public void disconnect() {
        if (!isEnabled())
            return;

        try {
            jda.shutdownNow();
        } catch (Throwable t) {
            Main.getInstance().getLogger().log(Level.SEVERE, "Discord bot failed during shutdown!");
            try {
                jda.shutdown();
            } catch (Throwable e1) {
                // nop
            }
        }

        jda = null;
    }

    private boolean sendMessage(String message, String author, String authorUrl, String authorIconUrl, File file, Color color, MessageChannel channel) {
        String escapedMessage = message.replace("_", "\\_");
        try {
            MessageCreateAction action;
            if (ConfigSetting.DISCORDBOT_USE_EMBEDS.getValueAsBoolean()) {
                EmbedBuilder builder = new EmbedBuilder();
                if (!ConfigSetting.DISCORDBOT_MESSAGE_RANDOM_COLOR.getValueAsBoolean())
                    builder.setColor(color);
                else
                    builder.setColor(getRandomColor());
                if (author != null)
                    builder.setAuthor(author, authorUrl, authorIconUrl);
                builder.setDescription(escapedMessage);
                action = channel.sendMessageEmbeds(builder.build());
            } else
                action = channel.sendMessage(escapedMessage);

            if (file != null)
                action.addFiles(FileUpload.fromData(file));

            action.queue();
            return true;
        } catch (InsufficientPermissionException e) {
            Main.getInstance().getLogger().log(Level.SEVERE, "Bot failed to write a message because of missing permission in channel " + channel.getName() + "! MISSING: " + e.getPermission());
            return false;
        }
    }

    public boolean sendMessage(String message, String author, String authorUrl, String authorIconUrl, File file, Color color, long channelId) {
        if (channelId == 0 || channelId == -1)
            return false;
        
        GuildMessageChannel channel = null;
        try {
            channel = jda.getChannelById(GuildMessageChannel.class, channelId);
        } catch (Exception e) {
            Main.getInstance().getLogger().log(Level.SEVERE, "Failed to print discord message!");
            return false;
        }
        if(channel == null) {
            Main.getInstance().getLogger().log(Level.SEVERE, "Failed to find discord channel {}", channelId);
            return false;
        }
        return this.sendMessage(message, author, authorUrl, authorIconUrl, file, color, channel);
    }
    
    public boolean sendMessage(String message, String author, File file, Color color, long channelId) {
        return this.sendMessage(message, author, null, null, file, color, channelId);
    }

    public boolean sendMessage(String message, String author, Color color, long channelId) {
        return this.sendMessage(message, author, null, color, channelId);
    }

    public void reply(String message, String author, String authorUrl, String authorIconUrl, Color color, IReplyCallback action) {
        String escapedMessage = message.replace("_", "\\_");

        if (ConfigSetting.DISCORDBOT_USE_EMBEDS.getValueAsBoolean()) {
            EmbedBuilder builder = new EmbedBuilder();
            if (!ConfigSetting.DISCORDBOT_MESSAGE_RANDOM_COLOR.getValueAsBoolean())
                builder.setColor(color);
            else
                builder.setColor(getRandomColor());
            if (author != null)
                builder.setAuthor(author, authorUrl, authorIconUrl);
            builder.setDescription(escapedMessage);
            action.replyEmbeds(builder.build()).queue();
        } else
            action.reply(escapedMessage).queue();
    }
    
    public void reply(String message, String author, Color color, IReplyCallback action) {
        this.reply(message, author, null, null, color, action);
    }

    public void replyError(String message, IReplyCallback action) {
        this.reply(message, "Error", Color.RED, action);
    }

    public String getMentionRole() {
        return this.pingRole;
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
