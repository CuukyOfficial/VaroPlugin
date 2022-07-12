package de.varoplugin.varo.config.language;

import de.varoplugin.varo.api.config.language.AbstractLanguage;
import de.varoplugin.varo.api.config.language.StringArrayTranslation;
import de.varoplugin.varo.api.config.language.StringTranslation;
import de.varoplugin.varo.api.config.language.Translation;

public class EnglishLanguage extends AbstractLanguage {
	
	private static final String NAME = "en";

	public EnglishLanguage(int id) {
		super(id, NAME, getDefaultValues());
	}
	
	private static Translation<?>[] getDefaultValues() {
		return new Translation<?>[] {
			new StringTranslation("hello.world", "Hello World"),
			new StringArrayTranslation("arraytest", "en_val0", "en_val1")
		};
	}
}
