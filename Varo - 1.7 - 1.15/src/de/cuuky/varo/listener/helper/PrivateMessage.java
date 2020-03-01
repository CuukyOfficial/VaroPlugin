package de.cuuky.varo.listener.helper;

import java.util.ArrayList;
import java.util.Date;

import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.logger.logger.ChatLogger.ChatLogType;

public class PrivateMessage {

	private static ArrayList<PrivateMessage> messages = new ArrayList<>();

	private String message;
	private Player reciever;
	private Player sender;
	private Date written;

	public PrivateMessage(Player reciever, Player sender, String message) {
		this.reciever = reciever;
		this.sender = sender;
		this.message = message;
		this.written = new Date();

		Main.getDataManager().getVaroLoggerManager().getChatLogger().println(ChatLogType.PRIVATE_CHAT, sender.getName() + " >> " + reciever.getName() + ": " + message);

		messages.add(this);
	}

	public String getMessage() {
		return message;
	}

	public Player getReciever() {
		return reciever;
	}

	public Player getSender() {
		return sender;
	}

	public Date getWritten() {
		return written;
	}

	public static PrivateMessage getMessage(Player player) {
		for(PrivateMessage pmessage : messages) {
			if(pmessage.getReciever().equals(player))
				continue;

			return pmessage;
		}

		return null;
	}
}