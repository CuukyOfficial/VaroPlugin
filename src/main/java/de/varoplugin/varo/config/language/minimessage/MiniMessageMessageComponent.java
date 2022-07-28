package de.varoplugin.varo.config.language.minimessage;

import org.bukkit.entity.Player;

import net.kyori.adventure.text.Component;

public interface MiniMessageMessageComponent {

	Component value();

	Component value(Object... localPlaceholders);

	Component value(Player player, Object... localPlaceholders);
}
