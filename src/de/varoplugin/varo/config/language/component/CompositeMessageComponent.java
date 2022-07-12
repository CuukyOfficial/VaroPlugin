package de.varoplugin.varo.config.language.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;

import de.varoplugin.varo.config.language.minimessage.MiniMessageCompositeMessageComponentAdapter;
import de.varoplugin.varo.config.language.minimessage.MiniMessageMessageComponent;
import de.varoplugin.varo.config.language.placeholder.ExternalPlaceholderApiPlaceholder;
import de.varoplugin.varo.config.language.placeholder.GlobalPlaceholder;
import de.varoplugin.varo.config.language.placeholder.LocalPlaceholder;
import me.clip.placeholderapi.PlaceholderAPI;

public class CompositeMessageComponent implements ExtendedMessageComponent {

	// Have fun ;)
	private static final Pattern REGEX = Pattern.compile("(?:((?:\\\\%|[^%\\\\])*)(?:%)((?:(?:\\\\%|[^%\\\\])*))(?:%)((?:\\\\%|[^%\\\\])*))|(.+)");

	private MessageComponent[] components;
	private MiniMessageMessageComponent miniComponent;

	public CompositeMessageComponent(String translation, String[] localPlaceholders, Map<String, GlobalPlaceholder> globalPlaceholders, boolean placeholderApiSupport) {
		this.components = processString(translation, localPlaceholders, globalPlaceholders, placeholderApiSupport);
	}

	@Override
	public String value() {
		return Arrays.stream(this.components).map(MessageComponent::value).collect(Collectors.joining());
	}

	@Override
	public String value(Object... placeholders) {
		return Arrays.stream(this.components).map(component -> component.value(placeholders)).collect(Collectors.joining());
	}

	@Override
	public String value(Player player, Object... localPlaceholders) {
		return Arrays.stream(this.components).map(component -> component.value(player, localPlaceholders)).collect(Collectors.joining());
	}

	protected static MessageComponent[] processString(String input, String[] localPlaceholders, Map<String, GlobalPlaceholder> globalPlaceholders, boolean placeholderApiSupport) {
		List<MessageComponent> components = new ArrayList<>();
		Matcher matcher = REGEX.matcher(input);
		while (matcher.find()) {
			for (int i = 1; i <= matcher.groupCount(); i++) {
				String group = matcher.group(i);
				if (group != null) {
					if (i == 2)
						// Placeholder
						components.add(resolvePlaceholder(group, localPlaceholders, globalPlaceholders, placeholderApiSupport));
					else
						// Not a placeholder
						components.add(new SimpleMessageComponent(group));
				}
			}
		}
		return components.toArray(new MessageComponent[components.size()]);
	}

	protected static MessageComponent resolvePlaceholder(String name, String[] localPlaceholders, Map<String, GlobalPlaceholder> globalPlaceholders, boolean placeholderApiSupport) {
		for (int i = 0; i < localPlaceholders.length; i++)
			if (localPlaceholders[i].equals(name))
				return new LocalPlaceholder(name, i);

		GlobalPlaceholder globalPlaceholder = globalPlaceholders.get(name);
		if (globalPlaceholder != null)
			return globalPlaceholder;

		String withPercent = "%" + name + "%";
		if (placeholderApiSupport && PlaceholderAPI.containsPlaceholders(withPercent))
			return new ExternalPlaceholderApiPlaceholder(withPercent);

		return new SimpleMessageComponent(name);
	}

	@Override
	public boolean shouldEscape() {
		return false;
	}

	@Override
	public MiniMessageMessageComponent miniMessage() {
		return this.miniComponent == null ? this.miniComponent = new MiniMessageCompositeMessageComponentAdapter(this.components) : this.miniComponent;
	}
}
