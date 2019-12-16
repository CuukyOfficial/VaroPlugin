package de.cuuky.varo.gui.utils.chat;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.cuuky.varo.Main;

public class ChatHook {

	private static ArrayList<ChatHook> chathooks;

	static {
		chathooks = new ArrayList<ChatHook>();
	}

	private Player player;
	private ChatHookListener listener;

	public ChatHook(Player player, String message, ChatHookListener chatHookListener) {
		this.player = player;
		this.listener = chatHookListener;

		if (getChatHook(player) != null)
			getChatHook(player).remove();

		player.sendMessage(Main.getPrefix() + message);

		chathooks.add(this);
	}

	public static ChatHook getChatHook(Player player) {
		for (ChatHook hook : chathooks)
			if (hook.getPlayer().equals(player))
				return hook;

		return null;
	}

	public static ArrayList<ChatHook> getChathooks() {
		return chathooks;
	}

	public Player getPlayer() {
		return player;
	}

	public void run(String input) {
		listener.onChat(input);

		remove();
	}

	public void remove() {
		chathooks.remove(this);
	}
}
