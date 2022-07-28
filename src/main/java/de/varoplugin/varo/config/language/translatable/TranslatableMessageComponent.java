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

package de.varoplugin.varo.config.language.translatable;

import org.bukkit.entity.Player;

import de.varoplugin.varo.config.language.Language;
import de.varoplugin.varo.config.language.LanguageContainer;
import de.varoplugin.varo.config.language.component.MessageComponent;
import de.varoplugin.varo.config.language.minimessage.MiniMessageTranslatableMessageComponent;

public interface TranslatableMessageComponent extends Translatable<String>, MessageComponent {

	String value(Language language, Object... localPlaceholders);

	String value(Language language, Player player,  Object... localPlaceholders);

	default String value(LanguageContainer languageContainer, Object... localPlaceholders) {
		return this.value(languageContainer.getLanguage(), localPlaceholders);
	}

	default String value(LanguageContainer languageContainer, Player player, Object... localPlaceholders) {
		return this.value(languageContainer.getLanguage(), player, localPlaceholders);
	}

	MiniMessageTranslatableMessageComponent miniMessage();
}
