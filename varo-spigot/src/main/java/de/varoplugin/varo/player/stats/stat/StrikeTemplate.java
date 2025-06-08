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

package de.varoplugin.varo.player.stats.stat;

import de.varoplugin.varo.serialize.identifier.VaroSerializeable;
import io.github.almightysatan.jaskl.annotation.Description;
import io.github.almightysatan.jaskl.annotation.Entry;

import java.util.Arrays;
import java.util.List;

public class StrikeTemplate implements VaroSerializeable {

    @Entry
    @Description("If true the player's coordinates will be posted.")
    public boolean postCoordinates;

    @Entry
    @Description("If true the player's inventory will be cleared.")
    public boolean clearInventory;

    @Entry
    @Description("If true the player will be eliminated from the project.")
    public boolean eliminate;

    @Entry
    @Description("The amount of hours the player should be banned after receiving this strike. Ignored if the value is less than or equal to zero.")
    public int banHours = -1;

    public StrikeTemplate() {}

    public StrikeTemplate(boolean postCoordinates, boolean clearInventory, boolean eliminate, int banHours) {
        this.postCoordinates = postCoordinates;
        this.clearInventory = clearInventory;
        this.eliminate = eliminate;
        this.banHours = banHours;
    }

    public boolean isPostCoordinates() {
        return this.postCoordinates;
    }

    public boolean isClearInventory() {
        return this.clearInventory;
    }

    public boolean isEliminate() {
        return this.eliminate;
    }

    public int getBanHours() {
        return this.banHours;
    }

    public static List<StrikeTemplate> getDefaultStrikeTemplates() {
        return Arrays.asList(
                new StrikeTemplate(true, false, false, -1),
                new StrikeTemplate(false, true, false, -1),
                new StrikeTemplate(false, false, true, -1)
        );
    }

    @Override
    public void onDeserializeEnd() {
        // nop
    }

    @Override
    public void onSerializeStart() {
        // nop
    }
}
