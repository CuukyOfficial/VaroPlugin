package de.cuuky.varo.entity.player.event.events;

import de.cuuky.varo.Main;
import de.cuuky.varo.bot.discord.VaroDiscordBot;
import de.cuuky.varo.bot.discord.register.BotRegister;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.event.BukkitEvent;
import de.cuuky.varo.entity.player.event.BukkitEventType;
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

		if(db != null)
			if(db.isEnabled() || !ConfigSetting.DISCORDBOT_ADD_POKAL_ON_WIN.getValueAsBoolean())
				return;

		BotRegister register = BotRegister.getBotRegisterByPlayerName(player.getName());
		if(register == null)
			return; 
		
		Member member = register.getMember();
		int wins = 1;
		String name = member.getNickname() == null ? member.getUser().getName() : member.getNickname();

		if(name.contains("|")) {
			wins = Integer.valueOf(name.split("\\|")[1].replace("ð", "").replace(" ", ""));
			wins++;
		}

		try {
			member.modifyNickname(member.getUser().getName() + " | " + wins + " ð");
		} catch(PermissionException e) {
			System.out.println("[Varo] Konnte den Pokal für '" + player.getName() + "' nicht setzen, da dieser Bot zu wenig, oder der Nutzer zu viele Rechte auf dem Discord hat!");
		}
	}
}