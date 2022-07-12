package de.varoplugin.varo.config.language;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import de.varoplugin.varo.VaroPlugin;
import de.varoplugin.varo.api.config.language.placeholder.GlobalPlaceholder;
import de.varoplugin.varo.api.config.language.placeholder.StaticPlaceholder;

public class Placeholders {

	public static Map<String, GlobalPlaceholder> getPlaceholders(VaroPlugin varo) {
		return Collections.unmodifiableMap(Arrays.stream(new GlobalPlaceholder[] {
				new StaticPlaceholder("pluginname", varo.getDescription().getName()),
				new StaticPlaceholder("pluginversion", varo.getDescription().getName()),
				new StaticPlaceholder("plugin", varo.getDescription().getName()),
				new StaticPlaceholder("website", varo.getWebsite()),
				new StaticPlaceholder("github", varo.getGithub()),
				new StaticPlaceholder("discord", varo.getDiscordInvite()),
				new StaticPlaceholder("heart", "♥"),
				new StaticPlaceholder("newline", "\n"),
				new StaticPlaceholder("null", "")
		}).collect(Collectors.toMap(GlobalPlaceholder::getName, Function.identity())));
	}
}
