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

package de.varoplugin.varo.game.entity.player;

import de.varoplugin.cfw.utils.UUIDUtils;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class EmptyPlayerBuilder implements PlayerBuilder {

    private UUID uuid;
    private String name;
    private Player player;
    private ParticipantState state = VaroParticipantState.ALIVE;
    private PlayerMode mode = VaroPlayerMode.NONE;

    @Override
    public PlayerBuilder uuid(UUID uuid) {
        this.uuid = Objects.requireNonNull(uuid);
        return this;
    }

    @Override
    public PlayerBuilder name(String name) {
        this.name = Objects.requireNonNull(name);
        return this;
    }

    @Override
    public PlayerBuilder player(Player player) {
        this.player = Objects.requireNonNull(player);
        this.name = player.getName();
        this.uuid = player.getUniqueId();
        return this;
    }

    @Override
    public PlayerBuilder state(ParticipantState state) {
        this.state = Objects.requireNonNull(state);
        return this;
    }

    @Override
    public PlayerBuilder mode(PlayerMode mode) {
        this.mode = Objects.requireNonNull(mode);
        return this;
    }

    @Override
    public PlayerBuilder parseMissing() throws ParseException {
        // Who tf just thought it is good API design to just throw Exception :/
        try {
            if (this.uuid == null && this.name != null) this.uuid = UUIDUtils.getUUID(this.name);
        } catch (Exception e) {
            throw new ParseException("Invalid name", e);
        }

        try {
            if (this.uuid != null && this.name == null) this.name = UUIDUtils.getName(this.uuid);
        } catch (Exception e) {
            throw new ParseException("Invalid uuid", e);
        }
        return this;
    }

    @Override
    public VaroPlayer create() {
        if (this.uuid == null || this.name == null) throw new IllegalArgumentException("No name or uuid provided");
        return new VaroPlayerImpl(this.uuid, this.name, this.player, this.state, this.mode);
    }
}
