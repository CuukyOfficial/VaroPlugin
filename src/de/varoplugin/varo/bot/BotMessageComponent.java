package de.varoplugin.varo.bot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class BotMessageComponent {

	private final String content;
	private final boolean placeholder;
	private final boolean escape;

	public BotMessageComponent(String content, boolean placeholder, boolean escape) {
		this.content = content;
		this.placeholder = placeholder;
		this.escape = escape;
	}

	public BotMessageComponent(String content, boolean escape) {
		this(content, true, escape);
	}

	public String getContent() {
		return this.content;
	}

	public boolean isPlaceholder() {
		return this.placeholder;
	}

	public boolean isEscape() {
		return this.escape;
	}

	public static BotMessageComponent[] splitPlaceholders(String content, boolean escapeNonPlaceholder, boolean escapePlaceholder, String... placecholders) {
		List<BotMessageComponent> components = new ArrayList<>(Collections.singletonList(new BotMessageComponent(content, escapeNonPlaceholder)));

		for (String placecholder : placecholders) {
			for (int j = 0; j < components.size(); j++) {
				String[] split = components.get(j).content.split(Pattern.quote(placecholder), -1);
				int insert = j;
				if (split.length > 1) {
					components.remove(j);
					for (int k = 0; k < split.length; k++) {
						if (k != 0) {
							components.add(new BotMessageComponent(placecholder, true, escapePlaceholder));
							j++;
						}
						if (!split[k].isEmpty()) {
							components.add(insert + k * 2, new BotMessageComponent(split[k], false, escapeNonPlaceholder));
							if (k != 0)
								j++;
						}
					}
				}
			}
		}
		return components.toArray(new BotMessageComponent[0]);
	}

	public static BotMessageComponent[] replacePlaceholders(BotMessageComponent[] components, String[] placeholders, String[] values) {
		if(placeholders.length != values.length)
			throw new IllegalArgumentException();

		BotMessageComponent[] result = new BotMessageComponent[components.length];

		for (int i = 0; i < placeholders.length; i++)
			for (int j = 0; j < components.length; j++) {
				BotMessageComponent component = components[j];
				if (component.isPlaceholder() && component.content.equals(placeholders[i]))
					result[j] = new BotMessageComponent(values[i], component.placeholder, component.escape);
				else if (i == 0)
					result[j] = component;
			}
		return result;
	}
}
