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

package de.varoplugin.varo.ui.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;

public class TestCommand extends VaroCommand implements Listener {

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

//         Cycle gamestate
         this.getVaro().nextState();
         sender.sendMessage(this.getVaro().getState().toString());
    	
    	// Messages messages = this.getVaro().getPlugin().getMessages();
    	// sender.sendMessage(messages.hello_world.value("This is a local placeholder"));
    	// sender.sendMessage(this.getVaro().getPlugin().getMessages().arrayTest.value()[0].value());
    	// sender.sendMessage(this.getVaro().getPlugin().getMessages().arrayTest.value()[1].value());
    	
    	// MiniMessage test
    	// Audience audience = BukkitAudiences.create(this.getVaro().getPlugin()).sender(sender);
    	// audience.sendMessage(messages.hello_world.miniMessage().value("<blue> This is a local placeholder"));

        return false;
    }
}