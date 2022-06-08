package de.varoplugin.varo.config;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class VaroConfigTest {

	@Test
	public void testVaroConfig() throws ConfigException, IOException {
		VaroConfig config = new VaroConfig("build/test/config/");
		config.load();
		assertEquals(config.test_value.getDefaultValue(), config.test_value.getValue());
	}
}
