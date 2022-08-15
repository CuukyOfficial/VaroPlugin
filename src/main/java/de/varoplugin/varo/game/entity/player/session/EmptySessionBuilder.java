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

package de.varoplugin.varo.game.entity.player.session;

import java.util.Calendar;
import java.util.Objects;

public class EmptySessionBuilder implements SessionBuilder {

    private Calendar start;

    @Override
    public SessionBuilder start(Calendar calendar) {
        this.start = Objects.requireNonNull(calendar);
        return this;
    }

    @Override
    public Session build() {
        if (this.start == null) throw new IllegalArgumentException("Start may not be null");
        return new SessionImpl(this.start, null);
    }
}
