package de.cuuky.varo.command.essentials;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.messages.ConfigMessages;
import de.cuuky.varo.version.BukkitVersion;
import de.cuuky.varo.version.VersionUtils;
import de.cuuky.varo.version.types.Sounds;
import de.cuuky.varo.world.border.VaroBorder;

public class BorderCommand implements CommandExecutor {

	/*
	 * OLD CODE
	 */

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(VersionUtils.getVersion() == BukkitVersion.ONE_7) {
			sender.sendMessage(Main.getPrefix() + "Nicht verfügbar in der 1.7!");
			return false;
		}

		if(args.length == 0) {
			sender.sendMessage(Main.getPrefix() + "§7Die Border ist " + Main.getColorCode() + (sender instanceof Player ? new VaroBorder(((Player) sender).getWorld()).getSize() : Main.getDataManager().getWorldHandler().getBorder().getSize()) + " §7Blöcke groß!");
			if(sender instanceof Player)
				sender.sendMessage(Main.getPrefix() + "§7Du bist " + Main.getColorCode() + (int) Main.getDataManager().getWorldHandler().getBorder().getDistanceTo((Player) sender) + "§7 Blöcke von der Border entfernt!");

			if(sender.hasPermission("varo.setup"))
				sender.sendMessage(Main.getPrefix() + "§7Du kannst die Größe der Border mit " + Main.getColorCode() + "/border <Größe> §7setzen!");
			return false;
		} else if(args.length >= 1 && sender.hasPermission("varo.setup")) {
			Player p = sender instanceof Player ? (Player) sender : null;
			int border1;
			int inSeconds = -1;

			try {
				border1 = Integer.parseInt(args[0]);
			} catch(NumberFormatException e) {
				p.sendMessage(Main.getPrefix() + "§7Das ist keine Zahl!");
				return false;
			}

			VaroBorder border = p != null ? new VaroBorder(p.getWorld()) : Main.getDataManager().getWorldHandler().getBorder();
			try {
				inSeconds = Integer.parseInt(args[1]);
				border.setSize(border1, inSeconds);
			} catch(ArrayIndexOutOfBoundsException e) {
				border.setSize(border1);
			}

			if(p != null)
				border.setCenter(p.getLocation());

			sender.sendMessage(Main.getPrefix() + ConfigMessages.COMMAND_SET_BORDER.getValue().replace("%zahl%", String.valueOf(border1)));
			if(p != null)
				p.playSound(p.getLocation(), Sounds.NOTE_BASS_DRUM.bukkitSound(), 1, 1);
		} else
			sender.sendMessage(Main.getPrefix() + "§7/border");
		return false;
	}
}
