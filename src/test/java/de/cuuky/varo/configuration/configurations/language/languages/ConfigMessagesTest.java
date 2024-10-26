package de.cuuky.varo.configuration.configurations.language.languages;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class ConfigMessagesTest {

	@Test
	public void testPaths() {
		Set<String> paths = new HashSet<>();
		for (ConfigMessages message : ConfigMessages.values())
			assertTrue(paths.add(message.getPath()), "Duplicate message path: " + message.getPath());
	}
}
