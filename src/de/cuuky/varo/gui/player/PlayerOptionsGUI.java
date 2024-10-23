package de.cuuky.varo.gui.player;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.cryptomorin.xseries.XMaterial;

import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.StatType;
import de.cuuky.varo.entity.player.stats.stat.PlayerState;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.utils.ArrayUtils;
import de.varoplugin.cfw.inventory.ItemClick;
import de.varoplugin.cfw.inventory.inbuilt.ConfirmInventory;
import de.varoplugin.cfw.inventory.page.AdvancedInfiniteInventory;
import de.varoplugin.cfw.item.ItemBuilder;
import de.varoplugin.cfw.player.hook.chat.ChatHookTriggerEvent;
import de.varoplugin.cfw.player.hook.chat.PlayerChatHookBuilder;

public class PlayerOptionsGUI extends AdvancedInfiniteInventory {

    private final VaroPlayer target;
    private int index;

    public PlayerOptionsGUI(Player opener, VaroPlayer target) {
        super(Main.getInventoryManager(), opener);

        this.target = target;
    }

    private ItemClick getClick(StatType statType) {
        return (event) -> {
            if (event.isLeftClick()) {
                new PlayerChatHookBuilder().message(Main.getPrefix() + "Enter new " + Main.getColorCode() + statType.name() + "§7: §8('!cancel' to cancel)")
                .subscribe(ChatHookTriggerEvent.class, hookEvent -> {
                    if (hookEvent.getMessage().equalsIgnoreCase("!cancel")) {
                        open();
                        hookEvent.getHook().unregister();
                        return;
                    }

                    if (statType.execute(hookEvent.getMessage(), target)) {
                        open();
                        hookEvent.getHook().unregister();
                    } else
                        getPlayer().sendMessage( Main.getPrefix() + "Konnte nicht gesetzt werden! Versuche es erneut:");
                }).complete(getPlayer(), Main.getInstance());
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
                if (s == StatType.ADMIN_IGNORE && !Main.getVaroGame().hasStarted()) {
                    event.getWhoClicked().sendMessage(Main.getPrefix() + "§cAdmin-ignore cannot be enabled before the game has started!");
                    return;
                }
                leftClick.accept(s);
            } else this.openReset(s);
        }), offset);
    }

    private ItemStack getItemStack(StatType statType) {
        return ItemBuilder.material(statType.getIcon()).displayName(statType.getDisplayName())
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
            this.addItem(index, ItemBuilder.material(XMaterial.RED_DYE).displayName("§c-").build(),
                    (e) -> s.execute(((int) s.get(this.target)) - 1, this.target));
            this.addItem(index + 4, this.getItemStack(s), this.getClick(s));
            this.addItem(index + 8, ItemBuilder.material(XMaterial.GREEN_DYE).displayName("§a+").build(),
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