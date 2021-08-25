package de.cuuky.varo.gui;

import de.cuuky.cfw.inventory.AdvancedInventory;
import de.cuuky.cfw.inventory.Info;
import de.cuuky.cfw.inventory.InfoProvider;
import de.cuuky.cfw.inventory.ItemInserter;
import de.cuuky.cfw.inventory.inserter.AnimatedClosingInserter;
import de.cuuky.cfw.inventory.inserter.DirectInserter;
import de.cuuky.cfw.item.ItemBuilder;
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

    VaroInventoryConfigProvider(AdvancedInventory inventory) {
        this.inventory = inventory;
        this.player = VaroPlayer.getPlayer(this.inventory.getPlayer());
    }

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public List<Info<?>> getProvidedInfos() {
        return Arrays.asList(Info.ITEM_INSERTER, Info.FILLER_STACK, Info.PLAY_SOUND);
    }

    @Override
    public ItemInserter getInserter() {
        return this.player.hasGuiAnimation() ? new AnimatedClosingInserter() : new DirectInserter();
    }

    @Override
    public ItemStack getFillerStack() {
        VaroMenuColor color = this.player.getGuiFiller();
        return color != null ? new ItemBuilder().itemstack(color.getColorPane()).displayname("§f").build() : null;
    }

    @Override
    public Consumer<Player> getSoundPlayer() {
        return this.player.getGuiSound() != null ? (player) ->
                player.playSound(player.getLocation(), this.player.getGuiSound(), 1f, 1f) : null;
    }
}