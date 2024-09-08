package de.cuuky.varo.configuration.configurations.config;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class ConfigSettingTest {

    @Disabled
	@Test
	public void testPaths() {
		Set<String> paths = new HashSet<>();
		for (ConfigSetting setting : ConfigSetting.values())
			assertTrue(paths.add(setting.getFullPath()), "Duplicate config path: " + setting.getFullPath());
	}
}
