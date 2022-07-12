package de.varoplugin.varo.api.config.language.placeholder;

abstract class GenericGlobalPlaceholder implements GlobalPlaceholder {

	private final String name;

	GenericGlobalPlaceholder(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}
}
