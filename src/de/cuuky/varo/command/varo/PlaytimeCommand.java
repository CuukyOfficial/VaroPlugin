package de.cuuky.varo.command.varo;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.entity.player.VaroPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlaytimeCommand extends VaroCommand {
    public PlaytimeCommand() {
        super("playtime", "Zeigt die restliche Spielzeit", null, "time");
    }

    @Override
    public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            String msg = Main.getPrefix() + ChatColor.GRAY + "Deine verbleibende Zeit: ";

            int secs = vp.getStats().getCountdown();
            int hours = secs / 3600;
            if (hours >= 1) msg += Main.getColorCode() + String.format("%02d", hours) + ChatColor.GRAY + ":";

            msg += Main.getColorCode() + String.format("%02d", (secs / 60) % 60) + ChatColor.GRAY + ":";
            msg += Main.getColorCode() + String.format("%02d", secs % 60) + ChatColor.GRAY + ".";

            vp.sendMessage(msg);
        } else sender.sendMessage(Main.getPrefix() + "Du musst ein Spieler sein!");
    }
}
