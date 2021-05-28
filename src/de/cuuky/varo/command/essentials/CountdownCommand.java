package de.cuuky.varo.command.essentials;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class CountdownCommand implements CommandExecutor {

    /*
     * OLD CODE
     */

    BukkitTask sched = null;
    int time = 0;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        VaroPlayer vp = (sender instanceof Player ? VaroPlayer.getPlayer((Player) sender) : null);
        if (!sender.hasPermission("varo.countdowm")) {
            sender.sendMessage(ConfigMessages.NOPERMISSION_NO_PERMISSION.getValue(vp));
            return false;
        }

        if (sched != null) {
            sched.cancel();
            sched = null;

            sender.sendMessage(Main.getPrefix() + ConfigMessages.COMMANDS_COUNTDOWN_ABORT.getValue(vp));
            return false;
        }

        if (args.length != 1) {
            sender.sendMessage(Main.getPrefix() + "§7/countdown <seconds>");
            return false;
        }

        time = 0;
        try {
            time = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_ERROR_NO_NUMBER.getValue(vp));
        }

        if (time < 1) {
            sender.sendMessage(Main.getPrefix() + ConfigMessages.COMMANDS_COUNTDOWN_TOO_SMALL.getValue(vp));
            return false;
        }

        sched = new BukkitRunnable() {
            @Override
            public void run() {
                if (time == 0) {
                    Bukkit.broadcastMessage(Main.getPrefix() + ConfigMessages.COMMANDS_COUNTDOWN_START.getValue(vp));
                    time = -1;
                    sched.cancel();
                    sched = null;
                    return;
                }

                Bukkit.broadcastMessage(Main.getPrefix() + ConfigMessages.COMMANDS_COUNTDOWN_FORMAT.getValue(vp).replace("%seconds%", String.valueOf(time)));
                time--;
            }
        }.runTaskTimer(Main.getInstance(), 0L, 20L);
        return false;
    }
}