package de.cuuky.varo.game.threads;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.Main;
import de.cuuky.varo.api.VaroAPI;
import de.cuuky.varo.api.event.events.game.VaroStartEvent;
import de.cuuky.varo.configuration.config.ConfigEntry;
import de.cuuky.varo.configuration.messages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.game.VaroGame;
import de.cuuky.varo.game.start.ProtectionTime;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.game.world.border.decrease.BorderDecreaseMinuteTimer;
import de.cuuky.varo.logger.logger.EventLogger.LogType;
import de.cuuky.varo.utils.JavaUtils;
import de.cuuky.varo.utils.varo.VaroUtils;
import de.cuuky.varo.version.VersionUtils;
import de.cuuky.varo.version.types.Sounds;

public class VaroStartThread implements Runnable {

	private VaroGame game;
	private int startcountdown;
	
	public VaroStartThread() {
		this.game = Main.getVaroGame();
		
		loadVaraibles();
	}
	
	private void fillChests() {
		if(!ConfigEntry.RANDOM_CHEST_FILL_RADIUS.isIntActivated())
			return;

		int radius = ConfigEntry.RANDOM_CHEST_FILL_RADIUS.getValueAsInt();
		Location loc = VaroUtils.getMainWorld().getSpawnLocation().clone().add(radius, radius, radius);
		Location loc2 = VaroUtils.getMainWorld().getSpawnLocation().clone().add(-radius, -radius, -radius);

		int itemsPerChest = ConfigEntry.RANDOM_CHEST_MAX_ITEMS_PER_CHEST.getValueAsInt();
		ArrayList<ItemStack> chestItems = Main.getDataManager().getListManager().getChestItems().getItems();
		for(Block block : getBlocksBetweenPoints(loc, loc2)) {
			if(!(block.getState() instanceof Chest))
				continue;

			Chest chest = (Chest) block.getState();
			chest.getBlockInventory().clear();
			for(int i = 0; i < itemsPerChest; i++) {
				int random = JavaUtils.randomInt(0, chest.getBlockInventory().getSize() - 1);
				while(chest.getBlockInventory().getContents().length != chest.getBlockInventory().getSize())
					random = JavaUtils.randomInt(0, chest.getBlockInventory().getSize() - 1);

				chest.getBlockInventory().setItem(random, chestItems.get(JavaUtils.randomInt(0, chestItems.size() - 1)));
			}
		}

		Bukkit.broadcastMessage("§7Alle Kisten um den " + Main.getColorCode() + "Spawn §7wurden " + Main.getColorCode() + "aufgefÃ¼llt§7!");
	}

	private List<Block> getBlocksBetweenPoints(Location l1, Location l2) {
		List<Block> blocks = new ArrayList<>();
		int topBlockX = (Math.max(l1.getBlockX(), l2.getBlockX()));
		int bottomBlockX = (Math.min(l1.getBlockX(), l2.getBlockX()));
		int topBlockY = (Math.max(l1.getBlockY(), l2.getBlockY()));
		int bottomBlockY = (Math.min(l1.getBlockY(), l2.getBlockY()));
		int topBlockZ = (Math.max(l1.getBlockZ(), l2.getBlockZ()));
		int bottomBlockZ = (Math.min(l1.getBlockZ(), l2.getBlockZ()));

		for(int x = bottomBlockX; x <= topBlockX; x++) {
			for(int y = bottomBlockY; y <= topBlockY; y++) {
				for(int z = bottomBlockZ; z <= topBlockZ; z++) {
					blocks.add(l1.getWorld().getBlockAt(x, y, z));
				}
			}
		}
		return blocks;
	}
	
	public void loadVaraibles() {
		this.startcountdown = ConfigEntry.STARTCOUNTDOWN.getValueAsInt();
	}
	
	@Override
	public void run() {
		if(VersionUtils.getOnlinePlayer().size() != 0)
			((Player) VersionUtils.getOnlinePlayer().toArray()[0]).getWorld().setTime(1000);

		if(startcountdown != 0)
			Bukkit.broadcastMessage(ConfigMessages.GAME_START_COUNTDOWN.getValue().replace("%countdown%", startcountdown == 1 ? "einer" : String.valueOf(startcountdown)));

		if(startcountdown == ConfigEntry.STARTCOUNTDOWN.getValueAsInt() || startcountdown == 1) {
			for(VaroPlayer pl1 : VaroPlayer.getOnlinePlayer()) {
				if(pl1.getStats().isSpectator())
					continue;

				Player pl = pl1.getPlayer();
				pl.setGameMode(GameMode.ADVENTURE);
				pl1.cleanUpPlayer();
			}
		}

		if(startcountdown == 5 || startcountdown == 4 || startcountdown == 3 || startcountdown == 2 || startcountdown == 1) {
			for(VaroPlayer vp : VaroPlayer.getOnlinePlayer()) {
				if(vp.getStats().isSpectator())
					continue;

				Player pl = vp.getPlayer();
				pl.playSound(pl.getLocation(), Sounds.NOTE_BASS_DRUM.bukkitSound(), 1, 1);

				String[] title = ConfigMessages.GAME_VARO_START_TITLE.getValue().replace("%countdown%", String.valueOf(startcountdown)).split("\n");
				if(title.length != 0)
					vp.getNetworkManager().sendTitle(title[0], title.length == 2 ? title[1] : "");
			}
		}

		if(startcountdown == 0) {
			for(VaroPlayer pl1 : VaroPlayer.getOnlinePlayer()) {
				if(pl1.getStats().isSpectator())
					continue;

				Player pl = pl1.getPlayer();
				pl.playSound(pl.getLocation(), Sounds.NOTE_PLING.bukkitSound(), 1, 1);
				pl.setGameMode(GameMode.SURVIVAL);
				pl1.cleanUpPlayer();
				pl1.getStats().loadStartDefaults();
			}

			if(VaroAPI.getEventManager().executeEvent(new VaroStartEvent(game))) {
				startcountdown = ConfigEntry.STARTCOUNTDOWN.getValueAsInt();
				Bukkit.getScheduler().cancelTask(game.getStartScheduler());
				return;
			}

			this.game.setGamestate(GameState.STARTED);
			this.game.setFirstTime(true);
			this.startcountdown = ConfigEntry.STARTCOUNTDOWN.getValueAsInt();
			this.game.setMinuteTimer(new BorderDecreaseMinuteTimer());
			
			fillChests();
			VaroUtils.getMainWorld().strikeLightningEffect(VaroUtils.getMainWorld().getSpawnLocation());
			
			Bukkit.broadcastMessage(ConfigMessages.GAME_VARO_START.getValue());
			Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, ConfigMessages.ALERT_GAME_STARTED.getValue());
			Bukkit.getScheduler().cancelTask(game.getStartScheduler());

			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

				@Override
				public void run() {
					game.setFirstTime(false);
				}
			}, ConfigEntry.PLAY_TIME.getValueAsInt() * 60 * 20);

			Main.getDataManager().getListManager().getStartItems().giveToAll();
			if(ConfigEntry.STARTPERIOD_PROTECTIONTIME.getValueAsInt() > 0) {
				Bukkit.broadcastMessage(ConfigMessages.PROTECTION_START.getValue().replace("%seconds%", String.valueOf(ConfigEntry.STARTPERIOD_PROTECTIONTIME.getValueAsInt())));
				game.setProtection(new ProtectionTime());
			}

			game.setStartThread(null);
			return;
		}

		startcountdown--;
	}
}