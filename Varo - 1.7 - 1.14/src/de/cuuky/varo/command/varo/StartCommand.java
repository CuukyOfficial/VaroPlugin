package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.game.Game;
import de.cuuky.varo.player.VaroPlayer;

public class StartCommand extends VaroCommand {

	public StartCommand() {
		super("start", "Startet das Varo", "varo.start");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		Game game = Main.getGame();
		if (game.isStarting()) {
			sender.sendMessage(Main.getPrefix() + "Das Spiel startet bereits!");
			return;
		}

		if (game.isStarted()) {
			sender.sendMessage(Main.getPrefix() + "Das Spiel wurde bereits gestartet!");
			return;
		}

//		if (!isInternetAvailable()) {
//			sender.sendMessage(Main.getPrefix() + "Es ist Internet erforderlich, um das Spiel zu starten!");
//			return;
//		}

		game.start();
		sender.sendMessage(Main.getPrefix() + "Spiel erfolgreich gestartet!");
	}

//	private boolean isInternetAvailable() {
//		Enumeration<NetworkInterface> interfaces = null;
//		try {
//			interfaces = NetworkInterface.getNetworkInterfaces();
//		} catch (SocketException e) {
//			e.printStackTrace();
//		}
//		while (interfaces.hasMoreElements()) {
//			NetworkInterface networkInterface = interfaces.nextElement();
//			try {
//				if (networkInterface.isUp())
//					return true;
//			} catch (SocketException e) {
//			}
//		}
//
//		return false;
//	}
}
