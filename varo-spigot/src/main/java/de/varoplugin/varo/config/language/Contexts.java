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

package de.varoplugin.varo.config.language;

import de.varoplugin.varo.player.VaroPlayer;
import de.varoplugin.varo.team.VaroTeam;
import org.bukkit.entity.Player;

public interface Contexts {

    class PlayerContext {
        private final VaroPlayer player;

        public PlayerContext(VaroPlayer player) {
            this.player = player;
        }

        public VaroPlayer getPlayer() {
            return this.player;
        }

        public OnlinePlayerContext toOnlinePlayerContext() {
            Player player = this.player.getPlayer();
            return player == null ? null : new OnlinePlayerContext(player);
        }
        
        public TeamContext toTeamContext() {
            VaroTeam team = this.player.getTeam();
            return team == null ? null : new TeamContext(team);
        }
    }

    class OnlinePlayerContext {
        private final Player player;

        OnlinePlayerContext(Player player) {
            this.player = player;
        }

        public Player getPlayer() {
            return this.player;
        }
    }

    class DeathContext extends PlayerContext {
        private final String reason;

        public DeathContext(VaroPlayer player, String reason) {
            super(player);
            this.reason = reason;
        }

        public String getReason() {
            return this.reason;
        }
    }

    class KillContext extends DeathContext {
        private final VaroPlayer killer;

        public KillContext(VaroPlayer player, VaroPlayer killer, String reason) {
            super(player, reason);
            this.killer = killer;
        }

        public VaroPlayer getKiller() {
            return this.killer;
        }
    }

    class StrikeContext extends PlayerContext {
        private final String reason;
        private final String operator;
        private final int num;

        public StrikeContext(VaroPlayer player, String reason, String operator, int num) {
            super(player);
            this.reason = reason;
            this.operator = operator;
            this.num = num;
        }

        public String getReason() {
            return this.reason;
        }

        public String getOperator() {
            return this.operator;
        }

        public String getNum() {
            return String.valueOf(this.num);
        }
    }

    class ContainerContext extends PlayerContext {
        private final VaroPlayer owner;

        public ContainerContext(VaroPlayer player, VaroPlayer owner) {
            super(player);
            this.owner = owner;
        }

        public VaroPlayer getOwner() {
            return this.owner;
        }
    }
    
    class TeamContext {
        private final VaroTeam team;

        TeamContext(VaroTeam team) {
            this.team = team;
        }

        public VaroTeam getTeam() {
            return this.team;
        }
    }

    class BorderDecreaseContext {
        private final int size;
        private final double speed;
        private final int time;

        public BorderDecreaseContext(int size, double speed, int time) {
            this.size = size;
            this.speed = speed;
            this.time = time;
        }

        public String getSize() {
            return String.valueOf(this.size);
        }

        public String getSpeed() {
            return String.valueOf(this.speed);
        }

        public String getTime() {
            return String.valueOf(this.time);
        }
    }
}
