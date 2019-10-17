package de.cuuky.varo.player;

import org.bukkit.entity.Player;

import de.cuuky.varo.player.stats.Stats;
import de.cuuky.varo.player.stats.VaroInventory;
import de.cuuky.varo.player.stats.stat.PlayerState;
import de.cuuky.varo.player.stats.stat.Rank;
import de.cuuky.varo.player.stats.stat.Strike;
import de.cuuky.varo.player.stats.stat.YouTubeVideo;
import de.cuuky.varo.player.stats.stat.inventory.InventoryBackup;
import de.cuuky.varo.player.stats.stat.inventory.VaroSaveable;
import de.cuuky.varo.player.stats.stat.offlinevillager.OfflineVillager;
import de.cuuky.varo.serialize.VaroSerializeHandler;
import de.cuuky.varo.version.VersionUtils;

public class PlayerHandler extends VaroSerializeHandler {

	static {
		VaroSerializeHandler.registerClass(Rank.class);
		VaroSerializeHandler.registerClass(Strike.class);
		VaroSerializeHandler.registerClass(Stats.class);
		VaroSerializeHandler.registerClass(YouTubeVideo.class);
		VaroSerializeHandler.registerClass(VaroSaveable.class);
		VaroSerializeHandler.registerClass(InventoryBackup.class);
		VaroSerializeHandler.registerClass(VaroInventory.class);
		VaroSerializeHandler.registerClass(OfflineVillager.class);
		VaroSerializeHandler.registerEnum(PlayerState.class);
	}

	public PlayerHandler() {
		super(VaroPlayer.class, "/stats/players.yml");

		load();

		for (Player player : VersionUtils.getOnlinePlayer())
			if (VaroPlayer.getPlayer(player) == null)
				new VaroPlayer(player).register();
	}

	@Override
	public void onSave() {
		clearOld();

		for (VaroPlayer vp : VaroPlayer.getVaroPlayer())
			save(String.valueOf(vp.getId()), vp, getConfiguration());

		saveFile();
	}
}
