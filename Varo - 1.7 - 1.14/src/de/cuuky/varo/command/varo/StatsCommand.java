package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.player.stats.stat.PlayerState;
import de.cuuky.varo.player.stats.stat.Rank;

public class StatsCommand extends VaroCommand {

	public StatsCommand() {
		super("stats", "Bearbeiten von Stats", "varo.stats", "stat");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if(args.length == 0) {
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + label + " stats §7<Spieler/All>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + label + " stats set §7<Spieler/All> <Stat> <Value>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + label + " stats remove §7<Spieler/All> <Stat>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + label + " stats reset §7<Spieler/All>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + label + " stats odvReset §7<Spieler/All> | Resettet alles außer Kills, Wins, Rank, Team und YT-Link");
			return;
		}

		String t = args.length == 1 ? args[0] : args[1];
		VaroPlayer target = VaroPlayer.getPlayer(t);
		if(target == null && !t.equalsIgnoreCase("all")) {
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + t + " §7nicht gefunden!");
			return;
		}

		if(args.length == 1) {
			sender.sendMessage(Main.getPrefix() + "Stats von " + Main.getColorCode() + target.getName() + "§7:");
			sender.sendMessage(Main.getPrefix());
			for(String stat : target.getStats().getStatsListed())
				sender.sendMessage(Main.getPrefix() + stat);
			return;
		}

		if(args[0].equalsIgnoreCase("set") || args[0].equalsIgnoreCase("remove")) {
			if(args.length == 2) {
				for(SetArgumentType all : SetArgumentType.values())
					sender.sendMessage(Main.getPrefix() + "STAT: " + Main.getColorCode() + all.getArg());
				return;
			}

			SetArgumentType type = SetArgumentType.getByName(args[2]);
			if(args[0].equalsIgnoreCase("set")) {
				if(args.length != 4) {
					sender.sendMessage(Main.getPrefix() + Main.getColorCode() + label + " stats set §7<Spieler/All> <Stat> <Value>");
					return;
				}
			} else {
				if(args.length != 3) {
					sender.sendMessage(Main.getPrefix() + Main.getColorCode() + label + " stats remove §7<Spieler/All> <Stat>");
					return;
				}
			}

			if(type == null) {
				sender.sendMessage(Main.getPrefix() + "Stat '" + Main.getColorCode() + args[2] + "§7' konnte nicht gefunden werden!");
				return;
			}

			if(target == null) {
				if(args[0].equalsIgnoreCase("set")) {
					for(VaroPlayer all : VaroPlayer.getVaroPlayer())
						if(!type.execute(args[3], all, sender)) {
							sender.sendMessage(Main.getPrefix() + "Vorgang für alle Spieler abgebrochen!");
							return;
						}

					sender.sendMessage(Main.getPrefix() + "Stat '" + Main.getColorCode() + type.getArg() + "§7' für alle Spieler erfolgreich auf '" + Main.getColorCode() + args[3] + "§7' gesetzt!");
				} else {
					for(VaroPlayer all : VaroPlayer.getVaroPlayer())
						type.remove(all);

					sender.sendMessage(Main.getPrefix() + "Stat '" + Main.getColorCode() + type.getArg() + "§7' für alle Spieler erfolgreich zurückgesetzt/entfernt!");
				}

				return;
			}

			if(args[0].equalsIgnoreCase("set")) {
				if(type.execute(args[3], target, sender))
					sender.sendMessage(Main.getPrefix() + "Stat '" + Main.getColorCode() + type.getArg() + "§7' für '" + Main.getColorCode() + target.getName() + "§7' erfolgreich auf '" + Main.getColorCode() + args[3] + "§7' gesetzt!");
			} else {
				type.remove(target);
				sender.sendMessage(Main.getPrefix() + "Stat '" + Main.getColorCode() + type.getArg() + "§7' für '" + Main.getColorCode() + target.getName() + "§7' erfolgreich zurückgesetzt/entfernt!");
			}
		} else if(args[0].equalsIgnoreCase("reset")) {
			if(target == null) {
				for(VaroPlayer all : VaroPlayer.getVaroPlayer())
					all.getStats().loadDefaults();

				sender.sendMessage(Main.getPrefix() + "Alle Spieler erfolgreich zurückgesetzt!");
				return;
			}

			target.getStats().loadDefaults();

			sender.sendMessage(Main.getPrefix() + "Spieler '" + Main.getColorCode() + target.getName() + "§7' erfolgreich zurückgesetzt!");
		} else if(args[0].equalsIgnoreCase("odvreset")) {
			if(target == null) {
				for(VaroPlayer all : VaroPlayer.getVaroPlayer()) {
					all.getStats().loadStartDefaults();
					all.getStats().setState(PlayerState.ALIVE);
					if(all.getTeam() != null)
						all.getTeam().removeMember(all);
				}

				sender.sendMessage(Main.getPrefix() + "Alle Spieler erfolgreich ODV-zurückgesetzt!");
				return;
			}

			target.getStats().loadStartDefaults();
			target.getStats().setState(PlayerState.ALIVE);
			if(target.getTeam() != null)
				target.getTeam().removeMember(target);

			sender.sendMessage(Main.getPrefix() + "Spieler '" + Main.getColorCode() + target.getName() + "§7' erfolgreich ODV-zurückgesetzt!");
		} else
			sender.sendMessage(Main.getPrefix() + "Not found! Type " + Main.getColorCode() + label + " stats §7for help.");
	}

	public enum SetArgumentType {
		KILLS("kills"),
		SESSIONS("sessions"),
		EPISODES_PLAYED("episodesplayed"),
		RANK("rank"),
		COUNTDOWN("countdown"),
		WINS("wins"),
		YOUTUBE_LINK("youtubelink"),
		PLAYER_STATE("playerstate");

		private String arg;

		private SetArgumentType(String arg) {
			this.arg = arg;
		}

		public String getArg() {
			return this.arg;
		}

		public boolean execute(String value, VaroPlayer vp, CommandSender sender) {
			try {
				switch(this) {
				case KILLS:
					vp.getStats().setKills(Integer.valueOf(value));
					break;
				case SESSIONS:
					vp.getStats().setSessions(Integer.valueOf(value));
					break;
				case EPISODES_PLAYED:
					vp.getStats().setSessionsPlayed(Integer.valueOf(value));
					break;
				case RANK:
					vp.setRank(new Rank(value));
					break;
				case COUNTDOWN:
					vp.getStats().setCountdown(Integer.valueOf(value));
					break;
				case PLAYER_STATE:
					vp.getStats().setState(PlayerState.getByName(value));
					break;
				case WINS:
					vp.getStats().setWins(Integer.valueOf(value));
					break;
				case YOUTUBE_LINK:
					vp.getStats().setYoutubeLink(value);
					break;
				}

				vp.update();
			} catch(Exception e) {
				sender.sendMessage(Main.getPrefix() + "§7Der Wert '" + Main.getColorCode() + value + "§7' §7konnte nicht für " + this.toString() + " gesetzt werden!");
				return false;
			}
			return true;
		}

		public void remove(VaroPlayer vp) {
			switch(this) {
			case KILLS:
				vp.getStats().setKills(0);
				break;
			case SESSIONS:
				vp.getStats().setSessions(ConfigEntry.SESSIONS_PER_DAY.getValueAsInt());
				break;
			case EPISODES_PLAYED:
				vp.getStats().setSessionsPlayed(0);
				break;
			case RANK:
				vp.setRank(null);
				break;
			case COUNTDOWN:
				vp.getStats().setCountdown(ConfigEntry.PLAY_TIME.getValueAsInt() * 60);
				break;
			case PLAYER_STATE:
				vp.getStats().setState(PlayerState.ALIVE);
				break;
			case WINS:
				vp.getStats().setWins(0);
				break;
			case YOUTUBE_LINK:
				vp.getStats().setYoutubeLink(null);
				break;
			}

			vp.update();
		}

		public static SetArgumentType getByName(String name) {
			for(SetArgumentType type : values())
				if(type.getArg().equalsIgnoreCase(name))
					return type;

			return null;
		}
	}
}