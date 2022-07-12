package de.varoplugin.varo.config.language;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.varoplugin.varo.config.language.translatable.Translatable;

public class MessageTest {

	private static AbstractLanguage[] languages;
	private static Messages messages;

	@BeforeAll
	public static void init() {
		languages = new AbstractLanguage[] { new EnglishLanguage(0) };
		messages = new Messages(languages, 0, new HashMap<>(), false, false);
	}

	@Test
	public void testMissingTranslations() {
		for (Translatable<?> message : messages.getMessages())
			for (Language language : languages)
				assertNotNull(language.getTranslation(message.getPath()));
	}
	
	@Test
	public void testDuplicatePaths() {
		Set<String> paths = new HashSet<>();
		for (Translatable<?> message : messages.getMessages()) {
			assertFalse(paths.contains(message.getPath()));
			paths.add(message.getPath());
		}
	}
}
