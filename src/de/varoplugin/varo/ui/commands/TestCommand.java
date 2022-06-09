package de.varoplugin.varo.ui.commands;

import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.GameState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class TestCommand implements CommandExecutor {

    private final Varo varo;
    private int index = 1;

    public TestCommand(Varo varo) {
        this.varo = varo;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        this.varo.setState(GameState.values()[this.index % GameState.values().length]);
        this.index++;
        sender.sendMessage(this.varo.getState().toString());
        return false;
    }
}