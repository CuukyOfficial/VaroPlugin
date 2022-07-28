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

package de.varoplugin.varo.game.strike;

import de.varoplugin.varo.api.event.game.strike.StrikeExecuteEvent;
import de.varoplugin.varo.api.event.game.strike.StrikeInitializedEvent;
import de.varoplugin.varo.game.UniqueGameObject;
import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.entity.player.VaroPlayer;

import java.util.UUID;

final class StrikeImpl extends UniqueGameObject implements Strike {

    private final StrikeType type;
    private VaroPlayer target;
    private boolean executed;

    StrikeImpl(UUID uuid, StrikeType type) {
        super(uuid);
        this.type = type;
    }

    @Override
    public void initialize(Varo varo) {
        super.initialize(varo);
        varo.getPlugin().callEvent(new StrikeInitializedEvent(this));
    }

    @Override
    public void setTarget(VaroPlayer target) {
        this.target = target;
    }

    @Override
    public VaroPlayer getTarget() {
        return this.target;
    }

    @Override
    public StrikeType getType() {
        return this.type;
    }

    @Override
    public void execute() {
        if (this.executed || !this.getVaro().getPlugin().isCancelled(new StrikeExecuteEvent(this))) return;
        this.executed = true;
    }

    @Override
    public boolean wasExecuted() {
        return this.executed;
    }
}
