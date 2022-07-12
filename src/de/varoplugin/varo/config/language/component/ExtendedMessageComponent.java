package de.varoplugin.varo.config.language.component;

import de.varoplugin.varo.config.language.minimessage.MiniMessageMessageComponent;

// Hopefully someone comes up with a better name for this interface
public interface ExtendedMessageComponent extends MessageComponent {

	MiniMessageMessageComponent miniMessage();
}
