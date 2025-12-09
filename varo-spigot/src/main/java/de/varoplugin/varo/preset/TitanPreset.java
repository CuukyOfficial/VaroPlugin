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

import java.math.BigDecimal;

class TitanPreset extends BuiltInPreset {

    TitanPreset() {
        super("Titan");
    }

    @Override
    public void loadConfig() {
        ConfigSetting.PREFIX.setValue("§7[§3Titan§7] ", true);
        ConfigSetting.PROJECT_NAME.setValue("Titan", true);

        ConfigSetting.BLOODLUST_DAYS.setValue(3, true);
        ConfigSetting.NO_ACTIVITY_DAYS.setValue(3, true);
        ConfigSetting.POST_COORDS_DAYS.setValue(3, true);
        
        ConfigSetting.BACKPACK_TEAM_ENABLED.setValue(true, true);
        ConfigSetting.BACKPACK_TEAM_SIZE.setValue(9, true);
        
        ConfigSetting.BORDER_TIME_DAY_DECREASE.setValue(false, true);
        
        ConfigSetting.TEAM_LIVES.setValue(BigDecimal.valueOf(3), true);
        
        ConfigSetting.STARTPERIOD_PROTECTIONTIME.setValue(120, true);
    }
}
