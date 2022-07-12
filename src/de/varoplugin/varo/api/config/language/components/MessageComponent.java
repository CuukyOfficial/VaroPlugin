package de.varoplugin.varo.api.config.language.components;

import org.bukkit.entity.Player;

public interface MessageComponent {

	String value();

	String value(Object... localPlaceholders);
	
	String value(Player player, Object... localPlaceholders);
}
