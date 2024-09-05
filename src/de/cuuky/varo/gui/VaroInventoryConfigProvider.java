package de.cuuky.varo.gui;

import de.varoplugin.cfw.inventory.*;
import de.varoplugin.cfw.inventory.inserter.AnimatedClosingInserter;
import de.varoplugin.cfw.inventory.inserter.DirectInserter;
import de.cuuky.cfw.utils.item.BuildItem;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.gui.settings.VaroMenuColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class VaroInventoryConfigProvider implements InfoProvider {

    private final AdvancedInventory inventory;
    private final VaroPlayer player;

    private final List<PrioritisedInfo> infos = Arrays.asList(new PrioritisedInfo(() -> 1, Info.ITEM_INSERTER),
            new PrioritisedInfo(() -> 1, Info.FILLER_STACK), new PrioritisedInfo(() -> 1, Info.PLAY_SOUND));

    VaroInventoryConfigProvider(AdvancedInventory inventory) {
        this.inventory = inventory;
        this.player = VaroPlayer.getPlayer(this.inventory.getPlayer());
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public List<Info<?>> getProvidedInfos() {
        return null;
    }

    @Override
    public List<PrioritisedInfo> getPrioritisedInfos() {
        return this.infos;
    }

    @Override
    public ItemInserter getInserter() {
        return this.player.hasGuiAnimation() ? new AnimatedClosingInserter() : new DirectInserter();
    }

    @Override
    public ItemStack getFillerStack() {
        VaroMenuColor color = this.player.getGuiFiller();
        return color != null ? new BuildItem().itemstack(color.getColorPane()).displayName("§f").build() : null;
    }

    @Override
    public Consumer<Player> getSoundPlayer() {
        return this.player.getGuiSound() != null ? (player) ->
                player.playSound(player.getLocation(), this.player.getGuiSound(), 1f, 1f) : null;
    }
}