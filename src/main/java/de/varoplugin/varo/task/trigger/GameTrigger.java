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

import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.api.task.trigger.ParentTrigger;
import de.varoplugin.varo.api.task.trigger.Trigger;

public abstract class GameTrigger extends ParentTrigger {

    private Varo varo;

    public GameTrigger(Varo varo, boolean match) {
        super(varo.getPlugin(), match);
        this.varo = varo;
    }

    public Varo getVaro() {
        return this.varo;
    }

    @Override
    public Trigger clone() {
        GameTrigger trigger = (GameTrigger) super.clone();
        trigger.varo = this.varo;
        return trigger;
    }
}
