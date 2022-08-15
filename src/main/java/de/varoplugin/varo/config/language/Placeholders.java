/* 
 * VaroPlugin
 * Copyright (C) 2022 Cuuky, Almighty-Satan
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/

package de.varoplugin.varo.config.language;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import de.varoplugin.varo.VaroPlugin;
import de.varoplugin.varo.config.language.placeholder.GlobalPlaceholder;
import de.varoplugin.varo.config.language.placeholder.StaticPlaceholder;

public class Placeholders {

	public static Map<String, GlobalPlaceholder> getPlaceholders(VaroPlugin varo) {
		return Collections.unmodifiableMap(Arrays.stream(new GlobalPlaceholder[] {
				new StaticPlaceholder("pluginname", varo.getDescription().getName()),
				new StaticPlaceholder("pluginversion", varo.getDescription().getName()),
				new StaticPlaceholder("plugin", varo.getDescription().getName()),
				new StaticPlaceholder("website", varo.getWebsite()),
				new StaticPlaceholder("github", varo.getGithub()),
				new StaticPlaceholder("plugindiscord", varo.getDiscordInvite()),
				new StaticPlaceholder("heart", "♥"),
				new StaticPlaceholder("newline", "\n"),
				new StaticPlaceholder("null", ""),
				new StaticPlaceholder("projectname", varo.getVaroConfig().projectname.getValue()),
				new StaticPlaceholder("discord", varo.getVaroConfig().bot_discord_invite.getValue())
		}).collect(Collectors.toMap(GlobalPlaceholder::getName, Function.identity())));
	}
}
