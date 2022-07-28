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
import de.varoplugin.varo.api.task.trigger.TriggerBuilder;
import de.varoplugin.varo.config.VaroConfig;
import de.varoplugin.varo.game.State;
import de.varoplugin.varo.game.entity.player.OnlineState;
import de.varoplugin.varo.game.entity.player.ParticipantState;
import de.varoplugin.varo.game.entity.player.PlayerMode;

import java.util.function.Predicate;

public interface IVaroPlayerTriggerBuilder extends IVaroTriggerBuilder {

    IVaroPlayerTriggerBuilder whenPState(Predicate<ParticipantState> state);

    IVaroPlayerTriggerBuilder whenMode(Predicate<PlayerMode> mode);

    IVaroPlayerTriggerBuilder whenOnline(Predicate<OnlineState> online);

    IVaroPlayerTriggerBuilder andOnline(Predicate<OnlineState> online);

    IVaroPlayerTriggerBuilder andPState(Predicate<ParticipantState> alive);

    IVaroPlayerTriggerBuilder whenState(Predicate<State> allowed);

    Trigger build();

    Trigger complete();

    IVaroPlayerTriggerBuilder when(Trigger trigger);

    IVaroPlayerTriggerBuilder when(TriggerBuilder when);

    IVaroPlayerTriggerBuilder and(VaroConfig.VaroBoolConfigEntry entry);

    IVaroPlayerTriggerBuilder and(Trigger triggers);

    IVaroPlayerTriggerBuilder and(TriggerBuilder and);

    IVaroPlayerTriggerBuilder down();

    IVaroPlayerTriggerBuilder ground();
}
