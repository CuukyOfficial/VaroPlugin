package de.cuuky.varo.command.varo;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.entity.player.VaroPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnpauseCommand extends VaroCommand {

    public UnpauseCommand() {
        super("pause", "Pausiere den Countdown eines Spielers", "varo.pause", "pauseCountdown", "pausePlayer", "pausePlayerCountdown");
    }

    @Override
    public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {

        if (args.length != 1) {
            sender.sendMessage(Main.getPrefix() + "§7/pause <Player/@a>");
            sender.sendMessage(Main.getPrefix() + "§7/unpause <Player/@a>");
            return;
        }

        if (args[0].equalsIgnoreCase("@a")) {
            for (VaroPlayer player : VaroPlayer.getOnlinePlayer()) {
                if (player.getPlayer().isOp()) {
                    continue;
                }
                player.getStats().setCountdownPaused(false);
            }

            sender.sendMessage(Main.getPrefix() + "Erfolgreich den Countdown aller Spieler fortgesetzt!");
            return;
        }

        if (Bukkit.getPlayerExact(args[0]) == null) {
            sender.sendMessage(Main.getPrefix() + "§7" + args[0] + " §7nicht gefunden!");
            return;
        }

        Player player = Bukkit.getPlayerExact(args[0]);
        VaroPlayer target = VaroPlayer.getPlayer(player);

        target.getStats().setCountdownPaused(false);

        sender.sendMessage(Main.getPrefix() + "§7" + args[0] + " §7erfolgreich Countdown fortgesetzt!");
        target.sendMessage(Main.getPrefix() + "§7Dein Countdown wurde fortgesetzt!");
    }

}
