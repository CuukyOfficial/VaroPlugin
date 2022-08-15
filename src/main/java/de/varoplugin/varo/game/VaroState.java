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

package de.varoplugin.varo.game;

public enum VaroState implements State {

    LOBBY(100, true, true, false, false),
    STARTING(200, false, false, true, false),
    RUNNING(300, false, false, false, true),
    MASS_RECORDING(400, false, false, false, true),
    FINISHED(500, true, false, false, false);

    private final int priority;
    private final boolean allowsUnregistered;
    private final boolean lobby;
    private final boolean starting;
    private final boolean running;

    VaroState(int priority, boolean allowsUnregistered, boolean lobby, boolean starting, boolean running) {
        this.priority = priority;
        this.allowsUnregistered = allowsUnregistered;
        this.lobby = lobby;
        this.starting = starting;
        this.running = running;
    }

    @Override
    public boolean allowsNonRegistered() {
        return this.allowsUnregistered;
    }

    @Override
    public boolean allowsStart() {
        return this.lobby;
    }

    @Override
    public boolean allowsPlayerMovement() {
        return !this.lobby;
    }

    @Override
    public boolean hasStartCountdown() {
        return this.starting;
    }

    @Override
    public boolean canFillChests() {
        return this.running;
    }

    @Override
    public boolean canFinish() {
        return this.running;
    }

    @Override
    public boolean canDoRandomTeam() {
        return this.starting;
    }

    @Override
    public boolean blocksProtectableAccess() {
        return this.running;
    }

    @Override
    public boolean doesPlayerCountdown() {
        return this.running;
    }

    @Override
    public int getPriority() {
        return this.priority;
    }
}