package de.cuuky.varo.command.essentials;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.messages.ConfigMessages;
import de.cuuky.varo.version.types.Sounds;

public class SetWorldspawnCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(!(sender instanceof Player)) {
			System.out.println("Nicht f√ºr die Konsole");
			return false;
		}

		Player p = (Player) sender;
		if(!p.hasPermission("Varo.setup")) {
			sender.sendMessage(ConfigMessages.OTHER_NO_PERMISSION.getValue());
			return false;
		}

		if(args.length != 0) {
			p.sendMessage(Main.getPrefix() + "ß7/setworldspawn");
			return false;
		}

		p.getWorld().setSpawnLocation(p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
		Main.getVaroGame().getVaroWorld().getVaroBorder().setBorderCenter(p.getLocation());
		p.sendMessage(Main.getPrefix() + Main.getColorCode() + "Weltspawn ß7erfolgreich gesetzt!");
		p.playSound(p.getLocation(), Sounds.NOTE_BASS_DRUM.bukkitSound(), 1, 1);
		return false;
	}
}