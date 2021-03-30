package de.cuuky.varo.listener.helper;

import java.util.ArrayList;
import java.util.Date;

import org.bukkit.entity.Player;

public class ChatMessage {

	private static ArrayList<ChatMessage> messages = new ArrayList<>();

	private String message;
	private Player player;
	private Date written;

	public ChatMessage(Player player, String message) {
		this.player = player;
		this.message = message;
		this.written = new Date();

		messages.add(this);
	}

	public String getMessage() {
		return message;
	}

	public Player getPlayer() {
		return player;
	}

	public Date getWritten() {
		return written;
	}

	public static ChatMessage getMessage(Player player) {
		for (ChatMessage cmessage : messages) {
			if (cmessage.getPlayer().equals(player))
				continue;

			return cmessage;
		}

		return null;
	}
}