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

import java.util.Arrays;
import java.util.Map;

import org.bukkit.entity.Player;

import de.varoplugin.varo.config.language.Language;
import de.varoplugin.varo.config.language.component.CompositeMessageComponent;
import de.varoplugin.varo.config.language.minimessage.MiniMessageTranslatableMessageComponent;
import de.varoplugin.varo.config.language.minimessage.MiniMessageTranslatableMessageComponentImpl;
import de.varoplugin.varo.config.language.placeholder.GlobalPlaceholder;

public class Message extends GenericTranslatable<String, CompositeMessageComponent> implements TranslatableMessageComponent {

	private MiniMessageTranslatableMessageComponent miniComponent;

	public Message(String path, String... localPlaceholderNames) {
		super(path, localPlaceholderNames);
	}

	@Override
	public void init(Language[] languages, int defaultTranslation, Map<String, GlobalPlaceholder> globalPlaceholders, boolean placeholderApiSupport) {
		super.init(languages, defaultTranslation, globalPlaceholders, placeholderApiSupport);
		this.translations = Arrays.stream(languages)
				.map(language -> this.createCompositeMessageComponent((String) language.getTranslation(this.getPath()).value(), this.getPlaceholderNames(), globalPlaceholders, placeholderApiSupport))
				.toArray(CompositeMessageComponent[]::new);
	}
	
	protected CompositeMessageComponent createCompositeMessageComponent(String translation, String[] localPlaceholders, Map<String, GlobalPlaceholder> globalPlaceholders, boolean placeholderApiSupport) {
		return new CompositeMessageComponent(translation, localPlaceholders, globalPlaceholders, placeholderApiSupport);
	}

	@Override
	public String value() {
		return this.translations[this.getDefaultTranslation()].value();
	}

	@Override
	public String value(Object... placeholders) {
		return this.translations[this.getDefaultTranslation()].value(placeholders);
	}

	@Override
	public String value(Player player, Object... localPlaceholders) {
		return this.translations[this.getDefaultTranslation()].value(player, localPlaceholders);
	}

	@Override
	public String value(Language language) {
		return this.translations[language.getId()].value();
	}

	@Override
	public String value(Language language, Object... localPlaceholders) {
		return this.translations[language.getId()].value(localPlaceholders);
	}

	@Override
	public String value(Language language, Player player,  Object... localPlaceholders) {
		return this.translations[language.getId()].value(player, localPlaceholders);
	}

	@Override
	public boolean shouldEscape() {
		return false;
	}

	@Override
	public MiniMessageTranslatableMessageComponent miniMessage() {
		return this.miniComponent == null ? this.miniComponent = new MiniMessageTranslatableMessageComponentImpl(this.translations, this.getDefaultTranslation()) : this.miniComponent;
	}
}
