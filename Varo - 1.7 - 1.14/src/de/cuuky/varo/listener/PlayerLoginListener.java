package de.cuuky.varo.listener;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.cuuky.varo.bot.BotLauncher;
import org.bukkit.BanEntry;
import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import de.cuuky.varo.Main;
import de.cuuky.varo.bot.discord.VaroDiscordBot;
import de.cuuky.varo.bot.discord.register.BotRegister;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.config.messages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.KickResult;
import net.dv8tion.jda.core.entities.User;

public class PlayerLoginListener implements Listener {

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		VaroPlayer vp = VaroPlayer.getPlayer(player) == null ? new VaroPlayer(player) : VaroPlayer.getPlayer(player);

		VaroDiscordBot discordBot = BotLauncher.getDiscordBot();
		if(ConfigEntry.DISCORDBOT_VERIFYSYSTEM.getValueAsBoolean() && discordBot != null && discordBot.getJda() != null) {
			BotRegister reg = BotRegister.getRegister(event.getPlayer().getUniqueId().toString());
			if(reg == null) {
				reg = new BotRegister(event.getPlayer().getUniqueId().toString(), true);
				reg.setPlayerName(event.getPlayer().getName());
				event.disallow(Result.KICK_OTHER, reg.getKickMessage());
				return;
			} else if(reg.isBypass()) {
				event.allow();
			} else if(!reg.isActive()) {
				event.disallow(Result.KICK_OTHER, reg.getKickMessage());
				return;
			} else {
				reg.setPlayerName(event.getPlayer().getName());
				try {
					User user = discordBot.getJda().getUserById(reg.getUserId());
					if(user == null || !discordBot.getJda().getGuildById(ConfigEntry.DISCORDBOT_SERVERID.getValueAsLong()).isMember(user)) {
						event.disallow(Result.KICK_OTHER, ConfigMessages.DISCORD_NO_SERVER_USER.getValue());
						vp.setPlayer(null);
						return;
					}
				} catch(Exception e2) {
					System.err.println("[Varo] Es wurde keine Server ID angegeben oder die ID des Spielers ist falsch!");
				}
			}
		}

		KickResult kickResult = vp.getStats().getKickResult(player);
		switch(kickResult) {
		case NO_PROJECTUSER:
			event.disallow(Result.KICK_OTHER, ConfigMessages.JOIN_KICK_NOT_USER_OF_PROJECT.getValue(vp));
			break;
		case BANNED:
			for(BanEntry entry : Bukkit.getBanList(Type.NAME).getBanEntries()) {
				if(entry.getTarget().equals(player.getName())) {
					event.disallow(Result.KICK_OTHER, ConfigMessages.JOIN_KICK_BANNED.getValue(vp).replace("%reason%", entry.getReason()));
					break;
				}
			}
			break;
		case DEAD:
			event.disallow(Result.KICK_OTHER, ConfigMessages.DEATH_KICK_DEAD.getValue());
			break;
		case STRIKE_BAN:
			event.disallow(Result.KICK_OTHER, ConfigMessages.JOIN_KICK_STRIKE_BAN.getValue(vp).replace("%hours%", String.valueOf(ConfigEntry.STRIKE_BAN_AFTER_STRIKE_HOURS.getValueAsInt())));
			break;
		case NOT_IN_TIME:
			Date date = new Date();
			event.disallow(Result.KICK_OTHER, ConfigMessages.SERVER_MODT_CANT_JOIN_HOURS.getValue().replaceAll("%minHour%", String.valueOf(ConfigEntry.ONLY_JOIN_BETWEEN_HOURS_HOUR1.getValueAsInt())).replaceAll("%maxHour%", String.valueOf(ConfigEntry.ONLY_JOIN_BETWEEN_HOURS_HOUR2.getValueAsInt())).replaceAll("%hours%", new SimpleDateFormat("HH").format(date)).replaceAll("%minutes%", new SimpleDateFormat("mm").format(date)).replaceAll("%seconds%", new SimpleDateFormat("ss").format(date)));
			break;
		case SERVER_FULL:
			event.disallow(Result.KICK_FULL, ConfigMessages.JOIN_KICK_SERVER_FULL.getValue(vp));
			break;
		case NO_SESSIONS_LEFT:
			event.disallow(Result.KICK_OTHER, ConfigMessages.JOIN_KICK_NO_SESSIONS_LEFT.getValue(vp));
			break;
		case NO_PREPRODUCES_LEFT:
			event.disallow(Result.KICK_OTHER, ConfigMessages.JOIN_KICK_NO_PREPRODUCES_LEFT.getValue());
			break;
		case NO_TIME:
			Date current = new Date();
			long milli = vp.getStats().getTimeUntilAddSession().getTime() - current.getTime();
			long sec = (milli / 1000) % 60;
			long min = (milli / 1000 / 60) % 60;
			long hr = (milli / 1000 / 60 / 60) % 24;
			String seconds = "";
			String minutes = "";
			String hours = "";
			if(String.valueOf(sec).length() == 1)
				seconds = "0" + sec;
			else
				seconds = "" + sec;
			if(String.valueOf(min).length() == 1)
				minutes = "0" + min;
			else
				minutes = "" + min;
			if(String.valueOf(hr).length() == 1)
				hours = "0" + hr;
			else
				hours = "" + hr;

			event.disallow(Result.KICK_OTHER, ConfigMessages.JOIN_KICK_NO_TIME_LEFT.getValue().replaceAll("%timeHours%", ConfigEntry.JOIN_AFTER_HOURS.getValueAsString()).replaceAll("%stunden%", hours).replaceAll("%minuten%", minutes).replaceAll("%sekunden%", seconds));
			break;
		case SERVER_NOT_PUBLISHED:
			event.disallow(Result.KICK_OTHER, ConfigMessages.JOIN_KICK_NOT_STARTED.getValue(vp));
			break;
		case MASS_RECORDING_JOIN:
		case FINALE_JOIN:
		default:
			event.allow();
			if(!vp.isRegistered())
				vp.register();
			break;
		}
	}
}
