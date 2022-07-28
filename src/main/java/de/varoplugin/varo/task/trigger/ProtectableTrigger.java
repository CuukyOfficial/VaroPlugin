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

import de.varoplugin.varo.api.event.game.world.protectable.ProtectableRemoveEvent;
import de.varoplugin.varo.api.task.trigger.Trigger;
import de.varoplugin.varo.game.world.protectable.Protectable;
import org.bukkit.event.EventHandler;

public class ProtectableTrigger extends GameTrigger {

    private Protectable protectable;

    private ProtectableTrigger(Protectable protectable, boolean match) {
        super(protectable.getVaro(), match);
        this.protectable = protectable;
    }

    public ProtectableTrigger(Protectable protectable) {
        this(protectable, true);
    }

    @EventHandler
    public void onProtectableRemove(ProtectableRemoveEvent event) {
        if (event.getProtectable().equals(this.protectable)) this.destroy();
    }

    @Override
    protected boolean isTriggered() {
        return true;
    }

    @Override
    public Trigger clone() {
        ProtectableTrigger trigger = (ProtectableTrigger) super.clone();
        trigger.protectable = protectable;
        return trigger;
    }
}
