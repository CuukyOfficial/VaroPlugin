package de.cuuky.varo.command.varo;

import java.io.File;

import de.cuuky.varo.data.DataManager;
import de.cuuky.varo.spigot.FileDownloader;
import de.cuuky.varo.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.entity.player.VaroPlayer;

public class UpdateCommand extends VaroCommand {

	private boolean pluginNameChanged;
	private String oldFileName;
	private boolean resetOldDirectory;

	public UpdateCommand() {
		super("update", "Installiert automatisch die neueste Version.", "varo.update");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		
		Utils.UpdateResult result;
		String updateVersion;
		
		Object[] updater = Utils.checkForUpdates();
		result = (Utils.UpdateResult) updater[0];
		updateVersion = (String) updater[1];

		if(args.length == 0 || (!args[0].equalsIgnoreCase("normal") && !args[0].equalsIgnoreCase("reset"))) {
			
			if (result == Utils.UpdateResult.UPDATE_AVAILABLE) {
				sender.sendMessage(Main.getPrefix() + "§c Es existiert eine neuere Version: " + updateVersion);
				sender.sendMessage("");
				sender.sendMessage(Main.getPrefix() + "§7§lUpdate Befehle:");
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo update normal §7- Updated die Version, aber behält alle Daten");
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo update reset §7- Updated die Version und löscht alle Daten");
			} else {
				sender.sendMessage(Main.getPrefix() + "Es wurde keine neue Version gefunden. Sollte dies ein Fehler sein, aktualisiere manuell.");
			}
			return;
		}

		if(args[0].equalsIgnoreCase("normal")) {
			resetOldDirectory = false;
		} else if(args[0].equalsIgnoreCase("reset")) {
			resetOldDirectory = true;
		}

		this.pluginNameChanged = false;

		this.oldFileName = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getName();

		if(!this.oldFileName.equals(Main.getInstance().getDescription().getName() + ".jar")) {
			this.pluginNameChanged = true;
		}

		DataManager.getInstance().setDoSave(false);
		
		if (result == Utils.UpdateResult.UPDATE_AVAILABLE) {
			sender.sendMessage(Main.getPrefix() + "§7Update wird installiert...");
			update(sender);
		} else {
			sender.sendMessage(Main.getPrefix() + "§7Das Plugin ist bereits auf dem neuesten Stand!");

		}

	}

	private void update(CommandSender sender) {
		// Step 1: Download new Version
		try {
			FileDownloader fd = new FileDownloader("http://api.spiget.org/v2/resources/71075/download", "plugins/Varo.jar");

			sender.sendMessage(Main.getPrefix() + "Starte Download...");

			fd.startDownload();
		} catch(Exception e) {
			sender.sendMessage(Main.getPrefix() + "§cEs bgab einen kritischen Fehler beim Download des Plugins.");
			sender.sendMessage(Main.getPrefix() + "§7Empfohlen wird ein manuelles Updaten des Plugins: https://www.spigotmc.org/resources/71075/");
			System.out.println("Es gab einen kritischen Fehler beim Download des Plugins.");
			System.out.println("---------- Stack Trace ----------");
			e.printStackTrace();
			System.out.println("---------- Stack Trace ----------");
			return;
		}

		sender.sendMessage(Main.getPrefix() + "Update erfolgreich installiert");

		// Step 2: Deleting old directory if wanted
		if(resetOldDirectory) {
			System.out.println("Das Verzeichnis der alten Pluginversion wird gelöscht.");
			File directory = new File("plugins/Varo/");
			deleteDirectory(directory);
		}

		// Step 3: Deleting old Version if existing
		if(this.pluginNameChanged) {
			System.out.println("Da sich der Pluginname verändert hat, wird die alte Pluginversion gelöscht.");
			File oldPlugin = new File("plugins/" + this.oldFileName);
			oldPlugin.delete();
		}

		Bukkit.getServer().shutdown();
	}

	private void deleteDirectory(File file) {
		for(File listFile : file.listFiles()) {
			if(listFile.isDirectory())
				deleteDirectory(listFile);

			listFile.delete();
		}

		file.delete();
	}
}