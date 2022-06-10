package de.cuuky.varo.command.varo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;

public class InfoCommand extends VaroCommand {
	private static final String[] subCommands = null;
	public InfoCommand() {
		super("info", "Zeigt Info ueber das Plugin & Server", null, subCommands, "plugin", "server", "support");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		PluginDescriptionFile pdf = Main.getInstance().getDescription();

		sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_HELP_HEADER.getValue(vp).replace("%category%", "Info"));
		sender.sendMessage(Main.getPrefix());
		sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "§lVaro Plugin§7:");
		sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "Author§7: " + pdf.getAuthors().get(0));
		sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "Authors§7: " + Main.getContributors());
		sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "Version§7: " + pdf.getVersion());
		sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "Server-Version§7: " + Bukkit.getServer().getVersion());
		sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "System-OS§7: " + System.getProperty("os.name"));
		sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "System-Version§7: " + System.getProperty("os.version"));
		sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "Java-Version§7: " + System.getProperty("java.version"));
		sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "Date§7: " + new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date()));
		sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "License§7: GNU AGPL v3");
		sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "Source code§7: https://github.com/CuukyOfficial/VaroPlugin");
		sender.sendMessage(Main.getPrefix());
		sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_HELP_FOOTER.getValue(vp));
	}
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		ArrayList<String> list = new ArrayList<>();
		if (args.length == 2 && subCommands != null) {
			List<String> subCommands = Arrays.asList(this.subCommands);
			list.addAll(subCommands);
		}
		ArrayList<String> completerList = new ArrayList<>();
		String curentarg = args[args.length - 1].toLowerCase();
		for (String s : list) {
			String s1 = s.toLowerCase();
			if (s1.startsWith(curentarg)) {
				completerList.add(s);
			}
		}
		return completerList;
	}
}