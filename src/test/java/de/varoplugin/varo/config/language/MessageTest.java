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

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.varoplugin.varo.config.language.translatable.Translatable;

public class MessageTest {

	private static AbstractLanguage[] languages;
	private static Messages messages;

	@BeforeAll
	public static void init() {
		languages = new AbstractLanguage[] { new EnglishLanguage(0) };
		messages = new Messages(languages, 0, new HashMap<>(), false);
	}

	@Test
	public void testMissingTranslations() {
		for (Translatable<?> message : messages.getMessages())
			for (Language language : languages)
				assertNotNull(language.getTranslation(message.getPath()));
	}
	
	@Test
	public void testDuplicatePaths() {
		Set<String> paths = new HashSet<>();
		for (Translatable<?> message : messages.getMessages()) {
			assertFalse(paths.contains(message.getPath()));
			paths.add(message.getPath());
		}
	}
}
