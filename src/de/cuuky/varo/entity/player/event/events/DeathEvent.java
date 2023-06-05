package de.cuuky.varo.entity.player.event.events;

import de.varoplugin.cfw.version.VersionUtils;
import de.cuuky.cfw.version.types.Sounds;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.event.BukkitEventType;
import de.cuuky.varo.entity.player.stats.stat.inventory.InventoryBackup;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

public class DeathEvent extends AbstractDeathEvent {

	public DeathEvent() {
		super(BukkitEventType.DEATH);
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
			VersionUtils.getVersionAdapter().getOnlinePlayers().forEach(pl -> pl.playSound(pl.getLocation(),
                Sound.valueOf(((Sounds) ConfigSetting.DEATH_SOUND.getValueAsEnum()).bukkitSound().name()), 1, 1));

        this.dropInventory(Main.getDataManager().getListManager().getDeathItems().getItems().toArray(new ItemStack[0]), location);
	}
}