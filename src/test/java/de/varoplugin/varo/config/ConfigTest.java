package de.varoplugin.varo.config;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.varoplugin.varo.api.config.ConfigException;

public class ConfigTest {
	
	private static final String PATH = "build/test/mock-config/";
	
	@BeforeEach
	public void deleteConfigs() {
		new MockConfig(PATH).delete();
	}

	@Test
	public void testList() throws ConfigException, IOException {
		MockConfig config = new MockConfig(PATH);
		config.load();

		config = new MockConfig(PATH);
		config.load();
		assertArrayEquals(MockConfig.LIST.toArray(), config.list_entry.getValue().toArray());
	}
}
