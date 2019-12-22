package de.cuuky.varo.entity.player;

import org.bukkit.entity.Player;

import de.cuuky.varo.entity.player.stats.Stats;
import de.cuuky.varo.entity.player.stats.VaroInventory;
import de.cuuky.varo.entity.player.stats.stat.PlayerState;
import de.cuuky.varo.entity.player.stats.stat.Rank;
import de.cuuky.varo.entity.player.stats.stat.Strike;
import de.cuuky.varo.entity.player.stats.stat.YouTubeVideo;
import de.cuuky.varo.entity.player.stats.stat.inventory.InventoryBackup;
import de.cuuky.varo.entity.player.stats.stat.inventory.VaroSaveable;
import de.cuuky.varo.entity.player.stats.stat.offlinevillager.OfflineVillager;
import de.cuuky.varo.serialize.VaroSerializeObject;
import de.cuuky.varo.version.VersionUtils;

public class PlayerHandler extends VaroSerializeObject {

	private static PlayerHandler instance;

	static {
		registerClass(Rank.class);
		registerClass(Strike.class);
		registerClass(Stats.class);
		registerClass(YouTubeVideo.class);
		registerClass(VaroSaveable.class);
		registerClass(InventoryBackup.class);
		registerClass(VaroInventory.class);
		registerClass(OfflineVillager.class);
		registerEnum(PlayerState.class);
	}

	private PlayerHandler() {
		super(VaroPlayer.class, "/stats/players.yml");

		load();

		for (Player player : VersionUtils.getOnlinePlayer())
			if (VaroPlayer.getPlayer(player) == null)
				new VaroPlayer(player).register();
	}

	public static void initialise() {
		if (instance == null) {
			instance = new PlayerHandler();
		}
	}

	@Override
	public void onSave() {
		clearOld();

		for (VaroPlayer vp : VaroPlayer.getVaroPlayer())
			save(String.valueOf(vp.getId()), vp, getConfiguration());

		saveFile();
	}
}