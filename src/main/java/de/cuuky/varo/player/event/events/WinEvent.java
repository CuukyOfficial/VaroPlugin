package de.cuuky.varo.player.event.events;

import java.util.logging.Level;

import de.cuuky.varo.Main;
import de.cuuky.varo.bot.discord.BotRegister;
import de.cuuky.varo.bot.discord.VaroDiscordBot;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.player.event.BukkitEvent;
import de.cuuky.varo.player.event.BukkitEventType;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.exceptions.PermissionException;

public class WinEvent extends BukkitEvent {

	public WinEvent() {
		super(BukkitEventType.WIN);
	}

	@Override
	public void onExec(VaroPlayer player) {
		player.getStats().addWin();
		VaroDiscordBot db = Main.getBotLauncher().getDiscordbot();

		if (db != null)
			if (db.isEnabled() || !ConfigSetting.DISCORDBOT_ADD_TROPHY_ON_WIN.getValueAsBoolean())
				return;

		BotRegister register = BotRegister.getBotRegisterByPlayerName(player.getName());
		if (register == null)
			return;

		Member member = register.getMember();
		int wins = 1;
		String name = member.getNickname() == null ? member.getUser().getName() : member.getNickname();

		if (name.contains("|"))
		    try {
		        wins = Integer.valueOf(name.split("\\|")[1].replace("🏆", "").replace(" ", ""));
		        wins++;
		    } catch (NumberFormatException e) {
		        // nop
		    }

		try {
			member.modifyNickname(member.getUser().getName() + " | " + wins + " 🏆");
		} catch (PermissionException e) {
		    Main.getInstance().getLogger().log(Level.SEVERE, "Insufficient permissions to change Discord nickname of member {}", member.getEffectiveName());
		}
	}
}