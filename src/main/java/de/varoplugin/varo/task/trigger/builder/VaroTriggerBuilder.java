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

package de.varoplugin.varo.task.trigger.builder;

import de.varoplugin.varo.api.task.trigger.Trigger;
import de.varoplugin.varo.api.task.trigger.LayeredTriggerBuilder;
import de.varoplugin.varo.api.task.trigger.TriggerBuilder;
import de.varoplugin.varo.config.VaroConfig;
import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.State;
import de.varoplugin.varo.task.trigger.GameStateTrigger;
import de.varoplugin.varo.task.trigger.VaroConfigTrigger;

import java.util.function.Predicate;

public class VaroTriggerBuilder implements IVaroTriggerBuilder {

    private final TriggerBuilder internal;
    private final Varo varo;

    public VaroTriggerBuilder(Varo varo) {
        this.internal = new LayeredTriggerBuilder(varo.getPlugin());
        this.varo = varo;
    }

    private void whenState(State state) {
        this.internal.when(new GameStateTrigger(this.varo, state));
    }

    @Override
    public IVaroTriggerBuilder whenState(Predicate<State> allowed) {
        this.varo.getStates().filter(allowed).forEach(this::whenState);
        return this;
    }

    @Override
    public Trigger build() {
        return this.internal.build();
    }

    @Override
    public Trigger complete() {
        return this.internal.complete();
    }

    @Override
    public IVaroTriggerBuilder when(Trigger trigger) {
        this.internal.when(trigger);
        return this;
    }

    @Override
    public IVaroTriggerBuilder when(TriggerBuilder when) {
        this.internal.when(when);
        return this;
    }

    @Override
    public IVaroTriggerBuilder and(VaroConfig.VaroBoolConfigEntry entry) {
        this.internal.and(new VaroConfigTrigger(this.varo, entry));
        return this;
    }

    @Override
    public IVaroTriggerBuilder and(Trigger triggers) {
        this.internal.and(triggers);
        return this;
    }

    @Override
    public IVaroTriggerBuilder and(TriggerBuilder and) {
        this.internal.and(and);
        return this;
    }

    @Override
    public IVaroTriggerBuilder down() {
        this.internal.down();
        return this;
    }

    @Override
    public IVaroTriggerBuilder ground() {
        this.internal.ground();
        return this;
    }
}
