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

import org.bukkit.entity.Player;

import de.varoplugin.varo.config.language.Language;
import de.varoplugin.varo.config.language.component.CompositeMessageComponent;
import net.kyori.adventure.text.Component;

public class MiniMessageTranslatableMessageComponentImpl implements MiniMessageTranslatableMessageComponent {

	private final CompositeMessageComponent[] translations;
	private final int defaultTranslation;

	public MiniMessageTranslatableMessageComponentImpl(CompositeMessageComponent[] translations, int defaultTranslation) {
		this.translations = translations;
		this.defaultTranslation = defaultTranslation;
	}

	@Override
	public Component value() {
		return this.translations[this.defaultTranslation].miniMessage().value();
	}

	@Override
	public Component value(Object... placeholders) {
		return this.translations[this.defaultTranslation].miniMessage().value(placeholders);
	}

	@Override
	public Component value(Player player, Object... localPlaceholders) {
		return this.translations[this.defaultTranslation].miniMessage().value(player, localPlaceholders);
	}

	@Override
	public Component value(Language language, Object... localPlaceholders) {
		return this.translations[language.getId()].miniMessage().value(localPlaceholders);
	}

	@Override
	public Component value(Language language, Player player,  Object... localPlaceholders) {
		return this.translations[language.getId()].miniMessage().value(player, localPlaceholders);
	}
}
