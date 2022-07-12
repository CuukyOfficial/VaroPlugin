package de.varoplugin.varo.config.language;

import de.varoplugin.varo.api.config.language.AbstractLanguage;
import de.varoplugin.varo.api.config.language.StringArrayTranslation;
import de.varoplugin.varo.api.config.language.StringTranslation;
import de.varoplugin.varo.api.config.language.Translation;

public class GermanLanguage extends AbstractLanguage {
	
	private static final String NAME = "de";

	public GermanLanguage(int id) {
		super(id, NAME, getDefaultValues());
	}
	
	private static Translation<?>[] getDefaultValues() {
		return new Translation<?>[] {
			new StringTranslation("hello.world", "SPRICH DEUTSCH DU HURENSOHN!!!"),
			new StringArrayTranslation("arraytest", "de_val0", "de_val1")
		};
	}
}
