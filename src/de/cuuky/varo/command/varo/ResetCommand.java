package de.cuuky.varo.command.varo;

import de.cuuky.cfw.utils.JavaUtils;
import de.cuuky.cfw.version.VersionUtils;
import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.gui.VaroConfirmInventory;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ResetCommand extends VaroCommand {

	public ResetCommand() {
		super("reset", "Setzt ausgewaehlte Teile des Servers zurueck", "varo.reset");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo reset §7<Modifier1> <Modifier2> ...");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "Modifier 1: §7Resettet den kompletten Plugin Ordner");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "Modifier 2: §7Resettet logs + stats (keine configs)");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "Modifier 3: §7Loescht alle Welten");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "Example: §7/varo reset 2 3 - Loescht alle Stats und Welten");
			sender.sendMessage(Main.getPrefix() + "§cWarnung: §7Der Server wird nach dem Vorgang gestoppt");
			return;
		}

		if (sender instanceof Player)
			new VaroConfirmInventory(Main.getCuukyFrameWork().getAdvancedInventoryManager(), vp.getPlayer(), "§4Reset server?", (result) -> {
				if (!result) return;
				this.resetServer(sender, args);
			});
		else
			this.resetServer(sender, args);
	}

	private void resetServer(CommandSender sender, String[] args) {
		for (Player pl : VersionUtils.getVersionAdapter().getOnlinePlayers())
			pl.kickPlayer("§cRESET");

		Main.getDataManager().save();
		List<Integer> success = new ArrayList<Integer>();
		List<File> toDelete = new ArrayList<File>();
		for (String arg : args) {
			int mod;
			try {
				mod = Integer.valueOf(arg);
			} catch (NumberFormatException e) {
				sender.sendMessage(Main.getPrefix() + arg + " ist keine Zahl!");
				continue;
			}

			switch (mod) {
			case 1:
				toDelete.add(new File("plugins/Varo/"));
				break;
			case 2:
				toDelete.add(new File("plugins/Varo/logs/"));
				toDelete.add(new File("plugins/Varo/stats/"));
				break;
			case 3:
				for (World world : Bukkit.getWorlds()) {
					world.setAutoSave(false);
					for (Chunk chunk : world.getLoadedChunks())
						chunk.unload(false);
					Bukkit.unloadWorld(world, false);

					VersionUtils.getVersionAdapter().forceClearWorlds();
					JavaUtils.deleteDirectory(world.getWorldFolder());
				}
				break;
			default:
				sender.sendMessage(Main.getPrefix() + "Modifier §c" + arg + " §7nicht gefunden!");
				break;
			}

			success.add(mod);
		}

		if (!toDelete.isEmpty()) {
			Main.getDataManager().setDoSave(false);
			for (File file : toDelete) {
				if (file.isDirectory())
					JavaUtils.deleteDirectory(file);
				else
					file.delete();
			}
		}

		if (!success.isEmpty())
			Bukkit.getServer().shutdown();
	}
}