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

class SuroPreset extends BuiltInPreset {

    SuroPreset() {
        super("Suro");
    }

    @Override
    public void loadConfig() {
        ConfigSetting.BORDER_DEATH_DECREASE.setValue(false, true);
        ConfigSetting.BORDER_TIME_DAY_DECREASE.setValue(false, true);

        ConfigSetting.STRIKE_ON_COMBATLOG.setValue(false, true);
        
        ConfigSetting.DEATH_SOUND_ENABLED.setValue(true, true);
        
        ConfigSetting.DISCONNECT_PER_SESSION.setValue(-1, true);

        ConfigSetting.PREFIX.setValue("§7[§3Suro§7] ", true);
        ConfigSetting.PROJECT_NAME.setValue("Suro", true);
        
        ConfigSetting.OFFLINEVILLAGER.setValue(true, true);

        ConfigSetting.NO_SATIATION_REGENERATE.setValue(true, true);
        ConfigSetting.PLAYER_CHEST_LIMIT.setValue(0, true);
        ConfigSetting.PLAYER_FURNACE_LIMIT.setValue(0, true);
        
        ConfigSetting.JOIN_PROTECTIONTIME.setValue(-1, true);
    }
}
