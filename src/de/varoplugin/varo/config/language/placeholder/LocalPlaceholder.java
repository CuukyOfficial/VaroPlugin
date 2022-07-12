package de.varoplugin.varo.config.language.placeholder;

import org.bukkit.entity.Player;

import de.varoplugin.varo.config.language.component.MessageComponent;

public class LocalPlaceholder implements MessageComponent {
	
	private final String name;
	private final int id;
	
	public LocalPlaceholder(String name, int id) {
		this.name = name;
		this.id = id;
	}

	@Override
	public String value() {
		return "%" + this.name + "%";
	}

	@Override
	public String value(Object... localPlaceholders) {
		return localPlaceholders[this.id].toString();
	}

	@Override
	public String value(Player player, Object... localPlaceholders) {
		return localPlaceholders[this.id].toString();
	}

}
