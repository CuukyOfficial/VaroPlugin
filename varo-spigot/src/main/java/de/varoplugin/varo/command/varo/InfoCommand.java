package de.varoplugin.varo.command.varo;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

import de.varoplugin.varo.Main;
import de.varoplugin.varo.command.VaroCommand;
import de.varoplugin.varo.config.language.Messages;
import de.varoplugin.varo.player.VaroPlayer;
import io.github.almightysatan.slams.Placeholder;

public class InfoCommand extends VaroCommand {

	public InfoCommand() {
		super("info", "Zeigt Info ueber das Plugin & Server", null, "plugin", "server", "support");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		PluginDescriptionFile pdf = Main.getInstance().getDescription();

		Messages.CATEGORY_HEADER.send(sender, Placeholder.constant("category", "Info"));
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
		Messages.CATEGORY_FOOTER.send(sender, Placeholder.constant("category", "Info"));
	}
}