package de.cuuky.varo.world;

import java.util.ArrayList;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.config.messages.ConfigMessages;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.spawns.Spawn;

public class PlayerSort {

	private boolean notFound;
	private boolean tooSmallTeamSpawns;
	private ArrayList<VaroPlayer> players;
	private ArrayList<Spawn> spawns;

	public PlayerSort() {
		players = VaroPlayer.getOnlinePlayer();
		spawns = Spawn.getSpawnsClone();

		for(VaroPlayer vp : players) {
			if(!vp.getStats().isSpectator()) {
				continue;
			}
			vp.getPlayer().teleport(vp.getPlayer().getWorld().getSpawnLocation());
			vp.sendMessage(Main.getPrefix() + ConfigMessages.SORT_SPECTATOR_TELEPORT.getValue());
			players.remove(vp);
		}

		for(Spawn spawn : spawns) {
			if(spawn.getPlayer() == null) {
				continue;
			} else if(!spawn.getPlayer().isOnline()) {
				continue;
			} else {
				setFullHealth(spawn.getPlayer().getPlayer());
				spawn.getPlayer().getPlayer().teleport(spawn.getLocation());
				spawn.getPlayer().sendMessage(Main.getPrefix() + ConfigMessages.SORT_OWN_HOLE.getValue());
				players.remove(spawn.getPlayer());
				spawns.remove(spawn);
			}
		}

		tooSmallTeamSpawns = false;

		while(spawns.size() > 0) {
			if(players.size() <= 0) {
				break;
			}

			VaroPlayer player = players.get(0);
			Spawn spawn = spawns.get(0);
			setFullHealth(player.getPlayer());
			player.getPlayer().teleport(spawn.getLocation());
			player.sendMessage(Main.getPrefix() + ConfigMessages.SORT_NUMBER_HOLE.getValue().replace("%number%", String.valueOf(spawn.getNumber())));
			players.remove(0);
			spawns.remove(0);

			if(player.getTeam() == null) {
				continue;
			}

			int playerTeamRegistered = 1;
			for(VaroPlayer teamPlayer : player.getTeam().getMember()) {
				if(spawns.size() <= 0) {
					break;
				}

				if(ConfigEntry.TEAM_PLACE_SPAWN.getValueAsInt() > 0) {
					if(playerTeamRegistered < ConfigEntry.TEAM_PLACE_SPAWN.getValueAsInt()) {
						if(players.contains(teamPlayer)) {
							setFullHealth(teamPlayer.getPlayer());
							teamPlayer.getPlayer().teleport(spawns.get(0).getLocation());
							teamPlayer.sendMessage(Main.getPrefix() + ConfigMessages.SORT_NUMBER_HOLE.getValue().replace("%number%", String.valueOf(spawns.get(0).getNumber())));
							players.remove(teamPlayer);
						}
						spawns.remove(0);
						playerTeamRegistered++;
					} else {
						tooSmallTeamSpawns = true;
						players.remove(teamPlayer);
						teamPlayer.sendMessage(Main.getPrefix() + ConfigMessages.SORT_NO_HOLE_FOUND_TEAM.getValue());
					}
				} else if(players.contains(teamPlayer)) {
					setFullHealth(teamPlayer.getPlayer());
					teamPlayer.getPlayer().teleport(spawns.get(0).getLocation());
					teamPlayer.sendMessage(Main.getPrefix() + ConfigMessages.SORT_NUMBER_HOLE.getValue().replace("%number%", String.valueOf(spawns.get(0).getNumber())));
					players.remove(teamPlayer);
					spawns.remove(0);
				}
			}
		}

		notFound = false;
		for(VaroPlayer vp : players) {
			vp.sendMessage(Main.getPrefix() + ConfigMessages.SORT_NO_HOLE_FOUND.getValue());
			notFound = true;
		}

	}

	public boolean hasNotFound() {
		return this.notFound;
	}

	public boolean hasTeamNotFound() {
		return this.tooSmallTeamSpawns;
	}

	private void setFullHealth(Player p) {
		p.setGameMode(GameMode.SURVIVAL);
		p.setHealth(20);
		p.setFoodLevel(20);
		p.getInventory().clear();
		p.getInventory().setArmorContents(new ItemStack[] {});
		p.setExp(0);
		p.setLevel(0);
	}
}
