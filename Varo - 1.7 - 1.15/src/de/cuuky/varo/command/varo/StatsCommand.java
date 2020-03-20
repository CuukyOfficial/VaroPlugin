package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.stat.PlayerState;
import de.cuuky.varo.entity.player.stats.stat.Rank;

public class StatsCommand extends VaroCommand {

	public enum SetArgumentType {
		
		ADMIN_IGNORE("adminignore"),
		COUNTDOWN("countdown"),
		EPISODES_PLAYED("episodesplayed"),
		KILLS("kills"),
		PLAYER_STATE("playerstate"),
		RANK("rank"),
		SESSIONS("sessions"),
		WINS("wins"),
		YOUTUBE_LINK("youtubelink");

		private String arg;

		private SetArgumentType(String arg) {
			this.arg = arg;
		}

		public boolean execute(String value, VaroPlayer vp, CommandSender sender) {
			try {
				switch(this) {
				case ADMIN_IGNORE:
					vp.setAdminIgnore(Boolean.valueOf(value));
					break;
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
				sender.sendMessage(Main.getPrefix() + "§7Der Wert '" + Main.getColorCode() + value + "§7' §7konnte nicht fuer " + this.toString() + " gesetzt werden!");
				return false;
			}
			return true;
		}

		public String getArg() {
			return this.arg;
		}

		public void remove(VaroPlayer vp) {
			switch(this) {
			case ADMIN_IGNORE:
				vp.setAdminIgnore(false);
				break;
			case KILLS:
				vp.getStats().setKills(0);
				break;
			case SESSIONS:
				vp.getStats().setSessions(ConfigSetting.SESSIONS_PER_DAY.getValueAsInt());
				break;
			case EPISODES_PLAYED:
				vp.getStats().setSessionsPlayed(0);
				break;
			case RANK:
				vp.setRank(null);
				break;
			case COUNTDOWN:
				vp.getStats().setCountdown(ConfigSetting.PLAY_TIME.getValueAsInt() * 60);
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

	public StatsCommand() {
		super("stats", "Bearbeiten von Stats", "varo.stats", "stat");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if(args.length == 0) {
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + label + " stats §7<Spieler/@a>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + label + " stats set §7<Spieler/@a> <Stat> <Value>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + label + " stats remove §7<Spieler/@a> <Stat>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + label + " stats reset §7<Spieler/@a>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + label + " stats odvReset §7<Spieler/@a> | Resettet alles ausser Kills, Wins, Rank, Team und YT-Link");
			return;
		}

		String t = args.length == 1 ? args[0] : args[1];
		VaroPlayer target = VaroPlayer.getPlayer(t);
		if(target == null && !t.equalsIgnoreCase("@a")) {
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
					sender.sendMessage(Main.getPrefix() + Main.getColorCode() + label + " stats set §7<Spieler/@a> <Stat> <Value>");
					return;
				}
			} else {
				if(args.length != 3) {
					sender.sendMessage(Main.getPrefix() + Main.getColorCode() + label + " stats remove §7<Spieler/@a> <Stat>");
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
							sender.sendMessage(Main.getPrefix() + "Vorgang fuer alle Spieler abgebrochen!");
							return;
						}

					sender.sendMessage(Main.getPrefix() + "Stat '" + Main.getColorCode() + type.getArg() + "§7' fuer alle Spieler erfolgreich auf '" + Main.getColorCode() + args[3] + "§7' gesetzt!");
				} else {
					for(VaroPlayer all : VaroPlayer.getVaroPlayer())
						type.remove(all);

					sender.sendMessage(Main.getPrefix() + "Stat '" + Main.getColorCode() + type.getArg() + "§7' fuer alle Spieler erfolgreich zurueckgesetzt/entfernt!");
				}

				return;
			}

			if(args[0].equalsIgnoreCase("set")) {
				if(type.execute(args[3], target, sender))
					sender.sendMessage(Main.getPrefix() + "Stat '" + Main.getColorCode() + type.getArg() + "§7' fuer '" + Main.getColorCode() + target.getName() + "§7' erfolgreich auf '" + Main.getColorCode() + args[3] + "§7' gesetzt!");
			} else {
				type.remove(target);
				sender.sendMessage(Main.getPrefix() + "Stat '" + Main.getColorCode() + type.getArg() + "§7' fuer '" + Main.getColorCode() + target.getName() + "§7' erfolgreich zurueckgesetzt/entfernt!");
			}
		} else if(args[0].equalsIgnoreCase("reset")) {
			if(target == null) {
				for(VaroPlayer all : VaroPlayer.getVaroPlayer())
					all.getStats().loadDefaults();

				sender.sendMessage(Main.getPrefix() + "Alle Spieler erfolgreich zurueckgesetzt!");
				return;
			}

			target.getStats().loadDefaults();

			sender.sendMessage(Main.getPrefix() + "Spieler '" + Main.getColorCode() + target.getName() + "§7' erfolgreich zurueckgesetzt!");
		} else if(args[0].equalsIgnoreCase("odvreset")) {
			if(target == null) {
				for(VaroPlayer all : VaroPlayer.getVaroPlayer()) {
					all.getStats().loadStartDefaults();
					all.getStats().setState(PlayerState.ALIVE);
					if(all.getTeam() != null)
						all.getTeam().removeMember(all);
				}

				sender.sendMessage(Main.getPrefix() + "Alle Spieler erfolgreich ODV-zurueckgesetzt!");
				return;
			}

			target.getStats().loadStartDefaults();
			target.getStats().setState(PlayerState.ALIVE);
			if(target.getTeam() != null)
				target.getTeam().removeMember(target);

			sender.sendMessage(Main.getPrefix() + "Spieler '" + Main.getColorCode() + target.getName() + "§7' erfolgreich ODV-zurueckgesetzt!");
		} else
			sender.sendMessage(Main.getPrefix() + "Not found! Type " + Main.getColorCode() + label + " stats §7for help.");
	}
}