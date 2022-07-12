package de.varoplugin.varo.api.config.language.placeholder;

import org.bukkit.entity.Player;

public class StaticPlaceholder extends GenericGlobalPlaceholder {
	
	private final String value;
	
	public StaticPlaceholder(String name, String value) {
		super(name);
		this.value = value;
	}

	@Override
	public String value() {
		return this.value;
	}

	@Override
	public String value(Player player, Object... localPlaceholders) {
		return this.value();
	}
}
