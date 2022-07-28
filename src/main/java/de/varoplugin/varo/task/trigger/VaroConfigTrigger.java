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

package de.varoplugin.varo.task.trigger;

import de.varoplugin.varo.api.task.trigger.Trigger;
import de.varoplugin.varo.config.VaroConfig;
import de.varoplugin.varo.game.Varo;

public class VaroConfigTrigger extends GameTrigger {

    private VaroConfig.VaroBoolConfigEntry entry;

    private VaroConfigTrigger(Varo varo, VaroConfig.VaroBoolConfigEntry entry, boolean match) {
        super(varo, match);
        this.entry = entry;
    }

    public VaroConfigTrigger(Varo varo, VaroConfig.VaroBoolConfigEntry entry) {
        this(varo, entry, true);
    }

    @Override
    protected boolean isTriggered() {
        return this.entry.getValue();
    }

    // TODO: Varo config entry change event

    @Override
    public Trigger clone() {
        VaroConfigTrigger trigger = (VaroConfigTrigger) super.clone();
        trigger.entry = this.entry;
        return trigger;
    }
}
