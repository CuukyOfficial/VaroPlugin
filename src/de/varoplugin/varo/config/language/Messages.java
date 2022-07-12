package de.varoplugin.varo.config.language;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.varoplugin.varo.api.config.language.Language;
import de.varoplugin.varo.api.config.language.components.MessageComponent;
import de.varoplugin.varo.api.config.language.placeholder.GlobalPlaceholder;
import de.varoplugin.varo.api.config.language.translatables.Message;
import de.varoplugin.varo.api.config.language.translatables.Translatable;
import de.varoplugin.varo.api.config.language.translatables.TranslatableMessageArray;
import de.varoplugin.varo.api.config.language.translatables.TranslatableMessageComponent;

public class Messages {

	private final List<Translatable<?>> messages = new ArrayList<>();
	
	public final TranslatableMessageComponent hello_world = new VaroMessage("hello.world", "placeholder");
	public final Translatable<MessageComponent[]> arrayTest = new VaroMessageArray("arraytest");

	public Messages(Language[] languages, int defaultLanguage, Map<String, GlobalPlaceholder> globalPlaceholders, boolean minimessages, boolean papi) {
		this.messages.forEach(message -> message.init(languages, defaultLanguage, globalPlaceholders, papi));
	}
	
	public List<Translatable<?>> getMessages() {
		return this.messages;
	}
	
	private class VaroMessage extends Message {
		public VaroMessage(String path, String... localPlaceholderNames) {
			super(path, localPlaceholderNames);
			Messages.this.getMessages().add(this);
		}
	}
	
	private class VaroMessageArray extends TranslatableMessageArray {
		public VaroMessageArray(String path, String... localPlaceholderNames) {
			super(path, localPlaceholderNames);
			Messages.this.getMessages().add(this);
		}
	}
}
