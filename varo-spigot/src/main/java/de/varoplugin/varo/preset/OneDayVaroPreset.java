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

import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;

class OneDayVaroPreset extends BuiltInPreset {

    OneDayVaroPreset() {
        super("OneDayVaro");
    }

    @Override
    public void loadConfig() {
        ConfigSetting.PLAY_TIME.setValue(-1, true);
        ConfigSetting.PREFIX.setValue("§7[§3ODV§7] ", true);
        ConfigSetting.PROJECT_NAME.setValue("ODV", true);

        ConfigSetting.TEAMREQUEST_ENABLED.setValue(true, true);
    }
}
