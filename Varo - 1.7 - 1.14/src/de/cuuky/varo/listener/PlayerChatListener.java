package de.cuuky.varo.listener;

import java.util.Date;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.config.messages.ConfigMessages;
import de.cuuky.varo.gui.utils.chat.ChatHook;
import de.cuuky.varo.listener.helper.ChatMessage;
import de.cuuky.varo.listener.helper.TeamChat;
import de.cuuky.varo.listener.helper.cancelable.CancelAbleType;
import de.cuuky.varo.listener.helper.cancelable.VaroCancelAble;
import de.cuuky.varo.logger.logger.ChatLogger.ChatLogType;
import de.cuuky.varo.player.VaroPlayer;

public class PlayerChatListener implements Listener {

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		if (event.isCancelled())
			return;

		Player player = event.getPlayer();

		String message = event.getMessage();
		ChatHook hook = ChatHook.getChatHook(player);
		if (hook != null) {
			hook.run(message);
			event.setCancelled(true);
			return;
		}

		if (message.contains("%"))
			message = message.replaceAll("%", "");

		VaroPlayer vp = VaroPlayer.getPlayer(player);
		String tc = ConfigEntry.TEAMCHAT_TRIGGER.getValueAsString();
		if (message.startsWith(tc)) {
			new TeamChat(vp, message.replaceFirst("\\" + tc, ""));
			event.setCancelled(true);
			return;
		}

		if (VaroCancelAble.getCancelAble(player, CancelAbleType.MUTE) != null) {
			player.sendMessage(Main.getPrefix() + ConfigMessages.CHAT_MUTED.getValue());
			event.setCancelled(true);
			return;
		}

		if (!player.isOp()) {
			if (ConfigEntry.CHAT_COOLDOWN_IF_STARTED.getValueAsBoolean() && Main.getGame().isStarted()
					|| !Main.getGame().isStarted()) {
				ChatMessage msg = ChatMessage.getMessage(player);
				if (msg != null) {
					long seconds = ((msg.getWritten().getTime() - new Date().getTime()) / 1000) * -1;
					if (seconds < ConfigEntry.CHAT_COOLDOWN_IN_SECONDS.getValueAsInt()) {
						player.sendMessage(Main.getPrefix() + "§7Du kannst nur alle §7"
								+ ConfigEntry.CHAT_COOLDOWN_IN_SECONDS.getValueAsInt() + " §7Sekunden schreiben!");
						event.setCancelled(true);
						return;
					}
				} else if (!player.isOp())
					new ChatMessage(player, message);
			}

			if (Main.getGame().isStarted() == false && ConfigEntry.CAN_CHAT_BEFORE_START.getValueAsBoolean() == false) {
				player.sendMessage(Main.getPrefix() + ConfigMessages.CHAT_WHEN_START.getValue());
				event.setCancelled(true);
				return;
			}
		} else
			message = message.replaceAll("&", "§");

		Main.getLoggerMaster().getChatLogger().println(ChatLogType.CHAT, player.getName() + "» '" + message + "'");
		sendMessageToAll(vp.getPrefix() + ConfigMessages.CHAT_FORMAT.getValue(vp) + message, vp, event);
	}

	private void sendMessageToAll(String msg, VaroPlayer vp, AsyncPlayerChatEvent event) {
		if (vp.getStats().getYoutubeLink() == null) {
			event.setCancelled(false);
			event.setFormat(msg);
			return;
		}

		for (VaroPlayer vpo : VaroPlayer.getOnlinePlayer())
			vpo.getNetworkManager().sendLinkedMessage(msg, vp.getStats().getYoutubeLink());
		event.setCancelled(true);
	}
}