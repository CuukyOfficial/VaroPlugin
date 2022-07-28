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

public class SessionImpl implements Session {

    private final Calendar start;
    private final Calendar end;

    SessionImpl(Calendar start, Calendar end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public Calendar getStart() {
        return this.start;
    }

    @Override
    public Calendar getFinish() {
        return this.end;
    }
}
