package de.varoplugin.varo.ui.commands;

import de.varoplugin.varo.game.GameState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class TestCommand extends VaroCommand {

    private int index = 1;

    public TestCommand() {
        super("test");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        this.getVaro().setState(GameState.values()[this.index % GameState.values().length]);
        this.index++;
        sender.sendMessage(this.getVaro().getState().toString());
        return false;
    }
}