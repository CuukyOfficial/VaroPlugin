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

package de.varoplugin.varo.ui.listener;

import de.varoplugin.varo.api.event.game.VaroInitializedEvent;
import de.varoplugin.varo.api.event.game.player.PlayerInitializedEvent;
import de.varoplugin.varo.game.State;
import de.varoplugin.varo.task.trigger.builder.VaroTriggerBuilder;
import de.varoplugin.varo.ui.tasks.StartingUiTask;
import org.bukkit.event.EventHandler;

public class DefaultUiTasks extends UiListener {

    @EventHandler
    public void onGameInitialize(VaroInitializedEvent event) {
        new VaroTriggerBuilder(event.getVaro()).whenState(State::hasStartCountdown).complete().register(new StartingUiTask(event.getVaro()));
    }

    @EventHandler
    public void onPlayerInitialize(PlayerInitializedEvent event) {
//        new VaroPlayerTriggerBuilder(event.getPlayer()).when(VaroState.RUNNING).when(VaroState.MASS_RECORDING)
//                .and(VaroParticipantState.ALIVE).and(true).complete().register(new PlayerShowCountdownTask(event.getPlayer()));
    }
}
