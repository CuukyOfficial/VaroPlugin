package de.varoplugin.varo.player;

import de.varoplugin.cfw.version.VersionUtils;
import de.varoplugin.varo.gui.settings.VaroMenuColor;
import de.varoplugin.varo.player.stats.Stats;
import de.varoplugin.varo.player.stats.VaroInventory;
import de.varoplugin.varo.player.stats.stat.*;
import de.varoplugin.varo.player.stats.stat.inventory.InventoryBackup;
import de.varoplugin.varo.player.stats.stat.inventory.VaroSaveable;
import de.varoplugin.varo.player.stats.stat.inventory.VaroSaveable.SaveableType;
import de.varoplugin.varo.player.stats.stat.offlinevillager.OfflineVillager;
import de.varoplugin.varo.serialize.VaroSerializeObject;

import org.bukkit.entity.Player;

public class VaroPlayerHandler extends VaroSerializeObject {

	static {
		registerClass(Rank.class);
		registerClass(Strike.class);
		registerClass(StrikeTemplate.class);
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
		super(VaroPlayer.class, "/stats/players5.yml");

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