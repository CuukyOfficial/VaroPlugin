package de.cuuky.varo.entity.player;

import de.varoplugin.cfw.version.VersionUtils;
import de.cuuky.varo.entity.player.stats.Stats;
import de.cuuky.varo.entity.player.stats.VaroInventory;
import de.cuuky.varo.entity.player.stats.stat.PlayerState;
import de.cuuky.varo.entity.player.stats.stat.Rank;
import de.cuuky.varo.entity.player.stats.stat.Strike;
import de.cuuky.varo.entity.player.stats.stat.YouTubeVideo;
import de.cuuky.varo.entity.player.stats.stat.inventory.InventoryBackup;
import de.cuuky.varo.entity.player.stats.stat.inventory.VaroSaveable;
import de.cuuky.varo.entity.player.stats.stat.inventory.VaroSaveable.SaveableType;
import de.cuuky.varo.entity.player.stats.stat.offlinevillager.OfflineVillager;
import de.cuuky.varo.gui.settings.VaroMenuColor;
import de.cuuky.varo.serialize.VaroSerializeObject;
import org.bukkit.entity.Player;

public class VaroPlayerHandler extends VaroSerializeObject {

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
		registerEnum(SaveableType.class);
		registerEnum(VaroMenuColor.class);
	}

	public VaroPlayerHandler() {
		super(VaroPlayer.class, "/stats/players.yml");

		load();

		for (Player player : VersionUtils.getVersionAdapter().getOnlinePlayers())
			if (VaroPlayer.getPlayer(player) == null) {
				VaroPlayer vp = new VaroPlayer(player);
				vp.register();
				vp.setPlayer(player);
			}
	}

	@Override
	public void onSave() {
		clearOld();

		for (VaroPlayer vp : VaroPlayer.getVaroPlayers())
			save(String.valueOf(vp.getId()), vp, getConfiguration());

		saveFile();
	}
}