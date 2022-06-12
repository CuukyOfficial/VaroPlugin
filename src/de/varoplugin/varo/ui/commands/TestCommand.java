package de.varoplugin.varo.ui.commands;

import de.varoplugin.cfw.player.hook.chat.PlayerChatHookBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class TestCommand extends VaroCommand {

//    private int index = 1;

    public TestCommand() {
        super("test");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        new PlayerChatHookBuilder().player((Player) sender).message("Test").register(this.getVaro().getPlugin());
//        this.getVaro().setState(GameState.values()[this.index % GameState.values().length]);
//        this.index++;
//        sender.sendMessage(this.getVaro().getState().toString());
        return false;
    }
}