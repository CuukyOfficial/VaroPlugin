package de.varoplugin.varo.ui.commands;

import de.varoplugin.cfw.player.hook.item.HookItemInteractEvent;
import de.varoplugin.cfw.player.hook.item.ItemHookBuilder;
import de.varoplugin.cfw.player.hook.item.PlayerItemHookBuilder;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class TestCommand extends VaroCommand implements Listener {

//    private int index = 1;

    private final ItemHookBuilder diamondClicker = new PlayerItemHookBuilder().item(new ItemStack(Material.DIAMOND_SWORD))
        .subscribe(HookItemInteractEvent.class, this::removeOnClick);

    public TestCommand() {
        super("test");
    }

    private void removeOnClick(HookItemInteractEvent event) {
        if (event.getSource().getAction() != Action.RIGHT_CLICK_AIR) return;
        event.getHook().unregister();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        this.diamondClicker.complete((Player) sender, this.getVaro().getPlugin());

//        new PlayerChatHookBuilder().player((Player) sender).message("Test").register(this.getVaro().getPlugin());
//        this.getVaro().setState(GameState.values()[this.index % GameState.values().length]);
//        this.index++;
//        sender.sendMessage(this.getVaro().getState().toString());
        return false;
    }
}