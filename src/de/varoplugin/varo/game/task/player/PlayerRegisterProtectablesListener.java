package de.varoplugin.varo.game.task.player;

import de.cuuky.cfw.version.VersionUtils;
import de.varoplugin.varo.game.entity.player.Player;
import de.varoplugin.varo.game.world.protectable.BlockProtectable;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.SignChangeEvent;

public class PlayerRegisterProtectablesListener extends AbstractPlayerListener {

    public PlayerRegisterProtectablesListener(Player player) {
        super(player);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onSignChange(SignChangeEvent event) {
        BlockFace attachedFace = VersionUtils.getVersionAdapter().getSignAttachedFace(event.getBlock());

        if(attachedFace == null)
            throw new Error("attachedFace should not be null");

        Block attached = event.getBlock().getRelative(attachedFace);
        this.getPlayer().addProtectable(new BlockProtectable(this.getPlayer(), attached));
    }
}
