package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.app.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;

public class PlaytimeCommand extends VaroCommand {
    public PlaytimeCommand() {
        super("playtime", "Zeigt die restliche Spielzeit", null, "time");
    }

    @Override
    public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            String msg = Main.getPrefix() + ConfigMessages.VARO_COMMANDS_PLAYTIME.getValue(vp, vp);
            vp.sendMessage(msg);
        } else sender.sendMessage(Main.getPrefix() + "Du musst ein Spieler sein!");
    }
}
