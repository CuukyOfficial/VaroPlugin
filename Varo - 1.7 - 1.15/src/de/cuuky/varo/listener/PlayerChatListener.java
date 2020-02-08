package de.cuuky.varo.listener;

import java.util.Date;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.config.ConfigEntry;
import de.cuuky.varo.configuration.messages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.game.VaroGame;
import de.cuuky.varo.gui.utils.chat.ChatHook;
import de.cuuky.varo.listener.helper.ChatMessage;
import de.cuuky.varo.listener.helper.TeamChat;
import de.cuuky.varo.listener.helper.cancelable.CancelAbleType;
import de.cuuky.varo.listener.helper.cancelable.VaroCancelAble;
import de.cuuky.varo.logger.logger.ChatLogger;
import de.cuuky.varo.logger.logger.ChatLogger.ChatLogType;

public class PlayerChatListener implements Listener {

	private void sendMessageToAll(String msg, VaroPlayer vp, AsyncPlayerChatEvent event) {
		if(vp.getStats().getYoutubeLink() == null) {
			event.setCancelled(false);
			event.setFormat(msg);
			return;
		}

		for(VaroPlayer vpo : VaroPlayer.getOnlinePlayer())
			vpo.getNetworkManager().sendLinkedMessage(msg, vp.getStats().getYoutubeLink());
		event.setCancelled(true);
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		if(event.isCancelled())
			return;

		Player player = event.getPlayer();

		String message = event.getMessage();
		ChatHook hook = ChatHook.getChatHook(player);
		if(hook != null) {
			hook.run(message);
			event.setCancelled(true);
			return;
		}

		if(message.contains("%"))
			message = message.replace("%", "");

		boolean mentionsHack = false;
		String[] hackMentions = { "hack", "cheat", "x-ray", "xray", "unlegit" };
		for(String mention : hackMentions) {
			if(message.toLowerCase().contains(mention)) {
				mentionsHack = true;
			}
		}
		if(mentionsHack == true && ConfigEntry.REPORTSYSTEM_ENABLED.getValueAsBoolean()) {
			player.sendMessage(Main.getPrefix() + "§7Erinnerung: Reporte Hacks, Cheats und ähnliches mit §l/report");
		}

		VaroPlayer vp = VaroPlayer.getPlayer(player);
		String tc = ConfigEntry.CHAT_TRIGGER.getValueAsString();
		boolean globalTrigger = ConfigEntry.TRIGGER_FOR_GLOBAL.getValueAsBoolean();
		if((message.startsWith(tc) && !globalTrigger) || (!message.startsWith(tc) && globalTrigger)) {
			new TeamChat(vp, message.replaceFirst("\\" + tc, ""));
			event.setCancelled(true);
			return;
		} else if(message.startsWith(tc)) {
			message = message.replaceFirst("\\" + tc, "");
		}

		if(ConfigEntry.BLOCK_CHAT_ADS.getValueAsBoolean() && !player.isOp()) {
			if(message.matches("(?ui).*(w\\s*w\\s*w|h\\s*t\\s*t\\s*p\\s*s?|[.,;]\\s*(d\\s*e|n\\s*e\\s*t|c\\s*o\\s*m|t\\s*v)).*")) {
				player.sendMessage(Main.getPrefix() + "Du darfst keine Werbung senden - bitte sende keine Links.");
				player.sendMessage(Main.getPrefix() + "Falls dies ein Fehler sein sollte, frage einen Admin.");
				event.setCancelled(true);
				return;
			}
			if(message.matches("(?iu).*(meins?e?m?n?)\\s*(Projekt|Plugin|Server|Netzwerk|Varo).*")) {
				player.sendMessage(Main.getPrefix() + "Du darfst keine Werbung senden - bitte sende keine Eigenwerbung.");
				player.sendMessage(Main.getPrefix() + "Falls dies ein Fehler sein sollte, frage einen Admin.");
				event.setCancelled(true);
				return;
			}
		}

		if(VaroCancelAble.getCancelAble(vp, CancelAbleType.MUTE) != null) {
			player.sendMessage(Main.getPrefix() + ConfigMessages.CHAT_MUTED.getValue());
			event.setCancelled(true);
			return;
		}

		if(!player.isOp()) {
			if((ConfigEntry.CHAT_COOLDOWN_IF_STARTED.getValueAsBoolean() && VaroGame.getInstance().hasStarted()) || !VaroGame.getInstance().hasStarted()) {
				ChatMessage msg = ChatMessage.getMessage(player);
				if(msg != null) {
					long seconds = ((msg.getWritten().getTime() - new Date().getTime()) / 1000) * -1;
					if(seconds < ConfigEntry.CHAT_COOLDOWN_IN_SECONDS.getValueAsInt()) {
						player.sendMessage(Main.getPrefix() + "§7Du kannst nur alle §7" + ConfigEntry.CHAT_COOLDOWN_IN_SECONDS.getValueAsInt() + " §7Sekunden schreiben!");
						event.setCancelled(true);
						return;
					}
				} else if(!player.isOp())
					new ChatMessage(player, message);
			}

			if(VaroGame.getInstance().hasStarted() == false && ConfigEntry.CAN_CHAT_BEFORE_START.getValueAsBoolean() == false) {
				player.sendMessage(Main.getPrefix() + ConfigMessages.CHAT_WHEN_START.getValue());
				event.setCancelled(true);
				return;
			}
		} else
			message = message.replace("&", "§");

		ChatLogger.getInstance().println(ChatLogType.CHAT, player.getName() + "» '" + message + "'");
		sendMessageToAll(vp.getPrefix() + ConfigMessages.CHAT_FORMAT.getValue(vp) + message, vp, event);
	}
}