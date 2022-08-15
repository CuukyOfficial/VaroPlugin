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

import de.varoplugin.varo.api.event.game.VaroStateChangeEvent;
import de.varoplugin.varo.api.task.trigger.Trigger;
import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.State;
import org.bukkit.event.EventHandler;

public class GameStateTrigger extends GameTrigger {

    private State state;

    private GameStateTrigger(Varo varo, State state, boolean match) {
        super(varo, match);
        this.state = state;
    }

    public GameStateTrigger(Varo varo, State state) {
        this(varo, state, true);
    }

    @Override
    protected boolean isTriggered() {
        return this.state == this.getVaro().getState();
    }

    @EventHandler
    public void onGameStateChange(VaroStateChangeEvent event) {
        if (this.state == null) this.triggerIf(true);
        this.triggerIf(this.state == event.getState());
    }

    @Override
    public Trigger clone() {
        GameStateTrigger trigger = (GameStateTrigger) super.clone();
        trigger.state = state;
        return trigger;
    }
}
