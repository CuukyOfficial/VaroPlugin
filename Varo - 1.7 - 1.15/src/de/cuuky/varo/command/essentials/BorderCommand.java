package de.cuuky.varo.command.essentials;

import de.cuuky.cfw.version.BukkitVersion;
import de.cuuky.cfw.version.VersionUtils;
import de.cuuky.cfw.version.types.Sounds;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.game.world.VaroWorldHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BorderCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
        VaroPlayer vp = (sender instanceof Player ? VaroPlayer.getPlayer((Player) sender) : null);
        if (!VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_7)) {
            sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_ERROR_WRONGVERSION.getValue(vp).replace("%version%", "1.8"));
            return false;
        }

        if (args.length == 0) {
            sender.sendMessage(Main.getPrefix() + ConfigMessages.COMMANDS_BORDER_SIZE.getValue(vp).replace("%size", String.valueOf((sender instanceof Player ? Main.getVaroGame().getVaroWorldHandler().getVaroWorld(((Player) sender).getWorld()).getVaroBorder().getBorderSize() : Main.getVaroGame().getVaroWorldHandler().getMainWorld().getVaroBorder().getBorderSize()))));
            if (sender instanceof Player)
                sender.sendMessage(Main.getPrefix() + ConfigMessages.COMMANDS_BORDER_DISTANCE.getValue(vp).replace("%distance%", String.valueOf((int) Main.getVaroGame().getVaroWorldHandler().getVaroWorld(((Player) sender).getWorld()).getVaroBorder().getBorderDistanceTo((Player) sender))));
            if (sender.hasPermission("varo.setup")) {
                sender.sendMessage(Main.getPrefix() + ConfigMessages.COMMANDS_BORDER_USAGE.getValue(vp));
            }
            return false;
        } else if (args.length >= 1 && sender.hasPermission("varo.setup")) {
            Player p = sender instanceof Player ? (Player) sender : null;
            VaroWorldHandler worldHandler = Main.getVaroGame().getVaroWorldHandler();

            if (args[0].equalsIgnoreCase("center")) {
                if (p != null)
                    worldHandler.getVaroWorld(p.getWorld()).getVaroBorder().setBorderCenter(p.getLocation());
                else sender.sendMessage("Only for players!");
                return true;
            }

            int borderSize, inSeconds = -1;
            try {
                borderSize = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                p.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_ERROR_NO_NUMBER.getValue(vp));
                return false;
            }

            try {
                inSeconds = Integer.parseInt(args[1]);
                worldHandler.setBorderSize(borderSize, inSeconds, p != null ? p.getWorld() : null);
            } catch (ArrayIndexOutOfBoundsException e) {
                worldHandler.setBorderSize(borderSize, 0, p != null ? p.getWorld() : null);
            } catch (NumberFormatException e) {
                sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_ERROR_NO_NUMBER.getValue(vp));
                return false;
            }

            sender.sendMessage(Main.getPrefix() + ConfigMessages.BORDER_COMMAND_SET_BORDER.getValue(vp).replace("%size%", String.valueOf(borderSize)));
            if (p != null)
                p.playSound(p.getLocation(), Sounds.NOTE_BASS_DRUM.bukkitSound(), 1, 1);
        } else
            sender.sendMessage(ConfigMessages.NOPERMISSION_NO_PERMISSION.getValue(vp));
        return false;
    }
}