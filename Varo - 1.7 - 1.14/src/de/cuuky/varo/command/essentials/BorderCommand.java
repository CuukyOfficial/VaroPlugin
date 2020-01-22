package de.cuuky.varo.command.essentials;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.messages.ConfigMessages;
import de.cuuky.varo.version.BukkitVersion;
import de.cuuky.varo.version.VersionUtils;
import de.cuuky.varo.version.types.Sounds;
import de.cuuky.varo.world.border.VaroBorder;

public class BorderCommand implements CommandExecutor {

	/*
	 * PARTLY OLD CODE
	 */

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(!VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_7)) {
			sender.sendMessage(Main.getPrefix() + "Nicht verfügbar vor der 1.8!");
			return false;
		}

		if(args.length == 0) {
			sender.sendMessage(Main.getPrefix() + "§7Die Border ist " + Main.getColorCode() + (sender instanceof Player ? VaroBorder.getInstance().getBorderSize(((Player) sender).getWorld()) : VaroBorder.getInstance().getBorderSize(null)) + " §7Blöcke groß!");
			if(sender instanceof Player)
				sender.sendMessage(Main.getPrefix() + "§7Du bist " + Main.getColorCode() + (int) VaroBorder.getInstance().getBorderDistanceTo((Player) sender) + "§7 Blöcke von der Border entfernt!");

			if(sender.hasPermission("varo.setup")) {
				sender.sendMessage(Main.getPrefix() + "§7Du kannst die Größe der Border mit " + Main.getColorCode() + "/border <Größe> §7setzen!");
				sender.sendMessage(Main.getPrefix() + "§7Der Mittelpunkt der Border bleibt dann der WorldSpawn.");
			}
			return false;
		} else if(args.length >= 1 && sender.hasPermission("varo.setup")) {
			Player p = sender instanceof Player ? (Player) sender : null;
			int borderSize, inSeconds = -1;

			try {
				borderSize = Integer.parseInt(args[0]);
			} catch(NumberFormatException e) {
				p.sendMessage(Main.getPrefix() + "§7Das ist keine Zahl!");
				return false;
			}

			VaroBorder border = VaroBorder.getInstance();
			World playerWorld = (p != null ? p.getWorld() : null);
			try {
				inSeconds = Integer.parseInt(args[1]);
				border.setBorderSize(borderSize, inSeconds, playerWorld);
			} catch(Exception e) {
				border.setBorderSize(borderSize, 0, playerWorld);
			}

			sender.sendMessage(Main.getPrefix() + ConfigMessages.COMMAND_SET_BORDER.getValue().replace("%zahl%", String.valueOf(borderSize)));
			if(p != null)
				p.playSound(p.getLocation(), Sounds.NOTE_BASS_DRUM.bukkitSound(), 1, 1);
		} else
			sender.sendMessage(ConfigMessages.OTHER_NO_PERMISSION.getValue());
		return false;
	}
}