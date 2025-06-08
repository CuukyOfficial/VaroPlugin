package de.varoplugin.varo.command.essentials;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.cryptomorin.xseries.XSound;

import de.varoplugin.varo.Main;
import de.varoplugin.varo.config.language.Messages;
import de.varoplugin.varo.game.world.border.VaroBorder;

public class SetWorldspawnCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if (!(sender instanceof Player)) {
		    Messages.COMMANDS_ERROR_NO_CONSOLE.send(sender);
			return false;
		}

		Player p = (Player) sender;
		if (!p.hasPermission("Varo.setup")) {
			Messages.COMMANDS_ERROR_PERMISSION.send(sender);
			return false;
		}

		if (args.length != 0) {
			p.sendMessage(Main.getPrefix() + "§7/setworldspawn");
			return false;
		}

		p.getWorld().setSpawnLocation(p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
		VaroBorder border = Main.getVaroGame().getVaroWorldHandler().getVaroWorld(p.getWorld()).getVaroBorder();
		if(border != null)
			border.setCenter(p.getLocation());

		Messages.COMMANDS_SETWORLDSPAWN.send(sender);
		p.playSound(p.getLocation(), XSound.BLOCK_NOTE_BLOCK_BASEDRUM.get(), 1, 1);
		return false;
	}
}