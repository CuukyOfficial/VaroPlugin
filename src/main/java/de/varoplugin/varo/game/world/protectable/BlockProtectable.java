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

package de.varoplugin.varo.game.world.protectable;

import de.varoplugin.varo.api.event.game.world.protectable.ProtectableInitializedEvent;
import de.varoplugin.varo.game.UniqueGameObject;
import de.varoplugin.varo.game.Varo;
import org.bukkit.block.Block;

import java.util.UUID;

final class BlockProtectable extends UniqueGameObject implements Protectable {

    private final Block block;
    private ProtectableOwner owner;

    BlockProtectable(UUID uuid, Block block) {
        super(uuid);
        this.block = block;
    }

    @Override
    public void initialize(Varo varo) {
        super.initialize(varo);
        varo.getPlugin().callEvent(new ProtectableInitializedEvent(this));
    }

    @Override
    public int hashCode() {
        return this.block.getLocation().hashCode();
    }

    @Override
    public Block getBlock() {
        return this.block;
    }

    @Override
    public void setOwner(ProtectableOwner owner) {
        this.owner = owner;
    }

    @Override
    public ProtectableOwner getOwner() {
        return this.owner;
    }
}