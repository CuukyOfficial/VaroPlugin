package de.cuuky.varo.entity.player.stats.stat.inventory;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;

import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.serialize.identifier.VaroSerializeField;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;
import de.cuuky.varo.utils.JavaUtils;

public class VaroSaveable implements VaroSerializeable {

	public enum SaveableType implements VaroSerializeable {
		@VaroSerializeField(enumValue = "CHEST")
		CHEST,
		
		@VaroSerializeField(enumValue = "FURNACE")
		FURNANCE;

		@Override
		public void onDeserializeEnd() {}

		@Override
		public void onSerializeStart() {}
	}

	private static ArrayList<VaroSaveable> saveables;

	static {
		saveables = new ArrayList<>();
	}

	@VaroSerializeField(path = "id")
	private int id;
	
	@VaroSerializeField(path = "playerId")
	private int playerId;
	
	@VaroSerializeField(path = "type")
	private SaveableType type;
	
	@VaroSerializeField(path = "blockLocation")
	private Location blockLocation;
	
	private VaroPlayer player;
	private Block block;

	public VaroSaveable() {
		saveables.add(this);
	}

	public VaroSaveable(SaveableType type, Location location, VaroPlayer player) {
		this.type = type;
		this.block = location.getBlock();
		this.id = generateId();
		this.player = player;
		player.getStats().addSaveable(this);

		saveables.add(this);
	}

	private int generateId() {
		int id = JavaUtils.randomInt(1000, 9999999);
		while(getSaveable(id) != null)
			generateId();

		return id;
	}

	public boolean canModify(VaroPlayer player) {
		if(this.player.getTeam() == null && !player.equals(this.player))
			return false;

		if(this.player.getTeam() != null && !this.player.getTeam().isMember(player))
			return false;

		return true;
	}

	public boolean holderDead() {
		if(this.player.getTeam() == null && this.player.getStats().isAlive())
			return false;

		if(this.player.getTeam() != null && !this.player.getTeam().isDead())
			return false;

		return true;
	}

	@Override
	public void onDeserializeEnd() {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				player = VaroPlayer.getPlayer(playerId);
				block = blockLocation.getBlock();
			}
		}, 1);
	}

	@Override
	public void onSerializeStart() {
		this.playerId = player.getId();
		this.blockLocation = block.getLocation();
	}

	public void remove() {
		player.getStats().removeSaveable(this);
		saveables.remove(this);
	}

	public static VaroSaveable getByLocation(Location loc) {
		for(VaroSaveable save : VaroSaveable.getSaveables()) {
			Location loc1 = save.getBlock().getLocation();
			if(loc1.getBlockX() == loc.getBlockX() && loc1.getBlockY() == loc.getBlockY() && loc.getBlockZ() == loc1.getBlockZ() && loc1.getWorld().equals(loc.getWorld()))
				return save;
		}

		return null;
	}

	public static VaroSaveable getSaveable(int id) {
		for(VaroSaveable save : saveables) {
			if(save.getId() != id)
				continue;

			return save;
		}

		return null;
	}
	
	public Block getBlock() {
		return block;
	}

	public int getId() {
		return id;
	}

	public VaroPlayer getPlayer() {
		return player;
	}

	public SaveableType getType() {
		return type;
	}

	public static ArrayList<VaroSaveable> getSaveable(VaroPlayer player) {
		return player.getStats().getSaveables();
	}

	public static ArrayList<VaroSaveable> getSaveables() {
		return saveables;
	}
}