package de.varoplugin.varo.config.language.placeholder;

import org.bukkit.entity.Player;

import de.varoplugin.varo.config.language.component.MessageComponent;
import me.clip.placeholderapi.PlaceholderAPI;

public class ExternalPlaceholderApiPlaceholder implements MessageComponent {

	private String name;

	public ExternalPlaceholderApiPlaceholder(String name) {
		this.name = name;
	}

	@Override
	public String value() {
		return PlaceholderAPI.setPlaceholders(null, this.name);
	}

	@Override
	public String value(Object... localPlaceholders) {
		return this.value();
	}

	@Override
	public String value(Player player, Object... localPlaceholders) {
		return PlaceholderAPI.setPlaceholders(player, this.name);
	}

	@Override
	public boolean shouldEscape() {
		return true;
	}
}
