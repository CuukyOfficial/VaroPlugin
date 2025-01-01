package de.cuuky.varo.command.essentials;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.language.Messages;
import io.github.almightysatan.slams.Placeholder;

public class CountdownCommand implements CommandExecutor {

    /*
     * OLD CODE
     */

    BukkitTask sched = null;
    int time = 0;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("varo.countdowm")) {
            Messages.COMMANDS_ERROR_PERMISSION.send(sender);
            return false;
        }

        if (sched != null) {
            sched.cancel();
            sched = null;

            Messages.COMMANDS_COUNTDOWN_ABORT.send(sender);
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
            Messages.COMMANDS_ERROR_NO_NUMBER.send(sender, Placeholder.constant("text", args[0]));
        }

        if (time < 1) {
            Messages.COMMANDS_COUNTDOWN_TOO_SMALL.send(sender);
            return false;
        }

        sched = new BukkitRunnable() {
            @Override
            public void run() {
                if (time == 0) {
                    Messages.COMMANDS_COUNTDOWN_START.broadcast();
                    time = -1;
                    sched.cancel();
                    sched = null;
                    return;
                }

                Messages.COMMANDS_COUNTDOWN_FORMAT.broadcast(Placeholder.constant("seconds", String.valueOf(time)));
                time--;
            }
        }.runTaskTimer(Main.getInstance(), 0L, 20L);
        return false;
    }
}