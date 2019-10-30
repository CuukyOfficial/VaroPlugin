package de.cuuky.varo.command.varo;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.update.Updater;
import de.cuuky.varo.update.Updater.UpdateResult;

public class UpdateCommand extends VaroCommand {
	
	private String urlDownload;
	private boolean pluginNameChanged;
	private String oldFileName;
	private boolean resetOldDirectory;
	
	public UpdateCommand() {
		super("update", "Installiert automatisch die neueste Version.", "varo.update");
	}
	
	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		
		if(args.length == 0 || (!args[0].equalsIgnoreCase("normal") && !args[0].equalsIgnoreCase("reset"))) {
			sender.sendMessage(Main.getPrefix() + "§7§lUpdate Befehle:");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo update normal §7- Updated die Version, aber behält alle Daten");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo update reset §7- Updated die Version und löscht alle Daten");
			return;
		}
		
		if (args[0].equalsIgnoreCase("normal")) {
			resetOldDirectory = false;
		} else if (args[0].equalsIgnoreCase("reset")) {
			resetOldDirectory = true;
		}
		
		this.urlDownload = "https://socialme.online/VaroPlugin/VaroAktuell.jar";
		this.pluginNameChanged = false;
		
		this.oldFileName = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getName();
		
		if (!this.oldFileName.equals(Main.getInstance().getDescription().getName() + ".jar")) {
			this.pluginNameChanged = true;
		}
		
		Main.getDataManager().setDoSave(false);
		
		try {
			Updater updater = Main.getUpdater();
			if(updater.getResult() == UpdateResult.UPDATE_AVAILABLE) {
				sender.sendMessage(Main.getPrefix() + "§7Update wird installiert.");
				update(sender);
			} else {
				sender.sendMessage(Main.getPrefix() + "§7Das Plugin ist bereits auf dem  neuesten Stand!");
			}
		} catch(NumberFormatException e) {
			sender.sendMessage(Main.getPrefix() + "§cEs gab einen Fehler beim Update-Prüfen.");
		}
		
	}
	
	private void update(CommandSender sender) {
		//Step 1: Download new Version
		try {
			URL download = new URL(this.urlDownload);
			BufferedInputStream in = null;
			FileOutputStream fout = null;
			
			try {
				System.out.println("Starte Download");
				in = new BufferedInputStream(download.openStream());
				fout = new FileOutputStream("plugins" + System.getProperty("file.separator") + Main.getInstance().getDescription().getName() + ".jar");
				
				final byte data[] = new byte[1024];
				int count;
				while ((count = in.read(data, 0, 1024)) != -1) {
					fout.write(data, 0, count);
				}
				
			} catch (IOException e) {
				sender.sendMessage(Main.getPrefix() + "§cEs bgab einen kritischen Fehler beim Download des Plugins.");
				sender.sendMessage(Main.getPrefix() + "§7Empfohlen wird ein manuelles Updaten des Plugins: https://www.spigotmc.org/resources/71075/");
				System.out.println("Es gab einen kritischen Fehler beim Download des Plugins.");
				System.out.println("---------- Stack Trace ----------");
				e.printStackTrace();
				System.out.println("---------- Stack Trace ----------");
				return;
			} finally {
				if (in != null) {
					in.close();
				}
				if (fout != null) {
					fout.close();
				}
			}
			sender.sendMessage(Main.getPrefix() + "Update wurde erfolgreich installiert.");
			System.out.println("Erfolgreich Update installiert.");
			
		} catch (IOException e) {
			sender.sendMessage(Main.getPrefix() + "§cEs bgab einen kritischen Fehler beim Download des Plugins.");
			sender.sendMessage(Main.getPrefix() + "§7Empfohlen wird ein manuelles Updaten des Plugins: https://www.spigotmc.org/resources/71075/");
			System.out.println("Es gab einen kritischen Fehler beim Download des Plugins.");
			System.out.println("---------- Stack Trace ----------");
			e.printStackTrace();
			System.out.println("---------- Stack Trace ----------");
			return;
		}
		
		
		//Step 2: Deleting old directory if wanted
		if (resetOldDirectory) {
			System.out.println("Das Verzeichnis der alten Pluginversion wird gelöscht.");
			File directory = new File("plugins/Varo/");
			deleteDirectory(directory);
		}
		
		
		//Step 3: Deleting old Version if existing
		if (this.pluginNameChanged) {
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