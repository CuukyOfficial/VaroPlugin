package de.cuuky.varo.listener;

import java.util.Date;

import org.bukkit.BanEntry;
import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import de.cuuky.varo.Main;
import de.cuuky.varo.bot.discord.BotRegister;
import de.cuuky.varo.bot.discord.VaroDiscordBot;
import de.cuuky.varo.config.language.Messages;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.player.stats.KickResult;
import de.cuuky.varo.player.stats.stat.PlayerState;
import io.github.almightysatan.slams.Placeholder;
import io.github.almightysatan.slams.PlaceholderResolver;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.UserSnowflake;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class PlayerLoginListener implements Listener {
    
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        BanEntry entry = Bukkit.getBanList(Type.NAME).getBanEntry(event.getName());
        if (entry != null)
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, Messages.PLAYER_KICK_NOT_USER_OF_PROJECT.value(Placeholder.constant("reason", entry.getReason())));
    }

	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerLogin(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		if (event.getResult() != Result.ALLOWED)
			return;

		VaroPlayer vp = VaroPlayer.getPlayer(player) == null ? new VaroPlayer(player) : VaroPlayer.getPlayer(player);
		VaroDiscordBot discordBot = Main.getBotLauncher().getDiscordbot();
		if (ConfigSetting.DISCORDBOT_VERIFYSYSTEM.getValueAsBoolean() && discordBot != null && discordBot.getJda() != null) {
			if(!discordBot.getJda().getGatewayIntents().contains(GatewayIntent.GUILD_MEMBERS)) {
				event.disallow(Result.KICK_OTHER, "§cPrivileged gateway intents have to be enabled in order to use the discord verify system!\n§cPlease enable them and restart the server!\n\n§7Need help? " + Main.DISCORD_INVITE);
				return;
			}

			BotRegister reg = BotRegister.getRegister(event.getPlayer().getUniqueId().toString());
			if (!ConfigSetting.DISCORDBOT_VERIFYSYSTEM_OPTIONAL.getValueAsBoolean()) {
				if (reg == null) {
					reg = new BotRegister(event.getPlayer().getUniqueId().toString(), true);
					reg.setPlayerName(event.getPlayer().getName());
					event.disallow(Result.KICK_OTHER, reg.getKickMessage(vp));
					return;
				} else if (!reg.isActive() && !reg.isBypass()) {
					event.disallow(Result.KICK_OTHER, reg.getKickMessage(vp));
					return;
				}
			}

			if (reg != null) {
				reg.setPlayerName(event.getPlayer().getName());
				try {
				    Guild guild = discordBot.getMainGuild();
					UserSnowflake userSnowflake = User.fromId(reg.getUserId());
					if (userSnowflake == null || guild == null || !guild.isMember(userSnowflake)) {
						if (!ConfigSetting.DISCORDBOT_VERIFYSYSTEM_OPTIONAL.getValueAsBoolean()) {
							event.disallow(Result.KICK_OTHER, ConfigMessages.BOTS_DISCORD_NO_SERVER_USER.getValue(vp, vp));
							vp.setPlayer(null);
							return;
						} else
							reg.delete();
					}
				} catch (Exception e2) {
					System.err.println("[Varo] Es wurde keine Server ID angegeben oder die ID des Spielers ist falsch!");
				}
			}
		}

		KickResult kickResult = vp.getStats().getKickResult(player);

		if (!kickResult.allowsJoin())
			if ((player.hasPermission("varo.alwaysjoin") && ConfigSetting.IGNORE_JOINSYSTEMS_AS_OP.getValueAsBoolean()) || (!Main.getVaroGame().hasStarted() && player.isOp())) {
				if (Main.getVaroGame().hasStarted()) {
					if (!vp.isRegistered()) {
						vp.register();
						vp.getStats().setState(PlayerState.SPECTATOR);
					} else
						vp.setAdminIgnore(true);
				}

				kickResult = KickResult.ALLOW;
			}

		switch (kickResult) {
		case NO_PROJECTUSER:
			event.disallow(Result.KICK_OTHER, Messages.PLAYER_KICK_NOT_USER_OF_PROJECT.value(vp));
			break;
		case DEAD:
			event.disallow(Result.KICK_OTHER, Messages.PLAYER_KICK_DEATH.value(vp));
			break;
		case STRIKE_BAN:
			event.disallow(Result.KICK_OTHER, Messages.PLAYER_KICK_DEATH.value(vp, Placeholder.constant("ban-hours", String.valueOf(ConfigSetting.STRIKE_BAN_AFTER_STRIKE_HOURS.getValueAsInt()))));
			break;
		case NOT_IN_TIME:
			event.disallow(Result.KICK_OTHER, ConfigMessages.SERVER_MODT_CANT_JOIN_HOURS.getValue(vp, vp).replace("%minHour%", String.valueOf(ConfigSetting.ONLY_JOIN_BETWEEN_HOURS_HOUR1.getValueAsInt())).replace("%maxHour%", String.valueOf(ConfigSetting.ONLY_JOIN_BETWEEN_HOURS_HOUR2.getValueAsInt())));
			break;
		case SERVER_FULL:
			event.disallow(Result.KICK_FULL, Messages.PLAYER_KICK_SERVER_FULL.value(vp));
			break;
		case NO_SESSIONS_LEFT:
			event.disallow(Result.KICK_OTHER, Messages.PLAYER_KICK_NO_SESSIONS_LEFT.value(vp));
			break;
		case NO_PREPRODUCES_LEFT:
			event.disallow(Result.KICK_OTHER, Messages.PLAYER_KICK_NO_PREPRODUCES_LEFT.value(vp));
			break;
		case NO_TIME:
			Date current = new Date();
			long milli = vp.getStats().getTimeUntilAddSession().getTime() - current.getTime();
			long sec = (milli / 1000) % 60;
			long min = (milli / 1000 / 60) % 60;
			long hr = (milli / 1000 / 60 / 60) % 24;
			String seconds, minutes, hours;
			if (String.valueOf(sec).length() == 1)
				seconds = "0" + sec;
			else
				seconds = "" + sec;
			if (String.valueOf(min).length() == 1)
				minutes = "0" + min;
			else
				minutes = "" + min;
			if (String.valueOf(hr).length() == 1)
				hours = "0" + hr;
			else
				hours = "" + hr;
			
			PlaceholderResolver placeholders = PlaceholderResolver.builder().constant("join-hours", hours)
        			.constant("join-minutes", minutes)
        			.constant("join-seconds", seconds).build();
			event.disallow(Result.KICK_OTHER, Messages.PLAYER_KICK_NO_TIME_LEFT.value(vp, placeholders));
			break;
		case SERVER_NOT_PUBLISHED:
			event.disallow(Result.KICK_OTHER, Messages.PLAYER_KICK_NOT_STARTED.value(vp));
			break;
		case ALLOW:
		case SPECTATOR:
		case MASS_RECORDING_JOIN:
		case FINALE_JOIN:
		default:
			if (!vp.isRegistered())
				vp.register();
			break;
		}
	}
}