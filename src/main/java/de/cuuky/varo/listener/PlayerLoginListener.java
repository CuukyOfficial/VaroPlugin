package de.cuuky.varo.listener;

import java.util.Date;
import java.util.logging.Level;

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
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.player.stats.KickResult;
import de.cuuky.varo.player.stats.stat.PlayerState;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.UserSnowflake;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class PlayerLoginListener implements Listener {
    
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        BanEntry entry = Bukkit.getBanList(Type.NAME).getBanEntry(event.getName());
        if (entry != null)
            event.disallow(org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result.KICK_BANNED, ConfigMessages.JOIN_KICK_BANNED.getValue().replace("%reason%", entry.getReason()));
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
						}
                        reg.delete();
					}
				} catch (Throwable t) {
				    Main.getInstance().getLogger().log(Level.SEVERE, "Error while checking discord user!", t);
				    event.disallow(Result.KICK_OTHER, ConfigMessages.BOTS_DISCORD_NO_SERVER_USER.getValue(vp, vp));
                    vp.setPlayer(null);
                    return;
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
			event.disallow(Result.KICK_OTHER, ConfigMessages.JOIN_KICK_NOT_USER_OF_PROJECT.getValue(vp, vp));
			break;
		case DEAD:
			event.disallow(Result.KICK_OTHER, ConfigMessages.DEATH_KICK_DEAD.getValue(vp));
			break;
		case STRIKE_BAN:
			event.disallow(Result.KICK_OTHER, ConfigMessages.JOIN_KICK_STRIKE_BAN.getValue(vp, vp).replace("%hours%", String.valueOf(ConfigSetting.STRIKE_BAN_AFTER_STRIKE_HOURS.getValueAsInt())));
			break;
		case NOT_IN_TIME:
			event.disallow(Result.KICK_OTHER, ConfigMessages.SERVER_MODT_CANT_JOIN_HOURS.getValue(vp, vp).replace("%minHour%", String.valueOf(ConfigSetting.ONLY_JOIN_BETWEEN_HOURS_HOUR1.getValueAsInt())).replace("%maxHour%", String.valueOf(ConfigSetting.ONLY_JOIN_BETWEEN_HOURS_HOUR2.getValueAsInt())));
			break;
		case SERVER_FULL:
			event.disallow(Result.KICK_FULL, ConfigMessages.JOIN_KICK_SERVER_FULL.getValue(vp, vp));
			break;
		case NO_SESSIONS_LEFT:
			event.disallow(Result.KICK_OTHER, ConfigMessages.JOIN_KICK_NO_SESSIONS_LEFT.getValue(vp, vp));
			break;
		case NO_PREPRODUCES_LEFT:
			event.disallow(Result.KICK_OTHER, ConfigMessages.JOIN_KICK_NO_PREPRODUCES_LEFT.getValue(vp, vp));
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

			event.disallow(Result.KICK_OTHER, ConfigMessages.JOIN_KICK_NO_TIME_LEFT.getValue(vp).replace("%timeHours%", String.valueOf(ConfigSetting.JOIN_AFTER_HOURS.getValueAsInt())).replace("%stunden%", hours).replace("%minuten%", minutes).replace("%sekunden%", seconds));
			break;
		case SERVER_NOT_PUBLISHED:
			event.disallow(Result.KICK_OTHER, ConfigMessages.JOIN_KICK_NOT_STARTED.getValue(vp, vp));
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