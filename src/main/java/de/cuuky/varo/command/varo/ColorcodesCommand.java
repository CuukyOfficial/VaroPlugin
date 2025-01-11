package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.player.VaroPlayer;

public class ColorcodesCommand extends VaroCommand {

	public ColorcodesCommand() {
		super("colorcodes", "Zeigt alle Colorcodes an", "varo.colorcodes", "cc");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {

            sender.sendMessage("§7==============================");
            sender.sendMessage("&0 = §0Black                §f&1 = §1Dark Blue");
            sender.sendMessage("&2 = §2Dark Green      §f&3 = §3Dark Aqua");
            sender.sendMessage("&4 = §4Dark Red          §f&5 = §5Dark Purple");
            sender.sendMessage("&6 = §6Gold                  §f&7 = §7Gray");
            sender.sendMessage("&8 = §8Dark Gray        §f&9 = §9Blue");
            sender.sendMessage("&a = §aGreen               §f&b = §bAqua");
            sender.sendMessage("&c = §cRed                   §f&d = §dLight Purple");
            sender.sendMessage("&e = §eYellow               §f&f = §fWhite");
            sender.sendMessage("§c==============================");
            sender.sendMessage("&k = §kMagic§r§f                 &l = §lBold");
            sender.sendMessage("&m = §mStrikethrough§r§f  &n = §nUnderline");
            sender.sendMessage("&o = §oItalic§r§f                 &r = §rReset");
            sender.sendMessage("§c==============================");
        
	}
}
