package de.varoplugin.varo.ui.commands;

import de.varoplugin.varo.VaroJavaPlugin;
import de.varoplugin.varo.config.language.Messages;
import de.varoplugin.varo.game.VaroState;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;

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
        // this.getVaro().setState(VaroState.values()[this.index % VaroState.values().length]);
        // this.index++;
    	
    	Messages messages = this.getVaro().getPlugin().getMessages();
    	sender.sendMessage(messages.hello_world.value("This is a local placeholder"));
    	sender.sendMessage(this.getVaro().getPlugin().getMessages().arrayTest.value()[0].value());
    	sender.sendMessage(this.getVaro().getPlugin().getMessages().arrayTest.value()[1].value());
    	
    	// MiniMessage test
    	// Audience audience = BukkitAudiences.create(this.getVaro().getPlugin()).sender(sender);
    	// audience.sendMessage(messages.hello_world.miniMessage().value("<blue> This is a local placeholder"));

        return false;
    }
}