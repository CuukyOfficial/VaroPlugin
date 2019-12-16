package de.cuuky.varo.entity.player.event.events;

import org.bukkit.Bukkit;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.exceptions.PermissionException;

import de.cuuky.varo.bot.BotLauncher;
import de.cuuky.varo.bot.discord.VaroDiscordBot;
import de.cuuky.varo.bot.discord.register.BotRegister;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.event.BukkitEvent;
import de.cuuky.varo.entity.player.event.BukkitEventType;

public class WinEvent extends BukkitEvent {

	public WinEvent() {
		super(BukkitEventType.WIN);
	}

	@Override
	public void onExec(VaroPlayer player) {
		player.getStats().addWin();
		VaroDiscordBot db = BotLauncher.getDiscordBot();

		if (db != null)
			if (db.isEnabled() || !ConfigEntry.DISCORDBOT_ADD_POKAL_ON_WIN.getValueAsBoolean())
				return;

		Member member = BotRegister.getBotRegisterByPlayerName(player.getName()).getMember();
		int wins = 1;
		String name = member.getNickname() == null ? member.getUser().getName() : member.getNickname();

		if (name.contains("|")) {
			wins = Integer.valueOf(name.split("\\|")[1].replace("ð", "").replaceAll(" ", ""));
			wins++;
		}

		try {
			db.getController().setNickname(member, member.getUser().getName() + " | " + wins + " ð").complete();
		} catch (PermissionException e) {
			System.out.println("[Varo] Konnte den Pokal für '" + player.getName() + "' nicht setzen, da dieser Bot zu wenig, oder der Nutzer zu viele Rechte auf dem Discord hat!");
		}

		if (ConfigEntry.STOP_SERVER_ON_WIN.getValueAsBoolean()) {
			Bukkit.getServer().shutdown();
		}
	}
}
