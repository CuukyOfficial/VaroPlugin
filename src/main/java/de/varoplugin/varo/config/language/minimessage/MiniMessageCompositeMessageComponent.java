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

package de.varoplugin.varo.config.language.minimessage;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;

import de.varoplugin.varo.config.language.component.MessageComponent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class MiniMessageCompositeMessageComponent implements MiniMessageMessageComponent {

	private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

	private final MessageComponent[] components;

	public MiniMessageCompositeMessageComponent(MessageComponent[] components) {
		this.components = components;
	}

	@Override
	public Component value() {
		return MINI_MESSAGE.deserialize(Arrays.stream(this.components)
				.map(component -> component.shouldEscape() ? MINI_MESSAGE.escapeTags(component.value()) : component.value()).collect(Collectors.joining()));
	}

	@Override
	public Component value(Object... localPlaceholders) {
		return MINI_MESSAGE.deserialize(Arrays.stream(this.components)
				.map(component -> component.shouldEscape() ? MINI_MESSAGE.escapeTags(component.value(localPlaceholders)) : component.value(localPlaceholders)).collect(Collectors.joining()));
	}

	@Override
	public Component value(Player player, Object... localPlaceholders) {
		return MINI_MESSAGE.deserialize(Arrays.stream(this.components)
				.map(component -> component.shouldEscape() ? MINI_MESSAGE.escapeTags(component.value(player, localPlaceholders)) : component.value(player, localPlaceholders)).collect(Collectors.joining()));
	}
}
