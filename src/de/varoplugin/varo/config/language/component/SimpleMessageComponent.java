package de.varoplugin.varo.config.language.component;

import org.bukkit.entity.Player;

public class SimpleMessageComponent implements MessageComponent {
	
	private final String value;
	
	public SimpleMessageComponent(String value) {
		this.value = value;
	}

	@Override
	public String value() {
		return this.value;
	}

	@Override
	public String value(Object... placeholders) {
		return this.value;
	}

	@Override
	public String value(Player player, Object... placeholders) {
		return this.value;
	}
}
