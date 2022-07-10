package de.varoplugin.varo.ui.commands;

import de.varoplugin.varo.game.VaroState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;

public class TestCommand extends VaroCommand implements Listener {

    private int index = 1;

//    private final ItemHookBuilder diamondClicker = new PlayerItemHookBuilder().item(new ItemStack(Material.DIAMOND_SWORD))
//        .subscribe(HookItemInteractEvent.class, this::removeOnClick);

    public TestCommand() {
        super("test");
    }

//    private void removeOnClick(HookItemInteractEvent event) {
//        if (event.getSource().getAction() != Action.RIGHT_CLICK_AIR) return;
//        event.getHook().unregister();
//    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Itemhook test
//        this.diamondClicker.complete((Player) sender, this.getVaro().getPlugin());

        // AutoStart test
//        Calendar now = new GregorianCalendar();
//        now.add(Calendar.SECOND, 5);
//        this.getVaro().setAutoStart(now);

        // ChatHook test
//        new PlayerChatHookBuilder().player((Player) sender).message("Test").register(this.getVaro().getPlugin());

        // Cycle gamestate
        this.getVaro().setState(VaroState.values()[this.index % VaroState.values().length]);
        this.index++;
        return false;
    }
}