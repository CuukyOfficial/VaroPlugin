package de.varoplugin.varo.config.language.placeholder;

import de.varoplugin.varo.config.language.component.MessageComponent;

public interface GlobalPlaceholder extends MessageComponent {

	String getName();
	
	@Override
	default String value(Object... localPlaceholders) {
		return this.value();
	}
}
