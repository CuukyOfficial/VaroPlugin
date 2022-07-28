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

import de.varoplugin.varo.config.language.Language;
import de.varoplugin.varo.config.language.component.CompositeMessageComponent;
import de.varoplugin.varo.config.language.component.MessageComponent;
import de.varoplugin.varo.config.language.placeholder.GlobalPlaceholder;

public class TranslatableMessageArray extends GenericTranslatable<MessageComponent[]> {

	private MessageComponent[][] translations;
	private int defaultTranslation;

	public TranslatableMessageArray(String path, String... localPlaceholderNames) {
		super(path, localPlaceholderNames);
	}

	@Override
	public void init(Language[] languages, int defaultTranslation, Map<String, GlobalPlaceholder> globalPlaceholders, boolean placeholderApiSupport) {
		this.defaultTranslation = defaultTranslation;
		this.translations = Arrays.stream(languages)
				.map(language -> (String[]) language.getTranslation(this.getPath()).value())
				.map(translations -> Arrays.stream(translations).map(translation -> new CompositeMessageComponent(translation, this.getPlaceholderNames(), globalPlaceholders, placeholderApiSupport)).toArray(CompositeMessageComponent[]::new))
				.toArray(CompositeMessageComponent[][]::new);
	}

	@Override
	public MessageComponent[] value() {
		return this.translations[this.defaultTranslation];
	}

	@Override
	public MessageComponent[] value(Language language) {
		return this.translations[language.getId()];
	}
}
