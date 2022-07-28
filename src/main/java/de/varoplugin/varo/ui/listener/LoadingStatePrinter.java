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

import de.varoplugin.varo.StartupState;
import de.varoplugin.varo.VaroLoadingState;
import de.varoplugin.varo.VaroPlugin;
import de.varoplugin.varo.ui.VaroLoadingStatePrinter;
import de.varoplugin.varo.util.ZipFileUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Level;

public class LoadingStatePrinter extends UiListener implements VaroLoadingStatePrinter {

    private static final String BANNER_LOCATION = "banner.txt";
    private static final String FORMAT = "%s";

    private String[] banner;

    @Override
    public void register(VaroPlugin plugin) {
        super.register(plugin);
        try {
            this.banner = Objects.requireNonNull(ZipFileUtils.readFileFromZip(plugin.getFile(), BANNER_LOCATION)).split("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printBanner() {
        Arrays.stream(this.banner).forEach(line -> this.getLogger().log(Level.INFO, line));
    }

    public void onLoadingStateUpdate(VaroLoadingState state, Object... format) {
        if (state.equals(StartupState.values()[0])) this.printBanner();
        if (!state.hasMessage()) return;
        this.getLogger().log(Level.INFO, String.format(FORMAT, state.formatMessage(format)));
    }
}