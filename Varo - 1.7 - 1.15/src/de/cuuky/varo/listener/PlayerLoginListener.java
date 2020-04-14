package de.cuuky.varo.listener;

import java.util.Date;

import org.bukkit.BanEntry;
import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import de.cuuky.varo.Main;
import de.cuuky.varo.ban.VaroPlayerBan;
import de.cuuky.varo.bot.discord.VaroDiscordBot;
import de.cuuky.varo.bot.discord.register.BotRegister;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.messages.language.languages.LanguageDE;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.KickResult;
import net.dv8tion.jda.api.entities.User;

public class PlayerLoginListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerLogin(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		VaroPlayerBan ban = VaroPlayerBan.getBan(player.getUniqueId().toString());
		if(ban != null && ban.checkBan(player, event))
			return;
		
		VaroPlayer vp = VaroPlayer.getPlayer(player) == null ? new VaroPlayer(player) : VaroPlayer.getPlayer(player);

		VaroDiscordBot discordBot = Main.getBotLauncher().getDiscordbot();
		if(ConfigSetting.DISCORDBOT_VERIFYSYSTEM.getValueAsBoolean() && discordBot != null && discordBot.getJda() != null) {
			BotRegister reg = BotRegister.getRegister(event.getPlayer().getUniqueId().toString());
			if(!ConfigSetting.DISCORDBOT_VERIFYSYSTEM_OPTIONAL.getValueAsBoolean()) {
				if(reg == null) {
					reg = new BotRegister(event.getPlayer().getUniqueId().toString(), true);
					reg.setPlayerName(event.getPlayer().getName());
					event.disallow(Result.KICK_OTHER, reg.getKickMessage(vp));
					return;
				} else if(reg.isBypass()) {
					event.allow();
				} else if(!reg.isActive()) {
					event.disallow(Result.KICK_OTHER, reg.getKickMessage(vp));
					return;
				}
			}

			if(reg != null) {
				reg.setPlayerName(event.getPlayer().getName());
				try {
					User user = discordBot.getJda().getUserById(reg.getUserId());
					if(user == null || !discordBot.getMainGuild().isMember(user)) {
						if(!ConfigSetting.DISCORDBOT_VERIFYSYSTEM_OPTIONAL.getValueAsBoolean()) {
							event.disallow(Result.KICK_OTHER, Main.getLanguageManager().getValue(LanguageDE.BOTS_DISCORD_NO_SERVER_USER, vp, vp));
							vp.setPlayer(null);
							return;
						} else
							reg.delete();
					}
				} catch(Exception e2) {
					System.err.println("[Varo] Es wurde keine Server ID angegeben oder die ID des Spielers ist falsch!");
				}
			}
		}

		KickResult kickResult = vp.getStats().getKickResult(player);
		switch(kickResult) {
		case NO_PROJECTUSER:
			event.disallow(Result.KICK_OTHER, Main.getLanguageManager().getValue(LanguageDE.JOIN_KICK_NOT_USER_OF_PROJECT, vp, vp));
			break;
		case BANNED:
			for(BanEntry entry : Bukkit.getBanList(Type.NAME).getBanEntries()) {
				if(entry.getTarget().equals(player.getName())) {
					event.disallow(Result.KICK_OTHER, Main.getLanguageManager().getValue(LanguageDE.JOIN_KICK_BANNED, vp, vp).replace("%reason%", entry.getReason()));
					break;
				}
			}
			break;
		case DEAD:
			event.disallow(Result.KICK_OTHER, Main.getLanguageManager().getValue(LanguageDE.DEATH_KICK_DEAD, vp));
			break;
		case STRIKE_BAN:
			event.disallow(Result.KICK_OTHER, Main.getLanguageManager().getValue(LanguageDE.JOIN_KICK_STRIKE_BAN, vp, vp).replace("%hours%", String.valueOf(ConfigSetting.STRIKE_BAN_AFTER_STRIKE_HOURS.getValueAsInt())));
			break;
		case NOT_IN_TIME:
			event.disallow(Result.KICK_OTHER, Main.getLanguageManager().getValue(LanguageDE.SERVER_MODT_CANT_JOIN_HOURS, vp, vp).replace("%minHour%", String.valueOf(ConfigSetting.ONLY_JOIN_BETWEEN_HOURS_HOUR1.getValueAsInt())).replace("%maxHour%", String.valueOf(ConfigSetting.ONLY_JOIN_BETWEEN_HOURS_HOUR2.getValueAsInt())));
			break;
		case SERVER_FULL:
			event.disallow(Result.KICK_FULL, Main.getLanguageManager().getValue(LanguageDE.JOIN_KICK_SERVER_FULL, vp, vp));
			break;
		case NO_SESSIONS_LEFT:
			event.disallow(Result.KICK_OTHER, Main.getLanguageManager().getValue(LanguageDE.JOIN_KICK_NO_SESSIONS_LEFT, vp, vp));
			break;
		case NO_PREPRODUCES_LEFT:
			event.disallow(Result.KICK_OTHER, Main.getLanguageManager().getValue(LanguageDE.JOIN_KICK_NO_PREPRODUCES_LEFT, vp, vp));
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

			event.disallow(Result.KICK_OTHER, Main.getLanguageManager().getValue(LanguageDE.JOIN_KICK_NO_TIME_LEFT, vp).replace("%timeHours%", String.valueOf(ConfigSetting.JOIN_AFTER_HOURS.getValueAsInt())).replace("%stunden%", hours).replace("%minuten%", minutes).replace("%sekunden%", seconds));
			break;
		case SERVER_NOT_PUBLISHED:
			event.disallow(Result.KICK_OTHER, Main.getLanguageManager().getValue(LanguageDE.JOIN_KICK_NOT_STARTED, vp, vp));
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