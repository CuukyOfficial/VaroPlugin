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

package de.varoplugin.varo.task.player;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.SignChangeEvent;

import de.varoplugin.cfw.version.VersionUtils;
import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.game.world.protectable.EmptyProtectableBuilder;

public class PlayerRegisterProtectablesListener extends AbstractPlayerListener {

    public PlayerRegisterProtectablesListener(VaroPlayer player) {
        super(player);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onSignChange(SignChangeEvent event) {
        BlockFace attachedFace = VersionUtils.getVersionAdapter().getSignAttachedFace(event.getBlock());

        if (attachedFace == null)
            throw new Error("attachedFace should not be null");

        Block attached = event.getBlock().getRelative(attachedFace);
        this.getPlayer().addProtectable(new EmptyProtectableBuilder().block(attached).create());
    }
}
