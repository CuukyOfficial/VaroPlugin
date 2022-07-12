package de.varoplugin.varo.config.language.component;

import org.bukkit.entity.Player;

public interface MessageComponent {

	String value();

	String value(Object... localPlaceholders);
	
	String value(Player player, Object... localPlaceholders);
}
