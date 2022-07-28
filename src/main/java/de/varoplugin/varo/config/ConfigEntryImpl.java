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

package de.varoplugin.varo.config;

import de.varoplugin.varo.api.config.ConfigCategory;
import de.varoplugin.varo.api.config.ConfigEntry;
import de.varoplugin.varo.api.config.ConfigException;

import java.awt.*;

public class ConfigEntryImpl<T> implements ConfigEntry<T> {

    private static final String DESCRIPTION_DEFAULT_VALUE = "\nDefault value: %s (%s)";

	private final ConfigCategory category;
	private final String path;
	private final T defaultValue;
	private final String description;
	private T value;

	public ConfigEntryImpl(ConfigCategory category, String path, T defaultValue, String description) {
		if (category == null || path == null || defaultValue == null || description == null)
			throw new IllegalArgumentException();

		this.category = category;
		this.path = path;
		this.defaultValue = defaultValue;
		this.description = description + String.format(DESCRIPTION_DEFAULT_VALUE, defaultValue, defaultValue.getClass().getSimpleName());
	}

	private void checkType(Class<?> type) throws ConfigException {
		if (this.defaultValue.getClass() != type && (List.class.isAssignableFrom(this.defaultValue.getClass()) || List.class.isAssignableFrom(type)))
			throw new ConfigException(String.format("Type missmatch! Expected: %s Actual: %s for %s", this.defaultValue.getClass().getName(), type.getName(), this.path));
	}

	@Override
	public ConfigCategory getCategory() {
		return this.category;
	}

	@Override
	public String getPath() {
		return this.path;
	}

	@Override
	public T getDefaultValue() {
		return this.defaultValue;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public T getValue() {
		return this.value;
	}

	@Override
	public void setValue(T value) {
		this.value = value;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setValueAsObject(Object value) throws ConfigException {
		if (value.getClass() == Integer.class && this.defaultValue.getClass() == Long.class)
			value = ((Integer) value).longValue();
		
		this.checkType(value.getClass());
		this.value = (T) value;
	}
}
