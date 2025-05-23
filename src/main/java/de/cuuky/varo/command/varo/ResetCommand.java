package de.cuuky.varo.command.varo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.player.VaroPlayer;
import de.varoplugin.cfw.inventory.inbuilt.ConfirmInventory;
import de.varoplugin.cfw.utils.JavaUtils;

public class ResetCommand extends VaroCommand {

	public ResetCommand() {
		super("reset", "Setzt ausgewaehlte Teile des Servers zurueck", "varo.reset");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " reset §7<Modifier1> <Modifier2> ...");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "Modifier 1: §7Resettet den kompletten Plugin Ordner");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "Modifier 2: §7Resettet logs + stats (keine configs)");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "Modifier 3: §7Loescht alle Welten");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "Example: §7/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " reset 2 3 - Loescht alle Stats und Welten");
			sender.sendMessage(Main.getPrefix() + "§cWarnung: §7Der Server wird nach dem Vorgang gestoppt");
			return;
		}

		if (sender instanceof Player)
			new ConfirmInventory(Main.getInventoryManager(), vp.getPlayer(), "§4Reset server?", (result) -> {
				if (!result) return;
				this.resetServer(args);
			});
		else
			this.resetServer(args);
	}

	private void resetServer(String[] args) {
		for (Player pl : Bukkit.getOnlinePlayers())
			pl.kickPlayer("§cRESET");

		// Wait to ensure the players are actually disconnected
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
		    Main.getDataManager().saveSync();
	        List<Integer> success = new ArrayList<>();
	        List<File> toDelete = new ArrayList<>();
	        for (String arg : args) {
	            int mod;
	            try {
	                mod = Integer.parseInt(arg);
	            } catch (NumberFormatException e) {
	                Bukkit.getConsoleSender().sendMessage(Main.getPrefix() + arg + " ist keine Zahl!");
	                continue;
	            }

	            switch (mod) {
	            case 1:
	                toDelete.add(new File("plugins/Varo/"));
	                Main.getDataManager().setDoSave(false);
	                break;
	            case 2:
	                toDelete.add(new File("plugins/Varo/logs/"));
	                toDelete.add(new File("plugins/Varo/stats/"));
	                Main.getDataManager().setDoSave(false);
	                break;
	            case 3:
	                Main.getVaroGame().setLobby(null);
	                for (World world : Bukkit.getWorlds())
	                    if (world.getWorldFolder() != null) {
	                        Bukkit.getConsoleSender().sendMessage(Main.getPrefix() + "Deleting world " + world.getName());
	                        toDelete.add(world.getWorldFolder());
	                    }
	                break;
	            default:
	                Bukkit.getConsoleSender().sendMessage(Main.getPrefix() + "Modifier §c" + arg + " §7nicht gefunden!");
	                break;
	            }

	            success.add(mod);
	        }
	        
	        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
	            for (File file : toDelete)
	                JavaUtils.deleteDirectory(file);
            }));

	        if (!success.isEmpty())
	            Bukkit.getServer().shutdown();
		}, 1);
	}
}