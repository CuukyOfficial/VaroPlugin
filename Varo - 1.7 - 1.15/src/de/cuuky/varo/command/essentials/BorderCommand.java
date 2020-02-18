package de.cuuky.varo.command.essentials;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.messages.ConfigMessages;
import de.cuuky.varo.game.world.border.VaroBorder;
import de.cuuky.varo.version.BukkitVersion;
import de.cuuky.varo.version.VersionUtils;
import de.cuuky.varo.version.types.Sounds;

public class BorderCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(!VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_7)) {
			sender.sendMessage(Main.getPrefix() + "Nicht verfügbar vor der 1.8!");
			return false;
		}

		if(args.length == 0) {
			sender.sendMessage(Main.getPrefix() + "§7Die Border ist " + Main.getColorCode() + (sender instanceof Player ? Main.getVaroGame().getVaroWorld().getVaroBorder().getBorderSize(((Player) sender).getWorld()) : Main.getVaroGame().getVaroWorld().getVaroBorder().getBorderSize(null)) + " §7Blöcke groß!");
			if(sender instanceof Player)
				sender.sendMessage(Main.getPrefix() + "§7Du bist " + Main.getColorCode() + (int) Main.getVaroGame().getVaroWorld().getVaroBorder().getBorderDistanceTo((Player) sender) + "§7 Blöcke von der Border entfernt!");

			if(sender.hasPermission("varo.setup")) {
				sender.sendMessage(Main.getPrefix() + "§7Du kannst die Größe der Border mit " + Main.getColorCode() + "/border <Größe> [Sekunden] §7setzen!");
				sender.sendMessage(Main.getPrefix() + "§7Der Mittelpunkt der Border wird zu deinem derzeiten Punkt gesetzt");
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

			VaroBorder border = Main.getVaroGame().getVaroWorld().getVaroBorder();
			World playerWorld = (p != null ? p.getWorld() : null);
			if(p != null)
				border.setBorderCenter(p.getLocation());
			try {
				inSeconds = Integer.parseInt(args[1]);
				border.setBorderSize(borderSize, inSeconds, playerWorld);
			} catch(ArrayIndexOutOfBoundsException e) {
				border.setBorderSize(borderSize, 0, playerWorld);
			} catch(NumberFormatException e) {
				sender.sendMessage(Main.getPrefix() + "§7Das ist keine Zahl!");
				return false;
			}

			sender.sendMessage(Main.getPrefix() + ConfigMessages.COMMAND_SET_BORDER.getValue().replace("%size%", String.valueOf(borderSize)));
			if(p != null)
				p.playSound(p.getLocation(), Sounds.NOTE_BASS_DRUM.bukkitSound(), 1, 1);
		} else
			sender.sendMessage(ConfigMessages.OTHER_NO_PERMISSION.getValue());
		return false;
	}
}