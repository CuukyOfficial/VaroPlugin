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

import java.util.Map;

import de.varoplugin.varo.config.language.Language;
import de.varoplugin.varo.config.language.placeholder.GlobalPlaceholder;

public abstract class GenericTranslatable<T, U> implements Translatable<T> {

	private final String path;
	private final String[] localPlaceholderNames;
	protected U[] translations;
	private int defaultTranslation;

	public GenericTranslatable(String path, String... localPlaceholderNames) {
		this.path = path;
		this.localPlaceholderNames = localPlaceholderNames;
	}

	@Override
	public void init(Language[] languages, int defaultTranslation, Map<String, GlobalPlaceholder> globalPlaceholders, boolean placeholderApiSupport) {
		this.defaultTranslation = defaultTranslation;
	}

	@Override
	public String getPath() {
		return this.path;
	}

	@Override
	public String[] getPlaceholderNames() {
		return this.localPlaceholderNames;
	}
	
	protected int getDefaultTranslation() {
		return this.defaultTranslation;
	}
}
