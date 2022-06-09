package de.varoplugin.varo.config;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.varoplugin.varo.api.config.ConfigCategory;
import de.varoplugin.varo.api.config.ConfigEntry;
import de.varoplugin.varo.api.config.ConfigException;

public class VaroConfigTest {

	private static final String PATH = "build/test/config/";

	@BeforeEach
	public void deleteConfigs() {
		new VaroConfig(PATH).delete();
	}

	@Test
	public void testVaroDefaultValue() throws ConfigException, IOException {
		VaroConfig config = new VaroConfig(PATH);
		config.load();
		assertEquals(config.scoreboard_enabled.getDefaultValue(), config.scoreboard_enabled.getValue());
	}

	@Test
	public void testSave() throws ConfigException, IOException {
		VaroConfig config = new VaroConfig(PATH);
		config.load();
		config.offlinemode.setValue(true);
		config.dump();

		config = new VaroConfig(PATH);
		config.load();
		assertTrue(config.offlinemode.getValue());
	}

	@Test
	public void testDuplicatePaths() throws ConfigException, IOException {
		VaroConfig config = new VaroConfig(PATH);
		config.load();
		for (ConfigCategory category : config.getConfigEntries().keys()) {
			Set<String> paths = new HashSet<>();
			for (ConfigEntry<?> entry : config.getConfigEntries().get(category))
				if (!paths.add(entry.getPath()))
					fail("Duplicate config entry: " + entry.getPath());
		}
	}
}
