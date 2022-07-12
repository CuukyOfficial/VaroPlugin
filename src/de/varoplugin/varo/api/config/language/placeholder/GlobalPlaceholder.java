package de.varoplugin.varo.api.config.language.placeholder;

import de.varoplugin.varo.api.config.language.components.MessageComponent;

public interface GlobalPlaceholder extends MessageComponent {

	String getName();
	
	@Override
	default String value(Object... localPlaceholders) {
		return this.value();
	}
}
