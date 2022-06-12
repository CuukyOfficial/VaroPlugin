package de.varoplugin.varo.bot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class BotMessageComponent {

	private final String content;
	private final boolean escape;

	public BotMessageComponent(String content, boolean escape) {
		this.content = content;
		this.escape = escape;
	}

	public String getContent() {
		return this.content;
	}

	public boolean isEscape() {
		return this.escape;
	}

	public static BotMessageComponent[] splitPlaceholders(String content, boolean escapeNonPlaceholder, boolean escapePlaceholder, String... placecholders) {
		List<BotMessageComponent> components = new ArrayList<>(Arrays.asList(new BotMessageComponent(content, escapeNonPlaceholder)));

		for (int i = 0; i < placecholders.length; i++) {
			for (int j = 0; j < components.size(); j++) {
				String[] split = components.get(j).content.split(Pattern.quote(placecholders[i]), -1);
				int insert = j;
				if (split.length > 1) {
					components.remove(j);
					for (int k = 0; k < split.length; k++) {
						if (k != 0) {
							components.add(new BotMessageComponent(placecholders[i], escapePlaceholder));
							j++;
						}
						if (!split[k].isEmpty()) {
							components.add(insert + k * 2, new BotMessageComponent(split[k], escapeNonPlaceholder));
							if(k != 0)
								j++;
						}
					}
				}
			}
		}
		return components.toArray(new BotMessageComponent[components.size()]);
	}

	public static BotMessageComponent[] replacePlaceholders(BotMessageComponent[] components, String[] placeholders, String[] values) {
		if(placeholders.length != values.length)
			throw new IllegalArgumentException();

		BotMessageComponent[] result = new BotMessageComponent[components.length];

		for (int i = 0; i < placeholders.length; i++)
			for (int j = 0; j < components.length; j++) {
				if (components[j].content.equals(placeholders[i]))
					result[j] = new BotMessageComponent(values[i], components[j].escape);
				else if (i == 0)
					result[j] = components[j];
			}
		return result;
	}
}
