package de.cuuky.varo.gui.player;

import de.cuuky.cfw.hooking.hooks.chat.ChatHook;
import de.cuuky.cfw.hooking.hooks.chat.ChatHookHandler;
import de.cuuky.cfw.inventory.ItemClick;
import de.cuuky.cfw.inventory.confirm.ConfirmInventory;
import de.cuuky.cfw.inventory.page.AdvancedInfiniteInventory;
import de.cuuky.cfw.utils.item.BuildItem;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.app.Main;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.StatType;
import de.cuuky.varo.entity.player.stats.stat.PlayerState;
import de.cuuky.varo.utils.ArrayUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class PlayerOptionsGUI extends AdvancedInfiniteInventory {

    private final VaroPlayer target;
    private int index;

    public PlayerOptionsGUI(Player opener, VaroPlayer target) {
        super(Main.getCuukyFrameWork().getAdvancedInventoryManager(), opener);

        this.target = target;
    }

    private ItemClick getClick(StatType statType) {
        return (event) -> {
            if (event.isLeftClick()) {
                Main.getCuukyFrameWork().getHookManager().registerHook(new ChatHook(getPlayer(),
                    Main.getPrefix() + "Enter new " + Main.getColorCode() + statType.name() +
                        "§7: §8('!cancel' to cancel)", new ChatHookHandler() {

                    @Override
                    public boolean onChat(AsyncPlayerChatEvent event) {
                        if (event.getMessage().equalsIgnoreCase("!cancel")) {
                            open();
                            return true;
                        }

                        boolean success = statType.execute(event.getMessage(), target);
                        if (success) {
                            open();
                        } else {
                            getPlayer().sendMessage(
                                Main.getPrefix() + "Konnte nicht gesetzt werden! Versuche es erneut:");
                        }
                        return success;
                    }
                }));
                this.close();
            } else if (event.isRightClick())
                this.openReset(statType);
        };
    }

    private void applyToTypes(Predicate<Object> objectPredicate, Consumer<StatType> forEach, int add) {
        Arrays.stream(StatType.values()).filter(s -> objectPredicate.test(s.get(target))).forEach(s -> {
            forEach.accept(s);
            this.index += add;
        });
    }

    private void addLeftClickable(Predicate<Object> objectPredicate, Consumer<StatType> leftClick, int offset) {
        this.applyToTypes(objectPredicate, s -> this.addItem(index, this.getItemStack(s), (event) -> {
            if (event.isLeftClick()) {
                leftClick.accept(s);
            } else this.openReset(s);
        }), offset);
    }

    private ItemStack getItemStack(StatType statType) {
        return new BuildItem().itemstack(statType.getIcon()).displayName(statType.getDisplayName())
            .lore("§7Current: " + Main.getColorCode() + statType.get(this.target),
                "", "§7Left-Click to change value", "§7Right-Click to reset").deleteDamageAnnotation().build();
    }

    private void openReset(StatType type) {
        this.openNext(new ConfirmInventory(this, "§cReset " + type.name() + "?", (accept) -> {
            if (accept) type.remove(this.target);
            this.open();
        }));
    }

    @Override
    public String getTitle() {
        return Main.getColorCode() + target.getName() + " §7(" + target.getId() + ")";
    }

    @Override
    public int getSize() {
        return 45;
    }

    public VaroPlayer getTarget() {
        return this.target;
    }

    @Override
    public void refreshContent() {
        this.index = 0;
        this.applyToTypes(i -> i instanceof Integer, s -> {
            this.addItem(index, new BuildItem().material(Materials.ROSE_RED).displayName("§c-").build(),
                (e) -> s.execute(((int) s.get(this.target)) - 1, this.target));
            this.addItem(index + 4, this.getItemStack(s), this.getClick(s));
            this.addItem(index + 8, new BuildItem().material(Materials.CACTUS_GREEN).displayName("§a+").build(),
                (e) -> s.execute(((int) s.get(this.target)) + 1, this.target));
        }, 9);
        this.index += 9;

        this.addLeftClickable(b -> b instanceof Boolean, s -> s.execute(!((Boolean) s.get(this.target)), this.target), 2);
        this.addLeftClickable(b -> b instanceof PlayerState,
            s -> s.execute(ArrayUtils.getNext(s.get(this.target), PlayerState.values()), this.target), 2);

        this.applyToTypes(i -> !(i instanceof Integer) && !(i instanceof Boolean) && !(i instanceof PlayerState),
            s -> this.addItem(index, this.getItemStack(s), this.getClick(s)), 2);
    }
}