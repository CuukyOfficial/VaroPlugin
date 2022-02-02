package de.cuuky.varo.entity.player.event.events;

import org.bukkit.entity.Player;

import de.cuuky.cfw.version.VersionUtils;
import de.cuuky.varo.Main;
import de.cuuky.varo.combatlog.PlayerHit;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.event.BukkitEvent;
import de.cuuky.varo.entity.player.event.BukkitEventType;

public class KillEvent extends BukkitEvent {

	public KillEvent() {
		super(BukkitEventType.KILL);
	}

	@Override
	public void onExec(VaroPlayer player) {
		PlayerHit hit1 = PlayerHit.getHit(player.getPlayer());
		if (hit1 != null)
			hit1.over();
		
		this.checkHealth(player.getPlayer());

		if (player.getTeam() != null) {
			Number addNumber = (Number) ConfigSetting.ADD_TEAM_LIFE_ON_KILL.getValue(), maxNumber = (Number) ConfigSetting.MAX_TEAM_LIFES.getValue();
			double add = addNumber.doubleValue(), max = maxNumber.doubleValue();

			if (add > 0 && player.getTeam().getLifes() + add <= max) {
				player.getTeam().setLifes(player.getTeam().getLifes() + add);
				player.sendMessage(ConfigMessages.DEATH_KILL_LIFE_ADD);
			}
		}

		player.getStats().addKill();
		super.onExec(player);
	}

	private void checkHealth(Player killer) {
		int healthAdd = ConfigSetting.KILLER_ADD_HEALTH_ON_KILL.getValueAsInt();
		if (healthAdd > 0) {
			double hearts = VersionUtils.getHearts(killer) + healthAdd;
			killer.setHealth(Math.min(hearts, 20.0));
			killer.sendMessage(Main.getPrefix() + "§7Du hast durch den Kill an §4" + healthAdd / 2 + "§7 Herzen regeneriert bekommen!");
		}
	}
}