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

import java.util.Objects;
import java.util.UUID;

public class EmptyStrikeBuilder implements StrikeBuilder {

    private UUID uuid;
    private StrikeType type;

    @Override
    public StrikeBuilder uuid(UUID uuid) {
        this.uuid = Objects.requireNonNull(uuid);
        return this;
    }

    @Override
    public StrikeBuilder type(StrikeType type) {
        this.type = Objects.requireNonNull(type);
        return this;
    }

    @Override
    public Strike create() {
        if (this.type == null) throw new IllegalArgumentException("No StrikeType provided");
        return new StrikeImpl(this.uuid == null ? UUID.randomUUID() : this.uuid, this.type);
    }
}
