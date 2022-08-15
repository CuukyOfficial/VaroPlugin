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

package de.varoplugin.varo.ui;

import de.varoplugin.varo.VaroLoadingState;
import de.varoplugin.varo.VaroPlugin;
import de.varoplugin.varo.ui.commands.TestCommand;
import de.varoplugin.varo.ui.listener.*;

import java.util.Arrays;
import java.util.Collection;

public class UIManager implements VaroUIManager {

    private final Collection<UiElement> elements;
    private final VaroLoadingStatePrinter loadingStatePrinter;

    public UIManager() {
        this.loadingStatePrinter = new LoadingStatePrinter();
        this.elements = Arrays.asList(this.loadingStatePrinter, new PlayerKickListener(), new DefaultUiTasks(),
                new GameStateChangePrinter(), new PlayerAddProtectablePrinter(), new TeamMemberAddPrinter(), new TestCommand());
    }

    @Override
    public void register(VaroPlugin plugin) {
        this.elements.forEach(element -> element.register(plugin));
    }

    @Override
    public void onLoadingStateUpdate(VaroLoadingState state, Object... format) {
        this.loadingStatePrinter.onLoadingStateUpdate(state, format);
    }
}