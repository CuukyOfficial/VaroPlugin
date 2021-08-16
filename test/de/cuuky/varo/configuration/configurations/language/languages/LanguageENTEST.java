package de.cuuky.varo.configuration.configurations.language.languages;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

public class LanguageENTEST {

	@Test
	public void testPaths() {
		Set<String> paths = new HashSet<>();
		for (LanguageEN message : LanguageEN.values())
			assertTrue(paths.add(message.getPath()), "Duplicate message path (en): " + message.getPath());
	}

	@Test
	public void testPathsEn() {
		Set<String> paths = Arrays.stream(ConfigMessages.values()).map(ConfigMessages::getPath)
				.collect(Collectors.toSet());
		Set<String> usedPaths = new HashSet<>();

		for (LanguageEN message : LanguageEN.values()) {
			String path = message.getPath();
			if (paths.contains(path))
				usedPaths.add(path);
			else
				fail("Unused message path (en): " + message.getPath());
		}

		paths.parallelStream().filter(path -> !usedPaths.contains(path))
				.forEach(path -> System.err.println("Missing message (en): " + path));
	}
}
