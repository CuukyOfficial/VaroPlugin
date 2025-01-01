package de.cuuky.varo.command.essentials;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.cryptomorin.xseries.XSound;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.language.Messages;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.game.world.VaroWorldHandler;
import de.cuuky.varo.player.VaroPlayer;
import de.varoplugin.cfw.version.ServerVersion;
import de.varoplugin.cfw.version.VersionUtils;
import io.github.almightysatan.slams.Placeholder;

public class BorderCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
        VaroPlayer vp = (sender instanceof Player ? VaroPlayer.getPlayer((Player) sender) : null);
        if (!VersionUtils.getVersion().isHigherThan(ServerVersion.ONE_7)) {
            Messages.COMMANDS_ERROR_WRONGVERSION.send(sender, Placeholder.constant("required-version", "1.8.8"));
            return false;
        }

        if (args.length == 0) {
            Messages.COMMANDS_BORDER_SIZE.send(sender);
            if (sender instanceof Player)
                Messages.COMMANDS_BORDER_DISTANCE.send(sender);
            if (sender.hasPermission("varo.setup")) {
                Messages.COMMANDS_BORDER_USAGE.send(sender);
            }
            return false;
        } else if (args.length >= 1 && sender.hasPermission("varo.setup")) {
            Player p = sender instanceof Player ? (Player) sender : null;
            VaroWorldHandler worldHandler = Main.getVaroGame().getVaroWorldHandler();

            if (args[0].equalsIgnoreCase("center")) {
                if (p != null) {
                    worldHandler.getVaroWorld(p.getWorld()).getVaroBorder().setCenter(p.getLocation());
                    p.sendMessage(Main.getPrefix() + "Zentrum der Border gesetzt!");
                } else sender.sendMessage("Only for players!");
                return true;
            }

            int borderSize, inSeconds;
            try {
                borderSize = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                Messages.COMMANDS_ERROR_NO_NUMBER.send(sender, Placeholder.constant("text", args[0]));
                return false;
            }

            try {
                inSeconds = Integer.parseInt(args[1]);
                worldHandler.setBorderSize(borderSize, inSeconds, p != null ? p.getWorld() : null);
            } catch (ArrayIndexOutOfBoundsException e) {
                worldHandler.setBorderSize(borderSize, 0, p != null ? p.getWorld() : null);
            } catch (NumberFormatException e) {
                Messages.COMMANDS_ERROR_NO_NUMBER.send(sender, Placeholder.constant("text", args[1]));
                return false;
            }

            sender.sendMessage(Main.getPrefix() + ConfigMessages.BORDER_COMMAND_SET_BORDER.getValue(vp).replace("%size%", String.valueOf(borderSize)));
            if (p != null)
                p.playSound(p.getLocation(), XSound.BLOCK_NOTE_BLOCK_BASEDRUM.parseSound(), 1, 1);
        } else
            sender.sendMessage(ConfigMessages.NOPERMISSION_NO_PERMISSION.getValue(vp));
        return false;
    }
}