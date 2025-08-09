package de.varoplugin.varo.configuration.configurations.config;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;

public class ConfigSettingTest {

    @Disabled
	@Test
	public void testPaths() {
		Set<String> paths = new HashSet<>();
		for (ConfigSetting setting : ConfigSetting.values())
			assertTrue(paths.add(setting.getFullPath()), "Duplicate config path: " + setting.getFullPath());
	}
}
