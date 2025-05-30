package de.varoplugin.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.varoplugin.varo.command.VaroCommand;
import de.varoplugin.varo.config.language.Messages;
import de.varoplugin.varo.player.VaroPlayer;

public class PlaytimeCommand extends VaroCommand {
    public PlaytimeCommand() {
        super("playtime", "Zeigt die restliche Spielzeit", null, "time");
    }

    @Override
    public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
        if (sender instanceof Player)
            Messages.COMMANDS_VARO_PLAYTIME.send(sender);
        else
            Messages.COMMANDS_ERROR_NO_CONSOLE.send(sender);
    }
}
