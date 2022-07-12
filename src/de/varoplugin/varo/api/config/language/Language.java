package de.varoplugin.varo.api.config.language;

import java.util.Arrays;

public interface Language {
	
	int getId();
	
	String getName();
	
	Translation<?>[] getTranslations();
	
	default Translation<?> getTranslation(String path) {
		return Arrays.stream(this.getTranslations()).filter(translation -> path.equals(translation.path())).findAny().orElseThrow(Error::new);
	}
}
