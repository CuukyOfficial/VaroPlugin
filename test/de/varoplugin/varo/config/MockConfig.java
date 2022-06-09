package de.varoplugin.varo.config;

import static de.varoplugin.varo.config.VaroConfigCategory.*;

import java.util.Arrays;
import java.util.List;

import de.varoplugin.varo.api.config.ConfigEntry;

public class MockConfig extends AbstractConfig {
	
	public static final List<String> LIST = Arrays.asList("ABC", "DEF");
	
	public final ConfigEntry<Integer> int_entry = new ConfigEntryImpl<>(MAIN, "int", 0__0, "u good java???");
	public final ConfigEntry<Long> long_entry = new ConfigEntryImpl<>(MAIN, "long", 69L, "69");
	// public final ConfigEntry<DisappointingMinecraftUpdates> enum_entry = new ConfigEntryImpl<>(MAIN, "enum", DisappointingMinecraftUpdates.RELEASE_1_9, "Thx Microsoft");
	public final ConfigEntry<List<String>> list_entry = new ConfigEntryImpl<>(MAIN, "list", LIST, "404 comment not found");
	
	protected MockConfig(String path) {
		super(path);
	}
	
	public enum DisappointingMinecraftUpdates {
		
		RELEASE_1_9,
		RELEASE_1_10,
		RELEASE_1_12,
		RELEASE_1_15,
		RELEASE_1_17,
		RELEASE_1_19,
		EVERYTHING_ELSE_SINCE_MICROSOFT_BOUGHT_MOJANG;
	}
}
