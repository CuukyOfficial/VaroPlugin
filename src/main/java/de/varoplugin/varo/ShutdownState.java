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

package de.varoplugin.varo;

public enum ShutdownState implements VaroLoadingState {

    INITIALIZING("INIT", null),
    STOPPING_BOTS("BOTS", "Shutting down %s bots..."),
    SAVING_STATS("STATS", "Saving stats of %s players..."),
    SUCCESS("FINISHED", "Saved all data");

    private final String name;
    private final String message;

    ShutdownState(String name, String message) {
        this.name = name;
        this.message = message;
    }

    @Override
    public boolean hasMessage() {
        return this.message != null;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String formatMessage(Object... args) {
        return String.format(this.message, args);
    }

}