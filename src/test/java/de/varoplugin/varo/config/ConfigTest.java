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

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.varoplugin.varo.api.config.ConfigException;

public class ConfigTest {
	
	private static final String PATH = "build/test/mock-config/";
	
	@BeforeEach
	public void deleteConfigs() {
		new MockConfig(PATH).delete();
	}

	@Test
	public void testList() throws ConfigException, IOException {
		MockConfig config = new MockConfig(PATH);
		config.load();

		config = new MockConfig(PATH);
		config.load();
		assertArrayEquals(MockConfig.LIST.toArray(), config.list_entry.getValue().toArray());
	}
}
