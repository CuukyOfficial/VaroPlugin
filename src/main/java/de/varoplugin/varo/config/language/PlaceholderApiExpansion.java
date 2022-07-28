package de.varoplugin.varo.config.language;

import java.util.Map;

import org.bukkit.entity.Player;

import de.varoplugin.varo.config.language.placeholder.GlobalPlaceholder;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class PlaceholderApiExpansion extends PlaceholderExpansion {

	private final String identifier;
	private final String author;
	private final String version;
	private final Map<String, GlobalPlaceholder> placeholders;

	public PlaceholderApiExpansion(String identifier, String author, String version, Map<String, GlobalPlaceholder> placeholders) {
		this.identifier = identifier;
		this.author = author;
		this.version = version;
		this.placeholders = placeholders;
	}

	@Override
	public String getIdentifier() {
		return this.identifier;
	}

	@Override
	public String getAuthor() {
		return this.author;
	}

	@Override
	public String getVersion() {
		return this.version;
	}

	@Override
	public String onPlaceholderRequest(Player player, String params) {
		GlobalPlaceholder globalPlaceholder = this.placeholders.get(params);
		if (globalPlaceholder == null)
			return null;
		if (player == null)
			return globalPlaceholder.value();
		return globalPlaceholder.value(player);
	}
}
