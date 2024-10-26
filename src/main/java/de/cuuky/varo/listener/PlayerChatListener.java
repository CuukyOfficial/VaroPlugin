package de.cuuky.varo.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.listener.helper.ChatMessage;
import de.cuuky.varo.listener.helper.TeamChat;
import de.cuuky.varo.listener.helper.cancelable.CancelableType;
import de.cuuky.varo.listener.helper.cancelable.VaroCancelable;
import de.cuuky.varo.logger.logger.ChatLogger.ChatLogType;
import de.cuuky.varo.player.VaroPlayer;

public class PlayerChatListener implements Listener {
	
	private static final String AD_REGEX = "(?ui).*(w\\s*w\\s*w|h\\s*t\\s*t\\s*p\\s*s?|[.](d\\s*e|n\\s*e\\s*t|c\\s*o\\s*m|t\\s*v|t\\s*o)).*";
	private static final String AD_REGEX_AGRESSIVE = "(?ui).*(w\\s*w\\s*w|h\\s*t\\s*t\\s*p\\s*s?|[.,;]\\s*(d\\s*e|n\\s*e\\s*t|c\\s*o\\s*m|t\\s*v|t\\s*o)).*";

	private void sendMessageToAll(String msg, VaroPlayer vp, AsyncPlayerChatEvent event) {
		if (vp.getStats().getYoutubeLink() == null) {
			event.setCancelled(false);
			event.setFormat(msg);
			return;
		}

		for (VaroPlayer vpo : VaroPlayer.getOnlinePlayer())
			vpo.getVersionAdapter().sendLinkedMessage(msg, vp.getStats().getYoutubeLink());
		event.setCancelled(true);
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		if (event.isCancelled())
			return;

		String message = event.getMessage();
		Player player = event.getPlayer();

		if (message.contains("%"))
			message = message.replace("%", "");

		boolean mentionsHack = false;
		String[] hackMentions = { "hack", "cheat", "x-ray", "xray", "unlegit" };
		for (String mention : hackMentions) {
			if (message.toLowerCase().contains(mention)) {
				mentionsHack = true;
			}
		}
		if (mentionsHack == true && ConfigSetting.REPORTSYSTEM_ENABLED.getValueAsBoolean()) {
			player.sendMessage(Main.getPrefix() + "§7Erinnerung: Reporte Hacks, Cheats und aehnliches mit §l/report");
		}

		VaroPlayer vp = VaroPlayer.getPlayer(player);
		String tc = ConfigSetting.CHAT_TRIGGER.getValueAsString();
		boolean globalTrigger = ConfigSetting.TRIGGER_FOR_GLOBAL.getValueAsBoolean();
		if ((message.startsWith(tc) && !globalTrigger) || (!message.startsWith(tc) && globalTrigger)) {
			new TeamChat(vp, message.replaceFirst("\\" + tc, ""));
			event.setCancelled(true);
			return;
		} else if (message.startsWith(tc)) {
			message = message.replaceFirst("\\" + tc, "");
		}

		if (ConfigSetting.BLOCK_CHAT_ADS.getValueAsBoolean() && !player.isOp()) {
			if (message.matches(ConfigSetting.BLOCK_CHAT_ADS_AGRESSIVE.getValueAsBoolean() ? AD_REGEX_AGRESSIVE : AD_REGEX)) {
				player.sendMessage(Main.getPrefix() + "Du darfst keine Werbung senden - bitte sende keine Links.");
				player.sendMessage(Main.getPrefix() + "Falls dies ein Fehler sein sollte, frage einen Admin.");
				event.setCancelled(true);
				return;
			}
			
			if (message.matches("(?iu).*(meins?e?m?n?)\\s*(Projekt|Plugin|Server|Netzwerk|Varo).*")) {
				player.sendMessage(Main.getPrefix() + "Du darfst keine Werbung senden - bitte sende keine Eigenwerbung.");
				player.sendMessage(Main.getPrefix() + "Falls dies ein Fehler sein sollte, frage einen Admin.");
				event.setCancelled(true);
				return;
			}
		}

		if (VaroCancelable.getCancelable(vp, CancelableType.MUTE) != null) {
			vp.sendMessage(ConfigMessages.CHAT_MUTED);
			event.setCancelled(true);
			return;
		}

		if (!player.isOp()) {
			if (ConfigSetting.CHAT_COOLDOWN_IF_STARTED.getValueAsBoolean() || !Main.getVaroGame().hasStarted()) {
				ChatMessage msg = vp.getLastMessage();
				if (msg != null) {
					long delta = System.currentTimeMillis() - msg.getTimestamp();
					if (delta < ConfigSetting.CHAT_COOLDOWN_IN_SECONDS.getValueAsInt() * 1000) {
						player.sendMessage(Main.getPrefix() + "§7Du kannst nur alle §7" + ConfigSetting.CHAT_COOLDOWN_IN_SECONDS.getValueAsInt() + " §7Sekunden schreiben!");
						event.setCancelled(true);
						return;
					}
				}
				vp.setLastMessage(new ChatMessage(message));
			}

			if (!Main.getVaroGame().hasStarted() && !ConfigSetting.CAN_CHAT_BEFORE_START.getValueAsBoolean()) {
				vp.sendMessage(ConfigMessages.CHAT_WHEN_START);
				event.setCancelled(true);
				return;
			}
		} else
			message = message.replace("&", "§");

		if (!ConfigSetting.SPECTATOR_CHAT.getValueAsBoolean() && vp.getStats().isSpectator() && !player.hasPermission("varo.spectatorchat")) {
			vp.sendMessage(ConfigMessages.CHAT_SPECTATOR);
			event.setCancelled(true);
			return;
		}

		Main.getDataManager().getVaroLoggerManager().getChatLogger().println(ChatLogType.CHAT, player, null, message);

		String messageFormat = "";
		if (vp.getTeam() != null) {
			if (vp.getRank() == null) {
				messageFormat = ConfigMessages.CHAT_PLAYER_WITH_TEAM.getValue(null, vp);
			} else {
				messageFormat = ConfigMessages.CHAT_PLAYER_WITH_TEAM_RANK.getValue(null, vp);
			}
		} else {
			if (vp.getRank() == null) {
				messageFormat = ConfigMessages.CHAT_PLAYER_WITHOUT_TEAM.getValue(null, vp);
			} else {
				messageFormat = ConfigMessages.CHAT_PLAYER_WITHOUT_TEAM_RANK.getValue(null, vp);
			}
		}

		sendMessageToAll(messageFormat.replace("%message%", message), vp, event);
	}
}