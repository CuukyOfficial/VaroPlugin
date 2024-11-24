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

package de.cuuky.varo.config.language;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import de.cuuky.varo.player.VaroPlayer;
import io.github.almightysatan.slams.Context;

interface Contexts {

    class VaroContext implements Context {
        @Override
        public @Nullable String language() {
            return Messages.DEFAULT_LANGUAGE;
        }
    }

    class PlayerContext extends VaroContext {
        private final VaroPlayer player;

        PlayerContext(VaroPlayer player) {
            this.player = player;
        }

        public VaroPlayer getPlayer() {
            return this.player;
        }

        public OnlinePlayerContext toOnlinePlayerContext() {
            Player player = this.player.getPlayer();
            return player == null ? null : new OnlinePlayerContext(player);
        }
    }

    class OnlinePlayerContext extends VaroContext {
        private final Player player;

        OnlinePlayerContext(Player player) {
            this.player = player;
        }

        public Player getPlayer() {
            return this.player;
        }
    }

    class KillerContext extends PlayerContext {
        private final VaroPlayer killer;

        KillerContext(VaroPlayer player, VaroPlayer killer) {
            super(player);
            this.killer = killer;
        }

        public VaroPlayer getKiller() {
            return this.killer;
        }
    }
}
