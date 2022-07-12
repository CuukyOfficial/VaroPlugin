package de.varoplugin.varo.config.language.minimessage;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;

import de.varoplugin.varo.config.language.component.MessageComponent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class MiniMessageCompositeMessageComponentAdapter implements MiniMessageMessageComponent {

	private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

	private final MessageComponent[] components;

	public MiniMessageCompositeMessageComponentAdapter(MessageComponent[] components) {
		this.components = components;
	}

	@Override
	public Component value() {
		return MINI_MESSAGE.deserialize(Arrays.stream(this.components)
				.map(component -> component.shouldEscape() ? MINI_MESSAGE.escapeTags(component.value()) : component.value()).collect(Collectors.joining()));
	}

	@Override
	public Component value(Object... localPlaceholders) {
		return MINI_MESSAGE.deserialize(Arrays.stream(this.components)
				.map(component -> component.shouldEscape() ? MINI_MESSAGE.escapeTags(component.value(localPlaceholders)) : component.value(localPlaceholders)).collect(Collectors.joining()));
	}

	@Override
	public Component value(Player player, Object... localPlaceholders) {
		return MINI_MESSAGE.deserialize(Arrays.stream(this.components)
				.map(component -> component.shouldEscape() ? MINI_MESSAGE.escapeTags(component.value(player, localPlaceholders)) : component.value(player, localPlaceholders)).collect(Collectors.joining()));
	}
}
