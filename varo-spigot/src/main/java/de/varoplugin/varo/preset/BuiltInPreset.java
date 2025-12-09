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

package de.varoplugin.varo.preset;

import de.varoplugin.varo.config.VaroConfig;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

abstract class BuiltInPreset extends AbstractPreset {

    BuiltInPreset(@NotNull String name) {
        super(name);
    }

    protected abstract void loadConfig() throws IOException;
    
    @Override
    public final void load() throws IOException {
        for (ConfigSetting entry : ConfigSetting.values())
            entry.setValue(entry.getDefaultValue());
        VaroConfig.reset();
        
        this.loadConfig();

        VaroConfig.write();
    }
}
