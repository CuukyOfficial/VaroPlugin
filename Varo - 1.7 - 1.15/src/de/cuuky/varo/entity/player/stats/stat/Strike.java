package de.cuuky.varo.entity.player.stats.stat;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.bukkit.Location;

import de.cuuky.varo.configuration.config.ConfigEntry;
import de.cuuky.varo.configuration.messages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.logger.logger.EventLogger;
import de.cuuky.varo.logger.logger.EventLogger.LogType;
import de.cuuky.varo.serialize.identifier.VaroSerializeField;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;
import de.cuuky.varo.utils.VaroUtils;

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

		if(ConfigEntry.STRIKE_BAN_AFTER_STRIKE_HOURS.isIntActivated() && !ConfigEntry.STRIKE_BAN_AT_POST.getValueAsBoolean())
			banUntil = DateUtils.addHours(new Date(), ConfigEntry.STRIKE_BAN_AFTER_STRIKE_HOURS.getValueAsInt());

		switch(number) {
		case 1:
			break;
		case 2:
			striked.getStats().clearInventory();
			break;
		case 3:
		default:
			striked.getStats().setState(PlayerState.DEAD);
			if(striked.isOnline())
				striked.getPlayer().kickPlayer(ConfigMessages.KICK_TOO_MANY_STRIKES.getValue());
			break;
		}

		if(!ConfigEntry.STRIKE_POST_RESET_HOUR.getValueAsBoolean())
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
		if(ConfigEntry.STRIKE_BAN_AFTER_STRIKE_HOURS.isIntActivated() && ConfigEntry.STRIKE_BAN_AT_POST.getValueAsBoolean())
			banUntil = DateUtils.addHours(new Date(), ConfigEntry.STRIKE_BAN_AFTER_STRIKE_HOURS.getValueAsInt());

		switch(number) {
		case 1:
			if(striked.getStats().getLastLocation() == null) {
				Location loc = VaroUtils.getMainWorld().getSpawnLocation();
				EventLogger.getInstance().println(LogType.STRIKE, ConfigMessages.ALERT_FIRST_STRIKE_NEVER_ONLINE.getValue().replace("%player%", striked.getName()).replace("%pos%", "X:" + loc.getBlockX() + ", Y:" + loc.getBlockY() + ", Z:" + loc.getBlockZ() + " & world: " + loc.getWorld().getName()).replace("%strikeBegründung%", reason).replace("%striker%", striker));
			} else {
				Location loc = striked.isOnline() ? striked.getPlayer().getLocation() : striked.getStats().getLastLocation();
				EventLogger.getInstance().println(LogType.STRIKE, ConfigMessages.ALERT_FIRST_STRIKE.getValue().replace("%player%", striked.getName()).replace("%pos%", "X:" + loc.getBlockX() + ", Y:" + loc.getBlockY() + ", Z:" + loc.getBlockZ() + " & world: " + loc.getWorld().getName()).replace("%strikeBegründung%", reason).replace("%striker%", striker));
			}
			break;
		case 2:
			EventLogger.getInstance().println(LogType.STRIKE, ConfigMessages.ALERT_SECOND_STRIKE.getValue().replace("%player%", striked.getName()).replace("%strikeBegründung%", reason).replace("%striker%", striker));
			break;
		case 3:
			EventLogger.getInstance().println(LogType.STRIKE, ConfigMessages.ALERT_THRID_STRIKE.getValue().replace("%player%", striked.getName()).replace("%strikeBegründung%", reason).replace("%striker%", striker));
			break;
		default:
			EventLogger.getInstance().println(LogType.STRIKE, ConfigMessages.ALERT_GENERAL_STRIKE.getValue().replace("%player%", striked.getName()).replace("%strikeNumber%", String.valueOf(number)).replace("%strikeBegründung%", reason).replace("%striker%", striker));
			break;
		}

		posted = true;
	}
}