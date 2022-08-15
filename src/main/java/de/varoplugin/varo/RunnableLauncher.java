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

import javax.swing.*;

/**
 * For letting the user know if launched as
 * application that this is not an executable program.
 */
public class RunnableLauncher {

    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "You may not execute this plugin as runnable!\n\nNeed help? https://discord.varoplugin.de/\n\nVaro Plugin by Cuuky");
    }
}