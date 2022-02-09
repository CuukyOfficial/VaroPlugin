package de.cuuky.varo.entity.player.event.events;

import de.cuuky.cfw.version.VersionUtils;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.event.BukkitEvent;
import de.cuuky.varo.entity.player.event.BukkitEventType;
import de.cuuky.varo.entity.player.stats.VaroInventory;
import de.cuuky.varo.entity.player.stats.stat.inventory.InventoryBackup;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

public class DeathEvent extends BukkitEvent {

	private Sound deathSound;

	public DeathEvent() {
		super(BukkitEventType.DEATH);
		this.deathSound = Sound.valueOf(ConfigSetting.DEATH_SOUND.getValueAsString());
	}

	private void dropInventory(VaroInventory inventory, Location location) {
		for (ItemStack item : inventory.getInventory().getContents())
			if (item != null && item.getType() != Material.AIR)
				location.getWorld().dropItemNaturally(location, item);
	}

	@Override
	public void onExec(VaroPlayer player) {
        Location location;
        if (player.isOnline()) {
            player.getStats().addInventoryBackup(new InventoryBackup(player));
            location = player.getPlayer().getLocation();
        } else location = player.getStats().getLastLocation();

		World world = location.getWorld();
		for (int i = 0; i < 3; i++)
			world.playEffect(location, Effect.MOBSPAWNER_FLAMES, 1);

		if (ConfigSetting.DEATH_LIGHTNING_EFFECT.getValueAsBoolean())
			world.strikeLightningEffect(location);

		if (ConfigSetting.DEATH_SOUND_ENABLED.getValueAsBoolean())
			VersionUtils.getVersionAdapter().getOnlinePlayers().forEach(pl -> pl.playSound(pl.getLocation(), this.deathSound, 1, 1));

		for (ItemStack stack : Main.getDataManager().getListManager().getDeathItems().getItems())
			if (stack.getType() != Material.AIR)
				world.dropItemNaturally(location, stack);

		if (ConfigSetting.BACKPACK_PLAYER_DROP_ON_DEATH.getValueAsBoolean())
			if (player.getStats().getPlayerBackpack() != null)
				dropInventory(player.getStats().getPlayerBackpack(), location);

		if (ConfigSetting.BACKPACK_TEAM_DROP_ON_DEATH.getValueAsBoolean())
			if (player.getTeam() != null && player.getTeam().isDead() && player.getTeam().getTeamBackPack() != null)
				dropInventory(player.getTeam().getTeamBackPack(), location);
	}
}