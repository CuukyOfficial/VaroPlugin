package de.cuuky.varo.gui.player;

import de.cuuky.cfw.hooking.hooks.chat.ChatHook;
import de.cuuky.cfw.hooking.hooks.chat.ChatHookHandler;
import de.cuuky.cfw.inventory.ItemClick;
import de.cuuky.cfw.inventory.confirm.ConfirmInventory;
import de.cuuky.cfw.utils.item.BuildItem;
import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.StatType;
import de.cuuky.varo.gui.VaroListInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class PlayerOptionsGUI extends VaroListInventory<StatType> {

    private final VaroPlayer target;

    public PlayerOptionsGUI(Player opener, VaroPlayer target) {
        super(Main.getCuukyFrameWork().getAdvancedInventoryManager(), opener, Arrays.asList(StatType.values()));

        this.target = target;
    }

    @Override
    public String getTitle() {
        return Main.getColorCode() + target.getName() + " §7(" + target.getId() + ")";
    }

    @Override
    public int getSize() {
        return this.getRecommendedSize(9);
    }

    public VaroPlayer getTarget() {
        return this.target;
    }

    @Override
    protected ItemStack getItemStack(StatType statType) {
        return new BuildItem().itemstack(statType.getIcon()).displayName(statType.getDisplayName())
                .lore("§7Current: " + Main.getColorCode() + statType.get(this.target),
                        "", "Left-Click to set value", "Right-Click to reset").build();
    }

    @Override
    protected ItemClick getClick(StatType statType) {
        return (event) -> {
            if (event.isLeftClick()) {
                Main.getCuukyFrameWork().getHookManager().registerHook(new ChatHook(getPlayer(),
                        Main.getPrefix() + "Enter new " + Main.getColorCode() + statType.name() + "§7: §8('!cancel' to cancel)", new ChatHookHandler() {

                    @Override
                    public boolean onChat(AsyncPlayerChatEvent event) {
                        if (event.getMessage().equalsIgnoreCase("!cancel")) {
                            open();
                            return true;
                        }

                        boolean success = statType.execute(event.getMessage(), target, getPlayer());
                        if (success) open();
                        return success;
                    }
                }));
                this.close();
            } else if (event.isRightClick())
                this.openNext(new ConfirmInventory(this, "§cReset " + statType.name() + "?", (accept) -> {
                    if (accept) statType.remove(this.target);
                    this.open();
                }));
        };
    }
}