package de.varoplugin.varo.config.language;

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
