package de.cuuky.varo.recovery.recoveries;

import java.io.File;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;

import de.cuuky.cfw.recovery.FileZipper;
import de.cuuky.cfw.utils.JavaUtils;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.threads.LagCounter;

public class VaroBugreport extends FileZipper {

	private static List<String> exeptions;

	static {
		String[] exc = new String[] { "chatlogs.yml", "blocklogs.yml", "presets", "backups" };

		exeptions = Arrays.asList(exc);
	}

	private String discordBotToken;
	private boolean failed;

	public VaroBugreport() {
		super(new File("plugins/Varo/bugreports/bugreport-" + JavaUtils.getCurrentDateAsFileable() + ".zip"));

		boolean fileInit = Main.getDataManager() != null && Main.getDataManager().getConfigHandler() != null;
		if (fileInit) {
			this.discordBotToken = ConfigSetting.DISCORDBOT_TOKEN.getValueAsString();
			ConfigSetting.DISCORDBOT_TOKEN.setValue("hidden", true);
		}

		loadFiles();

		if (fileInit)
			ConfigSetting.DISCORDBOT_TOKEN.setValue(this.discordBotToken, true);
	}

	private void loadFiles() {
		ArrayList<File> files = getFiles("plugins/Varo");

		try {
			postInformation();
			files.add(new File("logs"));
			files.add(new File("server.properties"));

			zip(files, Paths.get(""));
		} catch (Exception e) {
			e.printStackTrace();
			this.failed = true;
		}
	}

	private void postInformation() {
		PluginDescriptionFile pdf = Main.getInstance().getDescription();
		Runtime r = Runtime.getRuntime();

		System.out.println(Main.getConsolePrefix() + "----------------------");
		System.out.println(Main.getConsolePrefix());
		System.out.println(Main.getConsolePrefix() + "Plugin Version: " + pdf.getVersion());
		System.out.println(Main.getConsolePrefix() + "Plugins enabled: " + Bukkit.getPluginManager().getPlugins().length);
		System.out.println(Main.getConsolePrefix() + "Server-Version: " + Bukkit.getVersion());
		System.out.println(Main.getConsolePrefix() + "System OS: " + System.getProperty("os.name"));
		System.out.println(Main.getConsolePrefix() + "System Version: " + System.getProperty("os.version"));
		System.out.println(Main.getConsolePrefix() + "Java Version: " + System.getProperty("java.version"));
		System.out.println(Main.getConsolePrefix() + "Total memory usage: " + (r.totalMemory() - r.freeMemory()) / 1048576 + "MB!");
		System.out.println(Main.getConsolePrefix() + "Total memory available: " + r.maxMemory() / 1048576 + "MB!");
		System.out.println(Main.getConsolePrefix() + "TPS: " + (double) Math.round(LagCounter.getTPS() * 100) / 100);
		System.out.println(Main.getConsolePrefix() + "Date: " + new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date()));
		System.out.println(Main.getConsolePrefix());
		System.out.println(Main.getConsolePrefix() + "----------------------");
	}

	private ArrayList<File> getFiles(String path) {
		File pathFile = new File(path);
		ArrayList<File> files = new ArrayList<>();

		for (File file : pathFile.listFiles()) {
			if (exeptions.contains(file.getName()))
				continue;

			if (file.isDirectory())
				files.addAll(getFiles(file.getPath()));
			else
				files.add(file);
		}

		return files;
	}

	public boolean hasFailed() {
		return this.failed;
	}
}