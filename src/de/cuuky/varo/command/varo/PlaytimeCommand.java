package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlaytimeCommand extends VaroCommand {
    private static final String[] subCommands = null;
    public PlaytimeCommand() {
        super("playtime", "Zeigt die restliche Spielzeit", null, subCommands, "time");
    }

    @Override
    public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            String msg = Main.getPrefix() + ConfigMessages.VARO_COMMANDS_PLAYTIME.getValue(vp, vp);
            vp.sendMessage(msg);
        } else sender.sendMessage(Main.getPrefix() + "Du musst ein Spieler sein!");
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> list = new ArrayList<>();
        if (args.length == 2) {
            List<String> subCommands = Arrays.asList(this.subCommands);
            list.addAll(subCommands);
        }
        ArrayList<String> completerList = new ArrayList<>();
        String curentarg = args[args.length - 1].toLowerCase();
        for (String s : list) {
            String s1 = s.toLowerCase();
            if (s1.startsWith(curentarg)) {
                completerList.add(s);
            }
        }
        return completerList;
    }
}
