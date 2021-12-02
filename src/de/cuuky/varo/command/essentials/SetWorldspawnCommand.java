package de.cuuky.varo.command.essentials;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.cfw.version.types.Sounds;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.game.world.border.VaroWorldBorder;

public class SetWorldspawnCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		VaroPlayer vp = (sender instanceof Player ? VaroPlayer.getPlayer((Player) sender) : null);
		if (!(sender instanceof Player)) {
			System.out.println("Nicht fuer die Konsole");
			return false;
		}

		Player p = (Player) sender;
		if (!p.hasPermission("Varo.setup")) {
			sender.sendMessage(ConfigMessages.NOPERMISSION_NO_PERMISSION.getValue(vp));
			return false;
		}

		if (args.length != 0) {
			p.sendMessage(Main.getPrefix() + "§7/setworldspawn");
			return false;
		}

		p.getWorld().setSpawnLocation(p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
		VaroWorldBorder border = Main.getVaroGame().getVaroWorldHandler().getVaroWorld(p.getWorld()).getVaroBorder();
		if(border != null)
			border.setBorderCenter(p.getLocation());
		
		p.sendMessage(Main.getPrefix() + Main.getColorCode() + ConfigMessages.COMMANDS_SETWORLDSPAWN.getValue(vp));
		p.playSound(p.getLocation(), Sounds.NOTE_BASS_DRUM.bukkitSound(), 1, 1);
		return false;
	}
}