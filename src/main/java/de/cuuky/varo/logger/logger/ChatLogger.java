package de.cuuky.varo.logger.logger;

import org.bukkit.entity.Player;

import com.google.gson.annotations.Expose;

import de.cuuky.varo.logger.PlayerLogElement;
import de.cuuky.varo.logger.VaroLogger;

public class ChatLogger extends VaroLogger<ChatLogElement> {

    public ChatLogger(String name) {
        super(name);
    }

    public void println(ChatLogType type, Player player, String recipient, String message) {
        this.queueLog(new ChatLogElement(System.currentTimeMillis(), player.getUniqueId().toString(), player.getName(), type.getName(), recipient, message));
    }
    
    public enum ChatLogType {
        CHAT("CHAT"),
        PRIVATE_CHAT("PRIVATECHAT"),
        TEAM_CHAT("TEAMCHAT");

        private String name;

        ChatLogType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}

class ChatLogElement extends PlayerLogElement {

	@Expose
	private String type;
	@Expose
	private String recipient;
	@Expose
	private String message;

	public ChatLogElement(long timestamp, String uuid, String name, String type, String recipient, String message) {
		super(timestamp, name, uuid);
		this.type = type;
		this.recipient = recipient;
		this.message = message;
	}
}