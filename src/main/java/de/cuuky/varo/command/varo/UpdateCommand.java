package de.cuuky.varo.command.varo;

import java.io.File;
import java.net.URLDecoder;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.spigot.FileDownloader;
import de.cuuky.varo.spigot.VaroUpdateResultSet;
import de.cuuky.varo.spigot.VaroUpdateResultSet.UpdateResult;

public class UpdateCommand extends VaroCommand {

	public UpdateCommand() {
		super("update", "Installiert automatisch die neueste Version", "varo.update");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		VaroUpdateResultSet resultSet = Main.getVaroUpdater().checkForUpdates();
		UpdateResult result = resultSet.getUpdateResult();
		String updateVersion = resultSet.getVersionName();

		if (args.length == 0 || (!args[0].equalsIgnoreCase("normal") && !args[0].equalsIgnoreCase("reset"))) {
			if (result == UpdateResult.UPDATE_AVAILABLE) {
				sender.sendMessage(Main.getPrefix() + "§c Es existiert eine neuere Version: " + updateVersion);
				sender.sendMessage("");
				sender.sendMessage(Main.getPrefix() + "§7§lUpdate Befehle:");
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " update normal §7- Updated die Version, aber behält alle Daten");
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " update reset §7- Updated die Version und löscht alle Daten");
				sender.sendMessage(Main.getPrefix() + "§cWichtig: §7Der Updater spiget.org hat manchmal eine alte Version als Download. Falls sich nach dem Update die Version nicht geändert haben sollte, musst du manuell updaten.");
			} else if (result == UpdateResult.MAJOR_UPDATE_AVAILABLE) {
                sender.sendMessage(Main.getPrefix() + "§c Es existiert eine neuere Version: " + updateVersion);
                sender.sendMessage("");
                sender.sendMessage(Main.getPrefix() + "§7§lUpdate Befehle:");
                sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " update reset §7- Updated die Version und löscht alle Daten");
                sender.sendMessage(Main.getPrefix() + "§cWichtig: §7Der Updater spiget.org hat manchmal eine alte Version als Download. Falls sich nach dem Update die Version nicht geändert haben sollte, musst du manuell updaten.");
            } else {
				sender.sendMessage(Main.getPrefix() + "Es wurde keine neue Version gefunden. Sollte dies ein Fehler sein, aktualisiere manuell.");
			}
			return;
		}

		boolean resetOldDirectory = false;
		if (args[0].equalsIgnoreCase("normal")) {
		    if (result == UpdateResult.MAJOR_UPDATE_AVAILABLE) {
		        sender.sendMessage(Main.getPrefix() + "§cDieser Befehl ist für dieses Update nicht verfügbar! Nutze §l/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " update reset §r§cum alle Daten zu löschen.");
		        return;
		    }
			resetOldDirectory = false;
		} else if (args[0].equalsIgnoreCase("reset")) {
			resetOldDirectory = true;
		}

		String oldFileName = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getName();

		try {
			oldFileName = URLDecoder.decode(oldFileName, "UTF-8");
		} catch (Exception e) {
			oldFileName = oldFileName.replace("%20", " ");
		}

		Main.getDataManager().setDoSave(false);

		if (result == UpdateResult.UPDATE_AVAILABLE || result == UpdateResult.MAJOR_UPDATE_AVAILABLE) {
			sender.sendMessage(Main.getPrefix() + "§7Update wird installiert...");
			sender.sendMessage(Main.getPrefix() + "§7Backup wird erstellt...");
			
			String _oldFileName = oldFileName;
			boolean _resetOldDirectory = resetOldDirectory;
			Main.getDataManager().createBackup(backup -> {
			    if (backup == null) {
			        sender.sendMessage(Main.getPrefix() + "§cFehler beim erstellen des Backups");
			        return;
			    }

			    sender.sendMessage(Main.getPrefix() + "§7Unter Umstaenden wird nicht die neuste Version heruntergeladen, sollte dies der Fall sein, installieren die neue Version bitte manuell.");
	            update(sender, _oldFileName, _resetOldDirectory);
			});
		} else {
			sender.sendMessage(Main.getPrefix() + "§7Das Plugin ist bereits auf dem neuesten Stand!");
		}
	}
	
	private static void update(CommandSender sender, String oldFileName, boolean resetOldDirectory) {
        // Step 1: Download new Version
        try {
            FileDownloader fd = new FileDownloader("https://api.spiget.org/v2/resources/" + Main.getResourceId() + "/download", "plugins/update/" + oldFileName);

            sender.sendMessage(Main.getPrefix() + "Starte Download...");

            fd.startDownload();
        } catch (Exception e) {
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
        if (resetOldDirectory) {
            System.out.println("Das Verzeichnis der alten Pluginversion wird geloescht.");
            File directory = new File("plugins/Varo/");
            deleteDirectory(directory);
        }

        Bukkit.getServer().shutdown();
    }

	private static void deleteDirectory(File file) {
        for (File listFile : file.listFiles()) {
            if (listFile.isDirectory())
                deleteDirectory(listFile);

            listFile.delete();
        }

        file.delete();
    }
}