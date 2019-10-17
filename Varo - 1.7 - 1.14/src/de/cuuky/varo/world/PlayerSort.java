package de.cuuky.varo.world;

import java.util.ArrayList;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.messages.ConfigMessages;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.spawns.Spawn;
import de.cuuky.varo.version.VersionUtils;

public class PlayerSort {

	private boolean notFound;
	private ArrayList<VaroPlayer> finished;
	private ArrayList<Spawn> finishedSpawns;

	public PlayerSort() {
		finished = new ArrayList<VaroPlayer>();
		finishedSpawns = new ArrayList<>();

		for (VaroPlayer vp : VaroPlayer.getOnlinePlayer()) {
			if (!vp.getStats().isSpectator())
				continue;

			vp.getPlayer().teleport(vp.getPlayer().getWorld().getSpawnLocation());
			vp.sendMessage(Main.getPrefix() + ConfigMessages.SORT_SPECTATOR_TELEPORT.getValue());
			finished.add(vp);
		}

		for (Spawn spawn : Spawn.getSpawns()) {
			if (spawn.getPlayer() == null)
				continue;

			if (!spawn.getPlayer().isOnline())
				continue;

			setFullHealth(spawn.getPlayer().getPlayer());
			spawn.getPlayer().getPlayer().teleport(spawn.getLocation());
			spawn.getPlayer().sendMessage(Main.getPrefix() + ConfigMessages.SORT_OWN_HOLE.getValue());
			finished.add(spawn.getPlayer());
			finishedSpawns.add(spawn);
		}

		notFound = false;

		spawnLoop: for (Spawn spawn : Spawn.getSpawns()) {
			if (finishedSpawns.contains(spawn))
				continue spawnLoop;

			for (VaroPlayer player : VaroPlayer.getOnlinePlayer()) {
				if (finished.contains(player) || player.getStats().isSpectator())
					continue;

				setFullHealth(player.getPlayer());
				player.getPlayer().teleport(spawn.getLocation());
				player.sendMessage(Main.getPrefix() + ConfigMessages.SORT_NUMBER_HOLE.getValue().replace("%number%",
						String.valueOf(spawn.getNumber())));
				finished.add(player);
				finishedSpawns.add(spawn);

				if (player.getTeam() == null)
					continue spawnLoop;

				teamLoop: for (VaroPlayer teamPl : player.getTeam().getMember()) {
					if (!teamPl.isOnline() || finished.contains(teamPl) || teamPl.getStats().isSpectator())
						continue teamLoop;

					for (Spawn teamSpawn : Spawn.getSpawns()) {
						if (finishedSpawns.contains(teamSpawn))
							continue;

						setFullHealth(teamPl.getPlayer());
						teamPl.getPlayer().teleport(teamSpawn.getLocation());
						teamPl.sendMessage(Main.getPrefix() + ConfigMessages.SORT_NUMBER_HOLE.getValue()
								.replace("%number%", String.valueOf(teamSpawn.getNumber())));
						finished.add(teamPl);
						finishedSpawns.add(teamSpawn);
						continue teamLoop;
					}
				}

				continue spawnLoop;
			}
		}

		for (VaroPlayer vp : VaroPlayer.getOnlinePlayer())
			if (!finished.contains(vp))
				vp.sendMessage(Main.getPrefix() + ConfigMessages.SORT_NO_HOLE_FOUND.getValue());

		notFound = finished.size() != VersionUtils.getOnlinePlayer().size();
	}

	public boolean hasNotFound() {
		return this.notFound;
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
