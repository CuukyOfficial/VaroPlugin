package de.varoplugin.varo.player.stats.stat;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.bukkit.Location;

import de.varoplugin.varo.Main;
import de.varoplugin.varo.config.language.Messages;
import de.varoplugin.varo.config.language.Contexts.StrikeContext;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;
import de.varoplugin.varo.logger.logger.EventLogger.LogType;
import de.varoplugin.varo.player.VaroPlayer;
import de.varoplugin.varo.serialize.identifier.VaroSerializeField;
import de.varoplugin.varo.serialize.identifier.VaroSerializeable;
import io.github.almightysatan.slams.Placeholder;

public class Strike implements VaroSerializeable {

	@VaroSerializeField(path = "strikedId")
	private int strikedId;

	@VaroSerializeField(path = "striker")
	private String striker;

	@VaroSerializeField(path = "acquired")
	private Date acquired;

	@VaroSerializeField(path = "banUntil")
	private Date banUntil;

	@VaroSerializeField(path = "number")
	private int number;

	@VaroSerializeField(path = "posted")
	private boolean posted;

	@VaroSerializeField(path = "reason")
	private String reason;

	private VaroPlayer striked;

	public Strike() {}

	public Strike(String reason, VaroPlayer striked, String striker) {
		this.reason = reason;
		this.striker = striker;
		this.striked = striked;
		this.strikedId = striked.getId();
		this.acquired = new Date();
		this.posted = false;
	}

	public void activate(int number) {
		this.number = number;

		if (ConfigSetting.STRIKE_BAN_AFTER_STRIKE_HOURS.isIntActivated() && !ConfigSetting.STRIKE_BAN_AT_POST.getValueAsBoolean())
			banUntil = DateUtils.addHours(new Date(), ConfigSetting.STRIKE_BAN_AFTER_STRIKE_HOURS.getValueAsInt());

		switch (number) {
		case 1:
			break;
		case 2:
			if (striked.isOnline())
				striked.getStats().clearInventory();
			else
				striked.getStats().setWillClear(true);
			break;
		case 3:
		default:
			striked.getStats().setState(PlayerState.DEAD);
			if (striked.isOnline())
			    Messages.PLAYER_KICK_TOO_MANY_STRIKES.kick(this.striked);
			break;
		}

		if (!ConfigSetting.STRIKE_POST_RESET_HOUR.getValueAsBoolean())
			post();
	}

	public void decreaseStrikeNumber() {
		this.number -= 1;
	}

	public Date getAcquiredDate() {
		return acquired;
	}

	public Date getBanUntil() {
		return banUntil;
	}

	public String getReason() {
		return reason;
	}

	public VaroPlayer getStriked() {
		return striked;
	}

	public int getStrikeNumber() {
		return number;
	}

	public String getStriker() {
		return striker;
	}

	public boolean isPosted() {
		return posted;
	}

	@Override
	public void onDeserializeEnd() {
		this.striked = VaroPlayer.getPlayer(strikedId);
	}

	@Override
	public void onSerializeStart() {}

	public void post() {
		if (ConfigSetting.STRIKE_BAN_AFTER_STRIKE_HOURS.isIntActivated() && ConfigSetting.STRIKE_BAN_AT_POST.getValueAsBoolean())
			banUntil = DateUtils.addHours(new Date(), ConfigSetting.STRIKE_BAN_AFTER_STRIKE_HOURS.getValueAsInt());

		StrikeContext ctx = new StrikeContext(this.striked, this.reason, this.striker, this.number);
		switch (number) {
		case 1:
			if (striked.getStats().getLastLocation() == null) {
				Location loc = Main.getVaroGame().getVaroWorldHandler().getMainWorld().getWorld().getSpawnLocation();
				Messages.LOG_STRIKE_FIRST_NEVER_ONLINE.log(LogType.STRIKE, ctx, Placeholder.constant("strike-location", "X:" + loc.getBlockX() + ", Y:" + loc.getBlockY() + ", Z:" + loc.getBlockZ() + " & world: " + loc.getWorld().getName()));
			} else {
				Location loc = striked.isOnline() ? striked.getPlayer().getLocation() : striked.getStats().getLastLocation();
				Messages.LOG_STRIKE_FIRST.log(LogType.STRIKE, ctx, Placeholder.constant("strike-location", "X:" + loc.getBlockX() + ", Y:" + loc.getBlockY() + ", Z:" + loc.getBlockZ() + " & world: " + loc.getWorld().getName()));
			}
			break;
		case 2:
			Messages.LOG_STRIKE_SECOND.log(LogType.STRIKE, ctx);
            break;
		case 3:
			Messages.LOG_STRIKE_THRID.log(LogType.STRIKE, ctx);
			break;
		default:
		    Messages.LOG_STRIKE_GENERAL.log(LogType.STRIKE, ctx);
		    break;
		}

		posted = true;
	}
}